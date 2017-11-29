package main.com.netcracker.project.model.entity;

import java.math.BigInteger;
import java.util.Date;

public class WorkingDay {

  private BigInteger userId;
  private Date date;
  private Integer weekNumber;
  private Double workingHours;
  private Status status;
  private BigInteger pmId;

  private WorkingDay(WorkingDayBuilder builder) {
    this.userId = builder.userId;
    this.date = builder.date ;
    this.weekNumber = builder.weekNumber;
    this.workingHours = builder.workingHours;
    this.status = builder.status;
    this.pmId = builder.pmId;
  }

  public BigInteger getUserId() {
    return userId;
  }

  public Date getDate() {
    return date;
  }

  public Integer getWeekNumber() {
    return weekNumber;
  }

  public Double getWorkingHours() {
    return workingHours;
  }

  public Status getStatus() {
    return status;
  }

  public BigInteger getPmId() {
    return pmId;
  }

  public static class WorkingDayBuilder{
    private BigInteger userId;
    private Date date;
    private Integer weekNumber;
    private Double workingHours;
    private Status status;
    private BigInteger pmId;

    public WorkingDayBuilder(){
    }

    public WorkingDayBuilder userId(BigInteger userId) {
      this.userId = userId;
      return this;
    }

    public WorkingDayBuilder date(Date date) {
      this.date = date;
      return this;
    }

    public WorkingDayBuilder weekNumber(Integer weekNumber) {
      this.weekNumber = weekNumber;
      return this;
    }

    public WorkingDayBuilder workingHours(Double workingHours) {
      this.workingHours = workingHours;
      return this;
    }

    public WorkingDayBuilder status(Status status) {
      this.status = status;
      return this;
    }

    public WorkingDayBuilder pmId(BigInteger pmId) {
      this.pmId = pmId;
      return this;
    }
  }
}