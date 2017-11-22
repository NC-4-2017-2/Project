package main.net.cracker.project.model;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import main.net.cracker.project.model.entity.Task;

public interface TaskDAO {

  void createTask(Task task);

  void updateTask(BigInteger id);

  Collection<Task> findTaskByProjectld(BigInteger id);

  Collection<Task> findTaskByUserld(BigInteger id);

  Collection<Task> findTaskByDate(Date date, BigInteger id);

  Collection<Task> findTaskByPriority(TaskPriority taskPriority,
      BigInteger employeeID);

  Task findTaskByStatusAndUserId(String status, BigInteger id);

  String updateStatus(TaskStatus status);

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
