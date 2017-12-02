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
        .convertStringToDate(rs.getString(EnumMapper.WORKING_DATE.getFullName()));

    return new WorkingDay.WorkingDayBuilder()
        .workingDayId(new BigInteger(rs.getString(EnumMapper.WORKING_DAY_ID.getFullName())))
        .date(working_date)
        .weekNumber(rs.getInt(EnumMapper.WEEK_NUMBER.getFullName()))
        .workingHours(rs.getDouble(EnumMapper.WORKING_HOURS.getFullName()))
        .status(Status.valueOf(rs.getString(EnumMapper.STATUS.getFullName())))
        .pmId(new BigInteger(rs.getString(EnumMapper.PM_ID.getFullName())))
        .userId(new BigInteger(rs.getString(EnumMapper.USER_ID.getFullName())))
        .build();
  }
}
