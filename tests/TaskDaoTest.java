import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import main.com.netcracker.project.model.TaskDAO;
import main.com.netcracker.project.model.TaskDAO.TaskPriority;
import main.com.netcracker.project.model.TaskDAO.TaskStatus;
import main.com.netcracker.project.model.TaskDAO.TaskType;
import main.com.netcracker.project.model.entity.Task;
import main.com.netcracker.project.model.impl.mappers.MapperDateConverter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
    Task task = (Task) taskDao.findTaskByProjectId(BigInteger.valueOf(1));
    assertThat(BigInteger.valueOf(2), is(task.getProjectId()));
  }

  @Test
  public void findTaskByDate(){
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.mm.yy");
    Date date = null;
    try {
      date = dateFormat.parse("29.11.17");
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
    Task task = (Task) taskDao.findTaskByPriority(TaskPriority.CRITICAL, BigInteger.valueOf(1));
    assertThat(BigInteger.valueOf(2), is(task.getTaskId()));
  }

  @Test
  public void findTaskByStatusAndUserId(){
    Task task = (Task) taskDao.findTaskByStatusAndUserId(TaskStatus.CLOSED, BigInteger.valueOf(1));
    assertThat(BigInteger.valueOf(2), is(task.getTaskId()));
  }

  @Test
  public void insertTask(){
    MapperDateConverter mp = new MapperDateConverter();
    Date startDate = mp.convertStringToDate("28.11.17");
    Date endDate = mp.convertStringToDate("29.11.17");
    Date plannedEndDate = mp.convertStringToDate("29.11.17");
    Task task1 = new Task.TaskBuilder()
        .name("ERP-12")
        .taskType(TaskType.PROJECT_TASK)
        .startDate(startDate)
        .endDate(endDate)
        .plannedEndDate(plannedEndDate)
        .priority(TaskPriority.CRITICAL)
        .status(TaskStatus.COMPLETED)
        .description("Good")
        .reopenCounter(1)
        .comments("hard but ineteresting").build();

        taskDao.createTask(task1);
  }

  @Test
  public void updateTask(){
    MapperDateConverter mp = new MapperDateConverter();
    Date startDate = mp.convertStringToDate("28.11.17");
    Date endDate = mp.convertStringToDate("29.11.17");
    Date plannedEndDate = mp.convertStringToDate("29.11.17");
    Task createdTask = new Task.TaskBuilder()
        .name("ERP-12")
        .taskType(TaskType.PROJECT_TASK)
        .startDate(startDate)
        .endDate(endDate)
        .plannedEndDate(plannedEndDate)
        .priority(TaskPriority.CRITICAL)
        .status(TaskStatus.COMPLETED)
        .description("Good")
        .reopenCounter(1)
        .comments("hard but ineteresting").build();


    Task updatingTask = new Task.TaskBuilder()
        .name("ERP-13")
        .taskType(TaskType.REQUEST_TASK)
        .startDate(startDate)
        .endDate(endDate)
        .plannedEndDate(plannedEndDate)
        .priority(TaskPriority.HIGH)
        .status(TaskStatus.CLOSED)
        .description("Bad")
        .reopenCounter(1)
        .comments("easy but ineteresting").build();

    taskDao.createTask(createdTask);
    taskDao.updateTask(updatingTask);
  }

  @Test
  public void updateStatus(){
    taskDao.updateStatus(TaskStatus.CLOSED);
  }

}
