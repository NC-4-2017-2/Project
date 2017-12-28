package com.netcracker.project.controllers.validators;

import com.netcracker.project.controllers.validators.errorMessage.ErrorMessages;
import com.netcracker.project.services.impl.ListCountry;
import java.util.Map;
import org.springframework.util.StringUtils;

public class BusinessTripValidator extends AbstractValidator {

  public Map<String, String> validateBusinessTripStatus(String status) {
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
      String endDate) {
    validateCountry(country);
    validateStartEndDate(startDate, endDate);
    return getErrorMap();
  }

  public Map<String, String> validateInputId(String id) {
    validateId(id);
    return getErrorMap();
  }

  public Map<String, String> validateExistence(Integer businessTripExistence) {
    if (businessTripExistence == 0) {
      setErrorToMap("BUSINESS_TRIP_ERROR", ErrorMessages.BUSINESS_TRIP_ERROR);
    }
    return getErrorMap();
  }

  public Map<String, String> validateBetweenDates(String startDate, String endDate) {
    validateStartEndDate(startDate, endDate);
    return getErrorMap();
  }

  private void validateCountry(String country) {
    if (StringUtils.isEmpty(country)) {
      setErrorToMap("EMPTY_COUNTRY_ERROR", ErrorMessages.EMPTY_COUNTRY_ERROR);
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
    setErrorToMap("COUNTRY_EXISTENCE_ERROR",
        ErrorMessages.COUNTRY_EXISTENCE_ERROR);
  }

}
