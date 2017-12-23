package com.netcracker.project.model.impl;

import com.netcracker.project.model.StatisticDAO;
import com.netcracker.project.model.entity.SprintStatistic;
import com.netcracker.project.model.entity.UserTaskStatistic;
import com.netcracker.project.model.entity.VacationStatistic;
import com.netcracker.project.model.entity.WorkPeriodStatistic;
import com.netcracker.project.model.entity.WorkingHoursStatistic;
import com.netcracker.project.services.impl.DateConverterService;
import java.util.Date;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigInteger;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:Spring-Module.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StatisticDAOImplTest {

  @Autowired
  private StatisticDAO statisticDAO;
  private DateConverterService converter = new DateConverterService();


  @Before
  public void setUp() {
    Locale.setDefault(Locale.ENGLISH);
  }

  @Test
  public void findProjectSprintStatByProjectId() {
    List<SprintStatistic> sprintStatisticList = statisticDAO
        .findProjectSprintStatByProjectId(BigInteger.valueOf(4));
    assertEquals(
        "[SprintStatistic{sprintName='SPRINT1', plannedTakeDays=12, takeDays=11},"
            +
            " SprintStatistic{sprintName='Sprint3', plannedTakeDays=29, takeDays=31},"
            +
            " SprintStatistic{sprintName='Sprint4', plannedTakeDays=39, takeDays=31},"
            +
            " SprintStatistic{sprintName='Sprint5', plannedTakeDays=26, takeDays=31},"
            +
            " SprintStatistic{sprintName='Sprint6', plannedTakeDays=36, takeDays=28}]",
        sprintStatisticList.toString());
  }

  @Test
  public void findProjectTaskStatisticCountByProjectIdAndPeriod() {
    Date startDate = converter.convertStringToDate("11.11.2011");
    Date endDate = converter.convertStringToDate("16.12.2012");
    UserTaskStatistic result = statisticDAO
        .findProjectTaskStatisticCountByProjectIdAndPeriod(
            BigInteger.valueOf(4), startDate, endDate);
    assertEquals("UserTaskStatistic{critical=1, high=2, normal=0, low=0}",
        result.toString());
  }

  @Test
  public void findUserTaskCountByUserIdAndPeriod() {
    Date startDate = converter.convertStringToDate("11.11.2011");
    Date endDate = converter.convertStringToDate("16.12.2012");
    UserTaskStatistic result = statisticDAO
        .findUserTaskCountByUserIdAndPeriod(BigInteger.valueOf(2), startDate,
            endDate);
    assertEquals("UserTaskStatistic{critical=2, high=1, normal=0, low=0}",
        result.toString());
  }

  @Test
  public void findUserWorkingHoursByUserIdAndPeriod() {
    Date startDate = converter.convertStringToDate("12.12.2011");
    Date endDate = converter.convertStringToDate("15.12.2012");
    List<WorkingHoursStatistic> result = statisticDAO
        .findUserWorkingHoursByUserIdAndPeriod(BigInteger.valueOf(2),
            startDate, endDate);
    assertEquals(
        "[WorkingHoursStatistic{userId=2, workingDayDate='2012-12-14 00:00:00.0', hoursCount=8}]",
        result.toString());
  }

  @Test
  public void findWorkPeriodByProjectIdAndStatus() {
    WorkPeriodStatistic workPeriodStatistic = statisticDAO
        .findWorkPeriodByProjectIdAndStatus(BigInteger.valueOf(4));
    assertEquals("WorkPeriodStatistic{working=2, fired=0}",
        workPeriodStatistic.toString());

  }

  @Test
  public void findVacationsByProjectIdAndPeriod() {
    Date startDate = converter.convertStringToDate("16.12.2012");
    Date endDate = converter.convertStringToDate("28.12.2012");
    List<VacationStatistic> result = statisticDAO
        .findVacationsByProjectIdAndPeriod(BigInteger.valueOf(4), startDate,
            endDate);
    assertEquals(
        "[VacationStatistic{userId=2, countDays=10}, VacationStatistic{userId=3, countDays=9}]",
        result.toString());

  }
}
