package main.com.netcracker.project.model;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import main.com.netcracker.project.model.entity.Task;

public interface TaskDAO {

  void createTask(Task task);

  void updateTask(Task task);

  Collection<Task> findTaskByProjectId(BigInteger projectId);

  Collection<Task> findTaskByUserId(BigInteger userId);

  Collection<Task> findTaskByUserIdAndDate(Date date, BigInteger userId);

  Collection<Task> findTaskByPriority(Integer taskPriority);

  Collection<Task> findTaskByStatusAndUserId(BigInteger id, Integer taskStatus);

  void updateStatus(Integer taskStatus, BigInteger taskId);

  enum TaskPriority {
    CRITICAL(0, 5),
    HIGH(1, 4),
    NORMAL(2, 3),
    LOW(3, 1);
    private Integer[] id;

    TaskPriority(Integer... id) {
      this.id = id;
    }

    public Integer getId() {
      return id[0];
    }

    public Integer getPriorityCost() {
      return id[1];
    }
  }

  enum TaskStatus {
    OPENED(0), CLOSED(1), REOPENED(2), READY_FOR_TESTING(3);
    private int id;

    TaskStatus(int id) {
      this.id = id;
    }


    public int getId() {
      return id;
    }
  }

  enum TaskType {
    REQUEST_TASK(0), PROJECT_TASK(1);
    private int id;

    TaskType(int id) {
      this.id = id;
    }

    public int getId() {
      return id;
    }
  }

  String CREATE_TASK =
      "INSERT ALL " +
          "INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION, OBJECT_VERSION) VALUES (300,NULL,3,?,NULL, 1) "
          +
          "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (20,300,?,NULL,NULL) "
          +
          "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (21,300,NULL,NULL,?) "
          +
          "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (22,300,?,NULL,NULL) "
          +
          "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (23,300,?,NULL,NULL) "
          +
          "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (24,300,?,NULL,NULL) "
          +
          "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (25,300,NULL ,NULL,?)"
          +
          "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (26,300,NULL,NULL,?) "
          +
          "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (27,300,?,NULL,NULL) "
          +
          "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (28,300,?,NULL,NULL) "
          +
          "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (29,300,?,NULL,NULL) "
          +
          "INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) VALUES (30, 300, ?)"
          +
          "INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) VALUES (31, 300, ?)"
          +
          "INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) VALUES (32, 300, ?)"
          +
          "SELECT * " +
          "FROM Dual";

