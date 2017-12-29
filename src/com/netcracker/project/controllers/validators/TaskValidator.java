package com.netcracker.project.controllers.validators;

import com.netcracker.project.model.ProjectDAO;
import com.netcracker.project.model.entity.Project;
import com.netcracker.project.model.entity.Task;
import com.netcracker.project.model.entity.User;
import com.netcracker.project.model.enums.TaskPriority;
import com.netcracker.project.model.enums.TaskStatus;
import com.netcracker.project.model.enums.TaskType;
import com.netcracker.project.services.impl.DateConverterService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;


public class TaskValidator extends AbstractValidator {

  private Map<String, String> errorMap;
  private String datePattern = "[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])";
  private String symbolPattern = "^[\\pL\\pN0-9\\p{Punct}\\s]+$";

  @Autowired
  private ProjectDAO projectDAO;

  public TaskValidator() {
    errorMap = new HashMap<>();
  }

  public Map<String, String> validationCreate(String name, String taskType, String startDate,
      String plannedEndDate, String priority, String status,
      String description, String comment, String authorId, String userId, String project) {

    validateName(name);
    validateTaskType(taskType);
    validateStartDateAndPlannedEndDate(startDate, plannedEndDate);
    validatePriority(priority);
    validateTaskStatus(status);
    validateDescription(description);
    validateComment(comment);
    validateAuthorId(authorId);
    validateUserId(userId);
    validateProjects(project);

    return errorMap;
  }

  public Map<String, String> validationUpdate(String name, String taskType, String startDate,
      String plannedEndDate, String priority, String status,
      String description, String comment, String authorId, String userId, String project) {

    validateName(name);
    validateTaskType(taskType);
    validateStartDateAndPlannedEndDate(startDate, plannedEndDate);
    validatePriority(priority);
    validateTaskStatus(status);
    validateDescription(description);
    validateComment(comment);
    validateAuthorId(authorId);
    validateUserId(userId);
    validateProjects(project);

    return errorMap;
  }

  public Map<String, String> validationFindTask(String priority) {
    validatePriority(priority);
    return errorMap;
  }

  private void validateName(String name){
    if (name == null || name.isEmpty()){
      errorMap.put("name_error", "Name can't to be null!");
    }
    if (!checkString(name)){
      errorMap.put("name_error", "Invalid name, please try again!");
    }
  }

  private void validateTaskType(String taskType){
    if (taskType == null || taskType.toString().isEmpty()){
      errorMap.put("task_type_error", "Task type can't to be null");
    }
    if (!taskTypeCheck(taskType)){
      errorMap.put("task_type_error", "This task type not found");
    }
  }


  private void validateStartDateAndPlannedEndDate(String startDate, String plannedEndDate){
    if (startDate == null) {
      errorMap.put("start_date_error", "Start date can't null");
    }
    if (plannedEndDate == null) {
      errorMap.put("planned_end_date_error", "End date can't null");
    }

    if (startDate != null || plannedEndDate != null) {

      if(checkStartDate(startDate) && checkPlannedEndDate(plannedEndDate)) {

        Date start = new DateConverterService()
            .convertStringToDateFromJSP(startDate);
        Date plannedEnd = new DateConverterService()
            .convertStringToDateFromJSP(plannedEndDate);

        int compareDate = start.compareTo(plannedEnd);

        if (compareDate == 0) {
          errorMap.put("date_error", "Start date can't be same as planned end date");
        }

        if (compareDate == 1) {
          errorMap.put("date_error", "Start date can't be bigger than planned end date");
        }
      }
    }
  }

  private void validatePriority(String priority){
    if (priority == null || priority.toString().isEmpty()){
      errorMap.put("priority_error", "Priority can't to be empty");
    }

    if (!taskPriorityCheck(priority)){
      errorMap.put("priority_error", "This task priority not found");
    }
  }

  private void validateTaskStatus(String taskStatus){
    if (taskStatus == null || taskStatus.toString().isEmpty()){
      errorMap.put("status_error", "Status can't to be empty!");
    }
    if (!taskStatusCheck(taskStatus)){
      errorMap.put("status_error", "Task status not found!");
    }
  }


  private void validateDescription(String description){
    if (description == null || description.isEmpty()){
      errorMap.put("description_error", "Description can't to be empty!");
    }

    if (!checkString(description)){
      errorMap.put("description_error", "You entered incorrect symbols");
    }
  }

  private void validateComment(String comment){
    if (comment == null || comment.isEmpty()){
      errorMap.put("comment_error", "We must write comment!");
    }
    if (!checkString(comment)){
      errorMap.put("comment_error", "You entered incorrect comment");
    }
  }

  private void validateAuthorId(String authorId){
    if (authorId.isEmpty() || authorId == null){
      errorMap.put("author_error","User is null");
    }
    if (!checkString(authorId.toString())){
      errorMap.put("author_error","User is incorrect");
    }
  }

  private void validateUserId(String userId){
    if (userId.isEmpty() || userId == null){
      errorMap.put("user_error","User is null");
    }
    if (!checkString(userId.toString())){
      errorMap.put("user_error","User is incorrect");
    }
  }

  private void validateProjects(String projectName){
    if (projectName.isEmpty() || projectName == null){
      errorMap.put("projectName_error","Project name is null");
    }
    if (!checkString(projectName.toString())){
      errorMap.put("projectName_error","Project name is incorrect");
    }
  }

  private boolean checkStartDate(String startDate) {
    if (!checkDate(startDate)) {
      errorMap.put("startDateError", "Wrong start data format!");
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
