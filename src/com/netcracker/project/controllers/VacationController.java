package com.netcracker.project.controllers;

import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.VacationDAO;
import com.netcracker.project.model.entity.User;
import com.netcracker.project.model.entity.Vacation;
import com.netcracker.project.model.enums.Status;
import com.netcracker.project.model.impl.mappers.MapperDateConverter;
import java.math.BigInteger;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
  private UserDAO userDAO;
  private MapperDateConverter converter = new MapperDateConverter();

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public String createVacationPost(
      @RequestParam("userId") BigInteger userId,
      @RequestParam("projectId") BigInteger projectId,
      @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate,
      @RequestParam("pmApproveStatus") Status pmApproveStatus,
      @RequestParam("lmApproveStatus") Status lmApproveStatus,
      @RequestParam("pmId") BigInteger pmId,
      @RequestParam("lmId") BigInteger lmId) {

    logger.info("createVacationPost() method. Params: userId:" + userId
        + "projectId: " + projectId
        + "startDate: " + startDate
        + "endDate: " + endDate
        + "pmApproveStatus: " + pmApproveStatus
        + "lmApproveStatus: " + lmApproveStatus
        + "lmId: " + lmId
        + "pmId: " + pmId);

    User user;
    if (pmId != null) {
       user = userDAO.findUserByUserId(pmId);
    }

    Vacation vacation = new Vacation.VacationBuilder()
        .userId(userId)
        .projectId(projectId)
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

  @RequestMapping(value = "/create" , method = RequestMethod.GET)
  public String createVacationGet() {
    return "vacation/create";
  }

/*  @RequestMapping(value = "/approve={vacationID}", method = RequestMethod.GET)
  public String approveVacation(@PathVariable("vacationID") BigInteger vacationID) {

    return null;
  }*/


}
