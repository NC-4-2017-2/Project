package main.com.netcracker.project.model.impl;

import java.util.logging.Logger;
import javax.sql.DataSource;
import main.com.netcracker.project.model.TaskDAO;
import main.com.netcracker.project.model.entity.Task;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import main.com.netcracker.project.model.impl.mappers.MapperDateConverter;
import main.com.netcracker.project.model.impl.mappers.TaskMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;


public class TaskDAOImpl implements TaskDAO {

    private static final Logger logger = Logger.getLogger(TaskDAOImpl.class.getName());
    private JdbcTemplate template;
    private MapperDateConverter mapperDateConverter = new MapperDateConverter();

    public void setDataSource(DataSource dataSource){
        template = new JdbcTemplate(dataSource);
    }

    @Override
    @Transactional
    public void createTask(Task task) {
      logger.info("Entering createTask(task=" + task + ")");
      this.template.update(CREATE_TASK, new Object[]{
            task.getTaskId(),
            task.getName(),
            task.getTaskType().getId(),
            mapperDateConverter.convertDateTosString(task.getStartDate()),
            mapperDateConverter.convertDateTosString(task.getEndDate()),
            mapperDateConverter.convertDateTosString(task.getPlannedEndDate()),
            task.getPriority().getId(),
            task.getStatus().getId(),
            task.getDescription(),
            task.getReopenCounter(),
            task.getComments(),
            task.getAuthorId(),
            task.getUserId(),
            task.getProjectId()
        });
    }

    @Override
    @Transactional
    public void updateTask(Task task) {
      logger.info("Entering updateTask(task=" + task + ")");
      updateTaskByName(task.getName(), task.getTaskId());
      updateTaskByType(task.getTaskType(), task.getTaskId());
      updateTaskByStartDate(mapperDateConverter.convertDateTosString(task.getStartDate()), task.getTaskId());
      updateTaskByEndDate(mapperDateConverter.convertDateTosString(task.getEndDate()), task.getTaskId());
      updateTaskByPlannedEndDate(mapperDateConverter.convertDateTosString(task.getPlannedEndDate()), task.getTaskId());
      updateTaskByTaskPriority(task.getPriority(), task.getTaskId());
      updateTaskByStatus(task.getStatus(), task.getTaskId());
      updateTaskByDescription(task.getDescription(), task.getTaskId());
      updateTaskByReopenCounter(task.getReopenCounter(), task.getTaskId());
      updateTaskByComment(task.getComments(), task.getTaskId());
      updateTaskByTaskAuthor(task.getAuthorId(), task.getTaskId());
      updateTaskByUserId(task.getUserId(), task.getTaskId());
      updateTaskByProjectId(task.getProjectId(), task.getProjectId());

 }

    private void updateTaskByName(String name, BigInteger taskId) {
      logger.info("Entering updateTaskName(name=" + name + "," + " taskId=" + taskId + ")");
      template.update(UPDATE_TASK_BY_NAME, name, taskId);
    }

    private void updateTaskByType(TaskType typeTask, BigInteger taskId) {
      logger.info("Entering updateTaskByType(typeTask=" + typeTask + "," + " taskId=" + taskId + ")");
      template.update(UPDATE_TASK_BY_TYPE, typeTask.getId(), taskId);
    }

    private void updateTaskByStartDate(String startDate, BigInteger taskId) {
      logger.info("Entering updateStartDate(startDate=" + startDate + "," + " taskId=" + taskId + ")");
      template.update(UPDATE_TASK_BY_START_DATE, startDate, taskId);
    }

    private void updateTaskByEndDate(String endDate, BigInteger taskId) {
      logger.info("Entering updateTaskByEndDate(endDate=" + endDate + "," + " taskId=" + taskId + ")");
      template.update(UPDATE_TASK_BY_END_DATE, endDate, taskId);
    }

