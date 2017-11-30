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
import main.com.netcracker.project.model.BusinessTripDAO;
import main.com.netcracker.project.model.entity.BusinessTrip;
import main.com.netcracker.project.model.entity.Status;
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
public class BusinessTripDAOImplTest {

  private ApplicationContext context;
  private BusinessTripDAO businessTrip;
  private JdbcTemplate template;
  private DataSource dataSource;
  private BusinessTrip businessTripTest;
  private static final String DELETE_FROM_ATTRIBUTES = "DELETE FROM ATTRIBUTES WHERE OBJECT_ID = ?";
  private static final String DELETE_FROM_OBJECTS = "DELETE FROM OBJECTS WHERE OBJECT_ID = ?";
  private static final String DELETE_FROM_OBJREFERENCE = "DELETE FROM OBJREFERENCE WHERE OBJECT_ID = ?";


  @Before
  public void setUp() {
    Locale.setDefault(Locale.ENGLISH);
    context = new ClassPathXmlApplicationContext("Spring-Module.xml");
    businessTrip = (BusinessTripDAO) context.getBean("businessTripDAO");
    template = new JdbcTemplate(dataSource);
  }

  @Autowired
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Test
  public void createTripTest() {
    MapperDateConverter converter = new MapperDateConverter();
    Date startDate = converter.convertStringToDate("11.11.11");
    Date endDate = converter.convertStringToDate("12.12.12");

    businessTripTest = new BusinessTrip.BusinessTripBuilder()
        .businessTripId(BigInteger.valueOf(56))
        .country("MOLDOVA")
        .startDate(startDate)
        .endDate(endDate)
        .status(Status.APPROVED)
        .userId(BigInteger.valueOf(1))
        .authorId(BigInteger.valueOf(2))
        .projectId(BigInteger.valueOf(4))
        .pmId(BigInteger.valueOf(3))
        .build();

    businessTrip.createTrip(businessTripTest);
  }


  @Test
  public void updateTripAndFindByUserIdTest() {
    MapperDateConverter converter = new MapperDateConverter();
    Date startDate = converter.convertStringToDate("15.11.11");
    Date endDate = converter.convertStringToDate("25.12.12");

    BusinessTrip businessTripTestSecond = new BusinessTrip.BusinessTripBuilder()
        .businessTripId(BigInteger.valueOf(56))
        .country("SLOVAKIA")
        .startDate(startDate)
        .endDate(endDate)
        .status(Status.DISAPPROVED)
        .userId(BigInteger.valueOf(1))
        .authorId(BigInteger.valueOf(2))
        .projectId(BigInteger.valueOf(4))
        .pmId(BigInteger.valueOf(3))
        .build();

    businessTrip.updateTrip(businessTripTestSecond);

    Collection<BusinessTrip> tripByUserId = businessTrip
        .findTripByUserId(BigInteger.valueOf(56));

    assertEquals("[BusinessTrip{businessTripId=56, projectId=4, "
        + "userId=1, authorId=2, pmId=3, country='SLOVAKIA', "
        + "startDate=Thu Jan 15 00:11:00 EET 11, "
        + "endDate=Mon Jan 25 00:12:00 EET 12, status=DISAPPROVED}]",
        tripByUserId.toString());
  }

  @Test
  public void findTripByProjectId() {
    Collection<BusinessTrip> tripByProjectId = businessTrip
        .findTripByProjectId(BigInteger.valueOf(4));
    Comparator<BusinessTrip> trip = (o1, o2) -> o1.getUserId().compareTo(o2.getUserId());
    List<BusinessTrip> tripList = new ArrayList<>(tripByProjectId);
    Collections.sort(tripList, trip);

    assertEquals("[BusinessTrip{businessTripId=56, projectId=4, "
        + "userId=1, authorId=2, pmId=3, country='MOLDOVA', "
        + "startDate=Sun Jan 11 00:11:00 EET 11, "
        + "endDate=Tue Jan 12 00:12:00 EET 12, status=APPROVED}, "
        + "BusinessTrip{businessTripId=50, projectId=4, userId=1, "
        + "authorId=2, pmId=3, country='UKRAINE', "
        + "startDate=Thu Jan 28 00:10:00 EET 17, "
        + "endDate=Sun Jan 10 00:11:00 EET 17, status=APPROVED}, "
        + "BusinessTrip{businessTripId=9, projectId=4, userId=3, "
        + "authorId=3, pmId=1, country='Switzerland', "
        + "startDate=Wed Jan 13 00:12:00 EET 12, "
        + "endDate=Fri Jan 13 00:02:00 EET 13, status=APPROVED}, "
        + "BusinessTrip{businessTripId=8, projectId=4, userId=2, "
        + "authorId=2, pmId=1, country='USA', "
        + "startDate=Wed Jan 13 00:12:00 EET 12, "
        + "endDate=Fri Jan 13 00:02:00 EET 13, status=APPROVED}]",
        tripByProjectId.toString());
  }

  @Test
  public void deleteFromTable() {
    BigInteger id = BigInteger.valueOf(56);

    template.update(DELETE_FROM_ATTRIBUTES, new Object[]{id});
    template.update(DELETE_FROM_OBJECTS, new Object[]{id});
    template.update(DELETE_FROM_OBJREFERENCE, new Object[]{id});
  }
}
