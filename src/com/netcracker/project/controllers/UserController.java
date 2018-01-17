package com.netcracker.project.controllers;

import com.netcracker.project.controllers.validators.UserValidator;
import com.netcracker.project.controllers.validators.errorMessage.ErrorMessages;
import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.entity.User;
import com.netcracker.project.model.enums.JobTitle;
import com.netcracker.project.model.enums.ProjectStatus;
import com.netcracker.project.model.enums.UserRole;
import com.netcracker.project.model.enums.UserStatus;
import com.netcracker.project.services.EmailService;
import com.netcracker.project.services.impl.DateConverterService;
import com.netcracker.project.services.impl.PasswordService;
import java.math.BigInteger;
import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserDAO userDAO;
  @Autowired
  private EmailService emailService;

  private static final Logger logger = Logger
      .getLogger(UserController.class);

  private DateConverterService converter = new DateConverterService();
  private ShaPasswordEncoder encoder = new ShaPasswordEncoder();

  @Secured({"ROLE_ADMIN"})
  @RequestMapping(value = "/createUser", method = RequestMethod.GET)
  public String createUserGet() {
    logger.info("createUserGet() method. Return /createUser");
    return "user/createUser";
  }

  @Secured({"ROLE_ADMIN"})
  @RequestMapping(value = "/createUser", method = RequestMethod.POST)
  public String createUserPost(
      @RequestParam(value = "lastName") String lastName,
      @RequestParam(value = "firstName") String firstName,
      @RequestParam(value = "email") String email,
      @RequestParam(value = "dateOfBirth") String dateOfBirth,
      @RequestParam(value = "hireDate") String hireDate,
      @RequestParam(value = "phoneNumber") String phoneNumber,
      @RequestParam(value = "jobTitle", required = false) String jobTitle,
      @RequestParam(value = "login") String login,
      @RequestParam(value = "admin", required = false) String admin,
      Model model) {
    Map<String, String> errorMap = new HashMap<>();
    UserValidator validator = new UserValidator();
    Integer loginExistence = userDAO.findIfLoginExists(login);
    errorMap = validator.validateUserLoginIfExist(loginExistence);

    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "responseStatus/unsuccess";
    }

    if (jobTitle == null && admin == null) {
      errorMap.put("JOB_TITLE_ERROR", ErrorMessages.JOB_TITLE_ERROR);
      model.addAttribute("errorMap", errorMap);
      return "responseStatus/unsuccess";
    }

    errorMap = validator
        .validateCreate(lastName, firstName, email, dateOfBirth,
            hireDate, phoneNumber, jobTitle);

    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "responseStatus/unsuccess";
    }
    String userRole = null;

    if (admin != null) {
      userRole = UserRole.ROLE_ADMIN.name();
      jobTitle = JobTitle.SOFTWARE_ENGINEER.name();
    } else {
      if (jobTitle.equals(JobTitle.LINE_MANAGER.name())) {
        userRole = UserRole.ROLE_LM.name();
      }
      if (jobTitle.equals(JobTitle.PROJECT_MANAGER.name())) {
        userRole = UserRole.ROLE_PM.name();
      }
      if (jobTitle.equals(JobTitle.SOFTWARE_ENGINEER.name())) {
        userRole = UserRole.ROLE_SE.name();
      }
    }

    String password = new PasswordService().generatePassword();

    if (userRole != null) {
      User user = new User.UserBuilder()
          .lastName(lastName)
          .firstName(firstName)
          .email(email)
          .dateOfBirth(converter.convertStringToDateFromJSP(dateOfBirth))
          .hireDate(converter.convertStringToDateFromJSP(hireDate))
          .phoneNumber(phoneNumber)
          .jobTitle(JobTitle.valueOf(jobTitle))
          .projectStatus(ProjectStatus.TRANSIT)
          .login(login)
          .password(encoder.encodePassword(password, null))
          .role(UserRole.valueOf(userRole))
          .userStatus(UserStatus.WORKING)
          .build();

      userDAO.createUser(user);
      emailService.sendEmail(email, login, password);
      System.out.println(user.toString());
      return "responseStatus/success";
    }

    return "responseStatus/unsuccess";
  }

  @RequestMapping(value = "/showUser/{login}", method = RequestMethod.GET)
  public String showUserProfile(@PathVariable(value = "login") String login,
      Principal principal,
      Model model) {
    UserValidator validator = new UserValidator();
    Map<String, String> errorMap = new HashMap<>();
    Integer userExistence = userDAO.findUserByLoginIfExists(login);
    errorMap = validator.validateUserLoginExistence(userExistence);

    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "responseStatus/unsuccess";
    }

    User currentUser = userDAO.findUserByLogin(principal.getName());
    User user = userDAO.findFullUserByUserLogin(login);
    model.addAttribute("currentUser", currentUser);
    model.addAttribute("user", user);
    return "user/showUser";
  }

  @Secured({"ROLE_ADMIN"})
  @RequestMapping(value = "/updatePassword/{userId}/{userLogin}", method = RequestMethod.POST)
  public String updateUserPasswordPost(Model model,
      @PathVariable(value = "userId") String userId,
      @PathVariable("userLogin") String login) {
    logger.info("updateUserPassword() method. User id" + userId);
    UserValidator validator = new UserValidator();
    Map<String, String> errorMap = new HashMap<>();

    errorMap = validator.validateInputId(userId);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "responseStatus/unsuccess";
    }

    BigInteger validaUserId = new BigInteger(userId);
    Integer userIfExist = userDAO
        .findUserByUserIdIfExists(validaUserId);
    errorMap = validator.validateUserExistence(userIfExist);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "responseStatus/unsuccess";
    }
    Integer loginExist = userDAO.findIfLoginExists(login);
    errorMap = validator.validateUserExistence(loginExist);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "responseStatus/unsuccess";
    }
    User user = userDAO.findUserByUserId(validaUserId);
    String password = new PasswordService().generatePassword();
    userDAO.updatePassword(user.getUserId(),
        encoder.encodePassword(password, null));
    emailService.sendEmail(user.getEmail(), login, password);
    return "responseStatus/success";
  }

  @Secured({"ROLE_ADMIN"})
  @RequestMapping(value = "/updateJobTitle/{userId}", method = RequestMethod.GET)
  public String updateJobTitle(@PathVariable(value = "userId") String userId,
      Model model) {
    UserValidator validator = new UserValidator();
    Map<String, String> errorMap = new HashMap<>();
    errorMap = validator.validateInputId(userId);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "responseStatus/unsuccess";
    }
    BigInteger validUserId = new BigInteger(userId);
    Integer userExistence = userDAO.findUserByUserIdIfExists(validUserId);
    errorMap = validator.validateUserExistence(userExistence);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "responseStatus/unsuccess";
    }
    User user = userDAO.findUserByUserId(validUserId);
    model.addAttribute("user", user);
    return "user/updateUserJobTitle";
  }

  @Secured({"ROLE_ADMIN"})
  @RequestMapping(value = "/updateJobTitle/{userId}", method = RequestMethod.POST)
  public String updateJobTitlePost(@PathVariable("userId") String userId,
      Model model,
      @RequestParam(value = "jobTitle", required = false) String jobTitle,
      @RequestParam(value = "admin", required = false) String admin) {
    UserValidator validator = new UserValidator();
    Map<String, String> errorMap = new HashMap<>();
    errorMap = validator.validateInputId(userId);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "responseStatus/unsuccess";
    }
    BigInteger validUserId = new BigInteger(userId);
    Integer userExistence = userDAO.findUserByUserIdIfExists(validUserId);
    errorMap = validator.validateUserExistence(userExistence);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "responseStatus/unsuccess";
    }

    if (jobTitle == null && admin == null) {
      errorMap.put("JOB_TITLE_ERROR", ErrorMessages.JOB_TITLE_ERROR);
      model.addAttribute("errorMap", errorMap);
      return "responseStatus/unsuccess";
    }

    if (jobTitle != null) {
      errorMap = validator.validateUserJobTitle(jobTitle);
      if (!errorMap.isEmpty()) {
        model.addAttribute("errorMap", errorMap);
        return "responseStatus/unsuccess";
      }
    }
    String userRole = null;

    if (admin != null) {
      userRole = UserRole.ROLE_ADMIN.name();
      jobTitle = JobTitle.SOFTWARE_ENGINEER.name();
    } else {
      if (jobTitle.equals(JobTitle.LINE_MANAGER.name())) {
        userRole = UserRole.ROLE_LM.name();
      }
      if (jobTitle.equals(JobTitle.SOFTWARE_ENGINEER.name())) {
        userRole = UserRole.ROLE_SE.name();
      }
      if (jobTitle.equals(JobTitle.PROJECT_MANAGER.name())) {
        userRole = UserRole.ROLE_PM.name();
      }
    }
    userDAO.updateJobTitleAndUserRole(validUserId,
        UserRole.valueOf(userRole).getId(), JobTitle.valueOf(jobTitle).getId());
    return "responseStatus/success";
  }


  @Secured({"ROLE_ADMIN"})
  @RequestMapping(value = "/updateUserEmail/{userId}", method = RequestMethod.GET)
  public String updateUserEmail(@PathVariable(value = "userId") String userId,
      Model model) {
    UserValidator validator = new UserValidator();
    Map<String, String> errorMap = new HashMap<>();
    errorMap = validator.validateInputId(userId);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "responseStatus/unsuccess";
    }
    BigInteger validUserId = new BigInteger(userId);
    Integer userExistence = userDAO.findUserByUserIdIfExists(validUserId);
    errorMap = validator.validateUserExistence(userExistence);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "responseStatus/unsuccess";
    }
    User user = userDAO.findUserByUserId(validUserId);
    model.addAttribute("user", user);
    return "user/updateUserEmail";
  }

  @Secured({"ROLE_ADMIN"})
  @RequestMapping(value = "/updateUserEmail/{userId}", method = RequestMethod.POST)
  public String updateUserEmail(@PathVariable(value = "userId") String userId,
      @RequestParam(value = "email") String email,
      Model model) {
    UserValidator validator = new UserValidator();
    Map<String, String> errorMap = new HashMap<>();
    errorMap = validator.validateInputId(userId);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "responseStatus/unsuccess";
    }
    BigInteger validUserId = new BigInteger(userId);
    Integer userExistence = userDAO.findUserByUserIdIfExists(validUserId);
    errorMap = validator.validateUserExistence(userExistence);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "responseStatus/unsuccess";
    }
    errorMap = validator.validateEmail(email);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "responseStatus/unsuccess";
    }

    userDAO.updateEmail(validUserId, email);
    return "responseStatus/success";
  }

  @RequestMapping(value = "/updateUserPhoneNumber/{userId}", method = RequestMethod.GET)
  public String updateUserPhoneNumber(@PathVariable("userId") String userId,
      Principal principal,
      HttpServletRequest request,
      Model model) {
    UserValidator validator = new UserValidator();
    Map<String, String> errorMap = new HashMap<>();
    errorMap = validator.validateInputId(userId);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "responseStatus/unsuccess";
    }
    BigInteger validUserId = new BigInteger(userId);
    Integer userExistence = userDAO.findUserByUserIdIfExists(validUserId);
    errorMap = validator.validateUserExistence(userExistence);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "responseStatus/unsuccess";
    }

    User user = userDAO.findUserByUserId(validUserId);

    User currentUser = userDAO.findUserByLogin(principal.getName());
    if (!currentUser.getUserId().equals(user.getUserId()) && !request
        .isUserInRole("ROLE_ADMIN")) {
      errorMap.put("INVALID_USER_ERROR", ErrorMessages.INVALID_USER_ERROR);
      model.addAttribute("errorMap", errorMap);
      return "responseStatus/unsuccess";
    }

    model.addAttribute("user", user);
    return "user/updateUserPhoneNumber";
  }

  @RequestMapping(value = "/updateUserPhoneNumber/{userId}", method = RequestMethod.POST)
  public String updateUserPhoneNumber(
      @PathVariable(value = "userId") String userId,
      @RequestParam(value = "phoneNumber") String phoneNumber,
      Principal principal,
      Model model) {
    UserValidator validator = new UserValidator();
    Map<String, String> errorMap = new HashMap<>();
    errorMap = validator.validateInputId(userId);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "responseStatus/unsuccess";
    }
    BigInteger validUserId = new BigInteger(userId);
    Integer userExistence = userDAO.findUserByUserIdIfExists(validUserId);
    errorMap = validator.validateUserExistence(userExistence);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "responseStatus/unsuccess";
    }
    errorMap = validator.validatePhoneNumber(phoneNumber);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "responseStatus/unsuccess";
    }

    userDAO.updatePhoneNumber(validUserId, phoneNumber);
    return "responseStatus/success";
  }

  @RequestMapping(value = "/findUserByLastNameAndFirstName/lastName={lastName}&firstName={firstName}",
      method = RequestMethod.GET)
  public String findUserByLastNameAndFirstName(Model model,
      @PathVariable("lastName") String lastName,
      @PathVariable("firstName") String firstName) {
    Collection<User> userCollection = userDAO
        .findUserByLastNameAndFirstName(lastName, firstName);
    model.addAttribute("modelUser", userCollection);
    return "user/show_user_list";
  }

  @RequestMapping(value = "/findUserByProjectId/{projectId}", method = RequestMethod.GET)
  public String findUserByProjectId(
      @PathVariable("projectId") BigInteger projectId,
      Model model) {

    logger.info("findUserByProjectId() method. projectId: " + projectId);
    Collection<User> userCollection = userDAO.findUserByProjectId(projectId);
    model.addAttribute("modelUser", userCollection);

    return "user/show_user_list";
  }
}

