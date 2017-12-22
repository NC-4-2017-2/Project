package com.netcracker.project.model.enums;

public enum Status {
  APPROVED(0), DISAPPROVED(1), WAITING_FOR_APPROVAL(3);
  private int id;

  Status(int id) {
    this.id = id;
  }

  public int getId(){
    return id;
  }
}
