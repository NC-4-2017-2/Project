package com.netcracker.project.model.impl;

import static com.netcracker.project.model.entity.Sprint.SprintBuilder;
import static com.netcracker.project.model.enums.OCStatus.CLOSED;
import static com.netcracker.project.model.enums.OCStatus.OPENED;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import com.netcracker.project.AssertUtils;
import com.netcracker.project.model.ProjectDAO;
import com.netcracker.project.model.entity.Project;
import com.netcracker.project.model.entity.Project.ProjectBuilder;
import com.netcracker.project.model.entity.Sprint;
import com.netcracker.project.services.impl.DateConverterService;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.sql.DataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:Spring-Module.xml"})
public class ProjectDaoTest {

  @Autowired
  private ProjectDAO projectDAO;
  @Autowired
  private DataSource dataSource;
  private JdbcTemplate template;

  private static final String DELETE_FROM_ATTRIBUTES = "DELETE FROM ATTRIBUTES WHERE OBJECT_ID = ?";
  private static final String DELETE_FROM_OBJECTS = "DELETE FROM OBJECTS WHERE OBJECT_ID = ?";
  private DateConverterService converter = new DateConverterService();

  @Before
  public void setUp() {
    Locale.setDefault(Locale.ENGLISH);
    template = new JdbcTemplate(dataSource);
  }

