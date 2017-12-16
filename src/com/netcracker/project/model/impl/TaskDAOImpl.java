package com.netcracker.project.model.impl;

import com.netcracker.project.model.TaskDAO;
import com.netcracker.project.model.entity.Task;
import com.netcracker.project.model.impl.mappers.MapperDateConverter;
import com.netcracker.project.model.impl.mappers.TaskMapper;
import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

public class TaskDAOImpl implements TaskDAO {

  private static final Logger logger = Logger.getLogger(TaskDAOImpl.class);
  private JdbcTemplate template;
  private MapperDateConverter mapperDateConverter = new MapperDateConverter();

  public void setDataSource(DataSource dataSource) {
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
        mapperDateConverter.convertDateToString(task.getStartDate()),
        mapperDateConverter.convertDateToString(task.getEndDate()),
        mapperDateConverter.convertDateToString(task.getPlannedEndDate()),
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
    updateTaskByStartDate(
        mapperDateConverter.convertDateToString(task.getStartDate()),
        task.getTaskId());
    updateTaskByEndDate(
        mapperDateConverter.convertDateToString(task.getEndDate()),
        task.getTaskId());
    updateTaskByPlannedEndDate(
        mapperDateConverter.convertDateToString(task.getPlannedEndDate()),
        task.getTaskId());
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
    logger.info(
        "Entering updateTaskName(name=" + name + "," + " taskId=" + taskId
            + ")");
    template.update(UPDATE_TASK_BY_NAME, name, taskId);
  }

  private void updateTaskByType(TaskType typeTask, BigInteger taskId) {
    logger.info(
        "Entering updateTaskByType(typeTask=" + typeTask + "," + " taskId="
            + taskId + ")");
    template.update(UPDATE_TASK_BY_TYPE, typeTask.getId(), taskId);
  }

  private void updateTaskByStartDate(String startDate, BigInteger taskId) {
    logger.info(
        "Entering updateStartDate(startDate=" + startDate + "," + " taskId="
            + taskId + ")");
    template.update(UPDATE_TASK_BY_START_DATE, startDate, taskId);
  }

  private void updateTaskByEndDate(String endDate, BigInteger taskId) {
    logger.info(
        "Entering updateTaskByEndDate(endDate=" + endDate + "," + " taskId="
            + taskId + ")");
    template.update(UPDATE_TASK_BY_END_DATE, endDate, taskId);
  }

  private void updateTaskByPlannedEndDate(String plannedEndDate,
      BigInteger taskId) {
    logger.info(
        "Entering updateTaskByPlannedEndDate(plannedEndDate=" + plannedEndDate
            + "," + " taskId=" + taskId + ")");
    template.update(UPDATE_TASK_BY_PLANNED_END_DATE, plannedEndDate, taskId);
  }


  private void updateTaskByTaskPriority(TaskPriority taskPriority,
      BigInteger taskId) {
    logger.info("Entering updateTaskPriority(taskPriority=" + taskPriority + ","
        + " taskId=" + taskId + ")");
    template.update(UPDATE_TASK_BY_TASK_PRIORITY, taskPriority.getId(), taskId);
  }

  private void updateTaskByStatus(TaskStatus taskStatus, BigInteger taskId) {
    logger.info(
        "Entering updateTaskStatus(taskStatus=" + taskStatus + "," + " taskId="
            + taskId + ")");
    template.update(UPDATE_TASK_BY_TASK_STATUS, taskStatus.getId(), taskId);
  }

  private void updateTaskByDescription(String description, BigInteger taskId) {
    logger.info(
        "Entering updateTaskDescription(description=" + description + ","
            + " taskId=" + taskId + ")");
    template.update(UPDATE_TASK_BY_DESCRIPTION, description, taskId);
  }

  private void updateTaskByReopenCounter(Integer reopenCounter,
      BigInteger taskId) {
    logger.info(
        "Entering updateReopenCounter(reopenCounter=" + reopenCounter + ","
            + " taskId=" + taskId + ")");
    template.update(UPDATE_TASK_BY_REOPEN_COUNTER, reopenCounter, taskId);
  }

  private void updateTaskByComment(String comment, BigInteger taskId) {
    logger.info(
        "Entering updateComment(comment=" + comment + "," + " taskId=" + taskId
            + ")");
    template.update(UPDATE_TASK_BY_COMMENT, comment, taskId);
  }

  private void updateTaskByTaskAuthor(BigInteger authorId, BigInteger taskId) {
    logger.info(
        "Entering updateAuthorId(authorId=" + authorId + "," + " taskId="
            + taskId + ")");
    template.update(UPDATE_TASK_BY_TASK_AUTHOR, authorId, taskId);
  }

  private void updateTaskByUserId(BigInteger userId, BigInteger taskId) {
    logger.info(
        "Entering updateUserId(userId=" + userId + "," + " taskId=" + taskId
            + ")");
    template.update(UPDATE_TASK_BY_TASK_USER_ID, userId, taskId);
  }

  private void updateTaskByProjectId(BigInteger projectId, BigInteger taskId) {
    logger.info(
        "Entering updateProjectId(projectId=" + projectId + "," + " taskId="
            + taskId + ")");
    template.update(UPDATE_TASK_BY_TASK_PROJECT, projectId, taskId);
  }

  @Override
  public Collection<Task> findTaskByProjectId(BigInteger projectId) {
    logger.info("Entering findTaskByProjectId(id=" + projectId + ")");
    return template.query(FIND_TASK_BY_PROJECT_ID, new Object[]{projectId},
        new TaskMapper());
  }

  @Override
  public Collection<Task> findTaskByUserId(BigInteger userId) {
    logger.info("Entering findTaskByUserId(id=" + userId + ")");
    return template
        .query(FIND_TASK_BY_USER_ID, new Object[]{userId}, new TaskMapper());
  }

  @Override
  public Collection<Task> findTaskByUserIdAndDate(Date date,
      BigInteger userId) {
    logger.info(
        "Entering findTaskByUserIdAndDate(date=" + date + ", " + "id=" + userId
            + ")");
    MapperDateConverter dateConverter = new MapperDateConverter();
    String formattedDate = dateConverter.convertDateToString(date);
    return template.query(FIND_TASK_BY_USER_ID_AND_DATE,
        new Object[]{formattedDate, userId}, new TaskMapper());
  }

  @Override
  public Collection<Task> findTaskByPriorityAndUserId(Integer taskPriority,
      BigInteger userId) {
    logger
        .info("Entering findTaskByPriority(userId=" + userId + ", " + "taskPriority="
            + taskPriority + ")");
    return template.query(FIND_TASK_BY_PRIORITY_AND_USER_ID, new Object[]{taskPriority, userId},
        new TaskMapper());
  }


  @Override
  public Collection<Task> findTaskByStatusAndUserId(BigInteger id,
      Integer taskStatus) {
    logger.info("Entering findTaskByStatusAndUserId(id=" + id + ", " + "status="
        + taskStatus + ")");
    return template
        .query(FIND_TASK_BY_STATUS_AND_USER_ID, new Object[]{id, taskStatus},
            new TaskMapper());
  }

  @Override
  @Transactional
  public void updateStatus(Integer taskStatus, BigInteger taskId) {
    logger.info(
        "Entering updateStatus(status=" + taskStatus + ")" + "Entering taskId="
            + taskId);
    template.update(UPDATE_TASK_BY_TASK_STATUS, taskStatus, taskId);
  }
}
