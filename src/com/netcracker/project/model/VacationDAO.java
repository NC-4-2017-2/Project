package com.netcracker.project.model;

import com.netcracker.project.model.entity.Vacation;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

public interface VacationDAO {

  void createVacation(Vacation vacation);

  void updateVacation(Vacation vacation);

  void updateVacationStartAndEndDate(BigInteger vacationId, Date startDate, Date endDate);

  Collection<Vacation> findVacationByUserId(BigInteger userId);

  Collection<Vacation> findVacationByProjectId(BigInteger projectId);

  Collection<Vacation> findVacationByUserIdAndPmStatus(BigInteger userId,
      Integer status);

  Collection<Vacation> findVacationByUserIdAndLmStatus(BigInteger userId,
      Integer status);

  Collection<Vacation> findVacationByUserIdAndPmAndLmStatus(BigInteger userId,
      Integer pmStatus, Integer lmStatus);

  Collection<Vacation> findVacationByPMIdAndPMStatus(BigInteger pmId,
      Integer pmStatus);

  Collection<Vacation> findVacationByLMIdAndLMStatus(BigInteger lmId,
      Integer lmStatus);

  Vacation findVacationByVacationId(BigInteger vacationId);

  Integer findVacationIfExist(BigInteger vacationId);

  String CREATE_VACATION = "INSERT ALL "
      + "INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION,OBJECT_VERSION)"
      + "VALUES (OBJECT_SEQUENCE.NEXTVAL,NULL,5,'Vacation' || OBJECT_SEQUENCE.CURRVAL,NULL,1)  "
      + "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (41,OBJECT_SEQUENCE.CURRVAL,NULL,? ,NULL)  "
      + "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (42,OBJECT_SEQUENCE.CURRVAL,NULL,? ,NULL)  "
      + "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (43,OBJECT_SEQUENCE.CURRVAL,NULL,NULL,?)  "
      + "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (44,OBJECT_SEQUENCE.CURRVAL,NULL,NULL,?)  "
      + "INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (45,OBJECT_SEQUENCE.CURRVAL,?)  "
      + "INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (46,OBJECT_SEQUENCE.CURRVAL,?)  "
      + "INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (47,OBJECT_SEQUENCE.CURRVAL,?)  "
      + "INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (48,OBJECT_SEQUENCE.CURRVAL,?)  "
      + "select * from dual";

  String UPDATE_START_DATE = "UPDATE ATTRIBUTES "
      + "SET ATTRIBUTES.DATE_VALUE = ? "
      + "WHERE ATTRIBUTES.OBJECT_ID = ? AND "
      + "ATTRIBUTES.ATTR_ID = 41";

  String UPDATE_END_DATE = "UPDATE ATTRIBUTES "
      + "SET ATTRIBUTES.DATE_VALUE = ? "
      + "WHERE ATTRIBUTES.OBJECT_ID = ? AND "
      + "ATTRIBUTES.ATTR_ID = 42";

  String UPDATE_PM_STATUS = "UPDATE ATTRIBUTES "
      + "SET ATTRIBUTES.LIST_VALUE_ID = ? "
      + "WHERE ATTRIBUTES.OBJECT_ID = ? AND "
      + "ATTRIBUTES.ATTR_ID = 43";

  String UPDATE_LM_STATUS = "UPDATE ATTRIBUTES "
      + "SET ATTRIBUTES.LIST_VALUE_ID = ? "
      + "WHERE ATTRIBUTES.OBJECT_ID = ? AND "
      + "ATTRIBUTES.ATTR_ID = 44";

  String UPDATE_PROJECT_ID = "UPDATE OBJREFERENCE "
      + "SET OBJREFERENCE.REFERENCE = ? "
      + "WHERE OBJREFERENCE.OBJECT_ID = ? AND "
      + "OBJREFERENCE.ATTR_ID = 46";

  String UPDATE_USER_ID = "UPDATE OBJREFERENCE "
      + "SET OBJREFERENCE.REFERENCE = ? "
      + "WHERE OBJREFERENCE.OBJECT_ID = ? AND "
      + "OBJREFERENCE.ATTR_ID = 45";

