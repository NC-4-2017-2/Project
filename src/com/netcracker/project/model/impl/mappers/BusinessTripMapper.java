package com.netcracker.project.model.impl.mappers;

import com.netcracker.project.model.entity.BusinessTrip;
import com.netcracker.project.model.entity.Status;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BusinessTripMapper implements RowMapper<BusinessTrip> {

  @Override
  public BusinessTrip mapRow(ResultSet resultSet, int i) throws SQLException {
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
        .startDate(resultSet.getDate(EnumMapper.START_DATE.getFullName()))
        .endDate(resultSet.getDate(EnumMapper.END_DATE.getFullName()))
        .status(Status
            .valueOf(resultSet.getString(EnumMapper.STATUS.getFullName())))
        .build();
  }
}
