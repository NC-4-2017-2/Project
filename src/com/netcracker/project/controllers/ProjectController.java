package com.netcracker.project.controllers;

import com.netcracker.project.controllers.project_form.SprintFormData;
import com.netcracker.project.controllers.project_form.SprintsForm;
import com.netcracker.project.controllers.project_form.WorkPeriodForm;
import com.netcracker.project.controllers.project_form.WorkPeriodFormData;
import com.netcracker.project.controllers.validators.ProjectValidator;
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
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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

  private static final Logger logger = Logger
      .getLogger(ProjectController.class);

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
    ProjectValidator validator = new ProjectValidator();
    Map<String, String> errorMap = new HashMap<>();

    Integer existenceProject = projectDAO
        .findProjectByNameIfExist(projectName);
    if (existenceProject >= 1) {
      model.addAttribute("countSprints", countSprints);
      model.addAttribute("countWorkers", countWorkers);
      errorMap.put("PROJECT_EXIST_ERROR",
          projectName + " " + ErrorMessages.PROJECT_EXIST_ERROR);
      model.addAttribute("errorMap", errorMap);

      return "project/createProjectForm";
    }

    errorMap = validator.validateInteger(countSprints);
    if (!errorMap.isEmpty()) {
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
      model.addAttribute("errorMap", errorMap);
      return "project/createProjectForm";
    }
    Integer countWorkersInteger = new Integer(countWorkers);

    errorMap = validator
        .validateDates(projectStartDate, projectEndDate);
    Collection<Sprint> sprints = new ArrayList<>();

    if (!errorMap.isEmpty()) {
      model.addAttribute("countSprints", countSprints);
      model.addAttribute("countWorkers", countWorkers);
      model.addAttribute("errorMap", errorMap);
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
        return "project/createProjectForm";
      }
      if (users.size() == 1) {
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
          user = iterator.next();
        }
      }

      String startDateParam = request.getParameter("startDate" + i);
      String endDateParam = request.getParameter("endDate" + i);

      errorMap = validator
          .validateDates(startDateParam, endDateParam);
      if (!errorMap.isEmpty()) {
        model.addAttribute("countSprints", countSprints);
        model.addAttribute("countWorkers", countWorkers);
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
    Project currentProject = projectDAO.findProjectByName(projectName);
    for (WorkPeriod period : workPeriods) {
      userDAO.createWorkPeriod(period, currentProject.getProjectId());
    }

    return "response_status/success";
  }


  @RequestMapping(value = "/edit={id}", method = RequestMethod.POST)
  public String editProjectPost(
      @RequestParam("projectId") BigInteger projectId,
      @RequestParam("endDate") String endDate,
      @RequestParam("projectStatus") OCStatus projectStatus,
      @RequestParam("projectManagerId") BigInteger projectManagerId,
      @ModelAttribute("modelSprint") SprintsForm sprintsForm,
      @ModelAttribute("modelWorkers") WorkPeriodForm workPeriodForm) {
    logger.info("editProjectPost() method. Project id" + projectId
        + "endDate: " + endDate
        + "projectStatus: " + projectStatus
        + "projectManagerId: " + projectManagerId
        + "sprintsForm: " + sprintsForm);

    projectDAO
        .updateEndDate(projectId,
            converter.convertStringToDateFromJSP(endDate));
    projectDAO.updateStatus(projectId, projectStatus);
    projectDAO.updatePM(projectId, projectManagerId);

    if (sprintsForm.getSprints() != null) {
      List<SprintFormData> sprints = sprintsForm.getSprints();
      sprints.forEach(sprintData -> {
        projectDAO.updateSprintStatus(sprintData.getId(),
            sprintData.getSprintStatus());
        projectDAO.updateSprintPlannedEndDate(sprintData.getId(),
            converter
                .convertStringToDateFromJSP(sprintData.getPlannedEndDate()));
      });
    }

    if (workPeriodForm.getWorkers() != null) {
      List<WorkPeriodFormData> workings = workPeriodForm.getWorkers();

      workings.forEach(wp -> {
        WorkPeriod workPeriod = new WorkPeriod.WorkPeriodBuilder()
            .projectId(wp.getProjectId())
            .userId(wp.getUserId())
            .startWorkDate(
                converter.convertStringToDateFromJSP(wp.getStartWorkDate()))
            .endWorkDate(
                converter.convertStringToDateFromJSP(wp.getEndWorkDate()))
            .projectId(projectId)
            .workPeriodStatus(wp.getWorkPeriodStatus())
            .build();
        userDAO.updateWorkingPeriodEndDateByUserId(workPeriod);
        userDAO.updateWorkingPeriodStatusByUserId(workPeriod);
      });
    }

    return "response_status/success";
  }

  @RequestMapping(value = "/edit={id}", method = RequestMethod.GET)
  public String editProjectGet(@PathVariable("id") Integer id, Model model) {
    logger.info("editProjectGet() method. Id: " + id);
    Project project = projectDAO.findProjectByProjectId(BigInteger.valueOf(id));
    model.addAttribute("projectId", project.getProjectId());
    model.addAttribute("projectName", project.getName());
    model.addAttribute("startDate", project.getStartDate());
    model.addAttribute("endDate", project.getEndDate());
    model.addAttribute("projectStatus", project.getProjectStatus());
    model.addAttribute("pmId", project.getProjectManagerId());
    model.addAttribute("pmId", project.getProjectManagerId());

    Collection<Sprint> sprintCollection = null;

    Collection<WorkPeriod> workPeriodCollection = userDAO
        .findWorkPeriodByProjectIdAndStatus(project.getProjectId(),
            project.getProjectStatus().getId());

    SprintsForm sprintForm = new SprintsForm();
    List<SprintFormData> sprints = convertService
        .convertSprintToSprintForm(sprintCollection);
    sprintForm.setSprints(sprints);

    WorkPeriodForm workPeriodForm = new WorkPeriodForm();
    List<WorkPeriodFormData> workPeriodFormData = convertService
        .convertWorkPeriodToWPForm(
            workPeriodCollection);
    workPeriodForm.setWorkers(workPeriodFormData);

    model.addAttribute("modelSprint", sprintForm);
    model.addAttribute("modelWork", workPeriodForm);

    return "project/edit_project";
  }


  @RequestMapping(value = "/findProjectByProjectId/{id}", method = RequestMethod.GET)
  public String findProjectByProjectId(Model model,
      @PathVariable("id") Integer id) {
    logger.info("findProjectByProjectId() method. Id: " + id);

    Project project = projectDAO
        .findProjectByProjectId(BigInteger.valueOf(id));
    model.addAttribute("project", project);

    return "project/show_project";
  }

  @RequestMapping(value = "/findProjectByName/{name}", method = RequestMethod.GET)
  public String findProjectByName(Model model,
      @PathVariable("name") String name) {
    logger.info("findProjectByProjectId() method. Id: " + name);

    Project project = projectDAO.findProjectByName(name);
    model.addAttribute("project", project);

    return "project/show_project";
  }

  @RequestMapping(value = "/findProjectByDate/{date}", method = RequestMethod.GET)
  public String findProjectByDate(Model model,
      @PathVariable("date") Date date) {
    logger.info("findProjectByProjectId() method. Id: " + date);

    Collection<Project> projects = projectDAO.findProjectByDate(date);
    model.addAttribute("projectCollection", projects);

    return "project/show_project";
  }
}
