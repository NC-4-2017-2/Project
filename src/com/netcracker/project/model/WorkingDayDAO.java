package com.netcracker.project.model;

import com.netcracker.project.model.entity.WorkingDay;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

public interface WorkingDayDAO {

  void createWorkingDay(WorkingDay workingDay);

  void updateWorkingDayStatus(BigInteger workingDayId, Integer statusId);

  void updateWorkingHours(BigInteger workingDayId, Double hours);

  WorkingDay findWorkingDayById(BigInteger id);

  WorkingDay findWorkingDayByUserIdAndDate(BigInteger userId, Date workingDayDate);

  Collection<WorkingDay> findWorkingDayPerPeriod(BigInteger userId, Date startDate, Date endDate);

  Collection<WorkingDay> findWorkingDayByPMIdAndStatus(BigInteger pmId, Integer status);

  Integer findIfWorkingDayExist(BigInteger userId, Date workingDayDate);

  String CREATE_WORKING_DAY = "INSERT ALL "
      + "    INTO OBJECTS(OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION, OBJECT_VERSION) "
      + "VALUES (OBJECT_SEQUENCE.NEXTVAL , NULL, 6, ?, NULL, 1) "
      + "    INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE,DATE_VALUE,LIST_VALUE_ID) "
      + "VALUES (49, OBJECT_SEQUENCE.CURRVAL, NULL, ?, NULL) "
      + "    INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE,DATE_VALUE,LIST_VALUE_ID) "
      + "VALUES (50, OBJECT_SEQUENCE.CURRVAL, ?, NULL, NULL) "
      + "    INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE,DATE_VALUE,LIST_VALUE_ID) "
      + "VALUES (51, OBJECT_SEQUENCE.CURRVAL, ?, NULL, NULL) "
      + "    INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE,DATE_VALUE,LIST_VALUE_ID) "
      + "VALUES (52, OBJECT_SEQUENCE.CURRVAL, NULL, NULL, ?) "
      + "    INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) "
      + "VALUES (53, OBJECT_SEQUENCE.CURRVAL, ?) "
      + "    INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) "
      + "VALUES (54, OBJECT_SEQUENCE.CURRVAL, ?) "
      + "SELECT * FROM DUAL";

  String FIND_WORKING_DAY_BY_ID = "SELECT WD_ID.OBJECT_ID AS WORKING_DAY_ID, USER_ID.OBJECT_ID AS USER_ID, PM_ID.OBJECT_ID AS PM_ID, "
      + "WORKING_DAY_DATE.DATE_VALUE AS WORKING_DATE, WORKING_DAY_WEEK_NUMBER.VALUE AS WEEK_NUMBER, "
      + "WORKING_DAY_HOURS.VALUE AS WORKING_HOURS, WORKING_HOURS_STATUS_VALUE.VALUE AS STATUS "
      + "FROM OBJECTS WD_ID, OBJECTS USER_ID, OBJECTS PM_ID, "
      + "ATTRIBUTES WORKING_DAY_DATE, ATTRIBUTES WORKING_DAY_WEEK_NUMBER, "
      + "ATTRIBUTES WORKING_DAY_HOURS, ATTRIBUTES WORKING_HOURS_STATUS, "
      + "OBJREFERENCE WORKING_HOURS_AUTHOR, OBJREFERENCE PM_APPROVE, "
      + "LISTVALUE WORKING_HOURS_STATUS_VALUE "
      + "WHERE WD_ID.OBJECT_ID = :1 AND "
      + "WORKING_DAY_DATE.ATTR_ID = 49 AND "
      + "WORKING_DAY_DATE.OBJECT_ID = WD_ID.OBJECT_ID AND "
      + "WORKING_DAY_WEEK_NUMBER.ATTR_ID = 50 AND "
      + "WORKING_DAY_WEEK_NUMBER.OBJECT_ID = WD_ID.OBJECT_Id AND "
      + "WORKING_DAY_HOURS.ATTR_ID = 51 AND "
      + "WORKING_DAY_HOURS.OBJECT_ID = WD_ID.OBJECT_ID AND "
      + "WORKING_HOURS_STATUS.ATTR_ID = 52 AND "
      + "WORKING_HOURS_STATUS.OBJECT_ID = WD_ID.OBJECT_ID AND "
      + "WORKING_HOURS_STATUS_VALUE.ATTR_ID = WORKING_HOURS_STATUS.ATTR_ID AND "
      + "WORKING_HOURS_STATUS_VALUE.LIST_VALUE_ID = WORKING_HOURS_STATUS.LIST_VALUE_ID AND "
      + "WORKING_HOURS_AUTHOR.ATTR_ID = 53 AND "
      + "WORKING_HOURS_AUTHOR.OBJECT_ID = WD_ID.OBJECT_ID AND "
      + "USER_ID.OBJECT_ID = WORKING_HOURS_AUTHOR.REFERENCE AND "
      + "PM_APPROVE.ATTR_ID = 54 AND "
      + "PM_APPROVE.OBJECT_ID = WD_ID.OBJECT_ID AND "
      + "PM_ID.OBJECT_ID = PM_APPROVE.REFERENCE";

