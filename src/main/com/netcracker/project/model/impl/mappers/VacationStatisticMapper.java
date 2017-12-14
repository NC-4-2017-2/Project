package main.com.netcracker.project.model.impl.mappers;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.com.netcracker.project.model.entity.VacationStatistic;
import org.springframework.jdbc.core.RowMapper;

public class VacationStatisticMapper implements RowMapper<VacationStatistic> {

  @Override
  public VacationStatistic mapRow(ResultSet rs, int rowNum)
      throws SQLException {
    BigInteger userId = new BigInteger(
        rs.getString(EnumMapper.USER_ID.getFullName()));
    int countDays = rs.getInt(EnumMapper.COUNT_DAYS.getFullName());
    return new VacationStatistic(userId, countDays);
  }
}
