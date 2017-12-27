package com.netcracker.project.model;

import com.netcracker.project.model.entity.User;
import com.netcracker.project.model.entity.WorkPeriod;
import java.io.File;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;

public interface UserDAO {

    User createUser(User user);

    User findUserByUserId(BigInteger id);

    User findUserByLogin(String login);

    Collection<User> findUserByLastNameAndFirstName(String lastName, String firstName);

    Collection<User> findUserByProjectId(BigInteger projectId);

    Map<String, String> getAllUserName();

    void updatePhoneNumber(BigInteger id, String phoneNumber);

    void updateEmail(BigInteger id, String email);

    void updatePassword(BigInteger id, String password);

    void updatePhoto(BigInteger id, String photo);

    void updateProjectStatus(BigInteger id, Integer status);

    Collection<WorkPeriod> findWorkPeriodsByUserId(BigInteger id);

    Collection<WorkPeriod> findWorkPeriodsByProjectId(BigInteger id);

    Collection<WorkPeriod> findWorkPeriodByUserIdAndProjectId(BigInteger userId, BigInteger projectId);

    Collection<WorkPeriod> findWorkPeriodByProjectIdAndStatus(BigInteger projectId, Integer status);

    void createWorkPeriod(WorkPeriod workPeriod);

    void updateWorkingPeriodEndDateByUserId(WorkPeriod workPeriod);

    void updateWorkingPeriodStatusByUserId(WorkPeriod workPeriod);

    Integer findIfPMExists(BigInteger id);

    String CREATE_USER = "INSERT ALL " +
            "INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION,OBJECT_VERSION) VALUES (OBJECT_SEQUENCE.NEXTVAL,NULL,1,?,NULL,1) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (1,OBJECT_SEQUENCE.CURRVAL,?,NULL,NULL) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (2,OBJECT_SEQUENCE.CURRVAL,?,NULL,NULL) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (3,OBJECT_SEQUENCE.CURRVAL,?,NULL,NULL) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (4,OBJECT_SEQUENCE.CURRVAL,NULL,?,NULL) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (5,OBJECT_SEQUENCE.CURRVAL,NULL,?,NULL) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (6,OBJECT_SEQUENCE.CURRVAL,?,NULL,NULL) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (8,OBJECT_SEQUENCE.CURRVAL,NULL,NULL,?) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (9,OBJECT_SEQUENCE.CURRVAL,NULL,NULL,?) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (10,OBJECT_SEQUENCE.CURRVAL,?,NULL,NULL) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (11,OBJECT_SEQUENCE.CURRVAL,?,NULL,NULL) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (12,OBJECT_SEQUENCE.CURRVAL,NULL,NULL,?) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (13,OBJECT_SEQUENCE.CURRVAL,NULL,NULL,?) " +
            "SELECT * " +
            "FROM Dual";

    String FIND_USER_BY_USER_ID = "SELECT EMP.OBJECT_ID AS USER_ID, EMP_LAST_NAME.VALUE AS LAST_NAME, EMP_FIRST_NAME.VALUE AS FIRST_NAME, EMP_EMAIL.VALUE AS EMAIL, " +
            "EMP_BIRTH_DATE.DATE_VALUE AS BIRTH_DATE, EMP_HIRE_DATE.DATE_VALUE AS HIRE_DATE, EMP_PHONE_NUMBER.VALUE AS PHONE_NUMBER, " +
            "EMP_JOB_TITLE_VALUE.VALUE AS JOB_TITLE, EMP_PROJECT_STATUS_VALUE.VALUE AS STATUS " +
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

    String FIND_USER_BY_LOGIN = "SELECT EMP.OBJECT_ID AS USER_ID, EMP_LAST_NAME.VALUE AS LAST_NAME, EMP_FIRST_NAME.VALUE AS FIRST_NAME, EMP_EMAIL.VALUE AS EMAIL, " +
            "EMP_BIRTH_DATE.DATE_VALUE AS BIRTH_DATE, EMP_HIRE_DATE.DATE_VALUE AS HIRE_DATE, EMP_PHONE_NUMBER.VALUE AS PHONE_NUMBER ," +
            "EMP_JOB_TITLE_VALUE.VALUE AS JOB_TITLE, EMP_PROJECT_STATUS_VALUE.VALUE AS STATUS, " +
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

