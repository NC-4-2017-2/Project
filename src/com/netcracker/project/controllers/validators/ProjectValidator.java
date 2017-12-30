package com.netcracker.project.controllers.validators;

import java.util.Map;

public class ProjectValidator extends AbstractValidator {

  public Map<String, String> validateInteger(String countSprints) {
    validateId(countSprints);
    return getErrorMap();
  }

  public Map<String, String> validateDates(String start, String end) {
    validateStartEndDate(start, end);
    return getErrorMap();
  }
}
