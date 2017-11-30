package main.com.netcracker.project.model.impl;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import main.com.netcracker.project.model.TaskDAO;
import main.com.netcracker.project.model.TaskDAO.TaskPriority;
import main.com.netcracker.project.model.TaskDAO.TaskStatus;
import main.com.netcracker.project.model.TaskDAO.TaskType;
import main.com.netcracker.project.model.entity.Status;
import main.com.netcracker.project.model.entity.Task;
import main.com.netcracker.project.model.entity.Task.TaskBuilder;
import main.com.netcracker.project.model.entity.User;
import main.com.netcracker.project.model.impl.mappers.MapperDateConverter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:Spring-Module.xml"})
public class TaskDaoTest {

  private ApplicationContext context;
  private TaskDAO taskDao;

  @Before
  public void setUp() {
    Locale.setDefault(Locale.ENGLISH);

    context = new ClassPathXmlApplicationContext("Spring-Module.xml");
    taskDao = (TaskDAO) context.getBean("taskDAO");
  }

  @Test
  public void findTaskByProjectId(){
    Task task = (Task) taskDao.findTaskByProjectId(BigInteger.valueOf(6));
    assertThat(BigInteger.valueOf(6), is(task.getProjectId()));
  }

  @Test
  public void findTaskByDate(){
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.mm.yy");
    Date date = null;
    try {
      date = dateFormat.parse("29.11.2017");
    } catch (ParseException e) {
      e.printStackTrace();
    }

    Task task = (Task) taskDao.findTaskByUserIdAndDate(date, BigInteger.valueOf(1));

    assertThat(BigInteger.valueOf(2), is(task.getTaskId()));
  }

  @Test
  public void findTaskByUserId(){
    Task task = (Task) taskDao.findTaskByUserId(BigInteger.valueOf(1));
    assertThat(BigInteger.valueOf(2), is(task.getTaskId()));
  }

  @Test
  public void findTaskByPriority(){
    Task task = (Task) taskDao.findTaskByPriority(TaskPriority.CRITICAL);
    Assert.assertEquals(TaskPriority.CRITICAL, task.getStatus());
  }

  @Test
  public void findTaskByStatusAndUserId(){
    Task task = (Task) taskDao.findTaskByStatusAndUserId(TaskStatus.CLOSED, BigInteger.valueOf(1));
    assertThat(BigInteger.valueOf(2), is(task.getTaskId()));
  }

  @Test
  public void insertTask(){
    Date date = new Date();
    Collection<User> users = new ArrayList<>();
    Task task1 = new TaskBuilder()
        .name("Main")
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
        .users(users)
        .projectId(BigInteger.valueOf(4))
        .build();

    taskDao.createTask(task1);
  }

  @Test
  public void updateTask(){

    Date date = new Date();
    Collection<User> users = new ArrayList<>();

    Task createdTask = new Task.TaskBuilder()
        .name("Main")
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
        .users(users)
        .projectId(BigInteger.valueOf(4))
        .build();


    Task updatingTask = new Task.TaskBuilder()
        .name("Main")
        .name("ERP-12")
        .taskType(TaskType.PROJECT_TASK)
        .startDate(date)
        .endDate(date)
        .plannedEndDate(date)
        .priority(TaskPriority.HIGH)
        .status(TaskStatus.READY_FOR_TESTING)
        .description("Good")
        .reopenCounter(Integer.valueOf(1))
        .comments("easy but ineteresting")
        .authorId(BigInteger.valueOf(1))
        .users(users)
        .projectId(BigInteger.valueOf(4))
        .build();

    taskDao.createTask(createdTask);
    taskDao.updateTask(createdTask);
  }

  @Test
  public void updateStatus(){
    taskDao.updateStatus(Status.APPROVED, BigInteger.valueOf(1));
  }

}
