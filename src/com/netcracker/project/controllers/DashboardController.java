package com.netcracker.project.controllers;

import com.netcracker.project.model.ProjectDAO;
import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.entity.Project;
import com.netcracker.project.model.entity.User;
import com.netcracker.project.model.enums.JobTitle;
import com.netcracker.project.model.enums.ProjectStatus;
import java.math.BigInteger;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class DashboardController {

  @Autowired
  private UserDAO userDAO;
  @Autowired
  private ProjectDAO projectDAO;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String createVacationGet(Model model,
      Principal principal,
      HttpServletRequest request) {
    if (request.isUserInRole("ROLE_ADMIN")) {
      return "dashboard/adminDashboard";
    }
    User currentUser = userDAO.findUserByLogin(principal.getName());
    if(currentUser.getJobTitle().name().equals(JobTitle.PROJECT_MANAGER.name())) {
      if(!currentUser.getProjectStatus().name().equals(ProjectStatus.TRANSIT.name())) {
        BigInteger projectId = projectDAO.findProjectIdByPMLogin(principal.getName());
        Project pmCurrentProject = projectDAO.findProjectByProjectId(projectId);
        model.addAttribute("project", pmCurrentProject);
      }
      return "dashboard/PMDashboard";
    }
    return "dashboard/dashboard";
  }

}
