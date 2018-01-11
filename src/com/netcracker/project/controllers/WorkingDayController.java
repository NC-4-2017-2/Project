package com.netcracker.project.controllers;

import com.netcracker.project.controllers.validators.WorkingDayValidator;
import com.netcracker.project.controllers.validators.errorMessage.ErrorMessages;
import com.netcracker.project.model.ProjectDAO;
import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.WorkingDayDAO;
import com.netcracker.project.model.entity.Project;
import com.netcracker.project.model.entity.User;
import com.netcracker.project.model.entity.WorkingDay;
import com.netcracker.project.model.enums.JobTitle;
import com.netcracker.project.model.enums.Status;
import com.netcracker.project.services.impl.DateConverterService;
import com.netcracker.project.services.impl.WorkingDayService;
import java.math.BigInteger;
import java.security.Principal;
import java.time.DayOfWeek;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
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
    model.addAttribute("workingDays", workingDays);
    return "workingDay/viewWorkingDay";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/findPMWorkingDay", method = RequestMethod.GET)
  public String findPMWorkingDay() {
    logger.info("Entering findPMWorkingDay()");
    return "workingDay/findPMWorkingDay";
  }

  @Secured({"ROLE_PM"})
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
    if (workingDays.isEmpty()) {
      return "responseStatus/noDataFound";
    }
    model.addAttribute("workingDays", workingDays);
    return "workingDay/viewWorkingDay";
  }

  @Secured({"ROLE_PM"})
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
    model.addAttribute("workingDays", workingDays);
    return "workingDay/viewWorkingDay";
  }

  @RequestMapping(value = "/createWorkingDay", method = RequestMethod.GET)
  public String createWorkingDays() {
    logger.info("Entering createWorkingDays()");
    return "workingDay/createWorkingDay";
  }

  @RequestMapping(value = "/createWorkingDay", method = RequestMethod.POST)
  public String createWorkingDayPost(
      @RequestParam(value = "mondayStartTime") String mondayStartTime,
      @RequestParam(value = "mondayEndTime") String mondayEndTime,
      @RequestParam(value = "tuesdayStartTime") String tuesdayStartTime,
      @RequestParam(value = "tuesdayEndTime") String tuesdayEndTime,
      @RequestParam(value = "wednesdayStartTime") String wednesdayStartTime,
      @RequestParam(value = "wednesdayEndTime") String wednesdayEndTime,
      @RequestParam(value = "thursdayStartTime") String thursdayStartTime,
      @RequestParam(value = "thursdayEndTime") String thursdayEndTime,
      @RequestParam(value = "fridayStartTime") String fridayStartTime,
      @RequestParam(value = "fridayEndTime") String fridayEndTime,
      @RequestParam(value = "saturdayStartTime") String saturdayStartTime,
      @RequestParam(value = "saturdayEndTime") String saturdayEndTime,
      @RequestParam(value = "sundayStartTime") String sundayStartTime,
      @RequestParam(value = "sundayEndTime") String sundayEndTime,
      Principal principal,
      Model model) {
    logger.info("Entering createWorkingDayPost()");
    String userLogin = principal.getName();
    User user = userDAO.findUserByLogin(userLogin);
    BigInteger projectId;
    if (user.getJobTitle() != JobTitle.PROJECT_MANAGER) {
      projectId = projectDAO
          .findProjectIdByUserLogin(userLogin);
    } else {
      projectId = projectDAO
          .findProjectIdByPMLogin(userLogin);
    }

    Project project = projectDAO.findProjectByProjectId(projectId);
    Map<String, String> errorMap = new HashMap<>();

    WorkingDayValidator validator = new WorkingDayValidator();

    if (StringUtils.isEmpty(mondayStartTime) && StringUtils
        .isEmpty(mondayEndTime) &&
        StringUtils.isEmpty(tuesdayStartTime) && StringUtils
        .isEmpty(tuesdayEndTime) &&
        StringUtils.isEmpty(wednesdayStartTime) && StringUtils
        .isEmpty(wednesdayEndTime) &&
        StringUtils.isEmpty(thursdayStartTime) && StringUtils
        .isEmpty(thursdayEndTime) &&
        StringUtils.isEmpty(fridayStartTime) && StringUtils
        .isEmpty(fridayEndTime) &&
        StringUtils.isEmpty(saturdayStartTime) && StringUtils
        .isEmpty(saturdayEndTime) &&
        StringUtils.isEmpty(sundayStartTime) && StringUtils
        .isEmpty(sundayEndTime)
        ) {
      errorMap.put("EMPTY_DAY_ERROR", ErrorMessages.EMPTY_DAY_ERROR);
      model.addAttribute("errorMap", errorMap);
      return "workingDay/createWorkingDay";
    }
    if (!StringUtils.isEmpty(mondayStartTime) && !StringUtils
        .isEmpty(mondayEndTime)) {
      Map<String, String> mondayMap = validator
          .validateCreate(mondayStartTime, mondayEndTime, DayOfWeek.MONDAY);
      if (!mondayMap.isEmpty()) {
        errorMap.putAll(mondayMap);
      }
    }
    if (!StringUtils.isEmpty(tuesdayStartTime) && !StringUtils
        .isEmpty(tuesdayEndTime)) {
      Map<String, String> tuesdayMap = validator
          .validateCreate(tuesdayStartTime, tuesdayEndTime, DayOfWeek.TUESDAY);
      if (!tuesdayMap.isEmpty()) {
        errorMap.putAll(tuesdayMap);
      }
    }
    if (!StringUtils.isEmpty(wednesdayStartTime) && !StringUtils
        .isEmpty(wednesdayEndTime)) {
      Map<String, String> wednesdayMap = validator
          .validateCreate(wednesdayStartTime, wednesdayEndTime,
              DayOfWeek.WEDNESDAY);
      if (!wednesdayMap.isEmpty()) {
        errorMap.putAll(wednesdayMap);
      }
    }
    if (!StringUtils.isEmpty(thursdayStartTime) && !StringUtils
        .isEmpty(thursdayEndTime)) {
      Map<String, String> thursdayMap = validator
          .validateCreate(thursdayStartTime, thursdayEndTime,
              DayOfWeek.THURSDAY);
      if (!thursdayMap.isEmpty()) {
        errorMap.putAll(thursdayMap);
      }
    }
    if (!StringUtils.isEmpty(fridayStartTime) && !StringUtils
        .isEmpty(fridayEndTime)) {
      Map<String, String> fridayMap = validator
          .validateCreate(fridayStartTime, fridayEndTime, DayOfWeek.FRIDAY);
      if (!fridayMap.isEmpty()) {
        errorMap.putAll(fridayMap);
      }
    }
    if (!StringUtils.isEmpty(saturdayStartTime) && !StringUtils
        .isEmpty(saturdayEndTime)) {
      Map<String, String> saturdayMap = validator
          .validateCreate(saturdayStartTime, saturdayEndTime,
              DayOfWeek.SATURDAY);
      if (!saturdayMap.isEmpty()) {
        errorMap.putAll(saturdayMap);
      }
    }
    if (!StringUtils.isEmpty(sundayStartTime) && !StringUtils
        .isEmpty(sundayEndTime)) {
      Map<String, String> sundayMap = validator
          .validateCreate(sundayStartTime, sundayEndTime, DayOfWeek.SUNDAY);
      if (!sundayMap.isEmpty()) {
        errorMap.putAll(sundayMap);
      }
    }

    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "workingDay/createWorkingDay";
    }

    if (!StringUtils.isEmpty(mondayStartTime) && !StringUtils
        .isEmpty(mondayEndTime)) {
      createWorkingDay(mondayStartTime, mondayEndTime, user, project,
          DayOfWeek.MONDAY, errorMap);
      if (!errorMap.isEmpty()) {
        model.addAttribute("errorMap", errorMap);
        return "workingDay/createWorkingDay";
      }
    }

    if (!StringUtils.isEmpty(tuesdayStartTime) && !StringUtils
        .isEmpty(tuesdayEndTime)) {
      createWorkingDay(tuesdayStartTime, tuesdayEndTime, user, project,
          DayOfWeek.TUESDAY, errorMap);
      if (!errorMap.isEmpty()) {
        model.addAttribute("errorMap", errorMap);
        return "workingDay/createWorkingDay";
      }
    }
    if (!StringUtils.isEmpty(wednesdayStartTime) && !StringUtils
        .isEmpty(wednesdayEndTime)) {
      createWorkingDay(wednesdayStartTime, wednesdayEndTime, user, project,
          DayOfWeek.WEDNESDAY, errorMap);
      if (!errorMap.isEmpty()) {
        model.addAttribute("errorMap", errorMap);
        return "workingDay/createWorkingDay";
      }
    }
    if (!StringUtils.isEmpty(thursdayStartTime) && !StringUtils
        .isEmpty(thursdayEndTime)) {
      createWorkingDay(thursdayStartTime, thursdayEndTime, user, project,
          DayOfWeek.THURSDAY, errorMap);
      if (!errorMap.isEmpty()) {
        model.addAttribute("errorMap", errorMap);
        return "workingDay/createWorkingDay";
      }
    }
    if (!StringUtils.isEmpty(fridayStartTime) && !StringUtils
        .isEmpty(fridayEndTime)) {
      createWorkingDay(fridayStartTime, fridayEndTime, user, project,
          DayOfWeek.FRIDAY, errorMap);
      if (!errorMap.isEmpty()) {
        model.addAttribute("errorMap", errorMap);
        return "workingDay/createWorkingDay";
      }
    }
    if (!StringUtils.isEmpty(saturdayStartTime) && !StringUtils
        .isEmpty(saturdayEndTime)) {
      createWorkingDay(saturdayStartTime, saturdayEndTime, user, project,
          DayOfWeek.SATURDAY, errorMap);
      if (!errorMap.isEmpty()) {
        model.addAttribute("errorMap", errorMap);
        return "workingDay/createWorkingDay";
      }
    }
    if (!StringUtils.isEmpty(sundayStartTime) && !StringUtils
        .isEmpty(sundayEndTime)) {
      createWorkingDay(sundayStartTime, sundayEndTime, user, project,
          DayOfWeek.SUNDAY, errorMap);
      if (!errorMap.isEmpty()) {
        model.addAttribute("errorMap", errorMap);
        return "workingDay/createWorkingDay";
      }
    }
    return "responseStatus/success";
  }

  private void createWorkingDay(
      String startTime,
      String endTime, User user,
      Project project, DayOfWeek day, Map<String, String> errorMap) {
    WorkingDay workingDay = getWorkingDay(startTime, endTime,
        user, project,
        day);
    Integer existWorkingDay = workingDayDAO
        .findIfWorkingDayExists(user.getUserId(),
            workingDay.getDate());
    if (existWorkingDay > 0) {
      WorkingDay actualWorkingDay = workingDayDAO
          .findWorkingDayByUserIdAndDate(user.getUserId(),
              workingDay.getDate());
      if ((actualWorkingDay.getWorkingHours() + workingDay.getWorkingHours())
          > 12) {
        errorMap.put("WORKING_DAY_OVERSTATEMENT_ERROR",
            day + ErrorMessages.WORKING_DAY_OVERSTATEMENT_ERROR);
        return;
      }
      workingDayDAO.updateWorkingHours(actualWorkingDay.getWorkingDayId(),
          workingDay.getWorkingHours());
      if (user.getJobTitle().name().equals(JobTitle.PROJECT_MANAGER.name())) {
        workingDayDAO.updateWorkingDayStatus(actualWorkingDay.getWorkingDayId(),
            Status.WAITING_FOR_APPROVAL.getId());
      } else {
        workingDayDAO.updateWorkingDayStatus(actualWorkingDay.getWorkingDayId(),
            Status.WAITING_FOR_APPROVAL.getId());
      }
    } else if (existWorkingDay == 0) {
      workingDayDAO.createWorkingDay(workingDay);
    }
  }

  private WorkingDay getWorkingDay(String start, String end, User user,
      Project project, DayOfWeek dayOfWeek) {
    Long minutesBetweenTimes = converter
        .getMinutesBetweenLocalTimes(
            converter.getLocalTimeFromString(start),
            converter.getLocalTimeFromString(end));

    return new WorkingDayService()
        .getWorkingDay(minutesBetweenTimes, dayOfWeek,
            user.getUserId(), project.getProjectManagerId());
  }
}
