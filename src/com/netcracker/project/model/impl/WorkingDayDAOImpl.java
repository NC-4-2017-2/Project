package com.netcracker.project.model.impl;

import com.netcracker.project.model.entity.WorkingDay;
import java.math.BigInteger;
import java.util.Collection;
import javax.sql.DataSource;
import com.netcracker.project.model.WorkingDayDAO;

import java.util.Date;
import com.netcracker.project.model.impl.mappers.MapperDateConverter;
import com.netcracker.project.model.impl.mappers.WorkingDayMapper;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class WorkingDayDAOImpl implements WorkingDayDAO {

  private static final Logger logger = Logger.getLogger(WorkingDayDAOImpl.class);
  private JdbcTemplate template;
  private MapperDateConverter converter = new MapperDateConverter();

  public void setDataSource(DataSource dataSource) {
    template = new JdbcTemplate(dataSource);
  }

  @Override
  public void addHoursPerDay(WorkingDay workingDay) {
    logger.info("Entering addHoursPerDay(workingDay=" + workingDay + ")");
    String date = converter.convertDateToString(workingDay.getDate());

    template.update(ADD_HOURS_PER_DAY, new Object[]{
        workingDay.getWorkingDayId(),
        "WORKDAY" + workingDay.getWorkingDayId(),
        workingDay.getWorkingDayId(),
        date,
        workingDay.getWorkingDayId(),
        workingDay.getWeekNumber(),
        workingDay.getWorkingDayId(),
        workingDay.getWorkingHours(),
        workingDay.getWorkingDayId(),
        workingDay.getStatus().getId(),
        workingDay.getWorkingDayId(),
        workingDay.getUserId(),
        workingDay.getWorkingDayId(),
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
