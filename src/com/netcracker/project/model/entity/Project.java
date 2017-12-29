package com.netcracker.project.model.entity;

import com.netcracker.project.model.enums.OCStatus;
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

  private Project(ProjectBuilder builder) {
    this.projectId = builder.projectId;
    this.name = builder.name;
    this.startDate = builder.startDate;
    this.endDate = builder.endDate;
    this.projectManagerId = builder.projectManagerId;
    this.projectStatus = builder.projectStatus;
  }

  public Project() {
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

  public BigInteger getProjectManagerId() {
    return projectManagerId;
  }

  public static class ProjectBuilder {

    private BigInteger projectId;
    private String name;
    private Date startDate;
    private Date endDate;
    private OCStatus projectStatus;
    private BigInteger projectManagerId;

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

    public ProjectBuilder projectStatus(OCStatus projectStatus) {
      this.projectStatus = projectStatus;
      return this;
    }

    public ProjectBuilder projectManagerId(BigInteger projectManagerId) {
      this.projectManagerId = projectManagerId;
      return this;
    }

    public Project build() {
      return new Project(this);
    }

  }

}
