package main.net.cracker.project.model;

import java.io.File;
import java.math.BigInteger;
import java.util.Date;
import main.net.cracker.project.model.entity.User;

public interface UserDAO {

  User createUser(User user);

  void updateUser(BigInteger id, User user);

  User findUserByUserId(BigInteger id);

  User findUserByLogin(String login);

  void updatePhoneNumber(BigInteger id, String phoneNumber);

  void updateEmail(BigInteger id, String email);

  void updatePassword(BigInteger id, String password);

  void updatePhoto(BigInteger id, File photo);

  void updateWorkingPeriodByUserId(BigInteger userId, BigInteger projectId);

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

    BigInteger userId;
    BigInteger projectId;
    Date startWorkDate;
    Date endWorkDate;

    public WorkPeriod(BigInteger userId, BigInteger projectId,
        Date startWorkDate, Date endWorkDate) {
      this.userId = userId;
      this.projectId = projectId;
      this.startWorkDate = startWorkDate;
      this.endWorkDate = endWorkDate;
    }
  }


}
