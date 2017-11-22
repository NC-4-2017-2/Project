package main.net.cracker.project.model.entity;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import main.net.cracker.project.model.TaskDAO;

public class Task implements TaskDAO {

  private BigInteger taskId;
  private TaskType taskType;
  private String name;
  private Date startDate;
  private Date endDate;
  private TaskPriority priority;
  private TaskStatus status;
  private String description;
  private BigInteger authorId;
  private Collection<User> users;
  private String comments;
  private Integer reopenCounter;
  private BigInteger projectId;


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

  public Collection<User> getUsers() {
    return users;
  }

  public void setUsers(
      Collection<User> users) {
    this.users = users;
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

  public void createTask(Task task) {

  }

  public void updateTask(BigInteger id) {

  }

  public Collection<Task> findTaskByProjectld(BigInteger id) {
    return null;
  }

  public Collection<Task> findTaskByUserld(BigInteger id) {
    return null;
  }

  public Collection<Task> findTaskByDate(Date date, BigInteger id) {
    return null;
  }

  public Collection<Task> findTaskByPriority(TaskPriority taskPriority,
      BigInteger employeeID) {
    return null;
  }

  public Task findTaskByStatusAndUserId(String status, BigInteger id) {
    return null;
  }

  public String updateStatus(TaskStatus status) {
    return null;
  }
}
