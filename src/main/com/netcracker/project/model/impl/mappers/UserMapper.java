package main.com.netcracker.project.model.impl.mappers;

import main.com.netcracker.project.model.UserDAO;
import main.com.netcracker.project.model.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserMapper implements RowMapper<User> {

    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        Date dateBirth = null, dateHire = null;

        dateBirth = convertStringToDate(rs.getString("BIRTH_DATE"));
        dateHire = convertStringToDate(rs.getString("HIRE_DATE"));

        return new User.UserBuilder()
                .userId(new BigInteger(rs.getString("USER_ID")))
                .lastName(rs.getString("LAST_NAME"))
                .firstName(rs.getString("FIRST_NAME"))
                .email(rs.getString("EMAIL"))
                .dateOfBirth(dateBirth)
                .hireDate(dateHire)
                .phoneNumber(rs.getString("PHONE_NUMBER"))
                .jobTitle(UserDAO.JobTitle.valueOf(rs.getString("JOB_TITLE")))
                .projectStatus(UserDAO.ProjectStatus.valueOf(rs.getString("PROJECT_STATUS")))
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
