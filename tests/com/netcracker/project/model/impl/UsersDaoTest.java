package com.netcracker.project.model.impl;

import static org.junit.Assert.assertEquals;

import com.netcracker.project.model.ProjectDAO;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import javax.sql.DataSource;
import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.UserDAO.JobTitle;
import com.netcracker.project.model.UserDAO.ProjectStatus;
import com.netcracker.project.model.UserDAO.UserRole;
import com.netcracker.project.model.UserDAO.UserStatus;
import com.netcracker.project.model.UserDAO.WorkPeriod;
import com.netcracker.project.model.UserDAO.WorkPeriod.WorkPeriodStatus;
import com.netcracker.project.model.entity.User;
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
public class UsersDaoTest {

  @Autowired
  private UserDAO userDao;
  private JdbcTemplate template;
  @Autowired
  private DataSource dataSource;
  private static final String DELETE_FROM_ATTRIBUTES = "DELETE FROM ATTRIBUTES WHERE OBJECT_ID = ?";
  private static final String DELETE_FROM_OBJECTS = "DELETE FROM OBJECTS WHERE OBJECT_ID = ?";


  @Before
  public void setUp() {
    Locale.setDefault(Locale.ENGLISH);
    template = new JdbcTemplate(dataSource);
  }

  @Test
  public void findUserByUserId() {
    User user = userDao.findUserByUserId(BigInteger.valueOf(1));
    assertEquals(BigInteger.valueOf(1), user.getUserId());
    assertEquals("User{userId=1, firstName='Ivan', lastName='Ivanov', email='ivanov@gmail.com', dateOfBirth=1993-03-30, hireDate=2011-04-30,"
        + " phoneNumber='09797979797', photo='null', jobTitle=PROJECT_MANAGER, login='null', password='null', userRole=null, workPeriods=null, userStatus=null, projectStatus=WORKING}", user.toString());
  }

  @Test
  public void findUserByUserLogin() {
    User user = userDao.findUserByLogin("ivanov");
    assertEquals("User{userId=1, firstName='Ivan', lastName='Ivanov', email='ivanov@gmail.com', dateOfBirth=1993-03-30, hireDate=2011-04-30,"
        + " phoneNumber='09797979797', photo='null', jobTitle=PROJECT_MANAGER, login='null', password='null', userRole=null, workPeriods=null, userStatus=null, projectStatus=WORKING}", user.toString());
  }

  @Test
  public void findUserByLastNameAndFirstName() {
    Collection result = userDao.findUserByLastNameAndFirstName("Ivanov", "Ivan");
    assertEquals("[User{userId=1, firstName='Ivan', lastName='Ivanov', email='ivanov@gmail.com', dateOfBirth=1993-03-30, hireDate=2011-04-30,"
        + " phoneNumber='09797979797', photo='null', jobTitle=PROJECT_MANAGER, login='null', password='null', userRole=null, workPeriods=null, userStatus=null, projectStatus=WORKING}]", result.toString());
  }

  @Test
  public void insertUser() {
    MapperDateConverter converter = new MapperDateConverter();
    Date dateOfBirth = converter.convertStringToDate("11.11.1986");
    Date hireDate = converter.convertStringToDate("12.12.2010");

    User user = new User.UserBuilder()
        .lastName("Last")
        .firstName("First")
        .email("lastFirst@gmail.com")
        .dateOfBirth(dateOfBirth)
        .hireDate(hireDate)
        .phoneNumber("12345678")
        .jobTitle(JobTitle.SOFTWARE_ENGINEER)
        .projectStatus(ProjectStatus.WORKING)
        .login("lastFirst")
        .password("qwerty1")
        .role(UserRole.ROLE_SE)
        .userStatus(UserStatus.WORKING)
        .build();

    User returnedUser = userDao.createUser(user);
  }

  @Test
  public void updatePhoneNumber() {
    userDao.updatePhoneNumber(BigInteger.valueOf(1), "12345678");
  }

  @Test
  public void updateEmail() {
    userDao.updateEmail(BigInteger.valueOf(1), "qwerty@gmail.com");
  }

