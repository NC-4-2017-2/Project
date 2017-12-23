package com.netcracker.project.services.impl;

import com.netcracker.project.model.entity.WorkingDay;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WorkingDayService {

  public WorkingDay getWorkingDay(String start, String end,
      DayOfWeek dayOfWeek) {
    DateConverterService converter = new DateConverterService();
    LocalTime startTime = converter.getLocalTimeFromString(start);
    LocalTime endTime = converter.getLocalTimeFromString(end);
    Long minutesBetweenTimes = converter
        .getMinutesBetweenLocalTimes(startTime, endTime);
    Double hoursCount = converter.parseMinutes(minutesBetweenTimes);
    int currentWeekNumber = getCurrentWeekNumber();

    LocalDate localDate = LocalDate.now()
        .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, currentWeekNumber)
        .with(TemporalAdjusters.previousOrSame(dayOfWeek));

    WorkingDay workingDay = new WorkingDay.WorkingDayBuilder()
        .date(Date.from(
            localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
        .weekNumber(currentWeekNumber)
        .workingHours(hoursCount)
        .build();
    return workingDay;
  }

  private int getCurrentWeekNumber() {
    Date date = new Date();
    Calendar calendar = Calendar.getInstance(Locale.US);
    calendar.setTime(date);
    return calendar.get(Calendar.WEEK_OF_YEAR);
  }
}
