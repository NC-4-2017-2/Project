package com.netcracker.project.controllers.validators;

import com.netcracker.project.controllers.validators.errorMessage.ErrorMessages;
import java.util.Map;

public class UserValidator extends AbstractValidator {

  public Map<String, String> validateInteger(String countSprints) {
    validateId(countSprints);
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

  public Map<String, String> validateUserExistence(Integer existence) {
    if (existence == 0) {
      setErrorToMap("USER_ERROR", ErrorMessages.USER_ERROR);
    }
    return getErrorMap();
  }

  public Map<String, String> validateWorkPeriodExistence(Integer existence) {
    if (existence == 0) {
      setErrorToMap("WORK_PERIOD_ERROR", ErrorMessages.WORK_PERIOD_ERROR);
    }
    return getErrorMap();
  }
}
