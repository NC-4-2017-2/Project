package com.netcracker.project.controllers.project_form;


import com.netcracker.project.model.ProjectDAO.OCStatus;
import java.math.BigInteger;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class SprintFormData {

  private BigInteger id;
  private String name;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date startDate;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date plannedEndDate;
  private OCStatus sprintStatus;

  public SprintFormData(String name,
      Date startDate, Date plannedEndDate) {
    this.name = name;
    this.startDate = startDate;
    this.plannedEndDate = plannedEndDate;
  }

  public SprintFormData(BigInteger id, String name,
      OCStatus sprintStatus, Date plannedEndDate) {
    this.plannedEndDate = plannedEndDate;
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

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getPlannedEndDate() {
    return plannedEndDate;
  }

  public void setPlannedEndDate(Date plannedEndDate) {
    this.plannedEndDate = plannedEndDate;
  }
}