  String FIND_TASK_BY_PROJECT_ID =
      "SELECT TASK.OBJECT_ID AS TASK_ID, TASK_NAME.VALUE AS NAME, TASK_TYPE_VALUE.VALUE AS TASK_TYPE, "
          +
          " TASK_START_DATE.VALUE AS START_DATE, TASK_END_DATE.VALUE AS END_DATE, TASK_PLANNED_END_DATE.VALUE AS PLANNED_END_DATE, "
          +
          " TASK_PRIORITY_VALUE.VALUE AS TASK_PRIORITY, TASK_STATUS_VALUE.VALUE AS STATUS, TASK_DESCRIPTION.VALUE AS TASK_DESCRIPTION, "
          +
          " TASK_REOPEN_COUNTER.VALUE AS REOPEN_COUNTER, TASK_COMMENT.VALUE AS TASK_COMMENT  "
          +
          " FROM OBJECTS TASK, OBJECTS PROJ, OBJTYPE TASK_TYPE, " +
          " ATTRIBUTES TASK_NAME, ATTRIBUTES TASK_TYPE, ATTRIBUTES TASK_START_DATE, ATTRIBUTES TASK_END_DATE, "
          +
          " ATTRIBUTES TASK_PLANNED_END_DATE, ATTRIBUTES TASK_PRIORITY, ATTRIBUTES TASK_STATUS, ATTRIBUTES TASK_DESCRIPTION, "
          +
          " ATTRIBUTES TASK_REOPEN_COUNTER, ATTRIBUTES TASK_COMMENT, " +
          " LISTVALUE TASK_TYPE_VALUE, LISTVALUE TASK_PRIORITY_VALUE, LISTVALUE TASK_STATUS_VALUE "
          +
          " WHERE TASK_TYPE.OBJECT_TYPE_ID = 3 AND TASK.OBJECT_TYPE_ID = TASK_TYPE.OBJECT_TYPE_ID "
          +
          " AND PROJ.OBJECT_ID = ? " +
          " AND PROJ.OBJECT_TYPE_ID = 2 " +
          " AND TASK_NAME.ATTR_ID = 20 AND TASK_NAME.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_TYPE.ATTR_ID = 21 AND TASK_TYPE.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_TYPE_VALUE.ATTR_ID = 21 AND TASK_TYPE_VALUE.LIST_VALUE_ID = TASK_TYPE.LIST_VALUE_ID "
          +
          " AND TASK_START_DATE.ATTR_ID = 22 AND TASK_START_DATE.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_END_DATE.ATTR_ID = 23 AND TASK_END_DATE.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_PLANNED_END_DATE.ATTR_ID = 24 AND TASK_PLANNED_END_DATE.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_PRIORITY.ATTR_ID = 25 AND TASK_PRIORITY.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_PRIORITY_VALUE.ATTR_ID = 25 AND TASK_PRIORITY_VALUE.LIST_VALUE_ID = TASK_PRIORITY.LIST_VALUE_ID "
          +
          " AND TASK_STATUS.ATTR_ID = 26 AND TASK_STATUS.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_STATUS_VALUE.ATTR_ID = 26 AND TASK_STATUS_VALUE.LIST_VALUE_ID = TASK_STATUS.LIST_VALUE_ID "
          +
          " AND TASK_DESCRIPTION.ATTR_ID = 27 AND TASK_DESCRIPTION.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_REOPEN_COUNTER.ATTR_ID = 28 AND TASK_REOPEN_COUNTER.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_COMMENT.ATTR_ID = 29 AND TASK_COMMENT.OBJECT_ID = TASK.OBJECT_ID ";

  String FIND_TASK_BY_USER_ID =
      " SELECT TASK.OBJECT_ID AS TASK_ID, TASK_NAME.VALUE AS NAME, TASK_TYPE_VALUE.VALUE AS TASK_TYPE, "
          +
          " TASK_START_DATE.VALUE AS START_DATE, TASK_END_DATE.VALUE AS END_DATE, TASK_PLANNED_END_DATE.VALUE AS PLANNED_END_DATE, "
          +
          " TASK_PRIORITY_VALUE.VALUE AS TASK_PRIORITY, TASK_STATUS_VALUE.VALUE AS STATUS, TASK_DESCRIPTION.VALUE AS TASK_DESCRIPTION, "
          +
          " TASK_REOPEN_COUNTER.VALUE AS REOPEN_COUNTER, TASK_COMMENT.VALUE AS TASK_COMMENT "
          +
          " FROM OBJECTS TASK, OBJECTS EMP, OBJTYPE TASK_TYPE, " +
          " ATTRIBUTES TASK_NAME, ATTRIBUTES TASK_TYPE, ATTRIBUTES TASK_START_DATE, ATTRIBUTES TASK_END_DATE, "
          +
          " ATTRIBUTES TASK_PLANNED_END_DATE, ATTRIBUTES TASK_PRIORITY, ATTRIBUTES TASK_STATUS, ATTRIBUTES TASK_DESCRIPTION, "
          +
          " ATTRIBUTES TASK_REOPEN_COUNTER, ATTRIBUTES TASK_COMMENT, " +
          " LISTVALUE TASK_TYPE_VALUE, LISTVALUE TASK_PRIORITY_VALUE, LISTVALUE TASK_STATUS_VALUE "
          +
          " WHERE TASK_TYPE.OBJECT_TYPE_ID = 3 AND TASK.OBJECT_TYPE_ID = TASK_TYPE.OBJECT_TYPE_ID "
          +
          " AND EMP.OBJECT_ID = ? " +
          " AND EMP.OBJECT_TYPE_ID = 1 " +
          " AND TASK_NAME.ATTR_ID = 20 AND TASK_NAME.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_TYPE.ATTR_ID = 21 AND TASK_TYPE.OBJECT_ID = TASK.OBJECT_ID  "
          +
          " AND TASK_TYPE_VALUE.ATTR_ID = 21 AND TASK_TYPE_VALUE.LIST_VALUE_ID = TASK_TYPE.LIST_VALUE_ID "
          +
          " AND TASK_START_DATE.ATTR_ID = 22 AND TASK_START_DATE.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_END_DATE.ATTR_ID = 23 AND TASK_END_DATE.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_PLANNED_END_DATE.ATTR_ID = 24 AND TASK_PLANNED_END_DATE.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_PRIORITY.ATTR_ID = 25 AND TASK_PRIORITY.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_PRIORITY_VALUE.ATTR_ID = 25 AND TASK_PRIORITY_VALUE.LIST_VALUE_ID = TASK_PRIORITY.LIST_VALUE_ID "
          +
          " AND TASK_STATUS.ATTR_ID = 26 AND TASK_STATUS.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_STATUS_VALUE.ATTR_ID = 26 AND TASK_STATUS_VALUE.LIST_VALUE_ID = TASK_STATUS.LIST_VALUE_ID "
          +
          " AND TASK_DESCRIPTION.ATTR_ID = 27 AND TASK_DESCRIPTION.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_REOPEN_COUNTER.ATTR_ID = 28 AND TASK_REOPEN_COUNTER.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_COMMENT.ATTR_ID = 29 AND TASK_COMMENT.OBJECT_ID = TASK.OBJECT_ID ";

