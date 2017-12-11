package main.com.netcracker.project.controllers.project;

import java.util.List;

public class WorkPeriodForm {

  private List<WorkPeriodFormData> workers;

  public WorkPeriodForm() {
  }

  public List<WorkPeriodFormData> getWorkers() {
    return workers;
  }

  public void setWorkers(List<WorkPeriodFormData> workers) {
    this.workers = workers;
  }
}
