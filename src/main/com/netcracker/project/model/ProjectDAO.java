package main.com.netcracker.project.model;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

import java.util.List;
import main.com.netcracker.project.model.entity.Project;
import main.com.netcracker.project.model.entity.Sprint;

public interface ProjectDAO {

  void createProject(Project project);

  Project findProjectByProjectId(BigInteger id);

  Project findProjectByName(String name);

  List<Project> findProjectByDate(Date startDate);

  List<BigInteger> getIdUsers(BigInteger projectId);

  void deleteUserByUserId(BigInteger userId, BigInteger projectID);

  void updateEndDate(BigInteger projectId, Date endDate);

  void updateStatus(BigInteger projectId, OCStatus ocStatus);

  void updatePM(BigInteger projectId, BigInteger userId);

  void addUser(BigInteger userId, BigInteger projectId);

  Collection<Sprint> getAllSprints(BigInteger projectID);

  void createSprint(Sprint sprint, BigInteger projectId);

  enum OCStatus {
    OPENED(0), CLOSED(1);

    private int id;

    OCStatus(int id) {
      this.id = id;
    }

    public int getId() {
      return id;
    }
  }

  String CREATE_PROJECT =
      "INSERT ALL "
          + " INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) VALUES (202,NULL,2,'Project'||?,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (14,202,?,NULL,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (15,202,?,NULL,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (16,202,?,NULL,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (17,202,NULL,NULL,?) "
          + " INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (18,202,?)"
          + " SELECT * FROM dual";

  String UPDATE_USERS_IN_PROJECT =
      "INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (19,?,?)";

  String DELETE_USERS_IN_PROJECT =
      "DELETE FROM OBJREFERENCE WHERE OBJECT_ID = ? AND REFERENCE = ?";

  String FIND_PROJECT_BY_PROJECT_ID =
      "SELECT PR.OBJECT_ID AS PROJECT_ID, PR_NAME.VALUE AS NAME, START_DATE.VALUE "
          + "AS START_DATE, END_DATE.VALUE AS END_DATE, "
          + "PR_STATUS_VALUE.VALUE AS PROJECT_STATUS "
          + "FROM OBJTYPE PR_TYPE, OBJECTS PR, "
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
      "SELECT PR.OBJECT_ID AS PROJECT_ID, PR_NAME.VALUE AS NAME, START_DATE.VALUE "
          + "AS START_DATE, END_DATE.VALUE AS END_DATE, "
          + "PR_STATUS_VALUE.VALUE AS PROJECT_STATUS "
          + "FROM OBJTYPE PR_TYPE, OBJECTS PR, "
          + "ATTRIBUTES PR_NAME, ATTRIBUTES START_DATE, ATTRIBUTES END_DATE, "
          + "ATTRIBUTES PR_STATUS, "
          + "LISTVALUE PR_STATUS_VALUE "
          + "WHERE PR_NAME.VALUE = ?"
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
        + "  REF.OBJECT_ID AS SPRINT_ID, "
        + "  NAME.VALUE AS NAME, "
        + "  START_DATE.VALUE AS START_DATE, "
        + "  END_DATE.VALUE AS END_DATE, "
        + "  PLANNED_DATE.VALUE AS PLANNED_END_DATE, "
        + "  STATUS_VALUE.VALUE AS STATUS "
        + "FROM OBJREFERENCE REF, OBJECTS PROJECT, "
        + "  ATTRIBUTES NAME, ATTRIBUTES START_DATE, ATTRIBUTES END_DATE, "
        + "  ATTRIBUTES PLANNED_DATE, ATTRIBUTES STATUS, "
        + "  LISTVALUE STATUS_VALUE "
        + "WHERE PROJECT.OBJECT_ID = ? "
        + "      AND REF.ATTR_ID = 60 "
        + "      AND REF.REFERENCE = PROJECT.OBJECT_ID "
        + "      AND NAME.ATTR_ID = 55 "
        + "      AND NAME.OBJECT_ID = REF.OBJECT_ID "
        + "      AND START_DATE.ATTR_ID = 56 "
        + "      AND START_DATE.OBJECT_ID = REF.OBJECT_ID "
        + "      AND END_DATE.ATTR_ID = 57 "
        + "      AND END_DATE.OBJECT_ID = REF.OBJECT_ID "
        + "      AND PLANNED_DATE.ATTR_ID = 58 "
        + "      AND PLANNED_DATE.OBJECT_ID = REF.OBJECT_ID "
        + "      AND STATUS.ATTR_ID = 59 "
        + "      AND STATUS.OBJECT_ID = REF.OBJECT_ID "
        + "      AND STATUS_VALUE.ATTR_ID = 59 "
        + "      AND STATUS_VALUE.LIST_VALUE_ID = STATUS.LIST_VALUE_ID";

  String CREATE_SPRINT =
      "INSERT ALL "
          + " INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) VALUES (58,NULL,7,'Sprint'||58,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (55,58,?,NULL,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (56,58,?,NULL,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (57,58,?,NULL,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (58,58,?,NULL,NULL) "
          + " INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (59,58,NULL,NULL,?) "
          + " INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (60,58,?) "
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
      "SELECT PR.OBJECT_ID AS PROJECT_ID "
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
      "UPDATE ATTRIBUTES SET VALUE = to_char(?, 'dd.mm.yy') WHERE ATTR_ID = 16 AND OBJECT_ID = ?";

  String UPDATE_STATUS =
      "UPDATE ATTRIBUTES SET LIST_VALUE_ID = ? "
          + "WHERE ATTR_ID = 17 AND OBJECT_ID = ? ";
  String GET_STATUS_BY_PROJECT_ID =
      "SELECT "
          + "  STATUS.VALUE "
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

}
