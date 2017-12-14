package main.com.netcracker.project.model.impl.mappers;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.com.netcracker.project.model.entity.UserTaskStatistic;
import main.com.netcracker.project.model.entity.UserTaskStatistic.UserTaskStatisticBuilder;
import org.springframework.jdbc.core.RowMapper;

public class UserTaskStatisticMapper implements RowMapper<UserTaskStatistic> {

  @Override
  public UserTaskStatistic mapRow(ResultSet rs, int rowNum)
      throws SQLException {
    return new UserTaskStatisticBuilder()
        .userId(new BigInteger(rs.getString(EnumMapper.USER_ID.getFullName())))
        .critical(rs.getInt(EnumMapper.CRITICAL.getFullName()))
        .high(rs.getInt(EnumMapper.HIGH.getFullName()))
        .normal(rs.getInt(EnumMapper.NORMAL.getFullName()))
        .low(rs.getInt(EnumMapper.LOW.getFullName()))
        .build();
  }
}