    String FIND_USER_BY_LAST_NAME_AND_FIRST_NAME = "SELECT EMP.OBJECT_ID AS USER_ID, EMP_LAST_NAME.VALUE AS LAST_NAME, EMP_FIRST_NAME.VALUE AS FIRST_NAME, EMP_EMAIL.VALUE AS EMAIL, " +
        "EMP_BIRTH_DATE.DATE_VALUE AS BIRTH_DATE, EMP_HIRE_DATE.DATE_VALUE AS HIRE_DATE, EMP_PHONE_NUMBER.VALUE AS PHONE_NUMBER , " +
        "EMP_JOB_TITLE_VALUE.VALUE AS JOB_TITLE, EMP_PROJECT_STATUS_VALUE.VALUE AS STATUS, " +
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
        "LOWER(EMP_LAST_NAME.VALUE) = LOWER(:1) AND " +
        "EMP_FIRST_NAME.ATTR_ID = 2 AND " +
        "EMP_FIRST_NAME.OBJECT_ID = EMP.OBJECT_ID AND " +
        "LOWER(EMP_FIRST_NAME.VALUE) = LOWER(:2) AND " +
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
        "EMP_PASSWORD.ATTR_ID = 11 AND " +
        "EMP_PASSWORD.OBJECT_ID = EMP.OBJECT_ID AND " +
        "EMP_ROLE.ATTR_ID = 12 AND " +
        "EMP_ROLE.OBJECT_ID = EMP.OBJECT_ID AND " +
        "EMP_ROLE_VALUE.ATTR_ID = EMP_ROLE.ATTR_ID AND " +
        "EMP_ROLE_VALUE.LIST_VALUE_ID = EMP_ROLE.LIST_VALUE_ID";

