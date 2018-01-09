package com.netcracker.project.model.entity;

import com.netcracker.project.model.enums.TaskPriority;
import com.netcracker.project.model.enums.TaskStatus;
import com.netcracker.project.model.enums.TaskType;
import java.math.BigInteger;
import java.util.Date;



public class Task {

  private BigInteger taskId;
  private TaskType taskType;
  private String name;
  private Date startDate;
  private Date plannedEndDate;
  private Date endDate;
  private TaskPriority priority;
  private TaskStatus status;
  private String description;
  private BigInteger authorId;
  private BigInteger userId;
  private String comments;
  private Integer reopenCounter;
  private BigInteger projectId;

  public Task(TaskBuilder builder) {
    this.taskId = builder.taskId;
    this.taskType = builder.taskType;
    this.name = builder.name;
    this.startDate = builder.startDate;
    this.plannedEndDate = builder.plannedEndDate;
    this.endDate = builder.endDate;
    this.priority = builder.priority;
    this.status = builder.status;
    this.description = builder.description;
    this.authorId = builder.authorId;
    this.userId = builder.userId;
    this.comments = builder.comments;
    this.reopenCounter = builder.reopenCounter;
    this.projectId = builder.projectId;
  }

  public Task() {

  }


  public TaskType getTaskType() {
    return taskType;
  }

  public TaskPriority getPriority() {
    return priority;
  }

  public TaskStatus getStatus() {
    return status;
  }

  public BigInteger getTaskId() {
    return taskId;
  }

  public String getName() {
    return name;
  }

  public Date getStartDate() {
    return startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public Date getPlannedEndDate() {
    return plannedEndDate;
  }

  public String getDescription() {
    return description;
  }

  public Integer getReopenCounter() {
    return reopenCounter;
  }

  public String getComments() {
    return comments;
  }

  public BigInteger getAuthorId() {
    return authorId;
  }

  public BigInteger getUserId() {
    return userId;
  }

  public BigInteger getProjectId() {
    return projectId;
  }


  public void setTaskType(TaskType taskType) {
    this.taskType = taskType;
  }

  public void setPriority(
      TaskPriority priority) {
    this.priority = priority;
  }

  public void setStatus(TaskStatus status) {
    this.status = status;
  }

  public void setReopenCounter(Integer reopenCounter) {
    this.reopenCounter = reopenCounter;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public static class TaskBuilder {

    private BigInteger taskId;
    private TaskType taskType;
    private String name;
    private Date startDate;
    private Date plannedEndDate;
    private Date endDate;
    private TaskPriority priority;
    private TaskStatus status;
    private String description;
    private Integer reopenCounter;
    private String comments;
    private BigInteger authorId;
    private BigInteger userId;
    private BigInteger projectId;

    public TaskBuilder() {

    }


    public TaskBuilder taskId(BigInteger taskId) {
     this.taskId = taskId;
      return this;
    }

    public TaskBuilder name(String name) {
      this.name = name;
      return this;
    }

    public TaskBuilder taskType(TaskType taskType) {
      this.taskType = taskType;
      return this;
    }

    public TaskBuilder startDate(Date startDate) {
      this.startDate = startDate;
      return this;
    }

    public TaskBuilder plannedEndDate(Date plannedEndDate) {
      this.plannedEndDate = plannedEndDate;
      return this;
    }

    public TaskBuilder endDate(Date endDate) {
      this.endDate = endDate;
      return this;
    }

    public TaskBuilder priority(TaskPriority priority) {
      this.priority = priority;
      return this;
    }

    public TaskBuilder status(TaskStatus status) {
      this.status = status;
      return this;
    }

    public TaskBuilder description(String description) {
      this.description = description;
      return this;
    }

    public TaskBuilder reopenCounter(Integer reopenCounter) {
      this.reopenCounter = reopenCounter;
      return this;
    }

    public TaskBuilder comments(String comments) {
      this.comments = comments;
      return this;
    }

    public TaskBuilder authorId(BigInteger authorId) {
      this.authorId = authorId;
      return this;
    }

    public TaskBuilder userId(BigInteger userId) {
      this.userId = userId;
      return this;
    }


    public TaskBuilder projectId(BigInteger projectId) {
      this.projectId = projectId;
      return this;
    }


    public BigInteger getAuthorId() {
      return authorId;
    }

    public BigInteger getUserId() {
      return userId;
    }

    public BigInteger getProjectId() {
      return projectId;
    }

    public void setAuthorId(BigInteger authorId) {
      this.authorId = authorId;
    }

    public void setUserId(BigInteger userId) {
      this.userId = userId;
    }

    public void setProjectId(BigInteger projectId) {
      this.projectId = projectId;
    }

    public Task build() {
      return new Task(this);
    }

  }
}
