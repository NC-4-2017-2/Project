package com.netcracker.project.controllers.validators;

import java.util.Map;

public class VacationValidator extends AbstractValidator {

  public Map<String, String> validateDates(String startDate, String endDate) {
    validateStartEndDate(startDate, endDate);
    return getErrorMap();
  }

  public Map<String, String> validateVacationStatus(String status) {
    validateStatus(status);
    return getErrorMap();
  }

  public Map<String, String> validateVacationId(String id) {
    validateId(id);
    return getErrorMap();
  }
}
