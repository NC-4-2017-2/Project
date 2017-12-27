package com.netcracker.project.controllers.validators;

public interface RegexPatterns {
  String ID_PATTERN = "^\\d*$";
  String DATE_PATTERN = "[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])";
  String TIME_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
}
