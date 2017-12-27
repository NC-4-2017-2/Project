package com.netcracker.project.model.impl;

import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.entity.User;
import com.netcracker.project.model.entity.WorkPeriod;
import com.netcracker.project.model.impl.mappers.EnumMapper;
import com.netcracker.project.model.impl.mappers.UserMapper;
import com.netcracker.project.model.impl.mappers.WorkPeriodMapper;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
@ImportResource("classpath:Spring-User.xml")
public class UserDAOImpl implements UserDAO {

  private static final Logger logger = Logger.getLogger(UserDAOImpl.class);
  private JdbcTemplate template;

  @Autowired
  public void setDataSource(DataSource dataSource) {
    template = new JdbcTemplate(dataSource);
  }

  @Override
  public User createUser(User user) {
    logger.info("Entering insert(user=" + user + ")");
    this.template.update(CREATE_USER, new Object[]{
        user.getLastName() + " " + user.getFirstName(),
        user.getLastName(),
        user.getFirstName(),
        user.getEmail(),
        user.getDateOfBirth(),
        user.getHireDate(),
        user.getPhoneNumber(),
        user.getJobTitle().getId(),
        user.getProjectStatus().getId(),
        user.getLogin(),
        user.getPassword(),
        user.getUserRole().getId(),
        user.getUserStatus().getId()
    });

    return user;
  }

  @Override
  public User findUserByUserId(BigInteger id) {
    logger.info("Entering findUserByUserId(" + id + ")");
    return template.queryForObject(FIND_USER_BY_USER_ID, new Object[]{id}, new UserMapper());
  }

  @Override
  public User findUserByLogin(String login) {
    logger.info("Entering findUserByLogin(" + login + ")");
    return template.queryForObject(FIND_USER_BY_LOGIN, new Object[]{login}, new UserMapper());
  }

  @Override
  public Collection<User> findUserByLastNameAndFirstName(String lastName, String firstName) {
    logger.info("Entering findUserByFirstNameAndLastName(lastName=" + lastName + "," + " firstName="
        + firstName + ")");
    return template
        .query(FIND_USER_BY_LAST_NAME_AND_FIRST_NAME, new Object[]{lastName, firstName},
            new UserMapper());
  }

  @Override
  public Collection<User> findUserByProjectId(BigInteger projectId) {
    logger.info("Entering findUserByProjectId(" + projectId + ")");
    return template.query(FIND_USER_BY_PROJECT_ID, new Object[]{projectId}, new UserMapper());
  }

  @Override
  public Map<String, String> getAllUserName() {
    logger.info("Entering getAllUserName()");
    List idAndName = template.queryForList(GET_ALL_USERS);
    Map<String, String> result = new HashMap<>();
    for (Object idName : idAndName) {
      HashMap map = (HashMap) idName;
      result.put(String.valueOf(map.get(EnumMapper.USER_ID.toString())),
          String.valueOf(map.get(EnumMapper.FULL_NAME.toString())));
    }

    return result;
  }

  @Override
  public void updatePhoneNumber(BigInteger id, String phoneNumber) {
    logger.info("Entering updatePhoneNumber(id=" + id + "," + " phoneNumber=" + phoneNumber + ")");
    template.update(UPDATE_PHONE_NUMBER, phoneNumber, id);
  }

  @Override
  public void updateEmail(BigInteger id, String email) {
    logger.info("Entering updateEmail(id=" + id + "," + " email=" + email + ")");
    template.update(UPDATE_EMAIL, email, id);
  }

  @Override
  public void updatePassword(BigInteger id, String password) {
    logger.info("Entering updatePassword(id=" + id + "," + " password=" + password + ")");
    template.update(UPDATE_PASSWORD, password, id);
  }

  @Override
  public void updatePhoto(BigInteger id, String photo) {
    //don't forget about File!!!
  }

  @Override
  public void updateProjectStatus(BigInteger id, Integer status) {
    logger.info("Entering updateProjectStatus(id=" + id + "," + " status=" + status + ")");
    template.update(UPDATE_USER_PROJECT_STATUS, status, id);
  }

  @Override
  public Integer findIfPMExists(BigInteger id) {
    logger.info("Entering findIfPMExists(id=" + id + ")");
    return template.queryForObject(FIND_PM_IF_EXISTS, new Object[]{id}, Integer.class);
  }

  @Override
  public Collection<WorkPeriod> findWorkPeriodsByUserId(BigInteger id) {
    logger.info("Entering findWorkPeriodsByUserId(id=" + id + ")");
    return template.query(FIND_WORK_PERIOD_BY_USER_ID, new Object[]{id}, new WorkPeriodMapper());
  }

  @Override
  public Collection<WorkPeriod> findWorkPeriodsByProjectId(BigInteger id) {
    logger.info("Entering findWorkPeriodsByProjectId(id=" + id + ")");
    return template.query(FIND_WORK_PERIOD_BY_PROJECT_ID, new Object[]{id}, new WorkPeriodMapper());
  }

  @Override
  public Collection<WorkPeriod> findWorkPeriodByUserIdAndProjectId(BigInteger userId,
      BigInteger projectId) {
    logger.info("Entering findWorkPeriodByUserIdAndProjectId(userId=" + userId + "," + " projectId="
        + projectId + ")");
    return template
        .query(FIND_WORK_PERIOD_BY_USER_ID_AND_PROJECT_ID, new Object[]{userId, projectId},
            new WorkPeriodMapper());
  }

  @Override
  public Collection<WorkPeriod> findWorkPeriodByProjectIdAndStatus(
      BigInteger projectId, Integer status) {
    logger.info(
        "Entering findWorkPeriodByProjectIdAndStatus(projectId=" + projectId + "," + " status="
            + status + ")");
    return template
        .query(FIND_WORKING_PERIOD_BY_PROJECT_ID_AND_STATUS, new Object[]{status, projectId},
            new WorkPeriodMapper());
  }

  @Override
  public void updateWorkingPeriodEndDateByUserId(WorkPeriod workPeriod) {
    logger.info("Entering updateWorkingPeriodByUserId(userId=" + workPeriod.getUserId() + ","
        + " projectId=" + workPeriod.getProjectId() + "," + " UserDAO.WorkPeriod=" + workPeriod
        + ")");
    template
        .update(UPDATE_WORKING_PERIOD_END_DATE, workPeriod.getEndWorkDate(), workPeriod.getUserId(),
            workPeriod.getProjectId());
  }

  @Override
  public void updateWorkingPeriodStatusByUserId(WorkPeriod workPeriod) {
    logger.info("Entering updateWorkingPeriodStatusByUserId(userId=" + workPeriod.getUserId() + ","
        + " projectId=" + workPeriod.getProjectId() + "," + " UserDAO.WorkPeriod=" + workPeriod
        + ")");
    template.update(UPDATE_WORKING_PERIOD_STATUS, workPeriod.getWorkPeriodStatus().getId(),
        workPeriod.getUserId(), workPeriod.getProjectId());
  }

  @Override
  public void createWorkPeriod(WorkPeriod workPeriod) {
    logger.info("Entering createWorkPeriod(workPeriod=" + workPeriod + ")");
    this.template.update(CREATE_WORK_PERIOD, new Object[]{
        "WorkPeriod" + workPeriod.getWorkPeriodId(),
        workPeriod.getStartWorkDate(),
        workPeriod.getEndWorkDate(),
        workPeriod.getWorkPeriodStatus().getId(),
        workPeriod.getUserId(),
        workPeriod.getProjectId()
    });
  }

}
