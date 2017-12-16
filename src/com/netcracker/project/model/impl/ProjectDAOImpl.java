package com.netcracker.project.model.impl;

import com.netcracker.project.model.ProjectDAO;
import com.netcracker.project.model.entity.Project;
import com.netcracker.project.model.entity.Sprint;
import com.netcracker.project.model.impl.mappers.MapperDateConverter;
import com.netcracker.project.model.impl.mappers.SprintMapper;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import com.netcracker.project.model.impl.mappers.ProjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class ProjectDAOImpl implements ProjectDAO {

  private static final Logger logger = Logger.getLogger(ProjectDAOImpl.class);
  private JdbcTemplate template;

  @Autowired
  public void setDataSource(DataSource dataSource) {
    template = new JdbcTemplate(dataSource);
  }

  @Override
  public void createProject(Project project) {
    logger.info("Entering createProject(project_form=" + project + ")");
    MapperDateConverter mdc = new MapperDateConverter();
    template.update(CREATE_PROJECT, new Object[]{project.getProjectId(),
        project.getName(),
        mdc.convertDateToString(project.getStartDate()),
        mdc.convertDateToString(project.getEndDate()),
        project.getProjectStatus().getId(),
        project.getProjectManagerId()});

  }


  @Override
  public Project findProjectByProjectId(BigInteger id) {
    logger.info("Entering findProjectByProjectId(id=" + id + ")");

    Project project = template
        .queryForObject(FIND_PROJECT_BY_PROJECT_ID, new Object[]{id},
            new ProjectMapper());

    addUsersPMSprintToProject(project);

    return project;
  }

  @Override
  public Project findProjectByName(String name) {
    logger.info("Entering findProjectByName(name=" + name + ")");
    Project project = template
        .queryForObject(FIND_PROJECT_BY_NAME, new Object[]{name},
            new ProjectMapper());

    addUsersPMSprintToProject(project);

    return project;
  }

  private void addUsersPMSprintToProject(Project project) {
    logger.info(
        "Entering addUsersPMSprintToProject(project_form=" + project + ")");
    //todo delete List users id and set workPeriods from projectController
    List<BigInteger> idUsers = getIdUsers(project.getProjectId());
    Collection<BigInteger> tasks = getAllTaskIdByProject(
        project.getProjectId());
    Collection<Sprint> sprints = getAllSprints(project.getProjectId());
    BigInteger idPms = getIdPM(project.getProjectId());
    OCStatus status = getStatusById(project.getProjectId());

    if (idUsers.size() != 0) {
      project.setUsersId(idUsers);
    }
    if (idPms != null) {
      project.setProjectManagerId(idPms);
    }
    if (sprints.size() != 0) {
      project.setSprints(sprints);
    }
    if (tasks.size() != 0) {
      project.setTasksId(tasks);
    }
    if (status != null) {
      project.setProjectStatus(status);
    }
  }

  @Override
  public List<Project> findProjectByDate(Date startDate) {
    logger.info("Entering findProjectByDate(startDate=" + startDate + ")");
    MapperDateConverter dateConverter = new MapperDateConverter();
    String formattedDate = dateConverter.convertDateToString(startDate);

    List<BigInteger> projectId = findAllProjectIdFromDate(formattedDate);
    List<Project> projects = new ArrayList<>();
    for (BigInteger aProjectId : projectId) {
      Project project = this.findProjectByProjectId(aProjectId);
      projects.add(project);
    }

    return projects;
  }

  @Override
  public void deleteUserByUserId(BigInteger projectId, BigInteger userId) {
    logger.info(
        "Entering deleteUserByUserId(projectId=" + projectId + "," + " userId="
            + userId + ")");
    template.update(DELETE_USERS_IN_PROJECT, userId, projectId);
  }

  @Override
  public void updateEndDate(BigInteger projectId, Date endDate) {
    logger.info(
        "Entering updateEndDate(projectId=" + projectId + "," + " endDate="
            + endDate + ")");
    template.update(UPDATE_END_DATE, endDate, projectId);
  }

  @Override
  public void updateStatus(BigInteger projectId, OCStatus ocStatus) {
    logger.info(
        "Entering updateStatus(projectId=" + projectId + "," + " ocStatus="
            + ocStatus + ")");
    template.update(UPDATE_STATUS, ocStatus.getId(), projectId);
  }

  @Override
  public void updatePM(BigInteger projectId, BigInteger userId) {
    logger.info(
        "Entering updatePM(projectId=" + projectId + "," + " userId=" + userId
            + ")");
    template.update(UPDATE_PROJECT_PM_ID, userId, projectId);
  }

  @Override
  public void addUser(BigInteger projectId, BigInteger userId) {
    logger.info(
        "Entering addUser(projectId=" + projectId + "," + " userId=" + userId
            + ")");
    template.update(INSERT_USER_TO_PROJECT, projectId, userId);
  }

  @Deprecated
  @Override
  public List<BigInteger> getIdUsers(BigInteger projectId) {
    logger.info("Entering getIdUsers(projectId=" + projectId + ")");
    return template.queryForList(GET_ID_USERS, BigInteger.class, projectId);
  }

  @Override
  public Collection<Sprint> getAllSprints(BigInteger projectId) {
    logger.info("Entering getAllSprints(projectId=" + projectId + ")");
    return template.query(GET_SPRINTS, new SprintMapper(), projectId);
  }

  @Override
  public void createSprint(Sprint sprint, BigInteger projectId) {
    logger.info("Entering createSprint(sprint=" + sprint + "," + " projectId="
        + projectId + ")");
    MapperDateConverter mdc = new MapperDateConverter();
    template.update(CREATE_SPRINT,
        sprint.getSprintId(),
        sprint.getName(),
        mdc.convertDateToString(sprint.getStartDate()),
        mdc.convertDateToString(sprint.getPlannedEndDate()),
        mdc.convertDateToString(sprint.getPlannedEndDate()),
        OCStatus.OPENED.getId(),
        projectId
    );
  }

  @Override
  public void updateSprintStatus(BigInteger sprintId, OCStatus ocStatus) {
    logger.info(
        "Entering updateSprintStatus(sprintId=" + sprintId + "," + " ocStatus="
            + ocStatus + ")");
    template.update(UPDATE_SPRINT_STATUS, ocStatus.getId(), sprintId);
  }

  @Override
  public void updateSprintEndDate(BigInteger sprintId, Date endDate) {
    logger.info(
        "Entering updateSprintEndDate(sprintId=" + sprintId + "," + " endDate="
            + endDate + ")");
    template.update(UPDATE_SPRINT_END_DATE, endDate, sprintId);
  }

  @Override
  public void updateSprintPlannedEndDate(BigInteger sprintId,
      Date plannedEndDate) {
    logger.info(
        "Entering updateSprintPlannedEndDate(sprintId=" + sprintId + ","
            + " plannedEndDate="
            + plannedEndDate + ")");
    template.update(UPDATE_SPRINT_PLANNED_END_DATE, plannedEndDate, sprintId);
  }

  private Collection<BigInteger> getAllTaskIdByProject(BigInteger projectId) {
    logger.info("Entering getAllTaskIdByProject(projectId=" + projectId + ")");
    return template
        .queryForList(GET_TASKS_BY_PROJECT_ID, BigInteger.class, projectId);
  }

  private BigInteger getIdPM(BigInteger id) {
    logger.info("Entering getIdPM(PMId=" + id + ")");
    return BigInteger
        .valueOf(template.queryForObject(GET_PM_ID, Integer.class, id));
  }


  private List<BigInteger> findAllProjectIdFromDate(String formattedDate) {
    logger.info(
        "Entering findAllProjectIdFromDate(formattedDate=" + formattedDate
            + ")");
    List<BigInteger> projectsId = template
        .queryForList(GET_PROJECT_ID_BY_DATE, BigInteger.class,
            formattedDate);

    return projectsId;
  }


  private OCStatus getStatusById(BigInteger projectId) {
    logger.info("Entering getStatusById(projectId=" + projectId + ")");
    return OCStatus.valueOf(template
        .queryForObject(GET_STATUS_BY_PROJECT_ID, String.class, projectId));
  }
}
