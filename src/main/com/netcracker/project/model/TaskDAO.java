package main.com.netcracker.project.model;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import main.com.netcracker.project.model.entity.Status;
import main.com.netcracker.project.model.entity.Task;

public interface TaskDAO {

  void createTask(Task task);

  void updateTask(Task task);

  Collection<Task> findTaskByProjectId(BigInteger projectId);

  Collection<Task> findTaskByUserId(BigInteger userId);

  Collection<Task> findTaskByUserIdAndDate(Date date, BigInteger userId);

  Collection<Task> findTaskByPriority(TaskPriority taskPriority);

  Task findTaskByStatusAndUserId(TaskStatus status, BigInteger id);

  void updateStatus(Status status, BigInteger taskId);

  enum TaskPriority {
    CRITICAL(0), HIGH(1), NORMAL(2), LOW(3);
    private int id;

    TaskPriority(int id) {
      this.id = id;
    }

    public int getId(){
      return id;
    }
  }

  enum TaskStatus {
    COMPLETED(0), CLOSED(1), REOPEN(2), READY_FOR_TESTING(3);
    private int id;

    TaskStatus(int id) {
      this.id = id;
    }


    public int getId(){
      return id;
    }
  }

  enum TaskType {
    REQUEST_TASK(0), PROJECT_TASK(1);
    private int id;

    TaskType(int id) {
      this.id = id;
    }

    public int getId(){
      return id;
    }
  }



}
