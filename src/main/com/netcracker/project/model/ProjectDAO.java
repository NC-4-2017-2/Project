package main.com.netcracker.project.model;

import java.math.BigInteger;
import java.util.Date;

import java.util.List;
import main.com.netcracker.project.model.entity.Project;

public interface ProjectDAO {

  void createProject(Project project);

  Project findProjectByProjectId(BigInteger id);

  Project findProjectByName(String name);

  List<Project> findProjectByDate(Date startDate);

  void deleteUserByUserId(BigInteger userId, BigInteger projectID);

  void updateProject(Project project);

  void addUser(BigInteger userId, BigInteger projectId);

  enum OCStatus {
    OPEN(0), CLOSED(1);

    private int id;

    OCStatus(int id) {
      this.id = id;
    }

    public int getId(){
      return id;
    }
  }
}
