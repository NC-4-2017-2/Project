package com.netcracker.project.controllers;

import com.netcracker.project.model.BusinessTripDAO;
import com.netcracker.project.model.ProjectDAO;
import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.entity.BusinessTrip;
import com.netcracker.project.model.entity.Project;
import com.netcracker.project.model.entity.Status;
import com.netcracker.project.model.entity.User;
import com.netcracker.project.services.ListCountry;
import java.math.BigInteger;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
    Collection<BigInteger> usersId = project.getUsersId();
    Collection<User> users = new ArrayList<>();
    for (BigInteger userId : usersId) {
      users.add(userDAO.findUserByUserId(userId));
    }

    if (project.getProjectManagerId().equals(author.getUserId())) {
      model.addAttribute("projectId", project.getProjectId());
      model.addAttribute("pmId", author.getUserId());
      model.addAttribute("countryList", countries.getCountriesNames());
      model.addAttribute("usersList", users);
      return "businessTrip/createTripWithoutPm";
    } else {
      model.addAttribute("projectId", project.getProjectId());
      model.addAttribute("authorId", author.getUserId());
      model.addAttribute("countryList", countries.getCountriesNames());
      model.addAttribute("usersList", users);
      return "businessTrip/createTripWithPm";
    }
  }

  @RequestMapping(value = "createBusinessTripPostWithPm", method = RequestMethod.POST)
  public String createBusinessTripPostWithPm(
      @RequestParam(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @RequestParam(value = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
      @RequestParam(value = "user") BigInteger userId,
      @RequestParam(value = "authorId") BigInteger authorId,
      @RequestParam(value = "country") String country,
      @RequestParam(value = "projectId") BigInteger projectId
  ) {
    Project project = projectDAO.findProjectByProjectId(projectId);

    BusinessTrip trip = new BusinessTrip.BusinessTripBuilder()
        .authorId(authorId)
        .projectId(projectId)
        .userId(userId)
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
      @RequestParam(value = "user") BigInteger userId,
      @RequestParam(value = "country") String country
  ) {
    BusinessTrip trip = new BusinessTrip.BusinessTripBuilder()
        .projectId(projectId)
        .userId(userId)
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

  @RequestMapping(value = "businessTripToUpdate/{id}", method = RequestMethod.GET)
  public String updateBusinessTrip(Model model,
      @PathVariable(value = "id") BigInteger id) {
    BusinessTrip foundBusinessTrip = businessTripDAO.findBusinessTripById(id);
    model.addAttribute("countryList", countries.getCountriesNames());
    model.addAttribute("trip", foundBusinessTrip);
    return "businessTrip/updateBusinessTrip";
  }

  @RequestMapping(value = "businessTripToUpdate/{id}", method = RequestMethod.POST)
  public String updateBusinessTrip(
      @PathVariable(value = "id") BigInteger id,
      @RequestParam(value = "country") String country,
      @RequestParam(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @RequestParam(value = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
      @RequestParam(value = "status") String status) {
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
}
