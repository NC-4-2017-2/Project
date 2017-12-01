package main.com.netcracker.project.model.impl;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import javax.sql.DataSource;
import main.com.netcracker.project.model.UserDAO;
import main.com.netcracker.project.model.UserDAO.JobTitle;
import main.com.netcracker.project.model.UserDAO.ProjectStatus;
import main.com.netcracker.project.model.UserDAO.UserRole;
import main.com.netcracker.project.model.UserDAO.UserStatus;
import main.com.netcracker.project.model.entity.User;
import main.com.netcracker.project.model.impl.mappers.MapperDateConverter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:Spring-Module.xml"})
public class UsersDaoTest {

  private UserDAO userDao;
  private ApplicationContext context;
  private JdbcTemplate template;
  private DataSource dataSource;
  private static final String DELETE_FROM_ATTRIBUTES = "DELETE FROM ATTRIBUTES WHERE OBJECT_ID = ?";
  private static final String DELETE_FROM_OBJECTS = "DELETE FROM OBJECTS WHERE OBJECT_ID = ?";


  @Before
  public void setUp() {
    Locale.setDefault(Locale.ENGLISH);
    context = new ClassPathXmlApplicationContext("Spring-Module.xml");
    userDao = (UserDAO) context.getBean("userDAO");
    template = new JdbcTemplate(dataSource);

  }

  @Autowired
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }


  @Test
  public void findUserByUserId() {
    User user = userDao.findUserByUserId(BigInteger.valueOf(1));
    Assert.assertEquals(BigInteger.valueOf(1), user.getUserId());
    Assert.assertEquals("Ivanov", user.getLastName());
  }

  @Test
  public void findUserByUserLogin() {
    User user = userDao.findUserByLogin("ivanov");
    Assert.assertEquals("Ivanov", user.getLastName());
    Assert.assertEquals(BigInteger.valueOf(1), user.getUserId());
  }

  @Test
  public void insertUser() {
    MapperDateConverter converter = new MapperDateConverter();
    Date dateOfBirth = converter.convertStringToDate("11.11.86");
    Date hireDate = converter.convertStringToDate("12.12.10");

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
    Collection<UserDAO.WorkPeriod> workPeriodList = userDao
        .findWorkPeriodsByUserId(BigInteger.valueOf(2));
  }

  @Test
  public void findWorkPeriodsByProjectId() {
    Collection<UserDAO.WorkPeriod> workPeriodList = userDao
        .findWorkPeriodsByProjectId(BigInteger.valueOf(4));
  }


  @Test
  public void findWorkPeriodByUserIdAndProjectId() {
    Collection<UserDAO.WorkPeriod> workPeriodList = userDao
        .findWorkPeriodByUserIdAndProjectId(BigInteger.valueOf(2),
            BigInteger.valueOf(4));
  }

  @Test
  public void updateProjectStatus() {
    userDao.updateProjectStatus(BigInteger.valueOf(2), 1);
  }

  @Test
  public void deleteUser() {
    BigInteger id = BigInteger.valueOf(101);

    template.update(DELETE_FROM_ATTRIBUTES, new Object[]{id});
    template.update(DELETE_FROM_OBJECTS, new Object[]{id});
  }
}
