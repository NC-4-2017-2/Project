package main.com.netcracker.project.model.entity;

public class ProjectTaskStatistic {

  private Integer critical;
  private Integer high;
  private Integer normal;
  private Integer low;

  public ProjectTaskStatistic(Integer critical, Integer high,
      Integer normal, Integer low) {
    this.critical = critical;
    this.high = high;
    this.normal = normal;
    this.low = low;
  }

  public Integer getCritical() {
    return critical;
  }

  public void setCritical(Integer critical) {
    this.critical = critical;
  }

  public Integer getHigh() {
    return high;
  }

  public void setHigh(Integer high) {
    this.high = high;
  }

  public Integer getNormal() {
    return normal;
  }

  public void setNormal(Integer normal) {
    this.normal = normal;
  }

  public Integer getLow() {
    return low;
  }

  public void setLow(Integer low) {
    this.low = low;
  }

  @Override
  public String toString() {
    return "ProjectTaskStatistic{" +
        "critical=" + critical +
        ", high=" + high +
        ", normal=" + normal +
        ", low=" + low +
        '}';
  }
}
