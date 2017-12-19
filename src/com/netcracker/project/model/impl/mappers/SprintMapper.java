package com.netcracker.project.model.impl.mappers;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import com.netcracker.project.model.ProjectDAO.OCStatus;
import com.netcracker.project.model.entity.Sprint;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.RowMapper;

public class SprintMapper implements RowMapper<Sprint> {

  private MapperDateConverter converter = new MapperDateConverter();

  @Override
  public Sprint mapRow(ResultSet rs, int i) throws SQLException {
    String startDate;
    String endDate;
    String plannedEndDate;

    startDate = rs.getString(EnumMapper.START_DATE.getFullName());
    endDate = rs.getString(EnumMapper.END_DATE.getFullName());
    plannedEndDate = rs.getString(EnumMapper.PLANNED_END_DATE.getFullName());

    return new Sprint.SprintBuilder()
        .sprintId(
            new BigInteger(rs.getString(EnumMapper.SPRINT_ID.getFullName())))
        .name(rs.getString(EnumMapper.NAME.getFullName()))
        .startDate(converter.convertStringToDateFromJSP(startDate))
        .plannedEndDate(converter.convertStringToDateFromJSP(plannedEndDate))
        .endDate(converter.convertStringToDateFromJSP(endDate))
        .status(OCStatus.valueOf(rs.getString(EnumMapper.STATUS.getFullName())))
        .build();
  }
}
