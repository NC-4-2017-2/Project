package main.com.netcracker.project.model.impl;

import static main.com.netcracker.project.model.ProjectDAO.OCStatus.CLOSED;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import main.com.netcracker.project.model.ProjectDAO;
import main.com.netcracker.project.model.ProjectDAO.OCStatus;
import main.com.netcracker.project.model.entity.Project;
import main.com.netcracker.project.model.entity.Sprint;
import main.com.netcracker.project.model.impl.mappers.MapperDateConverter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProjectDaoTest {

  private ApplicationContext context;
  private ProjectDAO projectDAO;

  @Before
  public void setUp() {
    Locale.setDefault(Locale.ENGLISH);
    context = new ClassPathXmlApplicationContext("Spring-Module.xml");
    projectDAO = (ProjectDAO) context.getBean("projectDAO");
  }

  @Test
  public void findProjectByIDTest() {
    Project project = projectDAO.findProjectByProjectId(BigInteger.valueOf(4));
    assertThat(project.getProjectId(), is(BigInteger.valueOf(4)));
    assertThat(project.getProjectManagerId(), is(BigInteger.valueOf(1)));
    assertThat(project.getTasksId().size(), is(2));
  }

  @Test
  public void findProjectByNameTest() {
    Project project = projectDAO.findProjectByName("PROJECT1");
    assertThat("PROJECT1", is(project.getName()));
  }

  @Test
  public void findProjectByDateTest() {
    SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yy");
    Date d = null;
    try {
      d = sdf.parse("12.12.12");
    } catch (ParseException e) {
      e.printStackTrace();
    }

    List<Project> projects = projectDAO.findProjectByDate(d);

    if (projects.size() == 1) {
      assertThat(BigInteger.valueOf(4), is(projects.get(0).getProjectId()));
    }
    if (projects.size() == 2) {
      assertThat(BigInteger.valueOf(4), is(projects.get(0).getProjectId()));
      assertThat(BigInteger.valueOf(200), is(projects.get(1).getProjectId()));
    }
  }

  @Test
  public void addUserTest() {
    //INSERT INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) VALUES (23,NULL,1,'Test User Test',NULL);
    projectDAO.addUser(BigInteger.valueOf(4), BigInteger.valueOf(23));
    List<BigInteger> users = projectDAO.getIdUsers(BigInteger.valueOf(4));
    assertThat(3, is(users.size()));
  }

  @Test
  public void deleteUserTest() {
    projectDAO
        .deleteUserByUserId(BigInteger.valueOf(23), BigInteger.valueOf(4));
    List<BigInteger> users = projectDAO.getIdUsers(BigInteger.valueOf(4));
    assertThat(users.size(), is(2));
  }

  @Test
  public void createProjectTest() {
    MapperDateConverter mp = new MapperDateConverter();
    Date start = mp.convertStringToDate("13.11.14");
    Date end = mp.convertStringToDate("13.11.20");
    Project project = new Project.ProjectBuilder()
        .projectId(BigInteger.valueOf(204))
        .name("Test")
        .startDate(start)
        .endDate(end)
        .build();
    project.setProjectManagerId(BigInteger.ONE);

    projectDAO.createProject(project);

    Project result = projectDAO.findProjectByProjectId(BigInteger.valueOf(204));
    assertThat(BigInteger.valueOf(204), is(result.getProjectId()));
  }

  @Test
  public void createSprintTest() {
    MapperDateConverter mp = new MapperDateConverter();
    Date start = mp.convertStringToDate("13.11.14");
    Date plannedEnd = mp.convertStringToDate("10.12.14");
    Date end = mp.convertStringToDate("13.12.14");

    Sprint sprint = new Sprint.SprintBuilder()
        .sprintId(BigInteger.valueOf(58))
        .name("Sprint3")
        .startDate(start)
        .plannedEndDate(plannedEnd)
        .endDate(end)
        .status(OCStatus.CLOSED)
        .build();

    projectDAO.createSprint(sprint, BigInteger.valueOf(200));
    Collection<Sprint> sprints = projectDAO
        .getAllSprints(BigInteger.valueOf(200));

    assertThat(sprints.size(), is(4));
  }

  @Test
  public void getALlSprintsTest() {
    Collection<Sprint> collection = projectDAO
        .getAllSprints(BigInteger.valueOf(5));

    assertThat(collection.size(), is(3));
  }

  @Test
  public void updateEndDateTest() {
    MapperDateConverter mdc = new MapperDateConverter();
    Date endDate = mdc.convertStringToDate("10.11.10");
    projectDAO.updateEndDate(BigInteger.valueOf(4), endDate);
    Project project = projectDAO.findProjectByProjectId(BigInteger.valueOf(4));
    assertThat(project.getEndDate(), is(endDate));
  }

  @Test
  public void updateStatusTest() {
    projectDAO.updateStatus(BigInteger.valueOf(4), CLOSED);
    Project project = projectDAO.findProjectByProjectId(BigInteger.valueOf(4));
    assertThat(project.getProjectStatus(), is(CLOSED));
  }

  @Test
  public void updatePMTest() {
    projectDAO.updatePM(BigInteger.valueOf(4), BigInteger.valueOf(1));
    Project project = projectDAO.findProjectByProjectId(BigInteger.valueOf(4));
    assertThat(project.getProjectManagerId(), is(BigInteger.valueOf(1)));
  }
}



