package main.com.netcracker.project.model.impl.mappers;

import main.com.netcracker.project.model.entity.BusinessTrip;
import main.com.netcracker.project.model.entity.Status;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BusinessTripMapper implements RowMapper<BusinessTrip>{

    @Override
    public BusinessTrip mapRow(ResultSet resultSet, int i) throws SQLException {
        Date startDate = null;
        Date endDate = null;

        startDate = convertStringToDate(resultSet.getString("START_DATE"));
        endDate = convertStringToDate(resultSet.getString("END_DATE"));

        return new BusinessTrip.BusinessTripBuilder()
                .businessTripId(new BigInteger(resultSet.getString("BUSINESS_TRIP_ID")))
                .projectId(new BigInteger(resultSet.getString("PROJECT_ID")))
                .userId(new BigInteger(resultSet.getString("USER_ID")))
                .authorId(new BigInteger(resultSet.getString("AUTHOR_ID")))
                .userName(resultSet.getString("USER_NAME"))
                .country(resultSet.getString("COUNTRY"))
                .startDate(startDate)
                .endDate(endDate)
                .status(Status.valueOf(resultSet.getString("STATUS")))
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
