package com.netcracker.project.controllers;

import com.netcracker.project.controllers.validators.StatisticValidator;
import com.netcracker.project.controllers.validators.errorMessage.ErrorMessages;
import com.netcracker.project.model.ProjectDAO;
import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.entity.SprintStatistic;
import com.netcracker.project.model.entity.User;
import com.netcracker.project.model.entity.UserTaskStatistic;
import com.netcracker.project.model.entity.VacationStatistic;
import com.netcracker.project.model.entity.WorkPeriodStatistic;
import com.netcracker.project.model.enums.ProjectStatus;
import com.netcracker.project.services.impl.DateConverterService;
import java.math.BigInteger;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import com.netcracker.project.model.entity.WorkingHoursStatistic;
import com.netcracker.project.services.StatisticService;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/statistic")
public class StatisticController {

  @Autowired
  private StatisticService statisticService;
  @Autowired
  private UserDAO userDAO;
  @Autowired
  private ProjectDAO projectDAO;
  private static final Logger logger = Logger.getLogger(StatisticService.class);
  private DateConverterService converter = new DateConverterService();


  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/viewSprintStat", method = RequestMethod.GET)
  public String projectSprintStatLineChart(Model model, Principal principal) {
    logger.info("projectSprintStatLineChart() method. ");
    String userLogin = principal.getName();
    User currentUser = userDAO.findUserByLogin(userLogin);
    if (currentUser.getProjectStatus().name()
        .equals(ProjectStatus.TRANSIT.name())) {
      model.addAttribute("VIEW_SPRINT_TRANSIT_PM_ERROR",
          ErrorMessages.VIEW_SPRINT_TRANSIT_PM_ERROR);
      return "statistic/viewSprintStat";
    }
    BigInteger projectId = projectDAO
        .findProjectIdByPMLogin(userLogin);

    List<SprintStatistic> sprintStat = statisticService
        .getProjectSprintStatLineChart(projectId);
    List<Integer> plannedDate = new ArrayList<>();
    for (SprintStatistic sprint : sprintStat) {
      plannedDate.add(sprint.getPlannedTakeDays());
    }
    List<Integer> currentEnd = new ArrayList<>();
    for (SprintStatistic sprint : sprintStat) {
      currentEnd.add(sprint.getTakeDays());
    }
    model.addAttribute("sprintStatList", sprintStat);
    model.addAttribute("plannedDateList", plannedDate);
    model.addAttribute("currentEndList", currentEnd);

    return "statistic/viewSprintStat";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "findProjectTaskByPeriod", method = RequestMethod.GET)
  public String viewProjectTask() {
    return "statistic/findVacationProjectStatByPeriod";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/viewProjectTaskStat", params = {"startDate",
      "endDate"}, method = RequestMethod.GET)
  public String taskCountByProjectIdPieChart(
      @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate,
      Model model, Principal principal) {
    logger.info(
        "viewProjectTaskStat() method. startDate = " + startDate + ", endDate= "
            + endDate);

    StatisticValidator validator = new StatisticValidator();
    Map<String, String> errorMap = validator
        .validateDates(startDate, endDate);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "statistic/findVacationProjectStatByPeriod";
    }
    String userLogin = principal.getName();

    User currentUser = userDAO.findUserByLogin(userLogin);
    if (currentUser.getProjectStatus().name()
        .equals(ProjectStatus.TRANSIT.name())) {
      model.addAttribute("VIEW_SPRINT_TRANSIT_PM_ERROR",
          ErrorMessages.VIEW_SPRINT_TRANSIT_PM_ERROR);
      return "statistic/findVacationProjectStatByPeriod";
    }

    BigInteger projectId = projectDAO
        .findProjectIdByPMLogin(userLogin);

    UserTaskStatistic projectTasks = statisticService
        .getTaskCountByProjectIdAndDatePieChart(projectId,
            converter.convertStringToDateFromJSP(startDate),
            converter.convertStringToDateFromJSP(endDate));

    model.addAttribute("critical", projectTasks.getCritical());
    model.addAttribute("high", projectTasks.getHigh());
    model.addAttribute("normal", projectTasks.getNormal());
    model.addAttribute("low", projectTasks.getLow());
    return "statistic/viewProjectTaskStat";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/findUserTaskByFirstNameAndLastName", method = RequestMethod.GET)
  public String findUserTaskByFirstNameAndLastName() {
    return "statistic/findUserTaskByFirstNameAndLastName";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/viewUserTask", params = {"lastName", "firstName",
      "startDate", "endDate"}, method = RequestMethod.GET)
  public String taskCountByUserIdPieChart(Model model,
      @RequestParam("lastName") String lastName,
      @RequestParam("firstName") String firstName,
      @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate) {
    logger.info("taskCountByUserIdPieChart() method.");
    StatisticValidator validator = new StatisticValidator();
    Map<String, String> errorMap = validator
        .validateDates(startDate, endDate);

    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "statistic/findUserTaskByFirstNameAndLastName";
    }

    errorMap = validator.validateName(firstName);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "statistic/findUserTaskByFirstNameAndLastName";
    }

    errorMap = validator.validateName(lastName);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "statistic/findUserTaskByFirstNameAndLastName";
    }
    Collection<User> users = userDAO
        .findUserByLastNameAndFirstName(lastName, firstName);

    if (users.size() == 0) {
      errorMap.put("USER_ERROR", ErrorMessages.USER_ERROR);
      model.addAttribute("errorMap", errorMap);
      return "statistic/findUserTaskByFirstNameAndLastName";
    }
    Iterator<User> iterator = users.iterator();
    User user = null;
    while (iterator.hasNext()) {
      user = iterator.next();
    }

    UserTaskStatistic userTasks = statisticService
        .getTaskCountByUserIdPieChart(user.getUserId(),
            converter.convertStringToDateFromJSP(startDate),
            converter.convertStringToDateFromJSP(endDate));
    model.addAttribute("critical", userTasks.getCritical());
    model.addAttribute("high", userTasks.getHigh());
    model.addAttribute("normal", userTasks.getNormal());
    model.addAttribute("low", userTasks.getLow());
    return "statistic/viewUserTask";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/findUserHoursStat", method = RequestMethod.GET)
  public String findUserHoursStat() {
    return "statistic/findUserHoursStat";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/viewUserHoursStat", params = {"lastName",
      "firstName",
      "startDate", "endDate"}, method = RequestMethod.GET)
  public String workingHoursByUserId(
      @RequestParam("lastName") String lastName,
      @RequestParam("firstName") String firstName,
      @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate,
      Model model) {
    logger.info("viewUserHoursStat() method.");
    StatisticValidator validator = new StatisticValidator();
    Map<String, String> errorMap = validator
        .validateDates(startDate, endDate);

    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "statistic/findUserHoursStat";
    }

    errorMap = validator.validateName(firstName);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "statistic/findUserHoursStat";
    }

    errorMap = validator.validateName(lastName);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "statistic/findUserHoursStat";
    }
    Collection<User> users = userDAO
        .findUserByLastNameAndFirstName(lastName, firstName);

    if (users.size() == 0) {
      errorMap.put("USER_ERROR", ErrorMessages.USER_ERROR);
      model.addAttribute("errorMap", errorMap);
      return "statistic/findUserHoursStat";
    }
    Iterator<User> iterator = users.iterator();
    User user = null;
    while (iterator.hasNext()) {
      user = iterator.next();
    }

    List<WorkingHoursStatistic> workingHoursStatisticList = statisticService
        .getWorkingHoursByUserId(user.getUserId(),
            converter.convertStringToDateFromJSP(startDate),
            converter.convertStringToDateFromJSP(endDate));
    model.addAttribute("workingHoursStatisticList", workingHoursStatisticList);
    return "statistic/viewUserHoursStat";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/projectWorkers", method = RequestMethod.GET)
  public String workPeriodByProjectId(Model model, Principal principal) {
    String userLogin = principal.getName();
    User currentUser = userDAO.findUserByLogin(userLogin);

    if (currentUser.getProjectStatus().name()
        .equals(ProjectStatus.TRANSIT.name())) {
      model.addAttribute("VIEW_SPRINT_TRANSIT_PM_ERROR",
          ErrorMessages.VIEW_SPRINT_TRANSIT_PM_ERROR);
      return "statistic/viewWorkersProjectStat";
    }

    BigInteger projectId = projectDAO
        .findProjectIdByPMLogin(userLogin);
    logger.info("workPeriodByProjectId() method. id = " + projectId);

    WorkPeriodStatistic workPeriod = statisticService
        .getWorkPeriodByProjectId(projectId);

    model.addAttribute("working", workPeriod.getWorking());
    model.addAttribute("fired", workPeriod.getFired());
    return "statistic/viewWorkersProjectStat";
  }


  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/findVacationProjectStatByPeriod", method = RequestMethod.GET)
  public String findVacationProjectStat() {
    return "statistic/findVacationProjectStatByPeriod";
  }

  @RequestMapping(value = "/vacationProjectStat", params = {"startDate",
      "endDate"}, method = RequestMethod.GET)
  public String vacationsByProjectIdAndPeriod(
      @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate,
      Model model, Principal principal) {
    logger.info("vacationsByProjectIdAndPeriod() method.");
    StatisticValidator validator = new StatisticValidator();
    Map<String, String> errorMap = validator
        .validateDates(startDate, endDate);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "statistic/findVacationProjectStatByPeriod";
    }
    String userLogin = principal.getName();
    User currentUser = userDAO.findUserByLogin(userLogin);

    if (currentUser.getProjectStatus().name()
        .equals(ProjectStatus.TRANSIT.name())) {
      model.addAttribute("VIEW_SPRINT_TRANSIT_PM_ERROR",
          ErrorMessages.VIEW_SPRINT_TRANSIT_PM_ERROR);
      return "statistic/viewWorkersProjectStat";
    }
    BigInteger projectId = projectDAO
        .findProjectIdByPMLogin(userLogin);

    List<VacationStatistic> vacationsByProjectId = statisticService
        .getVacationsByProjectId(projectId,
            converter.convertStringToDateFromJSP(startDate),
            converter.convertStringToDateFromJSP(endDate));

    model.addAttribute("vacationsList", vacationsByProjectId);
    return "statistic/viewWorkersVacations";
  }

  @RequestMapping(value = "/findOwnTaskStatByPeriod", method = RequestMethod.GET)
  public String findOwnTaskStatByPeriod() {
    return "statistic/findOwnTaskStatByPeriod";
  }
  //TODO add data validation
  @RequestMapping(value = "/viewOwnTaskStatistic", params = {"startDate",
      "endDate"}, method = RequestMethod.GET)
  public String viewOwnTaskStatistic(
      @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate,
      Principal principal, Model model
  ) {
    String userLogin = principal.getName();
    User currentUser = userDAO.findUserByLogin(userLogin);
    UserTaskStatistic userTasks = statisticService
        .getTaskCountByUserIdPieChart(currentUser.getUserId(),
            converter.convertStringToDateFromJSP(startDate),
            converter.convertStringToDateFromJSP(endDate));
    model.addAttribute("critical", userTasks.getCritical());
    model.addAttribute("high", userTasks.getHigh());
    model.addAttribute("normal", userTasks.getNormal());
    model.addAttribute("low", userTasks.getLow());
    return "statistic/viewUserTask";
  }

  @RequestMapping(value = "/findOwnHoursStatByPeriod", method = RequestMethod.GET)
  public String findOwnHoursStatByPeriod() {
    return "statistic/findOwnHoursStatByPeriod";
  }

  @RequestMapping(value = "/viewOwnHoursStat", params = {"startDate",
      "endDate"}, method = RequestMethod.GET)
  public String viewOwnHoursStat(
      @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate,
      Model model, Principal principal
  ) {
    String userLogin = principal.getName();
    User currentUser = userDAO.findUserByLogin(userLogin);
    List<WorkingHoursStatistic> workingHoursStatisticList = statisticService
        .getWorkingHoursByUserId(currentUser.getUserId(),
            converter.convertStringToDateFromJSP(startDate),
            converter.convertStringToDateFromJSP(endDate));

    model.addAttribute("workingHoursStatisticList", workingHoursStatisticList);
    return "statistic/viewUserHoursStat";
  }
}
