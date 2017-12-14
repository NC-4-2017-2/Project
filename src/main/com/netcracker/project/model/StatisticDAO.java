package main.com.netcracker.project.model;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import main.com.netcracker.project.model.UserDAO.UserStatus;
import main.com.netcracker.project.model.entity.UserTaskStatistic;
import main.com.netcracker.project.model.entity.SprintStatistic;
import main.com.netcracker.project.model.entity.Status;
import main.com.netcracker.project.model.entity.WorkingHoursStatistic;

public interface StatisticDAO {

  List<SprintStatistic> findProjectSprintStatByProjectId(BigInteger projectId);

  List<UserTaskStatistic> findUserTaskCountByProjectIdAndPeriod(
      BigInteger projectId, String startDate, String endDate);

  UserTaskStatistic findUserTaskCountByUserIdAndPeriod(BigInteger userId,
      String startDate, String endDate);

  WorkingHoursStatistic findUserWorkingHoursByUserIdAndPeriod(BigInteger userId,
      Date startDate, Date endDate);

  String findWorkPeriodByProjectIdAndStatus(BigInteger projectId,
      UserStatus status);

  String findVacationsByProjectIdAndPeriod(BigInteger projectId,
      Date startDate, Date endDate);

  String FIND_PROJECT_SPRINT_STAT_BY_PROJECT_ID =
      "SELECT SPRINT_NAME AS NAME, " +
          "TO_DATE(SPRINT_PLANNED_END_DATE,'DD.MM.YY') - TO_DATE(SPRINT_START_DATE,'DD.MM.YY') AS PLANNED_TAKE_DAYS, " +
          "TO_DATE(SPRINT_END_DATE, 'DD.MM.YY') - TO_DATE(SPRINT_START_DATE, 'DD.MM.YY') AS TAKE_DAYS " +
          "FROM PROJECT_SPRINT_STAT " +
          "WHERE PROJECT_ID = ?";

  String FIND_USER_TASK_COUNT_BY_PROJECT_ID_AND_PERIOD = "SELECT * FROM ( " +
      "SELECT USER_ID, TASK_PRIORITY " +
      "FROM PROJECT_USER_TASK_STAT " +
      "WHERE PROJECT_ID = ? AND " +
      "TO_DATE(START_DATE, 'DD.MM.YY') >= TO_DATE(TO_CHAR(?), 'DD.MM.YY') AND TO_DATE(START_DATE, 'DD.MM.YY') <= TO_DATE(TO_CHAR(?), 'DD.MM.YY')) " +
      "PIVOT " +
      "(COUNT(TASK_PRIORITY) " +
      "FOR TASK_PRIORITY IN ('CRITICAL' AS \"CRITICAL\", 'HIGH' AS \"HIGH\", 'NORMAL' AS \"NORMAL\", 'LOW' AS \"LOW\"))";

  String FIND_USER_TASK_COUNT_BY_USER_ID_AND_PERIOD = "SELECT * FROM (" +
      "SELECT USER_ID, TASK_PRIORITY " +
      "FROM PROJECT_USER_TASK_STAT " +
      "WHERE USER_ID = ? AND " +
      "TO_DATE(START_DATE, 'DD.MM.YY') >= TO_DATE(TO_CHAR(?), 'DD.MM.YY') AND TO_DATE(START_DATE, 'DD.MM.YY') <= TO_DATE(TO_CHAR(?), 'DD.MM.YY')) " +
      "PIVOT " +
      "(COUNT(TASK_PRIORITY) " +
      "FOR TASK_PRIORITY IN ('CRITICAL' AS \"CRITICAL\", 'HIGH' AS \"HIGH\", 'NORMAL' AS \"NORMAL\", 'LOW' AS \"LOW\"))";
}
