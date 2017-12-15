package main.com.netcracker.project.controllers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import main.com.netcracker.project.model.entity.SprintStatistic;
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
  private Logger logger = Logger.getLogger(ProjectController.class);

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public String projectSprintStatLineChart(@PathVariable("id") Integer id,
      Model model) {
    logger.info("getProjectSprintStatLineChart() method. id = " + id);

    List<SprintStatistic> sprintStat = statisticService
        .getProjectSprintStatLineChart(BigInteger.valueOf(id));
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
}
