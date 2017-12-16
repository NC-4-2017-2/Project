package com.netcracker.project.services;

import com.netcracker.project.model.entity.SprintStatistic;
import com.netcracker.project.model.entity.UserTaskStatistic;
import com.netcracker.project.model.entity.VacationStatistic;
import com.netcracker.project.model.entity.WorkPeriodStatistic;
import java.math.BigInteger;
import java.util.List;
import com.netcracker.project.model.entity.WorkingHoursStatistic;


public interface StatisticService {

  List<SprintStatistic> getProjectSprintStatLineChart(BigInteger projectId);

  UserTaskStatistic getTaskCountByProjectIdPieChart(BigInteger projectId,
      String startDate, String endDate);

  UserTaskStatistic getTaskCountByUserIdPieChart(BigInteger userId,
      String startDate, String endDate);

  List<WorkingHoursStatistic> getWorkingHoursByUserId(BigInteger userId,
      String startDate, String endDate);

  WorkPeriodStatistic getWorkPeriodByProjectId(BigInteger projectId);

  List<VacationStatistic>  getVacationsByProjectId(BigInteger projectId,
      String startDate, String endDate);
}