  String FIND_TASK_BY_USER_ID_AND_DATE =
      "SELECT TASK.OBJECT_ID AS TASK_ID, TASK_NAME.VALUE AS NAME, TASK_TYPE_VALUE.VALUE AS TASK_TYPE, "
          +
          " TASK_START_DATE.VALUE AS START_DATE, TASK_END_DATE.VALUE AS END_DATE, TASK_PLANNED_END_DATE.VALUE AS PLANNED_END_DATE, "
          +
          " TASK_PRIORITY_VALUE.VALUE AS TASK_PRIORITY, TASK_STATUS_VALUE.VALUE AS STATUS, TASK_DESCRIPTION.VALUE AS TASK_DESCRIPTION, "
          +
          " TASK_REOPEN_COUNTER.VALUE AS REOPEN_COUNTER, TASK_COMMENT.VALUE AS TASK_COMMENT "
          +
          " FROM OBJECTS TASK, OBJECTS EMP, OBJTYPE TASK_TYPE, " +
          " ATTRIBUTES TASK_NAME, ATTRIBUTES TASK_TYPE, ATTRIBUTES TASK_START_DATE, ATTRIBUTES TASK_END_DATE, "
          +
          " ATTRIBUTES TASK_PLANNED_END_DATE, ATTRIBUTES TASK_PRIORITY, ATTRIBUTES TASK_STATUS, ATTRIBUTES TASK_DESCRIPTION, "
          +
          " ATTRIBUTES TASK_REOPEN_COUNTER, ATTRIBUTES TASK_COMMENT, " +
          " LISTVALUE TASK_TYPE_VALUE, LISTVALUE TASK_PRIORITY_VALUE, LISTVALUE TASK_STATUS_VALUE "
          +
          " WHERE TASK_TYPE.OBJECT_TYPE_ID = 3 AND TASK.OBJECT_TYPE_ID = TASK_TYPE.OBJECT_TYPE_ID "
          +
          " AND TASK_START_DATE.VALUE = ? " +
          " AND EMP.OBJECT_ID = ? " +
          " AND EMP.OBJECT_TYPE_ID = 1 " +
          " AND TASK_NAME.ATTR_ID = 20 AND TASK_NAME.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_TYPE.ATTR_ID = 21 AND TASK_TYPE.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_TYPE_VALUE.ATTR_ID = 21 AND TASK_TYPE_VALUE.LIST_VALUE_ID = TASK_TYPE.LIST_VALUE_ID "
          +
          " AND TASK_START_DATE.ATTR_ID = 22 AND TASK_START_DATE.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_END_DATE.ATTR_ID = 23 AND TASK_END_DATE.OBJECT_ID = TASK.OBJECT_ID"
          +
          " AND TASK_PLANNED_END_DATE.ATTR_ID = 24 AND TASK_PLANNED_END_DATE.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_PRIORITY.ATTR_ID = 25 AND TASK_PRIORITY.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_PRIORITY_VALUE.ATTR_ID = 25 AND TASK_PRIORITY_VALUE.LIST_VALUE_ID = TASK_PRIORITY.LIST_VALUE_ID "
          +
          " AND TASK_STATUS.ATTR_ID = 26 AND TASK_STATUS.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_STATUS_VALUE.ATTR_ID = 26 AND TASK_STATUS_VALUE.LIST_VALUE_ID = TASK_STATUS.LIST_VALUE_ID "
          +
          " AND TASK_DESCRIPTION.ATTR_ID = 27 AND TASK_DESCRIPTION.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_REOPEN_COUNTER.ATTR_ID = 28 AND TASK_REOPEN_COUNTER.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_COMMENT.ATTR_ID = 29 AND TASK_COMMENT.OBJECT_ID = TASK.OBJECT_ID ";

