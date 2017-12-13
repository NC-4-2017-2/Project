package main.com.netcracker.project.model.impl.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import main.com.netcracker.project.model.entity.SprintStatistic;
import org.springframework.jdbc.core.RowMapper;

public class SprintStatisticMapper implements RowMapper<SprintStatistic> {

  @Override
  public SprintStatistic mapRow(ResultSet rs, int rowNum) throws SQLException {

    String sprintName = rs.getString(EnumMapper.NAME.getFullName());
    int plannedDays = rs.getInt(EnumMapper.PLANNED_TAKE_DAYS.getFullName());
    int takeDays = rs.getInt(EnumMapper.TAKE_DAYS.getFullName());

    return new SprintStatistic(sprintName, plannedDays, takeDays);
  }
}
