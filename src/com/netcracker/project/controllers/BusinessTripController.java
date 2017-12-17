package com.netcracker.project.controllers;

import com.netcracker.project.model.ProjectDAO;
import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.UserDAO.JobTitle;
import com.netcracker.project.model.entity.BusinessTrip;
import com.netcracker.project.model.entity.Project;
import com.netcracker.project.model.entity.Status;
import com.netcracker.project.model.entity.User;
import com.netcracker.project.model.impl.mappers.MapperDateConverter;
import com.netcracker.project.services.ListCountry;
import java.math.BigInteger;
import java.security.Principal;
import java.util.Collection;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
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
    ListCountry countries = new ListCountry();
    String authorLogin = principal.getName();
    User author = userDAO.findUserByLogin(authorLogin);
    BigInteger pmId = null;
    String lastName = null;
    String firstName = null;
    if (author.getJobTitle() == JobTitle.PROJECT_MANAGER) {
      pmId = author.getUserId();
      lastName = author.getLastName();
      firstName = author.getFirstName();
    }
    model.addAttribute("pmId", pmId);
    model.addAttribute("lastNamePm", lastName);
    model.addAttribute("firstNamePm", firstName);
    model.addAttribute("projectId", project.getProjectId());
    model.addAttribute("countriesList", countries.getCountriesNames());
    return "businessTrip/createBusinessTrip";
  }

  @RequestMapping(value = "createBusinessTripPost", method = RequestMethod.POST)
  public String createBusinessTripPost(Model model,
      @RequestParam(value = "projectId") BigInteger projectId,
      @RequestParam(value = "pmId") BigInteger pmId,
      @RequestParam(value = "country") String country,
      @RequestParam(value = "startDate") String startDate,
      @RequestParam(value = "endDate") String endDate,
      @RequestParam(value = "firstName") String firstName,
      @RequestParam(value = "firstNamePm") String firstNamePm,
      @RequestParam(value = "lastNamePm") String lastNamePm,
      @RequestParam(value = "lastName") String lastName,
      Principal principal) {
    BigInteger pmIds = null;
    BigInteger userId = null;
    Status status = null;

    MapperDateConverter converter = new MapperDateConverter();
    String authorLogin = principal.getName();
    User author = userDAO.findUserByLogin(authorLogin);
    Collection<User> users = userDAO
        .findUserByLastNameAndFirstName(lastName, firstName);
    if (users.size() > 1) {
      model.addAttribute("usersList", users);
      return "businessTrip/findUserId";
    } else {
      Iterator<User> iterator = users.iterator();
      while (iterator.hasNext()) {
        User user = iterator.next();
        userId = user.getUserId();
      }
    }

    if (pmId == null) {
      Collection<User> userByLastNameAndFirstName = userDAO
          .findUserByLastNameAndFirstName(lastNamePm, firstNamePm);
      Iterator<User> iterator = userByLastNameAndFirstName.iterator();
      if (userByLastNameAndFirstName.size() == 1) {
        while (iterator.hasNext()) {
          User user = iterator.next();
          pmIds = user.getUserId();
        }
      }
    } else {
      pmIds = pmId;
    }

    if (author.getUserId().equals(pmIds)) {
      status = Status.APPROVED;
    } else {
      status = Status.DISAPPROVED;
    }

    BusinessTrip trip = new BusinessTrip.BusinessTripBuilder()
        .authorId(author.getUserId())
        .projectId(projectId)
        .userId(userId)
        .pmId(pmIds)
        .country(country)
        .startDate(converter.convertStringToDateFromJSP(startDate))
        .endDate(converter.convertStringToDateFromJSP(endDate))
        .status(status)
        .build();
    return "response_status/success";
  }
}