    private void updateTaskByPlannedEndDate(String plannedEndDate, BigInteger taskId) {
      logger.info("Entering updateTaskByPlannedEndDate(plannedEndDate=" + plannedEndDate + "," + " taskId=" + taskId + ")");
      template.update(UPDATE_TASK_BY_PLANNED_END_DATE, plannedEndDate, taskId);
    }


    private void updateTaskByTaskPriority(TaskPriority taskPriority, BigInteger taskId) {
      logger.info("Entering updateTaskPriority(taskPriority=" + taskPriority + "," + " taskId=" + taskId + ")");
      template.update(UPDATE_TASK_BY_TASK_PRIORITY, taskPriority.getId(), taskId);
    }

    private void updateTaskByStatus(TaskStatus taskStatus, BigInteger taskId) {
      logger.info("Entering updateTaskStatus(taskStatus=" + taskStatus + "," + " taskId=" + taskId + ")");
      template.update(UPDATE_TASK_BY_TASK_STATUS, taskStatus.getId(), taskId);
    }

    private void updateTaskByDescription(String description, BigInteger taskId) {
      logger.info("Entering updateTaskDescription(description=" + description + "," + " taskId=" + taskId + ")");
      template.update(UPDATE_TASK_BY_DESCRIPTION, description, taskId);
    }

    private void updateTaskByReopenCounter(Integer reopenCounter, BigInteger taskId) {
      logger.info("Entering updateReopenCounter(reopenCounter=" + reopenCounter + "," + " taskId=" + taskId + ")");
      template.update(UPDATE_TASK_BY_REOPEN_COUNTER, reopenCounter, taskId);
    }

    private void updateTaskByComment(String comment, BigInteger taskId) {
      logger.info("Entering updateComment(comment=" + comment + "," + " taskId=" + taskId + ")");
      template.update(UPDATE_TASK_BY_COMMENT, comment, taskId);
    }

    private void updateTaskByTaskAuthor(BigInteger authorId, BigInteger taskId) {
      logger.info("Entering updateAuthorId(authorId=" + authorId + "," + " taskId=" + taskId + ")");
      template.update(UPDATE_TASK_BY_TASK_AUTHOR, authorId, taskId);
    }

    private void updateTaskByUserId(BigInteger userId, BigInteger taskId) {
      logger.info("Entering updateUserId(userId=" + userId + "," + " taskId=" + taskId + ")");
      template.update(UPDATE_TASK_BY_TASK_USER_ID, userId, taskId);
    }

    private void updateTaskByProjectId(BigInteger projectId, BigInteger taskId) {
      logger.info("Entering updateProjectId(projectId=" + projectId + "," + " taskId=" + taskId + ")");
      template.update(UPDATE_TASK_BY_TASK_PROJECT, projectId, taskId);
    }

    @Override
    public Collection<Task> findTaskByProjectId(BigInteger projectId) {
      logger.info("Entering findTaskByProjectId(id=" + projectId + ")");
      return template.query(FIND_TASK_BY_PROJECT_ID, new Object[]{projectId}, new TaskMapper());
    }

    @Override
    public Collection<Task> findTaskByUserId(BigInteger userId) {
      logger.info("Entering findTaskByUserId(id=" + userId + ")");
      return template.query(FIND_TASK_BY_USER_ID, new Object[]{userId}, new TaskMapper());
    }

    @Override
    public Collection<Task> findTaskByUserIdAndDate(Date date, BigInteger userId) {
      logger.info("Entering findTaskByUserIdAndDate(date=" + date + ", " + "id=" + userId + ")");
      MapperDateConverter dateConverter = new MapperDateConverter();
      String formattedDate = dateConverter.convertDateTosString(date);
      return template.query(FIND_TASK_BY_USER_ID_AND_DATE, new Object[]{formattedDate, userId}, new TaskMapper());
    }

    @Override
    public Collection<Task> findTaskByPriority(Integer taskPriority) {
      logger.info("Entering findTaskByPriority(taskPriority=" + taskPriority + ")");
      return template.query(FIND_TASK_BY_PRIORITY, new Object[]{taskPriority}, new TaskMapper());
    }

