package main.com.netcracker.project.model.impl.mappers;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import main.com.netcracker.project.model.ProjectDAO.OCStatus;
import main.com.netcracker.project.model.entity.Sprint;
import org.springframework.jdbc.core.RowMapper;

public class SprintMapper implements RowMapper<Sprint> {

  private MapperDateConverter converter;

  @Override
  public Sprint mapRow(ResultSet rs, int i) throws SQLException {
    Date startDate = null;
    Date endDate = null;
    Date plannedEndDate = null;

    converter = new MapperDateConverter();

    startDate = converter.convertStringToDate(rs.getString("SPRINT_START"));
    endDate = converter.convertStringToDate(rs.getString("SPRINT_END"));
    plannedEndDate = converter
        .convertStringToDate(rs.getString("SPRINT_PLANNED_END"));

    return new Sprint.SprintBuilder()
        .sprintId(new BigInteger(rs.getString("SPRINT_ID")))
        .name(rs.getString("SPRINT_NAME"))
        .startDate(startDate)
        .plannedEndDate(plannedEndDate)
        .endDate(endDate)
        .status(OCStatus.valueOf(rs.getString("SPRINT_STATUS")))
        .build();
  }
}
