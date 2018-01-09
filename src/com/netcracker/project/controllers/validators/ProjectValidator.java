package com.netcracker.project.controllers.validators;

import com.netcracker.project.controllers.validators.errorMessage.ErrorMessages;
import java.util.Map;

public class ProjectValidator extends AbstractValidator {
  public Map<String, String> validateDates(String start, String end) {
    validateStartEndDate(start, end);
    return getErrorMap();
  }

  public Map<String, String> validateInputId(String id) {
    validateId(id);
    return getErrorMap();
  }

  public Map<String, String> validateExistence(Integer projectExistence) {

    return getErrorMap();
  }

  public Map<String, String> validateSprintsAndWorkers(String sprintCount,
      String workersCount) {
    validateId(sprintCount);
    validateId(workersCount);
    return getErrorMap();
  }

  public Map<String, String> validateCreateProject(
      Integer existenceProject,
      String startDate,
      String endDate, String id) {

    if (existenceProject > 0) {
      setErrorToMap("PROJECT_NAME_ERROR", ErrorMessages.PROJECT_NAME_ERROR);
    }
    validateStartEndDate(startDate, endDate);
    validateId(id);
    return getErrorMap();
  }

  public Map<String, String> validateDeleteUser(String projectId,
      String userId) {
    validateId(projectId);
    validateId(userId);
    return getErrorMap();
  }

  public Map<String, String> validateProjectAndUserExistence(
      Integer projectExistence, Integer userExistence) {
    if (projectExistence == 0) {
      setErrorToMap("PROJECT_ERROR", ErrorMessages.PROJECT_ERROR);
    }
    if (userExistence == 0) {
      setErrorToMap("USER_ERROR", ErrorMessages.USER_ERROR);
    }
    return getErrorMap();
  }

  public Map<String, String> validateAddingUser(String projectId,
      String firstName, String lastName, String startDate,
      String endDate) {
    validateId(projectId);
    validateAbstractName(firstName);
    validateAbstractName(lastName);
    validateStartEndDate(startDate, endDate);
    return getErrorMap();
  }

  public Map<String, String> validateAddingUserFromDuplicate(String projectId,
      String startDate, String endDate, String userId) {
    validateId(projectId);
    validateId(userId);
    validateStartEndDate(startDate, endDate);
    return getErrorMap();
  }
}
