package com.netcracker.project.model.impl.mappers;

import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.entity.WorkPeriod;
import com.netcracker.project.model.entity.WorkPeriod.WorkPeriodStatus;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class WorkPeriodMapper implements RowMapper<WorkPeriod> {

  private MapperDateConverter converter = new MapperDateConverter();

  public WorkPeriod mapRow(ResultSet rs, int rowNum)
      throws SQLException {

    return new WorkPeriod.WorkPeriodBuilder()
        .workPeriodId(new BigInteger(rs.getString(EnumMapper.WORK_PERIOD_ID.getFullName())))
        .userId(new BigInteger(rs.getString(EnumMapper.USER_ID.getFullName())))
        .projectId(new BigInteger(rs.getString(EnumMapper.PROJECT_ID.getFullName())))
        .startWorkDate(
            converter.convertStringToDateFromJSP(rs.getString(EnumMapper.START_DATE.getFullName())))
        .endWorkDate(
            converter.convertStringToDateFromJSP(rs.getString(EnumMapper.END_DATE.getFullName())))
        .workPeriodStatus(WorkPeriodStatus
            .valueOf(rs.getString(EnumMapper.WORKING_PERIOD_STATUS.getFullName())))
        .build();
  }

}
