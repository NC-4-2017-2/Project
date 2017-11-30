package main.com.netcracker.project.model.impl;

import main.com.netcracker.project.model.entity.User;
import main.com.netcracker.project.model.UserDAO;
import main.com.netcracker.project.model.impl.mappers.UserMapper;
import main.com.netcracker.project.model.impl.mappers.WorkPeriodMapper;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.File;
import java.math.BigInteger;
import java.util.Collection;

public class UserDAOImpl implements UserDAO {

    private Logger logger = Logger.getLogger(UserDAOImpl.class);

    private JdbcTemplate template;

    private static final String CREATE_USER = "INSERT ALL " +
            "INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) VALUES (101,NULL,1,?,NULL) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (1,101,?,NULL,NULL) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (2,101,?,NULL,NULL) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (3,101,?,NULL,NULL) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (4,101,?,NULL,NULL) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (5,101,?,NULL,NULL) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (6,101,?,NULL,NULL) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (8,101,NULL,NULL,?) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (9,101,NULL,NULL,?) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (10,101,?,NULL,NULL) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (11,101,?,NULL,NULL) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (12,101,NULL,NULL,?) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (13,101,NULL,NULL,?) " +
            "SELECT * " +
            "FROM Dual";

    private static final String FIND_USER_BY_USER_ID = "SELECT EMP.OBJECT_ID AS USER_ID, EMP_LAST_NAME.VALUE AS LAST_NAME, EMP_FIRST_NAME.VALUE AS FIRST_NAME, EMP_EMAIL.VALUE AS EMAIL, " +
            "EMP_BIRTH_DATE.VALUE AS BIRTH_DATE, EMP_HIRE_DATE.VALUE AS HIRE_DATE, EMP_PHONE_NUMBER.VALUE AS PHONE_NUMBER, " +
            "EMP_JOB_TITLE_VALUE.VALUE AS JOB_TITLE, EMP_PROJECT_STATUS_VALUE.VALUE AS PROJECT_STATUS " +
            "FROM OBJTYPE EMP_TYPE, OBJECTS EMP, " +
            "ATTRIBUTES EMP_LAST_NAME, ATTRIBUTES EMP_FIRST_NAME, ATTRIBUTES EMP_EMAIL, " +
            "ATTRIBUTES EMP_BIRTH_DATE, ATTRIBUTES EMP_HIRE_DATE, ATTRIBUTES EMP_PHONE_NUMBER, " +
            "ATTRIBUTES EMP_JOB_TITLE, ATTRIBUTES EMP_PROJECT_STATUS, " +
            "LISTVALUE EMP_JOB_TITLE_VALUE, LISTVALUE EMP_PROJECT_STATUS_VALUE " +
            "WHERE EMP_TYPE.OBJECT_TYPE_ID = 1 AND " +
            "EMP.OBJECT_TYPE_ID = EMP_TYPE.OBJECT_TYPE_ID AND " +
            "EMP.OBJECT_ID = ? AND " +
            "EMP_LAST_NAME.ATTR_ID = 1 AND " +
            "EMP_LAST_NAME.OBJECT_ID = EMP.OBJECT_ID AND " +
            "EMP_FIRST_NAME.ATTR_ID = 2 AND " +
            "EMP_FIRST_NAME.OBJECT_ID = EMP.OBJECT_ID AND " +
            "EMP_EMAIL.ATTR_ID = 3 AND " +
            "EMP_EMAIL.OBJECT_ID = EMP.OBJECT_ID AND " +
            "EMP_BIRTH_DATE.ATTR_ID = 4 AND " +
            "EMP_BIRTH_DATE.OBJECT_ID = EMP.OBJECT_ID AND " +
            "EMP_HIRE_DATE.ATTR_ID = 5 AND " +
            "EMP_HIRE_DATE.OBJECT_ID = EMP.OBJECT_ID AND " +
            "EMP_PHONE_NUMBER.ATTR_ID = 6 AND " +
            "EMP_PHONE_NUMBER.OBJECT_ID = EMP.OBJECT_ID AND " +
            "EMP_JOB_TITLE.ATTR_ID = 8 AND " +
            "EMP_JOB_TITLE.OBJECT_ID = EMP.OBJECT_ID AND " +
            "EMP_JOB_TITLE_VALUE.ATTR_ID = 8 AND " +
            "EMP_JOB_TITLE_VALUE.LIST_VALUE_ID = EMP_JOB_TITLE.LIST_VALUE_ID AND " +
            "EMP_PROJECT_STATUS.ATTR_ID = 9 AND " +
            "EMP_PROJECT_STATUS.OBJECT_ID = EMP.OBJECT_ID AND " +
            "EMP_PROJECT_STATUS_VALUE.ATTR_ID = 9 AND " +
            "EMP_PROJECT_STATUS_VALUE.LIST_VALUE_ID = EMP_PROJECT_STATUS.LIST_VALUE_ID";

