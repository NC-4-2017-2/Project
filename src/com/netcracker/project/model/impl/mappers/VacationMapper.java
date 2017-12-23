package com.netcracker.project.model.impl.mappers;

import com.netcracker.project.services.impl.DateConverterService;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.netcracker.project.model.enums.Status;
import com.netcracker.project.model.entity.Vacation;
import org.springframework.jdbc.core.RowMapper;

public class VacationMapper implements RowMapper<Vacation> {
  private DateConverterService converter = new DateConverterService();

  @Override
  public Vacation mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new Vacation.VacationBuilder()
        .vacationId(new BigInteger(rs.getString(EnumMapper.VACATION_ID.getFullName())))
        .userId(new BigInteger(rs.getString(EnumMapper.USER_ID.getFullName())))
        .projectId(new BigInteger(rs.getString(EnumMapper.PROJECT_ID.getFullName())))
        .startDate(rs.getDate(EnumMapper.START_DATE.getFullName()))
        .endDate(rs.getDate(EnumMapper.END_DATE.getFullName()))
        .pmStatus(Status.valueOf(rs.getString(EnumMapper.PM_STATUS.getFullName())))
        .lmStatus(Status.valueOf(rs.getString(EnumMapper.LM_STATUS.getFullName())))
        .pmId(new BigInteger(rs.getString(EnumMapper.PM_ID.getFullName())))
        .lmId(new BigInteger(rs.getString(EnumMapper.LM_ID.getFullName())))
        .build();
  }
}
