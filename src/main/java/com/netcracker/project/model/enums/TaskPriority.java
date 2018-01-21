package com.netcracker.project.model.enums;


public enum TaskPriority {
  CRITICAL(0),
  HIGH(1),
  NORMAL(2),
  LOW(3);
  private int id;

  TaskPriority(int id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

}
