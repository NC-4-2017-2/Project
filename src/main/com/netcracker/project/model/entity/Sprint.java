package main.com.netcracker.project.model.entity;

import java.math.BigInteger;
import java.util.Date;
import main.com.netcracker.project.model.ProjectDAO;
import main.com.netcracker.project.model.ProjectDAO.OCStatus;

public class Sprint {

  private BigInteger sprintId;
  private String name;
  private Date startDate;
  private Date plannedEndDate;
  private Date endDate;
  private ProjectDAO.OCStatus status;

  private Sprint(SprintBuilder builder) {
    this.sprintId = builder.sprintId;
    this.name = builder.name;
    this.startDate = builder.startDate;
    this.plannedEndDate = builder.plannedEndDate;
    this.endDate = builder.endDate;
    this.status = builder.status;
  }

  public BigInteger getSprintId() {
    return sprintId;
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

  public Date getPlannedEndDate() {
    return plannedEndDate;
  }

  public ProjectDAO.OCStatus getStatus() {
    return status;
  }

  public static class SprintBuilder {

    private BigInteger sprintId;
    private String name;
    private Date startDate;
    private Date plannedEndDate;
    private Date endDate;
    private ProjectDAO.OCStatus status;

    public SprintBuilder() {
    }

    public SprintBuilder sprintId(BigInteger sprintId) {
      this.sprintId = sprintId;
      return this;
    }

    public SprintBuilder name(String name) {
      this.name = name;
      return this;
    }

    public SprintBuilder startDate(Date date) {
      this.startDate = date;
      return this;
    }

    public SprintBuilder plannedEndDate(Date plannedEndDate) {
      this.plannedEndDate = plannedEndDate;
      return this;
    }

    public SprintBuilder endDate(Date endDate) {
      this.endDate = endDate;
      return this;
    }

    public SprintBuilder status(OCStatus status) {
      this.status = status;
      return this;
    }

    public Sprint build() {
      return new Sprint(this);
    }

  }
}
