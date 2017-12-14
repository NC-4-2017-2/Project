package main.com.netcracker.project.model.impl.mappers;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.com.netcracker.project.model.entity.WorkingHoursStatistic;
import org.springframework.jdbc.core.RowMapper;

public class WorkingHoursStatisticMapper implements
    RowMapper<WorkingHoursStatistic> {

  @Override
  public WorkingHoursStatistic mapRow(ResultSet rs, int rowNum)
      throws SQLException {
    BigInteger userId = new BigInteger(
        rs.getString(EnumMapper.USER_ID.getFullName()));
    String workingDayDate = rs.getString(EnumMapper.WORKING_DATE.getFullName());
    int workingHours = rs.getInt(EnumMapper.WORKING_HOURS.getFullName());
    return new WorkingHoursStatistic(userId, workingDayDate, workingHours);
  }
}
