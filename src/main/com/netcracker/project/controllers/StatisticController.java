package main.com.netcracker.project.controllers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import main.com.netcracker.project.model.entity.SprintStatistic;
import main.com.netcracker.project.model.entity.UserTaskStatistic;
import main.com.netcracker.project.model.entity.VacationStatistic;
import main.com.netcracker.project.model.entity.WorkPeriodStatistic;
import main.com.netcracker.project.model.entity.WorkingHoursStatistic;
import main.com.netcracker.project.services.StatisticService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/statistic")
public class StatisticController {

  @Autowired
  private StatisticService statisticService;
  private static final Logger logger = Logger.getLogger(StatisticService.class);

  @RequestMapping(value = "/view_sprint={id}", method = RequestMethod.GET)
  public String projectSprintStatLineChart(@PathVariable("id") BigInteger id,
      Model model) {
    logger.info("projectSprintStatLineChart() method. id = " + id);

    List<SprintStatistic> sprintStat = statisticService
        .getProjectSprintStatLineChart(id);
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

    return "statistic/show_sprint_stat";
  }

  //TODO change date to path parameters
  @RequestMapping(value = "/view_project_task={id}", method = RequestMethod.GET)
  public String taskCountByProjectIdPieChart(@PathVariable("id") BigInteger id,
      Model model) {
    logger.info("taskCountByProjectIdPieChart() method. id = " + id);
    UserTaskStatistic projectTasks = statisticService
        .getTaskCountByProjectIdPieChart(id, "10.12.12", "13.12.12");
    model.addAttribute("critical", projectTasks.getCritical());
    model.addAttribute("high", projectTasks.getHigh());
    model.addAttribute("normal", projectTasks.getNormal());
    model.addAttribute("low", projectTasks.getLow());
    return "statistic/show_project_task";
  }

  @RequestMapping(value = "/view_user_task={id}", method = RequestMethod.GET)
  public String taskCountByUserIdPieChart(@PathVariable("id") BigInteger id,
      Model model) {
    logger.info("taskCountByUserIdPieChart() method. id = " + id);
    UserTaskStatistic userTasks = statisticService
        .getTaskCountByUserIdPieChart(id, "10.12.12", "13.12.12");
    model.addAttribute("critical", userTasks.getCritical());
    model.addAttribute("high", userTasks.getHigh());
    model.addAttribute("normal", userTasks.getNormal());
    model.addAttribute("low", userTasks.getLow());
    return "statistic/show_user_task";
  }

  @RequestMapping(value = "/user_hours_stat={id}", method = RequestMethod.GET)
  public String workingHoursByUserId(@PathVariable("id") BigInteger id,
      Model model) {
    logger.info("workingHoursByUserId() method. id = " + id);
    List<WorkingHoursStatistic> workingHoursStatisticList = statisticService
        .getWorkingHoursByUserId(id, "10.12.12", "15.12.12");
    model.addAttribute("workingHoursStatisticList", workingHoursStatisticList);
    return "statistic/show_user_hours_stat";
  }

  @RequestMapping(value = "/project_workers={id}", method = RequestMethod.GET)
  public String workPeriodByProjectId(@PathVariable("id") BigInteger id,
      Model model) {
    logger.info("workPeriodByProjectId() method. id = " + id);
    WorkPeriodStatistic workPeriod = statisticService
        .getWorkPeriodByProjectId(id);

    model.addAttribute("working", workPeriod.getWorking());
    model.addAttribute("fired", workPeriod.getFired());
    return "statistic/show_workers_project_stat";
  }

  @RequestMapping(value = "/vacation_project_stat={id}", method = RequestMethod.GET)
  public String vacationsByProjectIdAndPeriod(@PathVariable("id") BigInteger id,
      Model model) {
    logger.info("vacationsByProjectIdAndPeriod() method. id = " + id);
    List<VacationStatistic>  vacationsByProjectId = statisticService
        .getVacationsByProjectId(id, "11.12.12", "30.12.12");

    model.addAttribute("vacationsList", vacationsByProjectId);
    return "statistic/show_workers_vacations";
  }
}
