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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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

  private static final Logger logger = Logger
      .getLogger(BusinessTripController.class);


  @RequestMapping(value = "/createBusinessTrip", method = RequestMethod.GET)
  public String createBusinessTripGet(Model model, Principal principal) {
    logger.info("Entering createBusinessTripGet()");
    String userLogin = principal.getName();
    User user = userDAO.findUserByLogin(userLogin);
    BigInteger projectId = null;

    if (user.getJobTitle().equals(JobTitle.PROJECT_MANAGER)) {
      projectId = projectDAO
          .findProjectIdByPMLogin(userLogin);
    } else {
      projectId = projectDAO
          .findProjectIdByUserLogin(userLogin);
    }

    Collection<User> users = userDAO.findUserByProjectId(projectId);
    Project project = projectDAO.findProjectByProjectId(projectId);
    User projectManager = userDAO
        .findUserByUserId(project.getProjectManagerId());
    users.add(projectManager);

    model.addAttribute("pmId", project.getProjectManagerId());
    model.addAttribute("projectId", project.getProjectId());
    model.addAttribute("countryList", countries.getCountriesNames());
    model.addAttribute("userList", users);
    model.addAttribute("authorId", user.getUserId());
    return "businessTrip/createBusinessTrip";
  }

  @RequestMapping(value = "/createBusinessTrip/projectId/{projectId}/pmId/{pmId}", method = RequestMethod.POST)
  public String createBusinessTripPost(
      @PathVariable(value = "projectId") String projectId,
      @PathVariable(value = "pmId") String pmId,
      @RequestParam(value = "startDate") String startDate,
      @RequestParam(value = "endDate") String endDate,
      @RequestParam(value = "user") String userId,
      @RequestParam(value = "authorId") String authorId,
      @RequestParam(value = "country") String country,
      Model model, Principal principal) {
    logger.info("Entering createBusinessTripPost()");
    Map<String, String> errorMap = new HashMap<>();
    BusinessTripValidator validator = new BusinessTripValidator();
    errorMap = validator.validateInputId(projectId);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "businessTrip/createBusinessTrip";
    }
    errorMap = validator.validateInputId(userId);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "businessTrip/createBusinessTrip";
    }
    errorMap = validator.validateInputId(authorId);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "businessTrip/createBusinessTrip";
    }
    errorMap = validator.validateInputId(pmId);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "businessTrip/createBusinessTrip";
    }
    BigInteger projectBigIntegerId = new BigInteger(projectId);
    BigInteger pmBigIntegerId = new BigInteger(pmId);
    BigInteger userBigIntegerId = new BigInteger(userId);
    BigInteger authorBigIntegerId = new BigInteger(authorId);

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

    String currentUserLogin = principal.getName();
    User currentUser = userDAO.findUserByLogin(currentUserLogin);
    Status status;
    if (currentUser.getJobTitle().name()
        .equals(JobTitle.PROJECT_MANAGER.name())) {
      status = Status.APPROVED;
    } else {
      status = Status.WAITING_FOR_APPROVAL;
    }

    BusinessTrip trip = new BusinessTrip.BusinessTripBuilder()
        .authorId(authorBigIntegerId)
        .projectId(projectBigIntegerId)
        .userId(userBigIntegerId)
        .pmId(pmBigIntegerId)
        .country(country)
        .startDate(converter.convertStringToDateFromJSP(startDate))
        .endDate(converter.convertStringToDateFromJSP(endDate))
        .status(status)
        .build();

    businessTripDAO.createTrip(trip);
    return "responseStatus/success";
  }

  @RequestMapping(value = "/updateBusinessTrip/{id}", method = RequestMethod.GET)
  public String updateBusinessTrip(Model model,
      @PathVariable(value = "id") String id) {
    logger.info("Entering updateBusinessTrip()");
    Map<String, String> errorMap = new HashMap<>();
    BusinessTripValidator validator = new BusinessTripValidator();
    errorMap = validator.validateInputId(id);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "businessTrip/updateBusinessTrip";
    }
    BigInteger bigIntegerId = new BigInteger(id);
    Integer businessTripExistence = businessTripDAO
        .findIfBusinessTripExists(bigIntegerId);
    Map<String, String> existenceError = validator
        .validateExistence(businessTripExistence);
    if (!existenceError.isEmpty()) {
      model.addAttribute("errorMap", existenceError);
      return "businessTrip/updateBusinessTrip";
    }
    BusinessTrip trip = businessTripDAO.findBusinessTripById(bigIntegerId);
    model.addAttribute("countryList", countries.getCountriesNames());
    model.addAttribute("businessTripId", trip.getBusinessTripId());
    model.addAttribute("startDate", trip.getStartDate());
    model.addAttribute("endDate", trip.getEndDate());
    model.addAttribute("status", trip.getStatus());
    model.addAttribute("tripCountry", trip.getCountry());
    return "businessTrip/updateBusinessTrip";
  }

  @RequestMapping(value = "/updateBusinessTrip/{id}", method = RequestMethod.POST)
  public String updateBusinessTrip(Model model,
      @PathVariable(value = "id") String id,
      @RequestParam(value = "country") String country,
      @RequestParam(value = "startDate") String startDate,
      @RequestParam(value = "endDate") String endDate) {
    logger.info("Entering updateBusinessTrip()");
    Map<String, String> errorMap = new HashMap<>();
    BusinessTripValidator validator = new BusinessTripValidator();
    errorMap = validator.validateInputId(id);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "businessTrip/updateBusinessTrip";
    }
    BigInteger bigIntegerId = new BigInteger(id);
    Integer businessTripExistence = businessTripDAO
        .findIfBusinessTripExists(bigIntegerId);
    Map<String, String> existenceError = validator
        .validateExistence(businessTripExistence);
    if (!existenceError.isEmpty()) {
      model.addAttribute("errorMap", existenceError);
      return "businessTrip/updateBusinessTrip";
    }
    errorMap = new BusinessTripValidator()
        .validateUpdate(country, startDate, endDate);

    if (!errorMap.isEmpty()) {
      model.addAttribute("countryList", countries.getCountriesNames());
      model.addAttribute("businessTripId", bigIntegerId);
      model.addAttribute("startDate", startDate);
      model.addAttribute("endDate", endDate);
      model.addAttribute("tripCountry", country);
      model.addAttribute("errorMap", errorMap);
      return "businessTrip/updateBusinessTrip";
    }

    BusinessTrip trip = new BusinessTrip.BusinessTripBuilder()
        .businessTripId(bigIntegerId)
        .country(country)
        .startDate(converter.convertStringToDateFromJSP(startDate))
        .endDate(converter.convertStringToDateFromJSP(endDate))
        .build();

    businessTripDAO.updateTrip(trip);
    return "responseStatus/success";
  }

  @RequestMapping(value = "/findTrip", method = RequestMethod.GET)
  public String findTripByStatus() {
    logger.info("Entering findTripByStatus()");
    return "businessTrip/findTrip";
  }

  @RequestMapping(value = "viewTrip", params = "status",
      method = RequestMethod.GET)
  public String showTripList(
      @RequestParam(value = "status") String status,
      Principal principal,
      Model model) {
    logger.info("Entering showTripList()");
    Map<String, String> errorMap = new BusinessTripValidator()
        .validateBusinessTripStatus(status);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "businessTrip/findTrip";
    }

    String userLogin = principal.getName();
    User user = userDAO.findUserByLogin(userLogin);
    Collection<BusinessTrip> trips = businessTripDAO
        .findTripByUserIdAndStatus(user.getUserId(),
            Status.valueOf(status).getId());

    model.addAttribute("tripList", trips);
    return "businessTrip/viewTrip";
  }

  @RequestMapping(value = "/showTrip/{businessTripId}", method = RequestMethod.GET)
  public String showTrip(
      @PathVariable(value = "businessTripId") String tripId,
      Model model, Principal principal) {
    logger.info("Entering showTrip()");
    Map<String, String> errorMap = new HashMap<>();
    BusinessTripValidator validator = new BusinessTripValidator();
    errorMap = validator.validateInputId(tripId);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "businessTrip/showTrip";
    }
    BigInteger bigIntegerId = new BigInteger(tripId);
    Integer businessTripExistence = businessTripDAO
        .findIfBusinessTripExists(bigIntegerId);
    Map<String, String> existenceError = validator
        .validateExistence(businessTripExistence);
    if (!existenceError.isEmpty()) {
      model.addAttribute("errorMap", existenceError);
      return "businessTrip/showTrip";
    }
    BusinessTrip trip = businessTripDAO
        .findBusinessTripById(bigIntegerId);
    User businessTripUser = userDAO.findUserByUserId(trip.getUserId());
    User businessTripAuthor = userDAO.findUserByUserId(trip.getAuthorId());
    User businessTripPM = userDAO.findUserByUserId(trip.getPmId());
    String currentUserLogin = principal.getName();
    User currentUser = userDAO.findUserByLogin(currentUserLogin);
    Project businessTripProject = projectDAO
        .findProjectByProjectId(trip.getProjectId());

    model.addAttribute("businessTrip", trip);
    model.addAttribute("businessTripUser",
        businessTripUser.getFirstName() + " " + businessTripUser.getLastName());
    model.addAttribute("businessTripAuthor",
        businessTripAuthor.getFirstName() + " " + businessTripAuthor
            .getLastName());
    model.addAttribute("businessTripPM",
        businessTripPM.getFirstName() + " " + businessTripPM.getLastName());
    model.addAttribute("businessTripProject", businessTripProject.getName());
    model.addAttribute("currentUser", currentUser);
    return "businessTrip/showTrip";
  }

  @RequestMapping(value = "/findTripByStatusPerPeriod", method = RequestMethod.GET)
  public String findTripStatusAndPerPeriod() {
    logger.info("Entering findTripStatusAndPerPeriod()");
    return "businessTrip/findTripByStatusPerPeriod";
  }

  @RequestMapping(value = "/showTrip", params = {"status", "startDate",
      "endDate"}, method = RequestMethod.GET)
  public String findTripStatusAndPerPeriodGET(
      @RequestParam(value = "status") String status,
      @RequestParam(value = "startDate") String startDate,
      @RequestParam(value = "endDate") String endDate,
      Principal principal,
      Model model) {
    logger.info("Entering findTripStatusAndPerPeriodGET()");
    Map<String, String> errorMap = new HashMap<>();
    BusinessTripValidator validator = new BusinessTripValidator();
    errorMap = validator.validateBusinessTripStatus(status);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "businessTrip/findTripByStatusPerPeriod";
    }
    errorMap = validator.validateBetweenDates(startDate, endDate);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "businessTrip/findTripByStatusPerPeriod";
    }
    User currentUser = userDAO.findUserByLogin(principal.getName());
    Collection<BusinessTrip> businessTrips = businessTripDAO
        .findTripByUserIdAndStatusAndPerPeriod(currentUser.getUserId(),
            converter.convertStringToDateFromJSP(startDate),
            converter.convertStringToDateFromJSP(endDate),
            Status.valueOf(status).getId());
    return "businessTrip/showTrip";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/updateTripStatus/{id}", method = RequestMethod.POST)
  public String updateTripStatus(
      @PathVariable(value = "id") String id,
      @RequestParam(value = "status") String status,
      Model model, Principal principal) {
    logger.info("Entering updateTripStatus()");
    BusinessTripValidator validator = new BusinessTripValidator();

    Map<String, String> inputIdMap = validator.validateInputId(id);
    if (!inputIdMap.isEmpty()) {
      model.addAttribute("errorMap", inputIdMap);
    }

    BigInteger tripId = new BigInteger(id);
    Integer businessTripExistence = businessTripDAO
        .findIfBusinessTripExists(tripId);
    Map<String, String> existenceError = validator
        .validateExistence(businessTripExistence);
    if (!existenceError.isEmpty()) {
      model.addAttribute("errorMap", existenceError);
      return "businessTrip/showTrip";
    }
    BusinessTrip businessTrip = businessTripDAO
        .findBusinessTripById(tripId);
    User currentUser = userDAO.findUserByLogin(principal.getName());
    Map<String, String> errorMap = validator
        .validateBusinessTripStatus(status);
    if (!errorMap.isEmpty()) {
      model.addAttribute("businessTrip", businessTrip);
      model.addAttribute("currentUser", currentUser);
      model.addAttribute("errorMap", errorMap);
      return "businessTrip/showTrip";
    }
    businessTripDAO
        .updateBusinessTripStatus(tripId, Status.valueOf(status).getId());
    BusinessTrip updatedBusinessTrip = businessTripDAO
        .findBusinessTripById(tripId);
    User businessTripUser = userDAO
        .findUserByUserId(updatedBusinessTrip.getUserId());
    User businessTripAuthor = userDAO
        .findUserByUserId(updatedBusinessTrip.getAuthorId());
    User businessTripPM = userDAO
        .findUserByUserId(updatedBusinessTrip.getPmId());
    Project businessTripProject = projectDAO
        .findProjectByProjectId(updatedBusinessTrip.getProjectId());

    model.addAttribute("businessTripUser",
        businessTripUser.getFirstName() + " " + businessTripUser.getLastName());
    model.addAttribute("businessTripAuthor",
        businessTripAuthor.getFirstName() + " " + businessTripAuthor
            .getLastName());
    model.addAttribute("businessTripPM",
        businessTripPM.getFirstName() + " " + businessTripPM.getLastName());
    model.addAttribute("businessTripProject", businessTripProject.getName());
    model.addAttribute("businessTrip", updatedBusinessTrip);
    model.addAttribute("currentUser", currentUser);
    return "businessTrip/showTrip";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/findPMTrip", method = RequestMethod.GET)
  public String findPMTrip() {
    logger.info("Entering findPMTrip()");
    return "businessTrip/findPMTrip";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/viewPMTrip", params = "status", method = RequestMethod.GET)
  public String viewPMWorkingDay(@RequestParam(value = "status") String status,
      Principal principal,
      Model model) {
    logger.info("Entering viewPMWorkingDay()");
    User currentUser = userDAO.findUserByLogin(principal.getName());
    Collection<BusinessTrip> businessTrip = businessTripDAO
        .findTripByPMIdAndStatus(currentUser.getUserId(),
            Status.valueOf(status).getId());
    model.addAttribute("tripList", businessTrip);
    return "businessTrip/viewTrip";
  }
}
