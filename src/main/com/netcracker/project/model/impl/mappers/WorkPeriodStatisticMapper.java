package main.com.netcracker.project.model.impl.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import main.com.netcracker.project.model.entity.WorkPeriodStatistic;
import org.springframework.jdbc.core.RowMapper;

public class WorkPeriodStatisticMapper implements RowMapper<WorkPeriodStatistic> {

  @Override
  public WorkPeriodStatistic mapRow(ResultSet rs, int rowNum)
      throws SQLException {
    int allTimeWorkers = rs.getInt(EnumMapper.ALL_TIME_WORKERS.getFullName());
    int currentWorkers = rs.getInt(EnumMapper.CURRENT_WORKERS.getFullName());
    return new WorkPeriodStatistic(allTimeWorkers, currentWorkers);
  }
}
