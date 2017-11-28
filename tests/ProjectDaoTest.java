import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import main.com.netcracker.project.model.ProjectDAO;
import main.com.netcracker.project.model.entity.Project;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProjectDaoTest {

  private ApplicationContext context;
  private ProjectDAO projectDAO;


  @Before
  public void setUp() {
    Locale.setDefault(Locale.ENGLISH);
    context = new ClassPathXmlApplicationContext("Spring-Module.xml");
    projectDAO = (ProjectDAO) context.getBean("projectDAO");
  }

  @Test
  public void findProjectByIDTest() {
    Project project = projectDAO.findProjectByProjectId(BigInteger.ONE);
    assertThat(BigInteger.valueOf(2), is(project.getProjectId()));
  }

  @Test
  public void findProjectByNameTest() {
    Project project = projectDAO.findProjectByName("PROJECT1");
    assertThat("PROJECT1", is(project.getName()));
  }

  @Test
  public void findProjectByDateTest() {
    SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yy");
    Date d = null;
    try {
      d = sdf.parse("12.12.12");
    } catch (ParseException e) {
      e.printStackTrace();
    }

    Project project = projectDAO.findProjectByDate(d);

    assertThat(BigInteger.valueOf(2), is(project.getProjectId()));
  }
}
