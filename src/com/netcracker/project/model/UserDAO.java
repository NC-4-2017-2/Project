package com.netcracker.project.model;

import java.io.File;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

import com.netcracker.project.model.entity.User;
import org.springframework.stereotype.Component;

public interface UserDAO {

    User createUser(User user);

    User findUserByUserId(BigInteger id);

    User findUserByLogin(String login);

    Collection<User> findUserByLastNameAndFirstName(String lastName, String firstName);

    void updatePhoneNumber(BigInteger id, String phoneNumber);

    void updateEmail(BigInteger id, String email);

    void updatePassword(BigInteger id, String password);

    void updatePhoto(BigInteger id, File photo);

    void updateProjectStatus(BigInteger id, Integer status);

    Collection<WorkPeriod> findWorkPeriodsByUserId(BigInteger id);

    Collection<WorkPeriod> findWorkPeriodsByProjectId(BigInteger id);

    Collection<WorkPeriod> findWorkPeriodByUserIdAndProjectId(BigInteger userId, BigInteger projectId);

    Collection<WorkPeriod> findWorkPeriodByProjectIdAndStatus(BigInteger projectId, Integer status);

    void createWorkPeriod(WorkPeriod workPeriod);

    void updateWorkingPeriodEndDateByUserId(UserDAO.WorkPeriod workPeriod);

    void updateWorkingPeriodStatusByUserId(UserDAO.WorkPeriod workPeriod);

    enum JobTitle {
        PROJECT_MANAGER(0), LINE_MANAGER(1), SOFTWARE_ENGINEER(2);

        private int id;

        JobTitle(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    enum ProjectStatus {
        WORKING(0), TRANSIT(1);

        private int id;

        ProjectStatus(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    enum UserStatus {
        WORKING(0), FIRED(1);

        private int id;

        UserStatus(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    enum UserRole {
        ROLE_ADMIN(0), ROLE_PM(1), ROLE_LM(2), ROLE_SE(3);

        private int id;

        UserRole(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    class WorkPeriod {

        BigInteger workPeriodId, userId, projectId;
        Date startWorkDate, endWorkDate;
        WorkPeriodStatus workPeriodStatus;

        public WorkPeriod() {

        }

        public WorkPeriod(BigInteger workPeriodId, BigInteger userId, BigInteger projectId,
                          Date startWorkDate, Date endWorkDate, WorkPeriodStatus workPeriodStatus) {
            this.workPeriodId = workPeriodId;
            this.userId = userId;
            this.projectId = projectId;
            this.startWorkDate = startWorkDate;
            this.endWorkDate = endWorkDate;
            this.workPeriodStatus = workPeriodStatus;
        }

        public BigInteger getWorkPeriodId() {
            return workPeriodId;
        }

        public void setWorkPeriodId(BigInteger workPeriodId) {
            this.workPeriodId = workPeriodId;
        }

        public BigInteger getUserId() {
            return userId;
        }

        public void setUserId(BigInteger userId) {
            this.userId = userId;
        }

        public BigInteger getProjectId() {
            return projectId;
        }

        public void setProjectId(BigInteger projectId) {
            this.projectId = projectId;
        }

        public Date getStartWorkDate() {
            return startWorkDate;
        }

        public void setStartWorkDate(Date startWorkDate) {
            this.startWorkDate = startWorkDate;
        }

        public Date getEndWorkDate() {
            return endWorkDate;
        }

        public void setEndWorkDate(Date endWorkDate) {
            this.endWorkDate = endWorkDate;
        }

        public WorkPeriodStatus getWorkPeriodStatus() {
            return workPeriodStatus;
        }

        public void setWorkPeriodStatus(WorkPeriodStatus workPeriodStatus) {
            this.workPeriodStatus = workPeriodStatus;
        }

        public enum WorkPeriodStatus {
            WORKING(0), FIRED(1);

            private int id;

            WorkPeriodStatus(int id) {
                this.id = id;
            }

            public int getId() {
                return id;
            }
        }

        @Override
        public String toString() {
            return "WorkPeriod{" +
                "workPeriodId=" + workPeriodId +
                ", userId=" + userId +
                ", projectId=" + projectId +
                ", startWorkDate=" + startWorkDate +
                ", endWorkDate=" + endWorkDate +
                ", workPeriodStatus=" + workPeriodStatus +
                '}';
        }
    }

    String CREATE_USER = "INSERT ALL " +
            "INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION,OBJECT_VERSION) VALUES (101,NULL,1,?,NULL,1) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (1,101,?,NULL,NULL) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (2,101,?,NULL,NULL) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (3,101,?,NULL,NULL) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (4,101,NULL,?,NULL) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (5,101,NULL,?,NULL) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (6,101,?,NULL,NULL) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (8,101,NULL,NULL,?) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (9,101,NULL,NULL,?) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (10,101,?,NULL,NULL) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (11,101,?,NULL,NULL) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (12,101,NULL,NULL,?) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (13,101,NULL,NULL,?) " +
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
            "SET ATTRIBUTES.VALUE = ? " +
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

    String FIND_WORKING_PERIOD_BY_PROJECT_ID_AND_STATUS = "SELECT WORK_PERIOD.OBJECT_ID AS WORK_PERIOD_ID, USER_ID.OBJECT_ID AS USER_ID, PROJECT_ID.OBJECT_ID AS PROJECT_ID, " +
        "START_DATE_ATTR.DATE_VALUE AS START_DATE, END_DATE_ATTR.DATE_VALUE AS END_DATE, STATUS_ATTR_VALUE.VALUE AS STATUS " +
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

}
