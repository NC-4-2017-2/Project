package com.netcracker.project.controllers;

import com.netcracker.project.model.BusinessTripDAO;
import com.netcracker.project.model.ProjectDAO;
import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.entity.BusinessTrip;
import com.netcracker.project.model.entity.Project;
import com.netcracker.project.model.entity.Status;
import com.netcracker.project.model.entity.User;
import com.netcracker.project.model.impl.mappers.MapperDateConverter;
import com.netcracker.project.services.ListCountry;
import java.math.BigInteger;
import java.security.Principal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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


  @RequestMapping(value = "/findBusinessTripProject", method = RequestMethod.GET)
  public String findBusinessTripProject(Model model) {
    Collection<String> projects = projectDAO.findAllOpenedProjects();
    model.addAttribute("projectNamesList", projects);
    return "businessTrip/findBusinessTripProject";
  }


  @RequestMapping(value = "createBusinessTrip", params = {
      "projectName"}, method = RequestMethod.GET)
  public String createBusinessTripGet(Model model,
      @RequestParam(value = "projectName") String projectName,
      Principal principal) {

    Project project = projectDAO.findProjectByName(projectName);
    String authorLogin = principal.getName();
    User author = userDAO.findUserByLogin(authorLogin);

    if (project.getProjectManagerId().equals(author.getUserId())) {
      model.addAttribute("projectId", project.getProjectId());
      model.addAttribute("pmId", author.getUserId());
      model.addAttribute("countriesList", countries.getCountriesNames());
      return "businessTrip/createTripWithoutPm";
    } else {
      model.addAttribute("projectId", project.getProjectId());
      model.addAttribute("authorId", author.getUserId());
      model.addAttribute("countriesList", countries.getCountriesNames());
      return "businessTrip/createTripWithPm";
    }
  }

  @RequestMapping(value = "createBusinessTripPostWithPm", method = RequestMethod.POST)
  public String createBusinessTripPostWithPm(
      @RequestParam(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @RequestParam(value = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
      @RequestParam(value = "lastName") String lastName,
      @RequestParam(value = "firstName") String firstName,
      @RequestParam(value = "authorId") BigInteger authorId,
      @RequestParam(value = "country") String country,
      @RequestParam(value = "projectId") BigInteger projectId
  ) {
    BigInteger userForTripId;

    Collection<User> userForTrip = userDAO
        .findUserByLastNameAndFirstName(lastName, firstName);
    String userValidation = getUserValidation(userForTrip);
    if (userValidation != null) {
      return userValidation;
    }
    userForTripId = getUserId(userForTrip);

    Project project = projectDAO.findProjectByProjectId(projectId);

    BusinessTrip trip = new BusinessTrip.BusinessTripBuilder()
        .authorId(authorId)
        .projectId(projectId)
        .userId(userForTripId)
        .pmId(project.getProjectManagerId())
        .country(country)
        .startDate(startDate)
        .endDate(endDate)
        .status(Status.DISAPPROVED)
        .build();

    businessTripDAO.createTrip(trip);
    return "response_status/success";
  }

  @RequestMapping(value = "createBusinessTripPostWithoutPm", method = RequestMethod.POST)
  public String createBusinessTripPostWithoutPm(
      @RequestParam(value = "projectId") BigInteger projectId,
      @RequestParam(value = "pmId") BigInteger pmId,
      @RequestParam(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @RequestParam(value = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
      @RequestParam(value = "lastName") String lastName,
      @RequestParam(value = "firstName") String firstName,
      @RequestParam(value = "country") String country
  ) {
    BigInteger userForTripId;
    Collection<User> userForTrip = userDAO
        .findUserByLastNameAndFirstName(lastName, firstName);
    String result = getUserValidation(userForTrip);
    if (result != null) {
      return result;
    }
    userForTripId = getUserId(userForTrip);

    BusinessTrip trip = new BusinessTrip.BusinessTripBuilder()
        .projectId(projectId)
        .userId(userForTripId)
        .authorId(pmId)
        .pmId(pmId)
        .country(country)
        .startDate(startDate)
        .endDate(endDate)
        .status(Status.APPROVED)
        .build();

    businessTripDAO.createTrip(trip);
    return "response_status/success";
  }

  private String getUserValidation(Collection<User> userForTrip) {
    if (userForTrip.size() > 1) {
      return "businessTrip/selectUserForTrip";
    } else if (userForTrip.isEmpty()) {
      return "response_status/unsuccess";
    }
    return null;
  }

  private BigInteger getUserId(Collection<User> user) {
    BigInteger userId = null;
    Iterator<User> iterator = user.iterator();
    while (iterator.hasNext()) {
      User userProfile = iterator.next();
      userId = userProfile.getUserId();
    }
    return userId;
  }
}
