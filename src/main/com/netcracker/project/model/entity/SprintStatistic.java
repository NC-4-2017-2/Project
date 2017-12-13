package main.com.netcracker.project.model.entity;


public class SprintStatistic {
  private String sprintName;
  private Integer plannedTakeDays;
  private Integer takeDays;

  public SprintStatistic(String sprintName, Integer plannedTakeDays,
      Integer takeDays) {
    this.sprintName = sprintName;
    this.plannedTakeDays = plannedTakeDays;
    this.takeDays = takeDays;
  }

  public String getSprintName() {
    return sprintName;
  }

  public void setSprintName(String sprintName) {
    this.sprintName = sprintName;
  }

  public Integer getPlannedTakeDays() {
    return plannedTakeDays;
  }

  public void setPlannedTakeDays(Integer plannedTakeDays) {
    this.plannedTakeDays = plannedTakeDays;
  }

  public Integer getTakeDays() {
    return takeDays;
  }

  public void setTakeDays(Integer takeDays) {
    this.takeDays = takeDays;
  }

  @Override
  public String toString() {
    return "SprintStatistic{" +
            "sprintName='" + sprintName + '\'' +
            ", plannedTakeDays=" + plannedTakeDays +
            ", takeDays=" + takeDays +
            '}';
  }
}
