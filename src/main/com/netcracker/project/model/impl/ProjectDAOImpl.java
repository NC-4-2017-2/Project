package main.com.netcracker.project.model.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import main.com.netcracker.project.model.ProjectDAO;
import main.com.netcracker.project.model.entity.Project;
import main.com.netcracker.project.model.entity.Sprint;
import main.com.netcracker.project.model.impl.mappers.MapperDateConverter;
import main.com.netcracker.project.model.impl.mappers.ProjectMapper;
import main.com.netcracker.project.model.impl.mappers.SprintMapper;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

public class ProjectDAOImpl implements ProjectDAO {

  private Logger logger = Logger.getLogger(ProjectDAOImpl.class);
  private JdbcTemplate template;

  private static final String CREATE_PROJECT =
      "INSERT ALL "
          + " INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) VALUES (202,NULL,2,'Project'||?,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (14,202,?,NULL,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (15,202,?,NULL,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (16,202,?,NULL,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (17,202,NULL,NULL,?) "
          + " INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (18,202,?)"
          + " SELECT * FROM dual";

  private static final String UPDATE_USERS_IN_PROJECT =
      "INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (19,?,?)";

  private static final String DELETE_USERS_IN_PROJECT =
      "DELETE FROM OBJREFERENCE WHERE OBJECT_ID = ? AND REFERENCE = ?";

  private static final String FIND_PROJECT_BY_PROJECT_ID =
      "SELECT PR.OBJECT_ID AS PROJECT_ID, PR_NAME.VALUE AS NAME, START_DATE.VALUE "
          + "AS START_DATE, END_DATE.VALUE AS END_DATE, "
          + "PR_STATUS_VALUE.VALUE AS PROJECT_STATUS "
          + "FROM OBJTYPE PR_TYPE, OBJECTS PR, "
          + "ATTRIBUTES PR_NAME, ATTRIBUTES START_DATE, ATTRIBUTES END_DATE, "
          + "ATTRIBUTES PR_STATUS, "
          + "LISTVALUE PR_STATUS_VALUE "
          + "WHERE PR.OBJECT_ID = ? "
          + "AND PR_TYPE.CODE = 'PROJECT' "
          + "AND PR_NAME.ATTR_ID = 14 "
          + "AND PR_NAME.OBJECT_ID = PR.OBJECT_ID "
          + "AND START_DATE.ATTR_ID = 15 "
          + "AND START_DATE.OBJECT_ID = PR.OBJECT_ID "
          + "AND END_DATE.ATTR_ID = 16 "
          + "AND END_DATE.OBJECT_ID = PR.OBJECT_ID "
          + "AND PR_STATUS.ATTR_ID = 17 "
          + "AND PR_STATUS.OBJECT_ID = PR.OBJECT_ID "
          + "AND PR_STATUS_VALUE.ATTR_ID = 59 "
          + "AND PR_STATUS_VALUE.LIST_VALUE_ID = PR_STATUS.LIST_VALUE_ID";

  private static final String FIND_PROJECT_BY_NAME =
      "SELECT PR.OBJECT_ID AS PROJECT_ID, PR_NAME.VALUE AS NAME, START_DATE.VALUE "
          + "AS START_DATE, END_DATE.VALUE AS END_DATE, "
          + "PR_STATUS_VALUE.VALUE AS PROJECT_STATUS "
          + "FROM OBJTYPE PR_TYPE, OBJECTS PR, "
          + "ATTRIBUTES PR_NAME, ATTRIBUTES START_DATE, ATTRIBUTES END_DATE, "
          + "ATTRIBUTES PR_STATUS, "
          + "LISTVALUE PR_STATUS_VALUE "
          + "WHERE PR_NAME.VALUE = ?"
          + "AND PR_TYPE.CODE = 'PROJECT' "
          + "AND PR_NAME.ATTR_ID = 14 "
          + "AND PR_NAME.OBJECT_ID = PR.OBJECT_ID "
          + "AND START_DATE.ATTR_ID = 15 "
          + "AND START_DATE.OBJECT_ID = PR.OBJECT_ID "
          + "AND END_DATE.ATTR_ID = 16 "
          + "AND END_DATE.OBJECT_ID = PR.OBJECT_ID "
          + "AND PR_STATUS.ATTR_ID = 17 "
          + "AND PR_STATUS.OBJECT_ID = PR.OBJECT_ID "
          + "AND PR_STATUS_VALUE.ATTR_ID = 59 "
          + "AND PR_STATUS_VALUE.LIST_VALUE_ID = PR_STATUS.LIST_VALUE_ID";

  private static final String GET_SPRINTS = "SELECT "
      + "  REF.OBJECT_ID AS SPRINT_ID, "
      + "  NAME.VALUE AS NAME, "
      + "  START_DATE.VALUE AS START_DATE, "
      + "  END_DATE.VALUE AS END_DATE, "
      + "  PLANNED_DATE.VALUE AS PLANNED_END_DATE, "
      + "  STATUS_VALUE.VALUE AS STATUS "
      + "FROM OBJREFERENCE REF, OBJECTS PROJECT, "
      + "  ATTRIBUTES NAME, ATTRIBUTES START_DATE, ATTRIBUTES END_DATE, "
      + "  ATTRIBUTES PLANNED_DATE, ATTRIBUTES STATUS, "
      + "  LISTVALUE STATUS_VALUE "
      + "WHERE PROJECT.OBJECT_ID = ? "
      + "      AND REF.ATTR_ID = 60 "
      + "      AND REF.REFERENCE = PROJECT.OBJECT_ID "
      + "      AND NAME.ATTR_ID = 55 "
      + "      AND NAME.OBJECT_ID = REF.OBJECT_ID "
      + "      AND START_DATE.ATTR_ID = 56 "
      + "      AND START_DATE.OBJECT_ID = REF.OBJECT_ID "
      + "      AND END_DATE.ATTR_ID = 57 "
      + "      AND END_DATE.OBJECT_ID = REF.OBJECT_ID "
      + "      AND PLANNED_DATE.ATTR_ID = 58 "
      + "      AND PLANNED_DATE.OBJECT_ID = REF.OBJECT_ID "
      + "      AND STATUS.ATTR_ID = 59 "
      + "      AND STATUS.OBJECT_ID = REF.OBJECT_ID "
      + "      AND STATUS_VALUE.ATTR_ID = 59 "
      + "      AND STATUS_VALUE.LIST_VALUE_ID = STATUS.LIST_VALUE_ID";

