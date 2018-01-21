package com.netcracker.project.model.enums;


public enum ProjectStatus {
  WORKING(0), TRANSIT(1);

  private int id;

  ProjectStatus(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }
}
