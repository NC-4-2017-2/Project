package com.netcracker.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class DashboardController {

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String createVacationGet() {
    return "dashboard";
  }

}
