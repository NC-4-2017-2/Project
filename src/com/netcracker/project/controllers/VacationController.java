package com.netcracker.project.controllers;

import com.netcracker.project.model.ProjectDAO;
import com.netcracker.project.model.VacationDAO;
import com.netcracker.project.model.entity.Project;
import com.netcracker.project.model.entity.Vacation;
import com.netcracker.project.model.enums.Status;
import com.netcracker.project.services.impl.DateConverterService;
import java.math.BigInteger;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/vacation")
public class VacationController {

  private static final Logger logger = Logger.getLogger(VacationController.class);
  @Autowired
  private VacationDAO vacationDAO;
  @Autowired
  private ProjectDAO projectDAO;
  private DateConverterService converter = new DateConverterService();

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public String createVacationPost(
      @RequestParam("userId") BigInteger userId,
      @RequestParam("projectName") String projectName,
      @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate,
      @RequestParam("pmApproveStatus") Status pmApproveStatus,
      @RequestParam("lmApproveStatus") Status lmApproveStatus,
      @RequestParam("pmId") BigInteger pmId,
      @RequestParam("lmId") BigInteger lmId) {

    logger.info("createVacationPost() method. Params: userId:" + userId
        + "projectName: " + projectName
        + "startDate: " + startDate
        + "endDate: " + endDate
        + "pmApproveStatus: " + pmApproveStatus
        + "lmApproveStatus: " + lmApproveStatus
        + "lmId: " + lmId
        + "pmId: " + pmId);

    Vacation vacation = new Vacation.VacationBuilder()
        .userId(userId)
        .projectId(projectDAO.findProjectByName(projectName).getProjectId())
        .startDate(converter.convertStringToDateFromJSP(startDate))
        .endDate(converter.convertStringToDateFromJSP(endDate))
        .pmStatus(pmApproveStatus)
        .lmStatus(lmApproveStatus)
        .pmId(pmId)
        .lmId(lmId)
        .build();

    //vacationDAO.createVacation(vacation);

    return "response_status/success";
  }

  @RequestMapping(value = "/create", method = RequestMethod.GET)
  public String createVacationGet(Model model) {
    logger.info("createVacationGet() method");

    Collection<String> projects = projectDAO.findAllOpenedProjects();
    model.addAttribute("projectNamesList", projects);

    return "vacation/create";
  }

  @RequestMapping(value = "/edit={id}", method = RequestMethod.POST)
  public String editVacationPost(
      @RequestParam("vacationId") BigInteger vacationId,
      @RequestParam("userId") BigInteger userId,
      @RequestParam("projectName") String projectName,
      @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate,
      @RequestParam("pmApproveStatus") Status pmApproveStatus,
      @RequestParam("lmApproveStatus") Status lmApproveStatus) {
    Project project = projectDAO.findProjectByName(projectName);
//todo finished update
    return "response_status/success";
  }

  @RequestMapping(value = "/edit={id}", method = RequestMethod.GET)
  public String editVacationFromPMGet(@PathVariable("id") BigInteger id, Model model) {
    logger.info("editVacationFromPMGet() method. Param: " + id);

    Vacation vacation = vacationDAO.findVacationById(id);
    Collection<String> projects = projectDAO.findAllOpenedProjects();
    Set<String> sortedProject = new LinkedHashSet<>();
    sortedProject.add(projectDAO.findProjectByProjectId(vacation.getProjectId()).getName());
    sortedProject.addAll(projects);

    model.addAttribute("projectNamesList", sortedProject);
    model.addAttribute("vacationId", vacation.getVacationId());
    model.addAttribute("userId", vacation.getUserId());
    model.addAttribute("startDate", vacation.getStartDate());
    model.addAttribute("endDate", vacation.getEndDate());
    model.addAttribute("pmApproveStatus", vacation.getPmStatus());
    model.addAttribute("lmApproveStatus", vacation.getLmStatus());
    return "vacation/edit_vacation_pm";
  }


  @RequestMapping(value = "/viewVacationByUserId/{userId}", method = RequestMethod.GET)
  public String findVacationByUserId(@PathVariable("userId") BigInteger userId, Model model) {
    logger.info("findVacationByUserId() method. Params: userId: " + userId);

    Collection<Vacation> vacations = vacationDAO.findVacationByUserId(userId);
    model.addAttribute("vacationList", vacations);
    return "vacation/show_vacation";
  }

  @RequestMapping(value = "/viewVacationByProjectId/{projectId}", method = RequestMethod.GET)
  public String findVacationByProjectId(@PathVariable("projectId") BigInteger projectId,
      Model model) {
    logger.info("findVacationByProjectId() method. Params: projectId: " + projectId);

    Collection<Vacation> vacations = vacationDAO.findVacationByProjectId(projectId);
    model.addAttribute("vacationList", vacations);
    return "vacation/show_vacation";
  }

  @RequestMapping(value = "/findVacationByUserIdAndPmStatus/{userId}/{pmStatus}", method = RequestMethod.GET)
  public String findVacationByUserIdAndPmStatus(@PathVariable("userId") BigInteger userId,
      @PathVariable("pmStatus") Status pmStatus, Model model) {
    logger.info(
        "findVacationByUserIdAndPmStatus() method. Params: " + userId + "pmStatus: " + pmStatus);

    Collection<Vacation> vacations = vacationDAO
        .findVacationByUserIdAndPmStatus(userId, pmStatus.getId());
    model.addAttribute("vacationList", vacations);
    return "vacation/show_vacation";
  }

  @RequestMapping(value = "/findVacationByUserIdAndLmStatus/{userId}/{lmStatus}", method = RequestMethod.GET)
  public String findVacationByUserIdAndLmStatus(@PathVariable("userId") BigInteger userId,
      @PathVariable("lmStatus") Status lmStatus, Model model) {
    logger.info(
        "findVacationByUserIdAndPmStatus() method. Params: " + userId + "pmStatus: " + lmStatus);

    Collection<Vacation> vacations = vacationDAO
        .findVacationByUserIdAndPmStatus(userId, lmStatus.getId());
    model.addAttribute("vacationList", vacations);
    return "vacation/show_vacation";
  }

/*  @RequestMapping(value = "/approve={vacationID}", method = RequestMethod.GET)
  public String approveVacation(@PathVariable("vacationID") BigInteger vacationID) {

    return null;
  }*/


}
