package com.netcracker.project.controllers;

import com.netcracker.project.controllers.validators.VacationValidator;
import com.netcracker.project.controllers.validators.errorMessage.ErrorMessages;
import com.netcracker.project.model.ProjectDAO;
import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.VacationDAO;
import com.netcracker.project.model.entity.Project;
import com.netcracker.project.model.entity.User;
import com.netcracker.project.model.entity.Vacation;
import com.netcracker.project.model.enums.JobTitle;
import com.netcracker.project.model.enums.ProjectStatus;
import com.netcracker.project.model.enums.Status;
import com.netcracker.project.services.impl.DateConverterService;
import java.math.BigInteger;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
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

  @Autowired
  private VacationDAO vacationDAO;
  @Autowired
  private ProjectDAO projectDAO;
  @Autowired
  private UserDAO userDAO;

  private static final Logger logger = Logger.getLogger(VacationController.class);
  private DateConverterService converter = new DateConverterService();

  @RequestMapping(value = "/createVacation", method = RequestMethod.GET)
  public String createVacationGet(Model model) {
    return "vacation/createVacation";
  }

  @RequestMapping(value = "/createVacation", method = RequestMethod.POST)
  public String createVacationPost(
      @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate,
      Principal principal,
      Model model) {
    Map<String, String> errorMap = new HashMap<>();
    VacationValidator validator = new VacationValidator();
    errorMap = validator.validateDates(startDate, endDate);
    if(!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "vacation/createVacation";
    }
    User currentUser = userDAO.findUserByLogin(principal.getName());
    if(currentUser.getProjectStatus().name().equals(ProjectStatus.TRANSIT.name())) {
      errorMap.put("USER_ON_TRANSIT_ERROR", ErrorMessages.USER_ON_TRANSIT_ERROR);
      model.addAttribute("errorMap", errorMap);
      return "vacation/createVacation";
    }
    Status pmStatus = null;
    Status lmStatus = null;
    BigInteger projectId = null;
    BigInteger pmApproveId = null;
    BigInteger lmApproveId = null;
    if(currentUser.getJobTitle().name().equals(JobTitle.PROJECT_MANAGER.name())) {
      pmApproveId = currentUser.getUserId();
      pmStatus = Status.APPROVED;
      lmStatus = Status.WAITING_FOR_APPROVAL;
      projectId = projectDAO.findProjectIdByPMLogin(principal.getName());
    } else if(currentUser.getJobTitle().name().equals(JobTitle.LINE_MANAGER.name())) {
      projectId = projectDAO.findProjectIdByUserLogin(principal.getName());
      pmApproveId = userDAO.findPMByProjectId(projectId).getUserId();
      pmStatus = Status.WAITING_FOR_APPROVAL;
      lmApproveId = currentUser.getUserId();
      lmStatus = Status.APPROVED;
    } else {
      projectId = projectDAO.findProjectIdByUserLogin(principal.getName());
      pmApproveId = userDAO.findPMByProjectId(projectId).getUserId();
      pmStatus = Status.WAITING_FOR_APPROVAL;
      lmStatus = Status.WAITING_FOR_APPROVAL;
    }

    Integer projectLMExistence = userDAO.findIfLMExists(projectId);
    if(projectLMExistence > 0) {
      User lineManager = userDAO.findLMByProjectId(projectId);
      lmApproveId = lineManager.getUserId();
    } else {
      lmApproveId = pmApproveId;
      lmStatus = Status.APPROVED;
    }

    Vacation vacation = new Vacation.VacationBuilder()
        .userId(currentUser.getUserId())
        .projectId(projectId)
        .startDate(converter.convertStringToDateFromJSP(startDate))
        .endDate(converter.convertStringToDateFromJSP(endDate))
        .pmStatus(pmStatus)
        .lmStatus(lmStatus)
        .pmId(pmApproveId)
        .lmId(lmApproveId)
        .build();

    vacationDAO.createVacation(vacation);

    return "response_status/success";
  }

  @RequestMapping(value = "/edit={id}", method = RequestMethod.POST)
  public String editVacationPost(
      @RequestParam("vacationId") BigInteger vacationId,
      @RequestParam("userNames") List userId,
      @RequestParam("projectName") String projectName,
      @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate,
      @RequestParam("pmApproveStatus") Status pmApproveStatus,
      @RequestParam("lmApproveStatus") Status lmApproveStatus) {
    logger.info("createVacationPost() method. Params: userId:" + userId
        + "projectName: " + projectName
        + "startDate: " + startDate
        + "endDate: " + endDate
        + "pmApproveStatus: " + pmApproveStatus
        + "lmApproveStatus: " + lmApproveStatus);

    Vacation vacation = new Vacation.VacationBuilder()
        .vacationId(vacationId)
        .userId(new BigInteger(userId.get(0).toString()))
        .projectId(projectDAO.findProjectByName(projectName).getProjectId())
        .startDate(converter.convertStringToDateFromJSP(startDate))
        .endDate(converter.convertStringToDateFromJSP(endDate))
        .pmStatus(pmApproveStatus)
        .lmStatus(lmApproveStatus)
        .build();
    vacationDAO.updateVacation(vacation);
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

    Map<String, String> userNames = userDAO.getAllUserName();

    model.addAttribute("userNames", userNames);
    model.addAttribute("projectNamesList", sortedProject);
    model.addAttribute("vacationId", vacation.getVacationId());
    model.addAttribute("userId", vacation.getUserId().toString());
    model.addAttribute("startDate", vacation.getStartDate());
    model.addAttribute("endDate", vacation.getEndDate());
    model.addAttribute("pmApproveStatus", addAllStatus(vacation.getPmStatus()));
    model.addAttribute("lmApproveStatus", addAllStatus(vacation.getLmStatus()));
    return "vacation/edit_vacation_pm";
  }

  @RequestMapping(value = "/findVacationProject", params = "projectName", method = RequestMethod.GET)
  public String findVacationList(@RequestParam("projectName") String projectName, Model model) {
    Collection<Vacation> vacations = vacationDAO
        .findVacationByProjectId(projectDAO.findProjectByName(projectName).getProjectId());
    model.addAttribute("vacationList", vacations);
    return "vacation/find_vacation_list";
  }

  @RequestMapping(value = "/findVacationProject", method = RequestMethod.GET)
  public String findVacationProject(Model model) {
    Collection<String> projects = projectDAO.findAllOpenedProjects();
    model.addAttribute("projectNames", projects);
    return "vacation/find_vacation_project";
  }

  @RequestMapping(value = "/findVacationUser", params = "userId", method = RequestMethod.GET)
  public String findVacationUserList(@RequestParam("userId") BigInteger userId, Model model) {
    Collection<Vacation> vacations = vacationDAO.findVacationByUserId(userId);
    model.addAttribute("vacationList", vacations);
    return "vacation/find_vacation_list";
  }

  @RequestMapping(value = "/findVacationUser", method = RequestMethod.GET)
  public String findVacationUser(Model model) {
    Map<String, String> userNames = userDAO.getAllUserName();
    model.addAttribute("userNames", userNames);
    return "vacation/find_vacation_user";
  }

  @RequestMapping(value = "/findVacationUserPm", params = {"userId",
      "pmStatus"}, method = RequestMethod.GET)
  public String findVacationUserPmList(@RequestParam("userId") BigInteger userId,
      @RequestParam("pmStatus") Status status,
      Model model) {
    Collection<Vacation> vacations = vacationDAO
        .findVacationByUserIdAndPmStatus(userId, status.getId());
    model.addAttribute("vacationList", vacations);
    return "vacation/find_vacation_list";
  }

  @RequestMapping(value = "/findVacationUserPm", method = RequestMethod.GET)
  public String findVacationUserPm(Model model) {
    Map<String, String> userNames = userDAO.getAllUserName();
    model.addAttribute("userNames", userNames);
    return "vacation/find_vacation_user_pm";
  }

  @RequestMapping(value = "/findVacationUserLm", params = {"userId",
      "lmStatus"}, method = RequestMethod.GET)
  public String findVacationUserLmList(@RequestParam("userId") BigInteger userId,
      @RequestParam("lmStatus") Status status,
      Model model) {
    Collection<Vacation> vacations = vacationDAO
        .findVacationByUserIdAndLmStatus(userId, status.getId());
    model.addAttribute("vacationList", vacations);
    return "vacation/find_vacation_list";
  }

  @RequestMapping(value = "/findVacationUserLm", method = RequestMethod.GET)
  public String findVacationUserLm(Model model) {
    Map<String, String> userNames = userDAO.getAllUserName();
    model.addAttribute("userNames", userNames);
    return "vacation/find_vacation_user_pm";
  }

  @RequestMapping(value = "/viewVacation/{vacationId}", method = RequestMethod.GET)
  public String viewVacationByVacationId(@PathVariable("vacationId") BigInteger vacationId,
      Model model) {
    logger.info("viewVacationByVacationId() method. Params: projectId: " + vacationId);
    Vacation vacation = vacationDAO.findVacationById(vacationId);
    model.addAttribute("vacation", vacation);
    return "vacation/show_vacation";
  }

