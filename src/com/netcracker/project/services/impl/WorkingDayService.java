package com.netcracker.project.services.impl;

import com.netcracker.project.model.entity.WorkingDay;
import com.netcracker.project.model.enums.Status;
import java.math.BigInteger;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WorkingDayService {

  public WorkingDay getWorkingDay(Long minutes,
      DayOfWeek dayOfWeek, BigInteger userId,
      BigInteger pmId) {
    DateConverterService converter = new DateConverterService();
    Double hoursCount = converter.parseMinutes(minutes);
    int currentWeekNumber = getCurrentWeekNumber();

    LocalDate localDate = LocalDate.now()
        .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, currentWeekNumber)
        .with(TemporalAdjusters.previousOrSame(dayOfWeek));

    WorkingDay workingDay = new WorkingDay.WorkingDayBuilder()
        .date(Date.from(
            localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
        .weekNumber(currentWeekNumber)
        .workingHours(hoursCount)
        .pmId(pmId)
        .userId(userId)
        .status(Status.WAITING_FOR_APPROVAL)
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
