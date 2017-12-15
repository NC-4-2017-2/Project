package main.com.netcracker.project.model;

import java.math.BigInteger;
import java.util.List;
import main.com.netcracker.project.model.entity.ProjectTaskStatistic;
import main.com.netcracker.project.model.entity.UserTaskStatistic;
import main.com.netcracker.project.model.entity.SprintStatistic;
import main.com.netcracker.project.model.entity.VacationStatistic;
import main.com.netcracker.project.model.entity.WorkPeriodStatistic;
import main.com.netcracker.project.model.entity.WorkingHoursStatistic;

public interface StatisticDAO {

  List<SprintStatistic> findProjectSprintStatByProjectId(BigInteger projectId);

  ProjectTaskStatistic findProjectTaskStatisticCountByProjectIdAndPeriod(
      BigInteger projectId, String startDate, String endDate);

  UserTaskStatistic findUserTaskCountByUserIdAndPeriod(BigInteger userId,
      String startDate, String endDate);

  WorkingHoursStatistic findUserWorkingHoursByUserIdAndPeriod(BigInteger userId,
      String startDate, String endDate);

  WorkPeriodStatistic findWorkPeriodByProjectIdAndStatus(BigInteger projectId);

  List<VacationStatistic> findVacationsByProjectIdAndPeriod(BigInteger projectId,
      String startDate, String endDate);

  String FIND_PROJECT_SPRINT_STAT_BY_PROJECT_ID = "SELECT SPRINT_NAME AS NAME, " +
      "TO_DATE(SPRINT_PLANNED_END_DATE,'DD.MM.YY') - TO_DATE(SPRINT_START_DATE,'DD.MM.YY') AS PLANNED_TAKE_DAYS, " +
      "TO_DATE(SPRINT_END_DATE, 'DD.MM.YY') - TO_DATE(SPRINT_START_DATE, 'DD.MM.YY') AS TAKE_DAYS " +
      "FROM PROJECT_SPRINT_STAT " +
      "WHERE PROJECT_ID = ?";

  String FIND_USER_TASK_COUNT_BY_PROJECT_ID_AND_PERIOD = "SELECT * FROM ( " +
      "SELECT  TASK_PRIORITY " +
      "FROM PROJECT_USER_TASK_STAT " +
      "WHERE PROJECT_ID = ? AND " +
      "TO_DATE(START_DATE, 'DD.MM.YY') >= TO_DATE(TO_CHAR(?), 'DD.MM.YY') AND TO_DATE(START_DATE, 'DD.MM.YY') <= TO_DATE(TO_CHAR(?), 'DD.MM.YY')) " +
      "PIVOT " +
      "(COUNT(TASK_PRIORITY) " +
      "FOR TASK_PRIORITY IN ('CRITICAL' AS \"CRITICAL\", 'HIGH' AS \"HIGH\", 'NORMAL' AS \"NORMAL\", 'LOW' AS \"LOW\"))";

  String FIND_USER_TASK_COUNT_BY_USER_ID_AND_PERIOD = "SELECT * FROM ( " +
      "SELECT USER_ID, TASK_PRIORITY " +
      "FROM PROJECT_USER_TASK_STAT " +
      "WHERE USER_ID = ? AND " +
      "TO_DATE(START_DATE, 'DD.MM.YY') >= TO_DATE(TO_CHAR(?), 'DD.MM.YY') AND TO_DATE(START_DATE, 'DD.MM.YY') <= TO_DATE(TO_CHAR(?), 'DD.MM.YY')) " +
      "PIVOT " +
      "(COUNT(TASK_PRIORITY) " +
      "FOR TASK_PRIORITY IN ('CRITICAL' AS \"CRITICAL\", 'HIGH' AS \"HIGH\", 'NORMAL' AS \"NORMAL\", 'LOW' AS \"LOW\"))";

  String FIND_USER_WORKING_HOURS_BY_USER_ID_AND_PERIOD = "SELECT USER_ID AS USER_ID, WORKING_DAY_DATE AS WORKING_DATE, SUM(HOURS_COUNT) AS WORKING_HOURS " +
      "FROM USER_WORKING_HOURS_STAT " +
      "WHERE USER_ID = ? AND " +
      "TO_DATE(WORKING_DAY_DATE, 'DD.MM.YY') >= TO_DATE(TO_CHAR(?), 'DD.MM.YY') AND " +
      "TO_DATE(WORKING_DAY_DATE, 'DD.MM.YY') <= TO_DATE(TO_CHAR(?), 'DD.MM.YY') " +
      "GROUP BY USER_ID, WORKING_DAY_DATE";

  String FIND_WORK_PERIOD_BY_PROJECT_ID_AND_STATUS = "SELECT * FROM ( " +
      "SELECT STATUS " +
      "FROM WORK_PERIOD_STAT " +
      "WHERE PROJECT_ID = ?) " +
      "PIVOT " +
      "(COUNT(STATUS) " +
      "FOR STATUS IN ('WORKING' AS \"CURRENT_WORKERS\", 'FIRED' AS \"FIRED_WORKERS\"))";

  String FIND_VACATION_DAYS_BY_PROJECT_ID_AND_PERIOD = "SELECT USER_ID AS USER_ID, SUM(TO_DATE(END_DATE, 'DD.MM.YY') - TO_DATE(START_DATE, 'DD.MM.YY')) AS COUNT_DAYS " +
      "FROM VACATION_STAT " +
      "WHERE PROJECT_ID = ? AND " +
      "TO_DATE(START_DATE, 'DD.MM.YY') >= TO_DATE(TO_CHAR(?), 'DD.MM.YY') AND " +
      "TO_DATE(END_DATE, 'DD.MM.YY') <= TO_DATE(TO_CHAR(?), 'DD.MM.YY') " +
      "GROUP BY USER_ID";
}
