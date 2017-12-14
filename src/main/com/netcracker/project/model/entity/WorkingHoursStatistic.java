package main.com.netcracker.project.model.entity;

import java.math.BigInteger;

public class WorkingHoursStatistic {

  private BigInteger userId;
  private String workingDayDate;
  private Integer hoursCount;

  public WorkingHoursStatistic(BigInteger userId,
      String workingDayDate,
      Integer hoursCount) {
    this.userId = userId;
    this.workingDayDate = workingDayDate;
    this.hoursCount = hoursCount;
  }

  public String getWorkingDayDate() {
    return workingDayDate;
  }

  public void setWorkingDayDate(String workingDayDate) {
    this.workingDayDate = workingDayDate;
  }

  public Integer getHoursCount() {
    return hoursCount;
  }

  public void setHoursCount(Integer hoursCount) {
    this.hoursCount = hoursCount;
  }

  public BigInteger getUserId() {
    return userId;
  }

  public void setUserId(BigInteger userId) {
    this.userId = userId;
  }

  @Override
  public String toString() {
    return "WorkingHoursStatistic{" +
        "userId=" + userId +
        ", workingDayDate='" + workingDayDate + '\'' +
        ", hoursCount=" + hoursCount +
        '}';
  }
}

