package main.com.netcracker.project.model.impl.mappers;

import main.com.netcracker.project.model.UserDAO;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Артём on 26.11.2017.
 */
public class WorkPeriodMapper implements RowMapper<UserDAO.WorkPeriod> {

    public UserDAO.WorkPeriod mapRow(ResultSet rs, int rowNum) throws SQLException {
        Date startDate = null, endDate = null;
        UserDAO.WorkPeriod workPeriod = new UserDAO.WorkPeriod();

        startDate = convertStringToDate(rs.getString("START_DATE"));
        endDate = convertStringToDate(rs.getString("END_DATE"));

        workPeriod.setUserId(new BigInteger(rs.getString("USER_ID")));
        workPeriod.setProjectId(new BigInteger(rs.getString("PROJECT_ID")));
        workPeriod.setStartWorkDate(startDate);
        workPeriod.setEndWorkDate(endDate);

        return workPeriod;
    }

    private java.util.Date convertStringToDate(String str) {
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
