package main.net.cracker.project.model.entity;

import java.math.BigInteger;
import java.util.Date;

public class Sprint {

  private BigInteger sprintId;
  private String name;
  private Date startDate;
  private Date plannedEndDate;
  private Date endDate;
  private SprintStatus status;

  public Sprint(BigInteger sprintId, String name, Date startDate,
      Date endDate) {
    this.sprintId = sprintId;
    this.name = name;
    this.startDate = startDate;
    this.endDate = endDate;
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

  public SprintStatus getStatus() {
    return status;
  }

  public void setStatus(SprintStatus status) {
    this.status = status;
  }

  public enum SprintStatus {
    OPEN, CLOSED;

    private Integer id;

    SprintStatus(Integer id) {
      this.id = id;
    }

    SprintStatus() {
      this(0);
    }
  }
}
