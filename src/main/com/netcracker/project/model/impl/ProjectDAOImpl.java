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
import org.springframework.jdbc.core.JdbcTemplate;

public class ProjectDAOImpl implements ProjectDAO {

  private JdbcTemplate template;
  /*
  *Params:
  * id
  * name
  * start
  * end
  * status
  * projectManager
  */
  private static final String CREATE_PROJECT =
      "INSERT ALL "
          + " INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) VALUES (204,NULL,2,'Project'||?,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (14,204,?,NULL,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (15,204,?,NULL,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (16,204,?,NULL,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (17,204,NULL,NULL,0) "
          + " INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (18,204,?)"
          + " SELECT * FROM dual";

  private static final String UPDATE_USERS_IN_PROJECT =
      "INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (19,?,?)";

  private static final String DELETE_USERS_IN_PROJECT =
      "";

  private static final String FIND_PROJECT_BY_PROJECT_ID =
      "SELECT PR.OBJECT_ID AS PROJECT_ID, PR_NAME.VALUE AS NAME, START_DATE.VALUE "
          + "AS START_DATE_PR, END_DATE.VALUE AS END_DATE_PR, "
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
          + "AS START_DATE_PR, END_DATE.VALUE AS END_DATE_PR, "
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

  private static final String GET_ALL_SPRINT = "SELECT "
      + "  REF.OBJECT_ID AS SPRINT_ID, "
      + "  NAME.VALUE AS SPRINT_NAME, "
      + "  START_DATE.VALUE AS SPRINT_START, "
      + "  END_DATE.VALUE AS SPRINT_END, "
      + "  PLANNED_DATE.VALUE AS SPRINT_PLANNED_END, "
      + "  STATUS_VALUE.VALUE AS SPRINT_STATUS "
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


  public void setDataSource(DataSource dataSource) {
    template = new JdbcTemplate(dataSource);
  }

  /*
    private OCStatus projectStatus;
    private Collection<Task> tasks;
    private Collection<Sprint> sprints;
  * */

  @Override

  public void createProject(Project project) {
    template.update(CREATE_PROJECT, new Object[]{project.getProjectId(),
        project.getName(),
        project.getStartDate(),
        project.getEndDate(),
        project.getProjectManager()});
  }

  @Override
  public Project findProjectByProjectId(BigInteger id) {
    List<BigInteger> idUsers = getIdUsers(id);
    Collection<Sprint> sprints = getAllSprints(id);
    BigInteger idPms = getIdPM(id);

    Project project = template
        .queryForObject(FIND_PROJECT_BY_PROJECT_ID, new Object[]{id},
            new ProjectMapper());

    if (idUsers.size() != 0) {
      project.setUsers(idUsers);
    }
    if (idPms != null) {
      project.setProjectManager(idPms);
    }
    if (sprints.size() != 0) {
      project.setSprints(sprints);
    }

    return project;
  }


  //todo add more attributes
  @Override
  public Project findProjectByName(String name) {
    return template.queryForObject(FIND_PROJECT_BY_NAME, new Object[]{name},
        new ProjectMapper());
  }

  //todo add more attributes
  @Override
  public List<Project> findProjectByDate(Date startDate) {
    MapperDateConverter dateConverter = new MapperDateConverter();
    String formattedDate = dateConverter.convertDateTosString(startDate);

    List<BigInteger> projectId = findAllProjectIdFromDate(formattedDate);
    List<Project> projects = new ArrayList<>();
    for (int i = 0; i < projectId.size(); i++) {
      Project project = this.findProjectByProjectId(projectId.get(i));
      projects.add(project);
    }

    return projects;
  }

  @Override
  public void deleteUserByUserId(BigInteger projectId, BigInteger userId) {
    template.update(
        "DELETE FROM OBJREFERENCE WHERE OBJECT_ID = ? AND REFERENCE = ?",
        userId, projectId);
  }

  @Override
  public void addUser(BigInteger projectId, BigInteger userId) {
    template.update(UPDATE_USERS_IN_PROJECT, projectId, userId);
  }

  @Override
  public void updateProject(Project project) {

  }

  @Override
  public List<BigInteger> getIdUsers(BigInteger projectId) {
    return template.queryForList("SELECT USER_PROJ_REF.REFERENCE "
        + "FROM OBJREFERENCE USER_PROJ_REF "
        + "WHERE USER_PROJ_REF.ATTR_ID = 19 AND "
        + "USER_PROJ_REF.OBJECT_ID = " + projectId, BigInteger.class);
  }

  @Override
  public Collection<Sprint> getAllSprints(BigInteger projectId) {
    return template.query(GET_ALL_SPRINT, new SprintMapper(), projectId);
  }

  private BigInteger getIdPM(BigInteger id) {
    return BigInteger
        .valueOf(template.queryForObject("SELECT USER_PROJ_REF.REFERENCE "
            + "FROM OBJREFERENCE USER_PROJ_REF "
            + "WHERE USER_PROJ_REF.ATTR_ID = 18 AND "
            + "USER_PROJ_REF.OBJECT_ID = " + id, Integer.class));
  }


  private List<BigInteger> findAllProjectIdFromDate(String formattedDate) {
    List<BigInteger> projectsId = template
        .queryForList("select PR.OBJECT_ID AS PROJECT_ID "
                + "FROM OBJTYPE PR_TYPE, OBJECTS PR, "
                + "ATTRIBUTES PR_NAME, ATTRIBUTES START_DATE "
                + "where "
                + "PR_TYPE.CODE = 'PROJECT' "
                + "and PR_NAME.ATTR_ID = 14 "
                + "and PR_NAME.OBJECT_ID = PR.OBJECT_ID "
                + "and START_DATE.VALUE = '" + formattedDate + "' "
                + "and START_DATE.ATTR_ID = 15 "
                + "and START_DATE.OBJECT_ID = PR.OBJECT_ID",
            BigInteger.class);

    return projectsId;
  }

}