    @Override
    public Collection<Task> findTaskByStatusAndUserId(BigInteger id, Integer taskStatus) {
      logger.info("Entering findTaskByStatusAndUserId(id=" + id + ", " + "status=" + taskStatus + ")");
      return template.query(FIND_TASK_BY_STATUS_AND_USER_ID, new Object[]{id, taskStatus}, new TaskMapper());
    }

    @Override
    @Transactional
    public void updateStatus(Integer taskStatus, BigInteger taskId) {
      logger.info("Entering updateStatus(status=" + taskStatus + ")" + "Entering taskId=" +  taskId);
      template.update(UPDATE_TASK_BY_TASK_STATUS, taskStatus, taskId);
    }

  private static final String CREATE_TASK =
      "INSERT ALL " +
      "INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) VALUES (300,NULL,3,?,NULL) " +
      "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (20,300,?,NULL,NULL) " +
      "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (21,300,NULL,NULL,?) " +
      "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (22,300,?,NULL,NULL) " +
      "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (23,300,?,NULL,NULL) " +
      "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (24,300,?,NULL,NULL) " +
      "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (25,300,NULL ,NULL,?)" +
      "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (26,300,NULL,NULL,?) " +
      "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (27,300,?,NULL,NULL) " +
      "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (28,300,?,NULL,NULL) " +
      "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (29,300,?,NULL,NULL) " +
      "INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) VALUES (30, 300, ?)" +
      "INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) VALUES (31, 300, ?)" +
      "INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) VALUES (32, 300, ?)" +
      "SELECT * " +
      "FROM Dual";

  private static final String FIND_TASK_BY_PROJECT_ID =
      "SELECT TASK.OBJECT_ID AS TASK_ID, TASK_NAME.VALUE AS NAME, TASK_TYPE_VALUE.VALUE AS TASK_TYPE, " +
      " TASK_START_DATE.VALUE AS START_DATE, TASK_END_DATE.VALUE AS END_DATE, TASK_PLANNED_END_DATE.VALUE AS PLANNED_END_DATE, " +
      " TASK_PRIORITY_VALUE.VALUE AS TASK_PRIORITY, TASK_STATUS_VALUE.VALUE AS STATUS, TASK_DESCRIPTION.VALUE AS TASK_DESCRIPTION, " +
      " TASK_REOPEN_COUNTER.VALUE AS REOPEN_COUNTER, TASK_COMMENT.VALUE AS TASK_COMMENT  " +
      " FROM OBJECTS TASK, OBJECTS PROJ, OBJTYPE TASK_TYPE, " +
      " ATTRIBUTES TASK_NAME, ATTRIBUTES TASK_TYPE, ATTRIBUTES TASK_START_DATE, ATTRIBUTES TASK_END_DATE, " +
      " ATTRIBUTES TASK_PLANNED_END_DATE, ATTRIBUTES TASK_PRIORITY, ATTRIBUTES TASK_STATUS, ATTRIBUTES TASK_DESCRIPTION, " +
      " ATTRIBUTES TASK_REOPEN_COUNTER, ATTRIBUTES TASK_COMMENT, " +
      " LISTVALUE TASK_TYPE_VALUE, LISTVALUE TASK_PRIORITY_VALUE, LISTVALUE TASK_STATUS_VALUE " +
      " WHERE TASK_TYPE.OBJECT_TYPE_ID = 3 AND TASK.OBJECT_TYPE_ID = TASK_TYPE.OBJECT_TYPE_ID " +
      " AND PROJ.OBJECT_ID = ? " +
      " AND PROJ.OBJECT_TYPE_ID = 2 " +
      " AND TASK_NAME.ATTR_ID = 20 AND TASK_NAME.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_TYPE.ATTR_ID = 21 AND TASK_TYPE.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_TYPE_VALUE.ATTR_ID = 21 AND TASK_TYPE_VALUE.LIST_VALUE_ID = TASK_TYPE.LIST_VALUE_ID " +
      " AND TASK_START_DATE.ATTR_ID = 22 AND TASK_START_DATE.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_END_DATE.ATTR_ID = 23 AND TASK_END_DATE.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_PLANNED_END_DATE.ATTR_ID = 24 AND TASK_PLANNED_END_DATE.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_PRIORITY.ATTR_ID = 25 AND TASK_PRIORITY.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_PRIORITY_VALUE.ATTR_ID = 25 AND TASK_PRIORITY_VALUE.LIST_VALUE_ID = TASK_PRIORITY.LIST_VALUE_ID " +
      " AND TASK_STATUS.ATTR_ID = 26 AND TASK_STATUS.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_STATUS_VALUE.ATTR_ID = 26 AND TASK_STATUS_VALUE.LIST_VALUE_ID = TASK_STATUS.LIST_VALUE_ID " +
      " AND TASK_DESCRIPTION.ATTR_ID = 27 AND TASK_DESCRIPTION.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_REOPEN_COUNTER.ATTR_ID = 28 AND TASK_REOPEN_COUNTER.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_COMMENT.ATTR_ID = 29 AND TASK_COMMENT.OBJECT_ID = TASK.OBJECT_ID ";

