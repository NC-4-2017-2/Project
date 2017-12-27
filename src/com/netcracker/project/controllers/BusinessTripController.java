package com.netcracker.project.controllers;

import com.netcracker.project.controllers.validators.BusinessTripValidator;
import com.netcracker.project.model.BusinessTripDAO;
import com.netcracker.project.model.ProjectDAO;
import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.entity.BusinessTrip;
import com.netcracker.project.model.entity.Project;
import com.netcracker.project.model.entity.User;
import com.netcracker.project.model.enums.JobTitle;
import com.netcracker.project.model.enums.Status;
import com.netcracker.project.services.impl.DateConverterService;
import com.netcracker.project.services.impl.ListCountry;
import java.math.BigInteger;
import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/businessTrip")
public class BusinessTripController {

  @Autowired
  private ProjectDAO projectDAO;
  @Autowired
  private UserDAO userDAO;
  @Autowired
  private BusinessTripDAO businessTripDAO;
  @Autowired
  private ListCountry countries;
  @Autowired
  private DateConverterService converter;


  @RequestMapping(value = "createBusinessTrip", method = RequestMethod.GET)
  public String createBusinessTripGet(Model model, Principal principal) {
    String userLogin = principal.getName();
    User user = userDAO.findUserByLogin(userLogin);
    BigInteger projectId = null;

    if (user.getJobTitle().equals(JobTitle.SOFTWARE_ENGINEER)) {
      projectId = projectDAO
          .findProjectIdByUserLogin(userLogin);
    }
    if (user.getJobTitle().equals(JobTitle.PROJECT_MANAGER)) {
      projectId = projectDAO
          .findProjectIdByPMLogin(userLogin);
    }
    Collection<User> users = userDAO.findUserByProjectId(projectId);

    Project project = projectDAO.findProjectByProjectId(projectId);
    model.addAttribute("pmId", project.getProjectManagerId());
    model.addAttribute("projectId", project.getProjectId());
    model.addAttribute("countryList", countries.getCountriesNames());
    model.addAttribute("userList", users);
    model.addAttribute("authorId", user.getUserId());
    return "businessTrip/createBusinessTrip";
  }

  @RequestMapping(value = "createBusinessTrip/projectId/{projectId}/pmId/{pmId}", method = RequestMethod.POST)
  public String createBusinessTripPost(
      @PathVariable(value = "projectId") String projectId,
      @PathVariable(value = "pmId") String pmId,
      @RequestParam(value = "startDate") String startDate,
      @RequestParam(value = "endDate") String endDate,
      @RequestParam(value = "user") BigInteger userId,
      @RequestParam(value = "authorId") BigInteger authorId,
      @RequestParam(value = "country") String country,
      Model model) {
    Map<String, String> errorMap = new HashMap<>();
    BusinessTripValidator validator = new BusinessTripValidator();
    errorMap = validator.validateInputId(projectId);
    if(!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "businessTrip/createBusinessTrip";
    }
    errorMap = validator.validateInputId(pmId);
    if(!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "businessTrip/createBusinessTrip";
    }
    BigInteger projectBigIntegerId = new BigInteger(projectId);
    BigInteger pmBigIntegerId = new BigInteger(pmId);


    Collection<User> users = userDAO.findUserByProjectId(projectBigIntegerId);
    errorMap = new BusinessTripValidator()
        .validateCreate(country, startDate, endDate);

    if (!errorMap.isEmpty()) {
      model.addAttribute("projectId", projectBigIntegerId);
      model.addAttribute("pmId", pmBigIntegerId);
      model.addAttribute("authorId", authorId);
      model.addAttribute("userList", users);
      model.addAttribute("countryList", countries.getCountriesNames());
      model.addAttribute("tripCountry", country);
      model.addAttribute("errorMap", errorMap);
      return "businessTrip/createBusinessTrip";
    }

    BusinessTrip trip = new BusinessTrip.BusinessTripBuilder()
        .authorId(authorId)
        .projectId(projectBigIntegerId)
        .userId(userId)
        .pmId(pmBigIntegerId)
        .country(country)
        .startDate(converter.convertStringToDateFromJSP(startDate))
        .endDate(converter.convertStringToDateFromJSP(endDate))
        .status(Status.DISAPPROVED)
        .build();

    businessTripDAO.createTrip(trip);
    return "response_status/success";
  }

