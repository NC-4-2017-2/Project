package com.netcracker.project.controllers;

import com.netcracker.project.controllers.validators.ProjectValidator;
import com.netcracker.project.controllers.validators.UserValidator;
import com.netcracker.project.controllers.validators.errorMessage.ErrorMessages;
import com.netcracker.project.model.ProjectDAO;
import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.entity.Project;
import com.netcracker.project.model.entity.Project.ProjectBuilder;
import com.netcracker.project.model.entity.Sprint;
import com.netcracker.project.model.entity.Sprint.SprintBuilder;
import com.netcracker.project.model.entity.User;
import com.netcracker.project.model.entity.WorkPeriod;
import com.netcracker.project.model.entity.WorkPeriod.WorkPeriodBuilder;
import com.netcracker.project.model.entity.WorkPeriod.WorkPeriodStatus;
import com.netcracker.project.model.enums.JobTitle;
import com.netcracker.project.model.enums.OCStatus;
import com.netcracker.project.model.enums.ProjectStatus;
import com.netcracker.project.services.impl.DateConverterService;
import java.math.BigInteger;
import java.security.Principal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
@RequestMapping("/project")
public class ProjectController {

  @Autowired
  private ProjectDAO projectDAO;
  @Autowired
  private UserDAO userDAO;
  @Autowired
  private DateConverterService converter;

  private static final Logger logger = Logger
      .getLogger(ProjectController.class);

