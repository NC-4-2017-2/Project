package main.com.netcracker.project.model.impl;

import java.util.Collection;
import main.com.netcracker.project.model.WorkingDayDAO;

import java.util.Date;
import main.com.netcracker.project.model.entity.WorkingDay;
import main.com.netcracker.project.model.impl.mappers.MapperDateConverter;
import main.com.netcracker.project.model.impl.mappers.WorkingDayMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class WorkingDayDAOImpl implements WorkingDayDAO {

  private JdbcTemplate template;

  private static final String ADD_HOURS_PER_DAY = "INSERT ALL "
      + "    INTO OBJECTS(OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) "
      + "VALUES (200, NULL, 6, ?, NULL) "
      + "    INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE,DATE_VALUE,LIST_VALUE_ID) "
      + "VALUES (49, 200, ?, NULL, NULL) "
      + "    INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE,DATE_VALUE,LIST_VALUE_ID) "
      + "VALUES (50, 200, ?, NULL, NULL) "
      + "    INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE,DATE_VALUE,LIST_VALUE_ID) "
      + "VALUES (51, 200, ?, NULL, NULL) "
      + "    INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE,DATE_VALUE,LIST_VALUE_ID) "
      + "VALUES (52, 200, NULL, NULL, ?) "
      + "    INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) "
      + "VALUES (53, 200, ?) "
      + "    INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) "
      + "VALUES (54, 200, ?) "
      + "SELECT * FROM DUAL";

  private static final String FIND_HOURS_PER_PERIOD =
      "SELECT WORKING_DAY_DATE.VALUE AS WORKING_DATE, WORKING_WEEK_NUM.VALUE AS WEEK_NUMBER, "
          + "WORKING_DAY_HOURS.VALUE AS WORKING_HOURS, USER_ID.OBJECT_ID AS USER_ID"
          + "WORKING_HOURS_STATUS_VALUE.VALUE AS STATUS"
          + "FROM OBJECTS USER_ID, "
          + "ATTRIBUTES WORKING_DAY_DATE, ATTRIBUTES WORKING_HOURS_STATUS, "
          + "ATTRIBUTES WORKING_WEEK_NUM, ATTRIBUTES WORKING_DAY_HOURS, "
          + "OBJREFERENCE WD_USER_REF, LISTVALUE WORKING_HOURS_STATUS_VALUE "
          + "WHERE WD_USER_REF.ATTR_ID = 53 AND "
          + "USER_ID.OBJECT_ID = WD_USER_REF.REFERENCE AND "
          + "USER_ID.OBJECT_ID = ? AND "
          + "WORKING_DAY_DATE.OBJECT_ID = WD_USER_REF.OBJECT_ID AND "
          + "WORKING_DAY_DATE.ATTR_ID = 49 AND "
          + "TO_DATE(WORKING_DAY_DATE.VALUE) "
          + "BETWEEN TO_DATE(?) AND TO_DATE(?) AND "
          + "WORKING_WEEK_NUM.OBJECT_ID = WD_USER_REF.OBJECT_ID AND "
          + "WORKING_WEEK_NUM.ATTR_ID = 50 AND "
          + "WORKING_DAY_HOURS.OBJECT_ID = WD_USER_REF.OBJECT_ID AND "
          + "WORKING_DAY_HOURS.ATTR_ID = 51 AND "
          + "WORKING_HOURS_STATUS.OBJECT_ID = WD_USER_REF.OBJECT_ID AND "
          + "WORKING_HOURS_STATUS.ATTR_ID = 52 AND "
          + "WORKING_HOURS_STATUS_VALUE.ATTR_ID = "
          + "WORKING_HOURS_STATUS.ATTR_ID AND "
          + "WORKING_HOURS_STATUS.LIST_VALUE_ID = "
          + "WORKING_HOURS_STATUS_VALUE.LIST_VALUE_ID";

  @Override
  public void addHoursPerDay(WorkingDay workingDay) {
    template.update(ADD_HOURS_PER_DAY, new Object[]{
        "WORKDAY" + workingDay.getWorkingDayId(),
        workingDay.getDate(),
        workingDay.getWeekNumber(),
        workingDay.getWorkingHours(),
        workingDay.getStatus().getId(),
        workingDay.getUserId(),
        workingDay.getPmId()
    });
  }

  @Override
  public Collection<WorkingDay> findHoursPerPeriod(Integer userId,
      Date startDate,
      Date endDate) {
    MapperDateConverter converter = new MapperDateConverter();
    String startDateString = converter.convertDateTosString(startDate);
    String endDateString = converter.convertDateTosString(endDate);

    return template.query(FIND_HOURS_PER_PERIOD, new Object[]{userId,
        startDateString, endDateString}, new WorkingDayMapper());
  }
}
