package main.com.netcracker.project.model;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import main.com.netcracker.project.model.UserDAO.UserStatus;
import main.com.netcracker.project.model.entity.ProjectTaskStatistic;
import main.com.netcracker.project.model.entity.SprintStatistic;
import main.com.netcracker.project.model.entity.Status;

public interface StatisticDAO {

  List<SprintStatistic> findProjectSprintStatByProjectId(BigInteger projectId);

  ProjectTaskStatistic findProjectsTasksStatisticsByProjectIdByPeriod(BigInteger projectId,
      Date startDate, Date endDate);

  String findUserTasksStatisticsByUserIdByPeriod(BigInteger userId,
      Date startDate, Date endDate);

  String findUserWorkingHoursByUserIdByPeriod(BigInteger userId,
      Date startDate, Date endDate);

  String findWorkPeriodByProjectIdAndStatus(BigInteger projectId,
      UserStatus status);

  String findVacationsByProjectIdAndStatusAndPeriod(BigInteger projectId,
      Status pmApprove, Status lmApprove, Date startDate, Date endDate);

  static final String FIND_PROJECT_SPRINT_STAT_BY_PROJECT_ID = "SELECT SPRINT_NAME AS NAME, " +
          "TO_DATE(SPRINT_PLANNED_END_DATE,'DD.MM.YY') - TO_DATE(SPRINT_START_DATE,'DD.MM.YY') AS PLANNED_TAKE_DAYS, " +
          "TO_DATE(SPRINT_END_DATE, 'DD.MM.YY') - TO_DATE(SPRINT_START_DATE, 'DD.MM.YY') AS TAKE_DAYS " +
          "FROM PROJECT_SPRINT_STAT " +
          "WHERE PROJECT_ID = ?";
}
