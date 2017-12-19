package com.netcracker.project.model.entity;

import java.math.BigInteger;
import java.util.Date;

public class WorkPeriod {

  private BigInteger workPeriodId;
  private BigInteger userId;
  private BigInteger projectId;
  private Date startWorkDate;
  private Date endWorkDate;
  private WorkPeriodStatus workPeriodStatus;

  private WorkPeriod(WorkPeriodBuilder builder) {
    this.workPeriodId = builder.workPeriodId;
    this.userId = builder.userId;
    this.projectId = builder.projectId;
    this.startWorkDate = builder.startWorkDate;
    this.endWorkDate = builder.endWorkDate;
    this.workPeriodStatus = builder.workPeriodStatus;
  }

  private WorkPeriod() {
  }

  public BigInteger getWorkPeriodId() {
    return workPeriodId;
  }

  public BigInteger getUserId() {
    return userId;
  }

  public BigInteger getProjectId() {
    return projectId;
  }

  public Date getStartWorkDate() {
    return startWorkDate;
  }

  public Date getEndWorkDate() {
    return endWorkDate;
  }

  public WorkPeriodStatus getWorkPeriodStatus() {
    return workPeriodStatus;
  }

  public static class WorkPeriodBuilder {

    private BigInteger workPeriodId;
    private BigInteger userId;
    private BigInteger projectId;
    private Date startWorkDate;
    private Date endWorkDate;
    private WorkPeriodStatus workPeriodStatus;


    public WorkPeriodBuilder workPeriodId(BigInteger workPeriodId) {
      this.workPeriodId = workPeriodId;
      return this;
    }

    public WorkPeriodBuilder userId(BigInteger userId) {
      this.userId = userId;
      return this;
    }

    public WorkPeriodBuilder projectId(BigInteger projectId) {
      this.projectId = projectId;
      return this;
    }

    public WorkPeriodBuilder startWorkDate(Date startWorkDate) {
      this.startWorkDate = startWorkDate;
      return this;
    }

    public WorkPeriodBuilder endWorkDate(Date endWorkDate) {
      this.endWorkDate = endWorkDate;
      return this;
    }

    public WorkPeriodBuilder workPeriodStatus(
        WorkPeriodStatus workPeriodStatus) {
      this.workPeriodStatus = workPeriodStatus;
      return this;
    }

    public WorkPeriod build() {
      return new WorkPeriod(this);
    }

  }


  public enum WorkPeriodStatus {
    WORKING(0), FIRED(1);

    private int id;

    WorkPeriodStatus(int id) {
      this.id = id;
    }

    public int getId() {
      return id;
    }
  }

  @Override
  public String toString() {
    return "WorkPeriod{" +
        "workPeriodId=" + workPeriodId +
        ", userId=" + userId +
        ", projectId=" + projectId +
        ", startWorkDate=" + startWorkDate +
        ", endWorkDate=" + endWorkDate +
        ", workPeriodStatus=" + workPeriodStatus +
        '}';
  }
}