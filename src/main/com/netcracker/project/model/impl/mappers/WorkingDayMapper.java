package main.com.netcracker.project.model.impl.mappers;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import main.com.netcracker.project.model.entity.Status;
import main.com.netcracker.project.model.entity.WorkingDay;
import org.springframework.jdbc.core.RowMapper;

public class WorkingDayMapper implements RowMapper<WorkingDay> {

  private MapperDateConverter converter = new MapperDateConverter();

  @Override
  public WorkingDay mapRow(ResultSet rs, int rowNum) throws SQLException {

    Date working_date = converter
        .convertStringToDate(rs.getString("WORKING_DATE"));

    return new WorkingDay.WorkingDayBuilder()
        .workingDayId(new BigInteger(rs.getString("WORKING_DAY_ID")))
        .date(working_date)
        .weekNumber(rs.getInt("WEEK_NUMBER"))
        .workingHours(rs.getDouble("WORKING_HOURS"))
        .status(Status.valueOf(rs.getString("STATUS")))
        .pmId(new BigInteger(rs.getString("PM_ID")))
        .userId(new BigInteger(rs.getString("USER_ID")))
        .build();
  }
}