    String FIND_USER_BY_PROJECT_ID = "SELECT EMP_ID.OBJECT_ID AS USER_ID, EMP_LAST_NAME.VALUE AS LAST_NAME, EMP_FIRST_NAME.VALUE AS FIRST_NAME, EMP_EMAIL.VALUE AS EMAIL, "
        + "EMP_BIRTH_DATE.DATE_VALUE AS BIRTH_DATE, EMP_HIRE_DATE.DATE_VALUE AS HIRE_DATE, EMP_PHONE_NUMBER.VALUE AS PHONE_NUMBER, "
        + "EMP_JOB_TITLE_VALUE.VALUE AS JOB_TITLE, EMP_PROJECT_STATUS_VALUE.VALUE AS STATUS "
        + "FROM OBJECTS EMP_ID, OBJECTS PROJECT_ID, "
        + "ATTRIBUTES EMP_LAST_NAME, ATTRIBUTES EMP_FIRST_NAME, ATTRIBUTES EMP_EMAIL, "
        + "ATTRIBUTES EMP_BIRTH_DATE, ATTRIBUTES EMP_HIRE_DATE, ATTRIBUTES EMP_PHONE_NUMBER, "
        + "ATTRIBUTES EMP_JOB_TITLE, ATTRIBUTES EMP_PROJECT_STATUS, "
        + "OBJREFERENCE EMP_PROJECT_REF, "
        + "LISTVALUE EMP_JOB_TITLE_VALUE, LISTVALUE EMP_PROJECT_STATUS_VALUE "
        + "WHERE EMP_PROJECT_REF.ATTR_ID = 19 AND "
        + "PROJECT_ID.OBJECT_ID = ? AND "
        + "EMP_PROJECT_REF.OBJECT_ID = PROJECT_ID.OBJECT_ID AND "
        + "EMP_PROJECT_REF.REFERENCE = EMP_ID.OBJECT_ID AND "
        + "EMP_LAST_NAME.ATTR_ID = 1 AND "
        + "EMP_LAST_NAME.OBJECT_ID = EMP_ID.OBJECT_ID AND "
        + "EMP_FIRST_NAME.ATTR_ID = 2 AND "
        + "EMP_FIRST_NAME.OBJECT_ID = EMP_ID.OBJECT_ID AND "
        + "EMP_EMAIL.ATTR_ID = 3 AND "
        + "EMP_EMAIL.OBJECT_ID = EMP_ID.OBJECT_ID AND "
        + "EMP_BIRTH_DATE.ATTR_ID = 4 AND "
        + "EMP_BIRTH_DATE.OBJECT_ID = EMP_ID.OBJECT_ID AND "
        + "EMP_HIRE_DATE.ATTR_ID = 5 AND "
        + "EMP_HIRE_DATE.OBJECT_ID = EMP_ID.OBJECT_ID AND "
        + "EMP_PHONE_NUMBER.ATTR_ID = 6 AND "
        + "EMP_PHONE_NUMBER.OBJECT_ID = EMP_ID.OBJECT_ID AND "
        + "EMP_JOB_TITLE.ATTR_ID = 8 AND "
        + "EMP_JOB_TITLE.OBJECT_ID = EMP_ID.OBJECT_ID AND "
        + "EMP_JOB_TITLE_VALUE.ATTR_ID = 8 AND "
        + "EMP_JOB_TITLE_VALUE.LIST_VALUE_ID = EMP_JOB_TITLE.LIST_VALUE_ID AND "
        + "EMP_PROJECT_STATUS.ATTR_ID = 9 AND "
        + "EMP_PROJECT_STATUS.OBJECT_ID = EMP_ID.OBJECT_ID AND "
        + "EMP_PROJECT_STATUS_VALUE.ATTR_ID = 9 AND "
        + "EMP_PROJECT_STATUS_VALUE.LIST_VALUE_ID = EMP_PROJECT_STATUS.LIST_VALUE_ID";

    String UPDATE_PHONE_NUMBER = "UPDATE ATTRIBUTES " +
            "SET ATTRIBUTES.VALUE = ? " +
            "WHERE ATTRIBUTES.OBJECT_ID = ? AND " +
            "ATTRIBUTES.ATTR_ID = 6";

    String UPDATE_EMAIL = "UPDATE ATTRIBUTES " +
            "SET ATTRIBUTES.VALUE = ? " +
            "WHERE ATTRIBUTES.OBJECT_ID = ? AND " +
            "ATTRIBUTES.ATTR_ID = 3";

    String UPDATE_PASSWORD = "UPDATE ATTRIBUTES " +
            "SET ATTRIBUTES.VALUE = ? " +
            "WHERE ATTRIBUTES.OBJECT_ID = ? AND " +
            "ATTRIBUTES.ATTR_ID = 11";
//TODO change status to enum
    String UPDATE_USER_PROJECT_STATUS = "UPDATE ATTRIBUTES " +
            "SET ATTRIBUTES.LIST_VALUE_ID = ? " +
            "WHERE ATTRIBUTES.OBJECT_ID = ? AND " +
            "ATTRIBUTES.ATTR_ID = 9";

