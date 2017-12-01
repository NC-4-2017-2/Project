package main.com.netcracker.project.model.impl;

import javax.sql.DataSource;
import main.com.netcracker.project.model.VacationDAO;
import main.com.netcracker.project.model.entity.Vacation;

import java.math.BigInteger;
import java.util.Collection;
import main.com.netcracker.project.model.impl.mappers.VacationMapper;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

public class VacationDAOImpl implements VacationDAO {

  private Logger logger = Logger.getLogger(VacationDAOImpl.class);

  private JdbcTemplate template;

  private static final String FIND_VACATION_BY_USER_ID_AND_PM_STATUS =
      "SELECT VACATION_ID.OBJECT_ID AS VACATION_ID, USER_ID.OBJECT_ID AS USER_ID, PROJECT_ID.OBJECT_ID AS PROJECT_ID, "
          + "PM_ID.OBJECT_ID AS PM_ID, LM_ID.OBJECT_ID AS LM_ID, START_DATE.VALUE AS START_DATE, END_DATE.VALUE AS END_DATE, PM_STATUS_VALUE.VALUE AS PM_STATUS, LM_STATUS_VALUE.VALUE AS LM_STATUS "
          + "FROM OBJECTS VACATION_ID, OBJECTS USER_ID, OBJECTS PROJECT_ID, OBJECTS PM_ID, OBJECTS LM_ID, "
          + "ATTRIBUTES START_DATE, ATTRIBUTES END_DATE, ATTRIBUTES PM_APPROVE, ATTRIBUTES LM_APPROVE, "
          + "OBJREFERENCE USER_VACATION_REF, OBJREFERENCE PROJECT_VACATION_REF, OBJREFERENCE PM_ID_PROJECT_APPROVE, OBJREFERENCE LM_ID_PROJECT_APPROVE, "
          + "LISTVALUE PM_STATUS_VALUE, LISTVALUE LM_STATUS_VALUE "
          + "WHERE USER_VACATION_REF.ATTR_ID = 45 AND "
          + "USER_ID.OBJECT_ID = ? AND "
          + "USER_ID.OBJECT_ID = USER_VACATION_REF.REFERENCE AND "
          + "VACATION_ID.OBJECT_ID = USER_VACATION_REF.OBJECT_ID AND "
          + "PROJECT_VACATION_REF.ATTR_ID = 46 AND "
          + "PROJECT_VACATION_REF.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PROJECT_ID.OBJECT_ID = PROJECT_VACATION_REF.REFERENCE AND "
          + "PM_ID_PROJECT_APPROVE.ATTR_ID = 47 AND "
          + "PM_ID_PROJECT_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PM_ID.OBJECT_ID = PM_ID_PROJECT_APPROVE.REFERENCE AND "
          + "LM_ID_PROJECT_APPROVE.ATTR_ID = 48 AND "
          + "LM_ID_PROJECT_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "LM_ID.OBJECT_ID = LM_ID_PROJECT_APPROVE.REFERENCE AND "
          + "START_DATE.ATTR_ID = 41 AND "
          + "START_DATE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "END_DATE.ATTR_ID = 42 AND "
          + "END_DATE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PM_APPROVE.ATTR_ID = 43 AND "
          + "PM_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "LM_APPROVE.ATTR_ID = 44 AND "
          + "LM_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PM_STATUS_VALUE.ATTR_ID = PM_APPROVE.ATTR_ID AND "
          + "PM_STATUS_VALUE.LIST_VALUE_ID = ? AND "
          + "PM_STATUS_VALUE.LIST_VALUE_ID = PM_APPROVE.LIST_VALUE_ID AND "
          + "LM_STATUS_VALUE.ATTR_ID = LM_APPROVE.ATTR_ID AND "
          + "LM_STATUS_VALUE.LIST_VALUE_ID = LM_APPROVE.LIST_VALUE_ID";

