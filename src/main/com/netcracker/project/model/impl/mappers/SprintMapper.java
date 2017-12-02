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
    Date startDate;
    Date endDate;
    Date plannedEndDate;

    converter = new MapperDateConverter();

    startDate = converter
        .convertStringToDate(rs.getString(EnumMapper.START_DATE.getFullName()));
    endDate = converter
        .convertStringToDate(rs.getString(EnumMapper.END_DATE.getFullName()));
    plannedEndDate = converter
        .convertStringToDate(
            rs.getString(EnumMapper.PLANNED_END_DATE.getFullName()));

    return new Sprint.SprintBuilder()
        .sprintId(
            new BigInteger(rs.getString(EnumMapper.SPRINT_ID.getFullName())))
        .name(rs.getString(EnumMapper.NAME.getFullName()))
        .startDate(startDate)
        .plannedEndDate(plannedEndDate)
        .endDate(endDate)
        .status(OCStatus.valueOf(rs.getString(EnumMapper.STATUS.getFullName())))
        .build();
  }
}
