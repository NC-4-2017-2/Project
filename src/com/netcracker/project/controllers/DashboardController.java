package com.netcracker.project.controllers;

import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.entity.User;
import com.netcracker.project.model.enums.JobTitle;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class DashboardController {

  @Autowired
  private UserDAO userDAO;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String createVacationGet(Principal principal) {
    User currentUser = userDAO.findUserByLogin(principal.getName());
    if(currentUser.getJobTitle().name().equals(JobTitle.PROJECT_MANAGER.name())) {
      return "dashboard/PMDashboard";
    }
    return "dashboard/dashboard";
  }

}
