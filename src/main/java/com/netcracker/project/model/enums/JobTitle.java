package com.netcracker.project.model.enums;


public enum JobTitle {
  PROJECT_MANAGER(0), LINE_MANAGER(1), SOFTWARE_ENGINEER(2);

  private int id;

  JobTitle(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }
}
