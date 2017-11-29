package main.com.netcracker.project.model.entity;

public enum Status {
  APPROVED(0), DISAPPROVED(1);
  private int id;

  Status(int id) {
    this.id = id;
  }

  public int getId(){
    return id;
  }
}
