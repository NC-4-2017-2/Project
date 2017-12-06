package main.com.netcracker.project.controllers;

import java.math.BigInteger;
import java.util.Date;
import main.com.netcracker.project.model.ProjectDAO;
import main.com.netcracker.project.model.ProjectDAO.OCStatus;
import main.com.netcracker.project.model.entity.Project;
import main.com.netcracker.project.model.impl.mappers.MapperDateConverter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Secured(value = {"ROLE_ADMIN"})
@RequestMapping("/project")
public class ProjectController {


  private ApplicationContext context =
      new ClassPathXmlApplicationContext("Spring-Module.xml");
  private ProjectDAO projectDao =
      (ProjectDAO) context.getBean("projectDAO");


  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public String createProject(
      @RequestParam("projectId") Integer id,
      @RequestParam("name") String name,
      @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate,
      @RequestParam("projectStatus") OCStatus projectStatus,
      @RequestParam("projectManagerId") Integer projectManagerId) {
    MapperDateConverter mdc = new MapperDateConverter();

    Project project = new Project.ProjectBuilder()
        .projectId(BigInteger.valueOf(id))
        .name(name)
        .startDate(mdc.convertStringToDate(startDate))
        .endDate(mdc.convertStringToDate(endDate))
        .build();
    project.setProjectStatus(projectStatus);
    project.setProjectManagerId(BigInteger.valueOf(projectManagerId));
    projectDao.createProject(project);
    return "response_status/success";
  }

  @RequestMapping(value = "/create", method = RequestMethod.GET)
  public String createProjectGet() {
    String s = System.getenv("ERP_URL");
    return "create";
  }


  //todo check input params
  @RequestMapping(value = "/edit={id}", method = RequestMethod.POST)
  public String editProject(@PathVariable("id") Integer id, Date date) {
    projectDao.updateEndDate(BigInteger.valueOf(id), date);
    return "response_status/success";
  }

  @RequestMapping(value = "/view={id}", method = RequestMethod.GET)
  public String viewProject(Model model, @PathVariable("id") Integer id) {
    Project project = projectDao
        .findProjectByProjectId(BigInteger.valueOf(id));

    model.addAttribute("projectId", project.getProjectId());
    model.addAttribute("projectName", project.getName());

    return "project";
  }

  @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
  @ResponseBody
  public Project findProjectById(Model model,
      @PathVariable("projectId") Integer projectId) {
    Project project = projectDao
        .findProjectByProjectId(BigInteger.valueOf(projectId));

    return project;
  }

}
