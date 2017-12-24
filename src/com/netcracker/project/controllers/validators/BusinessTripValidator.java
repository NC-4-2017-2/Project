package com.netcracker.project.controllers.validators;

import java.util.Map;

public class BusinessTripValidator extends AbstractValidator{

  public Map<String, String> validateFind(String status) {
    validateStatus(status);
    return getErrorMap();
  }

  public Map<String, String> validateCreate(String country, String startDate, String endDate) {
    validateCountry(country);
    validateStartEndDate(startDate, endDate);
    return getErrorMap();
  }

  public Map<String, String> validateUpdate(String country, String startDate,
      String endDate, String status) {
    validateCountry(country);
    validateStartEndDate(startDate, endDate);
    validateStatus(status);

    return getErrorMap();
  }
}
