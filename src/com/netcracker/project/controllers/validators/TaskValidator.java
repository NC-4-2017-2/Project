package com.netcracker.project.controllers.validators;

import com.netcracker.project.controllers.validators.errorMessage.ErrorMessages;
import com.netcracker.project.model.entity.Task;
import com.netcracker.project.model.enums.TaskPriority;
import com.netcracker.project.model.enums.TaskStatus;
import com.netcracker.project.model.enums.TaskType;
import com.netcracker.project.services.impl.DateConverterService;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TaskValidator extends AbstractValidator {

  private String datePattern = "[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])";
  private String symbolPattern = "^[\\pL\\pN0-9\\p{Punct}\\s]+$";

  public Map<String, String> validationCreate(String name, String taskType, String startDate,
      String plannedEndDate, String priority,
      String description, String comment, String projectName) {

    validateName(name);
    validateTaskType(taskType);
    validateStartEndDate(startDate, plannedEndDate);
    validatePriority(priority);
    validateDescription(description);
    validateComment(comment);
    validateProjects(projectName);

    return getErrorMap();
  }

  public Map<String, String> validationUpdate(String name, String taskType, String startDate,
      String plannedEndDate, String priority, String status,
      String description, String comment, String project) {

    validateName(name);
    validateTaskType(taskType);
    validateStartEndDate(startDate, plannedEndDate);
    validatePriority(priority);
    validateTaskStatus(status);
    validateDescription(description);
    validateComment(comment);
    validateProjects(project);

    return getErrorMap();
  }

  public Map<String, String> validateLastNameAndFirstName(String lastName, String firstName) {
    validateAbstractName(lastName);
    validateAbstractName(firstName);
    return getErrorMap();
  }

  public Map<String, String> validationFindTaskByPriority(String priority) {
    validatePriority(priority);
    return getErrorMap();
  }

  public Map<String, String> validationFindTaskByStatus(String status) {
    validateTaskStatus(status);
    return getErrorMap();
  }

  public Map<String, String> validationEntityTask(Task task) {
    validateTask(task);
    return getErrorMap();
  }

  public Map<String, String> validateBetweenDates(String startDate, String endDate) {
    validateStartEndDate(startDate, endDate);
    return getErrorMap();
  }

  public void validateTask(Task task){
    if (task == null){
      setErrorToMap("existence_error", "You haven't task!");
    }
  }

  public Map<String, String> validateInputId(String id) {
    validateId(id);
    return getErrorMap();
  }

  private void validateName(String name){
    if (name == null || name.isEmpty()){
      setErrorToMap("name_error", "Name can't to be null!");
    }
    if (!checkString(name)){
      setErrorToMap("name_error", "Invalid name, please try again!");
    }
  }

  private void validateTaskType(String taskType){
    if (taskType == null || taskType.toString().isEmpty()){
      setErrorToMap("task_type_error", "Task type can't to be null");
    }
    if (!taskTypeCheck(taskType)){
      setErrorToMap("task_type_error", "This task type not found");
    }
  }


  private void validatePriority(String priority){
    if (priority == null || priority.toString().isEmpty()){
      setErrorToMap("priority_error", "Priority can't to be empty");
    }

    if (!taskPriorityCheck(priority)){
      setErrorToMap("priority_error", "This task priority not found");
    }
  }

  private void validateTaskStatus(String taskStatus){
    if (taskStatus == null || taskStatus.toString().isEmpty()){
      setErrorToMap("status_error", "Status can't to be empty!");
    }
    if (!taskStatusCheck(taskStatus)){
      setErrorToMap("status_error", "Task status not found!");
    }
  }


  private void validateDescription(String description){
    if (description == null || description.isEmpty()){
      setErrorToMap("description_error", "Description can't to be empty!");
    }

    if (!checkString(description)){
      setErrorToMap("description_error", "You entered incorrect symbols");
    }
  }

  private void validateComment(String comment){
    if (comment == null || comment.isEmpty()){
      setErrorToMap("comment_error", "We must write comment!");
    }
    if (!checkString(comment)){
      setErrorToMap("comment_error", "You entered incorrect comment");
    }
  }


  private void validateUserId(String userId){
    if (userId.isEmpty() || userId == null){
      setErrorToMap("user_error","User is null");
    }
    if (!checkString(userId.toString())){
      setErrorToMap("user_error","User is incorrect");
    }
  }

  private void validateProjects(String projectName){
    if (projectName.isEmpty() || projectName == null){
      setErrorToMap("projectName_error","Project name is null");
    }
    if (!checkString(projectName.toString())){
      setErrorToMap("projectName_error","Project name is incorrect");
    }
  }

  public Map<String, String> validateExistence(Integer taskExistence) {
    if (taskExistence == 0) {
      setErrorToMap("TASK_ERROR", ErrorMessages.TASK_ERROR);
    }
    return getErrorMap();
  }

  private boolean checkStartDate(String startDate) {
    if (!checkDate(startDate)) {
      setErrorToMap("startDateError", "Wrong start data format!");
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
