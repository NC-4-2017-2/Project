package com.netcracker.project.controllers.validators.errorMessage;

public interface  ErrorMessages {
  String TIME_ERROR = " wrong start time format!";
  String WORKING_EXISTENCE_ERROR = "Working day doesn't exist!";
  String EMPTY_START_DAY_ERROR = " start day is empty!";
  String EMPTY_END_DAY_ERROR = " end day is empty!";
  String NULL_ERROR = " can't be null!";
  String WORKING_DAY_OVERSTATEMENT_ERROR = " working hours more than 12!";
  String START_TIME_LESS_END_TIME_ERROR = " start time can't be less than end time!";
  String START_TIME_EQUALS_END_TIME_ERROR = " start time can't be same as end time!";
  String DAYS_FILL_ERROR = "Please, fill in only current or previous days!";
  String NULL_DAY_ERROR = "Day of week can't be null!";
  String WRONG_DAY_ERROR = "Please choose correct day of week!";
  String INVALID_USER_ERROR = "Invalid user!";
  String EMPTY_DAY_ERROR = "Set at least one day!";
  String EMPTY_COUNTRY_ERROR = "Country can't be empty!";
  String COUNTRY_EXISTENCE_ERROR = "Country doesn't exist!";
  String START_DATE_NULL_ERROR = "Start date can't null!";
  String END_DATE_NULL_ERROR = "End date can't null!";
  String START_DATE_EQUALS_END_DATE_ERROR = "Start date can't be same as end date!";
  String START_DATE_BIGGER_THAN_END_DATE_ERROR = "Start date can't be bigger than end date!";
  String EMPTY_ID_ERROR = "Id can't be empty!";
  String WRONG_ID_FORMAT_ERROR = "Id wrong format!";
  String EMPTY_STATUS_ERROR = "Status can't be empty!";
  String INCORRECT_STATUS_ERROR = "Incorrect status!";
  String WRONG_START_DATE_FORMAT_ERROR = "Wrong start data format!";
  String WRONG_END_DATE_FORMAT_ERROR = "Wrong end data format!";
  String BUSINESS_TRIP_ERROR = "Business trip doesn't exist!";
  String USER_ERROR = "User does not exist!";
  String WORK_PERIOD_ERROR = "Work period does not exist!";
  String PROJECT_EXIST_ERROR = "Project already exists, please choose another name!";
  String USER_PROJECT_STATUS_WORKING_ERROR = "is currently working!";
  String PROJECT_ERROR = "Project doesn't exist!";
  String VIEW_SPRINT_TRANSIT_PM_ERROR = "You are currently does not have a project!";
  String USER_FIRST_OR_LAST_NAME_ERROR = "Please type correct name!";
  String SPRINTS_EXIST_ERROR = "No sprints available for project!";
  String USER_ON_TRANSIT_ERROR = "Can't create vacation while user is on transit!";
}