  @Test
  public void findProjectByIDTest() throws InvocationTargetException {
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
  public void findProjectByNameIfExistTest() {
    Integer result = projectDAO.findProjectByNameIfExist("lsks");

    assertEquals("0", result.toString());

  }

  @Test
  public void findProjectByDateTest() {
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Project expProject = buildTestProject();

    Date date = null;
    try {
      date = format.parse("2012-12-12");
    } catch (ParseException e) {
      e.printStackTrace();
    }

    List<Project> projects = projectDAO.findProjectByDate(date);

    if (projects.size() == 1) {
      AssertUtils.assertProject(expProject, projects.get(0));
    }
    if (projects.size() == 2) {
      assertThat(BigInteger.valueOf(4), is(projects.get(0).getProjectId()));
      assertThat(BigInteger.valueOf(200), is(projects.get(1).getProjectId()));
    }
  }

  @Test
  public void createProjectTest()
      throws InvocationTargetException, ParseException {
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    Date start = format.parse("2014-11-13");
    Date end = format.parse("2020-11-13");
    Project project = new Project.ProjectBuilder()
        .projectId(BigInteger.valueOf(200))
        .name("Test")
        .startDate(start)
        .endDate(end)
        .projectManagerId(BigInteger.valueOf(2))
        .projectStatus(OPENED)
        .build();

    //projectDAO.createProject(project);

    Project result = projectDAO.findProjectByProjectId(BigInteger.valueOf(200));
    assertThat(BigInteger.valueOf(200), is(result.getProjectId()));
    assertThat("Test", is(result.getName()));
    assertThat(start, is(result.getStartDate()));
    assertThat(OPENED, is(result.getProjectStatus()));

    deleteProject();

  }

  @Test
  public void createSprintTest() throws ParseException {
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Date start = format.parse("2014-11-13");
    Date plannedEnd = format.parse("2014-12-10");
    Date end = format.parse("2014-12-13");

    Sprint sprint = new SprintBuilder()
        .sprintId(BigInteger.valueOf(59))
        .name("Sprint3")
        .startDate(start)
        .plannedEndDate(plannedEnd)
        .endDate(end)
        .status(CLOSED)
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
  public void findAllOpenedProjects() {
    Collection<String> projectNameCollection = projectDAO
        .findAllOpenedProjects();
    assertEquals("[PROJECT1, PROJECT2]", projectNameCollection.toString());
  }

  @Test
  public void updateEndDateTest() throws InvocationTargetException {
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Date defEndDate = null;
    Date newEndDate = null;

    try {
      defEndDate = format.parse("2015-12-12");
      newEndDate = format.parse("2010-11-20");
    } catch (ParseException e) {
      e.printStackTrace();
    }

    Project project = projectDAO.findProjectByProjectId(BigInteger.valueOf(4));
    assertThat(project.getEndDate(), is(defEndDate));

    projectDAO.updateEndDate(BigInteger.valueOf(4), newEndDate);

    project = projectDAO.findProjectByProjectId(BigInteger.valueOf(4));
    assertThat(project.getEndDate(), is(newEndDate));

    projectDAO.updateEndDate(BigInteger.valueOf(4), defEndDate);
  }

  @Test
  public void updateStatusTest() throws InvocationTargetException {
    projectDAO.updateStatus(BigInteger.valueOf(4), CLOSED);
    Project project = projectDAO.findProjectByProjectId(BigInteger.valueOf(4));
    assertThat(project.getProjectStatus(), is(CLOSED));
    projectDAO.updateStatus(BigInteger.valueOf(4), OPENED);

  }

  @Test
  public void updatePMTest() throws InvocationTargetException {
    projectDAO.updatePM(BigInteger.valueOf(4), BigInteger.valueOf(1));
    Project project = projectDAO.findProjectByProjectId(BigInteger.valueOf(4));
    assertThat(project.getProjectManagerId(), is(BigInteger.valueOf(1)));
  }

  @Test
  public void updateSprintEndDate() {
    Date defEndDate = converter.convertStringToDate("25.12.2012");
    Date newEndDate = converter.convertStringToDate("05.01.2013");

    Collection<Sprint> sprints = projectDAO
        .getAllSprints(BigInteger.valueOf(4));
    assertThat(sprints.iterator().next().getEndDate(), is(defEndDate));

    projectDAO.updateSprintEndDate(BigInteger.valueOf(14), newEndDate);

    sprints = projectDAO
        .getAllSprints(BigInteger.valueOf(4));
    assertThat(sprints.iterator().next().getEndDate(), is(newEndDate));

    projectDAO.updateSprintEndDate(BigInteger.valueOf(14), defEndDate);

  }

  @Test
  public void updateSprintStatus() {

  }

  @Test
  public void deleteProject() {
    BigInteger bigInteger = BigInteger.valueOf(200);
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

  private Project buildTestProject() {
    Date startDate = converter.convertStringToDate("12.12.2012");
    Date endDate = converter.convertStringToDate("12.12.2015");

    Project project = new ProjectBuilder()
        .projectId(BigInteger.valueOf(4))
        .name("PROJECT1")
        .startDate(startDate)
        .endDate(endDate)
        .projectManagerId(BigInteger.valueOf(1))
        .projectStatus(OPENED)
        .build();

    return project;
  }

  private Sprint buildTestSprint() {
    DateConverterService mdc = new DateConverterService();
    Date startDate = mdc.convertStringToDate("14.12.2012");
    Date endDate = mdc.convertStringToDate("25.12.2012");
    Date plannedEndDate = mdc.convertStringToDate("26.12.2012");

    return new SprintBuilder()
        .sprintId(BigInteger.valueOf(15))
        .name("SPRINT2")
        .startDate(startDate)
        .endDate(endDate)
        .plannedEndDate(plannedEndDate)
        .status(CLOSED)
        .build();
  }

  @Test
  public void findProjectIdByUserLogin() {
    BigInteger result = projectDAO.findProjectIdByUserLogin("petrov");
    assertEquals(BigInteger.valueOf(4), result);
  }

  @Test
  public void findProjectIdByPMLogin() {
    BigInteger result = projectDAO.findProjectIdByPMLogin("ivanov");
    assertEquals(BigInteger.valueOf(4), result);
  }

  @Test
  public void findProjectByStartDate() {
    Collection<Project> result = projectDAO.findProjectByStartDate(
        converter.convertStringToDateFromJSP("2012-01-01"),
        converter.convertStringToDateFromJSP("2018-01-01"));
    assertEquals(
        "[Project{projectId=1012, name='qwe2', startDate=2017-02-20, endDate=2018-02-20, projectStatus=OPENED, projectManagerId=109},"
            + " Project{projectId=1014, name='qwe3', startDate=2017-02-20, endDate=2018-02-20, projectStatus=OPENED, projectManagerId=110},"
            + " Project{projectId=1010, name='qwe', startDate=2017-01-20, endDate=2018-02-20, projectStatus=OPENED, projectManagerId=109},"
            + " Project{projectId=1017, name='qwe4', startDate=2017-02-20, endDate=2018-02-20, projectStatus=OPENED, projectManagerId=109}]",
        result.toString());
  }

  @Test
  public void findIfProjectExists() {
    Integer result = projectDAO.findIfProjectExists(BigInteger.valueOf(4));
    assertEquals("1", result.toString());
  }

  @Test
  public void findSprintBySprintId() {
    Sprint result = projectDAO
        .findSprintBySprintId(BigInteger.valueOf(1028));
    assertEquals("Sprint{sprintId=1028, name='OP', "
        + "startDate=2017-02-20, plannedEndDate=2018-02-20, "
        + "endDate=2018-02-20, status=OPENED}", result.toString());
  }
}



