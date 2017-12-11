package main.com.netcracker.project.controllers.project;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.com.netcracker.project.model.ProjectDAO;
import main.com.netcracker.project.model.ProjectDAO.OCStatus;
import main.com.netcracker.project.model.entity.Project;
import main.com.netcracker.project.model.impl.mappers.MapperDateConverter;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/project")
public class ProjectController {

  private Logger logger = Logger.getLogger(ProjectController.class);
  private ApplicationContext context =
      new ClassPathXmlApplicationContext("Spring-Module.xml");
  private ProjectDAO projectDao =
      (ProjectDAO) context.getBean("projectDAO");
  private static int numberOfSprint;
  private static int numberOfWorkers;


  @RequestMapping(value = "/create-form", method = RequestMethod.POST)
  public String createProject(
      @RequestParam("projectId") Integer id,
      @RequestParam("name") String name,
      @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate,
      @RequestParam("projectStatus") OCStatus projectStatus,
      @RequestParam("projectManagerId") Integer projectManagerId,
      @ModelAttribute("model") Map<String, Object> formSprintData) {

    Map<String, Object> models = formSprintData;

    MapperDateConverter mdc = new MapperDateConverter();

    Project project = new Project.ProjectBuilder()
        .projectId(BigInteger.valueOf(id))
        .name(name)
        .startDate(mdc.convertStringToDate(startDate))
        .endDate(mdc.convertStringToDate(endDate))
        .build();
    project.setProjectManagerId(BigInteger.valueOf(projectManagerId));
    project.setProjectStatus(projectStatus);
    projectDao.createProject(project);

//    List<SprintFormData> sprintFormDataList = formSprintData.getSprints();
//    List<WorkPeriodFormData> workPeriodFormDataList = formWorkPeriodData
//        .getWorkers();

    return "response_status/success";
  }


  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public ModelAndView ProjectSizePost(
      @RequestParam("countSprints") Integer countSprints,
      @RequestParam("countWorkers") Integer countWorkers,
      Model model) {
    numberOfSprint = countSprints;
    numberOfWorkers = countWorkers;
    Map<String, Object> models = new HashMap<>();

    SprintsForm formSprintData = new SprintsForm();
    List<SprintFormData> sprints = createEmptyList(numberOfSprint);
    formSprintData.setSprints(sprints);

    WorkPeriodForm workPeriodForm = new WorkPeriodForm();
    List<WorkPeriodFormData> workers = createEmptyList(numberOfWorkers);
    workPeriodForm.setWorkers(workers);

    models.put("formSprintData", formSprintData);
    models.put("formWorkPeriodData", workPeriodForm);

    return new ModelAndView("project/create-form", "model", models);
  }

  @RequestMapping(value = "/create", method = RequestMethod.GET)
  public String ProjectSizeGet() {
    return "project/create";
  }

  @RequestMapping(value = "/edit={id}", method = RequestMethod.POST)
  public String editProject(@PathVariable("id") Integer id, Model model) {
    return "project/create-form";
  }

  @RequestMapping(value = "/edit={id}", method = RequestMethod.GET)
  public String editProjectGet(@PathVariable("id") Integer id, Model model)
      throws InvocationTargetException {
    MapperDateConverter mdc = new MapperDateConverter();
    Project project = projectDao.findProjectByProjectId(BigInteger.valueOf(id));
    model.addAttribute("projectId", project.getProjectId());
    model.addAttribute("projectName", project.getName());
    model.addAttribute("startDate",
        mdc.convertDateToString(project.getStartDate()));
    model
        .addAttribute("endDate", mdc.convertDateToString(project.getEndDate()));
    model.addAttribute("status", project.getProjectStatus());
    model.addAttribute("pmId", project.getProjectManagerId());
    return "project/create-form";
  }

  @RequestMapping(value = "/view={id}", method = RequestMethod.GET)
  public String findProjectByProjectId(Model model,
      @PathVariable("id") Integer id) {
    MapperDateConverter mdc = new MapperDateConverter();
    Project project = null;
    try {
      project = projectDao
          .findProjectByProjectId(BigInteger.valueOf(id));
    } catch (InvocationTargetException e) {
      logger.error("Project absent in DB. Project id= " + id);
      e.printStackTrace();
    }
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
}
