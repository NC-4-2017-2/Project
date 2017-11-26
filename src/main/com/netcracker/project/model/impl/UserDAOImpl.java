package main.com.netcracker.project.model.impl;

import main.com.netcracker.project.model.entity.User;
import main.com.netcracker.project.model.UserDAO;
import main.com.netcracker.project.model.impl.mappers.UserMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.File;
import java.math.BigInteger;

public class UserDAOImpl implements UserDAO {

    private JdbcTemplate template;

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


    public void setDataSource(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public void updateUser(BigInteger id, User user) {

    }

    @Override
    public User findUserByUserId(BigInteger id) {
        return template.queryForObject(FIND_USER_BY_USER_ID, new Object[]{id}, new UserMapper());
    }

    @Override
    public User findUserByLogin(String login) {
        return null;
    }

    @Override
    public void updatePhoneNumber(BigInteger id, String phoneNumber) {

    }

    @Override
    public void updateEmail(BigInteger id, String email) {

    }

    @Override
    public void updatePassword(BigInteger id, String password) {

    }

    @Override
    public void updatePhoto(BigInteger id, File photo) {

    }

    @Override
    public void updateWorkingPeriodByUserId(BigInteger userId, BigInteger projectId) {

    }
}