    String FIND_WORK_PERIOD_BY_USER_ID = "SELECT WORK_PERIOD.OBJECT_ID AS WORK_PERIOD_ID, USER_ID.OBJECT_ID AS USER_ID, PROJECT_ID.OBJECT_ID AS PROJECT_ID, " +
            "START_DATE_ATTR.DATE_VALUE AS START_DATE, END_DATE_ATTR.DATE_VALUE AS END_DATE, STATUS_ATTR_VALUE.VALUE AS STATUS " +
            "FROM OBJECTS USER_ID, OBJECTS PROJECT_ID, OBJECTS WORK_PERIOD, " +
            "OBJREFERENCE USER_WP_REF, OBJREFERENCE PROJECT_WP_REF, " +
            "ATTRIBUTES START_DATE_ATTR, ATTRIBUTES END_DATE_ATTR, ATTRIBUTES STATUS_ATTR, " +
            "LISTVALUE STATUS_ATTR_VALUE " +
            "WHERE USER_WP_REF.ATTR_ID = 64 AND " +
            "WORK_PERIOD.OBJECT_ID = USER_WP_REF.OBJECT_ID AND " +
            "USER_ID.OBJECT_ID = USER_WP_REF.REFERENCE AND " +
            "USER_ID.OBJECT_ID = ? AND " +
            "START_DATE_ATTR.OBJECT_ID = WORK_PERIOD.OBJECT_ID AND " +
            "START_DATE_ATTR.ATTR_ID = 61 AND " +
            "END_DATE_ATTR.OBJECT_ID = WORK_PERIOD.OBJECT_ID AND " +
            "END_DATE_ATTR.ATTR_ID = 62 AND " +
            "STATUS_ATTR.OBJECT_ID = WORK_PERIOD.OBJECT_ID AND " +
            "STATUS_ATTR.ATTR_ID = 63 AND " +
            "STATUS_ATTR_VALUE.ATTR_ID = STATUS_ATTR.ATTR_ID AND " +
            "STATUS_ATTR_VALUE.LIST_VALUE_ID = STATUS_ATTR.LIST_VALUE_ID AND " +
            "PROJECT_WP_REF.ATTR_ID = 65 AND " +
            "PROJECT_ID.OBJECT_ID = PROJECT_WP_REF.REFERENCE AND " +
            "WORK_PERIOD.OBJECT_ID = PROJECT_WP_REF.OBJECT_ID";

    String FIND_WORK_PERIOD_BY_PROJECT_ID = "SELECT WORK_PERIOD.OBJECT_ID AS WORK_PERIOD_ID, USER_ID.OBJECT_ID AS USER_ID, PROJECT_ID.OBJECT_ID AS PROJECT_ID, " +
            "START_DATE_ATTR.DATE_VALUE AS START_DATE, END_DATE_ATTR.DATE_VALUE AS END_DATE, STATUS_ATTR_VALUE.VALUE AS STATUS " +
            "FROM OBJECTS USER_ID, OBJECTS PROJECT_ID, OBJECTS WORK_PERIOD, " +
            "OBJREFERENCE USER_WP_REF, OBJREFERENCE PROJECT_WP_REF, " +
            "ATTRIBUTES START_DATE_ATTR, ATTRIBUTES END_DATE_ATTR, ATTRIBUTES STATUS_ATTR," +
            "LISTVALUE STATUS_ATTR_VALUE " +
            "WHERE USER_WP_REF.ATTR_ID = 64 AND " +
            "WORK_PERIOD.OBJECT_ID = USER_WP_REF.OBJECT_ID AND " +
            "USER_ID.OBJECT_ID = USER_WP_REF.REFERENCE AND " +
            "START_DATE_ATTR.OBJECT_ID = WORK_PERIOD.OBJECT_ID AND " +
            "START_DATE_ATTR.ATTR_ID = 61 AND " +
            "END_DATE_ATTR.OBJECT_ID = WORK_PERIOD.OBJECT_ID AND " +
            "END_DATE_ATTR.ATTR_ID = 62 AND " +
            "STATUS_ATTR.OBJECT_ID = WORK_PERIOD.OBJECT_ID AND " +
            "STATUS_ATTR.ATTR_ID = 63 AND " +
            "STATUS_ATTR_VALUE.ATTR_ID = STATUS_ATTR.ATTR_ID AND " +
            "STATUS_ATTR_VALUE.LIST_VALUE_ID = STATUS_ATTR.LIST_VALUE_ID AND " +
            "PROJECT_WP_REF.ATTR_ID = 65 AND " +
            "PROJECT_ID.OBJECT_ID = PROJECT_WP_REF.REFERENCE AND " +
            "PROJECT_ID.OBJECT_ID = ? AND " +
            "WORK_PERIOD.OBJECT_ID = PROJECT_WP_REF.OBJECT_ID";