  String FIND_VACATION_BY_USER_ID =
      "SELECT VACATION_ID.OBJECT_ID AS VACATION_ID, USER_ID.OBJECT_ID AS USER_ID, "
          + "PROJECT_ID.OBJECT_ID AS PROJECT_ID, "
          + "PM_ID.OBJECT_ID AS PM_ID, LM_ID.OBJECT_ID AS LM_ID, "
          + "START_DATE.DATE_VALUE AS START_DATE, END_DATE.DATE_VALUE AS END_DATE, "
          + "PM_STATUS_VALUE.VALUE AS PM_STATUS, LM_STATUS_VALUE.VALUE AS LM_STATUS "
          + "FROM OBJECTS VACATION_ID, OBJECTS USER_ID, OBJECTS PROJECT_ID, "
          + "OBJECTS PM_ID, OBJECTS LM_ID, "
          + "ATTRIBUTES START_DATE, ATTRIBUTES END_DATE, ATTRIBUTES PM_APPROVE, "
          + "ATTRIBUTES LM_APPROVE, "
          + "OBJREFERENCE USER_VACATION_REF, OBJREFERENCE PROJECT_VACATION_REF, "
          + "OBJREFERENCE PM_ID_PROJECT_APPROVE, OBJREFERENCE LM_ID_PROJECT_APPROVE, "
          + "LISTVALUE PM_STATUS_VALUE, LISTVALUE LM_STATUS_VALUE "
          + "WHERE USER_VACATION_REF.ATTR_ID = 45 AND "
          + "USER_ID.OBJECT_ID = ? AND "
          + "VACATION_ID.OBJECT_ID = USER_VACATION_REF.OBJECT_ID AND "
          + "PROJECT_VACATION_REF.ATTR_ID = 46 AND "
          + "PROJECT_VACATION_REF.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PROJECT_ID.OBJECT_ID = PROJECT_VACATION_REF.REFERENCE AND "
          + "PM_ID_PROJECT_APPROVE.ATTR_ID = 47 AND "
          + "PM_ID_PROJECT_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PM_ID.OBJECT_ID = PM_ID_PROJECT_APPROVE.REFERENCE AND "
          + "LM_ID_PROJECT_APPROVE.ATTR_ID = 48 AND "
          + "LM_ID_PROJECT_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "LM_ID.OBJECT_ID = LM_ID_PROJECT_APPROVE.REFERENCE AND "
          + "START_DATE.ATTR_ID = 41 AND "
          + "START_DATE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "END_DATE.ATTR_ID = 42 AND "
          + "END_DATE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PM_APPROVE.ATTR_ID = 43 AND "
          + "PM_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "LM_APPROVE.ATTR_ID = 44 AND "
          + "LM_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PM_STATUS_VALUE.ATTR_ID = PM_APPROVE.ATTR_ID AND "
          + "PM_STATUS_VALUE.LIST_VALUE_ID = PM_APPROVE.LIST_VALUE_ID AND "
          + "LM_STATUS_VALUE.ATTR_ID = LM_APPROVE.ATTR_ID AND "
          + "LM_STATUS_VALUE.LIST_VALUE_ID = LM_APPROVE.LIST_VALUE_ID";

