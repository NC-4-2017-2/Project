package com.netcracker.project.config;

import com.netcracker.project.model.BusinessTripDAO;
import com.netcracker.project.model.CommentDAO;
import com.netcracker.project.model.ProjectDAO;
import com.netcracker.project.model.SecurityUserDAO;
import com.netcracker.project.model.StatisticDAO;
import com.netcracker.project.model.TaskDAO;
import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.VacationDAO;
import com.netcracker.project.model.WorkingDayDAO;
import com.netcracker.project.model.impl.BusinessTripDAOImpl;
import com.netcracker.project.model.impl.CommentDAOImpl;
import com.netcracker.project.model.impl.ProjectDAOImpl;
import com.netcracker.project.model.impl.SecurityUserDAOImpl;
import com.netcracker.project.model.impl.StatisticDAOImpl;
import com.netcracker.project.model.impl.TaskDAOImpl;
import com.netcracker.project.model.impl.UserDAOImpl;
import com.netcracker.project.model.impl.VacationDAOImpl;
import com.netcracker.project.model.impl.WorkingDayDAOImpl;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class ApplicationContext {

  @Bean(name = "dataSource")
  public DataSource getDataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(System.getenv("jdbc.driverClassName"));
    dataSource.setUrl(System.getenv("jdbc.url"));
    dataSource.setUsername(System.getenv("jdbc.username"));
    dataSource.setPassword(System.getenv("jdbc.password"));
    return dataSource;
  }

  @Bean(name = "securityUserDAO")
  public SecurityUserDAO getSecurityUserDAO() {
    SecurityUserDAOImpl securityUserDAO = new SecurityUserDAOImpl();
    securityUserDAO.setDataSource(getDataSource());
    return securityUserDAO;
  }

  @Bean(name = "userDAO")
  public UserDAO getUserDAO() {
    UserDAOImpl userDAO = new UserDAOImpl();
    userDAO.setDataSource(getDataSource());
    return userDAO;
  }

  @Bean(name = "projectDAO")
  public ProjectDAO getProjectDAO() {
    ProjectDAOImpl projectDAO = new ProjectDAOImpl();
    projectDAO.setDataSource(getDataSource());
    return projectDAO;
  }

  @Bean(name = "vacationDAO")
  public VacationDAO getVacationDAO() {
    VacationDAOImpl vacationDAO = new VacationDAOImpl();
    vacationDAO.setDataSource(getDataSource());
    return vacationDAO;
  }

  @Bean(name = "businessTripDAO")
  public BusinessTripDAO getBusinessTripDAO() {
    BusinessTripDAOImpl businessTripDAO = new BusinessTripDAOImpl();
    businessTripDAO.setDataSource(getDataSource());
    return businessTripDAO;
  }

  @Bean(name = "taskDAO")
  public TaskDAO getTaskDAO() {
    TaskDAOImpl taskDAO = new TaskDAOImpl();
    taskDAO.setDataSource(getDataSource());
    return taskDAO;
  }


  @Bean(name = "commentDAO")
  public CommentDAO getCommentDAO() {
    CommentDAOImpl commentDAO = new CommentDAOImpl();
    commentDAO.setDataSource(getDataSource());
    return commentDAO;
  }

  @Bean(name = "workingDayDAO")
  public WorkingDayDAO getWorkingDayDAO() {
    WorkingDayDAOImpl workingDayDAO = new WorkingDayDAOImpl();
    workingDayDAO.setDataSource(getDataSource());
    return workingDayDAO;
  }

  @Bean(name = "statisticDAO")
  public StatisticDAO getStatisticDAO() {
    StatisticDAOImpl statisticDAO = new StatisticDAOImpl();
    statisticDAO.setDataSource(getDataSource());
    return statisticDAO;
  }
}