    String FIND_WORK_PERIOD_BY_USER_ID_AND_PROJECT_ID = "SELECT WORK_PERIOD.OBJECT_ID AS WORK_PERIOD_ID, USER_ID.OBJECT_ID AS USER_ID, PROJECT_ID.OBJECT_ID AS PROJECT_ID, " +
            "START_DATE_ATTR.DATE_VALUE AS START_DATE, END_DATE_ATTR.DATE_VALUE AS END_DATE, STATUS_ATTR_VALUE.VALUE AS STATUS " +
            "FROM OBJECTS USER_ID, OBJECTS PROJECT_ID, OBJECTS WORK_PERIOD, " +
            "OBJREFERENCE USER_WP_REF, OBJREFERENCE PROJECT_WP_REF, " +
            "ATTRIBUTES START_DATE_ATTR, ATTRIBUTES END_DATE_ATTR, ATTRIBUTES STATUS_ATTR, " +
            "LISTVALUE STATUS_ATTR_VALUE " +
            "WHERE USER_WP_REF.ATTR_ID = 64 AND " +
            "WORK_PERIOD.OBJECT_ID = USER_WP_REF.OBJECT_ID AND " +
            "USER_ID.OBJECT_ID = USER_WP_REF.REFERENCE AND " +
            "USER_ID.OBJECT_ID = ? AND " +
            "START_DATE_ATTR.OBJECT_ID = WORK_PERIOD.OBJECT_ID AND " +
            "START_DATE_ATTR.ATTR_ID = 61 AND " +
            "END_DATE_ATTR.OBJECT_ID = WORK_PERIOD.OBJECT_ID AND " +
            "END_DATE_ATTR.ATTR_ID = 62 AND " +
            "STATUS_ATTR.OBJECT_ID = WORK_PERIOD.OBJECT_ID AND " +
            "STATUS_ATTR.ATTR_ID = 63 AND " +
            "STATUS_ATTR_VALUE.ATTR_ID = STATUS_ATTR.ATTR_ID AND " +
            "STATUS_ATTR_VALUE.LIST_VALUE_ID = STATUS_ATTR.LIST_VALUE_ID AND " +
            "PROJECT_WP_REF.ATTR_ID = 65 AND " +
            "PROJECT_ID.OBJECT_ID = PROJECT_WP_REF.REFERENCE AND " +
            "PROJECT_ID.OBJECT_ID = ? AND " +
            "WORK_PERIOD.OBJECT_ID = PROJECT_WP_REF.OBJECT_ID";

    String UPDATE_WORKING_PERIOD_END_DATE = "UPDATE ATTRIBUTES " +
            "SET ATTRIBUTES.DATE_VALUE = ? " +
            "WHERE ATTRIBUTES.ATTR_ID = 62 AND " +
            "ATTRIBUTES.OBJECT_ID = " +
            "(SELECT WORKING_PERIOD_ID.OBJECT_ID " +
            "FROM OBJECTS WORKING_PERIOD_ID, OBJECTS USER_ID, OBJECTS PROJECT_ID, " +
            "OBJREFERENCE USER_WP_REF, OBJREFERENCE PROJECT_WP_REF, " +
            "ATTRIBUTES START_DATE_ATTR, ATTRIBUTES END_DATE_ATTR " +
            "WHERE USER_WP_REF.ATTR_ID = 64 AND " +
            "WORKING_PERIOD_ID.OBJECT_ID = USER_WP_REF.OBJECT_ID AND " +
            "USER_ID.OBJECT_ID = USER_WP_REF.REFERENCE AND " +
            "USER_ID.OBJECT_ID = ? AND " +
            "START_DATE_ATTR.OBJECT_ID = USER_WP_REF.OBJECT_ID AND " +
            "START_DATE_ATTR.ATTR_ID = 61 AND " +
            "END_DATE_ATTR.OBJECT_ID = USER_WP_REF.OBJECT_ID AND " +
            "END_DATE_ATTR.ATTR_ID = 62 AND " +
            "PROJECT_WP_REF.ATTR_ID = 65 AND " +
            "PROJECT_ID.OBJECT_ID = PROJECT_WP_REF.REFERENCE AND " +
            "PROJECT_ID.OBJECT_ID = ? AND " +
            "USER_WP_REF.OBJECT_ID = PROJECT_WP_REF.OBJECT_ID)";

