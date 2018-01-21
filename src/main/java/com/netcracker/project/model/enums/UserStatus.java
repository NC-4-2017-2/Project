package com.netcracker.project.model.enums;


public enum UserStatus {
  WORKING(0), FIRED(1);

  private int id;

  UserStatus(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }
}
