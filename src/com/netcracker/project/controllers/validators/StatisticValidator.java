package com.netcracker.project.controllers.validators;

import com.netcracker.project.controllers.validators.errorMessage.ErrorMessages;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StatisticValidator extends AbstractValidator {

  public Map<String, String> validateDates(String startDate, String endDate) {
    validateStartEndDate(startDate, endDate);
    return getErrorMap();
  }

  public Map<String, String> validateName(String name) {
    if (!checkName(name)) {
      setErrorToMap("USER_FIRST_OR_LAST_NAME_ERROR",
          ErrorMessages.USER_FIRST_OR_LAST_NAME_ERROR);
    }
    return getErrorMap();
  }

  private boolean checkName(String name) {
    Pattern p = Pattern.compile(RegexPatterns.NAME_PATTERN);
    Matcher m = p.matcher(name);
    return m.matches();
  }
}
