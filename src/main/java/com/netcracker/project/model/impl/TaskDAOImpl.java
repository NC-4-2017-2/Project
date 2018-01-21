package com.netcracker.project.model.impl;

import com.netcracker.project.model.TaskDAO;
import com.netcracker.project.model.entity.Task;
import com.netcracker.project.model.enums.TaskPriority;
import com.netcracker.project.model.enums.TaskStatus;
import com.netcracker.project.model.enums.TaskType;
import com.netcracker.project.model.impl.mappers.TaskMapper;
import java.time.LocalDate;
import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TaskDAOImpl implements TaskDAO {

  private static final Logger logger = Logger.getLogger(TaskDAOImpl.class);
  private JdbcTemplate template;

  @Autowired
  public void setDataSource(DataSource dataSource) {
    template = new JdbcTemplate(dataSource);
  }

  @Override
  @Transactional
  public void createTask(Task task) {
    logger.info("Entering createTask(task=" + task + ")");
    this.template.update(CREATE_TASK, new Object[]{
        task.getName(),
        task.getTaskType().getId(),
        task.getStartDate(),
        task.getEndDate(),
        task.getPlannedEndDate(),
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
    updateTaskByStartDate(task.getStartDate(), task.getTaskId());
    updateTaskByPlannedEndDate(task.getPlannedEndDate(), task.getTaskId());
    updateTaskByTaskPriority(task.getPriority(), task.getTaskId());
    updateTaskByStatus(task.getStatus(), task.getTaskId());
    updateTaskByDescription(task.getDescription(), task.getTaskId());
    updateTaskByComment(task.getComments(), task.getTaskId());
    updateTaskByTaskAuthor(task.getAuthorId(), task.getTaskId());
    updateTaskByUserId(task.getUserId(), task.getTaskId());
    updateTaskByProjectId(task.getProjectId(), task.getTaskId());
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

  private void updateTaskByStartDate(Date startDate, BigInteger taskId) {
    logger.info(
        "Entering updateStartDate(startDate=" + startDate + "," + " taskId="
            + taskId + ")");
    template.update(UPDATE_TASK_BY_START_DATE, startDate, taskId);
  }

  private void updateTaskByEndDate(Date endDate, BigInteger taskId) {
    logger.info(
        "Entering updateTaskByEndDate(endDate=" + endDate + "," + " taskId="
            + taskId + ")");
    template.update(UPDATE_TASK_BY_END_DATE, endDate, taskId);
  }

  private void updateTaskByPlannedEndDate(Date plannedEndDate,
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

  private void updateTaskByReopenCounter(Integer reopenCounter, BigInteger taskId) {
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
  public Collection<Task> findTaskByProjectIdAndTaskId(BigInteger projectId, BigInteger taskId) {
    logger.info("Entering findTaskByProjectIdAndStatus(projectId=" + projectId + "taskId=" + taskId + ")");
    return template.query(FIND_TASK_BY_PROJECT_ID_AND_TASK_ID, new Object[]{projectId, taskId},
        new TaskMapper());
  }


  @Override
  public Collection<Task> findTaskByProjectIdAndStatus(BigInteger projectId, Integer status) {
    logger.info("Entering findTaskByProjectIdAndStatus(id=" + projectId + "status" + status + ")");
    return template.query(FIND_TASK_BY_PROJECT_ID_AND_STATUS, new Object[]{projectId, status},
        new TaskMapper());
  }

  @Override
  public Collection<Task> findTaskByProjectId(BigInteger projectId) {
    logger.info("Entering findTaskByProjectId(projectId=" + projectId + ")");
    return template.query(FIND_TASK_BY_PROJECT_ID, new Object[]{projectId},
        new TaskMapper());
  }

  @Override
  public Task findTaskByTaskId(BigInteger taskId) {
    logger.info("Entering findTaskByTaskId. taskId=" + taskId + ")");
    return template.queryForObject(FIND_TASK_BY_TASK_ID, new Object[]{taskId},
        new TaskMapper());
  }

  @Override
  public Collection<Task> findTaskByProjectIdAndPriority(BigInteger projectId, Integer taskPriority) {
    logger.info("Entering findTaskByProjectIdAndPriority(projectId=" + projectId + "taskPriority" + taskPriority + ")");
    return template.query(FIND_TASK_BY_PROJECT_ID_AND_PRIORITY, new Object[]{projectId, taskPriority},
        new TaskMapper());
  }

  @Override
  public Collection<Task> findTaskByUserId (BigInteger userId) {
    logger.info("Entering findTaskByUserIdAndDate(userId=" + userId  + ")");
    return template.query(FIND_TASK_BY_USER_ID, new Object[]{userId}, new TaskMapper());
  }

  @Override
  public Collection<Task> findTaskByUserIdAndPriority(BigInteger userId, Integer taskPriority) {
    logger.info("Entering findTaskByPriority(userId=" + userId + ", " + "taskPriority="
        + taskPriority + ")");
    return template.query(FIND_TASK_BY_USER_ID_AND_PRIORITY, new Object[]{userId, taskPriority},
        new TaskMapper());
  }

  @Override
  public Integer findIfTaskExists(BigInteger id) {
    logger.info("Entering findIfTaskExists(id=" + id + ")");
    return template
        .queryForObject(FIND_TASK_IF_EXIST, new Object[]{id},
            Integer.class);
  }

  @Override
  public Collection<Task> findTaskByUserIdAndStatus(BigInteger userId, Integer taskStatus) {
    logger.info("Entering findTaskByStatusAndUserId(id=" + userId + ", " + "status="
        + taskStatus + ")");
    return template.query(FIND_TASK_BY_USER_ID_AND_STATUS, new Object[]{userId, taskStatus},
        new TaskMapper());
  }

  @Override
  public void updateStatus(Integer taskStatus, BigInteger taskId) {
    logger.info(
        "Entering updateStatus(status=" + taskStatus + ")" + "Entering taskId="
            + taskId);
    template.update(UPDATE_TASK_BY_TASK_STATUS, taskStatus, taskId);
  }

  @Override
  public void updateEndDate(Date date, BigInteger taskId) {
    logger.info(
        "Entering updateStatus(date=" + date + ")" + "Entering taskId="
            + taskId);
    template.update(UPDATE_END_DATE, date, taskId);
  }

  @Override
  public void updateReopenCounter(Integer counter, BigInteger taskId) {
    logger.info(
        "Entering updateStatus(counter=" + counter + ")" + "Entering taskId="
            + taskId);
    template.update(UPDATE_REOPEN_COUNTER, counter, taskId);
  }

  @Override
  public void updateComment(String comment, BigInteger taskId) {
    logger.info(
        "Entering updateStatus(comment=" + comment + ")" + "Entering taskId="
            + taskId);
    template.update(UPDATE_TASK_BY_COMMENT, comment, taskId);
  }

  @Override
  public BigInteger findTaskIdByPMLogin(String pmLogin) {
    logger.info("Entering findProjectIdByPMLogin(startDate=" + pmLogin + ")");
    return template
        .queryForObject(FIND_TASK_ID_BY_PM_LOGIN, new Object[]{pmLogin},
            BigInteger.class);
  }

  @Override
  public BigInteger findTaskIdByUserLogin(String pmLogin) {
    logger.info("Entering findProjectIdByPMLogin(startDate=" + pmLogin + ")");
    return template
        .queryForObject(FIND_TASK_ID_BY_USER_LOGIN, new Object[]{pmLogin},
            BigInteger.class);
  }

  @Override
  public Collection<Task> findTaskByUserAndStatusPerPeriod(BigInteger userId,
      Integer taskStatus, Date startDate, Date endDate) {
    logger.info("Entering findTaskByUserAndStatusPerPeriod(userId=" + userId + ", " + "taskStatus="
        + taskStatus + "startDate" + startDate +  "endDate" + endDate +")");
    return template.query(FIND_TASK_ID_BY_USER_AND_STATUS_PER_PERIOD, new Object[]{userId, taskStatus, startDate, endDate},
        new TaskMapper());
  }
}