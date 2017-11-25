package main.com.netcracker.project.model.entity;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

import main.com.netcracker.project.model.TaskDAO;

public class Task {

  private BigInteger taskId;
  private TaskDAO.TaskType taskType;
  private String name;
  private Date startDate;
  private Date plannedEndDate;
  private Date endDate;
  private TaskDAO.TaskPriority priority;
  private TaskDAO.TaskStatus status;
  private String description;
  private BigInteger authorId;
  private Collection<User> users;
  private String comments;
  private Integer reopenCounter;
  private BigInteger projectId;

  private Task(TaskBuilder builder) {
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
    this.users = builder.users;
    this.comments = builder.comments;
    this.reopenCounter = builder.reopenCounter;
    this.projectId = builder.projectId;
  }

  public BigInteger getTaskId() {
    return taskId;
  }

  public TaskDAO.TaskType getTaskType() {
    return taskType;
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

  public TaskDAO.TaskPriority getPriority() {
    return priority;
  }

  public TaskDAO.TaskStatus getStatus() {
    return status;
  }

  public String getDescription() {
    return description;
  }

  public BigInteger getAuthorId() {
    return authorId;
  }

  public Collection<User> getUsers() {
    return users;
  }

  public String getComments() {
    return comments;
  }

  public Integer getReopenCounter() {
    return reopenCounter;
  }

  public BigInteger getProjectId() {
    return projectId;
  }

  public Date getPlannedEndDate() {
    return plannedEndDate;
  }

  public static class TaskBuilder {

    private BigInteger taskId;
    private TaskDAO.TaskType taskType;
    private String name;
    private Date startDate;
    private Date plannedEndDate;
    private Date endDate;
    private TaskDAO.TaskPriority priority;
    private TaskDAO.TaskStatus status;
    private String description;
    private BigInteger authorId;
    private Collection<User> users;
    private String comments;
    private Integer reopenCounter;
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

    public TaskBuilder taskType(TaskDAO.TaskType taskType) {
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

    public TaskBuilder priority(TaskDAO.TaskPriority priority) {
      this.priority = priority;
      return this;
    }

    public TaskBuilder status(TaskDAO.TaskStatus status) {
      this.status = status;
      return this;
    }

    public TaskBuilder description(String description) {
      this.description = description;
      return this;
    }

    public TaskBuilder authorId(BigInteger authorId) {
      this.authorId = authorId;
      return this;
    }

    public TaskBuilder users(Collection<User> users) {
      this.users = users;
      return this;
    }

    public TaskBuilder comments(String comments) {
      this.comments = comments;
      return this;
    }

    public TaskBuilder reopenCounter(Integer reopenCounter) {
      this.reopenCounter = reopenCounter;
      return this;
    }

    public TaskBuilder projectId(BigInteger projectId) {
      this.projectId = projectId;
      return this;
    }

    public Task build() {
      return new Task(this);
    }
  }
}
