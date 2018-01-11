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
  String EMPTY_JOB_TITLE_ERROR = "Job title can't be empty!";
  String INCORRECT_STATUS_ERROR = "Incorrect status!";
  String WRONG_START_DATE_FORMAT_ERROR = "Wrong start data format!";
  String WRONG_END_DATE_FORMAT_ERROR = "Wrong end data format!";
  String BUSINESS_TRIP_ERROR = "Business trip doesn't exist!";
  String USER_ERROR = "User does not exist!";
  String USER_EXIST_ERROR = "User login already exists, please choose another login!";
  String WORK_PERIOD_ERROR = "Work period does not exist!";
  String PROJECT_EXIST_ERROR = "Project already exists, please choose another name!";
  String USER_PROJECT_STATUS_WORKING_ERROR = "is currently working!";
  String PROJECT_ERROR = "Project doesn't exist!";
  String PROJECT_NAME_ERROR = "Project name already exist!";
  String VIEW_SPRINT_TRANSIT_PM_ERROR = "You are currently does not have a project!";
  String USER_FIRST_OR_LAST_NAME_ERROR = "Please type correct name!";
  String SPRINTS_EXIST_ERROR = "No sprints available for project!";
  String USER_ON_TRANSIT_ERROR = "Can't create vacation while user is on transit!";
  String WRONG_USER_ERROR = "Wrong user!";
  String VACATION_MODIFY_ERROR = "Impossible to modify approved vacation!";
  String VACATION_EXIST_ERROR = "Vacation does not exist!";
  String LM_EXIST_ERROR = "LM already exist on project, choose another user";
  String PM_EXIST_ERROR = "LM already exist on project, choose another user";
  String TASK_ERROR = "Task does not exist!";
  String PROJECT_ACCESS_ERROR = "Can't get information about this project!";
  String SPRINT_START_DATE_ERROR = "Sprint start date can't be less than project start date!";
  String SPRINT_END_DATE_ERROR = "Sprint end date can't be greater than project end date!";
  String SPRINT_NAME_EXISTENCE_ERROR = "This sprint name is already in use!";
  String NEW_OLD_SPRINT_START_DATE_ERROR = "New sprint start date can't be less than previous sprint end date!";
  String TRIP_START_DATE_ERROR = "Trip start date can't be less than project start date!";
  String TRIP_END_DATE_ERROR = "Trip end date can't be greater than project end date!";
  String WORK_PERIOD_START_DATE_ERROR = "Work period start date can't be less than project start date!";
  String WORK_PERIOD_END_DATE_ERROR = "Work period end date can't be greater than project end date!";
}
