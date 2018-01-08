package com.netcracker.project.controllers;

import com.netcracker.project.controllers.validators.ProjectValidator;
import com.netcracker.project.controllers.validators.UserValidator;
import com.netcracker.project.controllers.validators.errorMessage.ErrorMessages;
import com.netcracker.project.model.ProjectDAO;
import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.entity.Project;
import com.netcracker.project.model.entity.Project.ProjectBuilder;
import com.netcracker.project.model.entity.Sprint;
import com.netcracker.project.model.entity.User;
import com.netcracker.project.model.entity.WorkPeriod;
import com.netcracker.project.model.entity.WorkPeriod.WorkPeriodBuilder;
import com.netcracker.project.model.entity.WorkPeriod.WorkPeriodStatus;
import com.netcracker.project.model.enums.JobTitle;
import com.netcracker.project.model.enums.OCStatus;
import com.netcracker.project.model.enums.ProjectStatus;
import com.netcracker.project.services.impl.DateConverterService;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
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
  @RequestMapping(value = "/createProject")
  public String createProject() {
    logger.info("Entering createProject()");
    return "project/createProject";
  }

  @Secured({"ROLE_ADMIN"})
  @RequestMapping(value = "/createProject", params = {"countSprints",
      "countWorkers"},
      method = RequestMethod.GET)
  public String createSprints(
      @RequestParam("countSprints") String sprintCount,
      @RequestParam("countWorkers") String workersCount,
      Model model) {
    logger.info("Entering createSprints()");
    ProjectValidator validator = new ProjectValidator();
    validator.validateSprintsAndWorkers(sprintCount, workersCount);
    Map<String, String> errorMap = validator
        .validateSprintsAndWorkers(sprintCount, workersCount);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "project/createProject";
    }

    Integer sprintCountInt = new Integer(sprintCount);
    Integer workersCountInt = new Integer(workersCount);

    Collection<User> pmOnTransit = userDAO
        .findUsersByJobTitleAndProjectStatus(JobTitle.PROJECT_MANAGER.getId(),
            ProjectStatus.TRANSIT.getId());

    model.addAttribute("pmOnTransitList", pmOnTransit);
    model.addAttribute("countSprints", sprintCountInt);
    model.addAttribute("countWorkers", workersCountInt);
    return "project/createProjectForm";
  }

  @Secured({"ROLE_ADMIN"})
  @RequestMapping(value = "/createProject/{countSprints}/{countWorkers}", method = RequestMethod.POST)
  public String createProjectPost(
      @PathVariable("countSprints") String countSprints,
      @PathVariable("countWorkers") String countWorkers,
      @RequestParam("projectName") String projectName,
      @RequestParam("projectStartDate") String projectStartDate,
      @RequestParam("projectEndDate") String projectEndDate,
      @RequestParam("pmId") String pmId,
      Model model,
      HttpServletRequest request) {
    logger.info("Entering createProjectPost()");
    Collection<User> pmOnTransit = userDAO
        .findUsersByJobTitleAndProjectStatus(JobTitle.PROJECT_MANAGER.getId(),
            ProjectStatus.TRANSIT.getId());

    ProjectValidator validator = new ProjectValidator();
    Map<String, String> errorMap = new HashMap<>();
    Integer existenceProject = projectDAO
        .findProjectByNameIfExist(projectName);

    errorMap = validator
        .validateCreateProject(existenceProject, countSprints, countWorkers,
            projectStartDate, projectEndDate, pmId);

    if (!errorMap.isEmpty()) {
      model.addAttribute("pmOnTransitList", pmOnTransit);
      model.addAttribute("countSprints", countSprints);
      model.addAttribute("countWorkers", countWorkers);
      model.addAttribute("errorMap", errorMap);
      return "project/createProjectForm";
    }

    Integer countSprintsInteger = new Integer(countSprints);
    Integer countWorkersInteger = new Integer(countWorkers);

    Collection<Sprint> sprints = new ArrayList<>();
    Date startDate = converter.convertStringToDateFromJSP(projectStartDate);
    Date endDate = converter.convertStringToDateFromJSP(projectEndDate);

    for (int index = 0; index < countSprintsInteger; index++) {
      String startDateParam = request.getParameter("startDate" + index);
      String endDateParam = request.getParameter("planedEndDate" + index);

      errorMap = validator
          .validateDates(startDateParam, endDateParam);

      if (!errorMap.isEmpty()) {
        model.addAttribute("countSprints", countSprints);
        model.addAttribute("pmOnTransitList", pmOnTransit);
        model.addAttribute("countWorkers", countWorkers);
        model.addAttribute("errorMap", errorMap);
        return "project/createProjectForm";
      }

      Date startDateSprint = converter
          .convertStringToDateFromJSP(startDateParam);
      Date endDateSprint = converter.convertStringToDateFromJSP(endDateParam);

      sprints.add(new Sprint.SprintBuilder()
          .name(request.getParameter("sprintName" + index))
          .startDate(startDateSprint)
          .plannedEndDate(endDateSprint)
          .build());
    }

    Collection<WorkPeriod> workPeriods = new ArrayList<>();

    for (int i = 0; i < countWorkersInteger; i++) {

      Collection<User> users = userDAO
          .findUserByLastNameAndFirstName(
              request.getParameter("userLastName" + i),
              request.getParameter("userFirstName" + i));

      User user = null;
      if (users.size() == 0) {
        errorMap
            .put("USER_ERROR", request.getParameter("userLastName" + i) + " " +
                request.getParameter("userFirstName" + i) + " "
                + ErrorMessages.USER_ERROR);
        model.addAttribute("countSprints", countSprints);
        model.addAttribute("countWorkers", countWorkers);
        model.addAttribute("errorMap", errorMap);
        model.addAttribute("pmOnTransitList", pmOnTransit);
        return "project/createProjectForm";
      }

      if (users.size() >= 1) {
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
          user = iterator.next();
          if (user.getProjectStatus().name()
              .equals(ProjectStatus.WORKING.name())) {
            errorMap.put("USER_PROJECT_STATUS_WORKING_ERROR",
                user.getLastName() + " " +
                    user.getFirstName() + " "
                    + ErrorMessages.USER_PROJECT_STATUS_WORKING_ERROR);
            model.addAttribute("pmOnTransitList", pmOnTransit);
            model.addAttribute("countWorkers", countWorkers);
            model.addAttribute("countSprints", countSprints);
            model.addAttribute("errorMap", errorMap);
            return "project/createProjectForm";
          }
        }
      }

      String startDateParam = request.getParameter("startDate" + i);
      String endDateParam = request.getParameter("endDate" + i);

      errorMap = validator
          .validateDates(startDateParam, endDateParam);
      if (!errorMap.isEmpty()) {
        model.addAttribute("pmOnTransitList", pmOnTransit);
        model.addAttribute("countWorkers", countWorkers);
        model.addAttribute("countSprints", countSprints);
        model.addAttribute("errorMap", errorMap);
        return "project/createProjectForm";
      }

      Date startDateWP = converter.convertStringToDateFromJSP(startDateParam);
      Date endDateWP = converter.convertStringToDateFromJSP(endDateParam);

      workPeriods.add(new WorkPeriod.WorkPeriodBuilder()
          .startWorkDate(startDateWP)
          .endWorkDate(endDateWP)
          .userId(user.getUserId())
          .workPeriodStatus(WorkPeriodStatus.WORKING)
          .build());
    }

    BigInteger validPMId = new BigInteger(pmId);
    Project project = new ProjectBuilder()
        .name(projectName)
        .startDate(startDate)
        .endDate(endDate)
        .projectManagerId(validPMId)
        .projectStatus(OCStatus.OPENED)
        .build();
    projectDAO.createProject(project, sprints, workPeriods);

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
    for (WorkPeriod period : workPeriods) {
      userDAO.createWorkPeriod(period, currentProject.getProjectId());
      userDAO.updateProjectStatus(period.getUserId(),
          ProjectStatus.WORKING.getId());
    }
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
    model.addAttribute("projectList", projectList);
    return "project/viewProject";
  }

  @Secured({"ROLE_PM", "ROLE_ADMIN"})
  @RequestMapping(value = "/showProject/{id}", method = RequestMethod.GET)
  public String showProject(
      @PathVariable("id") String id,
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
  @RequestMapping(value = "/deleteUserFromProject/project/{projectId}/user/{userId}", method = RequestMethod.POST)
  public String deleteUserFromProject(
      @PathVariable("projectId") String projectId,
      @PathVariable("userId") String userId,
      Model model) {
    logger.info("Entering deleteUserFromProject()");
    Map<String, String> errorMap = new HashMap<>();
    Map<String, String> existenceError = new HashMap<>();
    ProjectValidator validator = new ProjectValidator();
    errorMap = validator.validateDeleteUser(projectId, userId);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "project/deleteUserFromProject";
    }

    BigInteger validProjectId = new BigInteger(projectId);
    BigInteger validUserId = new BigInteger(userId);
    Integer projectExistence = projectDAO
        .findIfProjectExists(validProjectId);
    Integer userExistence = userDAO.findIfSEExists(validUserId);

    errorMap = validator
        .validateProjectAndUserExistence(projectExistence, userExistence);

    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", existenceError);
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

    projectDAO.deleteUserByUserId(validUserId, validProjectId);
    userDAO
        .updateProjectStatus(validUserId, ProjectStatus.TRANSIT.getId());
    WorkPeriod workPeriod = userDAO
        .findWorkingWorkPeriodByUserIdAndProjectId(validUserId,
            validProjectId);
    userDAO.updateWorkingPeriodStatusByUserId(workPeriod.getUserId(),
        workPeriod.getProjectId(), WorkPeriodStatus.FIRED.getId());
    Collection<User> userList = userDAO
        .findUserByProjectId(validProjectId);
    model.addAttribute("projectId", validProjectId);
    model.addAttribute("userList", userList);
    return "responseStatus/success";
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
        Integer lmExistence = userDAO.findIfLMExists(validProjectId);
        if (lmExistence > 0) {
          errorMap.put("LM_EXIST_ERROR", ErrorMessages.LM_EXIST_ERROR);
          model.addAttribute("errorMap", errorMap);
          return "project/addUser";
        }
      }

      if (user.getJobTitle().name().equals(JobTitle.PROJECT_MANAGER.name())) {
        Integer pmExistence = userDAO.findIfLMExists(validProjectId);
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
    }
    return "responseStatus/success";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/addUserFromDuplicate/{projectId}/{startDate}/{endDate}", method = RequestMethod.POST)
  public String addDuplicateUserToProject(
      @PathVariable("projectId") String projectId,
      @PathVariable("startDate") String startDate,
      @PathVariable("endDate") String endDate,
      @RequestParam("user") String userId,
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
      Integer lmExistence = userDAO.findIfLMExists(validProjectId);
      if (lmExistence > 0) {
        errorMap.put("LM_EXIST_ERROR", ErrorMessages.LM_EXIST_ERROR);
        model.addAttribute("errorMap", errorMap);
        return "project/addUser";
      }
    }

    if (user.getJobTitle().equals(JobTitle.PROJECT_MANAGER)) {
      Integer pmExistence = userDAO.findIfLMExists(validProjectId);
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
//    projectDAO.deleteUserByUserId(projectManagerId, validProjectId);
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
