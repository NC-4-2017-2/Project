package main.com.netcracker.project.service.impl;


import java.math.BigInteger;
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
import main.com.netcracker.project.services.impl.EmailServiceImpl;
import org.apache.log4j.Logger;
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
public class EmailServiceImplTest {

  private EmailServiceImpl emailService;
  private UserDAO userDAO;
  private ApplicationContext context;
  private JdbcTemplate template;
  private DataSource dataSource;
  private static final String DELETE_FROM_ATTRIBUTES = "DELETE FROM ATTRIBUTES WHERE OBJECT_ID = ?";
  private static final String DELETE_FROM_OBJECTS = "DELETE FROM OBJECTS WHERE OBJECT_ID = ?";


  private static final Logger logger = Logger.getLogger(EmailServiceImplTest.class);


  @Before
  public void setUp() {
    Locale.setDefault(Locale.ENGLISH);
    context = new ClassPathXmlApplicationContext("Spring-Module.xml");
    emailService = (EmailServiceImpl) context.getBean("emailServiceImpl");
    template = new JdbcTemplate(dataSource);
    userDAO = (UserDAO) context.getBean("userDAO");
  }

  @Autowired
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Test
  public void test1deleteUser() {
    BigInteger id = BigInteger.valueOf(101);

    template.update(DELETE_FROM_ATTRIBUTES, new Object[]{id});
    template.update(DELETE_FROM_OBJECTS, new Object[]{id});
  }


  @Test
  public void sendPass() {
    MapperDateConverter converter = new MapperDateConverter();
    Date dateOfBirth = converter.convertStringToDate("04.03.96");
    Date hireDate = converter.convertStringToDate("12.02.18");

    User user = new User.UserBuilder()
        .lastName("Egor")
        .firstName("Kovalchuk")
        .email("developerEgor@yandex.ru")
        .dateOfBirth(dateOfBirth)
        .hireDate(hireDate)
        .phoneNumber("98282")
        .jobTitle(JobTitle.SOFTWARE_ENGINEER)
        .projectStatus(ProjectStatus.WORKING)
        .login("lastFirst")
        .password("pass")
        .role(UserRole.ROLE_SE)
        .userStatus(UserStatus.WORKING)
        .build();

    User returnedUser = userDAO.createUser(user);
    logger.info("User was created!");

    emailService.sendEmail(returnedUser.getEmail(), "Use your pass",
                           returnedUser.getPassword());
    logger.info("Send was sent!");

  }

}
