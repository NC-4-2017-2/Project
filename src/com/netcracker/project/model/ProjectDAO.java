package com.netcracker.project.model;

import com.netcracker.project.model.entity.Project;
import com.netcracker.project.model.entity.Sprint;
import com.netcracker.project.model.enums.OCStatus;
import com.netcracker.project.model.impl.mappers.EnumMapper;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface ProjectDAO {

  void createProject(Project project);

  Project findProjectByProjectId(BigInteger id);

  Project findProjectByName(String name);

  List<Project> findProjectByDate(Date startDate);

  Collection<String> findAllOpenedProjects();

  List<BigInteger> getIdUsers(BigInteger projectId);

  void deleteUserByUserId(BigInteger userId, BigInteger projectID);

  void updateEndDate(BigInteger projectId, Date endDate);

  void updateStatus(BigInteger projectId, OCStatus ocStatus);

  void updatePM(BigInteger projectId, BigInteger userId);

  void addUser(BigInteger userId, BigInteger projectId);

  Collection<Sprint> getAllSprints(BigInteger projectID);

  void createSprint(Sprint sprint, BigInteger projectId);

  void updateSprintStatus(BigInteger sprintId, OCStatus ocStatus);

  void updateSprintEndDate(BigInteger sprintId, Date endDate);

  void updateSprintPlannedEndDate(BigInteger sprintId, Date endDate);

  BigInteger findProjectIdByUserLogin(String userLogin);

  BigInteger findProjectIdByPMLogin(String pmLogin);

  String CREATE_PROJECT =
      "INSERT ALL "
          + " INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION, OBJECT_VERSION) VALUES (201,NULL,2,'Project'||?,NULL, 1) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (14,201,?,NULL,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (15,201,NULL,?,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (16,201,NULL,?,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (17,201,NULL,NULL,?)  "
          + " INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (18,200,?)"
          + " SELECT * FROM dual";

  String INSERT_USER_TO_PROJECT =
      "INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (19,?,?)";

  String DELETE_USERS_IN_PROJECT =
      "DELETE FROM OBJREFERENCE WHERE OBJECT_ID = ? AND REFERENCE = ?";

  String FIND_PROJECT_BY_PROJECT_ID =
      "SELECT PR.OBJECT_ID AS " + EnumMapper.PROJECT_ID
          + ", PR_NAME.VALUE AS " + EnumMapper.NAME
          + ", START_DATE.DATE_VALUE AS " + EnumMapper.START_DATE
          + ", END_DATE.DATE_VALUE AS " + EnumMapper.END_DATE
          + ", PR_STATUS_VALUE.VALUE AS " + EnumMapper.STATUS
          + " FROM OBJTYPE PR_TYPE, OBJECTS PR, "
          + "ATTRIBUTES PR_NAME, ATTRIBUTES START_DATE, ATTRIBUTES END_DATE, "
          + "ATTRIBUTES PR_STATUS, "
          + "LISTVALUE PR_STATUS_VALUE "
          + "WHERE PR.OBJECT_ID = ? "
          + "AND PR_TYPE.CODE = 'PROJECT' "
          + "AND PR_NAME.ATTR_ID = 14 "
          + "AND PR_NAME.OBJECT_ID = PR.OBJECT_ID "
          + "AND START_DATE.ATTR_ID = 15 "
          + "AND START_DATE.OBJECT_ID = PR.OBJECT_ID "
          + "AND END_DATE.ATTR_ID = 16 "
          + "AND END_DATE.OBJECT_ID = PR.OBJECT_ID "
          + "AND PR_STATUS.ATTR_ID = 17 "
          + "AND PR_STATUS.OBJECT_ID = PR.OBJECT_ID "
          + "AND PR_STATUS_VALUE.ATTR_ID = 59 "
          + "AND PR_STATUS_VALUE.LIST_VALUE_ID = PR_STATUS.LIST_VALUE_ID";

  String FIND_PROJECT_BY_NAME =
      "SELECT PR.OBJECT_ID AS " + EnumMapper.PROJECT_ID
          + ", PR_NAME.VALUE AS " + EnumMapper.NAME
          + ", START_DATE.DATE_VALUE AS " + EnumMapper.START_DATE
          + ", END_DATE.DATE_VALUE AS " + EnumMapper.END_DATE
          + ", PR_STATUS_VALUE.VALUE AS " + EnumMapper.STATUS
          + " FROM OBJTYPE PR_TYPE, OBJECTS PR, "
          + "ATTRIBUTES PR_NAME, ATTRIBUTES START_DATE, ATTRIBUTES END_DATE, "
          + "ATTRIBUTES PR_STATUS, "
          + "LISTVALUE PR_STATUS_VALUE "
          + "WHERE PR_NAME.VALUE = ? "
          + "AND PR_TYPE.CODE = 'PROJECT' "
          + "AND PR_NAME.ATTR_ID = 14 "
          + "AND PR_NAME.OBJECT_ID = PR.OBJECT_ID "
          + "AND START_DATE.ATTR_ID = 15 "
          + "AND START_DATE.OBJECT_ID = PR.OBJECT_ID "
          + "AND END_DATE.ATTR_ID = 16 "
          + "AND END_DATE.OBJECT_ID = PR.OBJECT_ID "
          + "AND PR_STATUS.ATTR_ID = 17 "
          + "AND PR_STATUS.OBJECT_ID = PR.OBJECT_ID "
          + "AND PR_STATUS_VALUE.ATTR_ID = 59 "
          + "AND PR_STATUS_VALUE.LIST_VALUE_ID = PR_STATUS.LIST_VALUE_ID";

  String GET_SPRINTS = "SELECT "
      + "  PROJECT_ID.OBJECT_ID, "
      + "  SPRINT_ID.OBJECT_ID    AS " + EnumMapper.SPRINT_ID + ", "
      + "  SPRINT_NAME.VALUE      AS " + EnumMapper.NAME + ", "
      + "  to_char(START_DATE.DATE_VALUE, 'yyyy-MM-dd')        AS " + EnumMapper.START_DATE + ", "
      + "  to_char(END_DATE.DATE_VALUE, 'yyyy-MM-dd')         AS " + EnumMapper.END_DATE + ", "
      + "  to_char(PLANNED_END_DATE.DATE_VALUE, 'yyyy-MM-dd')  AS " + EnumMapper.PLANNED_END_DATE
      + ", "
      + "  STATUS_VALUE.VALUE     AS " + EnumMapper.STATUS + " "
      + "FROM OBJECTS PROJECT_ID, OBJECTS SPRINT_ID, "
      + "  ATTRIBUTES SPRINT_NAME, ATTRIBUTES START_DATE, ATTRIBUTES END_DATE, "
      + "  ATTRIBUTES PLANNED_END_DATE, "
      + "  OBJREFERENCE PROJECT_SPRINT_REF, ATTRIBUTES STATUS, "
      + "  LISTVALUE STATUS_VALUE "
      + "WHERE PROJECT_ID.OBJECT_ID = ? AND "
      + "      PROJECT_SPRINT_REF.ATTR_ID = 60 AND "
      + "      PROJECT_SPRINT_REF.REFERENCE = PROJECT_ID.OBJECT_ID AND "
      + "      SPRINT_ID.OBJECT_ID = PROJECT_SPRINT_REF.OBJECT_ID AND "
      + " "
      + "      SPRINT_NAME.ATTR_ID = 55 AND "
      + "      SPRINT_NAME.OBJECT_ID = SPRINT_ID.OBJECT_ID AND "
      + " "
      + "      START_DATE.ATTR_ID = 56 AND "
      + "      START_DATE.OBJECT_ID = SPRINT_ID.OBJECT_ID AND "
      + " "
      + "      END_DATE.ATTR_ID = 57 AND "
      + "      END_DATE.OBJECT_ID = SPRINT_ID.OBJECT_ID AND "
      + " "
      + "      PLANNED_END_DATE.ATTR_ID = 58 AND "
      + "      PLANNED_END_DATE.OBJECT_ID = SPRINT_ID.OBJECT_ID "
      + " "
      + "      AND STATUS.ATTR_ID = 59 "
      + "      AND STATUS.OBJECT_ID = PROJECT_SPRINT_REF.OBJECT_ID "
      + " "
      + "      AND STATUS_VALUE.ATTR_ID = 59 "
      + "      AND STATUS_VALUE.LIST_VALUE_ID = STATUS.LIST_VALUE_ID";

  String FIND_ALL_OPENED_PROJECTS = "SELECT PROJECT_NAME.VALUE "
      + "FROM OBJECTS PROJECT_ID, "
      + "ATTRIBUTES PROJECT_NAME, ATTRIBUTES PROJECT_STATUS, "
      + "LISTVALUE PROJECT_STATUS_VALUE "
      + "WHERE PROJECT_NAME.ATTR_ID = 14 AND "
      + "PROJECT_ID.OBJECT_ID = PROJECT_NAME.OBJECT_ID AND "
      + "PROJECT_STATUS.ATTR_ID = 17 AND "
      + "PROJECT_STATUS.OBJECT_ID = PROJECT_ID.OBJECT_ID AND "
      + "PROJECT_STATUS_VALUE.LIST_VALUE_ID = PROJECT_STATUS.LIST_VALUE_ID AND "
      + "PROJECT_STATUS_VALUE.ATTR_ID = PROJECT_STATUS.ATTR_ID AND "
      + "PROJECT_STATUS_VALUE.VALUE = 'OPENED'";

  String CREATE_SPRINT =
      "INSERT ALL "
          + " INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION, OBJECT_VERSION) VALUES (59,NULL,7,'Sprint'||?,NULL, 1) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (55,59,?,NULL,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (56,59,NULL,?,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (57,59,NULL,?,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (58,59,NULL,?,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (59,59,NULL,NULL,?) "
          + " INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (60,59,?) "
          + " SELECT * FROM dual";

  String GET_ID_USERS = "SELECT USER_PROJ_REF.REFERENCE "
      + "FROM OBJREFERENCE USER_PROJ_REF "
      + "WHERE USER_PROJ_REF.ATTR_ID = 19 AND "
      + "USER_PROJ_REF.OBJECT_ID = ?";

  String GET_PM_ID = "SELECT USER_PROJ_REF.REFERENCE "
      + "FROM OBJREFERENCE USER_PROJ_REF "
      + "WHERE USER_PROJ_REF.ATTR_ID = 18 AND "
      + "USER_PROJ_REF.OBJECT_ID = ?";

  String GET_PROJECT_ID_BY_DATE =
      "SELECT PR.OBJECT_ID AS " + EnumMapper.PROJECT_ID + " "
          + "FROM OBJTYPE PR_TYPE, OBJECTS PR, "
          + "ATTRIBUTES PR_NAME, ATTRIBUTES START_DATE "
          + "WHERE "
          + "PR_TYPE.CODE = 'PROJECT' "
          + "AND PR_NAME.ATTR_ID = 14 "
          + "AND PR_NAME.OBJECT_ID = PR.OBJECT_ID "
          + "AND START_DATE.VALUE = ? "
          + "AND START_DATE.ATTR_ID = 15 "
          + "AND START_DATE.OBJECT_ID = PR.OBJECT_ID";

  String GET_TASKS_BY_PROJECT_ID =
      "SELECT OBJECT_ID FROM OBJREFERENCE "
          + "WHERE ATTR_ID = 32 "
          + "AND REFERENCE = ?";

  String UPDATE_END_DATE =
      "UPDATE ATTRIBUTES SET DATE_VALUE = ? WHERE ATTR_ID = 16 AND OBJECT_ID = ?";

  String UPDATE_STATUS =
      "UPDATE ATTRIBUTES SET LIST_VALUE_ID = ? "
          + "WHERE ATTR_ID = 17 AND OBJECT_ID = ? ";

  String GET_STATUS_BY_PROJECT_ID =
      "SELECT STATUS.VALUE "
          + "FROM ATTRIBUTES STATUS_VALUE, LISTVALUE STATUS, OBJECTS PROJECT "
          + "WHERE "
          + "  PROJECT.OBJECT_ID = ? "
          + "  AND PROJECT.OBJECT_TYPE_ID = 2 "
          + "  AND STATUS_VALUE.ATTR_ID = 17 "
          + "  AND STATUS_VALUE.OBJECT_ID = PROJECT.OBJECT_ID "
          + "  AND STATUS.LIST_VALUE_ID = STATUS_VALUE.LIST_VALUE_ID "
          + "  AND STATUS.ATTR_ID = 59";

  String UPDATE_PROJECT_PM_ID =
      "UPDATE OBJREFERENCE "
          + "SET REFERENCE = ? "
          + "WHERE ATTR_ID = 18 AND OBJECT_ID = ?";

  String UPDATE_SPRINT_END_DATE =
      "UPDATE ATTRIBUTES SET DATE_VALUE = ? WHERE ATTR_ID = 57 AND OBJECT_ID = ?";

  String UPDATE_SPRINT_PLANNED_END_DATE =
      "UPDATE ATTRIBUTES SET DATE_VALUE = ? WHERE ATTR_ID = 58 AND OBJECT_ID = ?";

  String UPDATE_SPRINT_STATUS = "UPDATE ATTRIBUTES SET LIST_VALUE_ID = ? "
      + "WHERE ATTR_ID = 59 AND OBJECT_ID = ? ";

  String FIND_PROJECT_ID_BY_USER_LOGIN = "SELECT PROJECT_ID.OBJECT_ID "
      + "FROM OBJECTS PROJECT_ID, OBJECTS USER_ID, "
      + "ATTRIBUTES USER_LOGIN, "
      + "OBJREFERENCE PROJECT_USER_REF "
      + "WHERE USER_LOGIN.ATTR_ID = 10 AND "
      + "USER_LOGIN.VALUE = ? AND "
      + "USER_ID.OBJECT_ID = USER_LOGIN.OBJECT_ID AND "
      + "PROJECT_USER_REF.ATTR_ID = 19 AND "
      + "PROJECT_USER_REF.REFERENCE = USER_ID.OBJECT_ID AND "
      + "PROJECT_ID.OBJECT_ID = PROJECT_USER_REF.OBJECT_ID";

  String FIND_PROJECT_ID_BY_PM_LOGIN = "SELECT PROJECT_ID.OBJECT_ID "
      + "FROM OBJECTS PROJECT_ID, OBJECTS USER_ID, "
      + "ATTRIBUTES USER_LOGIN, "
      + "OBJREFERENCE PROJECT_PM_REF "
      + "WHERE USER_LOGIN.ATTR_ID = 10 AND "
      + "USER_LOGIN.VALUE = ? AND "
      + "USER_ID.OBJECT_ID = USER_LOGIN.OBJECT_ID AND "
      + "PROJECT_PM_REF.ATTR_ID = 18 AND "
      + "PROJECT_PM_REF.REFERENCE = USER_ID.OBJECT_ID AND "
      + "PROJECT_ID.OBJECT_ID = PROJECT_PM_REF.OBJECT_ID";
}