  String FIND_VACATION_BY_PROJECT_ID =
      "SELECT VACATION_ID.OBJECT_ID AS VACATION_ID, USER_ID.OBJECT_ID AS USER_ID, "
          + "PROJECT_ID.OBJECT_ID AS PROJECT_ID, "
          + "PM_ID.OBJECT_ID AS PM_ID, LM_ID.OBJECT_ID AS LM_ID, "
          + "START_DATE.DATE_VALUE AS START_DATE, END_DATE.DATE_VALUE AS END_DATE, "
          + "PM_STATUS_VALUE.VALUE AS PM_STATUS, LM_STATUS_VALUE.VALUE AS LM_STATUS "
          + "FROM OBJECTS VACATION_ID, OBJECTS USER_ID, OBJECTS PROJECT_ID, "
          + "OBJECTS PM_ID, OBJECTS LM_ID, "
          + "ATTRIBUTES START_DATE, ATTRIBUTES END_DATE, ATTRIBUTES PM_APPROVE, "
          + "ATTRIBUTES LM_APPROVE, "
          + "OBJREFERENCE USER_VACATION_REF, OBJREFERENCE PROJECT_VACATION_REF, "
          + "OBJREFERENCE PM_ID_PROJECT_APPROVE, OBJREFERENCE LM_ID_PROJECT_APPROVE, "
          + "LISTVALUE PM_STATUS_VALUE, LISTVALUE LM_STATUS_VALUE "
          + "WHERE "
          + "PROJECT_VACATION_REF.ATTR_ID = 46 AND "
          + "PROJECT_ID.OBJECT_ID = ? AND "
          + "USER_ID.OBJECT_ID = USER_VACATION_REF.REFERENCE AND "
          + "PROJECT_VACATION_REF.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PROJECT_ID.OBJECT_ID = PROJECT_VACATION_REF.REFERENCE AND "
          + "USER_VACATION_REF.ATTR_ID = 45 AND "
          + "VACATION_ID.OBJECT_ID = USER_VACATION_REF.OBJECT_ID AND "
          + "PM_ID_PROJECT_APPROVE.ATTR_ID = 47 AND "
          + "PM_ID_PROJECT_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PM_ID.OBJECT_ID = PM_ID_PROJECT_APPROVE.REFERENCE AND "
          + "LM_ID_PROJECT_APPROVE.ATTR_ID = 48 AND "
          + "LM_ID_PROJECT_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "LM_ID.OBJECT_ID = LM_ID_PROJECT_APPROVE.REFERENCE AND "
          + "START_DATE.ATTR_ID = 41 AND "
          + "START_DATE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "END_DATE.ATTR_ID = 42 AND "
          + "END_DATE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PM_APPROVE.ATTR_ID = 43 AND "
          + "PM_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "LM_APPROVE.ATTR_ID = 44 AND "
          + "LM_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PM_STATUS_VALUE.ATTR_ID = PM_APPROVE.ATTR_ID AND "
          + "PM_STATUS_VALUE.LIST_VALUE_ID = PM_APPROVE.LIST_VALUE_ID AND "
          + "LM_STATUS_VALUE.ATTR_ID = LM_APPROVE.ATTR_ID AND "
          + "LM_STATUS_VALUE.LIST_VALUE_ID = LM_APPROVE.LIST_VALUE_ID";

  String FIND_VACATION_BY_PM_ID_AND_STATUS =
      "SELECT VACATION_ID.OBJECT_ID AS VACATION_ID, USER_ID.OBJECT_ID AS USER_ID, "
          + "PROJECT_ID.OBJECT_ID AS PROJECT_ID, "
          + "PM_ID.OBJECT_ID AS PM_ID, LM_ID.OBJECT_ID AS LM_ID, "
          + "START_DATE.DATE_VALUE AS START_DATE, END_DATE.DATE_VALUE AS END_DATE, "
          + "PM_STATUS_VALUE.VALUE AS PM_STATUS, LM_STATUS_VALUE.VALUE AS LM_STATUS "
          + "FROM OBJECTS VACATION_ID, OBJECTS USER_ID, OBJECTS PROJECT_ID, "
          + "OBJECTS PM_ID, OBJECTS LM_ID, "
          + "ATTRIBUTES START_DATE, ATTRIBUTES END_DATE, ATTRIBUTES PM_APPROVE, ATTRIBUTES LM_APPROVE, "
          + "OBJREFERENCE USER_VACATION_REF, OBJREFERENCE PROJECT_VACATION_REF, "
          + "OBJREFERENCE PM_ID_PROJECT_APPROVE, OBJREFERENCE LM_ID_PROJECT_APPROVE, "
          + "LISTVALUE PM_STATUS_VALUE, LISTVALUE LM_STATUS_VALUE "
          + "WHERE USER_VACATION_REF.ATTR_ID = 45 AND "
          + "VACATION_ID.OBJECT_ID = USER_VACATION_REF.OBJECT_ID AND "
          + "USER_ID.OBJECT_ID = USER_VACATION_REF.REFERENCE AND "
          + "PROJECT_VACATION_REF.ATTR_ID = 46 AND "
          + "PROJECT_VACATION_REF.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PROJECT_ID.OBJECT_ID = PROJECT_VACATION_REF.REFERENCE AND "
          + "PM_ID_PROJECT_APPROVE.ATTR_ID = 47 AND "
          + "PM_ID_PROJECT_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PM_ID.OBJECT_ID = ? AND "
          + "PM_ID.OBJECT_ID = PM_ID_PROJECT_APPROVE.REFERENCE AND "
          + "LM_ID_PROJECT_APPROVE.ATTR_ID = 48 AND "
          + "LM_ID_PROJECT_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "LM_ID.OBJECT_ID = LM_ID_PROJECT_APPROVE.REFERENCE AND "
          + "START_DATE.ATTR_ID = 41 AND "
          + "START_DATE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "END_DATE.ATTR_ID = 42 AND "
          + "END_DATE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PM_APPROVE.ATTR_ID = 43 AND "
          + "PM_APPROVE.LIST_VALUE_ID = ? AND "
          + "PM_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "LM_APPROVE.ATTR_ID = 44 AND "
          + "LM_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PM_STATUS_VALUE.ATTR_ID = PM_APPROVE.ATTR_ID AND "
          + "PM_STATUS_VALUE.LIST_VALUE_ID = PM_APPROVE.LIST_VALUE_ID AND "
          + "LM_STATUS_VALUE.ATTR_ID = LM_APPROVE.ATTR_ID AND "
          + "LM_STATUS_VALUE.LIST_VALUE_ID = LM_APPROVE.LIST_VALUE_ID";

