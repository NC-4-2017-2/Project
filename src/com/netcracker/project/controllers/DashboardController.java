package com.netcracker.project.controllers;

import com.netcracker.project.model.ProjectDAO;
import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.entity.Project;
import com.netcracker.project.model.entity.User;
import com.netcracker.project.model.entity.UserTaskStatistic;
import com.netcracker.project.model.entity.WorkingHoursStatistic;
import com.netcracker.project.model.enums.JobTitle;
import com.netcracker.project.model.enums.ProjectStatus;
import com.netcracker.project.services.StatisticService;
import com.netcracker.project.services.impl.DateConverterService;
import java.math.BigInteger;
import java.security.Principal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
  @Autowired
  private StatisticService statisticService;

  private DateConverterService converter = new DateConverterService();

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String dashBoardGet(Model model,
      Principal principal,
      HttpServletRequest request) {
    String userName = principal.getName();

    User currentUser = userDAO.findUserByLogin(userName);
    List<WorkingHoursStatistic> workingHoursStatisticList = statisticService
        .getWorkingHoursByUserId(currentUser.getUserId(),
            converter.convertStringToDateFromJSP("1991-01-01"),
            converter.convertStringToDateFromJSP("2018-01-01"));

    UserTaskStatistic userTasks = statisticService
        .getTaskCountByUserIdPieChart(currentUser.getUserId(),
            converter.convertStringToDateFromJSP("1991-01-01"),
            converter.convertStringToDateFromJSP("2018-01-01"));

    model.addAttribute("userName", userName);
    model.addAttribute("workingHoursStatisticList", workingHoursStatisticList);

    model.addAttribute("critical", userTasks.getCritical());
    model.addAttribute("high", userTasks.getHigh());
    model.addAttribute("normal", userTasks.getNormal());
    model.addAttribute("low", userTasks.getLow());

    if (request.isUserInRole("ROLE_ADMIN")) {
      return "dashboard/adminDashboard";
    }
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