    private static final String FIND_USER_BY_LOGIN = "SELECT EMP.OBJECT_ID AS USER_ID, EMP_LAST_NAME.VALUE AS LAST_NAME, EMP_FIRST_NAME.VALUE AS FIRST_NAME, EMP_EMAIL.VALUE AS EMAIL, " +
            "EMP_BIRTH_DATE.VALUE AS BIRTH_DATE, EMP_HIRE_DATE.VALUE AS HIRE_DATE, EMP_PHONE_NUMBER.VALUE AS PHONE_NUMBER ," +
            "EMP_JOB_TITLE_VALUE.VALUE AS JOB_TITLE, EMP_PROJECT_STATUS_VALUE.VALUE AS PROJECT_STATUS, " +
            "EMP_LOGIN.VALUE AS LOGIN, EMP_PASSWORD.VALUE AS PASSWORD, EMP_ROLE_VALUE.VALUE AS ROLE " +
            "FROM OBJTYPE EMP_TYPE, OBJECTS EMP, " +
            "ATTRIBUTES EMP_LAST_NAME, ATTRIBUTES EMP_FIRST_NAME, ATTRIBUTES EMP_EMAIL, " +
            "ATTRIBUTES EMP_BIRTH_DATE, ATTRIBUTES EMP_HIRE_DATE, ATTRIBUTES EMP_PHONE_NUMBER, " +
            "ATTRIBUTES EMP_JOB_TITLE, ATTRIBUTES EMP_PROJECT_STATUS, " +
            "LISTVALUE EMP_JOB_TITLE_VALUE, LISTVALUE EMP_PROJECT_STATUS_VALUE, LISTVALUE EMP_ROLE_VALUE, " +
            "ATTRIBUTES EMP_LOGIN, ATTRIBUTES EMP_PASSWORD, ATTRIBUTES EMP_ROLE " +
            "WHERE EMP_TYPE.OBJECT_TYPE_ID = 1 AND " +
            "EMP.OBJECT_TYPE_ID = EMP_TYPE.OBJECT_TYPE_ID AND " +
            "EMP_LAST_NAME.ATTR_ID = 1 AND " +
            "EMP_LAST_NAME.OBJECT_ID = EMP.OBJECT_ID AND " +
            "EMP_FIRST_NAME.ATTR_ID = 2 AND " +
            "EMP_FIRST_NAME.OBJECT_ID = EMP.OBJECT_ID AND " +
            "EMP_EMAIL.ATTR_ID = 3 AND " +
            "EMP_EMAIL.OBJECT_ID = EMP.OBJECT_ID AND " +
            "EMP_BIRTH_DATE.ATTR_ID = 4 AND " +
            "EMP_BIRTH_DATE.OBJECT_ID = EMP.OBJECT_ID AND " +
            "EMP_HIRE_DATE.ATTR_ID = 5 AND " +
            "EMP_HIRE_DATE.OBJECT_ID = EMP.OBJECT_ID AND " +
            "EMP_PHONE_NUMBER.ATTR_ID = 6 AND " +
            "EMP_PHONE_NUMBER.OBJECT_ID = EMP.OBJECT_ID AND " +
            "EMP_JOB_TITLE.ATTR_ID = 8 AND " +
            "EMP_JOB_TITLE.OBJECT_ID = EMP.OBJECT_ID AND " +
            "EMP_JOB_TITLE_VALUE.ATTR_ID = EMP_JOB_TITLE.ATTR_ID AND " +
            "EMP_JOB_TITLE_VALUE.LIST_VALUE_ID = EMP_JOB_TITLE.LIST_VALUE_ID AND " +
            "EMP_PROJECT_STATUS.ATTR_ID = 9 AND " +
            "EMP_PROJECT_STATUS.OBJECT_ID = EMP.OBJECT_ID AND " +
            "EMP_PROJECT_STATUS_VALUE.ATTR_ID = EMP_PROJECT_STATUS.ATTR_ID AND " +
            "EMP_PROJECT_STATUS_VALUE.LIST_VALUE_ID = EMP_PROJECT_STATUS.LIST_VALUE_ID AND " +
            "EMP_LOGIN.ATTR_ID = 10 AND " +
            "EMP_LOGIN.OBJECT_ID = EMP.OBJECT_ID AND " +
            "EMP_LOGIN.VALUE = ? AND " +
            "EMP_PASSWORD.ATTR_ID = 11 AND " +
            "EMP_PASSWORD.OBJECT_ID = EMP.OBJECT_ID AND " +
            "EMP_ROLE.ATTR_ID = 12 AND " +
            "EMP_ROLE.OBJECT_ID = EMP.OBJECT_ID AND " +
            "EMP_ROLE_VALUE.ATTR_ID = EMP_ROLE.ATTR_ID AND " +
            "EMP_ROLE_VALUE.LIST_VALUE_ID = EMP_ROLE.LIST_VALUE_ID";

