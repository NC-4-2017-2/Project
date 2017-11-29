package main.com.netcracker.project.model.impl;

import main.com.netcracker.project.model.BusinessTripDAO;
import main.com.netcracker.project.model.entity.BusinessTrip;
import main.com.netcracker.project.model.impl.mappers.BusinessTripMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigInteger;
import java.util.Collection;

public class BusinessTripDAOImpl implements BusinessTripDAO {

  private JdbcTemplate template;

  private static final String CREATE_TRIP = "INSERT ALL " +
      "INTO OBJECTS(OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) VALUES (50, NULL, 4, ?, NULL) "
      +
      "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (33, 50, ?, NULL, NULL) "
      +
      "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (34, 50, ?, NULL, NULL) "
      +
      "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (35, 50, ?, NULL, NULL) "
      +
      "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (36, 50, NULL, NULL, 0) "
      +
      "INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) VALUES (37, ?, ?) " +
      "INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) VALUES (38, ?, ?) " +
      "INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) VALUES (39, ?, ?) " +
      "INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) VALUES (40, ?, ?) " +
      "SELECT * FROM DUAL;";

  private static final String FIND_TRIP_BY_USER_ID =
      "SELECT TRIP.OBJECT_ID AS BUSINESS_TRIP_ID, " +
          "PROJECT_ID_REF.REFERENCE AS PROJECT_ID, USER_ID_REF.REFERENCE AS USER_ID, "
          +
          "AUTHOR_ID_REF.REFERENCE AS AUTHOR_ID, " +
          "PM_ID_REF.REFERENCE AS PM_ID, COUNTRY.VALUE AS COUNTRY, START_DATE.VALUE AS START_DATE, "
          +
          "END_DATE.VALUE AS END_DATE, STATUS_VALUE.VALUE AS STATUS " +
          "FROM OBJTYPE TRIP_TYPE, OBJECTS TRIP, " +
          "OBJREFERENCE PROJECT_ID_REF, OBJREFERENCE USER_ID_REF, " +
          "OBJREFERENCE AUTHOR_ID_REF, OBJREFERENCE PM_ID_REF, " +
          "ATTRIBUTES COUNTRY, ATTRIBUTES START_DATE, ATTRIBUTES END_DATE, " +
          "ATTRIBUTES STATUS, LISTVALUE STATUS_VALUE " +
          "WHERE TRIP_TYPE.OBJECT_TYPE_ID = 4 AND " +
          "TRIP.OBJECT_TYPE_ID = TRIP_TYPE.OBJECT_TYPE_ID AND " +
          "TRIP.OBJECT_ID = 50 AND " +
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
          "STATUS.LIST_VALUE_ID = STATUS_VALUE.LIST_VALUE_ID;";

  private static final String FIND_TRIP_BY_PROJECT_ID = "SELECT TRIP_PROJ_REF.OBJECT_ID AS BUSINESS_TRIP_ID, COUNTRY.VALUE AS COUNTRY, START_DATE.VALUE AS START_DATE, "
      + "      END_DATE.VALUE AS END_DATE, TRIP_STATUS_VALUE.VALUE STATUS, USER_ID_REF.REFERENCE AS USER_ID, AUTHOR_ID_REF.REFERENCE AS AUTHOR_ID, "
      + "      PROJECT_ID.OBJECT_ID AS PROJECT_ID, PM_ID.REFERENCE AS PM_ID "
      + "FROM OBJECTS PROJECT_ID, OBJREFERENCE USER_ID_REF, "
      + "      OBJREFERENCE TRIP_PROJ_REF, OBJREFERENCE AUTHOR_ID_REF,  OBJREFERENCE PM_ID, "
      + "      ATTRIBUTES COUNTRY, ATTRIBUTES START_DATE,ATTRIBUTES END_DATE, "
      + "      ATTRIBUTES TRIP_STATUS, LISTVALUE TRIP_STATUS_VALUE "
      + "WHERE PROJECT_ID.OBJECT_ID = 4 AND "
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
      + "      PM_ID.ATTR_ID = 40;";

  @Override
  public void createTrip(BusinessTrip trip) {
    template.update(CREATE_TRIP, new Object[]{
        "BUSINESS_TRIP " + trip.getBusinessTripId(),
        trip.getCountry(),
        trip.getStartDate(),
        trip.getEndDate(),
        trip.getBusinessTripId(),
        trip.getUserId(),
        trip.getBusinessTripId(),
        trip.getAuthorId(),
        trip.getBusinessTripId(),
        trip.getProjectId(),
        trip.getBusinessTripId(),
        trip.getPmId()
    });
  }

  @Override
  public void updateTrip(BigInteger id, BusinessTrip trip) {

  }

  @Override
  public Collection<BusinessTrip> findTripByUserId(BigInteger id) {
    return template.query(FIND_TRIP_BY_USER_ID, new Object[]{id},
        new BusinessTripMapper());
  }

  @Override
  public Collection<BusinessTrip> findTripByProjectId(BigInteger id) {
    return template.query(FIND_TRIP_BY_PROJECT_ID, new Object[]{id},
        new BusinessTripMapper());
  }
}