  @Test
  public void updatePassword() {
    userDao.updatePassword(BigInteger.valueOf(1), "qwerty1");
  }

  @Test
  public void findWorkPeriodsByUserId() {
    Collection<UserDAO.WorkPeriod> result = userDao
        .findWorkPeriodsByUserId(BigInteger.valueOf(2));
    assertEquals("[WorkPeriod{workPeriodId=16, userId=2, projectId=4, startWorkDate=2012-12-14, endWorkDate=2012-12-25, workPeriodStatus=WORKING}]", result.toString());
  }

  @Test
  public void findWorkPeriodsByProjectId() {
    Collection<UserDAO.WorkPeriod> result = userDao
        .findWorkPeriodsByProjectId(BigInteger.valueOf(4));
    assertEquals("[WorkPeriod{workPeriodId=16, userId=2, projectId=4, startWorkDate=2012-12-14, endWorkDate=2012-12-25, workPeriodStatus=WORKING},"
        + " WorkPeriod{workPeriodId=17, userId=3, projectId=4, startWorkDate=2012-12-14, endWorkDate=2012-12-25, workPeriodStatus=WORKING}]", result.toString());
  }


  @Test
  public void findWorkPeriodByUserIdAndProjectId() {
    Collection<UserDAO.WorkPeriod> result = userDao
        .findWorkPeriodByUserIdAndProjectId(BigInteger.valueOf(2),
            BigInteger.valueOf(4));
    assertEquals("[WorkPeriod{workPeriodId=16, userId=2, projectId=4, startWorkDate=2012-12-14, endWorkDate=2012-12-25, workPeriodStatus=WORKING}]", result.toString());
  }

  @Test
  public void updateProjectStatus() {
    userDao.updateProjectStatus(BigInteger.valueOf(2), 1);
  }

  @Test
  public void updateWorkingPeriodEndDateByUserId() {
    MapperDateConverter converter = new MapperDateConverter();
    UserDAO.WorkPeriod workPeriod = new UserDAO.WorkPeriod();
    workPeriod.setUserId(BigInteger.valueOf(2));
    workPeriod.setProjectId(BigInteger.valueOf(4));
    workPeriod.setEndWorkDate(converter.convertStringToDate("11.11.1986"));
    userDao.updateWorkingPeriodEndDateByUserId(workPeriod);
  }

  @Test
  public void updateWorkingPeriodStatusByUserId() {
    UserDAO.WorkPeriod workPeriod = new UserDAO.WorkPeriod();
    workPeriod.setUserId(BigInteger.valueOf(2));
    workPeriod.setProjectId(BigInteger.valueOf(4));
    workPeriod.setWorkPeriodStatus(WorkPeriodStatus.FIRED);
    userDao.updateWorkingPeriodStatusByUserId(workPeriod);
  }

  @Test
  public void findWorkPeriodByProjectIdAndStatus() {
    Collection<WorkPeriod> result = userDao
        .findWorkPeriodByProjectIdAndStatus(BigInteger.valueOf(4),
            WorkPeriodStatus.WORKING.getId());
    assertEquals(2, result.size());
  }

  @Test
  public void createWorkPeriod() {
    WorkPeriod workPeriod = new WorkPeriod();
    MapperDateConverter converter = new MapperDateConverter();
    Date startDate = converter.convertStringToDate("11.11.2013");
    Date endDate = converter.convertStringToDate("11.12.2013");
    workPeriod.setStartWorkDate(startDate);
    workPeriod.setEndWorkDate(endDate);
    workPeriod.setWorkPeriodStatus(WorkPeriodStatus.WORKING);
    workPeriod.setUserId(BigInteger.valueOf(2));
    workPeriod.setProjectId(BigInteger.valueOf(4));
    userDao.createWorkPeriod(workPeriod);
  }

  @Test
  public void deleteUser() {
    BigInteger id = BigInteger.valueOf(101);

    template.update(DELETE_FROM_ATTRIBUTES, new Object[]{id});
    template.update(DELETE_FROM_OBJECTS, new Object[]{id});
  }


}
