package com.netcracker.project.controllers.validators;

import com.netcracker.project.services.impl.DateConverterService;
import java.time.DayOfWeek;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorkingDayValidator extends AbstractValidator {

  private String timePattern = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

  public Map<String, String> validateCreate(String start, String end,
      DayOfWeek day) {
    if (start == null || end == null) {
      setErrorToMap("nullError", day + " can't be null");
    }
    if (start.isEmpty() && !end.isEmpty()) {
      setErrorToMap("startDayEmptyError", day + " start day is empty");
    }
    if (!start.isEmpty() && end.isEmpty()) {
      setErrorToMap("endDayEmptyError", day + " end day is empty");
    }
    if (start != null && end != null) {
      if (!start.isEmpty() && !end.isEmpty()) {
        if (!validateTime(start)) {
          setErrorToMap("timeError", day + " wrong start time format");
        }
        if (!validateTime(end)) {
          setErrorToMap("timeError", day + " wrong  end time format");
        }
        if (validateTime(start) && validateTime(end)) {
          validateTimeDiff(start, end, day);
        }
      }
    }
    return getErrorMap();
  }

  private void validateTimeDiff(String start, String end, DayOfWeek day) {
    DateConverterService converter = new DateConverterService();
    Long minutesBetween = converter
        .getMinutesBetweenLocalTimes(
            converter.getLocalTimeFromString(start),
            converter.getLocalTimeFromString(end));
    if (minutesBetween < 0) {
      setErrorToMap("minutesBetweenError ",
          day + " start time can't be less than end time");
    }
    if (minutesBetween == 0) {
      setErrorToMap("minutesBetweenError",
          day + " start time can't be same as end time");
    }
  }

  boolean validateTime(String time) {
    Pattern p = Pattern.compile(timePattern);
    Matcher m = p.matcher(time);
    return m.matches();
  }
}

