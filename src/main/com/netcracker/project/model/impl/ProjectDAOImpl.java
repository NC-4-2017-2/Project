package main.com.netcracker.project.model.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import main.com.netcracker.project.model.ProjectDAO;
import main.com.netcracker.project.model.entity.Project;
import main.com.netcracker.project.model.entity.User;
import main.com.netcracker.project.model.impl.mappers.ProjectMapper;
import main.com.netcracker.project.model.impl.mappers.UserMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class ProjectDAOImpl implements ProjectDAO {

  private JdbcTemplate jdbcTemplate;

  private static final String CREATE_PROJECT =
      "INSERT ALL "
          + " INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) VALUES (200,NULL,2,?,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (14,200,?,NULL,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (15,200,?,NULL,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (16,200,?,NULL,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (17,200,NULL,NULL,?) "
          + "SELECT * FORM dual";

  private static final String FIND_PROJECT_BY_PROJECT_ID =
      "select PR.OBJECT_ID AS PROJECT_ID, PR_NAME.VALUE AS NAME, START_DATE.VALUE "
          + "AS START_DATE_PR, END_DATE.VALUE AS END_DATE_PR, "
          + "PR_STATUS_VALUE.VALUE AS PROJECT_STATUS "
          + "FROM OBJTYPE PR_TYPE, OBJECTS PR, "
          + "ATTRIBUTES PR_NAME, ATTRIBUTES START_DATE, ATTRIBUTES END_DATE, "
          + "ATTRIBUTES PR_STATUS, "
          + "LISTVALUE PR_STATUS_VALUE "
          + "where PR.OBJECT_ID = ? "
          + "and PR_TYPE.CODE = 'PROJECT' "
          + "and PR_NAME.ATTR_ID = 14 "
          + "and PR_NAME.OBJECT_ID = PR.OBJECT_ID "
          + "and START_DATE.ATTR_ID = 15 "
          + "and START_DATE.OBJECT_ID = PR.OBJECT_ID "
          + "and END_DATE.ATTR_ID = 16 "
          + "and END_DATE.OBJECT_ID = PR.OBJECT_ID "
          + "and PR_STATUS.ATTR_ID = 17 "
          + "and PR_STATUS.OBJECT_ID = PR.OBJECT_ID "
          + "and PR_STATUS_VALUE.ATTR_ID = 59 "
          + "and PR_STATUS_VALUE.LIST_VALUE_ID = PR_STATUS.LIST_VALUE_ID";

  private static final String FIND_PROJECT_BY_NAME =
      "select PR.OBJECT_ID AS PROJECT_ID, PR_NAME.VALUE AS NAME, START_DATE.VALUE "
          + "AS START_DATE_PR, END_DATE.VALUE AS END_DATE_PR, "
          + "PR_STATUS_VALUE.VALUE AS PROJECT_STATUS "
          + "FROM OBJTYPE PR_TYPE, OBJECTS PR, "
          + "ATTRIBUTES PR_NAME, ATTRIBUTES START_DATE, ATTRIBUTES END_DATE, "
          + "ATTRIBUTES PR_STATUS, "
          + "LISTVALUE PR_STATUS_VALUE "
          + "where PR_NAME.VALUE = ?"
          + "and PR_TYPE.CODE = 'PROJECT' "
          + "and PR_NAME.ATTR_ID = 14 "
          + "and PR_NAME.OBJECT_ID = PR.OBJECT_ID "
          + "and START_DATE.ATTR_ID = 15 "
          + "and START_DATE.OBJECT_ID = PR.OBJECT_ID "
          + "and END_DATE.ATTR_ID = 16 "
          + "and END_DATE.OBJECT_ID = PR.OBJECT_ID "
          + "and PR_STATUS.ATTR_ID = 17 "
          + "and PR_STATUS.OBJECT_ID = PR.OBJECT_ID "
          + "and PR_STATUS_VALUE.ATTR_ID = 59 "
          + "and PR_STATUS_VALUE.LIST_VALUE_ID = PR_STATUS.LIST_VALUE_ID";

  private static final String FIND_PROJECT_BY_START_DATE =
      "select PR_NAME.VALUE AS NAME, START_DATE.VALUE, END_DATE.VALUE, PM_ID.VALUE AS PM, "
          + "PR_STATUS_VALUE.VALUE AS PROJECT_STATUS "
          + "FROM OBJTYPE PR_TYPE, OBJECTS PR, "
          + "ATTRIBUTES PR_NAME, ATTRIBUTES START_DATE, ATTRIBUTES END_DATE, "
          + " ATTRIBUTES PR_STATUS,ATTRIBUTES PM_ID, "
          + "LISTVALUE PR_STATUS_VALUE "
          + "where PR_TYPE.CODE = 'PROJECT' "
          + "and PR_NAME.ATTR_ID = 14 "
          + "and PR_NAME.OBJECT_ID = PR.OBJECT_ID "
          + "and START_DATE.VALUE = ? "
          + "and START_DATE.OBJECT_ID = PR.OBJECT_ID "
          + "and END_DATE.ATTR_ID = 16 "
          + "and END_DATE.OBJECT_ID = PR.OBJECT_ID "
          + "and PM_ID.ATTR_ID = 18 "
          + "and PM_ID.OBJECT_ID = PR.OBJECT_ID "
          + "and PR_STATUS.ATTR_ID = 17 "
          + "and PR_STATUS.OBJECT_ID = PR.OBJECT_ID "
          + "and PR_STATUS_VALUE.ATTR_ID = 59 "
          + "and PR_STATUS_VALUE.LIST_VALUE_ID = PR_STATUS.LIST_VALUE_ID";


  public void setDataSource(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
  }


  @Override

  public void createProject(Project project) {
    jdbcTemplate.update(CREATE_PROJECT, new Object[]{project.getProjectId(),
        project.getName(),
        project.getStartDate(),
        project.getEndDate(),
        project.getProjectStatus(),
        project.getProjectManager(),
        project.getUsers(),
        project.getSprints()});
  }

  @Override
  public Project findProjectByProjectId(BigInteger id) {
    List<BigInteger> idUsers = getIdUsers(id);
    Collection<User> users = getAllUsersInProject(idUsers);
    List<BigInteger> idPms = getIdPM(id);
    User projectPM = findUserByUserId(idPms.remove(0));

    Project project = jdbcTemplate
        .queryForObject(FIND_PROJECT_BY_PROJECT_ID, new Object[]{id},
            new ProjectMapper());

    project.setUsers(users);
    project.setProjectManager(projectPM);

    return project;
  }


  private Collection<User> getAllUsersInProject(List<BigInteger> idUsers) {
    Collection<User> users = new ArrayList<>();
    for (BigInteger idUser : idUsers) {
      users.add(findUserByUserId(idUser));
    }
    return users;
  }


  @Override
  public Project findProjectByName(String name) {
    return jdbcTemplate.queryForObject(FIND_PROJECT_BY_NAME, new Object[]{name},
        new ProjectMapper());
  }

  @Override
  public Project findProjectByDate(Date startDate) {
    return jdbcTemplate.queryForObject(FIND_PROJECT_BY_START_DATE
        , new Object[]{startDate}, new ProjectMapper());
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

  private List<BigInteger> getIdPM(BigInteger id) {
    return jdbcTemplate.queryForList("SELECT USER_PROJ_REF.REFERENCE "
        + "FROM OBJREFERENCE USER_PROJ_REF "
        + "WHERE USER_PROJ_REF.ATTR_ID = 18 AND "
        + "USER_PROJ_REF.OBJECT_ID = " + id, BigInteger.class);
  }


  private User findUserByUserId(BigInteger id) {
    return jdbcTemplate.queryForObject(
        "SELECT EMP.OBJECT_ID AS USER_ID, EMP_LAST_NAME.VALUE AS LAST_NAME,"
            + " EMP_FIRST_NAME.VALUE AS FIRST_NAME, EMP_EMAIL.VALUE AS EMAIL, "
            +
            "EMP_BIRTH_DATE.VALUE AS BIRTH_DATE, EMP_HIRE_DATE.VALUE AS HIRE_DATE,"
            + " EMP_PHONE_NUMBER.VALUE AS PHONE_NUMBER, "
            +
            "EMP_JOB_TITLE_VALUE.VALUE AS JOB_TITLE, EMP_PROJECT_STATUS_VALUE.VALUE"
            + " AS PROJECT_STATUS "
            +
            "FROM OBJTYPE EMP_TYPE, OBJECTS EMP, " +
            "ATTRIBUTES EMP_LAST_NAME, ATTRIBUTES EMP_FIRST_NAME, ATTRIBUTES EMP_EMAIL, "
            +
            "ATTRIBUTES EMP_BIRTH_DATE, ATTRIBUTES EMP_HIRE_DATE, ATTRIBUTES EMP_PHONE_NUMBER, "
            +
            "ATTRIBUTES EMP_JOB_TITLE, ATTRIBUTES EMP_PROJECT_STATUS, " +
            "LISTVALUE EMP_JOB_TITLE_VALUE, LISTVALUE EMP_PROJECT_STATUS_VALUE "
            +
            "WHERE EMP_TYPE.OBJECT_TYPE_ID = 1 AND " +
            "EMP.OBJECT_TYPE_ID = EMP_TYPE.OBJECT_TYPE_ID AND " +
            "EMP.OBJECT_ID = ? AND " +
            "EMP_LAST_NAME.ATTR_ID = 1 AND " +
            "EMP_LAST_NAME.OBJECT_ID = EMP.OBJECT_ID AND " +
            "EMP_FIRST_NAME.ATTR_ID = 2 AND " +
            "EMP_FIRST_NAME.OBJECT_ID = EMP.OBJECT_ID AND " +
            "EMP_EMAIL.ATTR_ID = 3 AND " +
            "EMP_EMAIL.OBJECT_ID = EMP.OBJECT_ID AND " +
            "EMP_BIRTH_DATE.ATTR_ID = 4 AND " +
            "EMP_BIRTH_DATE.OBJECT_ID = EMP.OBJECT_ID AND " +
            "EMP_HIRE_DATE.ATTR_ID = 5 AND " +
            "EMP_HIRE_DATE.OBJECT_ID = EMP.OBJECT_ID AND " +
            "EMP_PHONE_NUMBER.ATTR_ID = 6 AND " +
            "EMP_PHONE_NUMBER.OBJECT_ID = EMP.OBJECT_ID AND " +
            "EMP_JOB_TITLE.ATTR_ID = 8 AND " +
            "EMP_JOB_TITLE.OBJECT_ID = EMP.OBJECT_ID AND " +
            "EMP_JOB_TITLE_VALUE.ATTR_ID = 8 AND " +
            "EMP_JOB_TITLE_VALUE.LIST_VALUE_ID = EMP_JOB_TITLE.LIST_VALUE_ID AND "
            +
            "EMP_PROJECT_STATUS.ATTR_ID = 9 AND " +
            "EMP_PROJECT_STATUS.OBJECT_ID = EMP.OBJECT_ID AND " +
            "EMP_PROJECT_STATUS_VALUE.ATTR_ID = 9 AND " +
            "EMP_PROJECT_STATUS_VALUE.LIST_VALUE_ID = EMP_PROJECT_STATUS.LIST_VALUE_ID",
        new Object[]{id}, new UserMapper());
  }

}
