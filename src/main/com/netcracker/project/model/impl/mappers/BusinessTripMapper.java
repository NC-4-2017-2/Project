package main.com.netcracker.project.model.impl.mappers;

import main.com.netcracker.project.model.entity.BusinessTrip;
import main.com.netcracker.project.model.entity.Status;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class BusinessTripMapper implements RowMapper<BusinessTrip> {

  private MapperDateConverter converter = new MapperDateConverter();

  @Override
  public BusinessTrip mapRow(ResultSet resultSet, int i) throws SQLException {
    Date startDate = converter
        .convertStringToDate(
            resultSet.getString(EnumMapper.START_DATE.getFullName()));
    Date endDate = converter.convertStringToDate(
        resultSet.getString(EnumMapper.END_DATE.getFullName()));

    return new BusinessTrip.BusinessTripBuilder()
        .businessTripId(new BigInteger(
            resultSet.getString(EnumMapper.BUSINESS_TRIP_ID.getFullName())))
        .projectId(new BigInteger(
            resultSet.getString(EnumMapper.PROJECT_ID.getFullName())))
        .userId(new BigInteger(
            resultSet.getString(EnumMapper.USER_ID.getFullName())))
        .authorId(new BigInteger(
            resultSet.getString(EnumMapper.AUTHOR_ID.getFullName())))
        .pmId(
            new BigInteger(resultSet.getString(EnumMapper.PM_ID.getFullName())))
        .country(resultSet.getString(EnumMapper.COUNTRY.getFullName()))
        .startDate(startDate)
        .endDate(endDate)
        .status(Status
            .valueOf(resultSet.getString(EnumMapper.STATUS.getFullName())))
        .build();
  }
}