  String FIND_VACATION_BY_LM_ID_AND_STATUS =
      "SELECT VACATION_ID.OBJECT_ID AS VACATION_ID, USER_ID.OBJECT_ID AS USER_ID, "
          + "PROJECT_ID.OBJECT_ID AS PROJECT_ID, "
          + "PM_ID.OBJECT_ID AS PM_ID, LM_ID.OBJECT_ID AS LM_ID, "
          + "START_DATE.DATE_VALUE AS START_DATE, END_DATE.DATE_VALUE AS END_DATE, "
          + "PM_STATUS_VALUE.VALUE AS PM_STATUS, LM_STATUS_VALUE.VALUE AS LM_STATUS "
          + "FROM OBJECTS VACATION_ID, OBJECTS USER_ID, OBJECTS PROJECT_ID, "
          + "OBJECTS PM_ID, OBJECTS LM_ID, "
          + "ATTRIBUTES START_DATE, ATTRIBUTES END_DATE, ATTRIBUTES PM_APPROVE, ATTRIBUTES LM_APPROVE, "
          + "OBJREFERENCE USER_VACATION_REF, OBJREFERENCE PROJECT_VACATION_REF, "
          + "OBJREFERENCE PM_ID_PROJECT_APPROVE, OBJREFERENCE LM_ID_PROJECT_APPROVE, "
          + "LISTVALUE PM_STATUS_VALUE, LISTVALUE LM_STATUS_VALUE "
          + "WHERE USER_VACATION_REF.ATTR_ID = 45 AND "
          + "VACATION_ID.OBJECT_ID = USER_VACATION_REF.OBJECT_ID AND "
          + "USER_ID.OBJECT_ID = USER_VACATION_REF.REFERENCE AND "
          + "PROJECT_VACATION_REF.ATTR_ID = 46 AND "
          + "PROJECT_VACATION_REF.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PROJECT_ID.OBJECT_ID = PROJECT_VACATION_REF.REFERENCE AND "
          + "PM_ID_PROJECT_APPROVE.ATTR_ID = 47 AND "
          + "PM_ID_PROJECT_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PM_ID.OBJECT_ID = PM_ID_PROJECT_APPROVE.REFERENCE AND "
          + "LM_ID_PROJECT_APPROVE.ATTR_ID = 48 AND "
          + "LM_ID_PROJECT_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "LM_ID.OBJECT_ID = ? AND "
          + "LM_ID.OBJECT_ID = LM_ID_PROJECT_APPROVE.REFERENCE AND "
          + "START_DATE.ATTR_ID = 41 AND "
          + "START_DATE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "END_DATE.ATTR_ID = 42 AND "
          + "END_DATE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PM_APPROVE.ATTR_ID = 43 AND "
          + "PM_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "LM_APPROVE.ATTR_ID = 44 AND "
          + "LM_APPROVE.LIST_VALUE_ID = ? AND "
          + "LM_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PM_STATUS_VALUE.ATTR_ID = PM_APPROVE.ATTR_ID AND "
          + "PM_STATUS_VALUE.LIST_VALUE_ID = PM_APPROVE.LIST_VALUE_ID AND "
          + "LM_STATUS_VALUE.ATTR_ID = LM_APPROVE.ATTR_ID AND "
          + "LM_STATUS_VALUE.LIST_VALUE_ID = LM_APPROVE.LIST_VALUE_ID";

