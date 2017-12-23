package com.netcracker.project.model.impl.mappers;

import com.netcracker.project.model.enums.Status;
import com.netcracker.project.model.entity.WorkingDay;
import com.netcracker.project.services.impl.DateConverterService;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class WorkingDayMapper implements RowMapper<WorkingDay> {

  private DateConverterService converter = new DateConverterService();

  @Override
  public WorkingDay mapRow(ResultSet rs, int rowNum) throws SQLException {

    return new WorkingDay.WorkingDayBuilder()
        .workingDayId(new BigInteger(rs.getString(EnumMapper.WORKING_DAY_ID.getFullName())))
        .date(rs.getDate(EnumMapper.WORKING_DATE.getFullName()))
        .weekNumber(rs.getInt(EnumMapper.WEEK_NUMBER.getFullName()))
        .workingHours(rs.getDouble(EnumMapper.WORKING_HOURS.getFullName()))
        .status(Status.valueOf(rs.getString(EnumMapper.STATUS.getFullName())))
        .pmId(new BigInteger(rs.getString(EnumMapper.PM_ID.getFullName())))
        .userId(new BigInteger(rs.getString(EnumMapper.USER_ID.getFullName())))
        .build();
  }
}
