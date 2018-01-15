package com.netcracker.project.controllers;

import com.netcracker.project.controllers.validators.UserValidator;
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
      @RequestParam(value = "jobTitle") String jobTitle,
      @RequestParam(value = "login") String login,
      @RequestParam(value = "userRole") String userRole,
      Model model) {
    Map<String, String> errorMap = new HashMap<>();
    UserValidator validator = new UserValidator();
    Integer loginExistence = userDAO.findIfLoginExists(login);
    errorMap = validator.validateUserLoginIfExist(loginExistence);

    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "responseStatus/unsuccess";
    }

    errorMap = validator
        .validateCreate(lastName, firstName, email, dateOfBirth,
            hireDate, phoneNumber, jobTitle, userRole);

    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "responseStatus/unsuccess";
    }

    String password = new PasswordService().generatePassword();

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

    return "responseStatus/success";
  }

  @RequestMapping(value = "/showUser/{login}", method = RequestMethod.GET)
  public String showUserProfile(@PathVariable(value = "login") String login,
      Model model) {
    UserValidator validator = new UserValidator();
    Map<String, String> errorMap = new HashMap<>();
    Integer userExistence = userDAO.findUserByLoginIfExist(login);
    errorMap = validator.validateUserLoginExistence(userExistence);

    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "responseStatus/unsuccess";
    }

    User user = userDAO.findFullUserByUserLogin(login);
    model.addAttribute("user", user);
    return "user/showUser";
  }

  @Secured({"ROLE_ADMIN"})
  @RequestMapping(value = "/updateUserPassword/{userId}", method = RequestMethod.GET)
  public String updateUserPassword(Model model,
      @PathVariable(value = "userId") String userId) {

    logger.info("updateUserPassword() method. userId: " + userId);
    BigInteger bigIntegerId = new BigInteger(userId);
    User user = userDAO.findUserByUserId(bigIntegerId);
    model.addAttribute("user", user);
    return "user/updateUserPassword";
  }

  @Secured({"ROLE_ADMIN"})
  @RequestMapping(value = "/updateUserPassword/{userId}", method = RequestMethod.POST)
  public String updateUserPassword(Model model,
      @PathVariable(value = "userId") String userId,
      @RequestParam(value = "password") String password) {

    logger.info("updateUserPassword() method. User id" + userId
        + "password: " + password);

    BigInteger bigIntegerId = new BigInteger(userId);
    userDAO.updatePassword(bigIntegerId, password);
    return "responseStatus/success";
  }

  @Secured({"ROLE_ADMIN"})
  @RequestMapping(value = "/updateUserEmail/{userId}", method = RequestMethod.GET)
  public String updateUserEmail(Model model,
      @PathVariable(value = "userId") String userId) {

    logger.info("updateUserEmail() method. userId: " + userId);
    BigInteger bigIntegerId = new BigInteger(userId);
    User user = userDAO.findUserByUserId(bigIntegerId);
    model.addAttribute("user", user);
    return "user/updateUserEmail";
  }

  @Secured({"ROLE_ADMIN"})
  @RequestMapping(value = "/updateUserEmail/{userId}", method = RequestMethod.POST)
  public String updateUserEmail(Model model,
      @PathVariable(value = "userId") String userId,
      @RequestParam(value = "email") String email) {

    logger.info("updateUserPassword() method. User id" + userId
        + "email: " + email);
    BigInteger bigIntegerId = new BigInteger(userId);
    userDAO.updateEmail(bigIntegerId, email);
    return "responseStatus/success";
  }

  @RequestMapping(value = "/updateUserPhoneNumber", method = RequestMethod.GET)
  public String updateUserPhoneNumber(Model model,
      Principal principal) {

    String currentUserLogin = principal.getName();
    User user = userDAO.findUserByLogin(currentUserLogin);
    model.addAttribute("user", user);
    return "user/updateUserPhoneNumber";
  }

  @RequestMapping(value = "/updateUserPhoneNumber", method = RequestMethod.POST)
  public String updateUserPhoneNumber(Model model, Principal principal,
      @RequestParam(value = "phoneNumber") String phoneNumber) {
    String currentUserLogin = principal.getName();
    User user = userDAO.findUserByLogin(currentUserLogin);
    userDAO.updatePhoneNumber(user.getUserId(), phoneNumber);
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

