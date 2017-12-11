package main.com.netcracker.project.controllers.project;

import java.util.List;

public class SprintsForm {

  private List<SprintFormData> sprints;

  public SprintsForm() {
  }

  public List<SprintFormData> getSprints() {
    return sprints;
  }

  public void setSprints(List<SprintFormData> sprints) {
    this.sprints = sprints;
  }
}