  String FIND_HOURS_PER_PERIOD =
      "SELECT WORKING_DAY_DATE.DATE_VALUE AS WORKING_DATE, WORKING_WEEK_NUM.VALUE AS WEEK_NUMBER, "
          + "       WORKING_DAY_HOURS.VALUE AS WORKING_HOURS, USER_ID.OBJECT_ID AS USER_ID, "
          + "  WORKING_HOURS_STATUS_VALUE.VALUE AS STATUS, WORKING_DAY_DATE.OBJECT_ID AS WORKING_DAY_ID, "
          + "  PM_REF.REFERENCE AS PM_ID "
          + "FROM OBJECTS USER_ID, "
          + "  ATTRIBUTES WORKING_DAY_DATE, ATTRIBUTES WORKING_HOURS_STATUS, "
          + "  ATTRIBUTES WORKING_WEEK_NUM, ATTRIBUTES WORKING_DAY_HOURS, "
          + "  OBJREFERENCE WD_USER_REF, LISTVALUE WORKING_HOURS_STATUS_VALUE, "
          + "  OBJREFERENCE PM_REF "
          + "WHERE WD_USER_REF.ATTR_ID = 53 AND "
          + "      USER_ID.OBJECT_ID = WD_USER_REF.REFERENCE AND "
          + "      USER_ID.OBJECT_ID = ? AND "
          + "WORKING_DAY_DATE.OBJECT_ID = WD_USER_REF.OBJECT_ID AND "
          + "WORKING_DAY_DATE.ATTR_ID = 49 AND "
          + "TO_DATE(WORKING_DAY_DATE.DATE_VALUE) "
          + "BETWEEN ? AND ? AND "
          + "WORKING_WEEK_NUM.OBJECT_ID = WD_USER_REF.OBJECT_ID AND "
          + "WORKING_WEEK_NUM.ATTR_ID = 50 AND "
          + "WORKING_DAY_HOURS.OBJECT_ID = WD_USER_REF.OBJECT_ID AND "
          + "WORKING_DAY_HOURS.ATTR_ID = 51 AND "
          + "WORKING_HOURS_STATUS.OBJECT_ID = WD_USER_REF.OBJECT_ID AND "
          + "WORKING_HOURS_STATUS.ATTR_ID = 52 AND "
          + "PM_REF.ATTR_ID = 54 AND "
          + "PM_REF.OBJECT_ID = WORKING_DAY_DATE.OBJECT_ID AND "
          + "WORKING_HOURS_STATUS_VALUE.ATTR_ID = "
          + "WORKING_HOURS_STATUS.ATTR_ID AND "
          + "WORKING_HOURS_STATUS.LIST_VALUE_ID = "
          + "WORKING_HOURS_STATUS_VALUE.LIST_VALUE_ID";

