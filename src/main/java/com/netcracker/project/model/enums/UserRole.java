package com.netcracker.project.model.enums;


public enum UserRole {
  ROLE_ADMIN(0), ROLE_PM(1), ROLE_LM(2), ROLE_SE(3);

  private int id;

  UserRole(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }
}
