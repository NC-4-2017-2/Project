package main.com.netcracker.project.model.entity;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import main.com.netcracker.project.model.ProjectDAO.OCStatus;

public class Project {

  private BigInteger projectId;
  private String name;
  private Date startDate;
  private Date endDate;
  private OCStatus projectStatus;
  private User projectManager;
  private Collection<User> users;
  private Collection<Task> tasks;
  private Collection<Sprint> sprints;


  private Project(ProjectBuilder builder) {
    this.projectId = builder.projectId;
    this.name = builder.name;
    this.startDate = builder.startDate;
    this.endDate = builder.endDate;
    this.projectStatus = builder.status;
    this.tasks = builder.tasks;
    this.sprints = builder.sprints;
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

  public OCStatus getProjectStatus() {
    return projectStatus;
  }

  public User getProjectManager() {
    return projectManager;
  }

  public Collection<User> getUsers() {
    return users;
  }

  public Collection<Task> getTasks() {
    return tasks;
  }

  public Collection<Sprint> getSprints() {
    return sprints;
  }

  public void setUsers(Collection<User> users) {
    this.users = users;
  }

  public void setProjectManager(User projectManager) {
    this.projectManager = projectManager;
  }

  public static class ProjectBuilder {

    private BigInteger projectId;
    private String name;
    private Date startDate;
    private Date endDate;
    private OCStatus status;
    private Collection<Task> tasks;
    private Collection<Sprint> sprints;

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

    public ProjectBuilder projectStatus(OCStatus status) {
      this.status = status;
      return this;
    }

    public ProjectBuilder tasks(Collection<Task> tasks) {
      this.tasks = tasks;
      return this;
    }

    public ProjectBuilder sprint(Collection<Sprint> sprint) {
      this.sprints = sprint;
      return this;
    }

    public Project build() {
      return new Project(this);
    }

  }

}
