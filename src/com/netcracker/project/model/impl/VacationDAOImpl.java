package com.netcracker.project.model.impl;

import com.netcracker.project.model.VacationDAO;
import com.netcracker.project.model.entity.Vacation;
import com.netcracker.project.model.enums.Status;
import com.netcracker.project.model.impl.mappers.VacationMapper;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class VacationDAOImpl implements VacationDAO {

  private static final Logger logger = Logger.getLogger(VacationDAOImpl.class);
  private JdbcTemplate template;

  @Autowired
  public void setDataSource(DataSource dataSource) {
    template = new JdbcTemplate(dataSource);
  }

  @Override
  public void createVacation(Vacation vacation) {
    logger.info("Entering createVacation(vacation=" + vacation + ")");
    template.update(CREATE_VACATION, new Object[]{
        "VACATION " + vacation.getVacationId(),
        vacation.getUserId(),
        vacation.getProjectId(),
        vacation.getStartDate(),
        vacation.getEndDate(),
        vacation.getPmStatus(),
        vacation.getLmStatus(),
        vacation.getPmId(),
        vacation.getLmId()
    });
  }

  @Override
  @Transactional
  public void updateVacation(BigInteger id, Vacation vacation) {
    logger.info(
        "Entering updateVacation(id=" + id + "," + " vacation=" + vacation
            + ")");
    updateStartDate(vacation.getStartDate(), vacation.getVacationId());
    updateEndDate(vacation.getEndDate(), vacation.getVacationId());
    updatePmStatus(vacation.getPmStatus(), vacation.getVacationId());
    updateLmStatus(vacation.getLmStatus(), vacation.getVacationId());
  }

  @Override
  public Collection<Vacation> findVacationByUserId(BigInteger id) {
    logger.info("Entering findVacationByUserId(id=" + id + ")");
    return template.query(FIND_VACATION_BY_USER_ID,
        new Object[]{id}, new VacationMapper());
  }

  @Override
  public Collection<Vacation> findVacationByProjectId(BigInteger id) {
    logger.info("Entering findVacationByProjectId(id=" + id + ")");
    return template.query(FIND_VACATION_BY_PROJECT_ID,
        new Object[]{id}, new VacationMapper());
  }

  @Override
  public Collection<Vacation> findVacationByUserIdAndPmStatus(BigInteger id,
      Integer status) {
    logger.info(
        "Entering findVacationByUserIdAndPmStatus(id=" + id + "," + " status="
            + status + ")");
    return template.query(FIND_VACATION_BY_USER_ID_AND_PM_STATUS,
        new Object[]{id, status}, new VacationMapper());
  }

  @Override
  public Collection<Vacation> findVacationByUserIdAndLmStatus(BigInteger id,
      Integer status) {
    logger.info(
        "Entering findVacationByUserIdAndLmStatus(id=" + id + "," + " status="
            + status + ")");
    return template.query(FIND_VACATION_BY_USER_ID_AND_LM_STATUS,
        new Object[]{id, status}, new VacationMapper());
  }

  private void updateStartDate(Date startDate, BigInteger vacationId) {
    logger.info(
        "Entering updateStartDate(startDate=" + startDate + "," + " vacationId="
            + vacationId + ")");
    template.update(UPDATE_START_DATE, startDate, vacationId);
  }

  private void updateEndDate(Date endDate, BigInteger vacationId) {
    logger.info(
        "Entering updateEndDate(endDate=" + endDate + "," + " vacationId="
            + vacationId + ")");
    template.update(UPDATE_END_DATE, endDate, vacationId);
  }

  private void updatePmStatus(Status status, BigInteger vacationId) {
    logger.info(
        "Entering updatePmStatus(status=" + status + "," + " vacationId="
            + vacationId + ")");
    template.update(UPDATE_PM_STATUS, status.getId(), vacationId);
  }

  private void updateLmStatus(Status status, BigInteger vacationId) {
    logger.info(
        "Entering updateLmStatus(status=" + status + "," + " vacationId="
            + vacationId + ")");
    template.update(UPDATE_LM_STATUS, status.getId(), vacationId);
  }
}
