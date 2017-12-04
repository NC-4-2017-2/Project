package main.com.netcracker.project.model.impl;

import static main.com.netcracker.project.model.ProjectDAO.OCStatus.CLOSED;
import static main.com.netcracker.project.model.ProjectDAO.OCStatus.OPENED;
import static main.com.netcracker.project.model.entity.Sprint.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.sql.DataSource;
import main.com.netcracker.project.AssertUtils;
import main.com.netcracker.project.model.ProjectDAO;
import main.com.netcracker.project.model.ProjectDAO.OCStatus;
import main.com.netcracker.project.model.entity.Project;
import main.com.netcracker.project.model.entity.Project.ProjectBuilder;
import main.com.netcracker.project.model.entity.Sprint;
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
public class ProjectDaoTest {

  private ApplicationContext context;
  private ProjectDAO projectDAO;
  private DataSource dataSource;
  private JdbcTemplate template;

  private static final String DELETE_FROM_ATTRIBUTES = "DELETE FROM ATTRIBUTES WHERE OBJECT_ID = ?";
  private static final String DELETE_FROM_OBJECTS = "DELETE FROM OBJECTS WHERE OBJECT_ID = ?";

  @Autowired
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Before
  public void setUp() {
    Locale.setDefault(Locale.ENGLISH);
    context = new ClassPathXmlApplicationContext("Spring-Module.xml");
    projectDAO = (ProjectDAO) context.getBean("projectDAO");
    template = new JdbcTemplate(dataSource);
  }

  @Test
  public void findProjectByIDTest() {
    Project actProject = projectDAO
        .findProjectByProjectId(BigInteger.valueOf(4));
    Project expProject = buildTestProject();

    AssertUtils.assertProject(expProject, actProject);
  }


  @Test
  public void findProjectByNameTest() {
    Project actProject = projectDAO.findProjectByName("PROJECT1");
    Project expProject = buildTestProject();

    AssertUtils.assertProject(expProject, actProject);

  }

  @Test
  public void findProjectByDateTest() {
    SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yy");
    Project expProject = buildTestProject();

    Date d = null;
    try {
      d = sdf.parse("12.12.12");
    } catch (ParseException e) {
      e.printStackTrace();
    }

    List<Project> projects = projectDAO.findProjectByDate(d);

    if (projects.size() == 1) {
      AssertUtils.assertProject(expProject, projects.get(0));
    }
    if (projects.size() == 2) {
      assertThat(BigInteger.valueOf(4), is(projects.get(0).getProjectId()));
      assertThat(BigInteger.valueOf(200), is(projects.get(1).getProjectId()));
    }
  }

  @Test
  public void addUserTest() {
    createTestUser(BigInteger.valueOf(140));

    projectDAO.addUser(BigInteger.valueOf(4), BigInteger.valueOf(140));
    List<BigInteger> users = projectDAO.getIdUsers(BigInteger.valueOf(4));
    assertThat(3, is(users.size()));
  }

  @Test
  public void createProjectTest() {

    MapperDateConverter mp = new MapperDateConverter();
    Date start = mp.convertStringToDate("13.11.14");
    Date end = mp.convertStringToDate("13.11.20");
    Project project = new Project.ProjectBuilder()
        .projectId(BigInteger.valueOf(202))
        .name("Test")
        .startDate(start)
        .endDate(end)
        .build();
    project.setProjectStatus(OCStatus.OPENED);
    project.setProjectManagerId(BigInteger.valueOf(140));

    projectDAO.createProject(project);

    Project result = projectDAO.findProjectByProjectId(BigInteger.valueOf(202));
    assertThat(BigInteger.valueOf(202), is(result.getProjectId()));
    assertThat("Test", is(result.getName()));
    assertThat(start, is(result.getStartDate()));
    assertThat(OPENED, is(result.getProjectStatus()));

    deleteProject();

  }

  private void createTestUser(BigInteger bigInteger) {
    template.update(
        "INSERT INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION)"
            + " VALUES (?,NULL,1,'Test Testov Testin',NULL)", bigInteger);

  }

  private void deleteTestUser(BigInteger bigInteger) {
    template.update(
        "DELETE OBJECTS WHERE OBJECT_ID = ?", bigInteger);
  }


  @Test
  public void createSprintTest() {
    MapperDateConverter mp = new MapperDateConverter();
    Date start = mp.convertStringToDate("13.11.14");
    Date plannedEnd = mp.convertStringToDate("10.12.14");
    Date end = mp.convertStringToDate("13.12.14");

    Sprint sprint = new SprintBuilder()
        .sprintId(BigInteger.valueOf(59))
        .name("Sprint3")
        .startDate(start)
        .plannedEndDate(plannedEnd)
        .endDate(end)
        .status(OCStatus.CLOSED)
        .build();

    projectDAO.createSprint(sprint, BigInteger.valueOf(4));
    Collection<Sprint> sprints = projectDAO
        .getAllSprints(BigInteger.valueOf(4));
    assertThat(sprints.size(), is(2));

    deleteSprint();

    sprints = projectDAO.getAllSprints(BigInteger.valueOf(4));
    assertThat(sprints.size(), is(1));
  }


  @Test
  public void getALlSprintsTest() {
    Collection<Sprint> collection = projectDAO
        .getAllSprints(BigInteger.valueOf(5));
    Sprint expSprint = buildTestSprint();

      AssertUtils.assertSprint(expSprint, collection.iterator().next());

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

  @Test
  public void deleteProject() {
    BigInteger bigInteger = BigInteger.valueOf(202);
    template.update(DELETE_FROM_ATTRIBUTES, bigInteger);
    template.update(DELETE_FROM_OBJECTS, bigInteger);
  }

  @Test
  public void deleteSprint() {
    BigInteger sprintId = BigInteger.valueOf(59);

    Collection<Sprint> sprints = projectDAO
        .getAllSprints(BigInteger.valueOf(4));
    template.update(DELETE_FROM_ATTRIBUTES, sprintId);
    template.update(DELETE_FROM_OBJECTS, sprintId);
  }

  @Test
  public void deleteUserTest() {
    projectDAO
        .deleteUserByUserId(BigInteger.valueOf(140), BigInteger.valueOf(4));
    List<BigInteger> users = projectDAO.getIdUsers(BigInteger.valueOf(4));
    assertThat(users.size(), is(2));
    deleteTestUser(BigInteger.valueOf(140));
  }


  private Project buildTestProject() {
    MapperDateConverter mdc = new MapperDateConverter();
    Date startDate = mdc.convertStringToDate("12.12.12");
    Date endDate = mdc.convertStringToDate("10.11.10");

    Project project = new ProjectBuilder()
        .projectId(BigInteger.valueOf(4))
        .name("PROJECT1")
        .startDate(startDate)
        .endDate(endDate)
        .build();

    project.setProjectStatus(CLOSED);
    project.setProjectManagerId(BigInteger.valueOf(1));

    return project;
  }

  private Sprint buildTestSprint() {
    MapperDateConverter mdc = new MapperDateConverter();
    Date startDate = mdc.convertStringToDate("14.12.12");
    Date endDate = mdc.convertStringToDate("25.12.12");
    Date plannedEndDate = mdc.convertStringToDate("26.12.12");

    return new SprintBuilder()
        .sprintId(BigInteger.valueOf(15))
        .name("SPRINT2")
        .startDate(startDate)
        .endDate(endDate)
        .plannedEndDate(plannedEndDate)
        .status(CLOSED)
        .build();
  }
}



