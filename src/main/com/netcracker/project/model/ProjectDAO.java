package main.com.netcracker.project.model;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

import java.util.List;
import main.com.netcracker.project.model.entity.Project;
import main.com.netcracker.project.model.entity.Sprint;

public interface ProjectDAO {

  void createProject(Project project);

  Project findProjectByProjectId(BigInteger id);

  Project findProjectByName(String name);

  List<Project> findProjectByDate(Date startDate);

  List<BigInteger> getIdUsers(BigInteger projectId);

  void deleteUserByUserId(BigInteger userId, BigInteger projectID);

  void updateEndDate(BigInteger projectId, Date endDate);

  void updateStatus(BigInteger projectId, OCStatus ocStatus);

  void updatePM(BigInteger projectId, BigInteger userId);

  void addUser(BigInteger userId, BigInteger projectId);

  Collection<Sprint> getAllSprints(BigInteger projectID);

  void createSprint(Sprint sprint, BigInteger projectId);

  enum OCStatus {
    OPENED(0), CLOSED(1);

    private int id;

    OCStatus(int id) {
      this.id = id;
    }

    public int getId(){
      return id;
    }
  }
}
