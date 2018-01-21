package com.netcracker.project.model.impl.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.netcracker.project.model.entity.SecurityUser;
import org.springframework.jdbc.core.RowMapper;

public class SecurityUserMapper implements RowMapper<SecurityUser> {

  public SecurityUser mapRow(ResultSet rs, int rowNum) throws SQLException {
    SecurityUser securityUser = new SecurityUser();
    securityUser.setUsername(rs.getString(EnumMapper.USER_NAME.getFullName()));
    securityUser.setPassword(rs.getString(EnumMapper.USER_PASSWORD.getFullName()));
    securityUser.setRole(rs.getString(EnumMapper.USER_ROLE.getFullName()));
    return securityUser;
  }
}
