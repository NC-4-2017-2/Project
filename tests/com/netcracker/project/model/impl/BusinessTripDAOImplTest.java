package com.netcracker.project.model.impl;

import static org.junit.Assert.assertEquals;

import com.netcracker.project.AssertUtils;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import javax.sql.DataSource;
import com.netcracker.project.model.BusinessTripDAO;
import com.netcracker.project.model.entity.BusinessTrip;
import com.netcracker.project.model.enums.Status;
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
public class BusinessTripDAOImplTest {

  @Autowired
  private BusinessTripDAO businessTrip;
  private JdbcTemplate template;
  @Autowired
  private DataSource dataSource;
  private static final String DELETE_FROM_ATTRIBUTES = "DELETE FROM ATTRIBUTES WHERE OBJECT_ID = ?";
  private static final String DELETE_FROM_OBJECTS = "DELETE FROM OBJECTS WHERE OBJECT_ID = ?";
  private static final String DELETE_FROM_OBJREFERENCE = "DELETE FROM OBJREFERENCE WHERE OBJECT_ID = ?";

  @Before
  public void setUp() {
      Locale.setDefault(Locale.ENGLISH);
      template = new JdbcTemplate(dataSource);
  }

  @Test
  public void test1CreateTrip() {
    BusinessTrip result = getTripForTest();

    businessTrip.createTrip(result);
  }

  @Test
  public void test2UpdateTripAndFindByUserId() {
    BusinessTrip businessTripTestSecond = getBusinessTripForUpdate();

    businessTrip.updateTrip(businessTripTestSecond);

    Collection<BusinessTrip> tripByUserId = businessTrip
        .findTripByUserId(BigInteger.valueOf(1));

    assertEquals(1, tripByUserId.size());

    for (BusinessTrip result : tripByUserId) {
      if (Objects.equals(result.getBusinessTripId(), BigInteger.valueOf(60))) {

        AssertUtils.assertBusinessTrip(getBusinessTripForUpdate(), result);
      }
    }
  }

  @Test
  public void test3FindTripByProjectId() {
    Collection<BusinessTrip> tripByProjectId = businessTrip
        .findTripByProjectId(BigInteger.valueOf(4));

    assertEquals(3, tripByProjectId.size());
    for (BusinessTrip trip : tripByProjectId) {
      if (Objects
          .equals(trip.getBusinessTripId(), BigInteger.valueOf(60))) {
        AssertUtils
            .assertBusinessTrip(getBusinessTripForUpdate(), trip);
      }
    }
  }

  @Test
  public void findTripByPMIdAndStatus() {
    Collection<BusinessTrip> result = businessTrip
        .findTripByPMIdAndStatus(BigInteger.valueOf(1), Status.APPROVED.getId());

    assertEquals(2, result.size());
    for (BusinessTrip trip : result) {
      if (Objects
          .equals(trip.getBusinessTripId(), BigInteger.valueOf(60))) {
        AssertUtils
            .assertBusinessTrip(getBusinessTripForUpdate(), trip);
      }
    }
  }

  @Test
  public void findBusinessTripById() {
    BusinessTrip result = businessTrip.findBusinessTripById(BigInteger.valueOf(8));
    assertEquals("BusinessTrip{businessTripId=8, projectId=4, userId=2, authorId=2, pmId=1, country='USA', startDate=2012-12-13, endDate=2013-02-13, status=APPROVED}",
        result.toString());
  }

  @Test
  public void test4DeleteFromTable() {
    BigInteger id = BigInteger.valueOf(60);

    template.update(DELETE_FROM_OBJREFERENCE, new Object[]{id});
    template.update(DELETE_FROM_ATTRIBUTES, new Object[]{id});
    template.update(DELETE_FROM_OBJECTS, new Object[]{id});
  }

  private BusinessTrip getBusinessTripForUpdate() {
    DateConverterService converter = new DateConverterService();
    Date startDate = converter.convertStringToDate("15.11.11");
    Date endDate = converter.convertStringToDate("25.12.12");

    return new BusinessTrip.BusinessTripBuilder()
        .businessTripId(BigInteger.valueOf(60))
        .country("SLOVAKIA")
        .startDate(startDate)
        .endDate(endDate)
        .status(Status.DISAPPROVED)
        .userId(BigInteger.valueOf(1))
        .authorId(BigInteger.valueOf(2))
        .projectId(BigInteger.valueOf(4))
        .pmId(BigInteger.valueOf(3))
        .build();
  }

  private BusinessTrip getTripForTest() {
    DateConverterService converter = new DateConverterService();
    Date startDate = converter.convertStringToDate("11.11.11");
    Date endDate = converter.convertStringToDate("12.12.12");

    BusinessTrip businessTripTest = new BusinessTrip.BusinessTripBuilder()
        .businessTripId(BigInteger.valueOf(60))
        .country("MOLDOVA")
        .startDate(startDate)
        .endDate(endDate)
        .status(Status.APPROVED)
        .userId(BigInteger.valueOf(1))
        .authorId(BigInteger.valueOf(2))
        .projectId(BigInteger.valueOf(4))
        .pmId(BigInteger.valueOf(3))
        .build();
    return businessTripTest;
  }

  @Test
  public void findTripByUserIdAndStatus() {
    Collection<BusinessTrip> result = businessTrip.findTripByUserIdAndStatus(BigInteger.valueOf(2), Status.APPROVED.getId());
    assertEquals("[BusinessTrip{businessTripId=8, projectId=4, userId=2, authorId=2, pmId=1,"
        + " country='USA', startDate=2012-12-13, endDate=2013-02-13, status=APPROVED}]", result.toString());
  }
}
