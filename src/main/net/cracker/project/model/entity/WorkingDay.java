package main.net.cracker.project.model.entity;

import java.math.BigInteger;
import java.util.Date;
import java.util.Map;
import main.net.cracker.project.model.WorkingDayDAO;

public class WorkingDay implements WorkingDayDAO{

  private BigInteger userId;
  private Date date;
  private Integer weekNumber;
  private Double workingHours;
  private Boolean status;
  private BigInteger pmId;

  public BigInteger getUserId() {
    return userId;
  }

  public void setUserId(BigInteger userId) {
    this.userId = userId;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Integer getWeekNumber() {
    return weekNumber;
  }

  public void setWeekNumber(Integer weekNumber) {
    this.weekNumber = weekNumber;
  }

  public Double getWorkingHours() {
    return workingHours;
  }

  public void setWorkingHours(Double workingHours) {
    this.workingHours = workingHours;
  }

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public BigInteger getPmId() {
    return pmId;
  }

  public void setPmId(BigInteger pmId) {
    this.pmId = pmId;
  }

  public Boolean addHoursPerDay(Double hours, User user, BigInteger taskId) {
    return null;
  }

  public Map<String, BigInteger> findHoursPerWeek(Integer numberOfWeek,
      BigInteger userId) {
    return null;
  }

  public Integer findHoursPerPeriod(Integer userId, Date startDate,
      Date endDate) {
    return null;
  }
}
