package com.netcracker.project.controllers.validators;

import com.netcracker.project.controllers.validators.errorMessage.ErrorMessages;
import com.netcracker.project.model.entity.Project;
import com.netcracker.project.services.impl.ListCountry;
import java.util.Date;
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

  public Map<String, String> validateBetweenDates(String startDate,
      String endDate) {
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

  public Map<String, String> validateCreateId(String projectId, String userId,
      String authorId, String pmId) {
    validateId(projectId);
    validateId(userId);
    validateId(authorId);
    validateId(pmId);
    return getErrorMap();
  }

  public Map<String, String> validateProjectAndTripDates(Project project,
      Date startDate, Date endDate) {
    if (project.getStartDate().compareTo(startDate) == 1) {
      setErrorToMap("TRIP_START_DATE_ERROR", ErrorMessages.TRIP_START_DATE_ERROR);
    }
    if (project.getEndDate().compareTo(endDate) == -1) {
      setErrorToMap("TRIP_END_DATE_ERROR", ErrorMessages.TRIP_END_DATE_ERROR);
    }
    return getErrorMap();
  }
}
