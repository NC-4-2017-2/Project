package com.netcracker.project.model;

import java.math.BigInteger;
import java.util.Collection;
import com.netcracker.project.model.entity.BusinessTrip;

public interface BusinessTripDAO {

  void createTrip(BusinessTrip trip);

  void updateTrip(BusinessTrip trip);

  BusinessTrip findBusinessTripById(BigInteger id);

  Collection<BusinessTrip> findTripByUserId(BigInteger id);

  Collection<BusinessTrip> findTripByProjectId(BigInteger id);

  Collection<BusinessTrip> findTripByPMIdAndStatus(BigInteger pmId, Integer status);

  String CREATE_TRIP = "INSERT ALL " +
      "INTO OBJECTS(OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION, OBJECT_VERSION) " +
      "VALUES (60, NULL, 4, ?, NULL, 1) " +
      "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE,DATE_VALUE,LIST_VALUE_ID) " +
      "VALUES (33, 60, ?, NULL, NULL) " +
      "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE,DATE_VALUE,LIST_VALUE_ID) " +
      "VALUES (34, 60, NULL, ?, NULL) " +
      "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE,DATE_VALUE,LIST_VALUE_ID) " +
      "VALUES (35, 60, ?, NULL, NULL) " +
      "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE,DATE_VALUE,LIST_VALUE_ID) " +
      "VALUES (36, 60, NULL, NULL, ?) " +
      "INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) VALUES (37, 60, ?) " +
      "INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) VALUES (38, 60, ?) " +
      "INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) VALUES (39, 60, ?) " +
      "INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) VALUES (40, 60, ?) " +
      "SELECT * FROM DUAL";

  String FIND_TRIP_BY_TRIP_ID =
      "SELECT TRIP.OBJECT_ID AS BUSINESS_TRIP_ID, " +
          "PROJECT_ID_REF.REFERENCE AS PROJECT_ID, " +
          "USER_ID_REF.REFERENCE AS USER_ID, " +
          "AUTHOR_ID_REF.REFERENCE AS AUTHOR_ID, " +
          "PM_ID_REF.REFERENCE AS PM_ID, COUNTRY.VALUE AS COUNTRY, " +
          "START_DATE.DATE_VALUE AS START_DATE, " +
          "END_DATE.DATE_VALUE AS END_DATE, STATUS_VALUE.VALUE AS STATUS " +
          "FROM OBJTYPE TRIP_TYPE, OBJECTS TRIP, " +
          "OBJREFERENCE PROJECT_ID_REF, OBJREFERENCE USER_ID_REF, " +
          "OBJREFERENCE AUTHOR_ID_REF, OBJREFERENCE PM_ID_REF, " +
          "ATTRIBUTES COUNTRY, ATTRIBUTES START_DATE, ATTRIBUTES END_DATE, " +
          "ATTRIBUTES STATUS, LISTVALUE STATUS_VALUE " +
          "WHERE TRIP_TYPE.OBJECT_TYPE_ID = 4 AND " +
          "TRIP.OBJECT_TYPE_ID = TRIP_TYPE.OBJECT_TYPE_ID AND " +
          "TRIP.OBJECT_ID = ? AND " +
          "PROJECT_ID_REF.OBJECT_ID = TRIP.OBJECT_ID AND " +
          "PROJECT_ID_REF.ATTR_ID = 39 AND " +
          "USER_ID_REF.OBJECT_ID = TRIP.OBJECT_ID AND " +
          "USER_ID_REF.ATTR_ID = 37 AND " +
          "AUTHOR_ID_REF.OBJECT_ID = TRIP.OBJECT_ID AND " +
          "AUTHOR_ID_REF.ATTR_ID = 38 AND " +
          "PM_ID_REF.OBJECT_ID = TRIP.OBJECT_ID AND " +
          "PM_ID_REF.ATTR_ID = 40 AND " +
          "COUNTRY.OBJECT_ID = TRIP.OBJECT_ID AND " +
          "COUNTRY.ATTR_ID = 33 AND " +
          "START_DATE.OBJECT_ID = TRIP.OBJECT_ID AND " +
          "START_DATE.ATTR_ID = 34 AND " +
          "END_DATE.OBJECT_ID = TRIP.OBJECT_ID AND " +
          "END_DATE.ATTR_ID = 35 AND " +
          "STATUS.OBJECT_ID = TRIP.OBJECT_ID AND " +
          "STATUS.ATTR_ID = 36 AND " +
          "STATUS.ATTR_ID = STATUS_VALUE.ATTR_ID AND " +
          "STATUS.LIST_VALUE_ID = STATUS_VALUE.LIST_VALUE_ID";