  String FIND_VACATION_BY_USER_ID_AND_PM_STATUS =
      "SELECT VACATION_ID.OBJECT_ID AS VACATION_ID, USER_ID.OBJECT_ID AS USER_ID, "
          + "PROJECT_ID.OBJECT_ID AS PROJECT_ID, "
          + "PM_ID.OBJECT_ID AS PM_ID, LM_ID.OBJECT_ID AS LM_ID, START_DATE.DATE_VALUE AS START_DATE, "
          + "END_DATE.DATE_VALUE AS END_DATE, PM_STATUS_VALUE.VALUE AS PM_STATUS, "
          + "LM_STATUS_VALUE.VALUE AS LM_STATUS "
          + "FROM OBJECTS VACATION_ID, OBJECTS USER_ID, OBJECTS PROJECT_ID, "
          + "OBJECTS PM_ID, OBJECTS LM_ID, "
          + "ATTRIBUTES START_DATE, ATTRIBUTES END_DATE, ATTRIBUTES PM_APPROVE, "
          + "ATTRIBUTES LM_APPROVE, "
          + "OBJREFERENCE USER_VACATION_REF, OBJREFERENCE PROJECT_VACATION_REF, "
          + "OBJREFERENCE PM_ID_PROJECT_APPROVE, OBJREFERENCE LM_ID_PROJECT_APPROVE, "
          + "LISTVALUE PM_STATUS_VALUE, LISTVALUE LM_STATUS_VALUE "
          + "WHERE USER_VACATION_REF.ATTR_ID = 45 AND "
          + "USER_ID.OBJECT_ID = ? AND "
          + "USER_VACATION_REF.REFERENCE = USER_ID.OBJECT_ID AND "
          + "VACATION_ID.OBJECT_ID = USER_VACATION_REF.OBJECT_ID AND "
          + "PROJECT_VACATION_REF.ATTR_ID = 46 AND "
          + "PROJECT_VACATION_REF.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PROJECT_ID.OBJECT_ID = PROJECT_VACATION_REF.REFERENCE AND "
          + "PM_ID_PROJECT_APPROVE.ATTR_ID = 47 AND "
          + "PM_ID_PROJECT_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PM_ID.OBJECT_ID = PM_ID_PROJECT_APPROVE.REFERENCE AND "
          + "LM_ID_PROJECT_APPROVE.ATTR_ID = 48 AND "
          + "LM_ID_PROJECT_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "LM_ID.OBJECT_ID = LM_ID_PROJECT_APPROVE.REFERENCE AND "
          + "START_DATE.ATTR_ID = 41 AND "
          + "START_DATE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "END_DATE.ATTR_ID = 42 AND "
          + "END_DATE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PM_APPROVE.ATTR_ID = 43 AND "
          + "PM_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "LM_APPROVE.ATTR_ID = 44 AND "
          + "LM_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PM_STATUS_VALUE.ATTR_ID = PM_APPROVE.ATTR_ID AND "
          + "PM_STATUS_VALUE.LIST_VALUE_ID = ? AND "
          + "PM_STATUS_VALUE.LIST_VALUE_ID = PM_APPROVE.LIST_VALUE_ID AND "
          + "LM_STATUS_VALUE.ATTR_ID = LM_APPROVE.ATTR_ID AND "
          + "LM_STATUS_VALUE.LIST_VALUE_ID = LM_APPROVE.LIST_VALUE_ID";

