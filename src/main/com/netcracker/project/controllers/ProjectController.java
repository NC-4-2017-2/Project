package main.com.netcracker.project.controllers;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import main.com.netcracker.project.model.ProjectDAO;
import main.com.netcracker.project.model.ProjectDAO.OCStatus;
import main.com.netcracker.project.model.entity.Project;
import main.com.netcracker.project.model.impl.mappers.MapperDateConverter;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Secured(value = {"ROLE_ADMIN"})
@RequestMapping("/project")
public class ProjectController {

  private Logger logger = Logger.getLogger(ProjectController.class);
  private ApplicationContext context =
      new ClassPathXmlApplicationContext("Spring-Module.xml");
  private ProjectDAO projectDao =
      (ProjectDAO) context.getBean("projectDAO");

  @RequestMapping(value = "/create-form", method = RequestMethod.POST)
  public String createProject(
      @RequestParam("projectId") Integer id,
      @RequestParam("name") String name,
      @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate,
      @RequestParam("projectStatus") OCStatus projectStatus,
      @RequestParam("projectManagerId") Integer projectManagerId,
      Model model) {

    MapperDateConverter mdc = new MapperDateConverter();
    Project project = new Project.ProjectBuilder()
        .projectId(BigInteger.valueOf(id))
        .name(name)
        .startDate(mdc.convertStringToDate(startDate))
        .endDate(mdc.convertStringToDate(endDate))
        .build();
    project.setProjectManagerId(BigInteger.valueOf(projectManagerId));
    //projectDao.createProject(project);
    return "project/create-form";
  }

  @RequestMapping(value = "/create-form", method = RequestMethod.GET)
  public String projectSizeGet(Model model) {
    model.addAttribute("countSprints");
    return "project/create-form";
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public String ProjectSizePost(
      @RequestParam("countSprints") Integer countSprints,
      @RequestParam("countWorkers") Integer countWorkers,
      Model model) {
    model.addAttribute("countSprints", countSprints);
    model.addAttribute("countWorkers", countWorkers);

    return "project/create-form";
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


}
