package com.netcracker.project.controllers;

import com.netcracker.project.controllers.project_form.SprintFormData;
import com.netcracker.project.controllers.project_form.SprintsForm;
import com.netcracker.project.controllers.project_form.WorkPeriodForm;
import com.netcracker.project.controllers.project_form.WorkPeriodFormData;
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
import java.util.Iterator;
import java.util.List;
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

  private static final Logger logger = Logger
      .getLogger(ProjectController.class);
  @Autowired
  private ProjectDAO projectDAO;
  @Autowired
  private UserDAO userDAO;

  private ConvertJspDataService convertService = new ConvertJspDataServiceImpl();
  private DateConverterService converter = new DateConverterService();


  @RequestMapping(value = "/createSprints/{countSprints}", method = RequestMethod.POST)
  public String createSprintsPost(
      @PathVariable("countSprints") Integer countSprints,
      HttpServletRequest request,
      Model model) {
    List<Sprint> sprintList = new ArrayList<>();

    for (int index = 0; index < countSprints; index++) {
      sprintList.add(new Sprint.SprintBuilder()
          .name(request.getParameter("sprintName" + index))
          .startDate(converter.convertStringToDateFromJSP(
              request.getParameter("startDate" + index)))
          .plannedEndDate(converter.convertStringToDateFromJSP(
              request.getParameter("planedEndDate" + index)))
          .build());
    }
    model.addAttribute("sprintList", sprintList);
    return "/project/setCountWorkers";
  }

  @RequestMapping(value = "/createWorkers", params = "countWorkers", method = RequestMethod.GET)
  public String createWorkers(
      @RequestParam("countWorkers") Integer countWorkers,
      HttpServletRequest request,
      Model model) {
    model.addAttribute("countWorkers", countWorkers);
    return "project/createWorkersForm";
  }

  @RequestMapping(value = "/createWorkers", params = "countWorkers", method = RequestMethod.POST)
  public String createWorkersPost(
      @RequestParam("countWorkers") Integer countWorkers,
      Model model,
      HttpServletRequest request) {
    List<WorkPeriod> workPeriods = new ArrayList<>();

    for (int i = 0; i < countWorkers; i++) {
      Collection<User> users = userDAO
          .findUserByLastNameAndFirstName(
              request.getParameter("userLastName" + i),
              request.getParameter("userFirstName" + i));
      User user = null;
      if (users.size() == 1) {
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
          user = iterator.next();
        }
      }

      new WorkPeriod.WorkPeriodBuilder()
          .userId(user.getUserId())
          .startWorkDate(converter.convertStringToDateFromJSP(
              request.getParameter("startDate" + i)))
          .endWorkDate(converter
              .convertStringToDateFromJSP(request.getParameter("endDate" + i)))
          .build();
    }

    model.addAttribute("countWorkers", countWorkers);
    return "project/createWorkersForm";
  }

  @RequestMapping(value = "/createProject")
  public String createProject() {
    return "project/createProject";
  }

  @RequestMapping(value = "/createProject", params = {"countSprints",
      "countWorkers"},
      method = RequestMethod.GET)
  public String createSprints(
      @RequestParam("countSprints") Integer sprintCount,
      @RequestParam("countWorkers") Integer workersCount,
      Model model) {
    Collection<User> pmOnTransit = userDAO
        .findUsersByJobTitleAndProjectStatus(JobTitle.PROJECT_MANAGER.getId(),
            ProjectStatus.TRANSIT.getId());

    model.addAttribute("pmOnTransitList", pmOnTransit);
    model.addAttribute("countSprints", sprintCount);
    model.addAttribute("countWorkers", sprintCount);
    return "project/createProjectForm";
  }

  @RequestMapping(value = "/createProject/{countSprints}/{countWorkers}", method = RequestMethod.POST)
  public String createProjectPost(
      @PathVariable("countSprints") Integer countSprints,
      @PathVariable("countWorkers") Integer countWorkers,
      @RequestParam("projectName") String projectName,
      @RequestParam("projectStartDate") String projectStartDate,
      @RequestParam("projectEndDate") String projectEndDate,
      @RequestParam("pmId") String pmId,
      Model model,
      HttpServletRequest request) {

    Collection<Sprint> sprints = new ArrayList<>();

    for (int index = 0; index < countSprints; index++) {
      sprints.add(new Sprint.SprintBuilder()
          .name(request.getParameter("sprintName" + index))
          .startDate(converter.convertStringToDateFromJSP(
              request.getParameter("startDate" + index)))
          .plannedEndDate(converter.convertStringToDateFromJSP(
              request.getParameter("planedEndDate" + index)))
          .build());
    }

    Collection<WorkPeriod> workPeriods = new ArrayList<>();

    for (int i = 0; i < countWorkers; i++) {
      Collection<User> users = userDAO
          .findUserByLastNameAndFirstName(
              request.getParameter("userLastName" + i),
              request.getParameter("userFirstName" + i));

      User user = null;
      if (users.size() == 1) {
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
          user = iterator.next();
        }
      }

      workPeriods.add(new WorkPeriod.WorkPeriodBuilder()
          .startWorkDate(converter.convertStringToDateFromJSP(
              request.getParameter("startDate" + i)))
          .endWorkDate(converter
              .convertStringToDateFromJSP(request.getParameter("endDate" + i)))
          .userId(user.getUserId())
          .workPeriodStatus(WorkPeriodStatus.WORKING)
          .build());
    }

    BigInteger pmIdBigInteger = new BigInteger(pmId);

    Project project = new ProjectBuilder()
        .name(projectName)
        .startDate(converter.convertStringToDateFromJSP(projectStartDate))
        .endDate(converter.convertStringToDateFromJSP(projectEndDate))
        .projectManagerId(pmIdBigInteger)
        .projectStatus(OCStatus.OPENED)
        .build();

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