    String FIND_WORKING_PERIOD_BY_PROJECT_ID_AND_STATUS = "SELECT WORK_PERIOD.OBJECT_ID AS WORK_PERIOD_ID, " +
        "USER_ID.OBJECT_ID AS USER_ID, PROJECT_ID.OBJECT_ID AS PROJECT_ID, " +
        "to_char(START_DATE_ATTR.DATE_VALUE, 'yyyy-MM-dd') AS START_DATE," +
        " to_char(END_DATE_ATTR.DATE_VALUE, 'yyyy-MM-dd') AS END_DATE," +
        " STATUS_ATTR_VALUE.VALUE AS STATUS " +
        "FROM OBJECTS USER_ID, OBJECTS PROJECT_ID, OBJECTS WORK_PERIOD, " +
        "OBJREFERENCE USER_WP_REF, OBJREFERENCE PROJECT_WP_REF, " +
        "ATTRIBUTES START_DATE_ATTR, ATTRIBUTES END_DATE_ATTR, ATTRIBUTES STATUS_ATTR, " +
        "LISTVALUE STATUS_ATTR_VALUE " +
        "WHERE USER_WP_REF.ATTR_ID = 64 AND " +
        "WORK_PERIOD.OBJECT_ID = USER_WP_REF.OBJECT_ID AND " +
        "USER_ID.OBJECT_ID = USER_WP_REF.REFERENCE AND " +
        "START_DATE_ATTR.OBJECT_ID = WORK_PERIOD.OBJECT_ID AND " +
        "START_DATE_ATTR.ATTR_ID = 61 AND " +
        "END_DATE_ATTR.OBJECT_ID = WORK_PERIOD.OBJECT_ID AND " +
        "END_DATE_ATTR.ATTR_ID = 62 AND " +
        "STATUS_ATTR.OBJECT_ID = WORK_PERIOD.OBJECT_ID AND " +
        "STATUS_ATTR.ATTR_ID = 63 AND " +
        "STATUS_ATTR_VALUE.ATTR_ID = STATUS_ATTR.ATTR_ID AND " +
        "STATUS_ATTR.LIST_VALUE_ID = ? AND " +
        "STATUS_ATTR_VALUE.LIST_VALUE_ID = STATUS_ATTR.LIST_VALUE_ID AND " +
        "PROJECT_WP_REF.ATTR_ID = 65 AND " +
        "PROJECT_ID.OBJECT_ID = PROJECT_WP_REF.REFERENCE AND " +
        "PROJECT_ID.OBJECT_ID = ? AND " +
        "WORK_PERIOD.OBJECT_ID = PROJECT_WP_REF.OBJECT_ID";

