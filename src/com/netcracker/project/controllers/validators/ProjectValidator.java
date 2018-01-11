package com.netcracker.project.controllers.validators;

import com.netcracker.project.controllers.validators.errorMessage.ErrorMessages;
import com.netcracker.project.model.entity.Project;
import com.netcracker.project.model.entity.Sprint;
import com.netcracker.project.model.entity.User;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class ProjectValidator extends AbstractValidator {

  public Map<String, String> validatePMTransitList(
      Collection<User> pmOnTransit) {
    if (pmOnTransit.isEmpty()) {
      setErrorToMap("PM_ON_TRANSIT_EMPTY_ERROR",
          ErrorMessages.PM_ON_TRANSIT_EMPTY_ERROR);
    }
    return getErrorMap();
  }

  public Map<String, String> validateDates(String start, String end) {
    validateStartEndDate(start, end);
    return getErrorMap();
  }

  public Map<String, String> validateInputId(String id) {
    validateId(id);
    return getErrorMap();
  }

  public Map<String, String> validateExistence(Integer projectExistence) {
    if (projectExistence == 0) {
      setErrorToMap("PROJECT_ERROR", ErrorMessages.PROJECT_ERROR);
    }
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
      String userId, String jobTitle) {
    validateId(projectId);
    validateId(userId);
    validateJobTitle(jobTitle);
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

  public Map<String, String> validateProjectSprintDates(Project project,
      Collection<Sprint> sprints,
      Sprint sprint) {
    if (project.getStartDate().compareTo(sprint.getStartDate()) == 1) {
      setErrorToMap("SPRINT_START_DATE_ERROR",
          ErrorMessages.SPRINT_START_DATE_ERROR);
    }
    if (project.getEndDate().compareTo(sprint.getPlannedEndDate()) == -1) {
      setErrorToMap("SPRINT_END_DATE_ERROR",
          ErrorMessages.SPRINT_END_DATE_ERROR);
    }
    for (Sprint oldSprint : sprints) {
      if (oldSprint.getEndDate().compareTo(sprint.getStartDate()) == 1) {
        setErrorToMap("NEW_OLD_SPRINT_START_DATE_ERROR",
            ErrorMessages.NEW_OLD_SPRINT_START_DATE_ERROR);
        return getErrorMap();
      }
    }
    return getErrorMap();
  }

  public Map<String, String> validateSprintName(Collection<Sprint> sprints,
      String sprintName) {
    for (Sprint sprint : sprints) {
      if (sprint.getName().equals(sprintName)) {
        setErrorToMap("SPRINT_NAME_EXISTENCE_ERROR",
            ErrorMessages.SPRINT_NAME_EXISTENCE_ERROR);
        return getErrorMap();
      }
    }
    return getErrorMap();
  }

  public Map<String, String> validateProjectAndWorkPeriodDates(Project project,
      Date startDate, Date endDate) {
    if (project.getStartDate().compareTo(startDate) == 1) {
      setErrorToMap("WORK_PERIOD_START_DATE_ERROR",
          ErrorMessages.WORK_PERIOD_START_DATE_ERROR);
    }
    if (project.getEndDate().compareTo(endDate) == -1) {
      setErrorToMap("WORK_PERIOD_END_DATE_ERROR",
          ErrorMessages.WORK_PERIOD_END_DATE_ERROR);
    }
    return getErrorMap();
  }

}
