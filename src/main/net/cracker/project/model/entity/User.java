package main.net.cracker.project.model.entity;

import java.io.File;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import main.net.cracker.project.model.UserDAO;
import main.net.cracker.project.model.UserDAO.JobTitle;
import main.net.cracker.project.model.UserDAO.WorkPeriod;

public class User{
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
  private Collection<WorkPeriod> workPeriods;
  private Boolean userStatus;
  private Boolean projectStatus;


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

  public void setHireDate(Date hireDate) {
    this.hireDate = hireDate;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public JobTitle getJobTitle() {
    return jobTitle;
  }

  public void setJobTitle(JobTitle jobTitle) {
    this.jobTitle = jobTitle;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Collection<WorkPeriod> getWorkPeriods() {
    return workPeriods;
  }

  public void setWorkPeriods(
      Collection<WorkPeriod> workPeriods) {
    this.workPeriods = workPeriods;
  }

  public Boolean getUserStatus() {
    return userStatus;
  }

  public void setUserStatus(Boolean userStatus) {
    this.userStatus = userStatus;
  }

  public Boolean getProjectStatus() {
    return projectStatus;
  }

  public void setProjectStatus(Boolean projectStatus) {
    this.projectStatus = projectStatus;
  }


}
