package com.netcracker.project.services.impl;

import com.netcracker.project.model.StatisticDAO;
import com.netcracker.project.model.entity.SprintStatistic;
import com.netcracker.project.model.entity.UserTaskStatistic;
import com.netcracker.project.model.entity.VacationStatistic;
import com.netcracker.project.model.entity.WorkPeriodStatistic;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.netcracker.project.model.entity.WorkingHoursStatistic;
import com.netcracker.project.services.StatisticService;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class StatisticServiceImpl implements StatisticService {

  private ApplicationContext context =
      new ClassPathXmlApplicationContext("Spring-Module.xml");
  private StatisticDAO statistic =
      (StatisticDAO) context.getBean("statisticDAO");
  private static final Logger logger = Logger.getLogger(StatisticServiceImpl.class);


  @Override
  public List<SprintStatistic> getProjectSprintStatLineChart(
      BigInteger projectId) {
    logger.info(
        "getProjectSprintStatLineChart() method. projectId = " + projectId);
    return statistic.findProjectSprintStatByProjectId(projectId);
  }

  @Override
  public UserTaskStatistic getTaskCountByProjectIdPieChart(
      BigInteger projectId, Date startDate, Date endDate) {
    logger.info(
        "getTaskCountByProjectIdPieChart() method. projectId = " + projectId
            + " startDate : " + startDate + " endDate : " + endDate);
    return statistic
        .findProjectTaskStatisticCountByProjectIdAndPeriod(projectId, startDate,
            endDate);
  }

  @Override
  public UserTaskStatistic getTaskCountByUserIdPieChart(BigInteger userId,
      Date startDate, Date endDate) {
    logger.info(
        "getTaskCountByUserIdPieChart() method. userId = " + userId
            + " startDate : " + startDate + " endDate : " + endDate);
    return statistic
        .findUserTaskCountByUserIdAndPeriod(userId, startDate, endDate);
  }

  @Override
  public List<WorkingHoursStatistic> getWorkingHoursByUserId(BigInteger userId,
      Date startDate, Date endDate) {
    logger.info(
        "getWorkingHoursByUserId() method. userId = " + userId
            + " startDate : " + startDate + " endDate : " + endDate);
    return statistic
        .findUserWorkingHoursByUserIdAndPeriod(userId, startDate, endDate);
  }

  @Override
  public WorkPeriodStatistic getWorkPeriodByProjectId(BigInteger projectId) {
    logger.info(
        "getWorkPeriodByProjectId() method. projectId = " + projectId);
    return statistic.findWorkPeriodByProjectIdAndStatus(projectId);
  }

  @Override
  public List<VacationStatistic> getVacationsByProjectId(BigInteger projectId,
      Date startDate, Date endDate) {
    logger.info(
        "getVacationsByProjectId() method. projectId = " + projectId
            + " startDate : " + startDate + " endDate : " + endDate);
    return statistic
        .findVacationsByProjectIdAndPeriod(projectId, startDate, endDate);
  }
}
