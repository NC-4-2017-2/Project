package com.netcracker.project.model.enums;

public enum OCStatus {
  OPENED(0), CLOSED(1);

  private int id;

  OCStatus() {
  }

  OCStatus(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }
}