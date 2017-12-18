package com.netcracker.project.model;

import com.netcracker.project.model.entity.SprintStatistic;
import com.netcracker.project.model.entity.UserTaskStatistic;
import com.netcracker.project.model.entity.VacationStatistic;
import com.netcracker.project.model.entity.WorkPeriodStatistic;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import com.netcracker.project.model.entity.WorkingHoursStatistic;

public interface StatisticDAO {

  List<SprintStatistic> findProjectSprintStatByProjectId(BigInteger projectId);

  UserTaskStatistic findProjectTaskStatisticCountByProjectIdAndPeriod(
      BigInteger projectId, Date startDate, Date endDate);

  UserTaskStatistic findUserTaskCountByUserIdAndPeriod(BigInteger userId,
      Date startDate, Date endDate);

  List<WorkingHoursStatistic> findUserWorkingHoursByUserIdAndPeriod(BigInteger userId,
      Date startDate, Date endDate);

  WorkPeriodStatistic findWorkPeriodByProjectIdAndStatus(BigInteger projectId);

  List<VacationStatistic> findVacationsByProjectIdAndPeriod(BigInteger projectId,
      Date startDate, Date endDate);

  String FIND_PROJECT_SPRINT_STAT_BY_PROJECT_ID = "SELECT SPRINT_NAME AS NAME, " +
      "SPRINT_PLANNED_END_DATE - SPRINT_START_DATE AS PLANNED_TAKE_DAYS, " +
      "SPRINT_END_DATE- SPRINT_START_DATE AS TAKE_DAYS " +
      "FROM PROJECT_SPRINT_STAT " +
      "WHERE PROJECT_ID = ?";

  String FIND_PROJECT_TASK_COUNT_BY_PROJECT_ID_AND_PERIOD = "SELECT * FROM ( " +
      "SELECT  TASK_PRIORITY " +
      "FROM PROJECT_USER_TASK_STAT " +
      "WHERE PROJECT_ID = ? AND " +
      "START_DATE >= ? AND START_DATE <= ?) " +
      "PIVOT " +
      "(COUNT(TASK_PRIORITY) " +
      "FOR TASK_PRIORITY IN ('CRITICAL' AS \"CRITICAL\", 'HIGH' AS \"HIGH\", 'NORMAL' AS \"NORMAL\", 'LOW' AS \"LOW\"))";

  String FIND_USER_TASK_COUNT_BY_USER_ID_AND_PERIOD = "SELECT * FROM ( " +
      "SELECT TASK_PRIORITY " +
      "FROM PROJECT_USER_TASK_STAT " +
      "WHERE USER_ID = ? AND " +
      "START_DATE >= ? AND START_DATE <= ? ) " +
      "PIVOT " +
      "(COUNT(TASK_PRIORITY) " +
      "FOR TASK_PRIORITY IN ('CRITICAL' AS \"CRITICAL\", 'HIGH' AS \"HIGH\", 'NORMAL' AS \"NORMAL\", 'LOW' AS \"LOW\"))";

  String FIND_USER_WORKING_HOURS_BY_USER_ID_AND_PERIOD = "SELECT USER_ID AS USER_ID, WORKING_DAY_DATE AS WORKING_DATE, HOURS_COUNT AS WORKING_HOURS " +
      "FROM USER_WORKING_HOURS_STAT " +
      "WHERE USER_ID = ? AND " +
      "WORKING_DAY_DATE >= ? AND " +
      "WORKING_DAY_DATE <= ?";

  String FIND_WORK_PERIOD_BY_PROJECT_ID_AND_STATUS = "SELECT * FROM ( " +
      "SELECT STATUS " +
      "FROM WORK_PERIOD_STAT " +
      "WHERE PROJECT_ID = ?) " +
      "PIVOT " +
      "(COUNT(STATUS) " +
      "FOR STATUS IN ('WORKING' AS \"CURRENT_WORKERS\", 'FIRED' AS \"FIRED_WORKERS\"))";

  String FIND_VACATION_DAYS_BY_PROJECT_ID_AND_PERIOD = "SELECT USER_ID AS USER_ID, SUM(END_DATE - START_DATE) AS COUNT_DAYS " +
      "FROM VACATION_STAT " +
      "WHERE PROJECT_ID = ? AND " +
      "START_DATE >= ? AND " +
      "END_DATE <= ? " +
      "GROUP BY USER_ID";
}
