package main.com.netcracker.project.model.impl;

import static org.junit.Assert.assertEquals;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        .status(TaskStatus.OPENED)
        .description("Good")
        .reopenCounter(1)
        .comments("hard but ineteresting")
        .authorId(BigInteger.valueOf(1))
        .userId(BigInteger.valueOf(1))
        .projectId(BigInteger.valueOf(5))
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
        .status(TaskStatus.OPENED)
        .description("Good")
        .reopenCounter(1)
        .comments("hard but ineteresting")
        .authorId(BigInteger.valueOf(1))
        .userId(BigInteger.valueOf(1))
        .projectId(BigInteger.valueOf(5))
        .build();

    taskDao.updateTask(taskUpdates);


    ArrayList<Task> tasks = (ArrayList<Task>) taskDao.findTaskByProjectId(BigInteger.valueOf(300));
    tasks.add(taskUpdates);

    assertEquals("ERP-13", tasks.get(0).getName());
  }

  @Test
  public void test3updateStatus(){
    taskDao.updateStatus(TaskStatus.OPENED.getId(), BigInteger.valueOf(1));
  }

  @Test
  public void test4findTaskByProjectId(){
    Collection<Task> tasks = taskDao.findTaskByProjectId(BigInteger.valueOf(4));
  }

  @Test
  public void test5findStatusByUserIdAndDate(){
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
  public void test6findTaskByUserId(){
    Collection<Task> task = taskDao.findTaskByUserId(BigInteger.valueOf(1));
  }

  @Test
  public void test7findTaskByPriority(){
    Collection<Task> task =  taskDao.findTaskByPriority(TaskPriority.HIGH.getId());

  }

  @Test
  public void test8findTaskByStatusAndUserId(){
    Collection<Task> task = taskDao.findTaskByStatusAndUserId(BigInteger.valueOf(1), TaskStatus.OPENED.getId());
  }


  @Test
  public void deleteForTable(){
    BigInteger id = BigInteger.valueOf(300);
    template.update(DELETE_FROM_ATTRIBUTES, new Object[]{id});
    template.update(DELETE_FROM_OBJECTS, new Object[]{id});
    template.update(DELETE_FROM_OBJREFERENCE, new Object[]{id});
  }

}
