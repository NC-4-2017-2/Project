package com.netcracker.project.controllers;

import com.netcracker.project.model.ProjectDAO;
import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.WorkingDayDAO;
import com.netcracker.project.model.entity.Project;
import com.netcracker.project.model.entity.User;
import com.netcracker.project.model.entity.UserTaskStatistic;
import com.netcracker.project.model.entity.WorkingDay;
import com.netcracker.project.model.entity.WorkingHoursStatistic;
import com.netcracker.project.model.enums.JobTitle;
import com.netcracker.project.model.enums.ProjectStatus;
import com.netcracker.project.services.StatisticService;
import com.netcracker.project.services.impl.DateConverterService;
import com.netcracker.project.services.impl.WorkingDayService;
import java.math.BigInteger;
import java.security.Principal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
  @Autowired
  private WorkingDayDAO workingDayDAO;


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
            converter.convertStringToDateFromJSP("2019-01-01"));

    UserTaskStatistic userTasks = statisticService
        .getTaskCountByUserIdPieChart(currentUser.getUserId(),
            converter.convertStringToDateFromJSP("1991-01-01"),
            converter.convertStringToDateFromJSP("2019-01-01"));

    model.addAttribute("userName", userName);
    model.addAttribute("workingHoursStatisticList", workingHoursStatisticList);

    model.addAttribute("critical", userTasks.getCritical());
    model.addAttribute("high", userTasks.getHigh());
    model.addAttribute("normal", userTasks.getNormal());
    model.addAttribute("low", userTasks.getLow());

    WorkingDayService workingDayService = new WorkingDayService();
    int currentWeekNumber = workingDayService.getCurrentWeekNumber();
    User user = userDAO.findUserByLogin(principal.getName());
    LocalDate date = LocalDate.now();
    DayOfWeek currentDay = date.getDayOfWeek();
    int currentDayValue = currentDay.getValue();
    if (currentDayValue >= DayOfWeek.MONDAY.getValue()) {
      LocalDate localDate = workingDayService
          .getLocalDatePrevious(currentWeekNumber, DayOfWeek.MONDAY);
      Date from = Date
          .from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
      Integer ifWorkingDayExists = workingDayDAO
          .findIfWorkingDayExists(user.getUserId(), from);
      if (ifWorkingDayExists == 1) {
        WorkingDay monday = workingDayDAO
            .findWorkingDayByUserIdAndDate(user.getUserId(), from);
        model.addAttribute("MondayTime", monday);
      }
    }
    if (currentDayValue >= DayOfWeek.TUESDAY.getValue()) {
      LocalDate localDate = workingDayService
          .getLocalDatePrevious(currentWeekNumber, DayOfWeek.TUESDAY);
      Date from = Date
          .from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
      Integer ifWorkingDayExists = workingDayDAO
          .findIfWorkingDayExists(user.getUserId(), from);
      if (ifWorkingDayExists == 1) {
        WorkingDay tuesday = workingDayDAO
            .findWorkingDayByUserIdAndDate(user.getUserId(), from);
        model.addAttribute("TuesdayTime", tuesday);
      }
    }
    if (currentDayValue >= DayOfWeek.WEDNESDAY.getValue()) {
      LocalDate localDate = workingDayService
          .getLocalDatePrevious(currentWeekNumber, DayOfWeek.WEDNESDAY);
      Date from = Date
          .from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
      Integer ifWorkingDayExists = workingDayDAO
          .findIfWorkingDayExists(user.getUserId(), from);
      if (ifWorkingDayExists == 1) {
        WorkingDay wednesday = workingDayDAO
            .findWorkingDayByUserIdAndDate(user.getUserId(), from);
        model.addAttribute("WednesdayTime", wednesday);
      }
    }
    if (currentDayValue >= DayOfWeek.THURSDAY.getValue()) {
      LocalDate localDate = workingDayService
          .getLocalDatePrevious(currentWeekNumber, DayOfWeek.THURSDAY);
      Date from = Date
          .from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
      Integer ifWorkingDayExists = workingDayDAO
          .findIfWorkingDayExists(user.getUserId(), from);
      if (ifWorkingDayExists == 1) {
        WorkingDay thursday = workingDayDAO
            .findWorkingDayByUserIdAndDate(user.getUserId(), from);
        model.addAttribute("ThursdayTime", thursday);

      }
    }
    if (currentDayValue >= DayOfWeek.FRIDAY.getValue()) {
      LocalDate localDate = workingDayService
          .getLocalDatePrevious(currentWeekNumber, DayOfWeek.FRIDAY);
      Date from = Date
          .from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
      Integer ifWorkingDayExists = workingDayDAO
          .findIfWorkingDayExists(user.getUserId(), from);
      if (ifWorkingDayExists == 1) {
        WorkingDay friday = workingDayDAO
            .findWorkingDayByUserIdAndDate(user.getUserId(), from);
        model.addAttribute("FridayTime", friday);
      }
    }
    if (currentDayValue >= DayOfWeek.SATURDAY.getValue()) {
      LocalDate localDate = workingDayService
          .getLocalDatePrevious(currentWeekNumber, DayOfWeek.SATURDAY);
      Date from = Date
          .from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
      Integer ifWorkingDayExists = workingDayDAO
          .findIfWorkingDayExists(user.getUserId(), from);
      if (ifWorkingDayExists == 1) {
        WorkingDay saturday = workingDayDAO
            .findWorkingDayByUserIdAndDate(user.getUserId(), from);
        model.addAttribute("SaturdayTime", saturday);
      }
    }
    if (currentDayValue >= DayOfWeek.SUNDAY.getValue()) {
      LocalDate localDate = workingDayService
          .getLocalDatePrevious(currentWeekNumber, DayOfWeek.SUNDAY);
      Date from = Date
          .from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
      Integer ifWorkingDayExists = workingDayDAO
          .findIfWorkingDayExists(user.getUserId(), from);
      if (ifWorkingDayExists == 1) {
        WorkingDay sunday = workingDayDAO
            .findWorkingDayByUserIdAndDate(user.getUserId(), from);
        model.addAttribute("SundayTime", sunday);
      }
    }
    if (request.isUserInRole("ROLE_ADMIN")) {
      return "dashboard/adminDashboard";
    }
    if (currentUser.getJobTitle().name()
        .equals(JobTitle.PROJECT_MANAGER.name())) {
      if (!currentUser.getProjectStatus().name()
          .equals(ProjectStatus.TRANSIT.name())) {
        BigInteger projectId = projectDAO
            .findProjectIdByPMLogin(principal.getName());
        Project pmCurrentProject = projectDAO
            .findProjectByProjectId(projectId);
        model.addAttribute("project", pmCurrentProject);
      }
      model.addAttribute("currentUser", currentUser);
      return "dashboard/PMDashboard";
    }
    if (currentUser.getJobTitle().name()
        .equals(JobTitle.LINE_MANAGER.name())) {
      return "dashboard/LMDashboard";
    }
    return "dashboard/dashboard";
  }
}