    String UPDATE_WORKING_PERIOD_STATUS = "UPDATE ATTRIBUTES " +
        "SET ATTRIBUTES.LIST_VALUE_ID = ? " +
        "WHERE ATTRIBUTES.ATTR_ID = 63 AND " +
        "ATTRIBUTES.OBJECT_ID = " +
        "(SELECT WORKING_PERIOD_ID.OBJECT_ID " +
        "FROM OBJECTS WORKING_PERIOD_ID, OBJECTS USER_ID, OBJECTS PROJECT_ID, " +
        "OBJREFERENCE USER_WP_REF, OBJREFERENCE PROJECT_WP_REF, " +
        "ATTRIBUTES START_DATE_ATTR, ATTRIBUTES END_DATE_ATTR " +
        "WHERE USER_WP_REF.ATTR_ID = 64 AND " +
        "WORKING_PERIOD_ID.OBJECT_ID = USER_WP_REF.OBJECT_ID AND " +
        "USER_ID.OBJECT_ID = USER_WP_REF.REFERENCE AND " +
        "USER_ID.OBJECT_ID = ? AND " +
        "START_DATE_ATTR.OBJECT_ID = USER_WP_REF.OBJECT_ID AND " +
        "START_DATE_ATTR.ATTR_ID = 61 AND " +
        "END_DATE_ATTR.OBJECT_ID = USER_WP_REF.OBJECT_ID AND " +
        "END_DATE_ATTR.ATTR_ID = 62 AND " +
        "PROJECT_WP_REF.ATTR_ID = 65 AND " +
        "PROJECT_ID.OBJECT_ID = PROJECT_WP_REF.REFERENCE AND " +
        "PROJECT_ID.OBJECT_ID = ? AND " +
        "USER_WP_REF.OBJECT_ID = PROJECT_WP_REF.OBJECT_ID)";

    String CREATE_WORK_PERIOD = "INSERT ALL " +
        "INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION,OBJECT_VERSION) VALUES (1000,NULL,8,?,NULL,1) " +
        "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (61,1000,NULL,?,NULL) " +
        "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (62,1000,NULL,?,NULL) " +
        "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (63,1000,?,NULL,NULL) " +
        "INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (64,1000,?) " +
        "INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (65,1000,?) " +
        "SELECT * " +
        "FROM Dual";

    String GET_ALL_USERS = "SELECT EMP.OBJECT_ID AS USER_ID, EMP_LAST_NAME.VALUE ||' '|| EMP_FIRST_NAME.VALUE AS FULL_NAME "
        + "FROM OBJTYPE EMP_TYPE, OBJECTS EMP,  "
        + "ATTRIBUTES EMP_LAST_NAME, ATTRIBUTES EMP_FIRST_NAME, ATTRIBUTES EMP_EMAIL,  "
        + "ATTRIBUTES EMP_BIRTH_DATE, ATTRIBUTES EMP_HIRE_DATE, ATTRIBUTES EMP_PHONE_NUMBER,  "
        + "ATTRIBUTES EMP_JOB_TITLE, ATTRIBUTES EMP_PROJECT_STATUS,  "
        + "LISTVALUE EMP_JOB_TITLE_VALUE, LISTVALUE EMP_PROJECT_STATUS_VALUE  "
        + "WHERE EMP_TYPE.OBJECT_TYPE_ID = 1 AND  "
        + "EMP.OBJECT_TYPE_ID = EMP_TYPE.OBJECT_TYPE_ID AND  "
        + "EMP_LAST_NAME.ATTR_ID = 1 AND  "
        + "EMP_LAST_NAME.OBJECT_ID = EMP.OBJECT_ID AND  "
        + "EMP_FIRST_NAME.ATTR_ID = 2 AND  "
        + "EMP_FIRST_NAME.OBJECT_ID = EMP.OBJECT_ID AND  "
        + "EMP_EMAIL.ATTR_ID = 3 AND  "
        + "EMP_EMAIL.OBJECT_ID = EMP.OBJECT_ID AND  "
        + "EMP_BIRTH_DATE.ATTR_ID = 4 AND  "
        + "EMP_BIRTH_DATE.OBJECT_ID = EMP.OBJECT_ID AND  "
        + "EMP_HIRE_DATE.ATTR_ID = 5 AND  "
        + "EMP_HIRE_DATE.OBJECT_ID = EMP.OBJECT_ID AND  "
        + "EMP_PHONE_NUMBER.ATTR_ID = 6 AND  "
        + "EMP_PHONE_NUMBER.OBJECT_ID = EMP.OBJECT_ID AND  "
        + "EMP_JOB_TITLE.ATTR_ID = 8 AND  "
        + "EMP_JOB_TITLE.OBJECT_ID = EMP.OBJECT_ID AND  "
        + "EMP_JOB_TITLE_VALUE.ATTR_ID = 8 AND  "
        + "EMP_JOB_TITLE_VALUE.LIST_VALUE_ID = EMP_JOB_TITLE.LIST_VALUE_ID AND "
        + "EMP_PROJECT_STATUS.ATTR_ID = 9 AND "
        + "EMP_PROJECT_STATUS.OBJECT_ID = EMP.OBJECT_ID AND "
        + "EMP_PROJECT_STATUS_VALUE.ATTR_ID = 9 AND "
        + "EMP_PROJECT_STATUS_VALUE.LIST_VALUE_ID = EMP_PROJECT_STATUS.LIST_VALUE_ID";

