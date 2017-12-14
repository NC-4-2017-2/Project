package main.com.netcracker.project.model.impl;

import main.com.netcracker.project.model.StatisticDAO;
import main.com.netcracker.project.model.entity.UserTaskStatistic;
import main.com.netcracker.project.model.entity.SprintStatistic;
import main.com.netcracker.project.model.entity.VacationStatistic;
import main.com.netcracker.project.model.entity.WorkPeriodStatistic;
import main.com.netcracker.project.model.entity.WorkingHoursStatistic;
import main.com.netcracker.project.model.impl.mappers.SprintStatisticMapper;
import main.com.netcracker.project.model.impl.mappers.UserTaskStatisticMapper;
import main.com.netcracker.project.model.impl.mappers.VacationStatisticMapper;
import main.com.netcracker.project.model.impl.mappers.WorkPeriodStatisticMapper;
import main.com.netcracker.project.model.impl.mappers.WorkingHoursStatisticMapper;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.List;

public class StatisticDAOImpl implements StatisticDAO {

  private Logger logger = Logger.getLogger(UserDAOImpl.class);

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
  public List<UserTaskStatistic> findUserTaskCountByProjectIdAndPeriod(
      BigInteger projectId, String startDate, String endDate) {
    logger.info(
        "Entering findUserTaskCountByProjectIdAndPeriod( "
            + "projectId : " + projectId + ", "
            + "startDate : " + startDate + ", "
            + "endDate : " + endDate + ")");
    return template
            .query(FIND_USER_TASK_COUNT_BY_PROJECT_ID_AND_PERIOD, new Object[]{projectId, startDate, endDate},
                    new UserTaskStatisticMapper());
  }


  @Override
  public UserTaskStatistic findUserTaskCountByUserIdAndPeriod(BigInteger userId,
      String startDate, String endDate) {
    logger.info(
        "Entering findUserTaskCountByUserIdAndPeriod("
            + "userId : " + userId + ", "
            + "startDate : " + startDate + ", "
            + "endDate : " + endDate + ")");
    return template.queryForObject(FIND_USER_TASK_COUNT_BY_USER_ID_AND_PERIOD, new Object[]{userId, startDate, endDate}, new UserTaskStatisticMapper());
  }

  @Override
  public WorkingHoursStatistic findUserWorkingHoursByUserIdAndPeriod(
      BigInteger userId, String startDate, String endDate) {
    logger.info(
        "Entering findUserWorkingHoursByUserIdAndPeriod("
            + "userId : " + userId + ", "
            + "startDate : " + startDate + ", "
            + "endDate : " + endDate + ")");
    return template.queryForObject(FIND_USER_WORKING_HOURS_BY_USER_ID_AND_PERIOD, new Object[]{userId, startDate, endDate}, new WorkingHoursStatisticMapper());
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
      String startDate, String endDate) {
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
