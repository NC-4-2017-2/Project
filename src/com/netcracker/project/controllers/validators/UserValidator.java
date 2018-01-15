package com.netcracker.project.controllers.validators;

import com.netcracker.project.controllers.validators.errorMessage.ErrorMessages;
import com.netcracker.project.model.enums.UserRole;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.util.StringUtils;

public class UserValidator extends AbstractValidator {

  public Map<String, String> validateCreate(String lastName,
      String firstName, String email, String dateOfBirth,
      String hireDate, String phoneNumber, String jobTitle,
      String userRole) {
    validateName(lastName);
    validateName(firstName);
    validateJobTitle(jobTitle);
    if (!validateEmail(email)) {
      setErrorToMap("WRONG_EMAIL_ERROR", ErrorMessages.WRONG_EMAIL_FORMAT_ERROR);
    }
    validateUserRole(userRole);
    return getErrorMap();
  }

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

  public Map<String, String> validateName(String name) {
    validateAbstractName(name);
    return getErrorMap();
  }

  public Map<String, String> validateUserExistence(Integer existence) {
    if (existence == 0) {
      setErrorToMap("USER_ERROR", ErrorMessages.USER_ERROR);
    }
    return getErrorMap();
  }

  public Map<String, String> validateUserLoginExistence(Integer existence) {
    if (existence == 0) {
      setErrorToMap("USER_ERROR", ErrorMessages.USER_ERROR);
    }
    return getErrorMap();
  }

  public Map<String, String> validateUserLoginIfExist(Integer existence) {
    if (existence > 0) {
      setErrorToMap("USER_EXIST_ERROR", ErrorMessages.USER_EXIST_ERROR);
    }
    return getErrorMap();
  }

  public Map<String, String> validateWorkPeriodExistence(Integer existence) {
    if (existence == 0) {
      setErrorToMap("WORK_PERIOD_ERROR", ErrorMessages.WORK_PERIOD_ERROR);
    }
    return getErrorMap();
  }

  private boolean validateEmail(String email) {
    Pattern p = Pattern.compile(RegexPatterns.EMAIL_PATTERN);
    Matcher m = p.matcher(email);
    return m.matches();
  }

  private void validateUserRole(String userRole) {
    if (StringUtils.isEmpty(userRole)) {
      setErrorToMap("EMPTY_USER_ROLE_ERROR",
          ErrorMessages.EMPTY_USER_ROLE_ERROR);
      return;
    }
    if (!userRoleEnumCheck(userRole)) {
      setErrorToMap("INCORRECT_USER_ROLE_ERROR",
          ErrorMessages.INCORRECT_USER_ROLE_ERROR);
    }
  }

  private boolean userRoleEnumCheck(String userRole) {
    for (UserRole enumStatus : UserRole.values()) {
      if (enumStatus.name().equals(userRole)) {
        return true;
      }
    }
    return false;
  }
}
