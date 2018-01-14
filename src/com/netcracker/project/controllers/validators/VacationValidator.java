package com.netcracker.project.controllers.validators;

import com.netcracker.project.controllers.validators.errorMessage.ErrorMessages;
import com.netcracker.project.model.entity.User;
import com.netcracker.project.model.enums.ProjectStatus;
import java.util.Map;

public class VacationValidator extends AbstractValidator {

  public Map<String, String> validateVacationStatus(String status) {
    validateStatus(status);
    return getErrorMap();
  }

  public Map<String, String> validateVacationId(String id) {
    validateId(id);
    return getErrorMap();
  }

  public Map<String, String> validateCreateVacation(String startDate,
      String endDate, User user) {
    validateStartEndDate(startDate, endDate);
    if (user.getProjectStatus().name()
        .equals(ProjectStatus.TRANSIT.name())) {
      setErrorToMap("USER_ON_TRANSIT_ERROR",
          ErrorMessages.USER_ON_TRANSIT_ERROR);
    }
    return getErrorMap();
  }

  public Map<String, String> validatePmAndLmStatus(String pmStatus,
      String lmStatus) {
    validateStatus(pmStatus);
    validateStatus(lmStatus);
    return getErrorMap();
  }

  public Map<String, String> validateUpdateAuthor(String vacationId,
      String startDate, String endDate) {
    validateId(vacationId);
    validateStartEndDate(startDate, endDate);
    return getErrorMap();
  }

  public Map<String, String> validateVacationIdAndStatus(String vacationId,
      String status) {
    validateId(vacationId);
    validateStatus(status);
    return getErrorMap();
  }
}