  String FIND_WORKING_DAY_BY_USER_ID_AND_DATE = "SELECT WD_ID.OBJECT_ID AS WORKING_DAY_ID, USER_ID.OBJECT_ID AS USER_ID, PM_ID.OBJECT_ID AS PM_ID, "
      + "WORKING_DAY_DATE.DATE_VALUE AS WORKING_DATE, WORKING_DAY_WEEK_NUMBER.VALUE AS WEEK_NUMBER, WORKING_HOURS_COUNT.VALUE AS WORKING_HOURS, WORKING_HOURS_STATUS_VALUE.VALUE AS STATUS "
      + "FROM OBJECTS WD_ID, OBJECTS USER_ID, OBJECTS PM_ID, "
      + "ATTRIBUTES WORKING_DAY_DATE, ATTRIBUTES WORKING_DAY_WEEK_NUMBER, ATTRIBUTES WORKING_HOURS_COUNT, ATTRIBUTES WORKING_HOURS_STATUS, "
      + "OBJREFERENCE WD_USER_REF, OBJREFERENCE WD_PM_REF, "
      + "LISTVALUE WORKING_HOURS_STATUS_VALUE "
      + "WHERE WD_USER_REF.ATTR_ID = 53 AND "
      + "USER_ID.OBJECT_ID = ? AND "
      + "WD_USER_REF.REFERENCE = USER_ID.OBJECT_ID AND  "
      + "WD_ID.OBJECT_ID = WD_USER_REF.OBJECT_ID AND "
      + "WD_PM_REF.ATTR_ID = 54 AND "
      + "PM_ID.OBJECT_ID = WD_PM_REF.REFERENCE AND "
      + "WD_PM_REF.OBJECT_ID = WD_ID.OBJECT_ID AND "
      + "WORKING_DAY_DATE.ATTR_ID = 49 AND "
      + "WORKING_DAY_DATE.OBJECT_ID = WD_ID.OBJECT_ID AND "
      + "WORKING_DAY_DATE.DATE_VALUE = ? AND "
      + "WORKING_DAY_WEEK_NUMBER.ATTR_ID = 50 AND "
      + "WORKING_DAY_WEEK_NUMBER.OBJECT_ID = WD_ID.OBJECT_ID AND "
      + "WORKING_HOURS_COUNT.ATTR_ID = 51 AND "
      + "WORKING_HOURS_COUNT.OBJECT_ID = WD_ID.OBJECT_ID AND "
      + "WORKING_HOURS_STATUS.ATTR_ID = 52 AND "
      + "WORKING_HOURS_STATUS.OBJECT_ID = WD_ID.OBJECT_ID AND "
      + "WORKING_HOURS_STATUS_VALUE.ATTR_ID = WORKING_HOURS_STATUS.ATTR_ID AND "
      + "WORKING_HOURS_STATUS_VALUE.LIST_VALUE_ID = WORKING_HOURS_STATUS.LIST_VALUE_ID";

  String FIND_WORKING_DAYS_BY_PM_ID_AND_STATUS = "SELECT WD_ID.OBJECT_ID AS WORKING_DAY_ID, USER_ID.OBJECT_ID AS USER_ID, PM_ID.OBJECT_ID AS PM_ID, "
      + "WORKING_DAY_DATE.DATE_VALUE AS WORKING_DATE, WORKING_DAY_WEEK_NUMBER.VALUE AS WEEK_NUMBER, WORKING_HOURS_COUNT.VALUE AS WORKING_HOURS, WORKING_HOURS_STATUS_VALUE.VALUE AS STATUS "
      + "FROM OBJECTS WD_ID, OBJECTS USER_ID, OBJECTS PM_ID, "
      + "ATTRIBUTES WORKING_DAY_DATE, ATTRIBUTES WORKING_DAY_WEEK_NUMBER, ATTRIBUTES WORKING_HOURS_COUNT, ATTRIBUTES WORKING_HOURS_STATUS, "
      + "OBJREFERENCE WD_USER_REF, OBJREFERENCE WD_PM_REF, "
      + "LISTVALUE WORKING_HOURS_STATUS_VALUE "
      + "WHERE WD_USER_REF.ATTR_ID = 53 AND "
      + "WD_USER_REF.REFERENCE = USER_ID.OBJECT_ID AND "
      + "WD_ID.OBJECT_ID = WD_USER_REF.OBJECT_ID AND "
      + "WD_PM_REF.ATTR_ID = 54 AND "
      + "PM_ID.OBJECT_ID = ? AND "
      + "WD_PM_REF.REFERENCE = PM_ID.OBJECT_ID AND "
      + "WD_PM_REF.OBJECT_ID = WD_ID.OBJECT_ID AND "
      + "WORKING_DAY_DATE.ATTR_ID = 49 AND "
      + "WORKING_DAY_DATE.OBJECT_ID = WD_ID.OBJECT_ID AND "
      + "WORKING_DAY_WEEK_NUMBER.ATTR_ID = 50 AND "
      + "WORKING_DAY_WEEK_NUMBER.OBJECT_ID = WD_ID.OBJECT_ID AND "
      + "WORKING_HOURS_COUNT.ATTR_ID = 51 AND "
      + "WORKING_HOURS_COUNT.OBJECT_ID = WD_ID.OBJECT_ID AND "
      + "WORKING_HOURS_STATUS.ATTR_ID = 52 AND "
      + "WORKING_HOURS_STATUS.OBJECT_ID = WD_ID.OBJECT_ID AND "
      + "WORKING_HOURS_STATUS.LIST_VALUE_ID = ? AND "
      + "WORKING_HOURS_STATUS_VALUE.ATTR_ID = WORKING_HOURS_STATUS.ATTR_ID AND "
      + "WORKING_HOURS_STATUS_VALUE.LIST_VALUE_ID = WORKING_HOURS_STATUS.LIST_VALUE_ID";

