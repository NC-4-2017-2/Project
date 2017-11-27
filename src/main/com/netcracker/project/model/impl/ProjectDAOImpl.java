package main.com.netcracker.project.model.impl;

import java.math.BigInteger;
import java.util.Date;
import javax.sql.DataSource;
import main.com.netcracker.project.model.ProjectDAO;
import main.com.netcracker.project.model.entity.Project;
import main.com.netcracker.project.model.impl.mappers.ProjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class ProjectDAOImpl implements ProjectDAO {

  private JdbcTemplate jdbcTemplate;

  private static final String CREATE_PROJECT =
      "";

  private static final String FIND_PROJECT_BY_PROJECT_ID =
      "select PR_NAME.VALUE AS NAME, START_DATE.VALUE, END_DATE.VALUE, PM_ID.VALUE AS PM, "
          + "PR_STATUS_VALUE.VALUE AS PROJECT_STATUS "
          + "FROM OBJTYPE PR_TYPE, OBJECTS PR, "
          + "ATTRIBUTES PR_NAME, ATTRIBUTES START_DATE, ATTRIBUTES END_DATE, "
          + "ATTRIBUTES PR_STATUS,ATTRIBUTES PM_ID, "
          + "LISTVALUE PR_STATUS_VALUE "
          + "where PR.OBJECT_ID = ? "
          + "and PR_TYPE.CODE = 'PROJECT' "
          + "and PR_NAME.ATTR_ID = 14 "
          + "and PR_NAME.OBJECT_ID = PR.OBJECT_ID "
          + "and START_DATE.ATTR_ID = 15 "
          + "and START_DATE.OBJECT_ID = PR.OBJECT_ID "
          + "and END_DATE.ATTR_ID = 16 "
          + "and END_DATE.OBJECT_ID = PR.OBJECT_ID "
          + "and PM_ID.ATTR_ID = 18 "
          + "and PM_ID.OBJECT_ID = PR.OBJECT_ID "
          + "and PR_STATUS.ATTR_ID = 17 "
          + "and PR_STATUS.OBJECT_ID = PR.OBJECT_ID "
          + "and PR_STATUS_VALUE.ATTR_ID = 59 "
          + "and PR_STATUS_VALUE.LIST_VALUE_ID = PR_STATUS.LIST_VALUE_ID";
  private static final String FIND_PROJECT_BY_NAME =
      "select PR_NAME.VALUE AS NAME, START_DATE.VALUE, END_DATE.VALUE, PM_ID.VALUE AS PM, "
          + "PR_STATUS_VALUE.VALUE AS PROJECT_STATUS "
          + "FROM OBJTYPE PR_TYPE, OBJECTS PR, "
          + "ATTRIBUTES PR_NAME, ATTRIBUTES START_DATE, ATTRIBUTES END_DATE, "
          + " ATTRIBUTES PR_STATUS,ATTRIBUTES PM_ID, "
          + "LISTVALUE PR_STATUS_VALUE "
          + "where PR_TYPE.CODE = 'PROJECT' "
          + "and PR_NAME.VALUE = ? "
          + "and PR_NAME.OBJECT_ID = PR.OBJECT_ID "
          + "and START_DATE.ATTR_ID = 15 "
          + "and START_DATE.OBJECT_ID = PR.OBJECT_ID "
          + "and END_DATE.ATTR_ID = 16 "
          + "and END_DATE.OBJECT_ID = PR.OBJECT_ID "
          + "and PM_ID.ATTR_ID = 18 "
          + "and PM_ID.OBJECT_ID = PR.OBJECT_ID "
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
    return jdbcTemplate
        .queryForObject(FIND_PROJECT_BY_PROJECT_ID, new Object[]{id},
            new ProjectMapper());
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
  public void updateProject(BigInteger id, Project project) {

  }

  @Override
  public void addUser(BigInteger userId, BigInteger projectId) {

  }
}