  private static final String FIND_TASK_BY_USER_ID =
      " SELECT TASK.OBJECT_ID AS TASK_ID, TASK_NAME.VALUE AS NAME, TASK_TYPE_VALUE.VALUE AS TASK_TYPE, " +
      " TASK_START_DATE.VALUE AS START_DATE, TASK_END_DATE.VALUE AS END_DATE, TASK_PLANNED_END_DATE.VALUE AS PLANNED_END_DATE, " +
      " TASK_PRIORITY_VALUE.VALUE AS TASK_PRIORITY, TASK_STATUS_VALUE.VALUE AS STATUS, TASK_DESCRIPTION.VALUE AS TASK_DESCRIPTION, " +
      " TASK_REOPEN_COUNTER.VALUE AS REOPEN_COUNTER, TASK_COMMENT.VALUE AS TASK_COMMENT " +
      " FROM OBJECTS TASK, OBJECTS EMP, OBJTYPE TASK_TYPE, " +
      " ATTRIBUTES TASK_NAME, ATTRIBUTES TASK_TYPE, ATTRIBUTES TASK_START_DATE, ATTRIBUTES TASK_END_DATE, " +
      " ATTRIBUTES TASK_PLANNED_END_DATE, ATTRIBUTES TASK_PRIORITY, ATTRIBUTES TASK_STATUS, ATTRIBUTES TASK_DESCRIPTION, " +
      " ATTRIBUTES TASK_REOPEN_COUNTER, ATTRIBUTES TASK_COMMENT, " +
      " LISTVALUE TASK_TYPE_VALUE, LISTVALUE TASK_PRIORITY_VALUE, LISTVALUE TASK_STATUS_VALUE " +
      " WHERE TASK_TYPE.OBJECT_TYPE_ID = 3 AND TASK.OBJECT_TYPE_ID = TASK_TYPE.OBJECT_TYPE_ID " +
      " AND EMP.OBJECT_ID = ? " +
      " AND EMP.OBJECT_TYPE_ID = 1 " +
      " AND TASK_NAME.ATTR_ID = 20 AND TASK_NAME.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_TYPE.ATTR_ID = 21 AND TASK_TYPE.OBJECT_ID = TASK.OBJECT_ID  " +
      " AND TASK_TYPE_VALUE.ATTR_ID = 21 AND TASK_TYPE_VALUE.LIST_VALUE_ID = TASK_TYPE.LIST_VALUE_ID " +
      " AND TASK_START_DATE.ATTR_ID = 22 AND TASK_START_DATE.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_END_DATE.ATTR_ID = 23 AND TASK_END_DATE.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_PLANNED_END_DATE.ATTR_ID = 24 AND TASK_PLANNED_END_DATE.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_PRIORITY.ATTR_ID = 25 AND TASK_PRIORITY.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_PRIORITY_VALUE.ATTR_ID = 25 AND TASK_PRIORITY_VALUE.LIST_VALUE_ID = TASK_PRIORITY.LIST_VALUE_ID " +
      " AND TASK_STATUS.ATTR_ID = 26 AND TASK_STATUS.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_STATUS_VALUE.ATTR_ID = 26 AND TASK_STATUS_VALUE.LIST_VALUE_ID = TASK_STATUS.LIST_VALUE_ID " +
      " AND TASK_DESCRIPTION.ATTR_ID = 27 AND TASK_DESCRIPTION.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_REOPEN_COUNTER.ATTR_ID = 28 AND TASK_REOPEN_COUNTER.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_COMMENT.ATTR_ID = 29 AND TASK_COMMENT.OBJECT_ID = TASK.OBJECT_ID ";

