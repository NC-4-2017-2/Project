package com.netcracker.project.model.entity;

import com.netcracker.project.model.enums.JobTitle;
import com.netcracker.project.model.enums.ProjectStatus;
import com.netcracker.project.model.enums.UserRole;
import com.netcracker.project.model.enums.UserStatus;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

public class User {

  private BigInteger userId;
  private String firstName;
  private String lastName;
  private String email;
  private Date dateOfBirth;
  private Date hireDate;
  private String phoneNumber;
  private String photo;
  private JobTitle jobTitle;
  private String login;
  private String password;
  private UserRole userRole;
  private Collection<WorkPeriod> workPeriods;
  private UserStatus userStatus;
  private ProjectStatus projectStatus;

  private User(UserBuilder builder) {
    this.userId = builder.userId;
    this.firstName = builder.firstName;
    this.lastName = builder.lastName;
    this.email = builder.email;
    this.dateOfBirth = builder.dateOfBirth;
    this.hireDate = builder.hireDate;
    this.phoneNumber = builder.phoneNumber;
    this.photo = builder.photo;
    this.jobTitle = builder.jobTitle;
    this.login = builder.login;
    this.password = builder.password;
    this.userRole = builder.role;
    this.workPeriods = builder.workPeriods;
    this.userStatus = builder.userStatus;
    this.projectStatus = builder.projectStatus;
  }

  public BigInteger getUserId() {
    return userId;
  }

  public void setUserId(BigInteger userId) {
    this.userId = userId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public Date getHireDate() {
    return hireDate;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getPhoto() {
    return photo;
  }

  public JobTitle getJobTitle() {
    return jobTitle;
  }

  public String getLogin() {
    return login;
  }

  public String getPassword() {
    return password;
  }

  public UserRole getUserRole() {
    return userRole;
  }

  public Collection<WorkPeriod> getWorkPeriods() {
    return workPeriods;
  }

  public UserStatus getUserStatus() {
    return userStatus;
  }

  public ProjectStatus getProjectStatus() {
    return projectStatus;
  }


  public static class UserBuilder {

    private BigInteger userId;
    private String firstName;
    private String lastName;
    private String email;
    private Date dateOfBirth;
    private Date hireDate;
    private String phoneNumber;
    private String photo;
    private JobTitle jobTitle;
    private String login;
    private String password;
    private UserRole role;
    private Collection<WorkPeriod> workPeriods;
    private UserStatus userStatus;
    private ProjectStatus projectStatus;

    public UserBuilder() {
    }

    public UserBuilder userId(BigInteger userId) {
      this.userId = userId;
      return this;
    }

    public UserBuilder firstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public UserBuilder lastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public UserBuilder email(String email) {
      this.email = email;
      return this;
    }

    public UserBuilder dateOfBirth(Date dateOfBirth) {
      this.dateOfBirth = dateOfBirth;
      return this;
    }

    public UserBuilder hireDate(Date hireDate) {
      this.hireDate = hireDate;
      return this;
    }

    public UserBuilder phoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
      return this;
    }

    public UserBuilder photo(String photo) {
      this.photo = photo;
      return this;
    }

    public UserBuilder jobTitle(JobTitle jobTitle) {
      this.jobTitle = jobTitle;
      return this;
    }

    public UserBuilder login(String login) {
      this.login = login;
      return this;
    }

    public UserBuilder password(String password) {
      this.password = password;
      return this;
    }

    public UserBuilder role(UserRole role) {
      this.role = role;
      return this;
    }

    public UserBuilder workPeriods(Collection<WorkPeriod> workPeriods) {
      this.workPeriods = workPeriods;
      return this;
    }

    public UserBuilder userStatus(UserStatus userStatus) {
      this.userStatus = userStatus;
      return this;
    }

    public UserBuilder projectStatus(ProjectStatus projectStatus) {
      this.projectStatus = projectStatus;
      return this;
    }

    public User build() {
      return new User(this);
    }
  }

  @Override
  public String toString() {
    return "User{" +
        "userId=" + userId +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", email='" + email + '\'' +
        ", dateOfBirth=" + dateOfBirth +
        ", hireDate=" + hireDate +
        ", phoneNumber='" + phoneNumber + '\'' +
        ", photo='" + photo + '\'' +
        ", jobTitle=" + jobTitle +
        ", login='" + login + '\'' +
        ", password='" + password + '\'' +
        ", userRole=" + userRole +
        ", workPeriods=" + workPeriods +
        ", userStatus=" + userStatus +
        ", projectStatus=" + projectStatus +
        '}';
  }
}
