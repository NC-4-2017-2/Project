package main.net.cracker.project.model;

import java.math.BigInteger;
import java.util.Date;
import main.net.cracker.project.model.entity.Project;

public interface ProjectDAO {

  void createProject(Project project);

  Project findProjectByProjectId(BigInteger id);

  Project findProjectByName(String name);

  Project findProjectByDate(Date startDate);

  void deleteUserByUserId(BigInteger userId, BigInteger projectID);

  void updateProject(BigInteger id, Project project);

  void addUser(BigInteger userId, BigInteger projectId);

  enum OCStatus {
    OPEN, CLOSED;

    private Integer id;

    OCStatus(Integer id) {
      this.id = id;
    }

    OCStatus() {
      this.id = 0;
    }
  }
}