    private static final String UPDATE_PHONE_NUMBER = "UPDATE ATTRIBUTES " +
            "SET ATTRIBUTES.VALUE = ? " +
            "WHERE ATTRIBUTES.OBJECT_ID = ? AND " +
            "ATTRIBUTES.ATTR_ID = 6";

    private static final String UPDATE_EMAIL = "UPDATE ATTRIBUTES " +
            "SET ATTRIBUTES.VALUE = ? " +
            "WHERE ATTRIBUTES.OBJECT_ID = ? AND " +
            "ATTRIBUTES.ATTR_ID = 3";

    private static final String UPDATE_PASSWORD = "UPDATE ATTRIBUTES " +
            "SET ATTRIBUTES.VALUE = ? " +
            "WHERE ATTRIBUTES.OBJECT_ID = ? AND " +
            "ATTRIBUTES.ATTR_ID = 11";

    private static final String UPDATE_USER_PROJECT_STATUS = "UPDATE ATTRIBUTES "
        + "SET ATTRIBUTES.LIST_VALUE_ID = ? "
        + "WHERE ATTRIBUTES.OBJECT_ID = ? AND "
        + "ATTRIBUTES.ATTR_ID = 9";

    private static final String FIND_WORK_PERIOD_BY_USER_ID = "SELECT WORK_PERIOD.OBJECT_ID AS PERIOD_ID, USER_ID.OBJECT_ID AS USER_ID, PROJECT_ID.OBJECT_ID AS PROJECT_ID, " +
            "START_DATE_ATTR.VALUE AS START_DATE, END_DATE_ATTR.VALUE AS END_DATE " +
            "FROM OBJECTS USER_ID, OBJECTS PROJECT_ID, OBJECTS WORK_PERIOD, " +
            "OBJREFERENCE USER_WP_REF, OBJREFERENCE PROJECT_WP_REF, " +
            "ATTRIBUTES START_DATE_ATTR, ATTRIBUTES END_DATE_ATTR " +
            "WHERE USER_WP_REF.ATTR_ID = 63 AND " +
            "WORK_PERIOD.OBJECT_ID = USER_WP_REF.OBJECT_ID AND " +
            "USER_ID.OBJECT_ID = USER_WP_REF.REFERENCE AND " +
            "USER_ID.OBJECT_ID = ? AND " +
            "START_DATE_ATTR.OBJECT_ID = WORK_PERIOD.OBJECT_ID AND " +
            "START_DATE_ATTR.ATTR_ID = 61 AND " +
            "END_DATE_ATTR.OBJECT_ID = WORK_PERIOD.OBJECT_ID AND " +
            "END_DATE_ATTR.ATTR_ID = 62 AND " +
            "PROJECT_WP_REF.ATTR_ID = 64 AND " +
            "PROJECT_ID.OBJECT_ID = PROJECT_WP_REF.REFERENCE AND " +
            "WORK_PERIOD.OBJECT_ID = PROJECT_WP_REF.OBJECT_ID";

