package com.netcracker.project.controllers;

import com.netcracker.project.controllers.validators.BusinessTripValidator;
import com.netcracker.project.model.BusinessTripDAO;
import com.netcracker.project.model.ProjectDAO;
import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.UserDAO.JobTitle;
import com.netcracker.project.model.entity.BusinessTrip;
import com.netcracker.project.model.entity.Project;
import com.netcracker.project.model.enums.Status;
import com.netcracker.project.model.entity.User;
import com.netcracker.project.model.impl.mappers.MapperDateConverter;
import com.netcracker.project.services.ListCountry;
import java.math.BigInteger;
import java.security.Principal;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
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
  private MapperDateConverter converter = new MapperDateConverter();


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
      @PathVariable(value = "projectId") BigInteger projectId,
      @PathVariable(value = "pmId") BigInteger pmId,
      @RequestParam(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @RequestParam(value = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
      @RequestParam(value = "user") BigInteger userId,
      @RequestParam(value = "authorId") BigInteger authorId,
      @RequestParam(value = "country") String country,
      Model model
  ) {
    Collection<User> users = userDAO.findUserByProjectId(projectId);
    Map<String, String> errorMap = new BusinessTripValidator()
        .validateCreate(country, startDate, endDate);

    if (!errorMap.isEmpty()) {
      model.addAttribute("projectId", projectId);
      model.addAttribute("pmId", pmId);
      model.addAttribute("authorId", authorId);
      model.addAttribute("userList", users);
      model.addAttribute("countryList", countries.getCountriesNames());
      model.addAttribute("tripCountry", country);
      model.addAttribute("errorMap", errorMap);
      return "businessTrip/createBusinessTrip";
    }

    BusinessTrip trip = new BusinessTrip.BusinessTripBuilder()
        .authorId(authorId)
        .projectId(projectId)
        .userId(userId)
        .pmId(pmId)
        .country(country)
        .startDate(startDate)
        .endDate(endDate)
        .status(Status.DISAPPROVED)
        .build();

    businessTripDAO.createTrip(trip);
    return "response_status/success";
  }


  @RequestMapping(value = "businessTripToUpdate/{id}", method = RequestMethod.GET)
  public String updateBusinessTrip(Model model,
      @PathVariable(value = "id") BigInteger id) {
    BusinessTrip trip = businessTripDAO.findBusinessTripById(id);
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
      @PathVariable(value = "id") BigInteger id,
      @RequestParam(value = "country") String country,
      @RequestParam(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @RequestParam(value = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
      @RequestParam(value = "status") String status) {
    Map<String, String> errorMap = new BusinessTripValidator()
        .validateUpdate(country, startDate, endDate, status);

    if (!errorMap.isEmpty()) {
      model.addAttribute("countryList", countries.getCountriesNames());
      model.addAttribute("businessTripId", id);
      model.addAttribute("startDate", converter.convertDateToString(startDate));
      model.addAttribute("endDate", converter.convertDateToString(endDate));
      model.addAttribute("status", status);
      model.addAttribute("tripCountry", country);
      model.addAttribute("errorMap", errorMap);
      return "businessTrip/updateBusinessTrip";
    }

    BusinessTrip trip = new BusinessTrip.BusinessTripBuilder()
        .businessTripId(id)
        .country(country)
        .startDate(startDate)
        .endDate(endDate)
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
      @RequestParam(value = "status") Status status,
      Principal principal,
      Model model) {
    String userLogin = principal.getName();
    User user = userDAO.findUserByLogin(userLogin);
    Collection<BusinessTrip> trips = null;
    if (user.getJobTitle().equals(JobTitle.PROJECT_MANAGER)) {
      trips = businessTripDAO
          .findTripByPMIdAndStatus(user.getUserId(), status.getId());
    }
    if (user.getJobTitle().equals(JobTitle.SOFTWARE_ENGINEER)) {
      trips = businessTripDAO
          .findTripByUserIdAndStatus(user.getUserId(), status.getId());
    }
    model.addAttribute("tripList", trips);
    return "businessTrip/showTripsList";
  }

  @RequestMapping(value = "showTrip/{businessTripId}", method = RequestMethod.GET)
  public String showTrip(@PathVariable(value = "businessTripId") BigInteger tripId,
      Model model){
    BusinessTrip trip = businessTripDAO
        .findBusinessTripById(tripId);
    model.addAttribute("businessTrip", trip);
    return "businessTrip/showTrip";
  }
}
