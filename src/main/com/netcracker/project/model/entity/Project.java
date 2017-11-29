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
  private BigInteger projectManager;
  private Collection<BigInteger> users;
  private Collection<BigInteger> tasks;
  private Collection<Sprint> sprints;


  private Project(ProjectBuilder builder) {
    this.projectId = builder.projectId;
    this.name = builder.name;
    this.startDate = builder.startDate;
    this.endDate = builder.endDate;
    this.projectStatus = builder.status;
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

  public BigInteger getProjectManager() {
    return projectManager;
  }

  public Collection<BigInteger> getUsers() {
    return users;
  }

  public Collection<BigInteger> getTasks() {
    return tasks;
  }

  public Collection<Sprint> getSprints() {
    return sprints;
  }

  public void setUsers(Collection<BigInteger> users) {
    this.users = users;
  }

  public void setProjectManager(BigInteger projectManager) {
    this.projectManager = projectManager;
  }

  public void setSprints(Collection<Sprint> sprints) {
    this.sprints = sprints;
  }

  public void setTasks(Collection<BigInteger> tasks) {
    this.tasks = tasks;
  }

  public static class ProjectBuilder {

    private BigInteger projectId;
    private String name;
    private Date startDate;
    private Date endDate;
    private OCStatus status;

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


    public Project build() {
      return new Project(this);
    }

  }

}
