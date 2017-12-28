package com.netcracker.project.model.impl;

import com.netcracker.project.model.BusinessTripDAO;
import com.netcracker.project.model.entity.BusinessTrip;
import com.netcracker.project.model.impl.mappers.BusinessTripMapper;
import java.util.Date;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigInteger;
import java.util.Collection;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@ImportResource("classpath:Spring-BusinessTrip.xml")
public class BusinessTripDAOImpl implements BusinessTripDAO {

  private static final Logger logger = Logger
      .getLogger(BusinessTripDAOImpl.class);
  private JdbcTemplate template;

  public void setDataSource(DataSource dataSource) {
    template = new JdbcTemplate(dataSource);
  }

  @Override
  public void createTrip(BusinessTrip trip) {
    logger.info("Entering createTrip(trip=" + trip + ")");
    template.update(CREATE_TRIP, new Object[]{
        "BUSINESS_TRIP " + trip.getProjectId(),
        trip.getCountry(),
        trip.getStartDate(),
        trip.getEndDate(),
        trip.getStatus().getId(),
        trip.getUserId(),
        trip.getAuthorId(),
        trip.getProjectId(),
        trip.getPmId()
    });
  }

  @Transactional
  @Override
  public void updateTrip(BusinessTrip trip) {
    logger.info("Entering updateTrip(trip=" + trip + ")");
    updateCountry(trip.getCountry(), trip.getBusinessTripId());
    updateStartDate(trip.getStartDate(), trip.getBusinessTripId());
    updateEndDate(trip.getEndDate(), trip.getBusinessTripId());
  }

  @Override
  public BusinessTrip findBusinessTripById(BigInteger id) {
    logger.info("Entering findBusinessTripById(id=" + id + ")");
    return template.queryForObject(FIND_TRIP_BY_TRIP_ID, new Object[]{id},
        new BusinessTripMapper());
  }

  @Override
  public Collection<BusinessTrip> findTripByUserId(BigInteger id) {
    logger.info("Entering findTripByUserId(id=" + id + ")");
    return template.query(FIND_TRIP_BY_USER_ID, new Object[]{id},
        new BusinessTripMapper());
  }

  @Override
  public Collection<BusinessTrip> findTripByProjectId(BigInteger id) {
    logger.info("Entering findTripByProjectId(id=" + id + ")");
    return template.query(FIND_TRIP_BY_PROJECT_ID, new Object[]{id},
        new BusinessTripMapper());
  }

  @Override
  public Collection<BusinessTrip> findTripByPMIdAndStatus(BigInteger pmId,
      Integer status) {
    logger.info("Entering findTripByPMIdAndStatus(pmId=" + pmId + ","
        + " status=" + status + ")");
    return template.query(FIND_BUSINESS_TRIP_BY_PM_ID_AND_STATUS,
        new Object[]{pmId, status},
        new BusinessTripMapper());
  }

  @Override
  public Collection<BusinessTrip> findTripByUserIdAndStatus(BigInteger userId,
      Integer status) {
    logger.info("Entering findTripByUserIdAndStatus(userId=" + userId + ","
        + " status=" + status + ")");
    return template.query(FIND_BUSINESS_TRIP_BY_USER_ID_AND_STATUS,
        new Object[]{userId, status},
        new BusinessTripMapper());
  }

  @Override
  public void updateBusinessTripStatus(BigInteger businessTripId,
      Integer statusId) {
    logger.info(
        "Entering updateBusinessTripStatus(statusId=" + statusId + ", " + " businessTripId="
            + businessTripId + ")");
    template.update(UPDATE_STATUS, statusId, businessTripId);
  }

  private void updateCountry(String country, BigInteger businessTripId) {
    logger.info(
        "Entering updateCountry(country=" + country + "," + " businessTripId="
            + businessTripId + ")");
    template.update(UPDATE_COUNTRY, country, businessTripId);
  }

  private void updateStartDate(Date startDate, BigInteger businessTripId) {
    logger.info("Entering updateStartDate(startDate=" + startDate + ","
        + " businessTripId=" + businessTripId + ")");
    template.update(UPDATE_START_DATE, startDate, businessTripId);
  }

  private void updateEndDate(Date endDate, BigInteger businessTripId) {
    logger.info(
        "Entering updateEndDate(endDate=" + endDate + "," + " businessTripId="
            + businessTripId + ")");
    template.update(UPDATE_END_DATE, endDate, businessTripId);
  }

  @Override
  public Integer findIfBusinessTripExists(BigInteger id) {
    logger.info("Entering findIfBusinessTripExists(id=" + id + ")");
    return template
        .queryForObject(FIND_BUSINESS_TRIP_IF_EXISTS, new Object[]{id},
            Integer.class);
  }
}
