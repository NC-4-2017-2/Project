package com.netcracker.project.model.impl;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.sql.DataSource;
import com.netcracker.project.model.WorkingDayDAO;
import com.netcracker.project.model.enums.Status;
import com.netcracker.project.model.entity.WorkingDay;
import com.netcracker.project.services.impl.DateConverterService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:Spring-Module.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WorkingDayDAOImplTest {

  @Autowired
  private WorkingDayDAO workingDayDAO;
  private JdbcTemplate template;
  @Autowired
  private DataSource dataSource;
  private WorkingDay workingDayTest1;
  private WorkingDay workingDayTest2;
  private DateConverterService converter = new DateConverterService();
  private static final String DELETE_FROM_ATTRIBUTES = "DELETE FROM ATTRIBUTES WHERE OBJECT_ID = ?";
  private static final String DELETE_FROM_OBJECTS = "DELETE FROM OBJECTS WHERE OBJECT_ID = ?";
  private static final String DELETE_FROM_OBJREFERENCE = "DELETE FROM OBJREFERENCE WHERE OBJECT_ID = ?";

  @Before
  public void setUp() {
    Locale.setDefault(Locale.ENGLISH);
    template = new JdbcTemplate(dataSource);
  }

  @Autowired
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Test
  public void test1CreateWorkingDay() {
    Date date1 = converter.convertStringToDate("09.09.1991");
    Date date2 = converter.convertStringToDate("15.09.1991");

    workingDayTest1 = new WorkingDay.WorkingDayBuilder()
        .workingDayId(BigInteger.valueOf(44))
        .workingHours(10.0)
        .date(date1)
        .pmId(BigInteger.valueOf(3))
        .userId(BigInteger.valueOf(1))
        .weekNumber(35)
        .status(Status.DISAPPROVED)
        .build();

    workingDayTest2 = new WorkingDay.WorkingDayBuilder()
        .workingDayId(BigInteger.valueOf(45))
        .workingHours(10.0)
        .date(date2)
        .pmId(BigInteger.valueOf(3))
        .userId(BigInteger.valueOf(1))
        .weekNumber(35)
        .status(Status.DISAPPROVED)
        .build();

    workingDayDAO.createWorkingDay(workingDayTest1);
    workingDayDAO.createWorkingDay(workingDayTest2);
  }

  @Test
  public void test2findHoursPerPeriod() {
    Date startDate = converter.convertStringToDate("09.05.1991");
    Date endDate = converter.convertStringToDate("17.09.1991");

    Collection<WorkingDay> hoursPerPeriod = workingDayDAO
        .findWorkingDayPerPeriod(BigInteger.valueOf(1), startDate, endDate);
    Comparator<WorkingDay> comparator = (o1, o2) -> o1.getWorkingDayId()
        .compareTo(o2.getWorkingDayId());
    List<WorkingDay> workingDays = new ArrayList<>(hoursPerPeriod);
    Collections.sort(workingDays, comparator);

    assertEquals("[WorkingDay{workingDayId=44, "
            + "userId=1, date=1991-09-09, weekNumber=35, workingHours=10.0, "
            + "status=DISAPPROVED, pmId=3}, WorkingDay{workingDayId=45, userId=1, "
            + "date=1991-09-15, weekNumber=35, workingHours=10.0, status=DISAPPROVED, "
            + "pmId=3}]",
        workingDays.toString());
  }

  @Test
  public void findWorkingDayByUserIdAndDate() {
    Date wdDay = converter.convertStringToDate("14.12.2012");
    WorkingDay result = workingDayDAO
        .findWorkingDayByUserIdAndDate(BigInteger.valueOf(2), wdDay);
    assertEquals("WorkingDay{workingDayId=12, userId=2, date=2012-12-14, weekNumber=48, workingHours=8.0, status=APPROVED, pmId=1}", result.toString());
  }

  @Test
  public void findWorkingDayByPMIdAndStatus() {
    Collection<WorkingDay> result = workingDayDAO
        .findWorkingDayByPMIdAndStatus(BigInteger.valueOf(1), Status.APPROVED.getId());
    assertEquals("[WorkingDay{workingDayId=13, userId=3, date=2012-12-12, weekNumber=5, workingHours=8.0, status=APPROVED, pmId=1},"
        + " WorkingDay{workingDayId=12, userId=2, date=2012-12-14, weekNumber=48, workingHours=8.0, status=APPROVED, pmId=1}]", result.toString());
  }

  @Test
  public void updateWorkingDayStatus() {
    workingDayDAO.updateWorkingDayStatus(BigInteger.valueOf(1010), 0);
  }

  @Test
  public void updateWorkingHours() {
    workingDayDAO.updateWorkingHours(BigInteger.valueOf(12), 1.1);
  }

  @Test
  public void findWorkingDayById() {
    WorkingDay result = workingDayDAO.findWorkingDayById(BigInteger.valueOf(12));
    assertEquals("WorkingDay{workingDayId=12, userId=2, date=2012-12-14, weekNumber=48, workingHours=13.1, status=APPROVED, pmId=1}", result.toString());
  }

  @Test
  public void findWorkingDayByUserIdAndStatus() {
    Collection<WorkingDay> result = workingDayDAO
        .findWorkingDayByUserIdAndStatus(BigInteger.valueOf(2), 0);
    assertEquals("[WorkingDay{workingDayId=12, userId=2, date=2012-12-14, weekNumber=48, workingHours=13.1, status=APPROVED, pmId=1}]", result.toString());
  }

  @Test
  public void test3DeleteWorkingDay() {
    BigInteger id1 = BigInteger.valueOf(1002);
    BigInteger id2 = BigInteger.valueOf(1003);

    template.update(DELETE_FROM_ATTRIBUTES, new Object[]{id1});
    template.update(DELETE_FROM_OBJREFERENCE, new Object[]{id1});
    template.update(DELETE_FROM_OBJECTS, new Object[]{id1});

    template.update(DELETE_FROM_ATTRIBUTES, new Object[]{id2});
    template.update(DELETE_FROM_OBJREFERENCE, new Object[]{id2});
    template.update(DELETE_FROM_OBJECTS, new Object[]{id2});
  }
}
