package main.com.netcracker.project.model.impl;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import main.com.netcracker.project.model.VacationDAO;
import main.com.netcracker.project.model.entity.Status;
import main.com.netcracker.project.model.entity.Vacation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:Spring-Module.xml"})
public class VacationDAOImplTest {

  private ApplicationContext context;
  private VacationDAO vacationDAO;

  @Before
  public void setUp() {
    Locale.setDefault(Locale.ENGLISH);
    context = new ClassPathXmlApplicationContext("Spring-Module.xml");
    vacationDAO = (VacationDAO) context.getBean("vacationDAO");
  }

  @Test
  public void findVacationByUserIdAndPmStatusTest() {
    Collection<Vacation> vacationByUserIdAndPmStatus = vacationDAO
        .findVacationByUserIdAndPmStatus(BigInteger.valueOf(2),
            Status.APPROVED.getId());

    Comparator<Vacation> vacationComparator = (o1, o2) -> o1.getVacationId()
        .compareTo(o2.getVacationId());
    List<Vacation> vacations = new ArrayList<>(vacationByUserIdAndPmStatus);
    Collections.sort(vacations, vacationComparator);

    assertEquals("[Vacation{vacationId=10, userId=2, projectId=4, "
        + "startDate=Mon Dec 17 00:00:00 EET 2012, endDate=Thu Dec 27 00:00:00 EET 2012, "
        + "pmStatus=APPROVED, lmStatus=APPROVED, pmId=1, lmId=3}]",
        vacations.toString());
  }

  @Test
  public void findVacationByUserIdAndLmStatusTest() {
    Collection<Vacation> vacationByUserIdAndLmStatus = vacationDAO
        .findVacationByUserIdAndLmStatus(BigInteger.valueOf(2),
            Status.APPROVED.getId());

    Comparator<Vacation> vacationComparator = (o1, o2) -> o1.getVacationId()
        .compareTo(o2.getVacationId());
    List<Vacation> vacations = new ArrayList<>(vacationByUserIdAndLmStatus);
    Collections.sort(vacations, vacationComparator);

    assertEquals("[Vacation{vacationId=10, userId=2, projectId=4, "
        + "startDate=Mon Dec 17 00:00:00 EET 2012, endDate=Thu Dec 27 00:00:00 EET 2012, "
        + "pmStatus=APPROVED, lmStatus=APPROVED, pmId=1, lmId=3}]",
        vacations.toString());
  }

}
