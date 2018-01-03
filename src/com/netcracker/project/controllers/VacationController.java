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
import org.springframework.security.access.annotation.Secured;
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

  private static final Logger logger = Logger
      .getLogger(VacationController.class);
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
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "vacation/createVacation";
    }
    User currentUser = userDAO.findUserByLogin(principal.getName());
    if (currentUser.getProjectStatus().name()
        .equals(ProjectStatus.TRANSIT.name())) {
      errorMap
          .put("USER_ON_TRANSIT_ERROR", ErrorMessages.USER_ON_TRANSIT_ERROR);
      model.addAttribute("errorMap", errorMap);
      return "vacation/createVacation";
    }
    Status pmStatus = null;
    Status lmStatus = null;
    BigInteger projectId = null;
    BigInteger pmApproveId = null;
    BigInteger lmApproveId = null;
    if (currentUser.getJobTitle().name()
        .equals(JobTitle.PROJECT_MANAGER.name())) {
      pmApproveId = currentUser.getUserId();
      pmStatus = Status.APPROVED;
      lmStatus = Status.WAITING_FOR_APPROVAL;
      projectId = projectDAO.findProjectIdByPMLogin(principal.getName());
    } else if (currentUser.getJobTitle().name()
        .equals(JobTitle.LINE_MANAGER.name())) {
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
    if (projectLMExistence > 0) {
      User lineManager = userDAO.findLMByProjectId(projectId);
      lmApproveId = lineManager.getUserId();
    } else {
      lmApproveId = pmApproveId;
      lmStatus = Status.WAITING_FOR_APPROVAL;
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

  @RequestMapping(value = "/findVacationByStatus", method = RequestMethod.GET)
  public String findVacationByStatus() {
    return "vacation/findVacationByStatus";
  }

  @RequestMapping(value = "/viewVacation", params = {"pmStatus",
      "lmStatus"}, method = RequestMethod.GET)
  public String viewUserVacation(
      @RequestParam("pmStatus") String pmStatus,
      @RequestParam("lmStatus") String lmStatus,
      Model model, Principal principal) {
    VacationValidator validator = new VacationValidator();
    Map<String, String> errorMap = validator
        .validateVacationStatus(pmStatus);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "vacation/findVacationByStatus";
    }
    errorMap = validator.validateVacationStatus(lmStatus);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "vacation/findVacationByStatus";
    }

    String userLogin = principal.getName();
    User currentUser = userDAO.findUserByLogin(userLogin);
    Collection<Vacation> vacations = vacationDAO
        .findVacationByUserIdAndPmAndLmStatus(currentUser.getUserId(),
            Status.valueOf(pmStatus).getId(), Status.valueOf(lmStatus).getId());

    model.addAttribute("vacationList", vacations);
    return "vacation/viewVacation";
  }

  @RequestMapping(value = "/showVacation/{vacationId}", method = RequestMethod.GET)
  public String showVacation(
      @PathVariable("vacationId") String vacationId,
      Model model, Principal principal) {
    VacationValidator validator = new VacationValidator();
    Map<String, String> errorMap = validator
        .validateVacationId(vacationId);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "vacation/viewVacation";
    }
    BigInteger validVacationId = new BigInteger(vacationId);

    Vacation vacation = vacationDAO.findVacationByVacationId(validVacationId);
    User currentUser = userDAO.findUserByLogin(principal.getName());

    if (!currentUser.getJobTitle().name()
        .equals(JobTitle.PROJECT_MANAGER.name())
        && !currentUser.getJobTitle().name()
        .equals(JobTitle.LINE_MANAGER.name())
        && !currentUser.getUserId().equals(vacation.getUserId())) {
      errorMap.put("WRONG_USER_ERROR", ErrorMessages.WRONG_USER_ERROR);
      model.addAttribute("errorMap", errorMap);
      return "vacation/viewVacation";
    }

    User author = userDAO.findUserByUserId(vacation.getUserId());
    User lineManager = userDAO.findUserByUserId(vacation.getLmId());
    User projectManager = userDAO.findUserByUserId(vacation.getPmId());
    Project project = projectDAO
        .findProjectByProjectId(vacation.getProjectId());

    model.addAttribute("author", author);
    model.addAttribute("lineManager", lineManager);
    model.addAttribute("projectManager", projectManager);
    model.addAttribute("project", project);
    model.addAttribute("currentUser", currentUser);
    model.addAttribute("vacation", vacation);

    return "vacation/showVacation";
  }

  @RequestMapping(value = "/updateAuthorVacation/{vacationId}", method = RequestMethod.GET)
  public String updateAuthorVacation(
      @PathVariable("vacationId") String vacationId,
      Model model) {
    VacationValidator validator = new VacationValidator();
    Map<String, String> errorMap = validator
        .validateVacationId(vacationId);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "vacation/showVacation";
    }
    BigInteger validVacationId = new BigInteger(vacationId);
    Vacation vacation = vacationDAO
        .findVacationByVacationId(validVacationId);
    if (vacation.getLmStatus().name().equals(Status.APPROVED.name())
        && vacation.getPmStatus().name().equals(Status.APPROVED.name())) {
      errorMap
          .put("VACATION_MODIFY_ERROR", ErrorMessages.VACATION_MODIFY_ERROR);
      model.addAttribute("errorMap", errorMap);
      return "vacation/showVacation";
    }
    model.addAttribute("vacation", vacation);

    return "vacation/updateAuthorVacation";
  }

  @RequestMapping(value = "/updateAuthorVacation/{vacationId}", method = RequestMethod.POST)
  public String updateAuthorVacationPost(
      @PathVariable("vacationId") String vacationId,
      @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate,
      Model model) {
    VacationValidator validator = new VacationValidator();
    Map<String, String> errorMap = validator
        .validateVacationId(vacationId);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "vacation/updateAuthorVacation";
    }
    errorMap = validator.validateDates(startDate, endDate);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "vacation/updateAuthorVacation";
    }
    BigInteger validVacationId = new BigInteger(vacationId);

    Integer vacationExistence = vacationDAO
        .findVacationIfExist(validVacationId);
    if (vacationExistence != 1) {
      errorMap.put("VACATION_EXIST_ERROR", ErrorMessages.VACATION_EXIST_ERROR);
      model.addAttribute("errorMap", errorMap);
      return "vacation/updateAuthorVacation";
    }
    vacationDAO.updateVacationStartAndEndDate(validVacationId,
        converter.convertStringToDateFromJSP(startDate),
        converter.convertStringToDateFromJSP(endDate));
    return "response_status/success";
  }

  @Secured({"ROLE_PM", "ROLE_LM"})
  @RequestMapping(value = "/updateStatus/{vacationId}", method = RequestMethod.POST)
  public String updatePMStatus(
      @PathVariable("vacationId") String vacationId,
      @RequestParam("status") String status,
      Principal principal,
      Model model) {
    VacationValidator validator = new VacationValidator();
    Map<String, String> errorMap = validator
        .validateVacationId(vacationId);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "vacation/showVacation";
    }
    BigInteger validVacationId = new BigInteger(vacationId);

    errorMap = validator.validateVacationStatus(status);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "vacation/showVacation";
    }
    Integer vacationExistence = vacationDAO
        .findVacationIfExist(validVacationId);
    if (vacationExistence != 1) {
      errorMap.put("VACATION_EXIST_ERROR", ErrorMessages.VACATION_EXIST_ERROR);
      model.addAttribute("errorMap", errorMap);
      return "vacation/showVacation";
    }

    User currentUser = userDAO.findUserByLogin(principal.getName());

    Vacation currentVacation = vacationDAO
        .findVacationByVacationId(validVacationId);

    Integer lmExistence = userDAO
        .findIfLMExists(currentVacation.getProjectId());

    if (lmExistence != 1) {
      if (currentUser.getJobTitle().name()
          .equals(JobTitle.PROJECT_MANAGER.name())) {
        vacationDAO
            .updatePmStatus(validVacationId, Status.valueOf(status).getId());
        vacationDAO
            .updateLmStatus(validVacationId, Status.valueOf(status).getId());
      }
    }

    if (lmExistence == 1) {
      if (currentUser.getJobTitle().name()
          .equals(JobTitle.PROJECT_MANAGER.name())) {
        vacationDAO
            .updatePmStatus(validVacationId, Status.valueOf(status).getId());
      }

      if (currentUser.getJobTitle().name()
          .equals(JobTitle.LINE_MANAGER.name())) {
        vacationDAO
            .updateLmStatus(validVacationId, Status.valueOf(status).getId());
      }
    }

    return "response_status/success";
  }

  @Secured({"ROLE_PM", "ROLE_LM"})
  @RequestMapping(value = "/findVacationByManagerStatus", method = RequestMethod.GET)
  public String findVacationByManagerStatus() {
    return "vacation/findVacationByManagerStatus";
  }

  @Secured({"ROLE_PM", "ROLE_LM"})
  @RequestMapping(value = "/findVacationByManagerStatus", params = "status", method = RequestMethod.GET)
  public String findVacationByManagerStatus(
      @RequestParam("status") String status,
      Principal principal,
      Model model) {
    VacationValidator validator = new VacationValidator();
    Map<String, String> errorMap = validator
        .validateVacationStatus(status);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "vacation/findVacationByManagerStatus";
    }

    User currentUser = userDAO.findUserByLogin(principal.getName());
    Collection<Vacation> vacations = null;

    if (currentUser.getJobTitle().name()
        .equals(JobTitle.PROJECT_MANAGER.name())) {
      vacations = vacationDAO
          .findVacationByPMIdAndPMStatus(currentUser.getUserId(),
              Status.valueOf(status).getId());
    }

    if (currentUser.getJobTitle().name().equals(JobTitle.LINE_MANAGER.name())) {
      vacations = vacationDAO
          .findVacationByLMIdAndLMStatus(currentUser.getUserId(),
              Status.valueOf(status).getId());
    }

    if (vacations != null) {
      model.addAttribute("vacationList", vacations);
      return "vacation/viewVacation";
    }

    return "vacation/viewVacation";
  }

}
