import main.com.netcracker.project.model.UserDAO;
import main.com.netcracker.project.model.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;


import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

public class UsersDaoTest {

    private ApplicationContext context;
    private UserDAO userDao;

    @Before
    public void setUp() {
        context = new ClassPathXmlApplicationContext("Spring-Module.xml");
        userDao = (UserDAO) context.getBean("userDAO");
    }

    @Test
    public void findUserByUserId() {
        User user = userDao.findUserByUserId(BigInteger.valueOf(1));
        Assert.assertEquals(BigInteger.valueOf(1), user.getUserId());
    }

    @Test
    public void findUserByUserLogin() {
        User user = userDao.findUserByLogin("ivanov");
        Assert.assertEquals("Ivanov", user.getLastName());
    }

    @Test
    public void insertUser() {
        Date date = new Date();

        User user = new User.UserBuilder()
                .lastName("Last")
                .firstName("First")
                .email("qwe")
                .dateOfBirth(date)
                .hireDate(date)
                .phoneNumber("qwe")
                .login("qwe")
                .password("qwe")
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
        List<UserDAO.WorkPeriod> workPeriodList = userDao.findWorkPeriodsByUserId(BigInteger.valueOf(2));

        for(UserDAO.WorkPeriod wk : workPeriodList) {
            LOGGER.log(Level.INFO, wk.getUserId().toString());
            LOGGER.log(Level.INFO, wk.getProjectId().toString());
            LOGGER.log(Level.INFO, wk.getStartWorkDate().toString());
            LOGGER.log(Level.INFO, wk.getEndWorkDate().toString());
        }
    }

    @Test
    public void findWorkPeriodByUserIdAndProjectId() {
        List<UserDAO.WorkPeriod> workPeriodList = userDao.findWorkPeriodByUserIdAndProjectId(BigInteger.valueOf(2), BigInteger.valueOf(4));

        for(UserDAO.WorkPeriod wk : workPeriodList) {
            LOGGER.log(Level.INFO, wk.getUserId().toString());
            LOGGER.log(Level.INFO, wk.getProjectId().toString());
            LOGGER.log(Level.INFO, wk.getStartWorkDate().toString());
            LOGGER.log(Level.INFO, wk.getEndWorkDate().toString());
        }
    }

    @Test
    public void updateWorkingPeriodByUserId() {
        UserDAO.WorkPeriod workPeriod = new UserDAO.WorkPeriod();
        workPeriod.setEndWorkDate(new Date());
        userDao.updateWorkingPeriodByUserId(BigInteger.valueOf(2), BigInteger.valueOf(4), workPeriod);
    }
}