  String FIND_VACATION_BY_USER_ID_AND_LM_STATUS =
      "SELECT VACATION_ID.OBJECT_ID AS VACATION_ID, USER_ID.OBJECT_ID AS USER_ID, "
          + "PROJECT_ID.OBJECT_ID AS PROJECT_ID, "
          + "PM_ID.OBJECT_ID AS PM_ID, LM_ID.OBJECT_ID AS LM_ID, "
          + "START_DATE.DATE_VALUE AS START_DATE, "
          + "END_DATE.DATE_VALUE AS END_DATE, PM_STATUS_VALUE.VALUE AS PM_STATUS, "
          + "LM_STATUS_VALUE.VALUE AS LM_STATUS "
          + "FROM OBJECTS VACATION_ID, OBJECTS USER_ID, OBJECTS PROJECT_ID, "
          + "OBJECTS PM_ID, OBJECTS LM_ID, "
          + "ATTRIBUTES START_DATE, ATTRIBUTES END_DATE, ATTRIBUTES PM_APPROVE, "
          + "ATTRIBUTES LM_APPROVE, "
          + "OBJREFERENCE USER_VACATION_REF, OBJREFERENCE PROJECT_VACATION_REF, "
          + "OBJREFERENCE PM_ID_PROJECT_APPROVE, OBJREFERENCE LM_ID_PROJECT_APPROVE, "
          + "LISTVALUE PM_STATUS_VALUE, LISTVALUE LM_STATUS_VALUE "
          + "WHERE USER_VACATION_REF.ATTR_ID = 45 AND "
          + "USER_ID.OBJECT_ID = ? AND "
          + "USER_VACATION_REF.REFERENCE = USER_ID.OBJECT_ID AND "
          + "VACATION_ID.OBJECT_ID = USER_VACATION_REF.OBJECT_ID AND "
          + "PROJECT_VACATION_REF.ATTR_ID = 46 AND "
          + "PROJECT_VACATION_REF.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PROJECT_ID.OBJECT_ID = PROJECT_VACATION_REF.REFERENCE AND "
          + "PM_ID_PROJECT_APPROVE.ATTR_ID = 47 AND "
          + "PM_ID_PROJECT_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PM_ID.OBJECT_ID = PM_ID_PROJECT_APPROVE.REFERENCE AND "
          + "LM_ID_PROJECT_APPROVE.ATTR_ID = 48 AND "
          + "LM_ID_PROJECT_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "LM_ID.OBJECT_ID = LM_ID_PROJECT_APPROVE.REFERENCE AND "
          + "START_DATE.ATTR_ID = 41 AND "
          + "START_DATE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "END_DATE.ATTR_ID = 42 AND "
          + "END_DATE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PM_APPROVE.ATTR_ID = 43 AND "
          + "PM_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "LM_APPROVE.ATTR_ID = 44 AND "
          + "LM_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PM_STATUS_VALUE.ATTR_ID = PM_APPROVE.ATTR_ID AND "
          + "PM_STATUS_VALUE.LIST_VALUE_ID = PM_APPROVE.LIST_VALUE_ID AND "
          + "LM_STATUS_VALUE.ATTR_ID = LM_APPROVE.ATTR_ID AND "
          + "LM_STATUS_VALUE.LIST_VALUE_ID = ? AND "
          + "LM_STATUS_VALUE.LIST_VALUE_ID = LM_APPROVE.LIST_VALUE_ID";

