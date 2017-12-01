package main.com.netcracker.project.model.impl.mappers;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import main.com.netcracker.project.model.entity.Status;
import main.com.netcracker.project.model.entity.Vacation;
import org.springframework.jdbc.core.RowMapper;

public class VacationMapper implements RowMapper<Vacation> {
  private MapperDateConverter converter = new MapperDateConverter();

  @Override
  public Vacation mapRow(ResultSet rs, int rowNum) throws SQLException {
    Date startDate = converter.convertStringToDate(rs.getString("START_DATE"));
    Date endDate = converter.convertStringToDate(rs.getString("END_DATE"));

    return new Vacation.VacationBuilder()
        .vacationId(new BigInteger(rs.getString("VACATION_ID")))
        .userId(new BigInteger(rs.getString("USER_ID")))
        .projectId(new BigInteger(rs.getString("PROJECT_ID")))
        .startDate(startDate)
        .endDate(endDate)
        .pmStatus(Status.valueOf(rs.getString("PM_STATUS")))
        .lmStatus(Status.valueOf(rs.getString("LM_STATUS")))
        .pmId(new BigInteger(rs.getString("PM_ID")))
        .lmId(new BigInteger(rs.getString("LM_ID")))
        .build();
  }
}
