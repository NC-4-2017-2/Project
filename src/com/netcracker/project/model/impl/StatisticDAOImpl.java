package com.netcracker.project.model.impl;

import com.netcracker.project.model.StatisticDAO;
import com.netcracker.project.model.entity.SprintStatistic;
import com.netcracker.project.model.entity.UserTaskStatistic;
import com.netcracker.project.model.entity.VacationStatistic;
import com.netcracker.project.model.entity.WorkPeriodStatistic;
import com.netcracker.project.model.impl.mappers.SprintStatisticMapper;
import com.netcracker.project.model.impl.mappers.UserTaskStatisticMapper;
import com.netcracker.project.model.impl.mappers.VacationStatisticMapper;
import com.netcracker.project.model.impl.mappers.WorkPeriodStatisticMapper;
import com.netcracker.project.model.impl.mappers.WorkingHoursStatisticMapper;
import com.netcracker.project.model.entity.WorkingHoursStatistic;
import java.util.Date;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class StatisticDAOImpl implements StatisticDAO {

  private static final Logger logger = Logger.getLogger(UserDAOImpl.class);

  private JdbcTemplate template;

  public void setDataSource(DataSource dataSource) {
    template = new JdbcTemplate(dataSource);
  }

  @Override
  public List<SprintStatistic> findProjectSprintStatByProjectId(
      BigInteger projectId) {
    logger.info("Entering findProjectSprintStatByProjectId(" + projectId + ")");
    return template
        .query(FIND_PROJECT_SPRINT_STAT_BY_PROJECT_ID, new Object[]{projectId},
            new SprintStatisticMapper());
  }

  @Override
  public UserTaskStatistic findProjectTaskStatisticCountByProjectIdAndPeriod(
      BigInteger projectId, Date startDate, Date endDate) {
    logger.info(
        "Entering findUserTaskCountByProjectIdAndPeriod( "
            + "projectId : " + projectId + ", "
            + "startDate : " + startDate + ", "
            + "endDate : " + endDate + ")");
    return template
            .queryForObject(FIND_PROJECT_TASK_COUNT_BY_PROJECT_ID_AND_PERIOD, new Object[]{projectId, startDate, endDate},
                    new UserTaskStatisticMapper());
  }


  @Override
  public UserTaskStatistic findUserTaskCountByUserIdAndPeriod(BigInteger userId,
      Date startDate, Date endDate) {
    logger.info(
        "Entering findUserTaskCountByUserIdAndPeriod("
            + "userId : " + userId + ", "
            + "startDate : " + startDate + ", "
            + "endDate : " + endDate + ")");
    return template.queryForObject(FIND_USER_TASK_COUNT_BY_USER_ID_AND_PERIOD, new Object[]{userId, startDate, endDate}, new UserTaskStatisticMapper());
  }

  @Override
  public List<WorkingHoursStatistic> findUserWorkingHoursByUserIdAndPeriod(
      BigInteger userId, Date startDate, Date endDate) {
    logger.info(
        "Entering findUserWorkingHoursByUserIdAndPeriod("
            + "userId : " + userId + ", "
            + "startDate : " + startDate + ", "
            + "endDate : " + endDate + ")");
    return template.query(FIND_USER_WORKING_HOURS_BY_USER_ID_AND_PERIOD, new Object[]{userId, startDate, endDate}, new WorkingHoursStatisticMapper());
  }

  @Override
  public WorkPeriodStatistic findWorkPeriodByProjectIdAndStatus(BigInteger projectId) {
    logger.info(
        "Entering findWorkPeriodByProjectIdAndStatus("
            + "projectId : " + projectId + ")");
    return template.queryForObject(FIND_WORK_PERIOD_BY_PROJECT_ID_AND_STATUS, new Object[]{projectId}, new WorkPeriodStatisticMapper());
  }

  @Override
  public List<VacationStatistic> findVacationsByProjectIdAndPeriod(BigInteger projectId,
      Date startDate, Date endDate) {
    logger.info(
        "Entering findVacationsByProjectIdAndPeriod("
            + "projectId : " + projectId + ", "
            + "startDate : " + startDate + ", "
            + "endDate : " + endDate + ")");
    return template
        .query(FIND_VACATION_DAYS_BY_PROJECT_ID_AND_PERIOD, new Object[]{projectId, startDate, endDate},
            new VacationStatisticMapper());
  }
}
