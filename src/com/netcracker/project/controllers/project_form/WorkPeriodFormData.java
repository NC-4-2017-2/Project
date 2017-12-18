package com.netcracker.project.controllers.project_form;

import com.netcracker.project.model.UserDAO.WorkPeriod.WorkPeriodStatus;
import java.math.BigInteger;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class WorkPeriodFormData {

  private String name;
  private BigInteger workPeriodId;
  private BigInteger userId;
  private BigInteger projectId;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date startWorkDate;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date endWorkDate;
  private WorkPeriodStatus workPeriodStatus;

  public WorkPeriodFormData(BigInteger workPeriodId, BigInteger userId,
      Date startWorkDate,
      Date endWorkDate,
      WorkPeriodStatus workPeriodStatus) {
    this.workPeriodId = workPeriodId;
    this.userId = userId;
    this.startWorkDate = startWorkDate;
    this.endWorkDate = endWorkDate;
    this.workPeriodStatus = workPeriodStatus;
  }

  public WorkPeriodFormData(
      WorkPeriodStatus workPeriodStatus) {
    this.workPeriodStatus = workPeriodStatus;
  }

  public WorkPeriodFormData() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigInteger getWorkPeriodId() {
    return workPeriodId;
  }

  public void setWorkPeriodId(BigInteger workPeriodId) {
    this.workPeriodId = workPeriodId;
  }

  public BigInteger getUserId() {
    return userId;
  }

  public void setUserId(BigInteger userId) {
    this.userId = userId;
  }

  public BigInteger getProjectId() {
    return projectId;
  }

  public void setProjectId(BigInteger projectId) {
    this.projectId = projectId;
  }

  public Date getStartWorkDate() {
    return startWorkDate;
  }

  public void setStartWorkDate(Date startWorkDate) {
    this.startWorkDate = startWorkDate;
  }

  public Date getEndWorkDate() {
    return endWorkDate;
  }

  public void setEndWorkDate(Date endWorkDate) {
    this.endWorkDate = endWorkDate;
  }

  public WorkPeriodStatus getWorkPeriodStatus() {
    return workPeriodStatus;
  }

  public void setWorkPeriodStatus(
      WorkPeriodStatus workPeriodStatus) {
    this.workPeriodStatus = workPeriodStatus;
  }
}
