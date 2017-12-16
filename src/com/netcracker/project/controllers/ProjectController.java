package com.netcracker.project.controllers;

import com.netcracker.project.model.impl.mappers.MapperDateConverter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.netcracker.project.controllers.project_form.SprintFormData;
import com.netcracker.project.controllers.project_form.SprintsForm;
import com.netcracker.project.controllers.project_form.WorkPeriodForm;
import com.netcracker.project.controllers.project_form.WorkPeriodFormData;
import com.netcracker.project.model.ProjectDAO;
import com.netcracker.project.model.ProjectDAO.OCStatus;
import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.UserDAO.WorkPeriod;
import com.netcracker.project.model.entity.Project;
import com.netcracker.project.model.entity.Sprint;
import com.netcracker.project.model.entity.Sprint.SprintBuilder;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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

  private Logger logger = Logger.getLogger(ProjectController.class);
  private ApplicationContext context =
      new ClassPathXmlApplicationContext("Spring-Module.xml");
  private ProjectDAO projectDao =
      (ProjectDAO) context.getBean("projectDAO");
  private UserDAO userDAO = (UserDAO) context.getBean("userDAO");
  private MapperDateConverter mdc = new MapperDateConverter();


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
        .startDate(mdc.convertStringToDate(startDate))
        .endDate(mdc.convertStringToDate(endDate))
        .build();
    project.setProjectManagerId(BigInteger.valueOf(projectManagerId));
    project.setProjectStatus(projectStatus);
    //projectDao.createProject(project);
    logger.info("createProject request from DB. Project id: " + id);
    project.setSprints(createSprintFromJsp(sprints, project.getProjectId()));
    project.setWorkPeriods(createWorkPeriod(workers, project.getProjectId()));

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
    List<SprintFormData> sprints = createEmptyList(countSprints);
    formSprintData.setSprints(sprints);

    WorkPeriodForm workPeriodForm = new WorkPeriodForm();
    List<WorkPeriodFormData> workers = createEmptyList(countWorkers);
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

    List<SprintFormData> sprints = sprintsForm.getSprints();
    List<WorkPeriodFormData> workings = workPeriodForm.getWorkers();

    projectDao
        .updateEndDate(projectId, mdc.convertStringToDate(endDate));
    projectDao.updateStatus(projectId, projectStatus);
    projectDao.updatePM(projectId, projectManagerId);

    for (SprintFormData s : sprints) {
      projectDao.updateSprintStatus(s.getId(), s.getSprintStatus());
      projectDao.updateSprintPlannedEndDate(s.getId(),
          mdc.convertStringToDate(s.getPlannedEndDate()));
    }

    for (WorkPeriodFormData wp : workings) {
      WorkPeriod workPeriod = new WorkPeriod();
      workPeriod.setProjectId(wp.getProjectId());
      workPeriod.setUserId(wp.getUserId());
      workPeriod.setEndWorkDate(mdc.convertStringToDate(wp.getEndWorkDate()));
      workPeriod.setProjectId(projectId);
      workPeriod.setWorkPeriodStatus(wp.getWorkPeriodStatus());
      userDAO.updateWorkingPeriodEndDateByUserId(workPeriod);
      userDAO.updateWorkingPeriodStatusByUserId(workPeriod);
    }

    return "response_status/success";
  }

  @RequestMapping(value = "/edit={id}", method = RequestMethod.GET)
  public String editProjectGet(@PathVariable("id") Integer id, Model model) {
    logger.info("editProjectGet() method. Id: " + id);
    Project project = projectDao.findProjectByProjectId(BigInteger.valueOf(id));
    model.addAttribute("projectId", project.getProjectId());
    model.addAttribute("projectName", project.getName());
    model.addAttribute("startDate",
        mdc.convertDateToString(project.getStartDate()));
    model
        .addAttribute("endDate", mdc.convertDateToString(project.getEndDate()));
    model.addAttribute("status", project.getProjectStatus());
    model.addAttribute("pmId", project.getProjectManagerId());
    model.addAttribute("pmId", project.getProjectManagerId());

    Collection<Sprint> sprintCollection = project.getSprints();

    Collection<WorkPeriod> workPeriodCollection = userDAO
        .findWorkPeriodByProjectIdAndStatus(project.getProjectId(),
            project.getProjectStatus().getId());

    SprintsForm sprintForm = new SprintsForm();
    List<SprintFormData> sprints = convertSprintToSprintForm(sprintCollection);
    sprintForm.setSprints(sprints);

    WorkPeriodForm workPeriodForm = new WorkPeriodForm();
    List<WorkPeriodFormData> workPeriodFormData = convertWorkPeriodToWPForm(
        workPeriodCollection);
    workPeriodForm.setWorkers(workPeriodFormData);

    model.addAttribute("modelSprint", sprintForm);
    model.addAttribute("modelWork", workPeriodForm);

    return "project/edit_project";
  }


  @RequestMapping(value = "/view={id}", method = RequestMethod.GET)
  public String findProjectByProjectId(Model model,
      @PathVariable("id") Integer id) {
    logger.info("findProjectByProjectId() method. Id: " + id);
    MapperDateConverter mdc = new MapperDateConverter();
    Project project = projectDao
        .findProjectByProjectId(BigInteger.valueOf(id));

    model.addAttribute("projectId", project.getProjectId());
    model.addAttribute("projectName", project.getName());
    model.addAttribute("startDate",
        mdc.convertDateToString(project.getStartDate()));
    model
        .addAttribute("endDate", mdc.convertDateToString(project.getEndDate()));
    model.addAttribute("status", project.getProjectStatus());
    model.addAttribute("pmId", project.getProjectManagerId());
    return "project/show_project";
  }


  private List createEmptyList(int i) {
    List<SprintFormData> list = new ArrayList<>();
    for (int j = 0; j < i; j++) {
      list.add(null);
    }
    return list;
  }

  private List<SprintFormData> convertSprintToSprintForm(
      Collection<Sprint> sprints) {
    List<SprintFormData> sprintForms = new ArrayList<>();

    for (Sprint sprint : sprints) {
      sprintForms.add(new SprintFormData(
          sprint.getSprintId(),
          sprint.getName(),
          sprint.getStatus(),
          mdc.convertDateToString(sprint.getPlannedEndDate())));
    }
    return sprintForms;
  }

  private List<WorkPeriodFormData> convertWorkPeriodToWPForm(
      Collection<WorkPeriod> workPeriodCollection) {
    List<WorkPeriodFormData> workPeriods = new ArrayList<>();
    for (WorkPeriod w : workPeriodCollection) {
      workPeriods.add(
          new WorkPeriodFormData(w.getWorkPeriodId(), w.getUserId(),
              mdc.convertDateToString(w.getStartWorkDate()),
              mdc.convertDateToString(w.getEndWorkDate()),
              w.getWorkPeriodStatus()));
    }
    return workPeriods;
  }


  private Collection<Sprint> createSprintFromJsp(List<SprintFormData> sprints,
      BigInteger projectId) {
    Collection<Sprint> resultSprint = new ArrayList<>();
    for (SprintFormData sprint1 : sprints) {
      Sprint sprint = new SprintBuilder()
          .name(sprint1.getName())
          .startDate(mdc.convertStringToDate(sprint1.getStartDate()))
          .plannedEndDate(
              mdc.convertStringToDate(sprint1.getPlannedEndDate()))
          .endDate(
              mdc.convertStringToDate(sprint1.getPlannedEndDate()))
          .build();
      resultSprint.add(sprint);

      //projectDao.createSprint(sprint, projectId);
    }
    return resultSprint;
  }

  private Collection<WorkPeriod> createWorkPeriod(
      List<WorkPeriodFormData> workers,
      BigInteger projectId) {
    Collection<WorkPeriod> resultPeriod = new ArrayList<>();
    for (WorkPeriodFormData w : workers) {
      WorkPeriod workPeriod = new WorkPeriod();
      workPeriod.setWorkPeriodId(w.getWorkPeriodId());
      workPeriod
          .setStartWorkDate(mdc.convertStringToDate(w.getStartWorkDate()));
      workPeriod.setEndWorkDate(mdc.convertStringToDate(w.getEndWorkDate()));
      workPeriod.setWorkPeriodStatus(w.getWorkPeriodStatus());
      workPeriod.setProjectId(projectId);
      workPeriod.setUserId(w.getUserId());

      resultPeriod.add(workPeriod);
      //userDAO.createWorkPeriod(workPeriod);
    }

    return resultPeriod;
  }

}
