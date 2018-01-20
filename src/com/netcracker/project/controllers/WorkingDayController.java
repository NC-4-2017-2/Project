package com.netcracker.project.controllers;

import com.netcracker.project.controllers.validators.WorkingDayValidator;
import com.netcracker.project.controllers.validators.errorMessage.ErrorMessages;
import com.netcracker.project.model.ProjectDAO;
import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.WorkingDayDAO;
import com.netcracker.project.model.entity.Project;
import com.netcracker.project.model.entity.User;
import com.netcracker.project.model.entity.WorkingDay;
import com.netcracker.project.model.entity.WorkingDayUserService;
import com.netcracker.project.model.enums.JobTitle;
import com.netcracker.project.model.enums.ProjectStatus;
import com.netcracker.project.model.enums.Status;
import com.netcracker.project.services.impl.DateConverterService;
import com.netcracker.project.services.impl.WorkingDayService;
import java.math.BigInteger;
import java.security.Principal;
import java.util.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/workingDay")
public class WorkingDayController {

  @Autowired
  private WorkingDayDAO workingDayDAO;
  @Autowired
  private ProjectDAO projectDAO;
  @Autowired
  private UserDAO userDAO;
  @Autowired
  private DateConverterService converter;

  private static final Logger logger = Logger
      .getLogger(WorkingDayController.class);

  @RequestMapping(value = "/findWorkingDayPerPeriod", method = RequestMethod.GET)
  public String findWorkingDay() {
    logger.info("Entering findWorkingDay()");
    return "workingDay/findWorkingDayPerPeriod";
  }

  @RequestMapping(value = "/viewWorkingDay", params = {"startDate",
      "endDate"}, method = RequestMethod.GET)
  public String viewWorkingDay(
      @RequestParam(value = "startDate") String startDate,
      @RequestParam(value = "endDate") String endDate,
      Principal principal,
      Model model) {
    logger.info("Entering viewWorkingDay()");
    WorkingDayValidator validator = new WorkingDayValidator();
    Map<String, String> errorMap = validator.validateFind(startDate, endDate);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "workingDay/findWorkingDayPerPeriod";
    }
    User user = userDAO.findUserByLogin(principal.getName());
    Collection<WorkingDay> workingDays = workingDayDAO
        .findWorkingDayPerPeriod(user.getUserId(),
            converter.convertStringToDateFromJSP(startDate),
            converter.convertStringToDateFromJSP(endDate));
    if (workingDays.isEmpty()) {
      return "responseStatus/noDataFound";
    }
    Collection<WorkingDayUserService> userService = new ArrayList<>();
    for (WorkingDay workingDay : workingDays) {
      User workingDayUser = userDAO.findUserByUserId(workingDay.getUserId());
      userService.add(
          new WorkingDayUserService(workingDayUser.getLastName(),
              workingDayUser.getFirstName(),
              workingDay));
    }
    model.addAttribute("workingDays", userService);
    return "workingDay/viewWorkingDay";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/findPMWorkingDay", method = RequestMethod.GET)
  public String findPMWorkingDay() {
    logger.info("Entering findPMWorkingDay()");
    return "workingDay/findPMWorkingDay";
  }

