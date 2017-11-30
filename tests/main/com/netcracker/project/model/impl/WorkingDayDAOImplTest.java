package main.com.netcracker.project.model.impl;

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
import main.com.netcracker.project.model.WorkingDayDAO;
import main.com.netcracker.project.model.entity.Status;
import main.com.netcracker.project.model.entity.WorkingDay;
import main.com.netcracker.project.model.impl.mappers.MapperDateConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:Spring-Module.xml"})
public class WorkingDayDAOImplTest {

  private ApplicationContext context;
  private WorkingDayDAO workingDay;
  private JdbcTemplate template;
  private DataSource dataSource;
  private WorkingDay workingDayTest1;
  private WorkingDay workingDayTest2;
  private MapperDateConverter converter = new MapperDateConverter();
  private static final String DELETE_FROM_ATTRIBUTES = "DELETE FROM ATTRIBUTES WHERE OBJECT_ID = ?";
  private static final String DELETE_FROM_OBJECTS = "DELETE FROM OBJECTS WHERE OBJECT_ID = ?";
  private static final String DELETE_FROM_OBJREFERENCE = "DELETE FROM OBJREFERENCE WHERE OBJECT_ID = ?";

  @Before
  public void setUP() {
    Locale.setDefault(Locale.ENGLISH);
    context = new ClassPathXmlApplicationContext("Spring-Module.xml");
    workingDay = (WorkingDayDAO) context.getBean("workingDayDAO");
    template = new JdbcTemplate(dataSource);
  }

  @Autowired
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Test
  public void createWorkingDayTest() {
    Date date1 = converter.convertStringToDate("09.09.91");
    Date date2 = converter.convertStringToDate("15.09.91");

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

    workingDay.addHoursPerDay(workingDayTest1);
    workingDay.addHoursPerDay(workingDayTest2);
  }

  @Test
  public void findHoursPerPeriodTest() {
    Date startDate = converter.convertStringToDate("09.05.91");
    Date endDate = converter.convertStringToDate("17.09.91");

    Collection<WorkingDay> hoursPerPeriod = workingDay
        .findHoursPerPeriod(BigInteger.valueOf(1), startDate, endDate);
    Comparator<WorkingDay> comparator = (o1, o2) -> o1.getWorkingDayId().compareTo(o2.getWorkingDayId());
    List<WorkingDay> workingDays = new ArrayList<>(hoursPerPeriod);
    Collections.sort(workingDays, comparator);

    assertEquals("[WorkingDay{workingDayId=44, userId=1, "
        + "date=Mon Sep 09 00:00:00 EEST 1991, weekNumber=35, "
        + "workingHours=10.0, status=DISAPPROVED, pmId=3}, "
        + "WorkingDay{workingDayId=45, userId=1, "
        + "date=Sun Sep 15 00:00:00 EEST 1991, weekNumber=35, "
        + "workingHours=10.0, status=DISAPPROVED, pmId=3}]",
        workingDays.toString());
  }

  @Test
  public void deleteWorkingDayTest() {
    BigInteger id1 = BigInteger.valueOf(44);
    BigInteger id2 = BigInteger.valueOf(45);

    template.update(DELETE_FROM_ATTRIBUTES, new Object[]{id1});
    template.update(DELETE_FROM_OBJECTS, new Object[]{id1});
    template.update(DELETE_FROM_OBJREFERENCE, new Object[]{id1});

    template.update(DELETE_FROM_ATTRIBUTES, new Object[]{id2});
    template.update(DELETE_FROM_OBJECTS, new Object[]{id2});
    template.update(DELETE_FROM_OBJREFERENCE, new Object[]{id2});
  }
}