  private static final String FIND_TASK_BY_USER_ID_AND_DATE =
      "SELECT TASK.OBJECT_ID AS TASK_ID, TASK_NAME.VALUE AS NAME, TASK_TYPE_VALUE.VALUE AS TASK_TYPE, " +
      " TASK_START_DATE.VALUE AS START_DATE, TASK_END_DATE.VALUE AS END_DATE, TASK_PLANNED_END_DATE.VALUE AS PLANNED_END_DATE, " +
      " TASK_PRIORITY_VALUE.VALUE AS TASK_PRIORITY, TASK_STATUS_VALUE.VALUE AS STATUS, TASK_DESCRIPTION.VALUE AS TASK_DESCRIPTION, " +
      " TASK_REOPEN_COUNTER.VALUE AS REOPEN_COUNTER, TASK_COMMENT.VALUE AS TASK_COMMENT " +
      " FROM OBJECTS TASK, OBJECTS EMP, OBJTYPE TASK_TYPE, " +
      " ATTRIBUTES TASK_NAME, ATTRIBUTES TASK_TYPE, ATTRIBUTES TASK_START_DATE, ATTRIBUTES TASK_END_DATE, " +
      " ATTRIBUTES TASK_PLANNED_END_DATE, ATTRIBUTES TASK_PRIORITY, ATTRIBUTES TASK_STATUS, ATTRIBUTES TASK_DESCRIPTION, " +
      " ATTRIBUTES TASK_REOPEN_COUNTER, ATTRIBUTES TASK_COMMENT, " +
      " LISTVALUE TASK_TYPE_VALUE, LISTVALUE TASK_PRIORITY_VALUE, LISTVALUE TASK_STATUS_VALUE " +
      " WHERE TASK_TYPE.OBJECT_TYPE_ID = 3 AND TASK.OBJECT_TYPE_ID = TASK_TYPE.OBJECT_TYPE_ID " +
      " AND TASK_START_DATE.VALUE = ? " +
      " AND EMP.OBJECT_ID = ? " +
      " AND EMP.OBJECT_TYPE_ID = 1 " +
      " AND TASK_NAME.ATTR_ID = 20 AND TASK_NAME.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_TYPE.ATTR_ID = 21 AND TASK_TYPE.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_TYPE_VALUE.ATTR_ID = 21 AND TASK_TYPE_VALUE.LIST_VALUE_ID = TASK_TYPE.LIST_VALUE_ID " +
      " AND TASK_START_DATE.ATTR_ID = 22 AND TASK_START_DATE.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_END_DATE.ATTR_ID = 23 AND TASK_END_DATE.OBJECT_ID = TASK.OBJECT_ID" +
      " AND TASK_PLANNED_END_DATE.ATTR_ID = 24 AND TASK_PLANNED_END_DATE.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_PRIORITY.ATTR_ID = 25 AND TASK_PRIORITY.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_PRIORITY_VALUE.ATTR_ID = 25 AND TASK_PRIORITY_VALUE.LIST_VALUE_ID = TASK_PRIORITY.LIST_VALUE_ID " +
      " AND TASK_STATUS.ATTR_ID = 26 AND TASK_STATUS.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_STATUS_VALUE.ATTR_ID = 26 AND TASK_STATUS_VALUE.LIST_VALUE_ID = TASK_STATUS.LIST_VALUE_ID " +
      " AND TASK_DESCRIPTION.ATTR_ID = 27 AND TASK_DESCRIPTION.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_REOPEN_COUNTER.ATTR_ID = 28 AND TASK_REOPEN_COUNTER.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_COMMENT.ATTR_ID = 29 AND TASK_COMMENT.OBJECT_ID = TASK.OBJECT_ID ";