  String FIND_TRIP_BY_USER_ID =
      "SELECT TRIP_PROJ_REF.OBJECT_ID AS BUSINESS_TRIP_ID, COUNTRY.VALUE AS COUNTRY, START_DATE.DATE_VALUE AS START_DATE, "
          + "      END_DATE.DATE_VALUE AS END_DATE, TRIP_STATUS_VALUE.VALUE STATUS, TRIP_PROJ_REF.REFERENCE AS PROJECT_ID, AUTHOR_ID_REF.REFERENCE AS AUTHOR_ID, "
          + "  USER_ID.OBJECT_ID AS USER_ID, PM_ID.REFERENCE AS PM_ID "
          + "FROM OBJECTS USER_ID, OBJREFERENCE TRIP_PROJ_REF, "
          + "      OBJREFERENCE TRIP_USER_REF, OBJREFERENCE AUTHOR_ID_REF,  OBJREFERENCE PM_ID, "
          + "      ATTRIBUTES COUNTRY, ATTRIBUTES START_DATE,ATTRIBUTES END_DATE, "
          + "      ATTRIBUTES TRIP_STATUS, LISTVALUE TRIP_STATUS_VALUE "
          + "WHERE USER_ID.OBJECT_ID = ? AND "
          + "      TRIP_USER_REF.ATTR_ID = 37 AND "
          + "      TRIP_USER_REF.REFERENCE = USER_ID.OBJECT_ID AND "
          + "      COUNTRY.OBJECT_ID = TRIP_USER_REF.OBJECT_ID AND "
          + "      COUNTRY.ATTR_ID = 33 AND "
          + "      START_DATE.OBJECT_ID = TRIP_USER_REF.OBJECT_ID AND "
          + "      START_DATE.ATTR_ID = 34 AND "
          + "      END_DATE.OBJECT_ID = TRIP_USER_REF.OBJECT_ID AND "
          + "      END_DATE.ATTR_ID = 35 AND "
          + "      TRIP_STATUS.OBJECT_ID = TRIP_USER_REF.OBJECT_ID AND "
          + "      TRIP_STATUS.ATTR_ID = 36 AND "
          + "      TRIP_STATUS_VALUE.ATTR_ID = TRIP_STATUS.ATTR_ID AND "
          + "      TRIP_STATUS_VALUE.LIST_VALUE_ID = TRIP_STATUS.LIST_VALUE_ID AND "
          + "      TRIP_PROJ_REF.OBJECT_ID = TRIP_USER_REF.OBJECT_ID AND "
          + "      TRIP_PROJ_REF.ATTR_ID = 39 AND "
          + "      AUTHOR_ID_REF.OBJECT_ID = TRIP_USER_REF.OBJECT_ID AND "
          + "      AUTHOR_ID_REF.ATTR_ID = 38 AND "
          + "      PM_ID.OBJECT_ID = TRIP_USER_REF.OBJECT_ID AND "
          + "      PM_ID.ATTR_ID = 40";