//  @RequestMapping(value = "/findVacationByUserIdAndPmStatus/{userId}/{pmStatus}", method = RequestMethod.GET)
//  public String findVacationByUserIdAndPmStatus(@PathVariable("userId") BigInteger userId,
//      @PathVariable("pmStatus") Status pmStatus, Model model) {
//    logger.info(
//        "findVacationByUserIdAndPmStatus() method. Params: " + userId + "pmStatus: " + pmStatus);
//
//    Collection<Vacation> vacations = vacationDAO
//        .findVacationByUserIdAndPmStatus(userId, pmStatus.getId());
//    model.addAttribute("vacationList", vacations);
//    return "vacation/show_vacation";
//  }
//
//  @RequestMapping(value = "/findVacationByUserIdAndLmStatus/{userId}/{lmStatus}", method = RequestMethod.GET)
//  public String findVacationByUserIdAndLmStatus(@PathVariable("userId") BigInteger userId,
//      @PathVariable("lmStatus") Status lmStatus, Model model) {
//    logger.info(
//        "findVacationByUserIdAndPmStatus() method. Params: " + userId + "pmStatus: " + lmStatus);
//
//    Collection<Vacation> vacations = vacationDAO
//        .findVacationByUserIdAndPmStatus(userId, lmStatus.getId());
//    model.addAttribute("vacationList", vacations);
//    return "vacation/show_vacation";
//  }

  private Set<Status> addAllStatus(Status status) {
    Set<Status> pmStatuses = new LinkedHashSet<>();

    Status[] statuses = Status.values();
    pmStatuses.add(status);
    Collections.addAll(pmStatuses, statuses);

    return pmStatuses;
  }
}
