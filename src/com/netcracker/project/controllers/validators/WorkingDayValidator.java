package com.netcracker.project.controllers.validators;

import com.netcracker.project.controllers.validators.errorMessage.ErrorMessages;
import com.netcracker.project.services.impl.DateConverterService;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorkingDayValidator extends AbstractValidator {

  public Map<String, String> validateWorkingDayStatus(String status) {
    validateStatus(status);
    return getErrorMap();
  }

  public Map<String, String> validateCreate(String start, String end,
      DayOfWeek day) {
    validateIfNull(start, end, day);
    if (validateDayOfWeek(day)) {
      validateIfDayIsPrevious(day);
    }
    if (start != null && end != null) {
      validateIfStartIsEmpty(start, end, day);
      validateIfEndIsEmpty(start, end, day);
      if (!start.isEmpty() && !end.isEmpty()) {
        if (!validateTime(start)) {
          setErrorToMap("TIME_ERROR", day + ErrorMessages.TIME_ERROR);
        }
        if (!validateTime(end)) {
          setErrorToMap("TIME_ERROR", day + ErrorMessages.TIME_ERROR);
        }
        if (validateTime(start) && validateTime(end)) {
          validateTimeDiff(start, end, day);
        }
      }
    }
    return getErrorMap();
  }

  public Map<String, String> validateFind(String start, String end) {
    validateStartEndDate(start, end);
    return getErrorMap();
  }

  public Map<String, String> validateExistence(Integer workingDayExistence) {
    if (workingDayExistence == 0) {
      setErrorToMap("WORKING_EXISTENCE_ERROR", ErrorMessages.WORKING_EXISTENCE_ERROR);
    }
    return getErrorMap();
  }

  public Map<String, String> validateInputId(String id) {
    validateId(id);
    return getErrorMap();
  }

  private void validateIfStartIsEmpty(String start, String end, DayOfWeek day) {
    if (start.isEmpty() && !end.isEmpty()) {
      setErrorToMap("EMPTY_START_DAY_ERROR", day + ErrorMessages.EMPTY_START_DAY_ERROR);
    }
  }


  private void validateIfEndIsEmpty(String start, String end, DayOfWeek day) {
    if (!start.isEmpty() && end.isEmpty()) {
      setErrorToMap("EMPTY_END_DAY_ERROR", day + ErrorMessages.EMPTY_END_DAY_ERROR);
    }
  }

  private void validateIfNull(String start, String end, DayOfWeek day) {
    if (start == null || end == null) {
      setErrorToMap("NULL_ERROR", day + ErrorMessages.NULL_ERROR);
    }
  }

  private void validateTimeDiff(String start, String end, DayOfWeek day) {
    DateConverterService converter = new DateConverterService();
    Long minutesBetween = converter
        .getMinutesBetweenLocalTimes(
            converter.getLocalTimeFromString(start),
            converter.getLocalTimeFromString(end));
    DateConverterService service = new DateConverterService();
    Double workHours = service.parseMinutes(minutesBetween);
    if (workHours > 12.00) {
      setErrorToMap("WORKING_DAY_OVERSTATEMENT_ERROR", day + ErrorMessages.WORKING_DAY_OVERSTATEMENT_ERROR);
    }
    if (minutesBetween < 0) {
      setErrorToMap("START_TIME_LESS_END_TIME_ERROR ",
          day + ErrorMessages.START_TIME_LESS_END_TIME_ERROR);
    }
    if (minutesBetween == 0) {
      setErrorToMap("START_TIME_EQUALS_END_TIME_ERROR",
          day + ErrorMessages.START_TIME_EQUALS_END_TIME_ERROR);
    }
  }

  private void validateIfDayIsPrevious(DayOfWeek comingDay) {
    LocalDate date = LocalDate.now();
    DayOfWeek currentDay = date.getDayOfWeek();
    int currentDayValue = currentDay.getValue();
    int comingDayValue = comingDay.getValue();
    if (currentDayValue < comingDayValue) {
      setErrorToMap("DAYS_FILL_ERROR",
          ErrorMessages.DAYS_FILL_ERROR);
    }
  }

  private boolean validateDayOfWeek(DayOfWeek dayOfWeek) {
    if (dayOfWeek == null) {
      setErrorToMap("NULL_DAY_ERROR", ErrorMessages.NULL_DAY_ERROR);
      return false;
    }
    for (DayOfWeek day : DayOfWeek.values()) {
      if (day.name().equals(dayOfWeek.name())) {
        return true;
      }
    }
    setErrorToMap("WRONG_DAY_ERROR", ErrorMessages.WRONG_DAY_ERROR);
    return false;
  }

  private boolean validateTime(String time) {
    Pattern p = Pattern.compile(RegexPatterns.TIME_PATTERN);
    Matcher m = p.matcher(time);
    return m.matches();
  }
}