  String FIND_TRIP_BY_PROJECT_ID =
      "SELECT TRIP_PROJ_REF.OBJECT_ID AS BUSINESS_TRIP_ID, COUNTRY.VALUE AS COUNTRY, START_DATE.DATE_VALUE AS START_DATE, "
          + "      END_DATE.DATE_VALUE AS END_DATE, TRIP_STATUS_VALUE.VALUE STATUS, USER_ID_REF.REFERENCE AS USER_ID, AUTHOR_ID_REF.REFERENCE AS AUTHOR_ID, "
          + "      PROJECT_ID.OBJECT_ID AS PROJECT_ID, PM_ID.REFERENCE AS PM_ID "
          + "FROM OBJECTS PROJECT_ID, OBJREFERENCE USER_ID_REF, "
          + "      OBJREFERENCE TRIP_PROJ_REF, OBJREFERENCE AUTHOR_ID_REF,  OBJREFERENCE PM_ID, "
          + "      ATTRIBUTES COUNTRY, ATTRIBUTES START_DATE,ATTRIBUTES END_DATE, "
          + "      ATTRIBUTES TRIP_STATUS, LISTVALUE TRIP_STATUS_VALUE "
          + "WHERE PROJECT_ID.OBJECT_ID = ? AND "
          + "      TRIP_PROJ_REF.ATTR_ID = 39 AND "
          + "      TRIP_PROJ_REF.REFERENCE = PROJECT_ID.OBJECT_ID AND "
          + "      COUNTRY.OBJECT_ID = TRIP_PROJ_REF.OBJECT_ID AND "
          + "      COUNTRY.ATTR_ID = 33 AND "
          + "      START_DATE.OBJECT_ID = TRIP_PROJ_REF.OBJECT_ID AND "
          + "      START_DATE.ATTR_ID = 34 AND "
          + "      END_DATE.OBJECT_ID = TRIP_PROJ_REF.OBJECT_ID AND "
          + "      END_DATE.ATTR_ID = 35 AND "
          + "      TRIP_STATUS.OBJECT_ID = TRIP_PROJ_REF.OBJECT_ID AND "
          + "      TRIP_STATUS.ATTR_ID = 36 AND "
          + "      TRIP_STATUS_VALUE.ATTR_ID = TRIP_STATUS.ATTR_ID AND "
          + "      TRIP_STATUS_VALUE.LIST_VALUE_ID = TRIP_STATUS.LIST_VALUE_ID AND "
          + "      USER_ID_REF.OBJECT_ID = TRIP_PROJ_REF.OBJECT_ID AND "
          + "      USER_ID_REF.ATTR_ID = 37 AND "
          + "      AUTHOR_ID_REF.OBJECT_ID = TRIP_PROJ_REF.OBJECT_ID AND "
          + "      AUTHOR_ID_REF.ATTR_ID = 38 AND "
          + "      PM_ID.OBJECT_ID = TRIP_PROJ_REF.OBJECT_ID AND "
          + "      PM_ID.ATTR_ID = 40";


  String UPDATE_COUNTRY = "UPDATE ATTRIBUTES "
      + "    SET ATTRIBUTES.VALUE = ? "
      + "    WHERE ATTRIBUTES.OBJECT_ID = ? AND "
      + "    ATTRIBUTES.ATTR_ID = 33";

  String UPDATE_START_DATE = "UPDATE ATTRIBUTES "
      + "    SET ATTRIBUTES.DATE_VALUE = ? "
      + "    WHERE ATTRIBUTES.OBJECT_ID = ? AND "
      + "    ATTRIBUTES.ATTR_ID = 34";

  String UPDATE_END_DATE = "UPDATE ATTRIBUTES "
      + "    SET ATTRIBUTES.DATE_VALUE = ? "
      + "    WHERE ATTRIBUTES.OBJECT_ID = ? AND "
      + "    ATTRIBUTES.ATTR_ID = 35";

  String UPDATE_STATUS = "UPDATE ATTRIBUTES "
      + "    SET ATTRIBUTES.LIST_VALUE_ID = ? "
      + "    WHERE ATTRIBUTES.OBJECT_ID = ? AND "
      + "    ATTRIBUTES.ATTR_ID = 36";

