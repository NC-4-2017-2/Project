package com.netcracker.project.model.impl;

import com.netcracker.project.model.entity.SecurityUser;
import com.netcracker.project.model.impl.mappers.SecurityUserMapper;
import javax.sql.DataSource;
import com.netcracker.project.model.SecurityUserDAO;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

public class SecurityUserDAOImpl implements SecurityUserDAO {

  private static final Logger logger = Logger.getLogger(SecurityUserDAOImpl.class);

  private JdbcTemplate template;

  public void setDataSource(DataSource dataSource) {
    this.template = new JdbcTemplate(dataSource);
  }

  public SecurityUser findByUsername(String userName) {
    logger.info("Entering findByUsername(" + userName + ")");
    return template.queryForObject(FIND_BY_USERNAME, new Object[]{userName}, new SecurityUserMapper());
  }
}
