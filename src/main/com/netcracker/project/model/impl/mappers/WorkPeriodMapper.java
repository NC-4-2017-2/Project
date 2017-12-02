package main.com.netcracker.project.model.impl.mappers;

import main.com.netcracker.project.model.UserDAO;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkPeriodMapper implements RowMapper<UserDAO.WorkPeriod> {

  private MapperDateConverter converter;

  public UserDAO.WorkPeriod mapRow(ResultSet rs, int rowNum)
      throws SQLException {
    Date startDate = null;
    Date endDate = null;

    UserDAO.WorkPeriod workPeriod = new UserDAO.WorkPeriod();
    converter = new MapperDateConverter();

    startDate = converter
        .convertStringToDate(rs.getString(EnumMapper.START_DATE.getFullName()));
    endDate = converter
        .convertStringToDate(rs.getString(EnumMapper.END_DATE.getFullName()));

    workPeriod.setWorkPeriodId(
        new BigInteger(rs.getString(EnumMapper.WORK_PERIOD_ID.getFullName())));
    workPeriod.setUserId(
        new BigInteger(rs.getString(EnumMapper.USER_ID.getFullName())));
    workPeriod.setProjectId(
        new BigInteger(rs.getString(EnumMapper.PROJECT_ID.getFullName())));
    workPeriod.setStartWorkDate(startDate);
    workPeriod.setEndWorkDate(endDate);

    return workPeriod;
  }

}
