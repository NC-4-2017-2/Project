package main.com.netcracker.project.model.entity;

public class WorkPeriodStatistic {

  private Integer working;
  private Integer fired;

  public WorkPeriodStatistic(Integer allTimeWorkers,
      Integer currentWorkers) {
    this.working = allTimeWorkers;
    this.fired = currentWorkers;
  }

  public Integer getWorking() {
    return working;
  }

  public void setWorking(Integer working) {
    this.working = working;
  }

  public Integer getFired() {
    return fired;
  }

  public void setFired(Integer fired) {
    this.fired = fired;
  }

  @Override
  public String toString() {
    return "WorkPeriodStatistic{" +
        "working=" + working +
        ", fired=" + fired +
        '}';
  }
}
