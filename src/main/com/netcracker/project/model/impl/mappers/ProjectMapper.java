package main.com.netcracker.project.model.impl.mappers;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import main.com.netcracker.project.model.entity.Project;
import org.springframework.jdbc.core.RowMapper;

public class ProjectMapper implements RowMapper<Project> {

  private MapperDateConverter converter;

  public Project mapRow(ResultSet rs, int i) throws SQLException {
    Date startDate = null;
    Date endDate = null;

    converter = new MapperDateConverter();

    String dateStart = rs.getString(EnumMapper.START_DATE.getFullName());
    String dateEnd = rs.getString(EnumMapper.END_DATE.getFullName());

    startDate = converter.convertStringToDate(dateStart);
    endDate = converter.convertStringToDate(dateEnd);

    return new Project.ProjectBuilder()
        .projectId(
            new BigInteger(rs.getString(EnumMapper.PROJECT_ID.getFullName())))
        .name(rs.getString(EnumMapper.NAME.getFullName()))
        .startDate(startDate)
        .endDate(endDate)
        .build();
  }

}
