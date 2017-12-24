package com.netcracker.project.controllers;

import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.entity.User;
import com.netcracker.project.model.entity.WorkPeriod.WorkPeriodStatus;
import com.netcracker.project.model.enums.JobTitle;
import com.netcracker.project.model.enums.ProjectStatus;
import com.netcracker.project.model.enums.UserRole;
import com.netcracker.project.model.enums.UserStatus;
import java.io.File;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/user")
public class UserController {

  private static final Logger logger = Logger
      .getLogger(UserController.class);
  private ApplicationContext context =
      new ClassPathXmlApplicationContext("Spring-Module.xml");
  @Autowired
  private UserDAO userDAO;
  /*@Autowired
  private WorkPeriod workPeriod;*/

  @RequestMapping(value = "/create_user", method = RequestMethod.GET)
  public String createUserGet() {
    logger.info("createUserGet() method. Return create_user");
    return "user/create_user";
  }

  @RequestMapping(value = "/create_user", method = RequestMethod.POST)
  public String createUserPost(
      @RequestParam(value = "userId") Integer userId,
      @RequestParam(value = "firstName") String firstName,
      @RequestParam(value = "lastName") String lastName,
      @RequestParam(value = "email") String email,
      @RequestParam(value = "dateOfBirth") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOfBirth,
      @RequestParam(value = "hireDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date hireDate,
      @RequestParam(value = "phoneNumber") String phoneNumber,
      @RequestParam(value = "photo") String photo,
      @RequestParam(value = "jobTitle") JobTitle jobTitle,
      @RequestParam(value = "login") String login,
      @RequestParam(value = "password") String password,
      @RequestParam(value = "userRole") UserRole userRole,
      @RequestParam(value = "userStatus") UserStatus userStatus,
      @RequestParam(value = "projectStatus") ProjectStatus projectStatus) {

    logger.info("POST request createUser(). UserId: " + userId);

    User user = new User.UserBuilder()
        .userId(BigInteger.valueOf(userId))
        .firstName(firstName)
        .lastName(lastName)
        .email(email)
        .dateOfBirth(dateOfBirth)
        .hireDate(hireDate)
        .phoneNumber(phoneNumber)
        .photo(photo)
        .jobTitle(jobTitle)
        .login(login)
        .password(password)
        .role(userRole)
        .userStatus(userStatus)
        .projectStatus(projectStatus)
        .build();

    /*!!!Uncomment when the sequences are added!!!
    //logger.info("createUser request from DB. User id: " + userId);
    //userDAO.createUser(user);*/
    return "response_status/success";
  }

  @RequestMapping(value = "/edit_user", method = RequestMethod.POST)
  public String editUserPost(
      @RequestParam(value = "userId") BigInteger userId,
      @RequestParam(value = "firstName") String firstName,
      @RequestParam(value = "lastName") String lastName,
      @RequestParam(value = "email") String email,
      @RequestParam(value = "phoneNumber") String phoneNumber,
      @RequestParam(value = "photo") String photo,
      @RequestParam(value = "login") String login,
      @RequestParam(value = "password") String password,
      @RequestParam(value = "projectStatus") Integer projectStatus) {

    logger.info("editUserPost() method. User id" + userId
        + "email: " + email
        + "phoneNumber: " + phoneNumber
        + "password: " + password
        + "photo: " + photo
        + "projectStatus: " + projectStatus);

    userDAO.updateEmail(userId, email);
    userDAO.updatePhoneNumber(userId, phoneNumber);
    userDAO.updatePassword(userId, password);
    userDAO.updatePhoto(userId, photo);
    userDAO.updateProjectStatus(userId, projectStatus);

    return "response_status/success";
  }

  @RequestMapping(value = "/edit_user/{userId}", method = RequestMethod.GET)
  public String editUserGet(@PathVariable("userId") Integer userId,
      Model model) {
    logger.info("editUserGet() method. userId: " + userId);
    User user = userDAO.findUserByUserId(BigInteger.valueOf(userId));
    model.addAttribute("userId", user.getUserId());
    model.addAttribute("firstName", user.getFirstName());
    model.addAttribute("lastName", user.getLastName());
    model.addAttribute("email", user.getEmail());
    model.addAttribute("phoneNumber", user.getPhoneNumber());
    model.addAttribute("photo", user.getPhoto());
    model.addAttribute("login", user.getLogin());
    model.addAttribute("password", user.getPassword());
    model.addAttribute("projectStatus", user.getProjectStatus());

    return "project/edit_user";
  }

  @RequestMapping(value = "/findUserByLogin/{login}", method = RequestMethod.GET)
  public String findUserByLogin(Model model,
      @PathVariable("login") String login) {
    logger.info("findUserByLogin() method. Login: " + login);

    User user = userDAO.findUserByLogin(login);

    model.addAttribute("userId", user.getUserId());
    model.addAttribute("firstName", user.getFirstName());
    model.addAttribute("lastName", user.getLastName());
    model.addAttribute("email", user.getEmail());
    model.addAttribute("dateOfBirth", user.getDateOfBirth());
    model.addAttribute("hireDate", user.getHireDate());
    model.addAttribute("phoneNumber", user.getPhoneNumber());
    model.addAttribute("photo", user.getPhoto());
    model.addAttribute("jobTitle", user.getJobTitle());
    model.addAttribute("login", user.getLogin());
    model.addAttribute("userRole", user.getUserRole());
    model.addAttribute("userStatus", user.getUserStatus());
    model.addAttribute("projectStatus", user.getProjectStatus());

    return "user/show_user";
  }

  @RequestMapping(value = "/findUserByUserId/{userId}", method = RequestMethod.GET)
  public String findUserByUserId(Model model,
      @PathVariable("userId") Integer userId) {
    logger.info("findUserById() method. userId: " + userId);

    User user = userDAO.findUserByUserId(BigInteger.valueOf(userId));

    model.addAttribute("userId", user.getUserId());
    model.addAttribute("firstName", user.getFirstName());
    model.addAttribute("lastName", user.getLastName());
    model.addAttribute("email", user.getEmail());
    model.addAttribute("dateOfBirth", user.getDateOfBirth());
    model.addAttribute("hireDate", user.getHireDate());
    model.addAttribute("phoneNumber", user.getPhoneNumber());
    model.addAttribute("photo", user.getPhoto());
    model.addAttribute("jobTitle", user.getJobTitle());
    model.addAttribute("login", user.getLogin());
    model.addAttribute("userRole", user.getUserRole());
    model.addAttribute("userStatus", user.getUserStatus());
    model.addAttribute("projectStatus", user.getProjectStatus());

    return "user/show_user";
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

  //in future
  @RequestMapping(value = "/create_workPeriod", method = RequestMethod.GET)
  public String createWorkPeriodGet() {
    logger.info("createWorkPeriodGet() method. Return create_workPeriod");
    return "user/create_workPeriod";
  }

  @RequestMapping(value = "/create_workPeriod", method = RequestMethod.POST)
  public String createWorkPeriod(
      @RequestParam(value = "userId") Integer userId,
      @RequestParam(value = "workPeriodId") BigInteger workPeriodId,
      @RequestParam(value = "projectId") BigInteger projectId,
      @RequestParam(value = "startWorkDate") Date startWorkDate,
      @RequestParam(value = "endWorkDate") Date endWorkDate,
      @RequestParam(value = "workPeriodStatus") WorkPeriodStatus workPeriodStatus) {
    // HERE'S AN QUESTION!!!
    return "response_status/success";
  }

  @RequestMapping(value = "/findWorkPeriodsByUserId/{id}", method = RequestMethod.GET)
  public String findWorkPeriodsByUserId(Model model,
      @PathVariable("id") BigInteger id) {
    return "user/findWorkPeriodsByUserId";
  }

  @RequestMapping(value = "/findWorkPeriodsByProjectId/{projectId}", method = RequestMethod.GET)
  public String findWorkPeriodsByProjectId(Model model,
      @PathVariable("projectId") BigInteger projectId) {
    return "user/findWorkPeriodsByProjectId";
  }

  @RequestMapping(value = "/findWorkPeriodByUserIdAndProjectId/userId={userId}&projectId={projectId}",
      method = RequestMethod.GET)
  public String findWorkPeriodByUserIdAndProjectId(Model model,
      @PathVariable("userId") BigInteger userId,
      @PathVariable("projectId") BigInteger projectId) {
    return "user/findWorkPeriodByUserIdAndProjectId";
  }

  @RequestMapping(value = "/findWorkPeriodByProjectIdAndStatus/projectId={projectId}&status={status}",
      method = RequestMethod.GET)
  public String findWorkPeriodByProjectIdAndStatus(Model model,
      @PathVariable("projectId") BigInteger projectId,
      @PathVariable("status") Integer status) {
    return "user/findWorkPeriodByProjectIdAndStatus";
  }
}

