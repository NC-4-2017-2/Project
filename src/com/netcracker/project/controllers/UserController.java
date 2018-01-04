package com.netcracker.project.controllers;

import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.entity.User;
import com.netcracker.project.model.enums.JobTitle;
import com.netcracker.project.model.enums.ProjectStatus;
import com.netcracker.project.model.enums.UserRole;
import com.netcracker.project.model.enums.UserStatus;
import com.netcracker.project.services.impl.DateConverterService;
import com.netcracker.project.services.impl.EmailServiceImpl;
import java.math.BigInteger;
import java.security.Principal;
import java.util.Collection;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

  private static final Logger logger = Logger
      .getLogger(UserController.class);
  private DateConverterService converter = new DateConverterService();
  @Autowired
  private UserDAO userDAO;
  @Autowired
  private EmailServiceImpl emailService;
  //private WorkPeriod workPeriod;


  @Secured({"ROLE_ADMIN"})
  @RequestMapping(value = "/createUser", method = RequestMethod.GET)
  public String createUserGet() {
    logger.info("createUserGet() method. Return /createUser");
    return "user/createUser";
  }

  @Secured({"ROLE_ADMIN"})
  @RequestMapping(value = "/createUser", method = RequestMethod.POST)
  public String createUserPost(
      @RequestParam(value = "firstName") String firstName,
      @RequestParam(value = "lastName") String lastName,
      @RequestParam(value = "email") String email,
      @RequestParam(value = "dateOfBirth") String dateOfBirth,
      @RequestParam(value = "hireDate") String hireDate,
      @RequestParam(value = "phoneNumber") String phoneNumber,
      @RequestParam(value = "jobTitle") JobTitle jobTitle,
      @RequestParam(value = "login") String login,
      @RequestParam(value = "password") String password,
      @RequestParam(value = "userRole") UserRole userRole,
      Model model) {

    //todo add and improve validation from my copy of controller
    /*Map<String, String> errorMap = new HashMap<>();
    UserValidator validator = new UserValidator();
    Integer checkUserLogin = userDAO.findUserByLoginIfExist(login);
    if (checkUserLogin >= 1) {
      errorMap.put("USER_EXIST_ERROR",
          login + " " + ErrorMessages.USER_EXIST_ERROR);
      model.addAttribute("errorMap", errorMap);
      return "user/createUser";
    }*/

    logger.info("POST request createUser(). UserLogin: " + login);

    User user = new User.UserBuilder()
        .lastName(lastName)
        .firstName(firstName)
        .email(email)
        .dateOfBirth(converter.convertStringToDateFromJSP(dateOfBirth))
        .hireDate(converter.convertStringToDateFromJSP(hireDate))
        .phoneNumber(phoneNumber)
        .jobTitle(jobTitle)
        .projectStatus(ProjectStatus.TRANSIT)
        .login(login)
        .password(password)
        .role(userRole)
        .userStatus(UserStatus.FIRED)
        .build();

    logger.info("createUser request from DB. UserLogin: " + login);

    //User returnedUser =
    userDAO.createUser(user);

    //catch null pointer in mailsender variable
    /*MailSender mailSender = new JavaMailSenderImpl() ;
    emailService.setMailSender(mailSender);
    emailService.sendEmail(returnedUser.getEmail(), "Use your pass",
                           returnedUser.getPassword());*/
    return "responseStatus/success";
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
    /*!!!Uncomment when the sequences are added!!!
    //logger.info("createUser request from DB. User id: " + userId);
    //userDAO.createUser(user);*/
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

  /*@RequestMapping(value = "/updateUserProjectStatus", method = RequestMethod.GET)
  public String updateUserProjectStatus(Model model,
      Principal principal) {

    String currentUserLogin = principal.getName();
    User user = userDAO.findUserByLogin(currentUserLogin);
    model.addAttribute("user", user);
    return "user/updateUserProjectStatus";
  }

  @RequestMapping(value = "/updateUserProjectStatus", method = RequestMethod.POST)
  public String updateUserPhoneNumber(Model model, Principal principal,
      @RequestParam(value = "phoneNumber") String phoneNumber) {
    String currentUserLogin = principal.getName();
    User user = userDAO.findUserByLogin(currentUserLogin);
    userDAO.updatePhoneNumber(user.getUserId(), phoneNumber);
    return "responseStatus/success";
  }*/
//    return "responseStatus/success";
//  }
//
//  @RequestMapping(value = "/edit_user/{userId}", method = RequestMethod.GET)
//  public String editUserGet(@PathVariable("userId") Integer userId,
//      Model model) {
//    logger.info("editUserGet() method. userId: " + userId);
//    User user = userDAO.findUserByUserId(BigInteger.valueOf(userId));
//    model.addAttribute("userId", user.getUserId());
//    model.addAttribute("firstName", user.getFirstName());
//    model.addAttribute("lastName", user.getLastName());
//    model.addAttribute("email", user.getEmail());
//    model.addAttribute("phoneNumber", user.getPhoneNumber());
//    model.addAttribute("photo", user.getPhoto());
//    model.addAttribute("login", user.getLogin());
//    model.addAttribute("password", user.getPassword());
//    model.addAttribute("projectStatus", user.getProjectStatus());
//
//    return "project/edit_user";
//  }

  @RequestMapping(value = "/findUserByLogin/{login}", method = RequestMethod.GET)
  public String findUserByLogin(Model model,
      @PathVariable("login") String login) {
    logger.info("findUserByLogin() method. Login: " + login);
    //todo check user existance
    //Integer checkUserLogin = userDAO.findUserByLoginIfExist(login);
    User user = userDAO.findUserByLogin(login);
    model.addAttribute("user", user);
    return "user/showUser";
  }

  @RequestMapping(value = "/findUserByUserId/{userId}", method = RequestMethod.GET)
  public String findUserByUserId(Model model,
      @PathVariable("userId") Integer userId) {
    logger.info("findUserById() method. userId: " + userId);
    User user = userDAO.findUserByUserId(BigInteger.valueOf(userId));
    model.addAttribute("user", user);
    return "user/showUser";
  }

  @RequestMapping(value = "/findUserByLastNameAndFirstName/lastName={lastName}&firstName={firstName}",
      method = RequestMethod.GET)
  public String findUserByLastNameAndFirstName(Model model,
      @PathVariable("lastName") String lastName,
      @PathVariable("firstName") String firstName) {

    logger.info("findUserByLastNameAndFirstName() method. lastName: "
        + lastName
        + " ,firstName: "
        + lastName);
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

  @RequestMapping(value = "/showUser/{userId}", method = RequestMethod.GET)
  public String showUser(
      @PathVariable("userId") String userId,
      Model model) {
    //todo check user existance
    BigInteger bigIntegerUserId = new BigInteger(userId);
    User user = userDAO.findUserByUserId(bigIntegerUserId);
    model.addAttribute("user", user);
    return "user/showUser";
  }

  @RequestMapping(value = "/viewUser/{userId}", method = RequestMethod.GET)
  public String viewUserByUserId(
      @PathVariable("userId") BigInteger userId,
      Model model) {
    logger.info(
        "viewUserByUserId() method. Params: userId: " + userId);
    User user = userDAO.findUserByUserId(userId);
    model.addAttribute("vacation", user);
    return "vacation/showUser";
  }

  //in future
  /*@Secured({"ROLE_PM"})
  @RequestMapping(value = "/create_workPeriod", method = RequestMethod.GET)
  public String createWorkPeriodGet() {
    logger.info("createWorkPeriodGet() method. Return create_workPeriod");
    return "user/create_workPeriod";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/create_workPeriod", method = RequestMethod.POST)
  public String createWorkPeriod(
      @RequestParam(value = "userId") Integer userId,
      @RequestParam(value = "workPeriodId") BigInteger workPeriodId,
      @RequestParam(value = "projectId") BigInteger projectId,
      @RequestParam(value = "startWorkDate") Date startWorkDate,
      @RequestParam(value = "endWorkDate") Date endWorkDate,
      @RequestParam(value = "workPeriodStatus") WorkPeriodStatus workPeriodStatus) {
    // HERE'S AN QUESTION!!!
    return "responseStatus/success";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/findWorkPeriodsByUserId/{id}", method = RequestMethod.GET)
  public String findWorkPeriodsByUserId(Model model,
      @PathVariable("id") BigInteger id) {
    return "user/findWorkPeriodsByUserId";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/findWorkPeriodsByProjectId/{projectId}", method = RequestMethod.GET)
  public String findWorkPeriodsByProjectId(Model model,
      @PathVariable("projectId") BigInteger projectId) {
    return "user/findWorkPeriodsByProjectId";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/findWorkPeriodByUserIdAndProjectId/userId={userId}&projectId={projectId}",
      method = RequestMethod.GET)
  public String findWorkPeriodByUserIdAndProjectId(Model model,
      @PathVariable("userId") BigInteger userId,
      @PathVariable("projectId") BigInteger projectId) {
    return "user/findWorkPeriodByUserIdAndProjectId";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/findWorkPeriodByProjectIdAndStatus/projectId={projectId}&status={status}",
      method = RequestMethod.GET)
  public String findWorkPeriodByProjectIdAndStatus(Model model,
      @PathVariable("projectId") BigInteger projectId,
      @PathVariable("status") Integer status) {
    return "user/findWorkPeriodByProjectIdAndStatus";
  }*/
}