  private static final String FIND_VACATION_BY_USER_ID_AND_LM_STATUS =
      "SELECT VACATION_ID.OBJECT_ID AS VACATION_ID, USER_ID.OBJECT_ID AS USER_ID, PROJECT_ID.OBJECT_ID AS PROJECT_ID, "
          + "PM_ID.OBJECT_ID AS PM_ID, LM_ID.OBJECT_ID AS LM_ID, START_DATE.VALUE AS START_DATE, END_DATE.VALUE AS END_DATE, PM_STATUS_VALUE.VALUE AS PM_STATUS, LM_STATUS_VALUE.VALUE AS LM_STATUS "
          + "FROM OBJECTS VACATION_ID, OBJECTS USER_ID, OBJECTS PROJECT_ID, OBJECTS PM_ID, OBJECTS LM_ID, "
          + "ATTRIBUTES START_DATE, ATTRIBUTES END_DATE, ATTRIBUTES PM_APPROVE, ATTRIBUTES LM_APPROVE, "
          + "OBJREFERENCE USER_VACATION_REF, OBJREFERENCE PROJECT_VACATION_REF, OBJREFERENCE PM_ID_PROJECT_APPROVE, OBJREFERENCE LM_ID_PROJECT_APPROVE, "
          + "LISTVALUE PM_STATUS_VALUE, LISTVALUE LM_STATUS_VALUE "
          + "WHERE USER_VACATION_REF.ATTR_ID = 45 AND "
          + "USER_ID.OBJECT_ID = ? AND "
          + "USER_ID.OBJECT_ID = USER_VACATION_REF.REFERENCE AND "
          + "VACATION_ID.OBJECT_ID = USER_VACATION_REF.OBJECT_ID AND "
          + "PROJECT_VACATION_REF.ATTR_ID = 46 AND "
          + "PROJECT_VACATION_REF.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PROJECT_ID.OBJECT_ID = PROJECT_VACATION_REF.REFERENCE AND "
          + "PM_ID_PROJECT_APPROVE.ATTR_ID = 47 AND "
          + "PM_ID_PROJECT_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PM_ID.OBJECT_ID = PM_ID_PROJECT_APPROVE.REFERENCE AND "
          + "LM_ID_PROJECT_APPROVE.ATTR_ID = 48 AND "
          + "LM_ID_PROJECT_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "LM_ID.OBJECT_ID = LM_ID_PROJECT_APPROVE.REFERENCE AND "
          + "START_DATE.ATTR_ID = 41 AND "
          + "START_DATE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "END_DATE.ATTR_ID = 42 AND "
          + "END_DATE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PM_APPROVE.ATTR_ID = 43 AND "
          + "PM_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "LM_APPROVE.ATTR_ID = 44 AND "
          + "LM_APPROVE.OBJECT_ID = VACATION_ID.OBJECT_ID AND "
          + "PM_STATUS_VALUE.ATTR_ID = PM_APPROVE.ATTR_ID AND "
          + "PM_STATUS_VALUE.LIST_VALUE_ID = PM_APPROVE.LIST_VALUE_ID AND "
          + "LM_STATUS_VALUE.ATTR_ID = LM_APPROVE.ATTR_ID AND "
          + "LM_STATUS_VALUE.LIST_VALUE_ID = ? AND "
          + "LM_STATUS_VALUE.LIST_VALUE_ID = LM_APPROVE.LIST_VALUE_ID";

  public void setDataSource(DataSource dataSource) {
    template = new JdbcTemplate(dataSource);
  }

  @Override
  public void createVacation(Vacation vacation) {
    logger.info("Entering createVacation(vacation=" + vacation + ")");

  }

  @Override
  public void updateVacation(BigInteger id, Vacation vacation) {
    logger.info(
        "Entering updateVacation(id=" + id + "," + " vacation=" + vacation
            + ")");

  }

  @Override
  public Vacation findVacationByUserId(BigInteger id) {
    logger.info("Entering findVacationByUserId(id=" + id + ")");
    return null;
  }

  @Override
  public Vacation findVacationByProjectId(BigInteger id) {
    logger.info("Entering findVacationByProjectId(id=" + id + ")");
    return null;
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
}
