package com.netcracker.project.services;

import com.netcracker.project.model.entity.SprintStatistic;
import com.netcracker.project.model.entity.UserTaskStatistic;
import com.netcracker.project.model.entity.VacationStatistic;
import com.netcracker.project.model.entity.WorkPeriodStatistic;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import com.netcracker.project.model.entity.WorkingHoursStatistic;


public interface StatisticService {

  List<SprintStatistic> getProjectSprintStatLineChart(BigInteger projectId);

  UserTaskStatistic getTaskCountByProjectIdAndDatePieChart(BigInteger projectId,
      Date startDate, Date endDate);

  UserTaskStatistic getTaskCountByUserIdPieChart(BigInteger userId,
      Date startDate, Date endDate);

  List<WorkingHoursStatistic> getWorkingHoursByUserId(BigInteger userId,
      Date startDate, Date endDate);

  WorkPeriodStatistic getWorkPeriodByProjectId(BigInteger projectId);

  List<VacationStatistic>  getVacationsByProjectId(BigInteger projectId,
      Date startDate, Date endDate);
}