  private static final String FIND_TASK_BY_PRIORITY =
      "SELECT TASK.OBJECT_ID AS TASK_ID, TASK_NAME.VALUE AS NAME, TASK_TYPE_VALUE.VALUE AS TASK_TYPE," +
      " TASK_START_DATE.VALUE AS START_DATE, TASK_END_DATE.VALUE AS END_DATE, TASK_PLANNED_END_DATE.VALUE AS PLANNED_END_DATE," +
      " TASK_PRIORITY_VALUE.VALUE AS TASK_PRIORITY, TASK_STATUS_VALUE.VALUE AS STATUS, TASK_DESCRIPTION.VALUE AS TASK_DESCRIPTION," +
      " TASK_REOPEN_COUNTER.VALUE AS REOPEN_COUNTER, TASK_COMMENT.VALUE AS TASK_COMMENT " +
      " FROM OBJECTS TASK, OBJTYPE TASK_TYPE," +
      " ATTRIBUTES TASK_NAME, ATTRIBUTES TASK_TYPE, ATTRIBUTES TASK_START_DATE, ATTRIBUTES TASK_END_DATE, " +
      " ATTRIBUTES TASK_PLANNED_END_DATE, ATTRIBUTES TASK_PRIORITY, ATTRIBUTES TASK_STATUS, ATTRIBUTES TASK_DESCRIPTION, " +
      " ATTRIBUTES TASK_REOPEN_COUNTER, ATTRIBUTES TASK_COMMENT, " +
      " LISTVALUE TASK_TYPE_VALUE, LISTVALUE TASK_PRIORITY_VALUE, LISTVALUE TASK_STATUS_VALUE " +
      " WHERE TASK_TYPE.OBJECT_TYPE_ID = 3 AND TASK.OBJECT_TYPE_ID = TASK_TYPE.OBJECT_TYPE_ID " +
      " AND TASK_PRIORITY_VALUE.LIST_VALUE_ID = ? " +
      " AND TASK_NAME.ATTR_ID = 20 AND TASK_NAME.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_TYPE.ATTR_ID = 21 AND TASK_TYPE.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_TYPE_VALUE.ATTR_ID = 21 AND TASK_TYPE_VALUE.LIST_VALUE_ID = TASK_TYPE.LIST_VALUE_ID " +
      " AND TASK_START_DATE.ATTR_ID = 22 AND TASK_START_DATE.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_END_DATE.ATTR_ID = 23 AND TASK_END_DATE.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_PLANNED_END_DATE.ATTR_ID = 24 AND TASK_PLANNED_END_DATE.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_PRIORITY.ATTR_ID = 25 AND TASK_PRIORITY.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_PRIORITY_VALUE.ATTR_ID = 25 AND TASK_PRIORITY_VALUE.LIST_VALUE_ID = TASK_PRIORITY.LIST_VALUE_ID " +
      " AND TASK_STATUS.ATTR_ID = 26 AND TASK_STATUS.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_STATUS_VALUE.ATTR_ID = 26 AND TASK_STATUS_VALUE.LIST_VALUE_ID = TASK_STATUS.LIST_VALUE_ID " +
      " AND TASK_DESCRIPTION.ATTR_ID = 27 AND TASK_DESCRIPTION.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_REOPEN_COUNTER.ATTR_ID = 28 AND TASK_REOPEN_COUNTER.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_COMMENT.ATTR_ID = 29 AND TASK_COMMENT.OBJECT_ID = TASK.OBJECT_ID ";