  @Secured({"ROLE_ADMIN"})
  @RequestMapping(value = "/createProject", method = RequestMethod.GET)
  public String createSprints(
      Model model) {
    logger.info("Entering createSprints()");
    ProjectValidator validator = new ProjectValidator();
    Map<String, String> errorMap = new HashMap<>();
    Collection<User> pmOnTransit = userDAO
        .findUsersByJobTitleAndProjectStatus(JobTitle.PROJECT_MANAGER.getId(),
            ProjectStatus.TRANSIT.getId());
    errorMap = validator.validatePMTransitList(pmOnTransit);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "responseStatus/unsuccess";
    }
    model.addAttribute("pmOnTransitList", pmOnTransit);
    return "project/createProject";
  }

  @Secured({"ROLE_ADMIN"})
  @RequestMapping(value = "/createProject", method = RequestMethod.POST)
  public String createProjectPost(
      @RequestParam("projectName") String projectName,
      @RequestParam("projectStartDate") String projectStartDate,
      @RequestParam("projectEndDate") String projectEndDate,
      @RequestParam("pmId") String pmId,
      Model model) {
    logger.info("Entering createProjectPost()");
    Collection<User> pmOnTransit = userDAO
        .findUsersByJobTitleAndProjectStatus(JobTitle.PROJECT_MANAGER.getId(),
            ProjectStatus.TRANSIT.getId());

    ProjectValidator validator = new ProjectValidator();
    Map<String, String> errorMap = new HashMap<>();
    Integer existenceProject = projectDAO
        .findProjectByNameIfExist(projectName);

    errorMap = validator
        .validateCreateProject(existenceProject,
            projectStartDate, projectEndDate, pmId);
    if (!errorMap.isEmpty()) {
      model.addAttribute("pmOnTransitList", pmOnTransit);
      model.addAttribute("errorMap", errorMap);
      return "project/createProject";
    }
    Date startDate = converter.convertStringToDateFromJSP(projectStartDate);
    Date endDate = converter.convertStringToDateFromJSP(projectEndDate);

    BigInteger validPMId = new BigInteger(pmId);
    Project project = new ProjectBuilder()
        .name(projectName)
        .startDate(startDate)
        .endDate(endDate)
        .projectManagerId(validPMId)
        .projectStatus(OCStatus.OPENED)
        .build();
    projectDAO.createProject(project);

    Project currentProject = projectDAO.findProjectByName(projectName);
    WorkPeriod pmWorkPeriod = new WorkPeriodBuilder()
        .startWorkDate(startDate)
        .endWorkDate(endDate)
        .userId(validPMId)
        .workPeriodStatus(WorkPeriodStatus.WORKING)
        .build();
    userDAO.createWorkPeriod(pmWorkPeriod, currentProject.getProjectId());
    userDAO.updateProjectStatus(pmWorkPeriod.getUserId(),
        ProjectStatus.WORKING.getId());

    userDAO.updateProjectStatus(validPMId, ProjectStatus.WORKING.getId());
    return "responseStatus/success";
  }

  @Secured({"ROLE_PM", "ROLE_ADMIN"})
  @RequestMapping(value = "/findProjectByStartDate", method = RequestMethod.GET)
  public String findProjectByStartDate() {
    logger.info("Entering findProjectByStartDate()");
    return "project/findProjectByStartDate";
  }

  @Secured({"ROLE_PM", "ROLE_ADMIN"})
  @RequestMapping(value = "/findProjectByStartDate", params = {"startDate",
      "endDate"}, method = RequestMethod.GET)
  public String findProjectPerPeriodDate(
      @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate,
      Model model) {
    logger.info("Entering findProjectPerPeriodDate()");
    Map<String, String> errorMap = new HashMap<>();
    ProjectValidator validator = new ProjectValidator();

    errorMap = validator.validateDates(startDate, endDate);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "project/findProjectByStartDate";
    }
    Collection<Project> projectList = projectDAO
        .findProjectByStartDate(converter.convertStringToDateFromJSP(startDate),
            converter.convertStringToDateFromJSP(endDate));
    if (projectList.isEmpty()) {
      return "responseStatus/noDataFound";
    }
    model.addAttribute("projectList", projectList);
    return "project/viewProject";
  }

  @Secured({"ROLE_PM", "ROLE_ADMIN"})
  @RequestMapping(value = "/showProject/{id}", method = RequestMethod.GET)
  public String showProject(
      @PathVariable("id") String id,
      Principal principal,
      Model model) {
    logger.info("Entering showProject()");
    Map<String, String> errorMap = new HashMap<>();
    ProjectValidator validator = new ProjectValidator();
    errorMap = validator.validateInputId(id);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "project/showProject";
    }
    BigInteger bigIntegerProjectId = new BigInteger(id);
    Integer projectExistence = projectDAO
        .findIfProjectExists(bigIntegerProjectId);
    Map<String, String> existenceError = validator
        .validateExistence(projectExistence);
    if (!existenceError.isEmpty()) {
      model.addAttribute("errorMap", existenceError);
      return "project/showProject";
    }

    Project project = projectDAO.findProjectByProjectId(bigIntegerProjectId);

    User currentUser = userDAO.findUserByLogin(principal.getName());
    User projectManager = userDAO
        .findUserByUserId(project.getProjectManagerId());

    model.addAttribute("currentUser", currentUser);
    model.addAttribute("projectManager", projectManager);
    model.addAttribute("project", project);
    return "project/showProject";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/showProjectUsersToDelete/{id}", method = RequestMethod.GET)
  public String getProjectUsersToDelete(
      @PathVariable("id") String id,
      Model model) {
    logger.info("Entering getProjectUsersToDelete()");
    Map<String, String> errorMap = new HashMap<>();
    ProjectValidator validator = new ProjectValidator();
    errorMap = validator.validateInputId(id);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "project/deleteUserFromProject";
    }
    BigInteger bigIntegerProjectId = new BigInteger(id);
    Collection<User> userList = userDAO
        .findUserByProjectId(bigIntegerProjectId);
    model.addAttribute("projectId", bigIntegerProjectId);
    model.addAttribute("userList", userList);
    return "project/deleteUserFromProject";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/deleteUserFromProject/project/{projectId}/user/{userId}/jobTitle/{jobTitle}", method = RequestMethod.POST)
  public String deleteUserFromProject(
      @PathVariable("projectId") String projectId,
      @PathVariable("userId") String userId,
      @PathVariable("jobTitle") String jobTitle,
      Model model) {
    logger.info("Entering deleteUserFromProject()");
    Map<String, String> errorMap = new HashMap<>();
    Map<String, String> existenceError = new HashMap<>();
    ProjectValidator validator = new ProjectValidator();
    errorMap = validator.validateDeleteUser(projectId, userId, jobTitle);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "project/deleteUserFromProject";
    }

    BigInteger validProjectId = new BigInteger(projectId);
    BigInteger validUserId = new BigInteger(userId);
    Integer projectExistence = projectDAO
        .findIfProjectExists(validProjectId);
    Integer userExistence = null;
    if (jobTitle.equals(JobTitle.SOFTWARE_ENGINEER.name())) {
      userExistence = userDAO.findIfSEExists(validUserId);
    }
    if (jobTitle.equals(JobTitle.LINE_MANAGER.name())) {
      userExistence = userDAO.findIfLMExists(validUserId);
    }
    if (userExistence == null) {
      return "responseStatus/unsuccess";
    }

    errorMap = validator
        .validateProjectAndUserExistence(projectExistence, userExistence);

    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "project/deleteUserFromProject";
    }

    UserValidator userValidator = new UserValidator();
    Integer workPeriodExistence = userDAO
        .findWorkingWorkPeriodIfExist(validUserId, validProjectId);
    existenceError = userValidator
        .validateWorkPeriodExistence(workPeriodExistence);
    if (!existenceError.isEmpty()) {
      model.addAttribute("errorMap", existenceError);
      return "project/deleteUserFromProject";
    }

    projectDAO.deleteUserByUserId(validProjectId, validUserId);
    userDAO
        .updateProjectStatus(validUserId, ProjectStatus.TRANSIT.getId());
    Date currentDate = new Date();
    userDAO.updateWorkingPeriodEndDateByUserId(validUserId, validProjectId,
        currentDate);
    userDAO.updateWorkingPeriodStatusByUserId(validUserId,
        validProjectId, WorkPeriodStatus.FIRED.getId());
    Collection<User> userList = userDAO
        .findUserByProjectId(validProjectId);
    model.addAttribute("projectId", validProjectId);
    model.addAttribute("userList", userList);
    return "project/deleteUserFromProject";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/userToAdd/{projectId}", method = RequestMethod.GET)
  public String getProjectUsersToAdd(
      @PathVariable("projectId") String projectId,
      Model model) {
    logger.info("Entering getProjectUsersToAdd()");
    ProjectValidator validator = new ProjectValidator();
    Map<String, String> errorMap = validator.validateInputId(projectId);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "project/showProject";
    }
    BigInteger validProjectId = new BigInteger(projectId);
    model.addAttribute("projectId", validProjectId);
    return "project/addUser";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/addUser/{projectId}", method = RequestMethod.POST)
  public String addUserToProject(
      @PathVariable("projectId") String projectId,
      @RequestParam("lastName") String lastName,
      @RequestParam("firstName") String firstName,
      @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate,
      Model model) {
    logger.info("Entering addUserToProject()");
    ProjectValidator validator = new ProjectValidator();
    Map<String, String> errorMap = new HashMap<>();

    errorMap = validator
        .validateAddingUser(projectId, firstName, lastName, startDate, endDate);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "project/addUser";
    }

    Collection<User> users = userDAO
        .findUserByLastNameAndFirstName(lastName, firstName);
    if (users.isEmpty()) {
      errorMap.put("USER_ERROR", ErrorMessages.USER_ERROR);
      model.addAttribute("errorMap", errorMap);
      return "project/addUser";
    }
    if (users.size() > 1) {
      model.addAttribute("usersList", users);
      model.addAttribute("projectId", projectId);
      model.addAttribute("startDate", startDate);
      model.addAttribute("endDate", endDate);

      return "project/chooseUserToAdd";
    }

    User user = null;
    Iterator<User> iterator = users.iterator();
    while (iterator.hasNext()) {
      user = iterator.next();
    }

    BigInteger validProjectId = new BigInteger(projectId);

    if (user != null) {
      if (user.getProjectStatus().name().equals(ProjectStatus.WORKING.name())) {
        errorMap.put("USER_PROJECT_STATUS_WORKING_ERROR",
            user.getLastName() + " " + user.getFirstName() + " "
                + ErrorMessages.USER_PROJECT_STATUS_WORKING_ERROR);
        model.addAttribute("errorMap", errorMap);
        return "project/addUser";
      }

      if (user.getJobTitle().name().equals(JobTitle.LINE_MANAGER.name())) {
        Integer lmExistence = userDAO.findIfLMExistsOnProject(validProjectId);
        if (lmExistence > 0) {
          errorMap.put("LM_EXIST_ERROR", ErrorMessages.LM_EXIST_ERROR);
          model.addAttribute("errorMap", errorMap);
          return "project/addUser";
        }
      }

      if (user.getJobTitle().name().equals(JobTitle.PROJECT_MANAGER.name())) {
        Integer pmExistence = userDAO.findIfLMExistsOnProject(validProjectId);
        if (pmExistence > 0) {
          errorMap.put("PM_EXIST_ERROR", ErrorMessages.PM_EXIST_ERROR);
          model.addAttribute("errorMap", errorMap);
          return "project/addUser";
        }
      }

      projectDAO.addUser(validProjectId, user.getUserId());
      userDAO
          .updateProjectStatus(user.getUserId(), ProjectStatus.WORKING.getId());

      Project project = projectDAO.findProjectByProjectId(validProjectId);

      Date validaStartDate = converter.convertStringToDateFromJSP(startDate);
      Date validaEndDate = converter.convertStringToDateFromJSP(endDate);

      errorMap = validator.validateProjectAndWorkPeriodDates(project,
          validaStartDate, validaEndDate);
      if (!errorMap.isEmpty()) {
        model.addAttribute("errorMap", errorMap);
        return "project/addUser";
      }

      WorkPeriod workPeriod = new WorkPeriodBuilder()
          .userId(user.getUserId())
          .projectId(validProjectId)
          .startWorkDate(validaStartDate)
          .endWorkDate(validaEndDate)
          .workPeriodStatus(WorkPeriodStatus.WORKING)
          .build();

      userDAO.createWorkPeriod(workPeriod, validProjectId);
    }
    return "responseStatus/success";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/addUserFromDuplicate/{projectId}/{startDate}/{endDate}/{userId}", method = RequestMethod.POST)
  public String addDuplicateUserToProject(
      @PathVariable("projectId") String projectId,
      @PathVariable("startDate") String startDate,
      @PathVariable("endDate") String endDate,
      @PathVariable("userId") String userId,
      Model model) {
    ProjectValidator validator = new ProjectValidator();
    Map<String, String> errorMap = new HashMap<>();

    errorMap = validator
        .validateAddingUserFromDuplicate(projectId, startDate, endDate, userId);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "project/addUser";
    }

    BigInteger validUserId = new BigInteger(userId);
    BigInteger validProjectId = new BigInteger(projectId);
    User user = userDAO.findUserByUserId(validUserId);

    if (user.getProjectStatus().name().equals(ProjectStatus.WORKING.name())) {
      errorMap.put("USER_PROJECT_STATUS_WORKING_ERROR",
          user.getLastName() + " " + user.getFirstName() + " "
              + ErrorMessages.USER_PROJECT_STATUS_WORKING_ERROR);
      model.addAttribute("errorMap", errorMap);
      return "project/addUser";
    }

    if (user.getJobTitle().equals(JobTitle.LINE_MANAGER)) {
      Integer lmExistence = userDAO.findIfLMExistsOnProject(validProjectId);
      if (lmExistence > 0) {
        errorMap.put("LM_EXIST_ERROR", ErrorMessages.LM_EXIST_ERROR);
        model.addAttribute("errorMap", errorMap);
        return "project/addUser";
      }
    }

    if (user.getJobTitle().equals(JobTitle.PROJECT_MANAGER)) {
      Integer pmExistence = userDAO.findIfLMExistsOnProject(validProjectId);
      if (pmExistence > 0) {
        errorMap.put("PM_EXIST_ERROR", ErrorMessages.PM_EXIST_ERROR);
        model.addAttribute("errorMap", errorMap);
        return "project/addUser";
      }
    }

    projectDAO.addUser(validProjectId, user.getUserId());
    userDAO
        .updateProjectStatus(user.getUserId(), ProjectStatus.WORKING.getId());

    WorkPeriod workPeriod = new WorkPeriodBuilder()
        .userId(user.getUserId())
        .projectId(validProjectId)
        .startWorkDate(converter.convertStringToDateFromJSP(startDate))
        .endWorkDate(converter.convertStringToDateFromJSP(endDate))
        .workPeriodStatus(WorkPeriodStatus.WORKING)
        .build();

    userDAO.createWorkPeriod(workPeriod, validProjectId);
    return "responseStatus/success";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/closeProject/{projectId}", method = RequestMethod.POST)
  public String closeProject(@PathVariable("projectId") String projectId,
      Model model) {
    logger.info("Entering closeProject()");
    ProjectValidator validator = new ProjectValidator();

    Map<String, String> errorMap = validator.validateInputId(projectId);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "project/showProject";
    }
    BigInteger validProjectId = new BigInteger(projectId);
    Collection<User> users = userDAO
        .findUserByProjectId(validProjectId);

    for (User user : users) {
      Integer workPeriodIfExist = userDAO
          .findWorkingWorkPeriodIfExist(user.getUserId(), validProjectId);
      Map<String, String> existenceError = validator
          .validateExistence(workPeriodIfExist);
      if (!existenceError.isEmpty()) {
        model.addAttribute("errorMap", existenceError);
        return "project/showProject";
      }
      projectDAO.deleteUserByUserId(user.getUserId(), validProjectId);
      userDAO.updateProjectStatus(user.getUserId(),
          ProjectStatus.TRANSIT.getId());
      WorkPeriod workPeriod = userDAO
          .findWorkingWorkPeriodByUserIdAndProjectId(user.getUserId(),
              validProjectId);
      userDAO.updateWorkingPeriodStatusByUserId(user.getUserId(),
          workPeriod.getProjectId(), WorkPeriodStatus.FIRED.getId());
    }
    Project project = projectDAO
        .findProjectByProjectId(validProjectId);
    BigInteger projectManagerId = project.getProjectManagerId();
    userDAO
        .updateProjectStatus(projectManagerId, ProjectStatus.TRANSIT.getId());
    WorkPeriod pMWorkPeriod = userDAO
        .findWorkingWorkPeriodByUserIdAndProjectId(projectManagerId,
            validProjectId);
    userDAO.updateWorkingPeriodStatusByUserId(projectManagerId,
        pMWorkPeriod.getProjectId(),
        WorkPeriodStatus.FIRED.getId());
    projectDAO.updateStatus(validProjectId, OCStatus.CLOSED);
    Date date = new Date();
    projectDAO.updateEndDate(validProjectId, date);
    return "responseStatus/success";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/createSprint/{projectId}", method = RequestMethod.GET)
  public String createSprintGet(@PathVariable("projectId") String projectId,
      Principal principal,
      Model model) {
    ProjectValidator validator = new ProjectValidator();
    Map<String, String> errorMap = validator.validateInputId(projectId);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "project/createSprint";
    }
    BigInteger validProjectId = new BigInteger(projectId);

    Project project = projectDAO.findProjectByProjectId(validProjectId);
    User currentUser = userDAO.findUserByLogin(principal.getName());

    if (!currentUser.getUserId().equals(project.getProjectManagerId())
        && project.getProjectStatus().name().equals(OCStatus.CLOSED.name())) {
      errorMap.put("PROJECT_ACCESS_ERROR", ErrorMessages.PROJECT_ACCESS_ERROR);
      model.addAttribute("errorMap", errorMap);
      return "project/createSprint";
    }

    model.addAttribute("projectId", validProjectId);
    return "project/createSprint";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/createSprint/{projectId}", method = RequestMethod.POST)
  public String createSprintPost(@PathVariable("projectId") String projectId,
      @RequestParam("sprintName") String sprintName,
      @RequestParam("startDate") String startDate,
      @RequestParam("plannedEndDate") String plannedEndDate,
      Model model) {
    ProjectValidator validator = new ProjectValidator();
    Map<String, String> errorMap = validator.validateInputId(projectId);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "project/createSprint";
    }

    BigInteger validProjectId = new BigInteger(projectId);
    Project project = projectDAO.findProjectByProjectId(validProjectId);
    Integer projectExistence = projectDAO.findIfProjectExists(validProjectId);

    errorMap = validator.validateExistence(projectExistence);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "project/createSprint";
    }

    errorMap = validator.validateDates(startDate, plannedEndDate);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "project/createSprint";
    }

    Sprint sprint = new SprintBuilder()
        .name(sprintName)
        .startDate(converter.convertStringToDateFromJSP(startDate))
        .endDate(converter.convertStringToDateFromJSP(plannedEndDate))
        .plannedEndDate(converter.convertStringToDateFromJSP(plannedEndDate))
        .build();

    Collection<Sprint> sprints = projectDAO.getAllSprints(validProjectId);

    errorMap = validator.validateProjectSprintDates(project, sprints, sprint);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "project/createSprint";
    }

    errorMap = validator.validateSprintName(sprints, sprintName);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "project/createSprint";
    }

    projectDAO.createSprint(sprint, validProjectId);
    return "responseStatus/success";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/viewSprints/{projectId}", method = RequestMethod.GET)
  public String viewSprints(@PathVariable("projectId") String projectId,
      Model model) {
    logger.info("Entering viewSprints()");
    ProjectValidator validator = new ProjectValidator();
    Map<String, String> errorMap = validator.validateInputId(projectId);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "project/viewSprints";
    }

    BigInteger validProjectId = new BigInteger(projectId);
    Collection<Sprint> sprints = projectDAO.getAllSprints(validProjectId);
    if (sprints.isEmpty()) {
      errorMap.put("SPRINTS_EXIST_ERROR", ErrorMessages.SPRINTS_EXIST_ERROR);
      model.addAttribute("errorMap", errorMap);
      return "project/viewSprints";
    }
    model.addAttribute("sprintList", sprints);
    return "project/viewSprints";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/showSprint/{sprintId}", method = RequestMethod.GET)
  public String showSprints(@PathVariable("sprintId") String sprintId,
      Model model) {
    logger.info("Entering showSprints()");
    ProjectValidator validator = new ProjectValidator();
    Map<String, String> errorMap = validator.validateInputId(sprintId);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "project/viewSprints";
    }

    BigInteger validSprintId = new BigInteger(sprintId);
    Sprint sprint = projectDAO.findSprintBySprintId(validSprintId);
    model.addAttribute("sprint", sprint);
    return "project/showSprint";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/closeSprint/{sprintId}", method = RequestMethod.POST)
  public String closeSprint(@PathVariable("sprintId") String sprintId,
      Model model) {
    logger.info("Entering closeSprint()");
    ProjectValidator validator = new ProjectValidator();
    Map<String, String> errorMap = validator.validateInputId(sprintId);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "project/showSprint";
    }
    BigInteger validSprintId = new BigInteger(sprintId);

    Date date = new Date();
    projectDAO.updateSprintEndDate(validSprintId, date);
    projectDAO.updateSprintStatus(validSprintId, OCStatus.CLOSED);

    Sprint sprint = projectDAO.findSprintBySprintId(validSprintId);
    model.addAttribute("sprint", sprint);
    return "project/showSprint";
  }
}
