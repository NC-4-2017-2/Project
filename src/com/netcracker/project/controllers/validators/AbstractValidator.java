package com.netcracker.project.controllers.validators;

import com.netcracker.project.model.enums.Status;
import com.netcracker.project.model.enums.TaskPriority;
import com.netcracker.project.model.enums.TaskStatus;
import com.netcracker.project.model.enums.TaskType;
import com.netcracker.project.services.impl.DateConverterService;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class AbstractValidator {

  private Map<String, String> errorMap;
  private String datePattern = "[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])";
  String symbolPattern = "[A-Za-zА-Яа-я0-9,\\.\\(\\)\\-\\s]{2,300}";

  AbstractValidator() {
    errorMap = new HashMap<>();
  }

  void validateStartEndDate(String startDate, String endDate) {
    if (startDate == null) {
      errorMap.put("startDateError", "Start date can't null!");
    }

    if (endDate == null) {
      errorMap.put("endDateError", "End date can't null!");
    }

    if (startDate != null && endDate != null) {

      if (checkStartDate(startDate) && checkEndDate(endDate)) {
        Date start = new DateConverterService()
            .convertStringToDateFromJSP(startDate);
        Date end = new DateConverterService()
            .convertStringToDateFromJSP(endDate);

        int dateCompare = start.compareTo(end);

        if (dateCompare == 0) {
          errorMap.put("dateError", "Start date can't be same as end date!");
        }

        if (dateCompare == 1) {
          errorMap
              .put("dateError", "Start date can't be bigger than end date!");
        }
      }
    }
  }



   void validateStartDateAndPlannedEndDate(String startDate, String plannedEndDate){
    if (startDate == null) {
      errorMap.put("start_date_error", "Start date can't null!");
    }
    if (plannedEndDate == null) {
      errorMap.put("planned_end_date_error", "End date can't null!");
    }

    if (startDate != null || plannedEndDate != null) {

      if(checkStartDate(startDate) && checkPlannedEndDate(plannedEndDate)) {

        Date start = new DateConverterService()
            .convertStringToDateFromJSP(startDate);
        Date plannedEnd = new DateConverterService()
            .convertStringToDateFromJSP(plannedEndDate);

        int compareDate = start.compareTo(plannedEnd);

        if (compareDate == 0) {
          errorMap.put("date_error",
              "Start date can't be same as planned end date!");
        }

        if (compareDate == 1) {
          errorMap.put("date_error",
              "Start date can't be bigger than planned end date!");
        }
      }
    }
  }

  void validateStatus(String status) {
    if (status == null || status.isEmpty()) {
      errorMap.put("statusError", "Status can't be empty!");
    }

    if (!statusEnumCheck(status)) {
      errorMap.put("statusError", "Incorrect status!");
    }
  }

  Map<String, String> getErrorMap() {
    return this.errorMap;
  }

  String getDatePattern() {
    return datePattern;
  }

  void setErrorToMap(String errorName, String errorDescription) {
    this.errorMap.put(errorName, errorDescription);
  }

  private boolean statusEnumCheck(String status) {
    for (Status enumStatus : Status.values()) {
      if (enumStatus.name().equals(status)) {
        return true;
      }
    }

    return false;
  }

   void validateTaskStatus(String taskStatus){
    if (taskStatus == null || taskStatus.toString().isEmpty()){
      errorMap.put("status_error", "status can't to be empty!");
    }
    if (!taskStatusCheck(taskStatus)){
      errorMap.put("status_error", "task status not found!");
    }
  }

   void validateName(String name){
    if (name == null || name.isEmpty()){
      errorMap.put("name_error", "name can't to be null!");
    }
    if (!checkString(name)){
      errorMap.put("name_error", "invalid name, please try again!");
    }
  }

   void validateDescription(String description){
    if (description == null || description.isEmpty()){
      errorMap.put("description_error", "description can't to be empty!");
    }

    if (!checkString(description)){
      errorMap.put("description_error", "you entered incorrect symbols");
    }
  }

   void validateComment(String comment){
    if (comment == null || comment.isEmpty()){
      errorMap.put("comment_error", "We must write comment!");
    }
    if (!checkString(comment)){
      errorMap.put("comment_error", "you entered incorrect comment");
    }
  }

   void validateTaskType(String taskType){
    if (taskType == null || taskType.toString().isEmpty()){
      errorMap.put("task_type_error", "task type can't to be null!");
    }
    if (!taskTypeCheck(taskType)){
      errorMap.put("task_type_error", "this task type not found! ");
    }
  }

  void validatePriority(String priority){
    if (priority == null || priority.toString().isEmpty()){
      errorMap.put("priority_error", "priority can't to be empty!");
    }

    if (!taskPriorityCheck(priority)){
      errorMap.put("priority_error", "this task priority not found!");
    }
  }


  private boolean checkStartDate(String startDate) {
    if (!checkDate(startDate)) {
      errorMap.put("startDateError", "Wrong start data format!");
      return false;
    }

    return true;
  }

  private boolean checkEndDate(String endDate) {
    if (!checkDate(endDate)) {
      errorMap.put("endDateError", "Wrong end data format!");
      return false;
    }

    return true;
  }

  private boolean checkPlannedEndDate(String plannedEndDate){
    if (!checkDate(plannedEndDate)){
      errorMap.put("planned_end_date_error", "Wrong planned end date format");
      return false;
    }
    return true;
  }

  private boolean taskStatusCheck(String taskStatus){
    for (TaskStatus statuses: TaskStatus.values()){
      if (statuses.name().equals(taskStatus)){
        return true;
      }
    }
    return false;
  }

  private boolean taskTypeCheck(String type){
    for (TaskType types: TaskType.values()){
      if (types.name().equals(type)){
        return true;
      }
    }
    return false;
  }

  private boolean taskPriorityCheck(String taskPriority){
    for (TaskPriority priorities: TaskPriority.values()){
      if (priorities.name().equals(taskPriority)){
        return true;
      }
    }
    return false;
  }


  private boolean checkDate(String dateString) {
    Pattern p = Pattern.compile(datePattern);
    Matcher m = p.matcher(dateString);
    return m.matches();
  }

  private boolean checkString(String enteringString){
    Pattern pattern = Pattern.compile(symbolPattern);
    Matcher matcher = pattern.matcher(enteringString);
    return matcher.matches();
  }
}
