package com.netcracker.project.services.impl;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import com.netcracker.project.model.impl.BusinessTripDAOImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class DateConverterService {

  private static final Logger logger = Logger
      .getLogger(BusinessTripDAOImpl.class);
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
      "dd.MM.yyyy");

  public Date convertStringToDate(String str) {
    Date date = null;
    String pattern = "dd.MM.yyyy";
    DateFormat dateFormat = new SimpleDateFormat(pattern);
    try {
      date = dateFormat.parse(str);
    } catch (ParseException e) {
      logger.error("ParseException : " + e.getMessage());
    }
    return date;
  }

  public String convertDateToString(Date startDate) {
    String pattern = "yyyy-MM-dd";
    String patternWriteFormat = "yyyy-MM-dd";
    String dateStr = startDate.toString();
    DateFormat readFormat = new SimpleDateFormat(pattern, Locale.US);
    DateFormat writeFormat = new SimpleDateFormat(patternWriteFormat);
    Date date = null;
    try {
      date = readFormat.parse(dateStr);
    } catch (ParseException e) {
      logger.error("ParseException : " + e.getMessage());
    }
    String formattedDate = "";
    if (date != null) {
      formattedDate = writeFormat.format(date);
    }
    return formattedDate;
  }

  public Date convertStringToDateFromJSP(String str) {
    Date date = null;
    String pattern = "yyyy-MM-dd";
    DateFormat dateFormat = new SimpleDateFormat(pattern);
    try {
      date = dateFormat.parse(str);
    } catch (ParseException e) {
      logger.error("ParseException : " + e.getMessage());
    }
    return date;
  }

  public LocalTime getLocalTimeFromString(String time) {
    return LocalTime.parse(time);
  }

  public Long getMinutesBetweenLocalTimes(LocalTime start, LocalTime end) {
    return MINUTES.between(start, end);
  }

  public Double parseMinutes(String time) {
    String[] timeSplit = time.split(":");
    Integer hours = Integer.valueOf(timeSplit[0].trim());
    Integer minutes = Integer.valueOf(timeSplit[1].trim());
    double hoursDouble = hours;
    double minutesDouble = minutes;
    double sum = hoursDouble + (minutesDouble / 60);
    return Math.floor(sum * 100) / 100;
  }

  public String getHoursFromDouble(double hours){
    long iPart = (long)hours;
    double fPart = hours - iPart;
    double x = Math.ceil(fPart * 60.0) / 100;
    return String.valueOf(iPart + x);
  }

  public Integer getYearFromDate(Date date) {
    int result = -1;
    if (date != null) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      result = cal.get(Calendar.YEAR);
    }
    return result;
  }

}