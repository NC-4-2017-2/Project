package main.com.netcracker.project.model.entity;

import java.math.BigInteger;

public class ProjectTaskStatistic {

  private BigInteger userId;
  private Integer critical;
  private Integer high;
  private Integer normal;
  private Integer low;

  public ProjectTaskStatistic(ProjectTaskStatisticBuilder builder) {
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

  public static class ProjectTaskStatisticBuilder {

    private BigInteger userId;
    private Integer critical;
    private Integer high;
    private Integer normal;
    private Integer low;

    public ProjectTaskStatisticBuilder() {
    }

    public ProjectTaskStatisticBuilder userId(BigInteger userId) {
      this.userId = userId;
      return this;
    }

    public ProjectTaskStatisticBuilder critical(Integer critical) {
      this.critical = critical;
      return this;
    }

    public ProjectTaskStatisticBuilder high(Integer high) {
      this.high = high;
      return this;
    }

    public ProjectTaskStatisticBuilder normal(Integer normal) {
      this.normal = normal;
      return this;
    }

    public ProjectTaskStatisticBuilder low(Integer low) {
      this.low = low;
      return this;
    }

    public ProjectTaskStatistic build(){
      return new ProjectTaskStatistic(this);
    }
  }
}