  String FIND_VACATION_BY_USER_ID_AND_PM_AND_LM_STATUS = "SELECT "
      + "  VACATION_ID.OBJECT_ID AS VACATION_ID, "
      + "  USER_ID.OBJECT_ID     AS USER_ID, "
      + "  PROJECT_ID.OBJECT_ID  AS PROJECT_ID, "
      + "  PM_ID.OBJECT_ID       AS PM_ID, "
      + "  LM_ID.OBJECT_ID       AS LM_ID, "
      + "  START_DATE.DATE_VALUE AS START_DATE, "
      + "  END_DATE.DATE_VALUE   AS END_DATE, "
      + "  PM_STATUS_VALUE.VALUE AS PM_STATUS, "
      + "  LM_STATUS_VALUE.VALUE AS LM_STATUS "
      + "FROM OBJECTS VACATION_ID, OBJECTS USER_ID, OBJECTS PROJECT_ID, "
      + "  OBJECTS PM_ID, OBJECTS LM_ID, "
      + "  ATTRIBUTES START_DATE, ATTRIBUTES END_DATE, ATTRIBUTES PM_APPROVE, "
      + "  ATTRIBUTES LM_APPROVE, "
      + "  OBJREFERENCE USER_VACATION_REF, OBJREFERENCE PROJECT_VACATION_REF, "
      + "  OBJREFERENCE PM_ID_PROJECT_APPROVE, OBJREFERENCE LM_ID_PROJECT_APPROVE, "
      + "  LISTVALUE PM_STATUS_VALUE, LISTVALUE LM_STATUS_VALUE "
      + "WHERE USER_VACATION_REF.ATTR_ID = 45 AND "
      + "      USER_ID.OBJECT_ID = ? AND "
      + "      USER_VACATION_REF.REFERENCE = USER_ID.OBJECT_ID AND "
      + "      VACATION_ID.OBJECT_ID = USER_VACATION_REF.OBJECT_ID AND "
      + "      PROJECT_VACATION_REF.ATTR_ID = 46 AND "
      + "      PROJECT_VACATION_REF.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
      + "      PROJECT_ID.OBJECT_ID = PROJECT_VACATION_REF.REFERENCE AND "
      + "      PM_ID_PROJECT_APPROVE.ATTR_ID = 47 AND "
      + "      PM_ID_PROJECT_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
      + "      PM_ID.OBJECT_ID = PM_ID_PROJECT_APPROVE.REFERENCE AND "
      + "      LM_ID_PROJECT_APPROVE.ATTR_ID = 48 AND "
      + "      LM_ID_PROJECT_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
      + "      LM_ID.OBJECT_ID = LM_ID_PROJECT_APPROVE.REFERENCE AND "
      + "      START_DATE.ATTR_ID = 41 AND "
      + "      START_DATE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
      + "      END_DATE.ATTR_ID = 42 AND "
      + "      END_DATE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
      + "      PM_APPROVE.ATTR_ID = 43 AND "
      + "      PM_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
      + "      LM_APPROVE.ATTR_ID = 44 AND "
      + "      LM_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
      + "      PM_STATUS_VALUE.ATTR_ID = PM_APPROVE.ATTR_ID AND "
      + "      PM_STATUS_VALUE.LIST_VALUE_ID = ? AND "
      + "      PM_STATUS_VALUE.LIST_VALUE_ID = PM_APPROVE.LIST_VALUE_ID AND "
      + "      LM_STATUS_VALUE.ATTR_ID = LM_APPROVE.ATTR_ID AND "
      + "      LM_STATUS_VALUE.LIST_VALUE_ID = ? AND "
      + "      LM_STATUS_VALUE.LIST_VALUE_ID = LM_APPROVE.LIST_VALUE_ID";

  String FIND_VACATION_BY_ID =
      "SELECT VACATION_ID.OBJECT_ID AS VACATION_ID, USER_ID.OBJECT_ID AS USER_ID,  "
          + "PROJECT_ID.OBJECT_ID AS PROJECT_ID,  "
          + "PM_ID.OBJECT_ID AS PM_ID, LM_ID.OBJECT_ID AS LM_ID,  "
          + "START_DATE.DATE_VALUE AS START_DATE, END_DATE.DATE_VALUE AS END_DATE,  "
          + "PM_STATUS_VALUE.VALUE AS PM_STATUS, LM_STATUS_VALUE.VALUE AS LM_STATUS  "
          + "FROM OBJECTS VACATION_ID, OBJECTS USER_ID, OBJECTS PROJECT_ID,  "
          + "OBJECTS PM_ID, OBJECTS LM_ID,  "
          + "ATTRIBUTES START_DATE, ATTRIBUTES END_DATE, ATTRIBUTES PM_APPROVE,  "
          + "ATTRIBUTES LM_APPROVE,  "
          + "OBJREFERENCE USER_VACATION_REF, OBJREFERENCE PROJECT_VACATION_REF,  "
          + "OBJREFERENCE PM_ID_PROJECT_APPROVE, OBJREFERENCE LM_ID_PROJECT_APPROVE,  "
          + "LISTVALUE PM_STATUS_VALUE, LISTVALUE LM_STATUS_VALUE  "
          + "WHERE USER_VACATION_REF.ATTR_ID = 45 AND  "
          + "VACATION_ID.OBJECT_ID  = ? AND "
          + "USER_ID.OBJECT_ID = USER_VACATION_REF.REFERENCE AND  "
          + "VACATION_ID.OBJECT_ID = USER_VACATION_REF.OBJECT_ID AND  "
          + "PROJECT_VACATION_REF.ATTR_ID = 46 AND  "
          + "PROJECT_VACATION_REF.OBJECT_ID = VACATION_ID.OBJECT_ID AND  "
          + "PROJECT_ID.OBJECT_ID = PROJECT_VACATION_REF.REFERENCE AND  "
          + "PM_ID_PROJECT_APPROVE.ATTR_ID = 47 AND  "
          + "PM_ID_PROJECT_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND  "
          + "PM_ID.OBJECT_ID = PM_ID_PROJECT_APPROVE.REFERENCE AND  "
          + "LM_ID_PROJECT_APPROVE.ATTR_ID = 48 AND  "
          + "LM_ID_PROJECT_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND  "
          + "LM_ID.OBJECT_ID = LM_ID_PROJECT_APPROVE.REFERENCE AND  "
          + "START_DATE.ATTR_ID = 41 AND  "
          + "START_DATE.OBJECT_ID = VACATION_ID.OBJECT_ID AND  "
          + "END_DATE.ATTR_ID = 42 AND  "
          + "END_DATE.OBJECT_ID = VACATION_ID.OBJECT_ID AND  "
          + "PM_APPROVE.ATTR_ID = 43 AND  "
          + "PM_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND  "
          + "LM_APPROVE.ATTR_ID = 44 AND  "
          + "LM_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND  "
          + "PM_STATUS_VALUE.ATTR_ID = PM_APPROVE.ATTR_ID AND  "
          + "PM_STATUS_VALUE.LIST_VALUE_ID = PM_APPROVE.LIST_VALUE_ID AND  "
          + "LM_STATUS_VALUE.ATTR_ID = LM_APPROVE.ATTR_ID AND  "
          + "LM_STATUS_VALUE.LIST_VALUE_ID = LM_APPROVE.LIST_VALUE_ID";

