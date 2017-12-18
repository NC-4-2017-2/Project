package com.netcracker.project.model.impl.mappers;

import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.UserDAO.WorkPeriod;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkPeriodMapper implements RowMapper<WorkPeriod> {

  private MapperDateConverter converter;

  public WorkPeriod mapRow(ResultSet rs, int rowNum)
      throws SQLException {

    WorkPeriod workPeriod = new WorkPeriod();

    workPeriod.setWorkPeriodId(
        new BigInteger(rs.getString(EnumMapper.WORK_PERIOD_ID.getFullName())));
    workPeriod.setUserId(
        new BigInteger(rs.getString(EnumMapper.USER_ID.getFullName())));
    workPeriod.setProjectId(
        new BigInteger(rs.getString(EnumMapper.PROJECT_ID.getFullName())));
    workPeriod.setStartWorkDate(rs.getDate(EnumMapper.START_DATE.getFullName()));
    workPeriod.setEndWorkDate(rs.getDate(EnumMapper.END_DATE.getFullName()));
    workPeriod.setWorkPeriodStatus(UserDAO.WorkPeriod.WorkPeriodStatus.valueOf(rs.getString(EnumMapper.WORKING_PERIOD_STATUS.getFullName())));

    return workPeriod;
  }

}
