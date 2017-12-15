package main.com.netcracker.project.controllers.task;


import java.math.BigInteger;
import java.util.Date;
import main.com.netcracker.project.model.TaskDAO;
import main.com.netcracker.project.model.TaskDAO.TaskPriority;
import main.com.netcracker.project.model.TaskDAO.TaskStatus;
import main.com.netcracker.project.model.TaskDAO.TaskType;

public class TaskData {
  private BigInteger taskId;
  private String name;
  private TaskDAO.TaskType taskType;
  private Date startDate;
  private Date plannedEndDate;
  private Date endDate;
  private TaskDAO.TaskPriority priority;
  private TaskDAO.TaskStatus status;
  private String description;
  private BigInteger authorId;
  private BigInteger userId;
  private String comments;
  private Integer reopenCounter;
  private BigInteger projectId;


  public TaskData(BigInteger taskId,
      TaskType taskType, String name, Date startDate,
      Date plannedEndDate, Date endDate,
      TaskPriority priority,
      TaskStatus status, String description,
      BigInteger authorId, BigInteger userId, String comments,
      Integer reopenCounter, BigInteger projectId) {
    this.taskId = taskId;
    this.taskType = taskType;
    this.name = name;
    this.startDate = startDate;
    this.plannedEndDate = plannedEndDate;
    this.endDate = endDate;
    this.priority = priority;
    this.status = status;
    this.description = description;
    this.authorId = authorId;
    this.userId = userId;
    this.comments = comments;
    this.reopenCounter = reopenCounter;
    this.projectId = projectId;
  }


  public BigInteger getTaskId() {
    return taskId;
  }

  public void setTaskId(BigInteger taskId) {
    this.taskId = taskId;
  }

  public TaskType getTaskType() {
    return taskType;
  }

  public void setTaskType(TaskType taskType) {
    this.taskType = taskType;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getPlannedEndDate() {
    return plannedEndDate;
  }

  public void setPlannedEndDate(Date plannedEndDate) {
    this.plannedEndDate = plannedEndDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public TaskPriority getPriority() {
    return priority;
  }

  public void setPriority(
      TaskPriority priority) {
    this.priority = priority;
  }

  public TaskStatus getStatus() {
    return status;
  }

  public void setStatus(TaskStatus status) {
    this.status = status;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigInteger getAuthorId() {
    return authorId;
  }

  public void setAuthorId(BigInteger authorId) {
    this.authorId = authorId;
  }

  public BigInteger getUserId() {
    return userId;
  }

  public void setUserId(BigInteger userId) {
    this.userId = userId;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public Integer getReopenCounter() {
    return reopenCounter;
  }

  public void setReopenCounter(Integer reopenCounter) {
    this.reopenCounter = reopenCounter;
  }

  public BigInteger getProjectId() {
    return projectId;
  }

  public void setProjectId(BigInteger projectId) {
    this.projectId = projectId;
  }
}