    private static final String FIND_WORK_PERIOD_BY_PROJECT_ID = "SELECT WORK_PERIOD.OBJECT_ID AS PERIOD_ID, USER_ID.OBJECT_ID AS USER_ID, PROJECT_ID.OBJECT_ID AS PROJECT_ID, " +
            "START_DATE_ATTR.VALUE AS START_DATE, END_DATE_ATTR.VALUE AS END_DATE " +
            "FROM OBJECTS USER_ID, OBJECTS PROJECT_ID, OBJECTS WORK_PERIOD, " +
            "OBJREFERENCE USER_WP_REF, OBJREFERENCE PROJECT_WP_REF, " +
            "ATTRIBUTES START_DATE_ATTR, ATTRIBUTES END_DATE_ATTR " +
            "WHERE USER_WP_REF.ATTR_ID = 63 AND " +
            "WORK_PERIOD.OBJECT_ID = USER_WP_REF.OBJECT_ID AND " +
            "USER_ID.OBJECT_ID = USER_WP_REF.REFERENCE AND " +
            "START_DATE_ATTR.OBJECT_ID = WORK_PERIOD.OBJECT_ID AND " +
            "START_DATE_ATTR.ATTR_ID = 61 AND " +
            "END_DATE_ATTR.OBJECT_ID = WORK_PERIOD.OBJECT_ID AND " +
            "END_DATE_ATTR.ATTR_ID = 62 AND " +
            "PROJECT_WP_REF.ATTR_ID = 64 AND " +
            "PROJECT_ID.OBJECT_ID = PROJECT_WP_REF.REFERENCE AND " +
            "PROJECT_ID.OBJECT_ID = ? AND " +
            "WORK_PERIOD.OBJECT_ID = PROJECT_WP_REF.OBJECT_ID";

    private static final String FIND_WORK_PERIOD_BY_USER_ID_AND_PROJECT_ID = "SELECT WORK_PERIOD.OBJECT_ID AS PERIOD_ID, USER_ID.OBJECT_ID AS USER_ID, PROJECT_ID.OBJECT_ID AS PROJECT_ID, " +
            "START_DATE_ATTR.VALUE AS START_DATE, END_DATE_ATTR.VALUE AS END_DATE " +
            "FROM OBJECTS USER_ID, OBJECTS PROJECT_ID, OBJECTS WORK_PERIOD," +
            "OBJREFERENCE USER_WP_REF, OBJREFERENCE PROJECT_WP_REF, " +
            "ATTRIBUTES START_DATE_ATTR, ATTRIBUTES END_DATE_ATTR " +
            "WHERE USER_WP_REF.ATTR_ID = 63 AND " +
            "WORK_PERIOD.OBJECT_ID = USER_WP_REF.OBJECT_ID AND " +
            "USER_ID.OBJECT_ID = USER_WP_REF.REFERENCE AND " +
            "USER_ID.OBJECT_ID = ? AND " +
            "START_DATE_ATTR.OBJECT_ID = WORK_PERIOD.OBJECT_ID AND " +
            "START_DATE_ATTR.ATTR_ID = 61 AND " +
            "END_DATE_ATTR.OBJECT_ID = WORK_PERIOD.OBJECT_ID AND " +
            "END_DATE_ATTR.ATTR_ID = 62 AND " +
            "PROJECT_WP_REF.ATTR_ID = 64 AND " +
            "PROJECT_ID.OBJECT_ID = PROJECT_WP_REF.REFERENCE AND " +
            "PROJECT_ID.OBJECT_ID = ? AND " +
            "WORK_PERIOD.OBJECT_ID = PROJECT_WP_REF.OBJECT_ID";

    private static final String UPDATE_WORKING_PERIOD_END_DATE = "UPDATE ATTRIBUTES " +
            "SET ATTRIBUTES.VALUE = ? " +
            "WHERE ATTRIBUTES.ATTR_ID = 62 AND " +
            "ATTRIBUTES.OBJECT_ID = " +
            "(SELECT WORKING_PERIOD_ID.OBJECT_ID " +
            "FROM OBJECTS WORKING_PERIOD_ID, OBJECTS USER_ID, OBJECTS PROJECT_ID, " +
            "OBJREFERENCE USER_WP_REF, OBJREFERENCE PROJECT_WP_REF, " +
            "ATTRIBUTES START_DATE_ATTR, ATTRIBUTES END_DATE_ATTR " +
            "WHERE USER_WP_REF.ATTR_ID = 63 AND " +
            "WORKING_PERIOD_ID.OBJECT_ID = USER_WP_REF.OBJECT_ID AND " +
            "USER_ID.OBJECT_ID = USER_WP_REF.REFERENCE AND " +
            "USER_ID.OBJECT_ID = ? AND " +
            "START_DATE_ATTR.OBJECT_ID = USER_WP_REF.OBJECT_ID AND " +
            "START_DATE_ATTR.ATTR_ID = 61 AND " +
            "END_DATE_ATTR.OBJECT_ID = USER_WP_REF.OBJECT_ID AND " +
            "END_DATE_ATTR.ATTR_ID = 62 AND " +
            "PROJECT_WP_REF.ATTR_ID = 64 AND " +
            "PROJECT_ID.OBJECT_ID = PROJECT_WP_REF.REFERENCE AND " +
            "PROJECT_ID.OBJECT_ID = ? AND " +
            "USER_WP_REF.OBJECT_ID = PROJECT_WP_REF.OBJECT_ID)";

