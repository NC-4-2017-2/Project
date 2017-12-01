package main.com.netcracker.project.model.impl;

import static org.junit.Assert.assertEquals;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import javax.sql.DataSource;
import main.com.netcracker.project.model.TaskDAO;
import main.com.netcracker.project.model.TaskDAO.TaskPriority;
import main.com.netcracker.project.model.TaskDAO.TaskStatus;
import main.com.netcracker.project.model.TaskDAO.TaskType;
import main.com.netcracker.project.model.entity.Status;
import main.com.netcracker.project.model.entity.Task;
import main.com.netcracker.project.model.entity.Task.TaskBuilder;
import main.com.netcracker.project.model.impl.mappers.MapperDateConverter;
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
public class TaskDaoTest {

  private ApplicationContext context;
  private TaskDAO taskDao;
  private JdbcTemplate template;
  private DataSource dataSource;
  private Task task;
  private static final String DELETE_FROM_ATTRIBUTES = "DELETE FROM ATTRIBUTES WHERE OBJECT_ID = ?";
  private static final String DELETE_FROM_OBJECTS = "DELETE FROM OBJECTS WHERE OBJECT_ID = ?";
  private static final String DELETE_FROM_OBJREFERENCE = "DELETE FROM OBJREFERENCE WHERE OBJECT_ID = ?";

  @Before
  public void setUp() {
    Locale.setDefault(Locale.ENGLISH);
    context = new ClassPathXmlApplicationContext("Spring-Module.xml");
    taskDao = (TaskDAO) context.getBean("taskDAO");
    template = new JdbcTemplate(dataSource);
  }

  @Autowired
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }


  @Test
  public void test1createTask(){
    MapperDateConverter mdc = new MapperDateConverter();
    Date date = mdc.convertStringToDate("01.12.17");
    task = new TaskBuilder()
        .taskId(BigInteger.valueOf(300))
        .name("ERP-12")
        .taskType(TaskType.PROJECT_TASK)
        .startDate(date)
        .endDate(date)
        .plannedEndDate(date)
        .priority(TaskPriority.CRITICAL)
        .status(TaskStatus.COMPLETED)
        .description("Good")
        .reopenCounter(Integer.valueOf(1))
        .comments("hard but ineteresting")
        .authorId(BigInteger.valueOf(1))
        .userId(BigInteger.valueOf(1))
        .projectId(BigInteger.valueOf(4))
        .build();

    taskDao.createTask(task);
  }

  @Test
  public void test2updateTask(){

    MapperDateConverter mdc = new MapperDateConverter();
    Date date = mdc.convertStringToDate("15.11.11");

    Task taskUpdates = new TaskBuilder()
        .taskId(BigInteger.valueOf(300))
        .name("ERP-13")
        .taskType(TaskType.PROJECT_TASK)
        .startDate(date)
        .endDate(date)
        .plannedEndDate(date)
        .priority(TaskPriority.CRITICAL)
        .status(TaskStatus.COMPLETED)
        .description("Good")
        .reopenCounter(Integer.valueOf(1))
        .comments("hard but ineteresting")
        .authorId(BigInteger.valueOf(1))
        .userId(BigInteger.valueOf(1))
        .projectId(BigInteger.valueOf(4))
        .build();

    taskDao.updateTask(taskUpdates);

    Collection<Task> taskByProjectId = taskDao.findTaskByProjectId(BigInteger.valueOf(4));

    assertEquals("[Task{taskId=300,"
        + " name='ERP-13', taskType=PROJECT_TASK,"
        + " startDate=Tue Nov 15 00:00:00 EET 2011,"
        + " endDate=Tue Nov 15 00:00:00 EET 2011,"
        + " plannedEndDate=Tue Nov 15 00:00:00 EET 2011, priority=CRITICAL,"
        + " status=COMPLETED, description='Good',"
        + " reopenCounter=1, comments='hard but ineteresting', authorId=1, "
        + " userId=1, projectId=4 " + "}]", taskByProjectId.toString());
  }


  @Test
  public void findTaskByProjectId(){
    Collection<Task> tasks = taskDao.findTaskByProjectId(BigInteger.valueOf(4));
  }

  @Test
  public void findStatusByUserIdAndDate(){
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.mm.yy");
    Date date = null;
    try {
      date = dateFormat.parse("01.12.17");
    } catch (ParseException e) {
      e.printStackTrace();
    }

    Collection<Task> task = taskDao.findTaskByUserIdAndDate(date, BigInteger.valueOf(1));

  }


  @Test
  public void findTaskByUserId(){
    Collection<Task> task = taskDao.findTaskByUserId(BigInteger.valueOf(1));
  }

  @Test
  public void findTaskByPriority(){
    Collection<Task> task =  taskDao.findTaskByPriority(TaskPriority.HIGH.getId());

  }

  @Test
  public void findTaskByStatusAndUserId(){
    Collection<Task> task = taskDao.findTaskByStatusAndUserId(BigInteger.valueOf(1), TaskStatus.COMPLETED.getId());
  }

  @Test
  public void updateStatus(){
    taskDao.updateStatus(Status.APPROVED.getId(), BigInteger.valueOf(1));
  }

  @Test
  public void deleteForTable(){
    BigInteger id = BigInteger.valueOf(300);
    template.update(DELETE_FROM_ATTRIBUTES, new Object[]{id});
    template.update(DELETE_FROM_OBJECTS, new Object[]{id});
    template.update(DELETE_FROM_OBJREFERENCE, new Object[]{id});
  }

}
