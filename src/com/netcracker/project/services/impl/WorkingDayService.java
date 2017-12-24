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
    LocalDate localDate = LocalDate.now();

    if (getCurrentDayOfWeek() == DayOfWeek.MONDAY) {
      localDate = getLocalDateNext(currentWeekNumber, dayOfWeek);
    }

    if (getCurrentDayOfWeek() == DayOfWeek.TUESDAY) {
      if (dayOfWeek == DayOfWeek.MONDAY) {
        localDate = getLocalDatePrevious(currentWeekNumber, dayOfWeek);
      } else {
        getLocalDateNext(currentWeekNumber, dayOfWeek);
      }
    }

    if (getCurrentDayOfWeek() == DayOfWeek.WEDNESDAY) {
      if (dayOfWeek == DayOfWeek.MONDAY) {
        localDate = getLocalDatePrevious(currentWeekNumber, dayOfWeek);
      } else if (dayOfWeek == DayOfWeek.TUESDAY) {
        localDate = getLocalDatePrevious(currentWeekNumber, dayOfWeek);
      } else {
        getLocalDateNext(currentWeekNumber, dayOfWeek);
      }
    }

    if (getCurrentDayOfWeek() == DayOfWeek.THURSDAY) {
      if (dayOfWeek == DayOfWeek.MONDAY) {
        localDate = getLocalDatePrevious(currentWeekNumber, dayOfWeek);
      } else if (dayOfWeek == DayOfWeek.TUESDAY) {
        localDate = getLocalDatePrevious(currentWeekNumber, dayOfWeek);
      } else if (dayOfWeek == DayOfWeek.WEDNESDAY) {
        localDate = getLocalDatePrevious(currentWeekNumber, dayOfWeek);
      } else {
        getLocalDateNext(currentWeekNumber, dayOfWeek);
      }
    }

    if (getCurrentDayOfWeek() == DayOfWeek.FRIDAY) {
      if (dayOfWeek == DayOfWeek.SATURDAY) {
        localDate = getLocalDateNext(currentWeekNumber, dayOfWeek);
      } else if (dayOfWeek == DayOfWeek.SUNDAY) {
        localDate = getLocalDateNext(currentWeekNumber, dayOfWeek);
      } else {
        localDate = getLocalDatePrevious(currentWeekNumber, dayOfWeek);
      }
    }

    if (getCurrentDayOfWeek() == DayOfWeek.SATURDAY) {
      if (dayOfWeek == DayOfWeek.SUNDAY) {
        localDate = getLocalDateNext(currentWeekNumber, dayOfWeek);
      } else {
        localDate = getLocalDatePrevious(currentWeekNumber, dayOfWeek);
      }
    }

    if (getCurrentDayOfWeek() == DayOfWeek.SUNDAY) {
      localDate = getLocalDatePrevious(currentWeekNumber, dayOfWeek);
    }

    return new WorkingDay.WorkingDayBuilder()
        .date(Date.from(
            localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
        .weekNumber(currentWeekNumber)
        .workingHours(hoursCount)
        .pmId(pmId)
        .userId(userId)
        .status(Status.WAITING_FOR_APPROVAL)
        .build();
  }

  private int getCurrentWeekNumber() {
    Date date = new Date();
    Calendar calendar = Calendar.getInstance(Locale.US);
    calendar.setTime(date);
    return calendar.get(Calendar.WEEK_OF_YEAR);
  }

  private DayOfWeek getCurrentDayOfWeek() {
    LocalDate date = LocalDate.now();
    return date.getDayOfWeek();
  }

  private LocalDate getLocalDateNext(int currentWeekNumber,
      DayOfWeek dayOfWeek) {
    return LocalDate.now()
        .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, currentWeekNumber)
        .with(TemporalAdjusters.nextOrSame(dayOfWeek));
  }

  private LocalDate getLocalDatePrevious(int currentWeekNumber,
      DayOfWeek dayOfWeek) {
    return LocalDate.now()
        .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, currentWeekNumber)
        .with(TemporalAdjusters.previousOrSame(dayOfWeek));
  }
}