    public void setDataSource(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public User createUser(User user) {
        logger.info("Entering insert(user=" + user + ")");
        this.template.update(CREATE_USER, new Object[]{
                user.getLastName() + " " + user.getFirstName(),
                user.getLastName(),
                user.getFirstName(),
                user.getEmail(),
                user.getDateOfBirth(),
                user.getHireDate(),
                user.getPhoneNumber(),
                user.getJobTitle().getId(),
                user.getProjectStatus().getId(),
                user.getLogin(),
                user.getPassword(),
                user.getUserRole().getId(),
                user.getUserStatus().getId()
        });

        return user;
    }

    @Override
    public User findUserByUserId(BigInteger id) {
        logger.info("Entering findUserByUserId()");
        return template.queryForObject(FIND_USER_BY_USER_ID, new Object[]{id}, new UserMapper());
    }

    @Override
    public User findUserByLogin(String login) {
        logger.info("Entering findUserByLogin()");
        return template.queryForObject(FIND_USER_BY_LOGIN, new Object[]{login}, new UserMapper());
    }

    @Override
    public void updatePhoneNumber(BigInteger id, String phoneNumber) {
        logger.info("Entering updatePhoneNumber(id=" + id + "," + " phoneNumber=" + phoneNumber + ")");
        template.update(UPDATE_PHONE_NUMBER, phoneNumber, id);
    }

    @Override
    public void updateEmail(BigInteger id, String email) {
        logger.info("Entering updateEmail(id=" + id + "," + " email=" + email + ")");
        template.update(UPDATE_EMAIL, email, id);
    }

    @Override
    public void updatePassword(BigInteger id, String password) {
        logger.info("Entering updatePassword(id=" + id + "," + " password=" + password + ")");
        template.update(UPDATE_PASSWORD, password, id);
    }

    @Override
    public void updatePhoto(BigInteger id, File photo) {

    }

    @Override
    public void updateProjectStatus(BigInteger id, Integer status) {
        logger.info("Entering updateProjectStatus(id=" + id + "," + " status=" + status + ")");
        template.update(UPDATE_USER_PROJECT_STATUS, status, id);
    }

    @Override
    public Collection<WorkPeriod> findWorkPeriodsByUserId(BigInteger id) {
        logger.info("Entering findWorkPeriodsByUserId(id=" + id + ")");
        return template.query(FIND_WORK_PERIOD_BY_USER_ID, new Object[]{id}, new WorkPeriodMapper());
    }

    @Override
    public Collection<WorkPeriod> findWorkPeriodsByProjectId(BigInteger id) {
        logger.info("Entering findWorkPeriodsByProjectId(id=" + id + ")");
        return template.query(FIND_WORK_PERIOD_BY_PROJECT_ID, new Object[]{id}, new WorkPeriodMapper());
    }

    @Override
    public Collection<WorkPeriod> findWorkPeriodByUserIdAndProjectId(BigInteger userId, BigInteger projectId) {
        logger.info("Entering findWorkPeriodByUserIdAndProjectId(userId=" + userId + "," + " projectId=" + projectId + ")");
        return template.query(FIND_WORK_PERIOD_BY_USER_ID_AND_PROJECT_ID, new Object[]{userId, projectId}, new WorkPeriodMapper());
    }

    @Override
    public void updateWorkingPeriodByUserId(BigInteger userId, BigInteger projectId, UserDAO.WorkPeriod workPeriod) {
        logger.info("Entering findWorkPeriodByUserIdAndProjectId(userId=" + userId + "," + " projectId=" + projectId + "," + " UserDAO.WorkPeriod=" + workPeriod + ")");
        template.update(UPDATE_WORKING_PERIOD_END_DATE, workPeriod.getEndWorkDate().toString(), userId, projectId);
    }

}
