package com.netcracker.project.controllers.project_form;


import com.netcracker.project.model.ProjectDAO.OCStatus;
import com.netcracker.project.model.impl.mappers.MapperDateConverter;
import java.math.BigInteger;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class SprintFormData {

  private BigInteger id;
  private String name;
  private String startDate;
  private String plannedEndDate;
  private OCStatus sprintStatus;
  private MapperDateConverter converter = new MapperDateConverter();

  public SprintFormData(String name,
      Date startDate, Date plannedEndDate) {
    this.name = name;
    this.startDate = converter.convertDateToString(startDate);
    this.plannedEndDate = converter.convertDateToString(plannedEndDate);
  }

  public SprintFormData(BigInteger id, String name,
      OCStatus sprintStatus, Date plannedEndDate) {
    this.plannedEndDate = converter.convertDateToString(plannedEndDate);
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
