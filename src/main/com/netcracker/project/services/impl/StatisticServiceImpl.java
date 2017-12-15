package main.com.netcracker.project.services.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import main.com.netcracker.project.model.StatisticDAO;
import main.com.netcracker.project.model.entity.SprintStatistic;
import main.com.netcracker.project.model.entity.UserTaskStatistic;
import main.com.netcracker.project.model.entity.VacationStatistic;
import main.com.netcracker.project.model.entity.WorkPeriodStatistic;
import main.com.netcracker.project.model.entity.WorkingHoursStatistic;
import main.com.netcracker.project.services.StatisticService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class StatisticServiceImpl implements StatisticService {

  private ApplicationContext context =
      new ClassPathXmlApplicationContext("Spring-Module.xml");
  private StatisticDAO statistic =
      (StatisticDAO) context.getBean("statisticDAO");

  @Override
  public List<SprintStatistic> getProjectSprintStatLineChart(
      BigInteger projectId) {
    return statistic.findProjectSprintStatByProjectId(projectId);
  }

  @Override
  public List<UserTaskStatistic> getTaskCountByProjectIdPieChart(
      BigInteger projectId, String startDate, String endDate) {
    return null;
  }

  @Override
  public UserTaskStatistic getTaskCountByUserIdPieChart(BigInteger userId,
      String startDate, String endDate) {
    return null;
  }

  @Override
  public WorkingHoursStatistic getWorkingHoursByUserId(BigInteger userId,
      Date startDate, Date endDate) {
    return null;
  }

  @Override
  public WorkPeriodStatistic getWorkPeriodByProjectId(BigInteger projectId) {
    return null;
  }

  @Override
  public VacationStatistic getVacationsByProjectId(BigInteger projectId,
      Date startDate, Date endDate) {
    return null;
  }
}