  String FIND_BUSINESS_TRIP_BY_PM_ID_AND_STATUS = "SELECT  BUSINESS_TRIP.OBJECT_ID AS BUSINESS_TRIP_ID, PROJECT_ID.OBJECT_ID AS PROJECT_ID, USER_ID.OBJECT_ID AS USER_ID, "
      + "        AUTHOR_ID.OBJECT_ID AS AUTHOR_ID, PM_ID.OBJECT_ID AS PM_ID, TRIP_COUNTRY.VALUE AS COUNTRY, START_DATE.DATE_VALUE AS START_DATE, "
      + "        END_DATE.DATE_VALUE AS END_DATE, TRIP_STATUS_VALUE.VALUE AS STATUS "
      + "FROM "
      + "  OBJECTS BUSINESS_TRIP, OBJECTS PM_ID, OBJECTS PROJECT_ID, OBJECTS USER_ID, OBJECTS AUTHOR_ID, "
      + "  ATTRIBUTES TRIP_COUNTRY, ATTRIBUTES START_DATE, ATTRIBUTES END_DATE, ATTRIBUTES TRIP_STATUS, "
      + "  OBJREFERENCE TRIP_USER_REF,  OBJREFERENCE TRIP_AUTHOR_REF, OBJREFERENCE TRIP_PROJECT_REF, OBJREFERENCE TRIP_PM_REF, "
      + "  LISTVALUE TRIP_STATUS_VALUE "
      + "WHERE "
      + "  TRIP_PM_REF.ATTR_ID = 40 AND "
      + "  PM_ID.OBJECT_ID = ? AND "
      + "  PM_ID.OBJECT_ID = TRIP_PM_REF.REFERENCE AND "
      + "  BUSINESS_TRIP.OBJECT_ID = TRIP_PM_REF.OBJECT_ID AND "
      + "  TRIP_PROJECT_REF.ATTR_ID = 39 AND "
      + "  TRIP_PROJECT_REF.OBJECT_ID = BUSINESS_TRIP.OBJECT_ID AND "
      + "  PROJECT_ID.OBJECT_ID = TRIP_PROJECT_REF.REFERENCE AND "
      + "  TRIP_USER_REF.ATTR_ID = 37 AND "
      + "  TRIP_USER_REF.OBJECT_ID = BUSINESS_TRIP.OBJECT_ID AND "
      + "  USER_ID.OBJECT_ID = TRIP_USER_REF.REFERENCE AND "
      + "  TRIP_AUTHOR_REF.ATTR_ID = 38 AND "
      + "  TRIP_AUTHOR_REF.OBJECT_ID = BUSINESS_TRIP.OBJECT_ID AND "
      + "  AUTHOR_ID.OBJECT_ID = TRIP_AUTHOR_REF.REFERENCE AND "
      + "  TRIP_COUNTRY.ATTR_ID = 33 AND "
      + "  TRIP_COUNTRY.OBJECT_ID = BUSINESS_TRIP.OBJECT_ID AND "
      + "  START_DATE.ATTR_ID = 34 AND "
      + "  START_DATE.OBJECT_ID = BUSINESS_TRIP.OBJECT_ID AND "
      + "  END_DATE.ATTR_ID = 35 AND "
      + "  END_DATE.OBJECT_ID = BUSINESS_TRIP.OBJECT_ID AND "
      + "  TRIP_STATUS.ATTR_ID = 36 AND "
      + "  TRIP_STATUS.OBJECT_ID = BUSINESS_TRIP.OBJECT_ID AND "
      + "  TRIP_STATUS_VALUE.ATTR_ID = TRIP_STATUS.ATTR_ID AND "
      + "  TRIP_STATUS_VALUE.LIST_VALUE_ID = ? AND "
      + "  TRIP_STATUS_VALUE.LIST_VALUE_ID = TRIP_STATUS.LIST_VALUE_ID ";
}
