package com.netcracker.project.model;

import com.netcracker.project.model.entity.WorkingDay;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

public interface WorkingDayDAO {

  void addHoursPerDay(WorkingDay workingDay);

  Collection<WorkingDay> findHoursPerPeriod(BigInteger userId, Date startDate, Date endDate);

  String ADD_HOURS_PER_DAY = "INSERT ALL "
      + "    INTO OBJECTS(OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION, OBJECT_VERSION) "
      + "VALUES (? , NULL, 6, ?, NULL, 1) "
      + "    INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE,DATE_VALUE,LIST_VALUE_ID) "
      + "VALUES (49, ?, NULL, ?, NULL) "
      + "    INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE,DATE_VALUE,LIST_VALUE_ID) "
      + "VALUES (50, ?, ?, NULL, NULL) "
      + "    INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE,DATE_VALUE,LIST_VALUE_ID) "
      + "VALUES (51, ?, ?, NULL, NULL) "
      + "    INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE,DATE_VALUE,LIST_VALUE_ID) "
      + "VALUES (52, ?, NULL, NULL, ?) "
      + "    INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) "
      + "VALUES (53, ?, ?) "
      + "    INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) "
      + "VALUES (54, ?, ?) "
      + "SELECT * FROM DUAL";

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

}
