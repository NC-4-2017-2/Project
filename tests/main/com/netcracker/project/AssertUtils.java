package main.com.netcracker.project;

import static org.junit.Assert.assertEquals;

import main.com.netcracker.project.model.entity.BusinessTrip;

public class AssertUtils {

  public static void assertBusinessTrip(BusinessTrip expTrip, BusinessTrip actualTrip) {
    assertEquals(expTrip.getBusinessTripId(), actualTrip.getBusinessTripId());
    assertEquals(expTrip.getCountry(), actualTrip.getCountry());
    assertEquals(expTrip.getEndDate(), actualTrip.getEndDate());
    assertEquals(expTrip.getStartDate(), actualTrip.getStartDate());
    assertEquals(expTrip.getStatus(), actualTrip.getStatus());
    assertEquals(expTrip.getAuthorId(), actualTrip.getAuthorId());
    assertEquals(expTrip.getPmId(), actualTrip.getPmId());
    assertEquals(expTrip.getUserId(), actualTrip.getUserId());
    assertEquals(expTrip.getProjectId(), actualTrip.getProjectId());
  }
}
