package com.netcracker.project.controllers.validators;

import com.netcracker.project.services.impl.ListCountry;
import java.util.Map;

public class BusinessTripValidator extends AbstractValidator {

  public Map<String, String> validateFind(String status) {
    validateStatus(status);
    return getErrorMap();
  }

  public Map<String, String> validateCreate(String country, String startDate,
      String endDate) {
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

  private void validateCountry(String country) {
    if (country == null || country.isEmpty()) {
      setErrorToMap("countryError", "Country can't be empty!");
    }

    if (country != null && !country.isEmpty()) {
      validateInCountryList(country);
    }
  }

  private void validateInCountryList(String country) {
    ListCountry listCountry = new ListCountry();

    for (String countryName : listCountry.getCountriesNames()) {
      if (country.equals(countryName)) {
        return;
      }
    }
    setErrorToMap("countryError", "Country doesn't exist!");
  }
}
