package main.com.netcracker.project.model.impl.mappers;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import main.com.netcracker.project.model.ProjectDAO.OCStatus;
import main.com.netcracker.project.model.entity.Project;
import org.springframework.jdbc.core.RowMapper;

public class ProjectMapper implements RowMapper<Project> {

  private MapperDateConverter converter;

  public Project mapRow(ResultSet rs, int i) throws SQLException {
    Date startDate = null;
    Date endDate = null;

    converter = new MapperDateConverter();

    String dateStart = rs.getString("START_DATE_PR");
    String dateEnd = rs.getString("END_DATE_PR");

    startDate = converter.convertStringToDate(dateStart);
    endDate = converter.convertStringToDate(dateEnd);

    return new Project.ProjectBuilder()
        .projectId(new BigInteger(rs.getString("PROJECT_ID")))
        .name(rs.getString("NAME"))
        .startDate(startDate)
        .endDate(endDate)
        .build();
  }

}