  String FIND_TASK_BY_PRIORITY =
      "SELECT TASK.OBJECT_ID AS TASK_ID, TASK_NAME.VALUE AS NAME, TASK_TYPE_VALUE.VALUE AS TASK_TYPE,"
          +
          " TASK_START_DATE.VALUE AS START_DATE, TASK_END_DATE.VALUE AS END_DATE, TASK_PLANNED_END_DATE.VALUE AS PLANNED_END_DATE,"
          +
          " TASK_PRIORITY_VALUE.VALUE AS TASK_PRIORITY, TASK_STATUS_VALUE.VALUE AS STATUS, TASK_DESCRIPTION.VALUE AS TASK_DESCRIPTION,"
          +
          " TASK_REOPEN_COUNTER.VALUE AS REOPEN_COUNTER, TASK_COMMENT.VALUE AS TASK_COMMENT "
          +
          " FROM OBJECTS TASK, OBJTYPE TASK_TYPE," +
          " ATTRIBUTES TASK_NAME, ATTRIBUTES TASK_TYPE, ATTRIBUTES TASK_START_DATE, ATTRIBUTES TASK_END_DATE, "
          +
          " ATTRIBUTES TASK_PLANNED_END_DATE, ATTRIBUTES TASK_PRIORITY, ATTRIBUTES TASK_STATUS, ATTRIBUTES TASK_DESCRIPTION, "
          +
          " ATTRIBUTES TASK_REOPEN_COUNTER, ATTRIBUTES TASK_COMMENT, " +
          " LISTVALUE TASK_TYPE_VALUE, LISTVALUE TASK_PRIORITY_VALUE, LISTVALUE TASK_STATUS_VALUE "
          +
          " WHERE TASK_TYPE.OBJECT_TYPE_ID = 3 AND TASK.OBJECT_TYPE_ID = TASK_TYPE.OBJECT_TYPE_ID "
          +
          " AND TASK_PRIORITY_VALUE.LIST_VALUE_ID = ? " +
          " AND TASK_NAME.ATTR_ID = 20 AND TASK_NAME.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_TYPE.ATTR_ID = 21 AND TASK_TYPE.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_TYPE_VALUE.ATTR_ID = 21 AND TASK_TYPE_VALUE.LIST_VALUE_ID = TASK_TYPE.LIST_VALUE_ID "
          +
          " AND TASK_START_DATE.ATTR_ID = 22 AND TASK_START_DATE.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_END_DATE.ATTR_ID = 23 AND TASK_END_DATE.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_PLANNED_END_DATE.ATTR_ID = 24 AND TASK_PLANNED_END_DATE.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_PRIORITY.ATTR_ID = 25 AND TASK_PRIORITY.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_PRIORITY_VALUE.ATTR_ID = 25 AND TASK_PRIORITY_VALUE.LIST_VALUE_ID = TASK_PRIORITY.LIST_VALUE_ID "
          +
          " AND TASK_STATUS.ATTR_ID = 26 AND TASK_STATUS.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_STATUS_VALUE.ATTR_ID = 26 AND TASK_STATUS_VALUE.LIST_VALUE_ID = TASK_STATUS.LIST_VALUE_ID "
          +
          " AND TASK_DESCRIPTION.ATTR_ID = 27 AND TASK_DESCRIPTION.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_REOPEN_COUNTER.ATTR_ID = 28 AND TASK_REOPEN_COUNTER.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_COMMENT.ATTR_ID = 29 AND TASK_COMMENT.OBJECT_ID = TASK.OBJECT_ID ";

