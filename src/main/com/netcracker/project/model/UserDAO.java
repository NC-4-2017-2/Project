package main.com.netcracker.project.model;

import java.io.File;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

import main.com.netcracker.project.model.entity.User;

public interface UserDAO {

  User createUser(User user);

  User findUserByUserId(BigInteger id);

  User findUserByLogin(String login);

  void updatePhoneNumber(BigInteger id, String phoneNumber);

  void updateEmail(BigInteger id, String email);

  void updatePassword(BigInteger id, String password);

  void updatePhoto(BigInteger id, File photo);

  void updateProjectStatus(BigInteger id, Integer status);

  Collection<WorkPeriod> findWorkPeriodsByUserId(BigInteger id);

  Collection<WorkPeriod> findWorkPeriodsByProjectId(BigInteger id);

  Collection<WorkPeriod> findWorkPeriodByUserIdAndProjectId(BigInteger userId, BigInteger projectId);

  void updateWorkingPeriodByUserId(BigInteger userId, BigInteger projectId, UserDAO.WorkPeriod workPeriod);

  enum JobTitle {
    PROJECT_MANAGER(0), LINE_MANAGER(1), SOFTWARE_ENGINEER(2);

    private int id;

    JobTitle(int id) {
      this.id = id;
    }

    public int getId(){
      return id;
    }
  }

  enum ProjectStatus {
    WORKING(0), TRANSIT(1);

    private int id;

    ProjectStatus(int id) {
      this.id = id;
    }

    public int getId(){
      return id;
    }
  }

  enum UserStatus {
    WORKING(0), FIRED(1);

    private int id;

    UserStatus(int id) {
      this.id = id;
    }

    public int getId(){
      return id;
    }
  }

  enum UserRole {
    ROLE_ADMIN(0), ROLE_PM(1), ROLE_LM(2), ROLE_SE(3);

    private int id;

    UserRole(int id) {
      this.id = id;
    }

    public int getId(){
      return id;
    }
  }

  class WorkPeriod {

    BigInteger workPeriodId, userId, projectId;
    Date startWorkDate, endWorkDate;

    public WorkPeriod() {

    }

    public WorkPeriod(BigInteger workPeriodId, BigInteger userId, BigInteger projectId,
        Date startWorkDate, Date endWorkDate) {
      this.workPeriodId = workPeriodId;
      this.userId = userId;
      this.projectId = projectId;
      this.startWorkDate = startWorkDate;
      this.endWorkDate = endWorkDate;
    }

    public BigInteger getWorkPeriodId() {
      return workPeriodId;
    }

    public void setWorkPeriodId(BigInteger workPeriodId) {
      this.workPeriodId = workPeriodId;
    }

    public BigInteger getUserId() {
      return userId;
    }

    public void setUserId(BigInteger userId) {
      this.userId = userId;
    }

    public BigInteger getProjectId() {
      return projectId;
    }

    public void setProjectId(BigInteger projectId) {
      this.projectId = projectId;
    }

    public Date getStartWorkDate() {
      return startWorkDate;
    }

    public void setStartWorkDate(Date startWorkDate) {
      this.startWorkDate = startWorkDate;
    }

    public Date getEndWorkDate() {
      return endWorkDate;
    }

    public void setEndWorkDate(Date endWorkDate) {
      this.endWorkDate = endWorkDate;
    }
  }


}