  private static final String FIND_TASK_BY_STATUS_AND_USER_ID =
      " SELECT TASK.OBJECT_ID AS TASK_ID, TASK_NAME.VALUE AS NAME, TASK_TYPE_VALUE.VALUE AS TASK_TYPE, " +
      " TASK_START_DATE.VALUE AS START_DATE, TASK_END_DATE.VALUE AS END_DATE, TASK_PLANNED_END_DATE.VALUE AS PLANNED_END_DATE, " +
      " TASK_PRIORITY_VALUE.VALUE AS TASK_PRIORITY, TASK_STATUS_VALUE.VALUE AS STATUS, TASK_DESCRIPTION.VALUE AS TASK_DESCRIPTION, " +
      " TASK_REOPEN_COUNTER.VALUE AS REOPEN_COUNTER, TASK_COMMENT.VALUE AS TASK_COMMENT " +
      " FROM OBJECTS TASK, OBJECTS EMP, OBJTYPE TASK_TYPE, " +
      " ATTRIBUTES TASK_NAME, ATTRIBUTES TASK_TYPE, ATTRIBUTES TASK_START_DATE, ATTRIBUTES TASK_END_DATE, " +
      " ATTRIBUTES TASK_PLANNED_END_DATE, ATTRIBUTES TASK_PRIORITY, ATTRIBUTES TASK_STATUS, ATTRIBUTES TASK_DESCRIPTION, " +
      " ATTRIBUTES TASK_REOPEN_COUNTER, ATTRIBUTES TASK_COMMENT, " +
      " LISTVALUE TASK_TYPE_VALUE, LISTVALUE TASK_PRIORITY_VALUE, LISTVALUE TASK_STATUS_VALUE " +
      " WHERE TASK_TYPE.OBJECT_TYPE_ID = 3 AND TASK.OBJECT_TYPE_ID = TASK_TYPE.OBJECT_TYPE_ID " +
      " AND EMP.OBJECT_ID = ? AND EMP.OBJECT_TYPE_ID = 1 " +
      " AND TASK_STATUS_VALUE.LIST_VALUE_ID = ? " +
      " AND TASK_NAME.ATTR_ID = 20 AND TASK_NAME.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_TYPE.ATTR_ID = 21 AND TASK_TYPE.OBJECT_ID = TASK.OBJECT_ID "  +
      " AND TASK_TYPE_VALUE.ATTR_ID = 21 AND TASK_TYPE_VALUE.LIST_VALUE_ID = TASK_TYPE.LIST_VALUE_ID " +
      " AND TASK_START_DATE.ATTR_ID = 22 AND TASK_START_DATE.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_END_DATE.ATTR_ID = 23 AND TASK_END_DATE.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_PLANNED_END_DATE.ATTR_ID = 24 AND TASK_PLANNED_END_DATE.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_PRIORITY.ATTR_ID = 25 AND TASK_PRIORITY.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_PRIORITY_VALUE.ATTR_ID = 25 AND TASK_PRIORITY_VALUE.LIST_VALUE_ID = TASK_PRIORITY.LIST_VALUE_ID " +
      " AND TASK_STATUS.ATTR_ID = 26 AND TASK_STATUS.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_STATUS_VALUE.ATTR_ID = 26 AND TASK_STATUS_VALUE.LIST_VALUE_ID = TASK_STATUS.LIST_VALUE_ID " +
      " AND TASK_DESCRIPTION.ATTR_ID = 27 AND TASK_DESCRIPTION.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_REOPEN_COUNTER.ATTR_ID = 28 AND TASK_REOPEN_COUNTER.OBJECT_ID = TASK.OBJECT_ID " +
      " AND TASK_COMMENT.ATTR_ID = 29 AND TASK_COMMENT.OBJECT_ID = TASK.OBJECT_ID ";

  private static final String UPDATE_TASK_BY_NAME =
      " UPDATE ATTRIBUTES " +
      " SET ATTRIBUTES.VALUE = ?" +
      " WHERE ATTRIBUTES.OBJECT_ID = ? " +
      " AND ATTRIBUTES.ATTR_ID = 20";

