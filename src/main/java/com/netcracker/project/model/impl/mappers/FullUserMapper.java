package com.netcracker.project.model.impl.mappers;

import com.netcracker.project.model.entity.User;
import com.netcracker.project.model.enums.JobTitle;
import com.netcracker.project.model.enums.ProjectStatus;
import com.netcracker.project.model.enums.UserStatus;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class FullUserMapper implements RowMapper<User> {

  public User mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new User.UserBuilder()
        .userId(new BigInteger(rs.getString(EnumMapper.USER_ID.getFullName())))
        .lastName(rs.getString(EnumMapper.LAST_NAME.getFullName()))
        .firstName(rs.getString(EnumMapper.FIRST_NAME.getFullName()))
        .email(rs.getString(EnumMapper.EMAIL.getFullName()))
        .dateOfBirth(rs.getDate(EnumMapper.BIRTH_DATE.getFullName()))
        .hireDate(rs.getDate(EnumMapper.HIRE_DATE.getFullName()))
        .phoneNumber(rs.getString(EnumMapper.PHONE_NUMBER.getFullName()))
        .jobTitle(JobTitle
            .valueOf(rs.getString(EnumMapper.JOB_TITLE.getFullName())))
        .projectStatus(ProjectStatus
            .valueOf(rs.getString(EnumMapper.STATUS.getFullName())))
        .login(rs.getString(EnumMapper.USER_NAME.getFullName()))
        .userStatus(UserStatus
            .valueOf(rs.getString(EnumMapper.USER_SYSTEM_STATUS.getFullName())))
        .build();
  }
}
