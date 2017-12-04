package main.com.netcracker.project.controllers;

import java.math.BigInteger;
import java.util.Locale;
import main.com.netcracker.project.model.ProjectDAO;
import main.com.netcracker.project.model.entity.Project;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Secured(value = {"ROLE_ADMIN"})
@RequestMapping("/project")
public class ProjectController {


  private ApplicationContext context =
      new ClassPathXmlApplicationContext("Spring-Module.xml");
  private ProjectDAO projectDao =
      (ProjectDAO) context.getBean("projectDAO");

  @RequestMapping(value = "/create", method = RequestMethod.GET)
  public String createProject() {

    return null;
  }

  @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
  @ResponseBody
  public Project findProjectById(Model model,
      @PathVariable("projectId") Integer projectId) {
    Locale.setDefault(Locale.ENGLISH);

    Project project = projectDao
        .findProjectByProjectId(BigInteger.valueOf(projectId));
    model.addAttribute("projectId", project.getProjectId());
    model.addAttribute("projectName", project.getName());

    return project;
  }

}
