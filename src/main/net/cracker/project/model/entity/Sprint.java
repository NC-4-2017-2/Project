package main.net.cracker.project.model.entity;

import java.math.BigInteger;
import java.util.Date;
import main.net.cracker.project.model.ProjectDAO.OCStatus;

public class Sprint {

  private BigInteger sprintId;
  private String name;
  private Date startDate;
  private Date plannedEndDate;
  private Date endDate;
  private OCStatus status;

  public Sprint(BigInteger sprintId, String name, Date startDate,
      Date plannedEndDate, Date endDate,
      OCStatus status) {
    this.sprintId = sprintId;
    this.name = name;
    this.startDate = startDate;
    this.plannedEndDate = plannedEndDate;
    this.endDate = endDate;
    this.status = status;
  }

  public BigInteger getSprintId() {
    return sprintId;
  }

  public void setSprintId(BigInteger sprintId) {
    this.sprintId = sprintId;
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

  public Date getPlannedEndDate() {
    return plannedEndDate;
  }

  public void setPlannedEndDate(Date plannedEndDate) {
    this.plannedEndDate = plannedEndDate;
  }

  public OCStatus getStatus() {
    return status;
  }

  public void setStatus(OCStatus status) {
    this.status = status;
  }
}
