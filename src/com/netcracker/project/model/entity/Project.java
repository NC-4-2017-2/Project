package com.netcracker.project.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netcracker.project.model.ProjectDAO.OCStatus;
import com.netcracker.project.model.UserDAO.WorkPeriod;
import com.netcracker.project.model.impl.mappers.MapperDateConverter;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

public class Project {

  private BigInteger projectId;
  private String name;
  private Date startDate;
  private Date endDate;
  private OCStatus projectStatus;
  private BigInteger projectManagerId;
  private Collection<BigInteger> usersId;
  private Collection<BigInteger> tasksId;
  private Collection<WorkPeriod> workPeriods;
  private Collection<Sprint> sprints;

  private Project(ProjectBuilder builder) {
    this.projectId = builder.projectId;
    this.name = builder.name;
    this.startDate = builder.startDate;
    this.endDate = builder.endDate;
  }

  public Project() {
  }

  public BigInteger getProjectId() {
    return projectId;
  }

  public String getName() {
    return name;
  }

  @JsonSerialize(using = MapperDateConverter.class)
  public Date getStartDate() {
    return startDate;
  }

  @JsonSerialize(using = MapperDateConverter.class)
  public Date getEndDate() {
    return endDate;
  }

  public OCStatus getProjectStatus() {
    return projectStatus;
  }

  public BigInteger getProjectManagerId() {
    return projectManagerId;
  }

  public Collection<BigInteger> getUsersId() {
    return usersId;
  }

  public Collection<BigInteger> getTasksId() {
    return tasksId;
  }

  public Collection<Sprint> getSprints() {
    return sprints;
  }

  public void setUsersId(Collection<BigInteger> usersId) {
    this.usersId = usersId;
  }

  public void setProjectManagerId(BigInteger projectManagerId) {
    this.projectManagerId = projectManagerId;
  }

  public void setSprints(Collection<Sprint> sprints) {
    this.sprints = sprints;
  }

  public void setTasksId(Collection<BigInteger> tasksId) {
    this.tasksId = tasksId;
  }

  public void setProjectStatus(OCStatus projectStatus) {
    this.projectStatus = projectStatus;
  }

  public Collection<WorkPeriod> getWorkPeriods() {
    return workPeriods;
  }

  public void setWorkPeriods(
      Collection<WorkPeriod> workPeriods) {
    this.workPeriods = workPeriods;
  }

  public static class ProjectBuilder {

    private BigInteger projectId;
    private String name;
    private Date startDate;
    private Date endDate;

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

    public Project build() {
      return new Project(this);
    }

  }

}