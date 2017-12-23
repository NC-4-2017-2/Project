package com.netcracker.project.controllers.validators;


import com.netcracker.project.model.enums.TaskPriority;
import com.netcracker.project.model.enums.TaskStatus;
import com.netcracker.project.model.enums.TaskType;
import com.netcracker.project.model.impl.mappers.MapperDateConverter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskValidator {

  Map<String, String> errorMsg = new HashMap<>();

  String datePattern = "[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])";
  String symbolPattern = "[A-Za-zА-Яа-я0-9,\\.\\(\\)\\-\\s]{2,300}";
  String numbersPattern = "[0-9][0-9]";

  public Map<String, String> validationCreate(String name, String startDate, String endDate, String plannedEndDate,
                                              String description, Integer reopenCounter, String comment){

    validateName(name);
    validateStartEndDate(startDate, endDate);
    validateStartDateAndPlannedEndDate(startDate, plannedEndDate);
    validateDescription(description);
    validateReopenCounter(reopenCounter);
    validateComment(comment);

    return errorMsg;
  }

  public Map<String, String> validationUpdate(String name, String taskType, String startDate, String endDate,
                                              String plannedEndDate, String priority, String status, String description,
                                              Integer reopenCounter, String comment){
    validateName(name);
    validateTaskType(taskType);
    validateStartEndDate(startDate, endDate);
    validateStartDateAndPlannedEndDate(startDate, plannedEndDate);
    validatePriority(priority);
    validateStatus(status);
    validateDescription(description);
    validateReopenCounter(reopenCounter);
    validateComment(comment);

    return errorMsg;
  }

  public Map<String, String> validationFindTask(String priority){
    validatePriority(priority);
    return errorMsg;
  }

  private void validateName(String name){
    if (name == null || name.isEmpty()){
      errorMsg.put("name_error", "name can't to be null!");
    }
    if (!checkString(name)){
      errorMsg.put("name_error", "invalid name, please try again!");
    }
  }

  private void validateTaskType(String taskType){
    if (taskType == null || taskType.toString().isEmpty()){
      errorMsg.put("task_type_error", "task type can't to be null!");
    }
    if (!taskTypeCheck(taskType)){
      errorMsg.put("task_type_error", "this task type not found! ");
    }
  }

  private boolean taskTypeCheck(String type){
    for (TaskType types: TaskType.values()){
      if (types.name().equals(type)){
        return true;
      }
    }
    return false;
  }


  private void validateStartEndDate(String startDate, String endDate){
    if (startDate == null) {
      errorMsg.put("start_date_error", "Start date can't null!");
    }
    if (endDate == null) {
      errorMsg.put("end_date_error", "End date can't null!");
    }

    if (startDate != null || endDate != null) {

      if (checkStartDate(startDate) && checkEndDate(endDate)) {
        Date start = new MapperDateConverter().convertStringToDateFromJSP(startDate);
        Date end = new MapperDateConverter().convertStringToDateFromJSP(endDate);

        int dateCompare = start.compareTo(end);

        if (dateCompare == 0) {
          errorMsg.put("date_error", "Start date can't be same as end date!");
        }

        if (dateCompare == 1) {
          errorMsg.put("date_error", "Start date can't be bigger than end date!");
        }
      }
    }
  }


  private void validateStartDateAndPlannedEndDate(String startDate, String plannedEndDate){
    if (startDate == null) {
      errorMsg.put("start_date_error", "Start date can't null!");
    }
    if (plannedEndDate == null) {
      errorMsg.put("planned_end_date_error", "End date can't null!");
    }

    if (startDate != null || plannedEndDate != null) {

      if(checkStartDate(startDate) && checkPlannedEndDate(plannedEndDate)) {

        Date start = new MapperDateConverter()
            .convertStringToDateFromJSP(startDate);
        Date plannedEnd = new MapperDateConverter()
            .convertStringToDateFromJSP(plannedEndDate);

        int compareDate = start.compareTo(plannedEnd);

        if (compareDate == 0) {
          errorMsg.put("date_error",
              "Start date can't be same as planned end date!");
        }

        if (compareDate == 1) {
          errorMsg.put("date_error",
              "Start date can't be bigger than planned end date!");
        }
      }
    }
  }

  private boolean checkDate(String dateString) {
    Pattern pattern = Pattern.compile(datePattern);
    Matcher matcher = pattern.matcher(dateString);
    return matcher.matches();
  }


  private boolean checkStartDate(String startDate) {
    if (!checkDate(startDate)) {
      errorMsg.put("start_date_error", "Wrong start date format!");
      return false;
    }

    return true;
  }

  private boolean checkEndDate(String endDate) {
    if (!checkDate(endDate)) {
      errorMsg.put("end_date_error", "Wrong end date format!");
      return false;
    }

    return true;
  }

  private boolean checkPlannedEndDate(String plannedEndDate){
    if (!checkDate(plannedEndDate)){
      errorMsg.put("planned_end_date_error", "Wrong planned end date format");
      return false;
    }
    return true;
  }


  private void validatePriority(String priority){
    if (priority == null || priority.toString().isEmpty()){
      errorMsg.put("priority_error", "priority can't to be empty!");
    }

    if (!taskPriorityCheck(priority)){
      errorMsg.put("priority_error", "this task priority not found!");
    }
  }

  private boolean taskPriorityCheck(String taskPriority){
    for (TaskPriority priorities: TaskPriority.values()){
      if (priorities.name().equals(taskPriority)){
        return true;
      }
    }
    return false;
  }

  private void validateStatus(String status){
    if (status == null || status.toString().isEmpty()){
      errorMsg.put("status_error", "status can't to be empty!");
    }
    if (!taskStatusCheck(status)){
      errorMsg.put("status_error", "task status not found!");
    }
  }

  private boolean taskStatusCheck(String taskStatus){
    for (TaskStatus statuses: TaskStatus.values()){
      if (statuses.name().equals(taskStatus)){
        return true;
      }
    }
    return false;
  }

  private boolean checkString(String enteringString){
    Pattern pattern = Pattern.compile(symbolPattern);
    Matcher matcher = pattern.matcher(enteringString);
    return matcher.matches();
  }

  private void validateDescription(String description){
    if (description == null || description.isEmpty()){
      errorMsg.put("description_error", "description can't to be empty!");
    }

    if (!checkString(description)){
      errorMsg.put("description_error", "you entered incorrect symbols");
    }
  }


  private void validateReopenCounter(Integer reopenCounter){
    if (reopenCounter < 0){
      errorMsg.put("counter_error", "counter can't to be empty!");
    }
    if (!checkNumbers(reopenCounter.toString())){
      errorMsg.put("counter_error", "invalid number");
    }
  }

  private boolean checkNumbers(String number){
    Pattern pattern = Pattern.compile(numbersPattern);
    Matcher matcher = pattern.matcher(number);
    return matcher.matches();
  }

  private void validateComment(String comment){
    if (comment == null || comment.isEmpty()){
      errorMsg.put("comment_error", "We must write comment!");
    }
    if (!checkString(comment)){
      errorMsg.put("comment_error", "you entered incorrect comment");
    }
  }
}
