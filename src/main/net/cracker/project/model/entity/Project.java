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


  private Project(ProjectBuilder builder) {
    this.projectId = builder.projectId;
    this.name = builder.name;
    this.startDate = builder.startDate;
    this.endDate = builder.endDate;
    this.status = builder.status;
    this.projectManager = builder.projectManager;
    this.users = builder.users;
    this.tasks = builder.tasks;
    this.sprint = builder.sprint;
  }

  public BigInteger getProjectId() {
    return projectId;
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

  public ProjectStatus getStatus() {
    return status;
  }

  public BigInteger getProjectManager() {
    return projectManager;
  }

  public Collection<User> getUsers() {
    return users;
  }

  public Collection<Task> getTasks() {
    return tasks;
  }

  public Sprint getSprint() {
    return sprint;
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

    public ProjectBuilder() {
    }

    public ProjectBuilder projectId(BigInteger projectId) {
      this.projectId = projectId;
      return this;
    }

    public ProjectBuilder name(String name) {
      this.name = name;
      return this;
    }

    public ProjectBuilder startDate(Date startDate) {
      this.startDate = startDate;
      return this;
    }

    public ProjectBuilder endDate(Date endDate) {
      this.endDate = endDate;
      return this;
    }

    public ProjectBuilder projectStatus(ProjectStatus status) {
      this.status = status;
      return this;
    }

    public ProjectBuilder projectManager(BigInteger projectManager) {
      this.projectManager = projectManager;
      return this;
    }

    public ProjectBuilder users(Collection<User> users) {
      this.users = users;
      return this;
    }

    public ProjectBuilder tasks(Collection<Task> tasks) {
      this.tasks = tasks;
      return this;
    }

    public ProjectBuilder sprint(Sprint sprint) {
      this.sprint = sprint;
      return this;
    }

    public Project build() {
      return new Project(this);
    }

  }

}
