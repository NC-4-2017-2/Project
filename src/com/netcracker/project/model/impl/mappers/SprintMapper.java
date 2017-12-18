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

  private MapperDateConverter converter;

  @Override
  public Sprint mapRow(ResultSet rs, int i) throws SQLException {
    Date startDate;
    Date endDate;
    Date plannedEndDate;

    startDate = rs.getDate(EnumMapper.START_DATE.getFullName());
    endDate = rs.getDate(EnumMapper.END_DATE.getFullName());
    plannedEndDate = rs.getDate(EnumMapper.PLANNED_END_DATE.getFullName());

    return new Sprint.SprintBuilder()
        .sprintId(
            new BigInteger(rs.getString(EnumMapper.SPRINT_ID.getFullName())))
        .name(rs.getString(EnumMapper.NAME.getFullName()))
        .startDate(startDate)
        .plannedEndDate(plannedEndDate)
        .endDate(endDate)
        .status(OCStatus.valueOf(rs.getString(EnumMapper.STATUS.getFullName())))
        .build();
  }
}
