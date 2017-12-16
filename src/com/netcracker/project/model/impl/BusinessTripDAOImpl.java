package com.netcracker.project.model.impl;

import com.netcracker.project.model.BusinessTripDAO;
import com.netcracker.project.model.entity.BusinessTrip;
import com.netcracker.project.model.entity.Status;
import com.netcracker.project.model.impl.mappers.BusinessTripMapper;
import com.netcracker.project.model.impl.mappers.MapperDateConverter;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigInteger;
import java.util.Collection;
import org.springframework.transaction.annotation.Transactional;

public class BusinessTripDAOImpl implements BusinessTripDAO {

  private static final Logger logger = Logger.getLogger(BusinessTripDAOImpl.class);
  private JdbcTemplate template;
  private MapperDateConverter converter = new MapperDateConverter();

  @Override
  public void createTrip(BusinessTrip trip) {
    logger.info("Entering createTrip(trip=" + trip + ")");
    template.update(CREATE_TRIP, new Object[]{
        "BUSINESS_TRIP " + trip.getBusinessTripId(),
        trip.getCountry(),
        converter.convertDateToString(trip.getStartDate()),
        converter.convertDateToString(trip.getEndDate()),
        trip.getStatus().getId(),
        trip.getUserId(),
        trip.getAuthorId(),
        trip.getProjectId(),
        trip.getPmId()
    });
  }

  public void setDataSource(DataSource dataSource) {
    template = new JdbcTemplate(dataSource);
  }

  @Transactional
  @Override
  public void updateTrip(BusinessTrip trip) {
    logger.info("Entering updateTrip(trip=" + trip + ")");
    updateCountry(trip.getCountry(), trip.getBusinessTripId());
    updateStartDate(converter.convertDateToString(trip.getStartDate()),
        trip.getBusinessTripId());
    updateEndDate(converter.convertDateToString(trip.getEndDate()),
        trip.getBusinessTripId());
    updateStatus(trip.getStatus(), trip.getBusinessTripId());
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

  private void updateCountry(String country, BigInteger businessTripId) {
    logger.info(
        "Entering updateCountry(country=" + country + "," + " businessTripId="
            + businessTripId + ")");
    template.update(UPDATE_COUNTRY, country, businessTripId);
  }

  private void updateStartDate(String startDate, BigInteger businessTripId) {
    logger.info("Entering updateStartDate(startDate=" + startDate + ","
        + " businessTripId=" + businessTripId + ")");
    template.update(UPDATE_START_DATE, startDate, businessTripId);
  }

  private void updateEndDate(String endDate, BigInteger businessTripId) {
    logger.info(
        "Entering updateEndDate(endDate=" + endDate + "," + " businessTripId="
            + businessTripId + ")");
    template.update(UPDATE_END_DATE, endDate, businessTripId);
  }

  private void updateStatus(Status status, BigInteger businessTripId) {
    logger.info(
        "Entering updateStatus(status=" + status + "," + " businessTripId="
            + businessTripId + ")");
    template.update(UPDATE_STATUS, status.getId(), businessTripId);
  }
}