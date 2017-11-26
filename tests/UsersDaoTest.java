import main.com.netcracker.project.model.UserDAO;
import main.com.netcracker.project.model.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigInteger;

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
        Assert.assertEquals(user.getUserId(), BigInteger.valueOf(1));
    }
}
