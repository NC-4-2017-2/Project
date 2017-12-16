package main.com.netcracker.project;

import static org.junit.Assert.assertEquals;

import com.netcracker.project.model.entity.BusinessTrip;
import com.netcracker.project.model.entity.Project;
import com.netcracker.project.model.entity.Sprint;
import com.netcracker.project.model.entity.Task;

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

  public static void assertTask(Task expTask, Task actTask){
    assertEquals(expTask.getTaskId(), actTask.getTaskId());
    assertEquals(expTask.getName(), actTask.getName());
    assertEquals(expTask.getTaskType(), actTask.getTaskType());
    assertEquals(expTask.getStartDate(), actTask.getStartDate());
    assertEquals(expTask.getEndDate(), actTask.getEndDate());
    assertEquals(expTask.getPlannedEndDate(), actTask.getPlannedEndDate());
    assertEquals(expTask.getPriority(), actTask.getPriority());
    assertEquals(expTask.getStatus(), actTask.getStatus());
    assertEquals(expTask.getDescription(), actTask.getDescription());
    assertEquals(expTask.getReopenCounter(), actTask.getReopenCounter());
    assertEquals(expTask.getComments(), actTask.getComments());
    assertEquals(expTask.getAuthorId(), actTask.getAuthorId());
    assertEquals(expTask.getUserId(), actTask.getUserId());
    assertEquals(expTask.getProjectId(), actTask.getProjectId());
  }
}
