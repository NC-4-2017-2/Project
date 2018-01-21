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
        vacation.getStartDate(),
        vacation.getEndDate(),
        vacation.getPmStatus().getId(),
        vacation.getLmStatus().getId(),
        vacation.getUserId(),
        vacation.getProjectId(),
        vacation.getPmId(),
        vacation.getLmId()
    });
  }

  @Override
  @Transactional
  public void updateVacation(Vacation vacation) {
    logger.info(
        "Entering updateVacation(vacationId=" + vacation.getVacationId() + ","
            + " vacation=" + vacation
            + ")");
    updateStartDate(vacation.getStartDate(), vacation.getVacationId());
    updateEndDate(vacation.getEndDate(), vacation.getVacationId());
  }

  @Transactional
  @Override
  public void updateVacationStartAndEndDate(BigInteger vacationId,
      Date startDate, Date endDate) {
    logger.info(
        "Entering updateVacationStartAndEndDate(vacationId=" + vacationId + ","
            + " startDate=" + startDate + ", endDate=" + endDate
            + ")");
    updateStartDate(startDate, vacationId);
    updateEndDate(endDate, vacationId);
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

  @Override
  public Collection<Vacation> findVacationByUserIdAndPmAndLmStatus(
      BigInteger userId, Integer pmStatus, Integer lmStatus) {
    return template.query(FIND_VACATION_BY_USER_ID_AND_PM_AND_LM_STATUS,
        new Object[]{userId, pmStatus, lmStatus}, new VacationMapper());
  }

  @Override
  public Vacation findVacationByVacationId(BigInteger id) {
    logger.info(
        "Entering findVacationByVacationId(vacationId=" + id + ")");
    return template.queryForObject(FIND_VACATION_BY_ID, new Object[]{id},
        new VacationMapper());
  }

  @Override
  public Integer findVacationIfExist(BigInteger vacationId) {
    return template
        .queryForObject(FIND_VACATION_IF_EXIST, new Object[]{vacationId},
            Integer.class);
  }

  @Override
  public Collection<Vacation> findVacationByPMIdAndPMStatus(BigInteger pmId,
      Integer pmStatus) {
    logger.info(
        "Entering findVacationByPMIdAndPMStatus(pmId=" + pmId + ","
            + " pmStatus="
            + pmStatus + ")");
    return template
        .query(FIND_VACATION_BY_PM_ID_AND_STATUS, new Object[]{pmId, pmStatus},
            new VacationMapper());
  }

  @Override
  public Collection<Vacation> findVacationByLMIdAndLMStatus(BigInteger lmId,
      Integer lmStatus) {
    logger.info(
        "Entering findVacationByLMIdAndLMStatus(lmId=" + lmId + ","
            + " lmStatus="
            + lmStatus + ")");
    return template
        .query(FIND_VACATION_BY_LM_ID_AND_STATUS, new Object[]{lmId, lmStatus},
            new VacationMapper());
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

  @Override
  public void updatePmStatus(BigInteger vacationId, Integer status) {
    logger.info(
        "Entering updatePmStatus(status=" + status + "," + " vacationId="
            + vacationId + ")");
    template.update(UPDATE_PM_STATUS, status, vacationId);
  }

  @Override
  public void updateLmStatus(BigInteger vacationId, Integer status) {
    logger.info(
        "Entering updateLmStatus(status=" + status + "," + " vacationId="
            + vacationId + ")");
    template.update(UPDATE_LM_STATUS, status, vacationId);
  }

  private void updateProjectId(BigInteger projectId, BigInteger vacationId) {
    logger.info(
        "Entering updateProjectId(projectId=" + projectId + "," + " vacationId="
            + vacationId + ")");
    template.update(UPDATE_PROJECT_ID, projectId, vacationId);
  }

  private void updateUserId(BigInteger userId, BigInteger vacationId) {
    logger.info(
        "Entering updateProjectId(projectId=" + userId + "," + " vacationId="
            + vacationId + ")");
    template.update(UPDATE_USER_ID, userId, vacationId);
  }
}
