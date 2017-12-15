package main.com.netcracker.project.model.entity;

public class UserTaskStatistic {

  private Integer critical;
  private Integer high;
  private Integer normal;
  private Integer low;

  public UserTaskStatistic(UserTaskStatisticBuilder builder) {
    this.critical = builder.critical;
    this.high = builder.high;
    this.normal = builder.normal;
    this.low = builder.low;
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

    private Integer critical;
    private Integer high;
    private Integer normal;
    private Integer low;

    public UserTaskStatisticBuilder() {
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

  @Override
  public String toString() {
    return "UserTaskStatistic{" +
            "critical=" + critical +
            ", high=" + high +
            ", normal=" + normal +
            ", low=" + low +
            '}';
  }
}
