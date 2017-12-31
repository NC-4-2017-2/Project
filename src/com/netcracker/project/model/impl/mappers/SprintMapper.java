package com.netcracker.project.model.impl.mappers;

import com.netcracker.project.model.enums.OCStatus;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.netcracker.project.model.entity.Sprint;
import org.springframework.jdbc.core.RowMapper;

public class SprintMapper implements RowMapper<Sprint> {


  @Override
  public Sprint mapRow(ResultSet rs, int i) throws SQLException {

    return new Sprint.SprintBuilder()
        .sprintId(
            new BigInteger(rs.getString(EnumMapper.SPRINT_ID.getFullName())))
        .name(rs.getString(EnumMapper.NAME.getFullName()))
        .startDate(rs.getDate(EnumMapper.START_DATE.getFullName()))
        .plannedEndDate(rs.getDate(EnumMapper.PLANNED_END_DATE.getFullName()))
        .endDate(rs.getDate(EnumMapper.END_DATE.getFullName()))
        .status(OCStatus.valueOf(rs.getString(EnumMapper.STATUS.getFullName())))
        .build();
  }
}
