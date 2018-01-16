package com.netcracker.project.controllers.validators;

import com.netcracker.project.controllers.validators.errorMessage.ErrorMessages;
import com.netcracker.project.model.enums.UserRole;
import com.netcracker.project.services.impl.DateConverterService;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.util.StringUtils;

public class UserValidator extends AbstractValidator {

  public Map<String, String> validateCreate(String lastName,
      String firstName, String email, String dateOfBirth,
      String hireDate, String phoneNumber, String jobTitle) {
    validateName(lastName);
    validateName(firstName);
    validateEmail(email);
    validateBirthDate(dateOfBirth);
    validateHireDate(hireDate);
    validatePhoneNumber(phoneNumber);
    if (jobTitle != null) {
      validateJobTitle(jobTitle);
    }
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

  public Map<String, String> validateEmail(String email) {
    if (!validateEmailFormat(email)) {
      setErrorToMap("WRONG_EMAIL_ERROR",
          ErrorMessages.WRONG_EMAIL_FORMAT_ERROR);
    }
    return getErrorMap();
  }


  public Map<String, String> validateBirthDate(String birthDate) {
    DateConverterService dateService = new DateConverterService();

    if (!checkDate(birthDate)) {
      setErrorToMap("WRONG_BIRTHDAY_FORMAT_ERROR",
          ErrorMessages.WRONG_BIRTHDAY_FORMAT_ERROR);
      return getErrorMap();
    }

    Date birthDayDate = dateService.convertStringToDateFromJSP(birthDate);

    if (dateService.getYearFromDate(new Date()) - dateService
        .getYearFromDate(birthDayDate) < 18) {
      setErrorToMap("WRONG_BIRTH_DATE_ERROR",
          ErrorMessages.WRONG_BIRTH_DATE_ERROR);
    }
    if (dateService.getYearFromDate(new Date()) - dateService
        .getYearFromDate(birthDayDate) > 60) {
      setErrorToMap("WRONG_BIRTH_DATE_ERROR",
          ErrorMessages.WRONG_BIRTH_DATE_ERROR);
    }
    return getErrorMap();
  }

  public Map<String, String> validateHireDate(String hireDate) {
    DateConverterService dateService = new DateConverterService();

    if (!checkDate(hireDate)) {
      setErrorToMap("WRONG_HIRE_DATE_FORMAT_ERROR",
          ErrorMessages.WRONG_HIRE_DATE_FORMAT_ERROR);
      return getErrorMap();
    }

    Date hireDayDate = dateService.convertStringToDateFromJSP(hireDate);

    int compareStart = new Date().compareTo(hireDayDate);

    if (compareStart == -1) {
      setErrorToMap("HIRE_DATE_CURRENT_DATE_ERROR",
          ErrorMessages.HIRE_DATE_CURRENT_DATE_ERROR);
    }

    return getErrorMap();
  }

  public Map<String, String> validatePhoneNumber(String phoneNumber) {
    if (!checkPhoneNumber(phoneNumber)) {
      setErrorToMap("PHONE_NUMBER_FORMAT_ERROR",
          ErrorMessages.PHONE_NUMBER_FORMAT_ERROR);
    }

    return getErrorMap();
  }

  private boolean validateEmailFormat(String email) {
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

  private boolean checkDate(String dateString) {
    Pattern p = Pattern.compile(RegexPatterns.DATE_PATTERN);
    Matcher m = p.matcher(dateString);
    return m.matches();
  }

  private boolean checkPhoneNumber(String phoneNumber) {
    Pattern p = Pattern.compile(RegexPatterns.PHONE_NUMBER_PATTERN);
    Matcher m = p.matcher(phoneNumber);
    return m.matches();
  }
}
