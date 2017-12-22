package com.netcracker.project.controllers;

import com.netcracker.project.controllers.project_form.SprintFormData;
import com.netcracker.project.controllers.project_form.SprintsForm;
import com.netcracker.project.controllers.project_form.WorkPeriodForm;
import com.netcracker.project.controllers.project_form.WorkPeriodFormData;
import com.netcracker.project.model.ProjectDAO;
import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.entity.Project;
import com.netcracker.project.model.entity.Sprint;
import com.netcracker.project.model.entity.WorkPeriod;
import com.netcracker.project.model.enums.OCStatus;
import com.netcracker.project.model.impl.mappers.MapperDateConverter;
import com.netcracker.project.services.ConvertJspDataService;
import com.netcracker.project.services.impl.ConvertJspDataServiceImpl;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
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

  private static final Logger logger = Logger.getLogger(ProjectController.class);
  @Autowired
  private ProjectDAO projectDAO;
  @Autowired
  private UserDAO userDAO;

  private ConvertJspDataService convertService = new ConvertJspDataServiceImpl();
  private MapperDateConverter converter = new MapperDateConverter();

  @RequestMapping(value = "/create_project", method = RequestMethod.POST)
  public String createProject(
      @RequestParam("projectId") Integer id,
      @RequestParam("name") String name,
      @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate,
      @RequestParam("projectStatus") OCStatus projectStatus,
      @RequestParam("projectManagerId") Integer projectManagerId,
      @ModelAttribute("modelSprint") SprintsForm sprintsForm,
      @ModelAttribute("modelWork") WorkPeriodForm modelWorker) {
    logger.info("POST request createProject(). ProjectId: " + id);

    List<SprintFormData> sprints = sprintsForm.getSprints();
    List<WorkPeriodFormData> workers = modelWorker.getWorkers();

    Project project = new Project.ProjectBuilder()
        .projectId(BigInteger.valueOf(id))
        .name(name)
        .startDate(converter.convertStringToDateFromJSP(startDate))
        .endDate(converter.convertStringToDateFromJSP(endDate))
        .build();
    project.setProjectManagerId(BigInteger.valueOf(projectManagerId));
    project.setProjectStatus(projectStatus);
    //projectDAO.createProject(project);
    logger.info("createProject request from DB. Project id: " + id);

    project.setSprints(convertService.createSprintFromJsp(sprints, project.getProjectId()));
    //project.getSprints().forEach(sprint -> projectDAO.createSprint(sprint, project.getProjectId()));

    project.setWorkPeriods(convertService.createWorkPeriod(workers, project.getProjectId()));
    //project.getWorkPeriods().forEach(workPeriod -> userDAO.createWorkPeriod(workPeriod));
    return "response_status/success";
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public String projectSizePost(
      @RequestParam("countSprints") Integer countSprints,
      @RequestParam("countWorkers") Integer countWorkers,
      Model model) {
    logger.info("projectSizePost() method. CountSprint: " + countSprints
        + ", countWorkers: " + countWorkers);

    SprintsForm formSprintData = new SprintsForm();
    List<SprintFormData> sprints = Arrays
        .asList(new SprintFormData[countSprints]);
    formSprintData.setSprints(sprints);

    WorkPeriodForm workPeriodForm = new WorkPeriodForm();
    List<WorkPeriodFormData> workers = Arrays
        .asList(new WorkPeriodFormData[countWorkers]);
    workPeriodForm.setWorkers(workers);

    model.addAttribute("modelSprint", formSprintData);
    model.addAttribute("modelWork", workPeriodForm);

    return "project/create_project";
  }

  @RequestMapping(value = "/create", method = RequestMethod.GET)
  public String projectSizeGet() {
    logger.info("projectSizeGet() method. Return project/create");
    return "project/create";
  }

  @RequestMapping(value = "/edit={id}", method = RequestMethod.POST)
  public String editProjectPost(
      @RequestParam("projectId") BigInteger projectId,
      @RequestParam("endDate") String endDate,
      @RequestParam("projectStatus") OCStatus projectStatus,
      @RequestParam("projectManagerId") BigInteger projectManagerId,
      @ModelAttribute("modelSprint") SprintsForm sprintsForm,
      @ModelAttribute("modelWorkers") WorkPeriodForm workPeriodForm,
      Model model) {
    logger.info("editProjectPost() method. Project id" + projectId
        + "endDate: " + endDate
        + "projectStatus: " + projectStatus
        + "projectManagerId: " + projectManagerId
        + "sprintsForm: " + sprintsForm);

    projectDAO
        .updateEndDate(projectId, converter.convertStringToDateFromJSP(endDate));
    projectDAO.updateStatus(projectId, projectStatus);
    projectDAO.updatePM(projectId, projectManagerId);

    if (sprintsForm.getSprints() != null) {
      List<SprintFormData> sprints = sprintsForm.getSprints();
      sprints.forEach(sprintData -> {
        projectDAO.updateSprintStatus(sprintData.getId(), sprintData.getSprintStatus());
        projectDAO.updateSprintPlannedEndDate(sprintData.getId(),
            converter.convertStringToDateFromJSP(sprintData.getPlannedEndDate()));
      });
    }

    if (workPeriodForm.getWorkers() != null) {
      List<WorkPeriodFormData> workings = workPeriodForm.getWorkers();

      workings.forEach(wp -> {
        WorkPeriod workPeriod = new WorkPeriod.WorkPeriodBuilder()
            .projectId(wp.getProjectId())
            .userId(wp.getUserId())
            .startWorkDate(converter.convertStringToDateFromJSP(wp.getStartWorkDate()))
            .endWorkDate(converter.convertStringToDateFromJSP(wp.getEndWorkDate()))
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

    Collection<Sprint> sprintCollection = project.getSprints();

    Collection<WorkPeriod> workPeriodCollection = userDAO
        .findWorkPeriodByProjectIdAndStatus(project.getProjectId(),
            project.getProjectStatus().getId());

    SprintsForm sprintForm = new SprintsForm();
    List<SprintFormData> sprints = convertService.convertSprintToSprintForm(sprintCollection);
    sprintForm.setSprints(sprints);

    WorkPeriodForm workPeriodForm = new WorkPeriodForm();
    List<WorkPeriodFormData> workPeriodFormData = convertService.convertWorkPeriodToWPForm(
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
