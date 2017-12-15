package main.com.netcracker.project.controllers.task;

import java.util.List;


public class TaskForm {

  private List<TaskData> tasks;

  public TaskForm() {
  }

  public List<TaskData> getTasks() {
    return tasks;
  }

  public void setTasks(List<TaskData> tasks) {
    this.tasks = tasks;
  }
}