  String FIND_TASK_BY_STATUS_AND_USER_ID =
      " SELECT TASK.OBJECT_ID AS TASK_ID, TASK_NAME.VALUE AS NAME, TASK_TYPE_VALUE.VALUE AS TASK_TYPE, "
          +
          " TASK_START_DATE.VALUE AS START_DATE, TASK_END_DATE.VALUE AS END_DATE, TASK_PLANNED_END_DATE.VALUE AS PLANNED_END_DATE, "
          +
          " TASK_PRIORITY_VALUE.VALUE AS TASK_PRIORITY, TASK_STATUS_VALUE.VALUE AS STATUS, TASK_DESCRIPTION.VALUE AS TASK_DESCRIPTION, "
          +
          " TASK_REOPEN_COUNTER.VALUE AS REOPEN_COUNTER, TASK_COMMENT.VALUE AS TASK_COMMENT "
          +
          " FROM OBJECTS TASK, OBJECTS EMP, OBJTYPE TASK_TYPE, " +
          " ATTRIBUTES TASK_NAME, ATTRIBUTES TASK_TYPE, ATTRIBUTES TASK_START_DATE, ATTRIBUTES TASK_END_DATE, "
          +
          " ATTRIBUTES TASK_PLANNED_END_DATE, ATTRIBUTES TASK_PRIORITY, ATTRIBUTES TASK_STATUS, ATTRIBUTES TASK_DESCRIPTION, "
          +
          " ATTRIBUTES TASK_REOPEN_COUNTER, ATTRIBUTES TASK_COMMENT, " +
          " LISTVALUE TASK_TYPE_VALUE, LISTVALUE TASK_PRIORITY_VALUE, LISTVALUE TASK_STATUS_VALUE "
          +
          " WHERE TASK_TYPE.OBJECT_TYPE_ID = 3 AND TASK.OBJECT_TYPE_ID = TASK_TYPE.OBJECT_TYPE_ID "
          +
          " AND EMP.OBJECT_ID = ? AND EMP.OBJECT_TYPE_ID = 1 " +
          " AND TASK_STATUS_VALUE.LIST_VALUE_ID = ? " +
          " AND TASK_NAME.ATTR_ID = 20 AND TASK_NAME.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_TYPE.ATTR_ID = 21 AND TASK_TYPE.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_TYPE_VALUE.ATTR_ID = 21 AND TASK_TYPE_VALUE.LIST_VALUE_ID = TASK_TYPE.LIST_VALUE_ID "
          +
          " AND TASK_START_DATE.ATTR_ID = 22 AND TASK_START_DATE.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_END_DATE.ATTR_ID = 23 AND TASK_END_DATE.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_PLANNED_END_DATE.ATTR_ID = 24 AND TASK_PLANNED_END_DATE.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_PRIORITY.ATTR_ID = 25 AND TASK_PRIORITY.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_PRIORITY_VALUE.ATTR_ID = 25 AND TASK_PRIORITY_VALUE.LIST_VALUE_ID = TASK_PRIORITY.LIST_VALUE_ID "
          +
          " AND TASK_STATUS.ATTR_ID = 26 AND TASK_STATUS.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_STATUS_VALUE.ATTR_ID = 26 AND TASK_STATUS_VALUE.LIST_VALUE_ID = TASK_STATUS.LIST_VALUE_ID "
          +
          " AND TASK_DESCRIPTION.ATTR_ID = 27 AND TASK_DESCRIPTION.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_REOPEN_COUNTER.ATTR_ID = 28 AND TASK_REOPEN_COUNTER.OBJECT_ID = TASK.OBJECT_ID "
          +
          " AND TASK_COMMENT.ATTR_ID = 29 AND TASK_COMMENT.OBJECT_ID = TASK.OBJECT_ID ";

