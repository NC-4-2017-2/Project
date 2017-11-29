package main.com.netcracker.project.model.impl;

import main.com.netcracker.project.model.WorkingDayDAO;

import java.math.BigInteger;
import java.util.Date;
import java.util.Map;
import main.com.netcracker.project.model.entity.WorkingDay;
import org.springframework.jdbc.core.JdbcTemplate;

public class WorkingDayDAOImpl implements WorkingDayDAO {

  private JdbcTemplate template;

  private static final String ADD_HOURS_PER_DAY = "INSERT ALL "
      + "    INTO OBJECTS(OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) VALUES (200, NULL, 6, ?, NULL) "
      + "    INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (49, 200, ?, NULL, NULL) "
      + "    INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (50, 200, ?, NULL, NULL) "
      + "    INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (51, 200, ?, NULL, NULL) "
      + "    INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (52, 200, NULL, NULL, ?) "
      + "    INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) VALUES (53, 200, ?) "
      + "    INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) VALUES (54, 200, ?) "
      + "SELECT * FROM DUAL;";

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
  public Map<String, BigInteger> findHoursPerWeek(Integer numberOfWeek,
      BigInteger userId) {
    return null;
  }

  @Override
  public Integer findHoursPerPeriod(Integer userId, Date startDate,
      Date endDate) {
    return null;
  }
}
