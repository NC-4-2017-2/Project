package com.netcracker.project.model.impl;

import static org.junit.Assert.assertEquals;

import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.entity.User;
import com.netcracker.project.model.entity.WorkPeriod;
import com.netcracker.project.model.entity.WorkPeriod.WorkPeriodStatus;
import com.netcracker.project.model.enums.JobTitle;
import com.netcracker.project.model.enums.ProjectStatus;
import com.netcracker.project.model.enums.UserRole;
import com.netcracker.project.model.enums.UserStatus;
import com.netcracker.project.services.impl.DateConverterService;
import com.netcracker.project.services.impl.PasswordService;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
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
    assertEquals(
        "User{userId=1, firstName='Ivan', lastName='Ivanov', email='ivanov@gmail.com', dateOfBirth=1993-03-30, hireDate=2011-04-30,"
            + " phoneNumber='09797979797', photo='null', jobTitle=PROJECT_MANAGER, login='null', password='null', userRole=null, workPeriods=null, userStatus=null, projectStatus=WORKING}",
        user.toString());
  }

  @Test
  public void findFullUserByUserId() {
    User user = userDao.findFullUserByUserLogin("ivanov");
    assertEquals(BigInteger.valueOf(1), user.getUserId());
    assertEquals(
        "User{userId=1, firstName='Ivan', lastName='Ivanov', email='ivanov@gmail.com', dateOfBirth=1993-03-30, hireDate=2011-04-30,"
            + " phoneNumber='09797979797', jobTitle=PROJECT_MANAGER, login='ivanov', password='null', userRole=null, workPeriods=null, userStatus=WORKING, projectStatus=WORKING}",
        user.toString());
  }

  @Test
  public void findUserByUserLogin() {
    User user = userDao.findUserByLogin("ivanov");
    assertEquals(
        "User{userId=1, firstName='Ivan', lastName='Ivanov', email='ivanov@gmail.com', dateOfBirth=1993-03-30, hireDate=2011-04-30,"
            + " phoneNumber='09797979797', photo='null', jobTitle=PROJECT_MANAGER, login='null', password='null', userRole=null, workPeriods=null, userStatus=null, projectStatus=WORKING}",
        user.toString());
  }

  @Test
  public void findUserByLastNameAndFirstName() {
    Collection result = userDao
        .findUserByLastNameAndFirstName("Ivanov", "Ivan");
    assertEquals(
        "[User{userId=1, firstName='Ivan', lastName='Ivanov', email='ivanov@gmail.com', dateOfBirth=1993-03-30, hireDate=2011-04-30,"
            + " phoneNumber='09797979797', photo='null', jobTitle=PROJECT_MANAGER, login='null', password='null', userRole=null, workPeriods=null, userStatus=null, projectStatus=WORKING}]",
        result.toString());
  }

  @Test
  public void insertUser() {
    DateConverterService converter = new DateConverterService();
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
    Collection<WorkPeriod> result = userDao
        .findWorkPeriodsByUserId(BigInteger.valueOf(2));
    assertEquals(
        "[WorkPeriod{workPeriodId=16, userId=2, projectId=4, startWorkDate=2012-12-14, endWorkDate=2012-12-25, workPeriodStatus=WORKING}]",
        result.toString());
  }

  @Test
  public void findWorkPeriodsByProjectId() {
    Collection<WorkPeriod> result = userDao
        .findWorkPeriodsByProjectId(BigInteger.valueOf(4));
    assertEquals(
        "[WorkPeriod{workPeriodId=16, userId=2, projectId=4, startWorkDate=2012-12-14, endWorkDate=2012-12-25, workPeriodStatus=WORKING},"
            + " WorkPeriod{workPeriodId=17, userId=3, projectId=4, startWorkDate=2012-12-14, endWorkDate=2012-12-25, workPeriodStatus=WORKING}]",
        result.toString());
  }

  @Test
  public void findWorkPeriodByUserIdAndProjectId() {
    Collection<WorkPeriod> result = userDao
        .findWorkPeriodByUserIdAndProjectId(BigInteger.valueOf(2),
            BigInteger.valueOf(4));
    assertEquals(
        "[WorkPeriod{workPeriodId=16, userId=2, projectId=4, startWorkDate=2012-12-14, endWorkDate=2012-12-25, workPeriodStatus=WORKING}]",
        result.toString());
  }

  @Test
  public void findUserByProjectId() {
    Collection<User> userCollection = userDao
        .findUserByProjectId(BigInteger.valueOf(4));
    assertEquals(
        "[User{userId=2, firstName='Petr', lastName='Petrov', email='petrov@gmail.com', dateOfBirth=1992-03-30, hireDate=2011-04-30,"
            + " phoneNumber='09797979796', photo='null', jobTitle=LINE_MANAGER, login='null', password='null', userRole=null, workPeriods=null, userStatus=null, projectStatus=WORKING},"
            + " User{userId=3, firstName='Admin', lastName='Adminov', email='admin@gmail.com', dateOfBirth=1991-03-30, hireDate=2011-04-30,"
            + " phoneNumber='09797979795', photo='null', jobTitle=SOFTWARE_ENGINEER, login='null', password='null', userRole=null, workPeriods=null, userStatus=null, projectStatus=WORKING}]",
        userCollection.toString());
  }

  @Test
  public void updateProjectStatus() {
    userDao.updateProjectStatus(BigInteger.valueOf(2), 0);
  }

  @Test
  public void updateWorkingPeriodEndDateByUserId() {
    DateConverterService converter = new DateConverterService();
    WorkPeriod workPeriod = new WorkPeriod.WorkPeriodBuilder()
        .userId(BigInteger.valueOf(2))
        .projectId(BigInteger.valueOf(4))
        .endWorkDate(converter.convertStringToDateFromJSP("11.11.1986"))
        .build();
    userDao.updateWorkingPeriodEndDateByUserId(BigInteger.valueOf(3),
        BigInteger.valueOf(4),
        converter.convertStringToDateFromJSP("1986-11-11"));
  }

  @Test
  public void updateWorkingPeriodStatusByUserId() {
    WorkPeriod workPeriod = new WorkPeriod.WorkPeriodBuilder()
        .userId(BigInteger.valueOf(2))
        .projectId(BigInteger.valueOf(4))
        .workPeriodStatus(WorkPeriodStatus.FIRED)
        .build();
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
    DateConverterService converter = new DateConverterService();
    String startDate = "11.11.2013";
    String endDate = "11.12.2013";

    WorkPeriod workPeriod = new WorkPeriod.WorkPeriodBuilder()
        .startWorkDate(converter.convertStringToDateFromJSP(startDate))
        .endWorkDate(converter.convertStringToDateFromJSP(endDate))
        .workPeriodStatus(WorkPeriodStatus.WORKING)
        .userId(BigInteger.valueOf(2))
        .projectId(BigInteger.valueOf(4))
        .build();
    userDao.createWorkPeriod(workPeriod, BigInteger.valueOf(4));
  }

  @Test
  public void findUsersByJobTitleAndProjectStatus() {
    Collection<User> result = userDao
        .findUsersByJobTitleAndProjectStatus(JobTitle.PROJECT_MANAGER.getId(),
            ProjectStatus.WORKING.getId());
    assertEquals(
        "[User{userId=1, firstName='Ivan', lastName='Ivanov', email='ivanov@gmail.com', dateOfBirth=1993-03-30, hireDate=2011-04-30,"
            + " phoneNumber='09797979797', photo='null', jobTitle=PROJECT_MANAGER, login='null', password='null', userRole=null, workPeriods=null, userStatus=null, projectStatus=WORKING},"
            + " User{userId=104, firstName='Victor', lastName='Pavlov', email='pavlov@gmail.com', dateOfBirth=1993-03-30, hireDate=2011-04-30,"
            + " phoneNumber='09797979780', photo='null', jobTitle=PROJECT_MANAGER, login='null', password='null', userRole=null, workPeriods=null, userStatus=null, projectStatus=WORKING},"
            + " User{userId=105, firstName='Victor', lastName='Ovechkin', email='ovechkin@gmail.com', dateOfBirth=1992-04-25, hireDate=2011-06-02,"
            + " phoneNumber='09797979794', photo='null', jobTitle=PROJECT_MANAGER, login='null', password='null', userRole=null, workPeriods=null, userStatus=null, projectStatus=WORKING},"
            + " User{userId=107, firstName='Taras', lastName='Strahov', email='strahov@gmail.com', dateOfBirth=1994-07-07, hireDate=2011-05-22,"
            + " phoneNumber='09797979792', photo='null', jobTitle=PROJECT_MANAGER, login='null', password='null', userRole=null, workPeriods=null, userStatus=null, projectStatus=WORKING}]",
        result.toString());
  }

  @Test
  public void findHiredUserIfExistsByLastFirstNameAndJobTitle() {
    Integer result = userDao
        .findHiredUserIfExistsByLastFirstNameAndJobTitle("Adminov", "Admin",
            JobTitle.SOFTWARE_ENGINEER.getId());
    assertEquals("0", result.toString());
  }

  @Test
  public void findWOrkPeriodIfExistWithCorrectParameters() {
    Integer result = userDao
        .findWorkingWorkPeriodIfExist(BigInteger.valueOf(3),
            BigInteger.valueOf(4));
    assertEquals(new Long(1), new Long(result));
  }

  @Test
  public void findWOrkPeriodIfExistWithWrongParameters() {
    Integer result = userDao
        .findWorkingWorkPeriodIfExist(BigInteger.valueOf(3),
            BigInteger.valueOf(10));
    assertEquals(new Long(0), new Long(result));
  }

  @Test
  public void deleteUser() {
    BigInteger id = BigInteger.valueOf(1084);

    template.update(DELETE_FROM_ATTRIBUTES, new Object[]{id});
    template.update(DELETE_FROM_OBJECTS, new Object[]{id});
  }


  @Test
  public void findIfLMExistsOnProject() {
    Integer result = userDao.findIfLMExistsOnProject(BigInteger.valueOf(4));
    assertEquals("1", result.toString());
  }

  @Test
  public void findIfLMExists() {
    Integer result = userDao.findIfLMExists(BigInteger.valueOf(2));
    assertEquals("1", result.toString());
  }

  @Test
  public void updateUserRoleAndJobTitle() {
    userDao.updateJobTitleAndUserRole(BigInteger.valueOf(1085),
        UserRole.ROLE_LM.getId(), JobTitle.LINE_MANAGER.getId());
    User result = userDao.findUserByUserId(BigInteger.valueOf(1085));
    assertEquals(
        "User{userId=1085, firstName='Alexander', lastName='Yanov', email='yanov.alexander@gmail.com', dateOfBirth=1991-02-17, hireDate=2018-01-15, phoneNumber='1111111112', jobTitle=LINE_MANAGER, login='null', password='null',"
            + " userRole=null, workPeriods=null, userStatus=null, projectStatus=TRANSIT}",
        result.toString());
  }

  @Test
  public void checkLoginExistence() {
    Integer result = userDao.findIfLoginExists("ivanov");
    assertEquals("1", result.toString());
  }

  @Test
  public void findUserByUserIdIfExists() {
    Integer result = userDao.findUserByUserIdIfExists(BigInteger.valueOf(3));
    assertEquals("1", result.toString());
  }

  @Test
  public void generatePassword() {
    String password = new PasswordService().generatePassword();
    System.out.println(password);
  }
}
