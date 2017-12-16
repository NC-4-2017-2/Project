package com.netcracker.project.model.impl;

import static com.netcracker.project.model.ProjectDAO.OCStatus.CLOSED;
import static com.netcracker.project.model.ProjectDAO.OCStatus.OPENED;
import static com.netcracker.project.model.entity.Sprint.SprintBuilder;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.netcracker.project.AssertUtils;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.sql.DataSource;
import com.netcracker.project.model.ProjectDAO;
import com.netcracker.project.model.ProjectDAO.OCStatus;
import com.netcracker.project.model.entity.Project;
import com.netcracker.project.model.entity.Project.ProjectBuilder;
import com.netcracker.project.model.entity.Sprint;
import com.netcracker.project.model.impl.mappers.MapperDateConverter;
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
  public void createProjectTest() throws InvocationTargetException {

    MapperDateConverter mp = new MapperDateConverter();
    Date start = mp.convertStringToDate("13.11.14");
    Date end = mp.convertStringToDate("13.11.20");
    Project project = new Project.ProjectBuilder()
        .projectId(BigInteger.valueOf(200))
        .name("Test")
        .startDate(start)
        .endDate(end)
        .build();
    project.setProjectStatus(OCStatus.OPENED);
    project.setProjectManagerId(BigInteger.valueOf(2));

    projectDAO.createProject(project);

    Project result = projectDAO.findProjectByProjectId(BigInteger.valueOf(200));
    assertThat(BigInteger.valueOf(200), is(result.getProjectId()));
    assertThat("Test", is(result.getName()));
    assertThat(start, is(result.getStartDate()));
    assertThat(OPENED, is(result.getProjectStatus()));

    deleteProject();

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
  public void updateEndDateTest() throws InvocationTargetException {
    MapperDateConverter mdc = new MapperDateConverter();
    Date defEndDate = mdc.convertStringToDate("12.12.15");
    Date newEndDate = mdc.convertStringToDate("20.11.10");

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
  }

  @Test
  public void updatePMTest() throws InvocationTargetException {
    projectDAO.updatePM(BigInteger.valueOf(4), BigInteger.valueOf(1));
    Project project = projectDAO.findProjectByProjectId(BigInteger.valueOf(4));
    assertThat(project.getProjectManagerId(), is(BigInteger.valueOf(1)));
  }

  @Test
  public void updateSprintEndDate() {
    MapperDateConverter mdc = new MapperDateConverter();
    Date defEndDate = mdc.convertStringToDate("25.12.12");
    Date newEndDate = mdc.convertStringToDate("05.01.13");

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
  public void updateprintStatus() {

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
    MapperDateConverter mdc = new MapperDateConverter();
    Date startDate = mdc.convertStringToDate("12.12.12");
    Date endDate = mdc.convertStringToDate("12.12.15");

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



