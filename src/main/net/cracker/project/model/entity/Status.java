package main.net.cracker.project.model.entity;

public enum Status {
  APPROVED, DISAPPROVED;
  private Integer id;

  Status(Integer id) {
    this.id = id;
  }

  Status() {
    this.id = 0;
  }

}
