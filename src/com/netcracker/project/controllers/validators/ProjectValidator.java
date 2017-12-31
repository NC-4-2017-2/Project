package com.netcracker.project.controllers.validators;

import com.netcracker.project.controllers.validators.errorMessage.ErrorMessages;
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

  public Map<String, String> validateUserName(String firstName, String lastName) {
    validateAbstractName(firstName);
    validateAbstractName(lastName);
    return getErrorMap();
  }
}
