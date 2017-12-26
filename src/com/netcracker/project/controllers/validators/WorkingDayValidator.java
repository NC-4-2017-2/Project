package com.netcracker.project.controllers.validators;

import com.netcracker.project.services.impl.DateConverterService;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorkingDayValidator extends AbstractValidator {

  private String timePattern = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

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
          setErrorToMap("timeError", day + " wrong start time format!");
        }
        if (!validateTime(end)) {
          setErrorToMap("timeError", day + " wrong  end time format!");
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

  private void validateIfStartIsEmpty(String start, String end, DayOfWeek day) {
    if (start.isEmpty() && !end.isEmpty()) {
      setErrorToMap("startDayEmptyError", day + " start day is empty!");
    }
  }


  private void validateIfEndIsEmpty(String start, String end, DayOfWeek day) {
    if (!start.isEmpty() && end.isEmpty()) {
      setErrorToMap("endDayEmptyError", day + " end day is empty!");
    }
  }

  private void validateIfNull(String start, String end, DayOfWeek day) {
    if (start == null || end == null) {
      setErrorToMap("nullError", day + " can't be null!");
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
      setErrorToMap("failOvertime", day + " working hours more than 12!");
    }
    if (minutesBetween < 0) {
      setErrorToMap("minutesBetweenError ",
          day + " start time can't be less than end time!");
    }
    if (minutesBetween == 0) {
      setErrorToMap("minutesBetweenError",
          day + " start time can't be same as end time!");
    }
  }

  private void validateIfDayIsPrevious(DayOfWeek comingDay) {
    LocalDate date = LocalDate.now();
    DayOfWeek currentDay = date.getDayOfWeek();
    int currentDayValue = currentDay.getValue();
    int comingDayValue = comingDay.getValue();
    if (currentDayValue < comingDayValue) {
      setErrorToMap("compareDayError",
          "Please, fill in only current or previous days!");
    }
  }

  private boolean validateDayOfWeek(DayOfWeek dayOfWeek) {
    if (dayOfWeek == null) {
      setErrorToMap("nullDayError", "Day of week can't be null!");
      return false;
    }
    for (DayOfWeek day : DayOfWeek.values()) {
      if (day.name().equals(dayOfWeek.name())) {
        return true;
      }
    }
    setErrorToMap("wrongDayOfWeek", "Please choose correct day of week!");
    return false;
  }

  private boolean validateTime(String time) {
    Pattern p = Pattern.compile(timePattern);
    Matcher m = p.matcher(time);
    return m.matches();
  }
}

