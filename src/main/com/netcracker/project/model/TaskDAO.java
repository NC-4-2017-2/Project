package main.com.netcracker.project.model;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import main.com.netcracker.project.model.entity.Status;
import main.com.netcracker.project.model.entity.Task;

public interface TaskDAO {

  void createTask(Task task);

  void updateTask(Task task);

  Collection<Task> findTaskByProjectId(BigInteger id);

  Collection<Task> findTaskByUserId(BigInteger id);

  Collection<Task> findTaskByDate(Date date, BigInteger id);

  Collection<Task> findTaskByPriority(TaskPriority taskPriority,
      BigInteger employeeID);

  Task findTaskByStatusAndUserId(String status, BigInteger id);

  TaskStatus updateStatus(TaskStatus status);

  enum TaskPriority {
    CRITICAL, HIGH, NORMAL, LOW;
    private Integer id;

    TaskPriority(Integer id) {
      this.id = id;
    }

    TaskPriority() {
      this.id = 0;
    }
  }

  enum TaskStatus {
    COMPLETED, CLOSED, REOPEN, READY_FOR_TESTING;
    private Integer id;

    TaskStatus(Integer id) {
      this.id = id;
    }

    TaskStatus() {
      this.id = 0;
    }
  }

  enum TaskType {
    REQUEST_TASK, PROJECT_TASK;
    private Integer id;

    TaskType(Integer id) {
      this.id = id;
    }

    TaskType() {
      this.id = 0;
    }
  }



}
