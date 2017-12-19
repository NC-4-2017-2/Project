package com.netcracker.project.controllers;

import com.netcracker.project.model.VacationDAO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/vacation")
public class VacationController {

  private static final Logger logger = Logger.getLogger(VacationController.class);
  @Autowired
  private VacationDAO vacationDAO;


  @RequestMapping(value = "/create")
  public String createVacationGet() {
    return "vacation/create";
  }

}
