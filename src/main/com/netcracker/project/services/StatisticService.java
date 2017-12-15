package main.com.netcracker.project.services;

import java.math.BigInteger;
import java.util.List;
import main.com.netcracker.project.model.entity.ProjectTaskStatistic;
import main.com.netcracker.project.model.entity.SprintStatistic;
import main.com.netcracker.project.model.entity.UserTaskStatistic;
import main.com.netcracker.project.model.entity.VacationStatistic;
import main.com.netcracker.project.model.entity.WorkPeriodStatistic;
import main.com.netcracker.project.model.entity.WorkingHoursStatistic;


public interface StatisticService {

  List<SprintStatistic> getProjectSprintStatLineChart(BigInteger projectId);

  ProjectTaskStatistic getTaskCountByProjectIdPieChart(BigInteger projectId,
      String startDate, String endDate);

  UserTaskStatistic getTaskCountByUserIdPieChart(BigInteger userId,
      String startDate, String endDate);

  WorkingHoursStatistic getWorkingHoursByUserId(BigInteger userId,
      String startDate, String endDate);

  WorkPeriodStatistic getWorkPeriodByProjectId(BigInteger projectId);

  List<VacationStatistic>  getVacationsByProjectId(BigInteger projectId,
      String startDate, String endDate);
}
