package com.netcracker.project.model.entity;

public class WorkingDayUserService {

  private String lastName;
  private String firstName;
  private WorkingDay workingDay;

  public WorkingDayUserService(String lastName, String firstName,
      WorkingDay workingDay) {
    this.lastName = lastName;
    this.firstName = firstName;
    this.workingDay = workingDay;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public WorkingDay getWorkingDay() {
    return workingDay;
  }

  public void setWorkingDay(WorkingDay workingDay) {
    this.workingDay = workingDay;
  }
}
