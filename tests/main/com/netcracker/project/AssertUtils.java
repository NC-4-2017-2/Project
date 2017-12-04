package main.com.netcracker.project;

import static org.junit.Assert.assertEquals;

import main.com.netcracker.project.model.entity.BusinessTrip;
import main.com.netcracker.project.model.entity.Project;
import main.com.netcracker.project.model.entity.Sprint;

public class AssertUtils {

  public static void assertBusinessTrip(BusinessTrip expTrip,
      BusinessTrip actualTrip) {
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

  public static void assertProject(Project expPr, Project actualPr) {
    assertEquals(expPr.getProjectId(), actualPr.getProjectId());
    assertEquals(expPr.getName(), actualPr.getName());
    assertEquals(expPr.getStartDate(), actualPr.getStartDate());
    assertEquals(expPr.getEndDate(), actualPr.getEndDate());
    assertEquals(expPr.getProjectStatus(), actualPr.getProjectStatus());
    assertEquals(expPr.getProjectManagerId(), actualPr.getProjectManagerId());
  }

  public static void assertSprint(Sprint expSprint, Sprint actSprint) {
    assertEquals(expSprint.getSprintId(), actSprint.getSprintId());
    assertEquals(expSprint.getName(), actSprint.getName());
    assertEquals(expSprint.getStartDate(), actSprint.getStartDate());
    assertEquals(expSprint.getEndDate(), actSprint.getEndDate());
    assertEquals(expSprint.getPlannedEndDate(), actSprint.getPlannedEndDate());
    assertEquals(expSprint.getStatus(), actSprint.getStatus());
  }
}