    String FIND_PM_IF_EXISTS = "SELECT COUNT(USER_ID.OBJECT_ID) "
        +"FROM OBJTYPE USER_TYPE, OBJECTS USER_ID, "
        +"ATTRIBUTES USER_LAST_NAME, ATTRIBUTES USER_FIRST_NAME, ATTRIBUTES USER_EMAIL, "
        +"ATTRIBUTES USER_BIRTH_DATE, ATTRIBUTES USER_HIRE_DATE, ATTRIBUTES USER_PHONE_NUMBER, "
        +"ATTRIBUTES USER_JOB_TITLE, ATTRIBUTES USER_PROJECT_STATUS, "
        +"LISTVALUE USER_JOB_TITLE_VALUE, LISTVALUE USER_PROJECT_STATUS_VALUE "
        +"WHERE USER_TYPE.OBJECT_TYPE_ID = 1 AND "
        +"USER_ID.OBJECT_TYPE_ID = USER_TYPE.OBJECT_TYPE_ID AND "
        +"USER_ID.OBJECT_ID = ? AND "
        +"USER_LAST_NAME.ATTR_ID = 1 AND "
        +"USER_LAST_NAME.OBJECT_ID = USER_ID.OBJECT_ID AND "
        +"USER_FIRST_NAME.ATTR_ID = 2 AND "
        +"USER_FIRST_NAME.OBJECT_ID = USER_ID.OBJECT_ID AND "
        +"USER_EMAIL.ATTR_ID = 3 AND "
        +"USER_EMAIL.OBJECT_ID = USER_ID.OBJECT_ID AND "
        +"USER_BIRTH_DATE.ATTR_ID = 4 AND "
        +"USER_BIRTH_DATE.OBJECT_ID = USER_ID.OBJECT_ID AND "
        +"USER_HIRE_DATE.ATTR_ID = 5 AND "
        +"USER_HIRE_DATE.OBJECT_ID = USER_ID.OBJECT_ID AND "
        +"USER_PHONE_NUMBER.ATTR_ID = 6 AND "
        +"USER_PHONE_NUMBER.OBJECT_ID = USER_ID.OBJECT_ID AND "
        +"USER_JOB_TITLE.ATTR_ID = 8 AND "
        +"USER_JOB_TITLE.OBJECT_ID = USER_ID.OBJECT_ID AND "
        +"USER_JOB_TITLE_VALUE.ATTR_ID = 8 AND "
        +"USER_JOB_TITLE_VALUE.LIST_VALUE_ID = USER_JOB_TITLE.LIST_VALUE_ID AND "
        +"USER_JOB_TITLE_VALUE.LIST_VALUE_ID = 0 AND "
        +"USER_PROJECT_STATUS.ATTR_ID = 9 AND "
        +"USER_PROJECT_STATUS.OBJECT_ID = USER_ID.OBJECT_ID AND "
        +"USER_PROJECT_STATUS_VALUE.ATTR_ID = 9 AND "
        +"USER_PROJECT_STATUS_VALUE.LIST_VALUE_ID = USER_PROJECT_STATUS.LIST_VALUE_ID";
}