  private static final String UPDATE_TASK_BY_TYPE =
      " UPDATE ATTRIBUTES " +
      " SET ATTRIBUTES.LIST_VALUE_ID = ?" +
      " WHERE ATTRIBUTES.OBJECT_ID = ?" +
      " AND ATTRIBUTES.ATTR_ID = 21";

  private static final String UPDATE_TASK_BY_START_DATE =
      " UPDATE ATTRIBUTES " +
      "SET ATTRIBUTES.VALUE = ?" +
      "WHERE ATTRIBUTES.OBJECT_ID = ?" +
      "AND ATTRIBUTES.ATTR_ID = 22";

  private static final String UPDATE_TASK_BY_END_DATE =
      " UPDATE ATTRIBUTES " +
      " SET ATTRIBUTES.VALUE = ?" +
      " WHERE ATTRIBUTES.OBJECT_ID = ? " +
      " AND ATTRIBUTES.ATTR_ID = 23" ;

  private static final String UPDATE_TASK_BY_PLANNED_END_DATE =
      " UPDATE ATTRIBUTES " +
      " SET ATTRIBUTES.VALUE = ?" +
      " WHERE ATTRIBUTES.OBJECT_ID = ? " +
      " AND ATTRIBUTES.ATTR_ID = 24" ;

  private static final String UPDATE_TASK_BY_TASK_PRIORITY =
      " UPDATE ATTRIBUTES " +
      " SET ATTRIBUTES.LIST_VALUE_ID = ? " +
      " WHERE ATTRIBUTES.OBJECT_ID = ? " +
      " AND ATTRIBUTES.ATTR_ID = 25 ";

  private static final String UPDATE_TASK_BY_TASK_STATUS =
      " UPDATE ATTRIBUTES " +
      " SET ATTRIBUTES.LIST_VALUE_ID = ? " +
      " WHERE ATTRIBUTES.OBJECT_ID = ? " +
      " AND ATTRIBUTES.ATTR_ID = 26 ";

  private static final String UPDATE_TASK_BY_DESCRIPTION =
      " UPDATE ATTRIBUTES " +
      " SET ATTRIBUTES.VALUE = ? " +
      " WHERE ATTRIBUTES.OBJECT_ID = ? " +
      " AND ATTRIBUTES.ATTR_ID = 27 ";

  private static final String UPDATE_TASK_BY_REOPEN_COUNTER =
      " UPDATE ATTRIBUTES " +
      " SET ATTRIBUTES.VALUE = ? " +
      " WHERE ATTRIBUTES.OBJECT_ID = ? " +
      " AND ATTRIBUTES.ATTR_ID = 28 ";

  private static final String UPDATE_TASK_BY_COMMENT =
      " UPDATE ATTRIBUTES " +
      " SET ATTRIBUTES.VALUE = ? " +
      " WHERE ATTRIBUTES.OBJECT_ID = ? " +
      " AND ATTRIBUTES.ATTR_ID = 29 ";

  private static final String UPDATE_TASK_BY_TASK_AUTHOR =
      " UPDATE ATTRIBUTES " +
      " SET ATTRIBUTES.VALUE = ? " +
      " WHERE ATTRIBUTES.OBJECT_ID = ? " +
      " AND ATTRIBUTES.ATTR_ID = 30 ";

  private static final String UPDATE_TASK_BY_TASK_USER_ID =
      " UPDATE ATTRIBUTES " +
      " SET ATTRIBUTES.VALUE = ? " +
      " WHERE ATTRIBUTES.OBJECT_ID = ? " +
      " AND ATTRIBUTES.ATTR_ID = 31 ";

  private static final String UPDATE_TASK_BY_TASK_PROJECT =
      " UPDATE ATTRIBUTES " +
      " SET ATTRIBUTES.VALUE = ? " +
      " WHERE ATTRIBUTES.OBJECT_ID = ? " +
      " AND ATTRIBUTES.ATTR_ID = 32 ";
}
