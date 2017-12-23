package com.netcracker.project.model.impl;

import com.netcracker.project.model.entity.WorkingDay;
import java.math.BigInteger;
import java.util.Collection;
import javax.sql.DataSource;
import com.netcracker.project.model.WorkingDayDAO;

import java.util.Date;
import com.netcracker.project.services.impl.DateConverterService;
import com.netcracker.project.model.impl.mappers.WorkingDayMapper;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@ImportResource("classpath:Spring-WorkingDay.xml")
public class WorkingDayDAOImpl implements WorkingDayDAO {

  private static final Logger logger = Logger.getLogger(WorkingDayDAOImpl.class);
  private JdbcTemplate template;
  private DateConverterService converter = new DateConverterService();

  public void setDataSource(DataSource dataSource) {
    template = new JdbcTemplate(dataSource);
  }

  @Override
  public void addHoursPerDay(WorkingDay workingDay) {
    logger.info("Entering addHoursPerDay(workingDay=" + workingDay + ")");

    template.update(ADD_HOURS_PER_DAY, new Object[]{
        "WORKDAY" + workingDay.getUserId(),
        workingDay.getDate(),
        workingDay.getWeekNumber(),
        workingDay.getWorkingHours(),
        workingDay.getStatus().getId(),
        workingDay.getUserId(),
        workingDay.getPmId()
    });
  }

  @Override
  public Collection<WorkingDay> findHoursPerPeriod(BigInteger userId,
      Date startDate,
      Date endDate) {
    logger.info("Entering findHoursPerPeriod(projectId=" + userId + ", " + "startDate=" + startDate + ", " + "endDate=" + endDate + ")");

    return template.query(FIND_HOURS_PER_PERIOD, new Object[]{userId,
        startDate, endDate}, new WorkingDayMapper());
  }
}
