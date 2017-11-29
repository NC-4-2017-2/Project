import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import main.com.netcracker.project.model.ProjectDAO;
import main.com.netcracker.project.model.entity.Project;
import main.com.netcracker.project.model.impl.mappers.MapperDateConverter;
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
    Project project = projectDAO.findProjectByProjectId(BigInteger.valueOf(4));
    assertThat(BigInteger.valueOf(4), is(project.getProjectId()));
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

    List<Project> projects = projectDAO.findProjectByDate(d);

    if (projects.size() == 1) {
      assertThat(BigInteger.valueOf(4), is(projects.get(0).getProjectId()));
    }
    if (projects.size() == 2) {
      assertThat(BigInteger.valueOf(4), is(projects.get(0).getProjectId()));
      assertThat(BigInteger.valueOf(200), is(projects.get(1).getProjectId()));
    }
  }

  @Test
  public void createProjectTest() {
    MapperDateConverter mp = new MapperDateConverter();
    Date start = mp.convertStringToDate("13.11.14");
    Date end = mp.convertStringToDate("13.11.20");
    Project project = new Project.ProjectBuilder()
        .projectId(BigInteger.valueOf(204))
        .name("Test")
        .startDate(start)
        .endDate(end)
        .build();
    project.setProjectManager(BigInteger.ONE);

    projectDAO.createProject(project);

    Project result = projectDAO.findProjectByProjectId(BigInteger.valueOf(204));
    assertThat(BigInteger.valueOf(204), is(result.getProjectId()));
  }
}


