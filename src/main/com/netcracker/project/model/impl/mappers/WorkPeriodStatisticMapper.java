package main.com.netcracker.project.model.impl.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import main.com.netcracker.project.model.entity.WorkPeriodStatistic;
import org.springframework.jdbc.core.RowMapper;

public class WorkPeriodStatisticMapper implements RowMapper<WorkPeriodStatistic> {

  @Override
  public WorkPeriodStatistic mapRow(ResultSet rs, int rowNum)
      throws SQLException {
    int working = rs.getInt(EnumMapper.CURRENT_WORKERS.getFullName());
    int fired = rs.getInt(EnumMapper.FIRED_WORKERS.getFullName());
    return new WorkPeriodStatistic(working, fired);
  }
}
