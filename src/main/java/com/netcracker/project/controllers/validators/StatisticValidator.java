package com.netcracker.project.controllers.validators;

import com.netcracker.project.controllers.validators.errorMessage.ErrorMessages;
import com.netcracker.project.model.entity.User;
import com.netcracker.project.model.enums.ProjectStatus;
import java.util.Map;

public class StatisticValidator extends AbstractValidator {

  public Map<String, String> validateDates(String startDate, String endDate) {
    validateStartEndDate(startDate, endDate);
    return getErrorMap();
  }

  public Map<String, String> validateNameStatistic(String name) {
    validateAbstractName(name);
    return getErrorMap();
  }

  public Map<String, String> validateProjectManager(User user) {
    if (user.getProjectStatus().name()
        .equals(ProjectStatus.TRANSIT.name())) {
      setErrorToMap("VIEW_SPRINT_TRANSIT_PM_ERROR",
          ErrorMessages.VIEW_SPRINT_TRANSIT_PM_ERROR);
    }
    return getErrorMap();
  }
}
