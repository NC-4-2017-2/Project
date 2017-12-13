package main.com.netcracker.project.model;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import main.com.netcracker.project.model.UserDAO.UserStatus;
import main.com.netcracker.project.model.entity.SprintStatistic;
import main.com.netcracker.project.model.entity.Status;

public interface StatisticDAO {

  List<SprintStatistic> findProjectSprintStatByProjectId(BigInteger projectId);

  String findProjectsTasksStatisticsByProjectIdByPeriod(BigInteger projectId,
      Date startDate, Date endDate);

  String findUserTasksStatisticsByUserIdByPeriod(BigInteger userId,
      Date startDate, Date endDate);

  String findUserWorkingHoursByUserIdByPeriod(BigInteger userId,
      Date startDate, Date endDate);

  String findWorkPeriodByProjectIdAndStatus(BigInteger projectId,
      UserStatus status);

  String findVacationsByProjectIdAndStatusAndPeriod(BigInteger projectId,
      Status pmApprove, Status lmApprove, Date startDate, Date endDate);
}
