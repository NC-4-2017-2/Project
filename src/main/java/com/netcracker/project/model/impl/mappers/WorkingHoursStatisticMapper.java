package com.netcracker.project.model.impl.mappers;

import com.netcracker.project.services.impl.DateConverterService;
import java.math.BigInteger;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.netcracker.project.model.entity.WorkingHoursStatistic;
import org.springframework.jdbc.core.RowMapper;

public class WorkingHoursStatisticMapper implements
    RowMapper<WorkingHoursStatistic> {

  @Override
  public WorkingHoursStatistic mapRow(ResultSet rs, int rowNum)
      throws SQLException {
    DateConverterService converter = new DateConverterService();
    Date date = rs.getDate(EnumMapper.WORKING_DATE.getFullName());
    String workingDay = converter
        .convertDateToString(date);
    BigInteger userId = new BigInteger(
        rs.getString(EnumMapper.USER_ID.getFullName()));
    int workingHours = rs.getInt(EnumMapper.WORKING_HOURS.getFullName());
    return new WorkingHoursStatistic(userId, workingDay, workingHours);
  }
}
