package com.netcracker.project.controllers.validators;

import com.netcracker.project.model.enums.Status;
import com.netcracker.project.model.impl.mappers.MapperDateConverter;
import com.netcracker.project.services.ListCountry;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BusinessTripValidator {

  Map<String, String> errorMap = new HashMap<>();
  String datePattern = "[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])";

  public Map<String, String> validateFind(String status) {
    validateStatus(status);
    return errorMap;
  }

  public Map<String, String> validateCreate(String country, String startDate, String endDate) {
    validateCountry(country);
    validateStartEndDate(startDate, endDate);
    return errorMap;
  }

  public Map<String, String> validateUpdate(String country, String startDate,
      String endDate, String status) {
    validateCountry(country);
    validateStartEndDate(startDate, endDate);
    validateStatus(status);

    return errorMap;
  }

  private void validateCountry(String country) {
    if (country == null || country.isEmpty()) {
      errorMap.put("countryError", "Country can't be empty!");
    }

    if(country != null && !country.isEmpty()) {
      validateInCountryList(country);
    }
  }

  private boolean validateInCountryList(String country) {
    ListCountry listCountry = new ListCountry();

    for(String countryName : listCountry.getCountriesNames()) {
      if(country.equals(countryName)) {
        return true;
      }
    }

    errorMap.put("countryError", "Country doesn't exist!");
    return false;
  }

  private void validateStartEndDate(String startDate, String endDate) {
    if (startDate == null) {
      errorMap.put("startDateError", "Start date can't null!");
    }

    if (endDate == null) {
      errorMap.put("endDateError", "End date can't null!");
    }

    if (startDate != null || endDate != null) {

      if (checkStartDate(startDate) && checkEndDate(endDate)) {
        Date start = new MapperDateConverter().convertStringToDateFromJSP(startDate);
        Date end = new MapperDateConverter().convertStringToDateFromJSP(endDate);

        int dateCompare = start.compareTo(end);

        if (dateCompare == 0) {
          errorMap.put("dateError", "Start date can't be same as end date!");
        }

        if (dateCompare == 1) {
          errorMap.put("dateError", "Start date can't be bigger than end date!");
        }
      }
    }
  }

  private void validateStatus(String status) {
    if (status == null || status.isEmpty()) {
      errorMap.put("statusError", "Status can't be empty!");
    }

    if(!statusEnumCheck(status)) {
      errorMap.put("statusError", "Incorrect status!");
    }
  }

  private boolean checkStartDate(String startDate) {
    if (!checkDate(startDate)) {
      errorMap.put("startDateError", "Wrong start data format!");
      return false;
    }

    return true;
  }

  private boolean checkEndDate(String endDate) {
    if (!checkDate(endDate)) {
      errorMap.put("endDateError", "Wrong end data format!");
      return false;
    }

    return true;
  }

  private boolean checkDate(String dateString) {
    Pattern p = Pattern.compile(datePattern);
    Matcher m = p.matcher(dateString);
    return m.matches();
  }

  private boolean statusEnumCheck(String status) {
    for(Status enumStatus : Status.values()) {
      if(enumStatus.name().equals(status)) {
        return true;
      }
    }

    return false;
  }
}
