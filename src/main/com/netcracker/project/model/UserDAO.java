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

//  void updateProjectStatus(BigInteger id, String status);

  Collection<WorkPeriod> findWorkPeriodsByUserId(BigInteger id);

  Collection<WorkPeriod> findWorkPeriodByUserIdAndProjectId(BigInteger userId, BigInteger projectId);

  void updateWorkingPeriodByUserId(BigInteger userId, BigInteger projectId, UserDAO.WorkPeriod workPeriod);

  enum JobTitle {
    PROJECT_MANAGER, LINE_MANAGER, SOFTWARE_ENGINEER;

    private Integer id;

    JobTitle(Integer id) {
      this.id = id;
    }

    JobTitle() {
      this.id = 0;
    }
  }

  enum ProjectStatus {
    WORKING, TRANSIT;

    private Integer id;

    ProjectStatus(Integer id) {
      this.id = id;
    }

    ProjectStatus() {
      this.id = 0;
    }
  }

  enum UserStatus {
    WORKING, FIRED;

    private Integer id;

    UserStatus(Integer id) {
      this.id = id;
    }

    UserStatus() {
      this.id = 0;
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

    public BigInteger getWorkPeiodId() {
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
