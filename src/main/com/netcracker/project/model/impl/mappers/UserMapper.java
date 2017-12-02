package main.com.netcracker.project.model.impl.mappers;

import main.com.netcracker.project.model.UserDAO;
import main.com.netcracker.project.model.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class UserMapper implements RowMapper<User> {

  private MapperDateConverter converter;

  public User mapRow(ResultSet rs, int rowNum) throws SQLException {
    Date dateBirth = null;
    Date dateHire = null;

    converter = new MapperDateConverter();

    dateBirth = converter
        .convertStringToDate(rs.getString(EnumMapper.BIRTH_DATE.getFullName()));
    dateHire = converter
        .convertStringToDate(rs.getString(EnumMapper.HIRE_DATE.getFullName()));

    return new User.UserBuilder()
        .userId(new BigInteger(rs.getString(EnumMapper.USER_ID.getFullName())))
        .lastName(rs.getString(EnumMapper.LAST_NAME.getFullName()))
        .firstName(rs.getString(EnumMapper.FIRST_NAME.getFullName()))
        .email(rs.getString(EnumMapper.EMAIL.getFullName()))
        .dateOfBirth(dateBirth)
        .hireDate(dateHire)
        .phoneNumber(rs.getString(EnumMapper.PHONE_NUMBER.getFullName()))
        .jobTitle(UserDAO.JobTitle
            .valueOf(rs.getString(EnumMapper.JOB_TITLE.getFullName())))
        .projectStatus(UserDAO.ProjectStatus
            .valueOf(rs.getString(EnumMapper.STATUS.getFullName())))
        .build();
  }
}
