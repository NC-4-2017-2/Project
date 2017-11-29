package main.com.netcracker.project.model.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import main.com.netcracker.project.model.ProjectDAO;
import main.com.netcracker.project.model.entity.Project;
import main.com.netcracker.project.model.impl.mappers.MapperDateConverter;
import main.com.netcracker.project.model.impl.mappers.ProjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class ProjectDAOImpl implements ProjectDAO {

  private JdbcTemplate jdbcTemplate;
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


  public void setDataSource(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
  }

  /*
    private OCStatus projectStatus;
    private Collection<Task> tasks;
    private Collection<Sprint> sprints;
  * */

  @Override

  public void createProject(Project project) {
    jdbcTemplate.update(CREATE_PROJECT, new Object[]{project.getProjectId(),
            project.getName(),
            project.getStartDate(),
            project.getEndDate(),
            //project.getProjectStatus(),
            project.getProjectManager()});

  }

  @Override
  public Project findProjectByProjectId(BigInteger id) {
    List<BigInteger> idUsers = getIdUsers(id);
    BigInteger idPms = getIdPM(id);

    Project project = jdbcTemplate
        .queryForObject(FIND_PROJECT_BY_PROJECT_ID, new Object[]{id},
            new ProjectMapper());

    project.setUsers(idUsers);
    project.setProjectManager(idPms);

    return project;
  }


  @Override
  public Project findProjectByName(String name) {
    return jdbcTemplate.queryForObject(FIND_PROJECT_BY_NAME, new Object[]{name},
        new ProjectMapper());
  }

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
  public void deleteUserByUserId(BigInteger userId, BigInteger projectID) {

  }

  @Override
  public void addUser(BigInteger userId, BigInteger projectId) {

  }

  @Override
  public void updateProject(Project project) {

  }

  private List<BigInteger> getIdUsers(BigInteger id) {
    return jdbcTemplate.queryForList("SELECT USER_PROJ_REF.REFERENCE "
        + "FROM OBJREFERENCE USER_PROJ_REF "
        + "WHERE USER_PROJ_REF.ATTR_ID = 19 AND "
        + "USER_PROJ_REF.OBJECT_ID = " + id, BigInteger.class);
  }

  private BigInteger getIdPM(BigInteger id) {
    return BigInteger
        .valueOf(jdbcTemplate.queryForObject("SELECT USER_PROJ_REF.REFERENCE "
            + "FROM OBJREFERENCE USER_PROJ_REF "
            + "WHERE USER_PROJ_REF.ATTR_ID = 18 AND "
            + "USER_PROJ_REF.OBJECT_ID = " + id, Integer.class));
  }


  private List<BigInteger> findAllProjectIdFromDate(String formattedDate) {
    List<BigInteger> projectsId = jdbcTemplate
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
