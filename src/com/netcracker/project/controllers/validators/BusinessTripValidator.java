package com.netcracker.project.controllers.validators;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BusinessTripValidator {

  Map<String, String> errorMap = new HashMap<>();

  public Map<String, String> validateUpdate(String country, Date startDate, Date endDate, String status) {
    validateCountry(country);
    validateStartEndDate(startDate, endDate);
    validateStatus(status);

    return errorMap;
  }

  private void validateCountry(String country) {
    if (country == null || country.isEmpty()) {
      errorMap.put("countryError" , "Country can't be empty");
    }
  }

  private void validateStartEndDate(Date startDate, Date endDate) {
    if (startDate != null || endDate != null) {
      int dateCompare = startDate.compareTo(endDate);

      if (dateCompare == 0) {
        errorMap.put("dateError", "Start date can't be same as end date");
      }

      if (dateCompare == 1) {
        errorMap.put("dateError", "Start date can't be bigger than end date");
      }
    }
  }

  private void validateStatus(String status) {
    if (status == null || status.toString().isEmpty()) {
      errorMap.put("statusError", "Status can't be empty");
    }
  }
}
