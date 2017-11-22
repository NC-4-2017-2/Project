package main.net.cracker.project.model.entity;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import main.net.cracker.project.model.ProjectDAO.ProjectStatus;

public class Project {

  private BigInteger projectId;
  private String name;
  private Date startDate;
  private Date endDate;
  private ProjectStatus status;
  private BigInteger projectManager;
  private Collection<User> users;
  private Collection<Task> tasks;
  private Sprint sprint;


  public BigInteger getProjectId() {
    return projectId;
  }

  public void setProjectId(BigInteger projectId) {
    this.projectId = projectId;
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

  public ProjectStatus getStatus() {
    return status;
  }

  public void setStatus(ProjectStatus status) {
    this.status = status;
  }

  public BigInteger getProjectManager() {
    return projectManager;
  }

  public void setProjectManager(BigInteger projectManager) {
    this.projectManager = projectManager;
  }

  public Collection<User> getUsers() {
    return users;
  }

  public void setUsers(Collection<User> users) {
    this.users = users;
  }

  public Collection<Task> getTasks() {
    return tasks;
  }

  public void setTasks(Collection<Task> tasks) {
    this.tasks = tasks;
  }

  public Sprint getSprint() {
    return sprint;
  }

  public void setSprint(Sprint sprint) {
    this.sprint = sprint;
  }

  public static class ProjectBuilder {

    private BigInteger projectId;
    private String name;
    private Date startDate;
    private Date endDate;
    private ProjectStatus status;
    private BigInteger projectManager;
    private Collection<User> users;
    private Collection<Task> tasks;
    private Sprint sprint;

    public ProjectBuilder(BigInteger projectId, String name,
        Date startDate, Date endDate) {
      this.projectId = projectId;
      this.name = name;
      this.startDate = startDate;
      this.endDate = endDate;
    }

  }

}