  private static final String CREATE_SPRINT =
      "INSERT ALL "
          + " INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) VALUES (58,NULL,7,'Sprint'||58,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (55,58,?,NULL,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (56,58,?,NULL,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (57,58,?,NULL,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (58,58,?,NULL,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (59,58,NULL,NULL,?) "
          + " INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (60,58,?) "
          + " SELECT * FROM dual";


  private static final String GET_ID_USERS = "SELECT USER_PROJ_REF.REFERENCE "
      + "FROM OBJREFERENCE USER_PROJ_REF "
      + "WHERE USER_PROJ_REF.ATTR_ID = 19 AND "
      + "USER_PROJ_REF.OBJECT_ID = ?";

  private static final String GET_PM_ID = "SELECT USER_PROJ_REF.REFERENCE "
      + "FROM OBJREFERENCE USER_PROJ_REF "
      + "WHERE USER_PROJ_REF.ATTR_ID = 18 AND "
      + "USER_PROJ_REF.OBJECT_ID = ?";

  private static final String GET_PROJECT_ID_BY_DATE =
      "SELECT PR.OBJECT_ID AS PROJECT_ID "
          + "FROM OBJTYPE PR_TYPE, OBJECTS PR, "
          + "ATTRIBUTES PR_NAME, ATTRIBUTES START_DATE "
          + "WHERE "
          + "PR_TYPE.CODE = 'PROJECT' "
          + "AND PR_NAME.ATTR_ID = 14 "
          + "AND PR_NAME.OBJECT_ID = PR.OBJECT_ID "
          + "AND START_DATE.VALUE = ? "
          + "AND START_DATE.ATTR_ID = 15 "
          + "AND START_DATE.OBJECT_ID = PR.OBJECT_ID";

  private static final String GET_TASKS_BY_PROJECT_ID =
      "SELECT OBJECT_ID FROM OBJREFERENCE "
          + "WHERE ATTR_ID = 32 "
          + "AND REFERENCE = ?";
  private static final String UPDATE_END_DATE =
      "UPDATE ATTRIBUTES SET VALUE = to_char(?, 'dd.mm.yy') WHERE ATTR_ID = 16 AND OBJECT_ID = ?";

  private static final String UPDATE_STATUS =
      "UPDATE ATTRIBUTES SET LIST_VALUE_ID = ? "
          + "WHERE ATTR_ID = 17 AND OBJECT_ID = ? ";
  private static final String GET_STATUS_BY_PROJECT_ID =
      "SELECT "
          + "  STATUS.VALUE "
          + "FROM ATTRIBUTES STATUS_VALUE, LISTVALUE STATUS, OBJECTS PROJECT "
          + "WHERE "
          + "  PROJECT.OBJECT_ID = ? "
          + "  AND PROJECT.OBJECT_TYPE_ID = 2 "
          + "  AND STATUS_VALUE.ATTR_ID = 17 "
          + "  AND STATUS_VALUE.OBJECT_ID = PROJECT.OBJECT_ID "
          + "  AND STATUS.LIST_VALUE_ID = STATUS_VALUE.LIST_VALUE_ID "
          + "  AND STATUS.ATTR_ID = 59";

  private static final String UPDATE_PROJECT_PM_ID =
      "UPDATE OBJREFERENCE "
          + "SET REFERENCE = ? "
          + "WHERE ATTR_ID = 18 AND OBJECT_ID = ?";

  public void setDataSource(DataSource dataSource) {
    template = new JdbcTemplate(dataSource);
  }

  @Override
  public void createProject(Project project) {
    logger.info("Entering createProject(project=" + project + ")");
    MapperDateConverter mdc = new MapperDateConverter();
    template.update(CREATE_PROJECT, new Object[]{project.getProjectId(),
        project.getName(),
        mdc.convertDateTosString(project.getStartDate()),
        mdc.convertDateTosString(project.getEndDate()),
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


  //todo add more attributes
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
    logger.info("Entering addUsersPMSprintToProject(project=" + project + ")");
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


  //todo add more attributes
  @Override
  public List<Project> findProjectByDate(Date startDate) {
    logger.info("Entering findProjectByDate(startDate=" + startDate + ")");
    MapperDateConverter dateConverter = new MapperDateConverter();
    String formattedDate = dateConverter.convertDateTosString(startDate);

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
    template.update(UPDATE_USERS_IN_PROJECT, projectId, userId);
  }


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
        //sprint.getSprintId(),
        sprint.getName(),
        mdc.convertDateTosString(sprint.getStartDate()),
        mdc.convertDateTosString(sprint.getPlannedEndDate()),
        mdc.convertDateTosString(sprint.getEndDate()),
        sprint.getStatus().getId(),
        projectId
    );
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