  String FIND_VACATION_IF_EXIST =
      "SELECT COUNT(VACATION_ID.OBJECT_ID) "
          + "FROM OBJECTS VACATION_ID, OBJECTS USER_ID, OBJECTS PROJECT_ID,  "
          + "OBJECTS PM_ID, OBJECTS LM_ID,  "
          + "ATTRIBUTES START_DATE, ATTRIBUTES END_DATE, ATTRIBUTES PM_APPROVE,  "
          + "ATTRIBUTES LM_APPROVE,  "
          + "OBJREFERENCE USER_VACATION_REF, OBJREFERENCE PROJECT_VACATION_REF,  "
          + "OBJREFERENCE PM_ID_PROJECT_APPROVE, OBJREFERENCE LM_ID_PROJECT_APPROVE,  "
          + "LISTVALUE PM_STATUS_VALUE, LISTVALUE LM_STATUS_VALUE  "
          + "WHERE USER_VACATION_REF.ATTR_ID = 45 AND  "
          + "VACATION_ID.OBJECT_ID  = ? AND "
          + "USER_ID.OBJECT_ID = USER_VACATION_REF.REFERENCE AND  "
          + "VACATION_ID.OBJECT_ID = USER_VACATION_REF.OBJECT_ID AND  "
          + "PROJECT_VACATION_REF.ATTR_ID = 46 AND  "
          + "PROJECT_VACATION_REF.OBJECT_ID = VACATION_ID.OBJECT_ID AND  "
          + "PROJECT_ID.OBJECT_ID = PROJECT_VACATION_REF.REFERENCE AND  "
          + "PM_ID_PROJECT_APPROVE.ATTR_ID = 47 AND  "
          + "PM_ID_PROJECT_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND  "
          + "PM_ID.OBJECT_ID = PM_ID_PROJECT_APPROVE.REFERENCE AND  "
          + "LM_ID_PROJECT_APPROVE.ATTR_ID = 48 AND  "
          + "LM_ID_PROJECT_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND  "
          + "LM_ID.OBJECT_ID = LM_ID_PROJECT_APPROVE.REFERENCE AND  "
          + "START_DATE.ATTR_ID = 41 AND  "
          + "START_DATE.OBJECT_ID = VACATION_ID.OBJECT_ID AND  "
          + "END_DATE.ATTR_ID = 42 AND  "
          + "END_DATE.OBJECT_ID = VACATION_ID.OBJECT_ID AND  "
          + "PM_APPROVE.ATTR_ID = 43 AND  "
          + "PM_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND  "
          + "LM_APPROVE.ATTR_ID = 44 AND  "
          + "LM_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND  "
          + "PM_STATUS_VALUE.ATTR_ID = PM_APPROVE.ATTR_ID AND  "
          + "PM_STATUS_VALUE.LIST_VALUE_ID = PM_APPROVE.LIST_VALUE_ID AND  "
          + "LM_STATUS_VALUE.ATTR_ID = LM_APPROVE.ATTR_ID AND  "
          + "LM_STATUS_VALUE.LIST_VALUE_ID = LM_APPROVE.LIST_VALUE_ID";

}
