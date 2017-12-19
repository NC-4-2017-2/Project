package com.netcracker.project.controllers.project_form;

import com.netcracker.project.model.entity.WorkPeriod.WorkPeriodStatus;
import java.math.BigInteger;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class WorkPeriodFormData {

  private String name;
  private BigInteger workPeriodId;
  private BigInteger userId;
  private BigInteger projectId;
  private String startWorkDate;
  private String endWorkDate;
  private WorkPeriodStatus workPeriodStatus;

  public WorkPeriodFormData(BigInteger workPeriodId, BigInteger userId,
      String startWorkDate,
      String endWorkDate,
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

  public String getStartWorkDate() {
    return startWorkDate;
  }

  public void setStartWorkDate(String startWorkDate) {
    this.startWorkDate = startWorkDate;
  }

  public String getEndWorkDate() {
    return endWorkDate;
  }

  public void setEndWorkDate(String endWorkDate) {
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
