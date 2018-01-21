package com.netcracker.project.model.enums;


public enum TaskStatus {
  OPENED(0), CLOSED(1), REOPENED(2), READY_FOR_TESTING(3);
  private int id;


  TaskStatus(int id) {
    this.id = id;
  }


  public int getId() {
    return id;
  }
}
