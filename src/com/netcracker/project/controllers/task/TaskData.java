package com.netcracker.project.controllers.task;


import com.netcracker.project.model.TaskDAO.TaskPriority;
import com.netcracker.project.model.TaskDAO.TaskStatus;
import com.netcracker.project.model.TaskDAO.TaskType;
import java.math.BigInteger;

public class TaskData {
  private BigInteger taskId;
  private String name;
  private TaskType taskType;
  private String startDate;
  private String plannedEndDate;
  private String endDate;
  private TaskPriority priority;
  private TaskStatus status;
  private String description;
  private Integer reopenCounter;
  private String comments;
  private BigInteger authorId;
  private BigInteger userId;
  private BigInteger projectId;


  public TaskData(BigInteger taskId, String name,
      TaskType taskType, String startDate, String plannedEndDate,
      String endDate, TaskPriority priority,
      TaskStatus status, String description, Integer reopenCounter,
      String comments, BigInteger authorId, BigInteger userId,
      BigInteger projectId) {
    this.taskId = taskId;
    this.name = name;
    this.taskType = taskType;
    this.startDate = startDate;
    this.plannedEndDate = plannedEndDate;
    this.endDate = endDate;
    this.priority = priority;
    this.status = status;
    this.description = description;
    this.reopenCounter = reopenCounter;
    this.comments = comments;
    this.authorId = authorId;
    this.userId = userId;
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

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getPlannedEndDate() {
    return plannedEndDate;
  }

  public void setPlannedEndDate(String plannedEndDate) {
    this.plannedEndDate = plannedEndDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
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