  @Secured({"ROLE_PM", "ROLE_ADMIN"})
  @RequestMapping(value = "/viewPMWorkingDay", params = "status", method = RequestMethod.GET)
  public String viewPMWorkingDay(@RequestParam(value = "status") String status,
      Principal principal,
      Model model) {
    logger.info("Entering viewPMWorkingDay()");
    Map<String, String> errorMap = new HashMap<>();
    errorMap = new WorkingDayValidator().validateWorkingDayStatus(status);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "workingDay/findPMWorkingDay";
    }
    User currentUser = userDAO.findUserByLogin(principal.getName());
    Collection<WorkingDay> workingDays = workingDayDAO
        .findWorkingDayByPMIdAndStatus(currentUser.getUserId(),
            Status.valueOf(status).getId());
    Collection<WorkingDayUserService> userService = new ArrayList<>();
    for (WorkingDay workingDay : workingDays) {
      User user = userDAO.findUserByUserId(workingDay.getUserId());
      userService.add(
          new WorkingDayUserService(user.getLastName(), user.getFirstName(),
              workingDay));
    }
    if (workingDays.isEmpty()) {
      return "responseStatus/noDataFound";
    }
    model.addAttribute("workingDays", userService);
    return "workingDay/viewWorkingDay";
  }

  @Secured({"ROLE_PM", "ROLE_ADMIN"})
  @RequestMapping(value = "/showUpdatePMWorkingDayStatus/{id}", method = RequestMethod.POST)
  public String showUpdatePMWorkingDayStatus(
      @PathVariable(value = "id") String id,
      @RequestParam(value = "status") String status,
      Model model,
      Principal principal) {
    logger.info("Entering showUpdatePMWorkingDayStatus()");
    WorkingDayValidator validator = new WorkingDayValidator();
    Map<String, String> errorMap = validator.validateInputId(id);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "workingDay/showWorkingDay";
    }
    BigInteger bigIntegerId = new BigInteger(id);
    Integer workingDayExistence = workingDayDAO
        .findIfWorkingDayExists(bigIntegerId);
    Map<String, String> existenceError = new WorkingDayValidator()
        .validateExistence(workingDayExistence);
    if (!existenceError.isEmpty()) {
      model.addAttribute("errorMap", existenceError);
      return "workingDay/showWorkingDay";
    }
    WorkingDay workingDay = workingDayDAO.findWorkingDayById(bigIntegerId);
    User currentUser = userDAO.findUserByLogin(principal.getName());
    errorMap = validator
        .validateWorkingDayStatus(status);
    if (!errorMap.isEmpty()) {
      model.addAttribute("workingDay", workingDay);
      model.addAttribute("currentUser", currentUser);
      model.addAttribute("errorMap", errorMap);
      return "workingDay/showWorkingDay";
    }
    User workingDayUser = userDAO.findUserByUserId(workingDay.getUserId());
    User workingDayPm = userDAO.findUserByUserId(workingDay.getPmId());
    workingDayDAO
        .updateWorkingDayStatus(bigIntegerId, Status.valueOf(status).getId());
    WorkingDay updatedWorkingDay = workingDayDAO
        .findWorkingDayById(bigIntegerId);

    model.addAttribute("workingDayUser",
        workingDayUser.getFirstName() + " " + workingDayUser.getLastName());
    model.addAttribute("workingDayPm",
        workingDayPm.getFirstName() + " " + workingDayPm.getLastName());
    model.addAttribute("workingDay", updatedWorkingDay);
    model.addAttribute("currentUser", currentUser);
    return "workingDay/showWorkingDay";
  }

  @RequestMapping(value = "/showWorkingDay/{id}", method = RequestMethod.GET)
  public String showWorkingDay(@PathVariable(value = "id") String id,
      Principal principal,
      Model model) {
    logger.info("Entering showWorkingDay()");
    WorkingDayValidator validator = new WorkingDayValidator();
    Map<String, String> errorMap = validator.validateInputId(id);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "workingDay/showWorkingDay";
    }
    BigInteger bigIntegerId = new BigInteger(id);
    User currentUser = userDAO.findUserByLogin(principal.getName());
    WorkingDay workingDay = workingDayDAO.findWorkingDayById(bigIntegerId);
    if (!currentUser.getUserId().equals(workingDay.getUserId()) && !currentUser
        .getUserId().equals(workingDay.getPmId()) && !currentUser
        .getJobTitle().name().equals(JobTitle.PROJECT_MANAGER.name())) {
      errorMap.put("INVALID_USER_ERROR", ErrorMessages.INVALID_USER_ERROR);
      model.addAttribute("errorMap", errorMap);
      return "workingDay/showWorkingDay";
    }
    User workingDayUser = userDAO.findUserByUserId(workingDay.getUserId());
    User workingDayPm = userDAO.findUserByUserId(workingDay.getPmId());

    model.addAttribute("currentUser", currentUser);
    model.addAttribute("workingDayUser",
        workingDayUser.getFirstName() + " " + workingDayUser.getLastName());
    model.addAttribute("workingDayPm",
        workingDayPm.getFirstName() + " " + workingDayPm.getLastName());
    model.addAttribute("workingDay", workingDay);
    return "workingDay/showWorkingDay";
  }

  @RequestMapping(value = "/findUserWorkingDayByStatus", method = RequestMethod.GET)
  public String findWorkingDayByUserIdAndStatus() {
    logger.info("Entering findWorkingDayByUserIdAndStatus()");
    return "workingDay/findWorkingDayByStatus";
  }

  @RequestMapping(value = "/viewWorkingDay", params = "status", method = RequestMethod.GET)
  public String viewWorkingDayByStatus(
      @RequestParam(value = "status") String status,
      Principal principal,
      Model model) {
    logger.info("Entering viewWorkingDayByStatus()");
    Map<String, String> errorMap = new HashMap<>();
    errorMap = new WorkingDayValidator().validateWorkingDayStatus(status);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "workingDay/findWorkingDayByStatus";
    }
    User currentUser = userDAO.findUserByLogin(principal.getName());
    Collection<WorkingDay> workingDays = workingDayDAO
        .findWorkingDayByUserIdAndStatus(currentUser.getUserId(),
            Status.valueOf(status).getId());
    if (workingDays.isEmpty()) {
      return "responseStatus/noDataFound";
    }
    Collection<WorkingDayUserService> userService = new ArrayList<>();
    for (WorkingDay workingDay : workingDays) {
      User workingDayUser = userDAO.findUserByUserId(workingDay.getUserId());
      userService.add(
          new WorkingDayUserService(workingDayUser.getLastName(),
              workingDayUser.getFirstName(),
              workingDay));
    }
    model.addAttribute("workingDays", userService);
    return "workingDay/viewWorkingDay";
  }

  @RequestMapping(value = "/createWorkingDay", method = RequestMethod.GET)
  public String createWorkingDays(Principal principal, Model model) {
    logger.info("Entering createWorkingDays()");
    return "workingDay/createWorkingDay";
  }

  @RequestMapping(value = "/createWorkingDay", method = RequestMethod.POST)
  public String createWorkingDayPost(
      @RequestParam(value = "monday") String monday,
      @RequestParam(value = "tuesday") String tuesday,
      @RequestParam(value = "wednesday") String wednesday,
      @RequestParam(value = "thursday") String thursday,
      @RequestParam(value = "friday") String friday,
      @RequestParam(value = "saturday") String saturday,
      @RequestParam(value = "sunday") String sunday,
      Principal principal, HttpServletRequest request,
      RedirectAttributes attributes,
          Model model) {
    logger.info("Entering createWorkingDayPost()");
    String userLogin = principal.getName();
    User user = userDAO.findUserByLogin(userLogin);
    BigInteger projectManagerId;
    if (user.getProjectStatus().name().equals(ProjectStatus.TRANSIT.name())) {
      projectManagerId = BigInteger.valueOf(18);
    } else {

      BigInteger projectId;
      if (user.getJobTitle() != JobTitle.PROJECT_MANAGER) {
        projectId = projectDAO
            .findProjectIdByUserLogin(userLogin);
      } else {
        projectId = projectDAO
            .findProjectIdByPMLogin(userLogin);
      }

      Project project = projectDAO.findProjectByProjectId(projectId);
      projectManagerId = project.getProjectManagerId();
    }

    Map<String, String> errorMap = new HashMap<>();

    WorkingDayValidator validator = new WorkingDayValidator();

    if (StringUtils.isEmpty(monday) && StringUtils
        .isEmpty(tuesday) &&
        StringUtils.isEmpty(wednesday) && StringUtils
        .isEmpty(thursday) &&
        StringUtils.isEmpty(friday) && StringUtils
        .isEmpty(saturday) &&
        StringUtils.isEmpty(sunday)) {
      errorMap.put("EMPTY_DAY_ERROR", ErrorMessages.EMPTY_DAY_ERROR);
      attributes.addFlashAttribute("workingDayError", errorMap);
        return "redirect:/";
    }
    if (!StringUtils.isEmpty(monday)) {
      Map<String, String> mondayMap = validator
          .validateCreate(monday, DayOfWeek.MONDAY);
      if (!mondayMap.isEmpty()) {
        errorMap.putAll(mondayMap);
      }
    }
    if (!StringUtils.isEmpty(tuesday)) {
      Map<String, String> tuesdayMap = validator
          .validateCreate(tuesday, DayOfWeek.TUESDAY);
      if (!tuesdayMap.isEmpty()) {
        errorMap.putAll(tuesdayMap);
      }
    }
    if (!StringUtils.isEmpty(wednesday)) {
      Map<String, String> wednesdayMap = validator
          .validateCreate(wednesday,
              DayOfWeek.WEDNESDAY);
      if (!wednesdayMap.isEmpty()) {
        errorMap.putAll(wednesdayMap);
      }
    }
    if (!StringUtils.isEmpty(thursday)) {
      Map<String, String> thursdayMap = validator
          .validateCreate(thursday,
              DayOfWeek.THURSDAY);
      if (!thursdayMap.isEmpty()) {
        errorMap.putAll(thursdayMap);
      }
    }
    if (!StringUtils.isEmpty(friday)) {
      Map<String, String> fridayMap = validator
          .validateCreate(friday, DayOfWeek.FRIDAY);
      if (!fridayMap.isEmpty()) {
        errorMap.putAll(fridayMap);
      }
    }
    if (!StringUtils.isEmpty(saturday)) {
      Map<String, String> saturdayMap = validator
          .validateCreate(saturday,
              DayOfWeek.SATURDAY);
      if (!saturdayMap.isEmpty()) {
        errorMap.putAll(saturdayMap);
      }
    }
    if (!StringUtils.isEmpty(sunday)) {
      Map<String, String> sundayMap = validator
          .validateCreate(sunday, DayOfWeek.SUNDAY);
      if (!sundayMap.isEmpty()) {
        errorMap.putAll(sundayMap);
      }
    }

    if (!errorMap.isEmpty()) {
      attributes.addFlashAttribute("workingDayError", errorMap);
      return "redirect:/";
    }

    if (!StringUtils.isEmpty(monday)) {
      createWorkingDay(monday, user, projectManagerId,
          DayOfWeek.MONDAY, errorMap);
      if (!errorMap.isEmpty()) {
        attributes.addFlashAttribute("workingDayError", errorMap);
        return "redirect:/";
      }
    }

    if (!StringUtils.isEmpty(tuesday)) {
      createWorkingDay(tuesday, user, projectManagerId,
          DayOfWeek.TUESDAY, errorMap);
      if (!errorMap.isEmpty()) {
        attributes.addFlashAttribute("workingDayError", errorMap);
        return "redirect:/";
      }
    }
    if (!StringUtils.isEmpty(wednesday)) {
      createWorkingDay(wednesday, user, projectManagerId,
          DayOfWeek.WEDNESDAY, errorMap);
      if (!errorMap.isEmpty()) {
        attributes.addFlashAttribute("workingDayError", errorMap);
        return "redirect:/";
      }
    }
    if (!StringUtils.isEmpty(thursday)) {
      createWorkingDay(thursday, user, projectManagerId,
          DayOfWeek.THURSDAY, errorMap);
      if (!errorMap.isEmpty()) {
        attributes.addFlashAttribute("workingDayError", errorMap);
        return "redirect:/";
      }
    }
    if (!StringUtils.isEmpty(friday)) {
      createWorkingDay(friday, user, projectManagerId,
          DayOfWeek.FRIDAY, errorMap);
      if (!errorMap.isEmpty()) {
        attributes.addFlashAttribute("workingDayError", errorMap);
        return "redirect:/";
      }
    }
    if (!StringUtils.isEmpty(saturday)) {
      createWorkingDay(saturday, user, projectManagerId,
          DayOfWeek.SATURDAY, errorMap);
      if (!errorMap.isEmpty()) {
        attributes.addFlashAttribute("workingDayError", errorMap);
        return "redirect:/";
      }
    }
    if (!StringUtils.isEmpty(sunday)) {
      createWorkingDay(sunday, user, projectManagerId,
          DayOfWeek.SUNDAY, errorMap);
      if (!errorMap.isEmpty()) {
        attributes.addFlashAttribute("workingDayError", errorMap);
        return "redirect:/";
      }
    }
    attributes.addFlashAttribute("success", "success");
    return "redirect:/";
  }

  private void createWorkingDay(
      String time, User user,
      BigInteger projectManagerId, DayOfWeek day,
      Map<String, String> errorMap) {
    WorkingDay workingDay = getWorkingDay(time, user, projectManagerId,
        day);
    Integer existWorkingDay = workingDayDAO
        .findIfWorkingDayExists(user.getUserId(),
            workingDay.getDate());
    if (existWorkingDay > 0) {
      WorkingDay actualWorkingDay = workingDayDAO
          .findWorkingDayByUserIdAndDate(user.getUserId(),
              workingDay.getDate());
      if (!actualWorkingDay.getStatus().name()
          .equals(Status.DISAPPROVED.name())) {
        if ((actualWorkingDay.getWorkingHours() + workingDay.getWorkingHours())
            > 12) {
          errorMap.put("WORKING_DAY_OVERSTATEMENT_ERROR",
              day + ErrorMessages.WORKING_DAY_OVERSTATEMENT_ERROR);
          return;
        }
        workingDayDAO.updateWorkingHours(actualWorkingDay.getWorkingDayId(),
            workingDay.getWorkingHours());
        if (user.getJobTitle().name().equals(JobTitle.PROJECT_MANAGER.name())) {
          workingDayDAO
              .updateWorkingDayStatus(actualWorkingDay.getWorkingDayId(),
                  Status.WAITING_FOR_APPROVAL.getId());
        } else {
          workingDayDAO
              .updateWorkingDayStatus(actualWorkingDay.getWorkingDayId(),
                  Status.WAITING_FOR_APPROVAL.getId());
        }
      } else {
        workingDayDAO
            .updateDisapprovedWorkingHours(actualWorkingDay.getWorkingDayId(),
                workingDay.getWorkingHours());
        if (user.getJobTitle().name().equals(JobTitle.PROJECT_MANAGER.name())) {
          workingDayDAO
              .updateWorkingDayStatus(actualWorkingDay.getWorkingDayId(),
                  Status.WAITING_FOR_APPROVAL.getId());
        } else {
          workingDayDAO
              .updateWorkingDayStatus(actualWorkingDay.getWorkingDayId(),
                  Status.WAITING_FOR_APPROVAL.getId());
        }
      }
    } else if (existWorkingDay == 0) {
      if (workingDay.getWorkingHours() > 12) {
        errorMap.put("WORKING_DAY_OVERSTATEMENT_ERROR",
            day + ErrorMessages.WORKING_DAY_OVERSTATEMENT_ERROR);
        return;
      }
      workingDayDAO.createWorkingDay(workingDay);
    }
  }

  private WorkingDay getWorkingDay(String time, User user,
      BigInteger projectManagerId, DayOfWeek dayOfWeek) {

    return new WorkingDayService()
        .getWorkingDay(time, dayOfWeek,
            user.getUserId(), projectManagerId);
  }
}
