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
import com.netcracker.project.model.entity.WorkPeriod.WorkPeriodStatus;
import com.netcracker.project.model.enums.JobTitle;
import com.netcracker.project.model.enums.OCStatus;
import com.netcracker.project.model.enums.ProjectStatus;
import com.netcracker.project.services.impl.DateConverterService;
import com.netcracker.project.services.ConvertJspDataService;
import com.netcracker.project.services.impl.ConvertJspDataServiceImpl;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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

  private ConvertJspDataService convertService = new ConvertJspDataServiceImpl();
  private DateConverterService converter = new DateConverterService();

  @RequestMapping(value = "/createProject")
  public String createProject() {
    return "project/createProject";
  }

  @RequestMapping(value = "/createProject", params = {"countSprints",
      "countWorkers"},
      method = RequestMethod.GET)
  public String createSprints(
      @RequestParam("countSprints") String sprintCount,
      @RequestParam("countWorkers") String workersCount,
      Model model) {
    ProjectValidator validator = new ProjectValidator();

    Map<String, String> errorMap = validator
        .validateInteger(sprintCount);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "project/createProject";
    }

    errorMap = validator.validateInteger(workersCount);
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
    Collection<User> pmOnTransit = userDAO
        .findUsersByJobTitleAndProjectStatus(JobTitle.PROJECT_MANAGER.getId(),
            ProjectStatus.TRANSIT.getId());

    ProjectValidator validator = new ProjectValidator();
    Map<String, String> errorMap = new HashMap<>();

    Integer existenceProject = projectDAO
        .findProjectByNameIfExist(projectName);
    if (existenceProject >= 1) {
      model.addAttribute("countSprints", countSprints);
      model.addAttribute("countWorkers", countWorkers);
      errorMap.put("PROJECT_EXIST_ERROR",
          projectName + " " + ErrorMessages.PROJECT_EXIST_ERROR);
      model.addAttribute("pmOnTransitList", pmOnTransit);
      model.addAttribute("errorMap", errorMap);

      return "project/createProjectForm";
    }

    errorMap = validator.validateInteger(countSprints);
    if (!errorMap.isEmpty()) {
      model.addAttribute("pmOnTransitList", pmOnTransit);
      model.addAttribute("countSprints", countSprints);
      model.addAttribute("countWorkers", countWorkers);
      model.addAttribute("errorMap", errorMap);
      return "project/createProjectForm";
    }

    Integer countSprintsInteger = new Integer(countSprints);

    errorMap = validator.validateInteger(countWorkers);
    if (!errorMap.isEmpty()) {
      model.addAttribute("countSprints", countSprints);
      model.addAttribute("countWorkers", countWorkers);
      model.addAttribute("pmOnTransitList", pmOnTransit);
      model.addAttribute("errorMap", errorMap);
      return "project/createProjectForm";
    }

    Integer countWorkersInteger = new Integer(countWorkers);

    errorMap = validator
        .validateDates(projectStartDate, projectEndDate);
    Collection<Sprint> sprints = new ArrayList<>();

    if (!errorMap.isEmpty()) {
      model.addAttribute("pmOnTransitList", pmOnTransit);
      model.addAttribute("errorMap", errorMap);
      model.addAttribute("countSprints", countSprints);
      model.addAttribute("countWorkers", countWorkers);
      return "project/createProjectForm";
    }

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
          if(user.getProjectStatus().name().equals(ProjectStatus.WORKING.name())) {
            errorMap.put("USER_PROJECT_STATUS_WORKING_ERROR", user.getLastName() + " " +
                user.getFirstName() + " " + ErrorMessages.USER_PROJECT_STATUS_WORKING_ERROR);
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

    BigInteger pmIdBigInteger = new BigInteger(pmId);

    Project project = new ProjectBuilder()
        .name(projectName)
        .startDate(startDate)
        .endDate(endDate)
        .projectManagerId(pmIdBigInteger)
        .projectStatus(OCStatus.OPENED)
        .build();

    projectDAO.createProject(project, sprints, workPeriods);
    userDAO.updateProjectStatus(pmIdBigInteger, ProjectStatus.WORKING.getId());
    Project currentProject = projectDAO.findProjectByName(projectName);
    for (WorkPeriod period : workPeriods) {
      userDAO.createWorkPeriod(period, currentProject.getProjectId());
      userDAO.updateProjectStatus(period.getUserId(), ProjectStatus.WORKING.getId());
    }

    return "response_status/success";
  }

  @RequestMapping(value = "/findProjectByStartDate", method = RequestMethod.GET)
  public String findProjectByStartDate() {
    return "project/findProjectByStartDate";
  }

  @RequestMapping(value = "/findProjectByStartDate", params = {"startDate", "endDate"}, method = RequestMethod.GET)
  public String findProjectPerPeriodDate(
      @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate,
      Model model) {
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

  @RequestMapping(value = "/showProject/{id}", method = RequestMethod.GET)
  public String showProject(
      @PathVariable("id") String id,
      Model model) {
    Map<String, String> errorMap = new HashMap<>();
    ProjectValidator validator = new ProjectValidator();
    errorMap = validator.validateInputId(id);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "project/showProject";
    }
    BigInteger bigIntegerProjectId = new BigInteger(id);
    Integer projectExistence = projectDAO.findIfProjectExists(bigIntegerProjectId);
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

  @RequestMapping(value = "/showProjectUsersToDelete/{id}", method = RequestMethod.GET)
  public String getProjectUsersToDelete(
      @PathVariable("id") String id,
      Model model) {
    Map<String, String> errorMap = new HashMap<>();
    ProjectValidator validator = new ProjectValidator();
    errorMap = validator.validateInputId(id);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "project/deleteUserFromProject";
    }
    BigInteger bigIntegerProjectId = new BigInteger(id);
    Collection<User> userList = userDAO.findUserByProjectId(bigIntegerProjectId);
    model.addAttribute("projectId", bigIntegerProjectId);
    model.addAttribute("userList", userList);
    return "project/deleteUserFromProject";
  }

  @RequestMapping(value = "/deleteUserFromProject/project/{projectId}/user/{userId}", method = RequestMethod.POST)
  public String deleteUserFromProject(
      @PathVariable("projectId") String projectId,
      @PathVariable("userId") String userId,
      Model model) {
    Map<String, String> errorMap = new HashMap<>();
    Map<String, String> existenceError = new HashMap<>();
    ProjectValidator projectValidator = new ProjectValidator();
    errorMap = projectValidator.validateInputId(projectId);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "project/deleteUserFromProject";
    }
    BigInteger bigIntegerProjectId = new BigInteger(projectId);
    Integer projectExistence = projectDAO.findIfProjectExists(bigIntegerProjectId);
    projectValidator.validateExistence(projectExistence);
    if (!existenceError.isEmpty()) {
      model.addAttribute("errorMap", existenceError);
      return "project/deleteUserFromProject";
    }
    UserValidator userValidator = new UserValidator();
    errorMap = userValidator.validateInputId(userId);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "project/deleteUserFromProject";
    }
    BigInteger bigIntegerUserId = new BigInteger(userId);
    Integer userExistence = userDAO.findIfSEExists(bigIntegerUserId);
    existenceError = userValidator
        .validateExistence(userExistence);
    if (!existenceError.isEmpty()) {
      model.addAttribute("errorMap", existenceError);
      return "project/deleteUserFromProject";
    }
    projectDAO.deleteUserByUserId(bigIntegerUserId, bigIntegerProjectId);
    userDAO.updateProjectStatus(bigIntegerUserId, ProjectStatus.TRANSIT.getId());
    //TODO validate incoming work period and test this method
    WorkPeriod workPeriod = userDAO.findWorkingWorkPeriodByUserIdAndProjectId(bigIntegerUserId, bigIntegerProjectId);
    userDAO.updateWorkingPeriodStatusByUserId(workPeriod.getUserId(), workPeriod.getProjectId(), WorkPeriodStatus.FIRED.getId());
    Collection<User> userList = userDAO.findUserByProjectId(bigIntegerProjectId);
    model.addAttribute("projectId", bigIntegerProjectId);
    model.addAttribute("userList", userList);
    return "project/deleteUserFromProject";
  }

}
