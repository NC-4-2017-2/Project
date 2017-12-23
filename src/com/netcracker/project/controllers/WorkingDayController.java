package com.netcracker.project.controllers;

import com.netcracker.project.model.entity.WorkingDay;
import com.netcracker.project.services.impl.WorkingDayService;
import java.time.DayOfWeek;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/workingDay")
public class WorkingDayController {

  @RequestMapping(value = "/create", method = RequestMethod.GET)
  public String createWorkingDays() {
    return "workingDay/workingDay";
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
      @RequestParam(value = "sundayEndTime") String sundayEndTime) {
    WorkingDay workingDay = new WorkingDayService()
        .getWorkingDay(mondayStartTime, mondayEndTime, DayOfWeek.MONDAY);

    return "workingDay/workingDay";
  }
}
