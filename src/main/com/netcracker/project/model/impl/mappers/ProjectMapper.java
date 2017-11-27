package main.com.netcracker.project.model.impl.mappers;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import main.com.netcracker.project.model.ProjectDAO.OCStatus;
import main.com.netcracker.project.model.entity.Project;
import org.springframework.jdbc.core.RowMapper;

public class ProjectMapper implements RowMapper<Project> {

  public Project mapRow(ResultSet rs, int i) throws SQLException {
    Date startDate = null;
    Date endDate = null;

    String dateStart = rs.getString("START_DATE");
    String dateEnd = rs.getString("END_DATE");
    BigInteger projectManagetId = new BigInteger(
        rs.getString("MANAGES_PROJECT"));

    startDate = convertStringToDate(dateStart);
    endDate = convertStringToDate(dateEnd);

    return new Project.ProjectBuilder()
        .projectId(new BigInteger(rs.getString("PROJECT_ID")))
        .startDate(startDate)
        .endDate(endDate)
        .projectStatus(OCStatus.valueOf(rs.getString("PROJECT_STATUS")))
        .projectManager(projectManagetId)
        .build();
  }

  private Date convertStringToDate(String str) {
    Date date = null;
    DateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy");

    try {
      date = dateFormat.parse(str);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }
}
