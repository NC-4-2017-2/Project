package com.netcracker.project.controllers.validators;

import java.util.Map;


public class TaskValidator extends AbstractValidator {


  public Map<String, String> validationCreate(String name, String startDate,
      String endDate, String plannedEndDate,
      String description, String comment) {

    validateName(name);
    validateStartEndDate(startDate, endDate);
    validateStartDateAndPlannedEndDate(startDate, plannedEndDate);
    validateDescription(description);
    validateComment(comment);

    return getErrorMap();
  }

  public Map<String, String> validationUpdate(String name, String taskType,
      String startDate, String endDate,
      String plannedEndDate, String priority, String status,
      String description, String comment) {
    validateName(name);
    validateTaskType(taskType);
    validateStartEndDate(startDate, endDate);
    validateStartDateAndPlannedEndDate(startDate, plannedEndDate);
    validatePriority(priority);
    validateTaskStatus(status);
    validateDescription(description);
    validateComment(comment);

    return getErrorMap();
  }

  public Map<String, String> validationFindTask(String priority) {
    validatePriority(priority);
    return getErrorMap();
  }
}