  String UPDATE_TASK_BY_NAME =
      " UPDATE ATTRIBUTES " +
          " SET ATTRIBUTES.VALUE = ?" +
          " WHERE ATTRIBUTES.OBJECT_ID = ? " +
          " AND ATTRIBUTES.ATTR_ID = 20";

  String UPDATE_TASK_BY_TYPE =
      " UPDATE ATTRIBUTES " +
          " SET ATTRIBUTES.LIST_VALUE_ID = ?" +
          " WHERE ATTRIBUTES.OBJECT_ID = ?" +
          " AND ATTRIBUTES.ATTR_ID = 21";

  String UPDATE_TASK_BY_START_DATE =
      " UPDATE ATTRIBUTES " +
          "SET ATTRIBUTES.VALUE = ?" +
          "WHERE ATTRIBUTES.OBJECT_ID = ?" +
          "AND ATTRIBUTES.ATTR_ID = 22";

  String UPDATE_TASK_BY_END_DATE =
      " UPDATE ATTRIBUTES " +
          " SET ATTRIBUTES.VALUE = ?" +
          " WHERE ATTRIBUTES.OBJECT_ID = ? " +
          " AND ATTRIBUTES.ATTR_ID = 23";

  String UPDATE_TASK_BY_PLANNED_END_DATE =
      " UPDATE ATTRIBUTES " +
          " SET ATTRIBUTES.VALUE = ?" +
          " WHERE ATTRIBUTES.OBJECT_ID = ? " +
          " AND ATTRIBUTES.ATTR_ID = 24";

  String UPDATE_TASK_BY_TASK_PRIORITY =
      " UPDATE ATTRIBUTES " +
          " SET ATTRIBUTES.LIST_VALUE_ID = ? " +
          " WHERE ATTRIBUTES.OBJECT_ID = ? " +
          " AND ATTRIBUTES.ATTR_ID = 25 ";

  String UPDATE_TASK_BY_TASK_STATUS =
      " UPDATE ATTRIBUTES " +
          " SET ATTRIBUTES.LIST_VALUE_ID = ? " +
          " WHERE ATTRIBUTES.OBJECT_ID = ? " +
          " AND ATTRIBUTES.ATTR_ID = 26 ";

  String UPDATE_TASK_BY_DESCRIPTION =
      " UPDATE ATTRIBUTES " +
          " SET ATTRIBUTES.VALUE = ? " +
          " WHERE ATTRIBUTES.OBJECT_ID = ? " +
          " AND ATTRIBUTES.ATTR_ID = 27 ";

  String UPDATE_TASK_BY_REOPEN_COUNTER =
      " UPDATE ATTRIBUTES " +
          " SET ATTRIBUTES.VALUE = ? " +
          " WHERE ATTRIBUTES.OBJECT_ID = ? " +
          " AND ATTRIBUTES.ATTR_ID = 28 ";

  String UPDATE_TASK_BY_COMMENT =
      " UPDATE ATTRIBUTES " +
          " SET ATTRIBUTES.VALUE = ? " +
          " WHERE ATTRIBUTES.OBJECT_ID = ? " +
          " AND ATTRIBUTES.ATTR_ID = 29 ";

  String UPDATE_TASK_BY_TASK_AUTHOR =
      " UPDATE ATTRIBUTES " +
          " SET ATTRIBUTES.VALUE = ? " +
          " WHERE ATTRIBUTES.OBJECT_ID = ? " +
          " AND ATTRIBUTES.ATTR_ID = 30 ";

  String UPDATE_TASK_BY_TASK_USER_ID =
      " UPDATE ATTRIBUTES " +
          " SET ATTRIBUTES.VALUE = ? " +
          " WHERE ATTRIBUTES.OBJECT_ID = ? " +
          " AND ATTRIBUTES.ATTR_ID = 31 ";

  String UPDATE_TASK_BY_TASK_PROJECT =
      " UPDATE ATTRIBUTES " +
          " SET ATTRIBUTES.VALUE = ? " +
          " WHERE ATTRIBUTES.OBJECT_ID = ? " +
          " AND ATTRIBUTES.ATTR_ID = 32 ";

}
