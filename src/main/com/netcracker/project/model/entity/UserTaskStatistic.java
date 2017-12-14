package main.com.netcracker.project.model.entity;

import java.math.BigInteger;

public class UserTaskStatistic {

  private BigInteger userId;
  private Integer critical;
  private Integer high;
  private Integer normal;
  private Integer low;

  public UserTaskStatistic(UserTaskStatisticBuilder builder) {
    this.userId = builder.userId;
    this.critical = builder.critical;
    this.high = builder.high;
    this.normal = builder.normal;
    this.low = builder.low;
  }

  public BigInteger getUserId() {
    return userId;
  }

  public Integer getCritical() {
    return critical;
  }

  public Integer getHigh() {
    return high;
  }

  public Integer getNormal() {
    return normal;
  }

  public Integer getLow() {
    return low;
  }

  public static class UserTaskStatisticBuilder {

    private BigInteger userId;
    private Integer critical;
    private Integer high;
    private Integer normal;
    private Integer low;

    public UserTaskStatisticBuilder() {
    }

    public UserTaskStatisticBuilder userId(BigInteger userId) {
      this.userId = userId;
      return this;
    }

    public UserTaskStatisticBuilder critical(Integer critical) {
      this.critical = critical;
      return this;
    }

    public UserTaskStatisticBuilder high(Integer high) {
      this.high = high;
      return this;
    }

    public UserTaskStatisticBuilder normal(Integer normal) {
      this.normal = normal;
      return this;
    }

    public UserTaskStatisticBuilder low(Integer low) {
      this.low = low;
      return this;
    }

    public UserTaskStatistic build(){
      return new UserTaskStatistic(this);
    }
  }
}
