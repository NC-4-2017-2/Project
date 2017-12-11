package main.com.netcracker.project.controllers.project;


public class SprintFormData {

  private String name;
  private String startDate;
  private String plannedEndDate;

  public SprintFormData(String name,
      String startDate, String plannedEndDate) {
    this.name = name;
    this.startDate = startDate;
    this.plannedEndDate = plannedEndDate;
  }

  public SprintFormData() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getPlannedEndDate() {
    return plannedEndDate;
  }

  public void setPlannedEndDate(String plannedEndDate) {
    this.plannedEndDate = plannedEndDate;
  }
}
