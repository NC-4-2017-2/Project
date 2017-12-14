package main.com.netcracker.project.model.impl;

import main.com.netcracker.project.model.StatisticDAO;
import main.com.netcracker.project.model.UserDAO;
import main.com.netcracker.project.model.entity.UserTaskStatistic;
import main.com.netcracker.project.model.entity.SprintStatistic;
import main.com.netcracker.project.model.entity.WorkingHoursStatistic;
import main.com.netcracker.project.model.impl.mappers.SprintStatisticMapper;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.Date;
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
  public UserTaskStatistic findUserTaskCountByProjectIdAndUserIdAndPeriod(
      BigInteger projectId, BigInteger userId, Date startDate, Date endDate) {
    return null;
  }


  @Override
  public UserTaskStatistic findUserTaskCountByUserIdAndPeriod(BigInteger userId,
      Date startDate, Date endDate) {
    return null;
  }

  @Override
  public WorkingHoursStatistic findUserWorkingHoursByUserIdAndPeriod(
      BigInteger userId, Date startDate, Date endDate) {
    return null;
  }

  @Override
  public String findWorkPeriodByProjectIdAndStatus(BigInteger projectId,
      UserDAO.UserStatus status) {
    return null;
  }

  @Override
  public String findVacationsByProjectIdAndPeriod(BigInteger projectId,
      Date startDate, Date endDate) {
    return null;
  }
}
