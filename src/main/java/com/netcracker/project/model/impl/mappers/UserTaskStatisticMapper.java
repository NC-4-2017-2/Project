package com.netcracker.project.model.impl.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.netcracker.project.model.entity.UserTaskStatistic;
import com.netcracker.project.model.entity.UserTaskStatistic.UserTaskStatisticBuilder;
import org.springframework.jdbc.core.RowMapper;

public class UserTaskStatisticMapper implements RowMapper<UserTaskStatistic> {

  @Override
  public UserTaskStatistic mapRow(ResultSet rs, int rowNum)
      throws SQLException {
    int critical = rs.getInt(EnumMapper.CRITICAL.getFullName());
    int high = rs.getInt(EnumMapper.HIGH.getFullName());
    int normal = rs.getInt(EnumMapper.NORMAL.getFullName());
    int low = rs.getInt(EnumMapper.LOW.getFullName());
    return new UserTaskStatisticBuilder()
        .critical(critical)
        .high(high)
        .normal(normal)
        .low(low)
        .build();
  }
}
