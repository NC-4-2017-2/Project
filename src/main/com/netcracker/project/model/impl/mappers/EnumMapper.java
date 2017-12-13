package main.com.netcracker.project.model.impl.mappers;

public enum EnumMapper {
  BUSINESS_TRIP_ID("BUSINESS_TRIP_ID"),
  PROJECT_ID("PROJECT_ID"),
  USER_ID("USER_ID"),
  AUTHOR_ID("AUTHOR_ID"),
  PM_ID("PM_ID"),
  LM_ID("LM_ID"),
  SPRINT_ID("SPRINT_ID"),
  TASK_ID("TASK_ID"),
  VACATION_ID("VACATION_ID"),
  WORK_PERIOD_ID("WORK_PERIOD_ID"),
  WORKING_DAY_ID("WORKING_DAY_ID"),
  COUNTRY("COUNTRY"),
  STATUS("STATUS"),
  WORKING_PERIOD_STATUS("STATUS"),
  START_DATE("START_DATE"),
  END_DATE("END_DATE"),
  NAME("NAME"),
  PLANNED_END_DATE("PLANNED_END_DATE"),
  TASK_TYPE("TASK_TYPE"),
  TASK_PRIORITY("TASK_PRIORITY"),
  TASK_DESCRIPTION("TASK_DESCRIPTION"),
  TASK_COMMENT("TASK_COMMENT"),
  BIRTH_DATE("BIRTH_DATE"),
  HIRE_DATE("HIRE_DATE"),
  LAST_NAME("LAST_NAME"),
  FIRST_NAME("FIRST_NAME"),
  EMAIL("EMAIL"),
  PHONE_NUMBER("PHONE_NUMBER"),
  JOB_TITLE("JOB_TITLE"),
  PM_STATUS("PM_STATUS"),
  LM_STATUS("LM_STATUS"),
  WORKING_DATE("WORKING_DATE"),
  WEEK_NUMBER("WEEK_NUMBER"),
  WORKING_HOURS("WORKING_HOURS"),
  TAKE_DAYS("TAKE_DAYS"),
  PLANNED_TAKE_DAYS("PLANNED_TAKE_DAYS"),
  CRITICAL("CRITICAL"),
  HIGH("HIGH"),
  NORMAL("NORMAL"),
  LOW("LOW");

  private String fullName;

  EnumMapper(String fullName) {
    this.fullName = fullName;
  }

  String getFullName() {
    return fullName;
  }

}
