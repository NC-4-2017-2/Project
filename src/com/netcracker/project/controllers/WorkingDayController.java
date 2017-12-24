package com.netcracker.project.controllers;

import com.netcracker.project.controllers.validators.WorkingDayValidator;
import com.netcracker.project.model.ProjectDAO;
import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.WorkingDayDAO;
import com.netcracker.project.model.entity.Project;
import com.netcracker.project.model.entity.User;
import com.netcracker.project.model.entity.WorkingDay;
import com.netcracker.project.services.impl.DateConverterService;
import com.netcracker.project.services.impl.WorkingDayService;
import java.math.BigInteger;
import java.security.Principal;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

  @RequestMapping(value = "/create", method = RequestMethod.GET)
  public String createWorkingDays() {
    return "workingDay/createWorkingDay";
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public String getWorkingDays(
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
    String userLogin = principal.getName();
    User user = userDAO.findUserByLogin(userLogin);
    BigInteger projectId = projectDAO
        .findProjectIdByUserLogin(userLogin);
    Project project = projectDAO.findProjectByProjectId(projectId);

    WorkingDayValidator validator = new WorkingDayValidator();
    Map<String, String> errorMap = new HashMap<>();

    if (mondayStartTime.isEmpty() && mondayEndTime.isEmpty() &&
        tuesdayStartTime.isEmpty() && tuesdayEndTime.isEmpty() &&
        wednesdayStartTime.isEmpty() && wednesdayEndTime.isEmpty() &&
        thursdayStartTime.isEmpty() && thursdayEndTime.isEmpty() &&
        fridayStartTime.isEmpty() && fridayEndTime.isEmpty() &&
        saturdayStartTime.isEmpty() && saturdayEndTime.isEmpty() &&
        sundayStartTime.isEmpty() && sundayEndTime.isEmpty()
        ) {
      errorMap.put("emptyWorkingDay", "Set at least one day!");
      model.addAttribute("errorMap", errorMap);
      return "workingDay/createWorkingDay";
    }

    Map<String, String> mondayMap = validator
        .validateCreate(mondayStartTime, mondayEndTime, DayOfWeek.MONDAY);
    if (!mondayMap.isEmpty()) {
      errorMap.putAll(mondayMap);
    }
    Map<String, String> tuesdayMap = validator
        .validateCreate(tuesdayStartTime, tuesdayEndTime, DayOfWeek.TUESDAY);
    if (!tuesdayMap.isEmpty()) {
      errorMap.putAll(tuesdayMap);
    }
    Map<String, String> wednesdayMap = validator
        .validateCreate(wednesdayStartTime, wednesdayEndTime,
            DayOfWeek.WEDNESDAY);
    if (!wednesdayMap.isEmpty()) {
      errorMap.putAll(wednesdayMap);
    }
    Map<String, String> thursdayMap = validator
        .validateCreate(thursdayStartTime, thursdayEndTime, DayOfWeek.THURSDAY);
    if (!thursdayMap.isEmpty()) {
      errorMap.putAll(thursdayMap);
    }
    Map<String, String> fridayMap = validator
        .validateCreate(fridayStartTime, fridayEndTime, DayOfWeek.FRIDAY);
    if (!fridayMap.isEmpty()) {
      errorMap.putAll(fridayMap);
    }
    Map<String, String> saturdayMap = validator
        .validateCreate(saturdayStartTime, saturdayEndTime, DayOfWeek.SATURDAY);
    if (!saturdayMap.isEmpty()) {
      errorMap.putAll(saturdayMap);
    }
    Map<String, String> sundayMap = validator
        .validateCreate(sundayStartTime, sundayEndTime, DayOfWeek.SUNDAY);
    if (!sundayMap.isEmpty()) {
      errorMap.putAll(sundayMap);
    }

    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "workingDay/createWorkingDay";
    }

    if (!mondayStartTime.isEmpty() && !mondayEndTime.isEmpty()) {
      WorkingDay workingDay = getWorkingDay(mondayStartTime, mondayEndTime,
          user, project,
          DayOfWeek.MONDAY);
      workingDayDAO.addHoursPerDay(workingDay);
    }

    if (!tuesdayStartTime.isEmpty() && !tuesdayEndTime.isEmpty()) {
      WorkingDay workingDay = getWorkingDay(tuesdayStartTime, tuesdayEndTime,
          user, project,
          DayOfWeek.TUESDAY);
      workingDayDAO.addHoursPerDay(workingDay);
    }
    if (!wednesdayStartTime.isEmpty() && !wednesdayEndTime.isEmpty()) {
      WorkingDay workingDay = getWorkingDay(wednesdayStartTime,
          wednesdayEndTime,
          user, project,
          DayOfWeek.WEDNESDAY);
      workingDayDAO.addHoursPerDay(workingDay);
    }
    if (!thursdayStartTime.isEmpty() && !thursdayEndTime.isEmpty()) {
      WorkingDay workingDay = getWorkingDay(thursdayStartTime, thursdayEndTime,
          user, project,
          DayOfWeek.THURSDAY);
      workingDayDAO.addHoursPerDay(workingDay);
    }
    if (!fridayStartTime.isEmpty() && !fridayEndTime.isEmpty()) {
      WorkingDay workingDay = getWorkingDay(fridayStartTime, fridayEndTime,
          user, project,
          DayOfWeek.FRIDAY);
      workingDayDAO.addHoursPerDay(workingDay);
    }
    if (!saturdayStartTime.isEmpty() && !saturdayEndTime.isEmpty()) {
      WorkingDay workingDay = getWorkingDay(saturdayStartTime, saturdayEndTime,
          user, project,
          DayOfWeek.SATURDAY);
      workingDayDAO.addHoursPerDay(workingDay);
    }
    if (!sundayStartTime.isEmpty() && !sundayEndTime.isEmpty()) {
      WorkingDay workingDay = getWorkingDay(sundayStartTime, sundayEndTime,
          user, project,
          DayOfWeek.SUNDAY);
      workingDayDAO.addHoursPerDay(workingDay);
    }

    return "response_status/success";
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
