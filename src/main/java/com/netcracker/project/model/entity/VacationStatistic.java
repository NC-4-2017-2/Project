package com.netcracker.project.model.entity;

import java.math.BigInteger;

public class VacationStatistic {

  private BigInteger userId;
  private Integer countDays;

  public VacationStatistic(BigInteger userId, Integer countDays) {
    this.userId = userId;
    this.countDays = countDays;
  }

  public BigInteger getUserId() {
    return userId;
  }

  public void setUserId(BigInteger userId) {
    this.userId = userId;
  }

  public Integer getCountDays() {
    return countDays;
  }

  public void setCountDays(Integer countDays) {
    this.countDays = countDays;
  }

  @Override
  public String toString() {
    return "VacationStatistic{" +
        "userId=" + userId +
        ", countDays=" + countDays +
        '}';
  }
}
