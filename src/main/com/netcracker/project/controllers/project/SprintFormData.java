package main.com.netcracker.project.controllers.project;


import java.math.BigInteger;
import main.com.netcracker.project.model.ProjectDAO.OCStatus;

public class SprintFormData {

  private BigInteger id;
  private String name;
  private String startDate;
  private String plannedEndDate;
  private OCStatus sprintStatus;

  public SprintFormData(String name,
      String startDate, String plannedEndDate) {
    this.name = name;
    this.startDate = startDate;
    this.plannedEndDate = plannedEndDate;
  }

  public SprintFormData(BigInteger id, String name,
      OCStatus sprintStatus) {
    this.id = id;
    this.name = name;
    this.sprintStatus = sprintStatus;
  }

  public SprintFormData() {
  }

  public BigInteger getId() {
    return id;
  }

  public void setId(BigInteger id) {
    this.id = id;
  }

  public OCStatus getSprintStatus() {
    return sprintStatus;
  }

  public void setSprintStatus(
      OCStatus sprintStatus) {
    this.sprintStatus = sprintStatus;
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
