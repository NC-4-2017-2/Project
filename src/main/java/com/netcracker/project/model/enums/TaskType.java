package com.netcracker.project.model.enums;


public enum TaskType {

  REQUEST_TASK(0), PROJECT_TASK(1);
  private int id;

  TaskType(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

}
