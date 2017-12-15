package main.com.netcracker.project.model.impl.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import main.com.netcracker.project.model.entity.ProjectTaskStatistic;
import org.springframework.jdbc.core.RowMapper;

public class ProjectTaskStatisticMapper implements RowMapper<ProjectTaskStatistic>{

  @Override
  public ProjectTaskStatistic mapRow(ResultSet rs, int rowNum)
      throws SQLException {
    int critical = rs.getInt(EnumMapper.CRITICAL.getFullName());
    int high = rs.getInt(EnumMapper.HIGH.getFullName());
    int normal = rs.getInt(EnumMapper.NORMAL.getFullName());
    int low = rs.getInt(EnumMapper.LOW.getFullName());
    return new ProjectTaskStatistic(critical, high, normal, low);
  }
}