  String UPDATE_WORKING_HOURS = "UPDATE ATTRIBUTES "
      + "SET VALUE = "
      + "(SELECT TO_NUMBER(VALUE) "
      + "FROM ATTRIBUTES "
      + "WHERE OBJECT_ID = ? AND "
      + "ATTR_ID = 51) + ? "
      + "WHERE OBJECT_ID = ?"
      + "AND ATTR_ID = 51";

  String UPDATE_WORKING_DAY_STATUS = "UPDATE ATTRIBUTES "
      + "SET LIST_VALUE_ID = ? "
      + "WHERE OBJECT_ID = ? AND "
      + "ATTR_ID = 52";

  String FIND_WORKING_DAY_IF_EXIST = "SELECT COUNT(WD_ID.OBJECT_ID) "
      + " FROM OBJECTS WD_ID, OBJECTS USER_ID, OBJECTS PM_ID, "
      + " ATTRIBUTES WORKING_DAY_DATE, ATTRIBUTES WORKING_DAY_WEEK_NUMBER, ATTRIBUTES WORKING_HOURS_COUNT, ATTRIBUTES WORKING_HOURS_STATUS, "
      + " OBJREFERENCE WD_USER_REF, OBJREFERENCE WD_PM_REF, "
      + " LISTVALUE WORKING_HOURS_STATUS_VALUE "
      + " WHERE WD_USER_REF.ATTR_ID = 53 AND "
      + " USER_ID.OBJECT_ID = ? AND "
      + " WD_USER_REF.REFERENCE = USER_ID.OBJECT_ID AND "
      + " WD_ID.OBJECT_ID = WD_USER_REF.OBJECT_ID AND "
      + " WD_PM_REF.ATTR_ID = 54 AND "
      + " PM_ID.OBJECT_ID = WD_PM_REF.REFERENCE AND "
      + " WD_PM_REF.OBJECT_ID = WD_ID.OBJECT_ID AND "
      + " WORKING_DAY_DATE.ATTR_ID = 49 AND "
      + " WORKING_DAY_DATE.OBJECT_ID = WD_ID.OBJECT_ID AND "
      + " WORKING_DAY_DATE.DATE_VALUE = ? AND "
      + " WORKING_DAY_WEEK_NUMBER.ATTR_ID = 50 AND "
      + " WORKING_DAY_WEEK_NUMBER.OBJECT_ID = WD_ID.OBJECT_ID AND "
      + " WORKING_HOURS_COUNT.ATTR_ID = 51 AND "
      + " WORKING_HOURS_COUNT.OBJECT_ID = WD_ID.OBJECT_ID AND "
      + " WORKING_HOURS_STATUS.ATTR_ID = 52 AND "
      + " WORKING_HOURS_STATUS.OBJECT_ID = WD_ID.OBJECT_ID AND "
      + " WORKING_HOURS_STATUS_VALUE.ATTR_ID = WORKING_HOURS_STATUS.ATTR_ID AND "
      + " WORKING_HOURS_STATUS_VALUE.LIST_VALUE_ID = WORKING_HOURS_STATUS.LIST_VALUE_ID";
}
