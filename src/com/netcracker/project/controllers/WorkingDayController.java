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
    Map<String, String> errorMap = new HashMap<>();

    WorkingDayValidator validator = new WorkingDayValidator();

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
    if (!mondayStartTime.isEmpty() && !mondayEndTime.isEmpty()) {
      Map<String, String> mondayMap = validator
          .validateCreate(mondayStartTime, mondayEndTime, DayOfWeek.MONDAY);
      if (!mondayMap.isEmpty()) {
        errorMap.putAll(mondayMap);
      }
    }
    if (!tuesdayStartTime.isEmpty() && !tuesdayEndTime.isEmpty()) {
      Map<String, String> tuesdayMap = validator
          .validateCreate(tuesdayStartTime, tuesdayEndTime, DayOfWeek.TUESDAY);
      if (!tuesdayMap.isEmpty()) {
        errorMap.putAll(tuesdayMap);
      }
    }
    if (!wednesdayStartTime.isEmpty() && !wednesdayEndTime.isEmpty()) {
      Map<String, String> wednesdayMap = validator
          .validateCreate(wednesdayStartTime, wednesdayEndTime,
              DayOfWeek.WEDNESDAY);
      if (!wednesdayMap.isEmpty()) {
        errorMap.putAll(wednesdayMap);
      }
    }
    if (!thursdayStartTime.isEmpty() && !thursdayEndTime.isEmpty()) {
      Map<String, String> thursdayMap = validator
          .validateCreate(thursdayStartTime, thursdayEndTime,
              DayOfWeek.THURSDAY);
      if (!thursdayMap.isEmpty()) {
        errorMap.putAll(thursdayMap);
      }
    }
    if (!fridayStartTime.isEmpty() && !fridayEndTime.isEmpty()) {
      Map<String, String> fridayMap = validator
          .validateCreate(fridayStartTime, fridayEndTime, DayOfWeek.FRIDAY);
      if (!fridayMap.isEmpty()) {
        errorMap.putAll(fridayMap);
      }
    }
    if (!saturdayStartTime.isEmpty() && !saturdayEndTime.isEmpty()) {
      Map<String, String> saturdayMap = validator
          .validateCreate(saturdayStartTime, saturdayEndTime,
              DayOfWeek.SATURDAY);
      if (!saturdayMap.isEmpty()) {
        errorMap.putAll(saturdayMap);
      }
    }
    if (!sundayStartTime.isEmpty() && !sundayEndTime.isEmpty()) {
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

    if (!mondayStartTime.isEmpty() && !mondayEndTime.isEmpty()) {
      createWorkingDay(mondayStartTime, mondayEndTime, user, project,
          DayOfWeek.MONDAY, errorMap);
      if (!errorMap.isEmpty()) {
        model.addAttribute("errorMap", errorMap);
        return "workingDay/createWorkingDay";
      }
    }

    if (!tuesdayStartTime.isEmpty() && !tuesdayEndTime.isEmpty()) {
      createWorkingDay(tuesdayStartTime, tuesdayEndTime, user, project,
          DayOfWeek.TUESDAY, errorMap);
      if (!errorMap.isEmpty()) {
        model.addAttribute("errorMap", errorMap);
        return "workingDay/createWorkingDay";
      }
    }
    if (!wednesdayStartTime.isEmpty() && !wednesdayEndTime.isEmpty()) {
      createWorkingDay(wednesdayStartTime, wednesdayEndTime, user, project,
          DayOfWeek.WEDNESDAY, errorMap);
      if (!errorMap.isEmpty()) {
        model.addAttribute("errorMap", errorMap);
        return "workingDay/createWorkingDay";
      }
    }
    if (!thursdayStartTime.isEmpty() && !thursdayEndTime.isEmpty()) {
      createWorkingDay(thursdayStartTime, thursdayEndTime, user, project,
          DayOfWeek.THURSDAY, errorMap);
      if (!errorMap.isEmpty()) {
        model.addAttribute("errorMap", errorMap);
        return "workingDay/createWorkingDay";
      }
    }
    if (!fridayStartTime.isEmpty() && !fridayEndTime.isEmpty()) {
      createWorkingDay(fridayStartTime, fridayEndTime, user, project,
          DayOfWeek.FRIDAY, errorMap);
      if (!errorMap.isEmpty()) {
        model.addAttribute("errorMap", errorMap);
        return "workingDay/createWorkingDay";
      }
    }
    if (!saturdayStartTime.isEmpty() && !saturdayEndTime.isEmpty()) {
      createWorkingDay(saturdayStartTime, saturdayEndTime, user, project,
          DayOfWeek.SATURDAY, errorMap);
      if (!errorMap.isEmpty()) {
        model.addAttribute("errorMap", errorMap);
        return "workingDay/createWorkingDay";
      }
    }
    if (!sundayStartTime.isEmpty() && !sundayEndTime.isEmpty()) {
      createWorkingDay(sundayStartTime, sundayEndTime, user, project,
          DayOfWeek.SUNDAY, errorMap);
      if (!errorMap.isEmpty()) {
        model.addAttribute("errorMap", errorMap);
        return "workingDay/createWorkingDay";
      }
    }
    return "response_status/success";
  }

  private void createWorkingDay(
      String mondayStartTime,
      String mondayEndTime, User user,
      Project project, DayOfWeek day, Map<String, String> errorMap) {
    WorkingDay workingDay = getWorkingDay(mondayStartTime, mondayEndTime,
        user, project,
        day);
    Integer existWorkingDay = workingDayDAO
        .findIfWorkingDayExist(user.getUserId(),
            workingDay.getDate());
    if (existWorkingDay > 0) {
      WorkingDay actualWorkingDay = workingDayDAO
          .findWorkingDayByUserIdAndDate(user.getUserId(),
              workingDay.getDate());
      if ((actualWorkingDay.getWorkingHours() + workingDay.getWorkingHours())
          > 12) {
        errorMap.put("failOvertime", day + " working hours more than 12!");
        return;
      }
      workingDayDAO.updateWorkingHours(actualWorkingDay.getWorkingDayId(),
          workingDay.getWorkingHours());
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
