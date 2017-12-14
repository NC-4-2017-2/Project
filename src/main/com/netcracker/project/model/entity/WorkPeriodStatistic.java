package main.com.netcracker.project.model.entity;

public class WorkPeriodStatistic {

  private Integer allTimeWorkers;
  private Integer currentWorkers;

  public WorkPeriodStatistic(Integer allTimeWorkers,
      Integer currentWorkers) {
    this.allTimeWorkers = allTimeWorkers;
    this.currentWorkers = currentWorkers;
  }

  public Integer getAllTimeWorkers() {
    return allTimeWorkers;
  }

  public void setAllTimeWorkers(Integer allTimeWorkers) {
    this.allTimeWorkers = allTimeWorkers;
  }

  public Integer getCurrentWorkers() {
    return currentWorkers;
  }

  public void setCurrentWorkers(Integer currentWorkers) {
    this.currentWorkers = currentWorkers;
  }

  @Override
  public String toString() {
    return "WorkPeriodStatistic{" +
        "allTimeWorkers=" + allTimeWorkers +
        ", currentWorkers=" + currentWorkers +
        '}';
  }
}