  @RequestMapping(value = "businessTripToUpdate/{id}", method = RequestMethod.GET)
  public String updateBusinessTrip(Model model,
      @PathVariable(value = "id") String id) {
    BusinessTripValidator validator = new BusinessTripValidator();
    Map<String, String> errorMap = validator.validateInputId(id);
    if(!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "businessTrip/updateBusinessTrip";
    }
    BigInteger bigIntegerId = new BigInteger(id);
    BusinessTrip trip = businessTripDAO.findBusinessTripById(bigIntegerId);
    model.addAttribute("countryList", countries.getCountriesNames());
    model.addAttribute("businessTripId", trip.getBusinessTripId());
    model.addAttribute("startDate", trip.getStartDate());
    model.addAttribute("endDate", trip.getEndDate());
    model.addAttribute("status", trip.getStatus());
    model.addAttribute("tripCountry", trip.getCountry());
    return "businessTrip/updateBusinessTrip";
  }

  @RequestMapping(value = "businessTripToUpdate/{id}", method = RequestMethod.POST)
  public String updateBusinessTrip(Model model,
      @PathVariable(value = "id") String id,
      @RequestParam(value = "country") String country,
      @RequestParam(value = "startDate") String startDate,
      @RequestParam(value = "endDate") String endDate,
      @RequestParam(value = "status") String status) {
    BusinessTripValidator validator = new BusinessTripValidator();
    Map<String, String> errorMap = validator.validateInputId(id);
    if(!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "businessTrip/updateBusinessTrip";
    }
    BigInteger bigIntegerId = new BigInteger(id);
    errorMap = new BusinessTripValidator()
        .validateUpdate(country, startDate, endDate, status);
    Status correctStatus = businessTripDAO.findBusinessTripById(bigIntegerId).getStatus();

    if (!errorMap.isEmpty()) {
      model.addAttribute("countryList", countries.getCountriesNames());
      model.addAttribute("businessTripId", bigIntegerId);
      model.addAttribute("startDate", startDate);
      model.addAttribute("endDate", endDate);
      model.addAttribute("status", correctStatus);
      model.addAttribute("tripCountry", country);
      model.addAttribute("errorMap", errorMap);
      return "businessTrip/updateBusinessTrip";
    }

    BusinessTrip trip = new BusinessTrip.BusinessTripBuilder()
        .businessTripId(bigIntegerId)
        .country(country)
        .startDate(converter.convertStringToDateFromJSP(startDate))
        .endDate(converter.convertStringToDateFromJSP(endDate))
        .status(Status.valueOf(status))
        .build();

    businessTripDAO.updateTrip(trip);
    return "response_status/success";
  }

  @RequestMapping(value = "findTrip", method = RequestMethod.GET)
  public String findTripByStatus() {
    return "businessTrip/findTrip";
  }

  @RequestMapping(value = "findTrip", params = "status",
      method = RequestMethod.GET)
  public String showTripList(
      @RequestParam(value = "status") String status,
      Principal principal,
      Model model) {
    Map<String, String> errorMap = new BusinessTripValidator()
        .validateFind(status);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "businessTrip/findTrip";
    }
    String userLogin = principal.getName();
    User user = userDAO.findUserByLogin(userLogin);
    Collection<BusinessTrip> trips = null;
    if (user.getJobTitle().equals(JobTitle.PROJECT_MANAGER)) {
      trips = businessTripDAO
          .findTripByPMIdAndStatus(user.getUserId(),
              Status.valueOf(status).getId());
    }
    if (user.getJobTitle().equals(JobTitle.SOFTWARE_ENGINEER)) {
      trips = businessTripDAO
          .findTripByUserIdAndStatus(user.getUserId(),
              Status.valueOf(status).getId());
    }
    model.addAttribute("tripList", trips);
    return "businessTrip/showTripsList";
  }

  @RequestMapping(value = "showTrip/{businessTripId}", method = RequestMethod.GET)
  public String showTrip(
      @PathVariable(value = "businessTripId") String tripId,
      Model model) {
    BusinessTripValidator validator = new BusinessTripValidator();
    Map<String, String> errorMap = validator.validateInputId(tripId);
    if(!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "businessTrip/showTrip";
    }
    BigInteger bigIntegerId = new BigInteger(tripId);
    BusinessTrip trip = businessTripDAO
        .findBusinessTripById(bigIntegerId);
    model.addAttribute("businessTrip", trip);
    return "businessTrip/showTrip";
  }
}
