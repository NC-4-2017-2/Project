package com.netcracker.project.controllers.validators;

import com.netcracker.project.controllers.validators.errorMessage.ErrorMessages;
import com.netcracker.project.model.enums.Status;
import com.netcracker.project.services.impl.DateConverterService;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.util.StringUtils;

abstract class AbstractValidator {

  private Map<String, String> errorMap;

  AbstractValidator() {
    errorMap = new HashMap<>();
  }

  void validateStartEndDate(String startDate, String endDate) {
    if (startDate == null) {
      errorMap.put("START_DATE_NULL_ERROR", ErrorMessages.START_DATE_NULL_ERROR);
    }

    if (endDate == null) {
      errorMap.put("END_DATE_NULL_ERROR", ErrorMessages.END_DATE_NULL_ERROR);
    }

    if (startDate != null && endDate != null) {

      if (checkStartDate(startDate) && checkEndDate(endDate)) {
        Date start = new DateConverterService()
            .convertStringToDateFromJSP(startDate);
        Date end = new DateConverterService()
            .convertStringToDateFromJSP(endDate);

        int dateCompare = start.compareTo(end);

        if (dateCompare == 0) {
          errorMap.put("START_DATE_EQUALS_END_DATE_ERROR", ErrorMessages.START_DATE_EQUALS_END_DATE_ERROR);
        }

        if (dateCompare == 1) {
          errorMap
              .put("START_DATE_BIGGER_THAN_END_DATE_ERROR", ErrorMessages.START_DATE_BIGGER_THAN_END_DATE_ERROR);
        }
      }
    }
  }

  void validateId(String id) {
    if(StringUtils.isEmpty(id)) {
      errorMap.put("EMPTY_ID_ERROR", ErrorMessages.EMPTY_ID_ERROR);
      return;
    }

    if(!checkId(id)) {
      errorMap.put("WRONG_ID_FORMAT_ERROR", ErrorMessages.WRONG_ID_FORMAT_ERROR);
      return;
    }

    try {
      BigInteger bigIntegerId = new BigInteger(id);
    } catch(NumberFormatException ex) {
      errorMap.put("WRONG_ID_FORMAT_ERROR", ErrorMessages.WRONG_ID_FORMAT_ERROR);
    }
  }

  void validateStatus(String status) {
    if (StringUtils.isEmpty(status)) {
      errorMap.put("EMPTY_STATUS_ERROR", ErrorMessages.EMPTY_STATUS_ERROR);
      return;
    }

    if (!statusEnumCheck(status)) {
      errorMap.put("INCORRECT_STATUS_ERROR", ErrorMessages.INCORRECT_STATUS_ERROR);
    }
  }

   void validateAbstractName(String name) {
    if (!checkName(name)) {
      errorMap.put("USER_FIRST_OR_LAST_NAME_ERROR",
          ErrorMessages.USER_FIRST_OR_LAST_NAME_ERROR);
    }
  }

  Map<String, String> getErrorMap() {
    return this.errorMap;
  }

  void setErrorToMap(String errorName, String errorDescription) {
    this.errorMap.put(errorName, errorDescription);
  }

  private boolean statusEnumCheck(String status) {
    for (Status enumStatus : Status.values()) {
      if (enumStatus.name().equals(status)) {
        return true;
      }
    }

    return false;
  }

  private boolean checkStartDate(String startDate) {
    if (!checkDate(startDate)) {
      errorMap.put("WRONG_START_DATE_FORMAT_ERROR", ErrorMessages.WRONG_START_DATE_FORMAT_ERROR);
      return false;
    }

    return true;
  }

  private boolean checkEndDate(String endDate) {
    if (!checkDate(endDate)) {
      errorMap.put("WRONG_END_DATE_FORMAT_ERROR", ErrorMessages.WRONG_END_DATE_FORMAT_ERROR);
      return false;
    }

    return true;
  }

  private boolean checkId(String id) {
    Pattern p = Pattern.compile(RegexPatterns.ID_PATTERN);
    Matcher m = p.matcher(id);
    return m.matches();
  }

  private boolean checkDate(String dateString) {
    Pattern p = Pattern.compile(RegexPatterns.DATE_PATTERN);
    Matcher m = p.matcher(dateString);
    return m.matches();
  }

  private boolean checkName(String name) {
    Pattern p = Pattern.compile(RegexPatterns.NAME_PATTERN);
    Matcher m = p.matcher(name);
    return m.matches();
  }
}
