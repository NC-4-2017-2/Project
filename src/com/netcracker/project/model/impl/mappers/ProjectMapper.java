package com.netcracker.project.model.impl.mappers;

import com.netcracker.project.model.entity.Project;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ProjectMapper implements RowMapper<Project> {

  public Project mapRow(ResultSet rs, int i) throws SQLException {
    return new Project.ProjectBuilder()
        .projectId(
            new BigInteger(rs.getString(EnumMapper.PROJECT_ID.getFullName())))
        .name(rs.getString(EnumMapper.NAME.getFullName()))
        .startDate(rs.getDate(EnumMapper.START_DATE.getFullName()))
        .endDate(rs.getDate(EnumMapper.END_DATE.getFullName()))
        .build();
  }

}
