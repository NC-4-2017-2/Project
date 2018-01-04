package com.netcracker.project.controllers;

import com.netcracker.project.controllers.validators.TaskValidator;
import com.netcracker.project.controllers.validators.errorMessage.ErrorMessages;
import com.netcracker.project.model.ProjectDAO;
import com.netcracker.project.model.TaskDAO;
import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.entity.Project;
import com.netcracker.project.model.entity.Task.TaskBuilder;
import com.netcracker.project.model.entity.User;
import com.netcracker.project.model.enums.JobTitle;
import com.netcracker.project.model.enums.TaskPriority;
import com.netcracker.project.model.enums.TaskStatus;
import com.netcracker.project.model.enums.TaskType;
import com.netcracker.project.model.entity.Task;
import com.netcracker.project.services.impl.DateConverterService;
import java.math.BigInteger;
import java.security.Principal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/task")
public class TaskController {

  private static Logger logger = Logger
      .getLogger(com.netcracker.project.controllers.TaskController.class);
  private DateConverterService converter = new DateConverterService();

  @Autowired
  private TaskDAO taskDAO;
  @Autowired
  private UserDAO userDAO;
  @Autowired
  private ProjectDAO projectDAO;

  @RequestMapping(value = "/createTask", method = RequestMethod.GET)
  public String createTaskWithUsers(Model model) {
    logger.info("createTask with get params.: ");

    Collection<String> projects = projectDAO.findAllOpenedProjects();

    model.addAttribute("projectNamesList", projects);
    return "task/createTask";
  }

  @RequestMapping(value = "/createTask", method = RequestMethod.POST)
  public String createTask(
      @RequestParam("name") String name,
      @RequestParam("taskType") String taskType,
      @RequestParam("startDate") String startDate,
      @RequestParam("plannedEndDate") String plannedEndDate,
      @RequestParam("priority") String priority,
      @RequestParam("description") String description,
      @RequestParam("comments") String comments,
      @RequestParam("projectNames") String projectName,
      @RequestParam("lastName") String lastName,
      @RequestParam("firstName") String firstName,
      Model model, Principal principal) {

    logger.info("begin work with process creation:");
    TaskValidator validator = new TaskValidator();
    Map<String, String> errorMap = new HashMap<>();
    Collection<String> projects = projectDAO.findAllOpenedProjects();
    errorMap = validator
        .validationCreate(name, taskType, startDate, plannedEndDate, priority,
            description, comments, projectName);

    if (!errorMap.isEmpty()) {
      model.addAttribute("name", name);
      model.addAttribute("taskType", taskType);
      model.addAttribute("startDate", startDate);
      model.addAttribute("plannedEndDate", plannedEndDate);
      model.addAttribute("priority", priority);
      model.addAttribute("description", description);
      model.addAttribute("comments", comments);
      model.addAttribute("projectNamesList", projects);
      model.addAttribute("errorMap", errorMap);
      return "task/createTask";
    }


    Integer existenceProject = projectDAO
        .findProjectByNameIfExist(projectName);
    if (existenceProject != 1) {
      errorMap.put("PROJECT_EXIST_ERROR", ErrorMessages.PROJECT_EXIST_ERROR);
      model.addAttribute("errorMap", errorMap);
      return "task/createTask";
    }

    errorMap = validator.validateLastNameAndFirstName(lastName, firstName);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap",
          lastName + " " + firstName + " " + errorMap);
      return "task/createTask";
    }
    Collection<User> users = userDAO
        .findUserByLastNameAndFirstName(lastName, firstName);

    User user = null;
    if (users.isEmpty()) {
      errorMap
          .put("USER_ERROR", lastName + " " +
              firstName + " "
              + ErrorMessages.USER_ERROR);
      model.addAttribute("errorMap", errorMap);
      return "task/createTask";
    }
    if (users.size() >= 1) {
      Iterator<User> userIterator = users.iterator();
      while (userIterator.hasNext()) {
        user = userIterator.next();
      }
    }

    Project project = projectDAO.findProjectByName(projectName);
    User currentUser = userDAO.findUserByLogin(principal.getName());

    Task task = new TaskBuilder()
        .taskType(TaskType.valueOf(taskType))
        .name(name)
        .startDate(converter.convertStringToDateFromJSP(startDate))
        .plannedEndDate(converter.convertStringToDateFromJSP(plannedEndDate))
        .endDate(converter.convertStringToDateFromJSP(plannedEndDate))
        .priority(TaskPriority.valueOf(priority))
        .status(TaskStatus.OPENED)
        .description(description)
        .authorId(currentUser.getUserId())
        .userId(user.getUserId())
        .reopenCounter(0)
        .comments(comments)
        .projectId(project.getProjectId())
        .build();

    logger.info("createTask request from DB. Task name: " + name);
    taskDAO.createTask(task);
    return "responseStatus/success";
  }

  @RequestMapping(value = "/edit={taskId}", method = RequestMethod.POST)
  public String editTask(
      @RequestParam("taskId") BigInteger id,
      @RequestParam("name") String name,
      @RequestParam("taskType") String type,
      @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate,
      @RequestParam("plannedEndDate") String plannedEndDate,
      @RequestParam("priority") String priority,
      @RequestParam("status") String status,
      @RequestParam("description") String description,
      @RequestParam("reopenCounter") Integer reopenCounter,
      @RequestParam("comments") String comments,
      @RequestParam("user") BigInteger userId,
      @RequestParam("projectNames") String projectName, Model model,
      Principal principal) {
    logger.info("begin work updating process:");
    Project project = projectDAO.findProjectByName(projectName);
    Map<String, String> errorMap = new HashMap<>();
    String login = principal.getName();
    User curUser = userDAO.findUserByLogin(login);

    errorMap = new TaskValidator().validateInputId(userId.toString());
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "task/edit";
    }

    BigInteger projectId = null;

    if (curUser.getJobTitle().equals(JobTitle.PROJECT_MANAGER)) {
      projectId = projectDAO.findProjectIdByPMLogin(login);
    } else {
      projectId = projectDAO.findProjectIdByUserLogin(login);
    }

    errorMap = new TaskValidator()
        .validationUpdate(name, type, startDate, plannedEndDate, priority,
            status, description,
            comments, userId.toString(), projectName);

    if (!errorMap.isEmpty()) {
      Collection<Task> taskCollection = taskDAO.findTaskByTaskId(id);
      model.addAttribute("modelTask", taskCollection);
      Collection<User> user = userDAO.findUserByProjectId(projectId);
      model.addAttribute("userList", user);
      Collection<String> projects = projectDAO.findAllOpenedProjects();
      model.addAttribute("projectNamesList", projects);

      model.addAttribute("errorMap", errorMap);
      return "task/edit";
    }

    Task updatingTask = new Task.TaskBuilder()
        .taskId(id)
        .name(name)
        .taskType(TaskType.valueOf(type))
        .startDate(converter.convertStringToDateFromJSP(startDate))
        .endDate(converter.convertStringToDateFromJSP(endDate))
        .plannedEndDate(converter.convertStringToDateFromJSP(plannedEndDate))
        .priority(TaskPriority.valueOf(priority))
        .status(TaskStatus.valueOf(status))
        .description(description)
        .reopenCounter(reopenCounter)
        .comments(comments)
        .authorId(curUser.getUserId())
        .userId(userId)
        .projectId(project.getProjectId())
        .build();

    if (TaskStatus.valueOf(status).getId() == 2) {
      reopenCounter++;
      taskDAO.updateReopenCounter(reopenCounter, updatingTask.getTaskId());
    }

    if (TaskStatus.valueOf(status).getId() == 1) {
      Date date = new Date();
      taskDAO.updateEndDate(date, updatingTask.getTaskId());
    }

    taskDAO.updateTask(updatingTask);

    return "responseStatus/success";
  }

  @RequestMapping(value = "/edit={taskId}", method = RequestMethod.GET)
  public String editTaskWithGetParams(@PathVariable("taskId") BigInteger taskId,
      Model model, Principal principal) {
    logger.info("editTaskWithGetParams method. taskId" + taskId);

    String userLogin = principal.getName();
    User user = userDAO.findUserByLogin(userLogin);
    BigInteger projectId = null;

    if (user.getJobTitle().equals(JobTitle.PROJECT_MANAGER)) {
      projectId = projectDAO.findProjectIdByPMLogin(userLogin);
    } else {
      projectId = projectDAO.findProjectIdByUserLogin(userLogin);
    }

    Collection<User> users = userDAO.findUserByProjectId(projectId);
    Collection<Task> taskCollection = taskDAO.findTaskByTaskId(taskId);
    Collection<String> projects = projectDAO.findAllOpenedProjects();

    model.addAttribute("userList", users);
    model.addAttribute("authorId", user.getUserId());
    model.addAttribute("projectNamesList", projects);
    model.addAttribute("modelTask", taskCollection);
    return "task/edit";

  }

  @RequestMapping(value = "findTaskByPriority", method = RequestMethod.GET)
  public String findTaskByPriority() {
    return "task/findTaskByPriority";
  }

  @RequestMapping(value = "findTaskByStatus", method = RequestMethod.GET)
  public String findTaskByStatus() {
    return "task/findTaskByStatus";
  }

  @RequestMapping(value = "findTaskByUserId", method = RequestMethod.GET)
  public String findTaskByUserId() {
    return "task/findTaskByUserId";
  }


  @RequestMapping(value = "findTaskByPriority", params = "priority", method = RequestMethod.GET)
  public String showTaskListWithPriority(
      @RequestParam(value = "priority") String priority, Model model,
      Principal principal) {

    Map<String, String> errorMap = new TaskValidator()
        .validationFindTaskByPriority(priority);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "businessTrip/findTaskByStatus";
    }

    String loginUser = principal.getName();
    User user = userDAO.findUserByLogin(loginUser);

    Collection<Task> tasks = taskDAO
        .findTaskByUserIdAndPriority(user.getUserId(),
            TaskPriority.valueOf(priority).getId());

    model.addAttribute("taskList", tasks);
    return "task/showTaskListWithPriority";
  }


  @RequestMapping(value = "findTaskByStatus", params = "status", method = RequestMethod.GET)

  public String showTaskListWithStatus(
      @RequestParam(value = "status") String status, Model model,
      Principal principal) {

    Map<String, String> errorMap = new TaskValidator()
        .validationFindTaskByStatus(status);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "businessTrip/findTaskByStatus";
    }

    String loginUser = principal.getName();
    User user = userDAO.findUserByLogin(loginUser);

    Collection<Task> tasks = taskDAO.findTaskByUserIdAndStatus(user.getUserId(),
        TaskStatus.valueOf(status).getId());

    model.addAttribute("taskList", tasks);

    return "task/showTaskListWithStatus";
  }


  @RequestMapping(value = "findTaskByUserId", params = {"status", "startDate",
      "endDate"}, method = RequestMethod.GET)
  public String showTaskListWithUser(@RequestParam("status") String status,
      @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate, Model model,
      Principal principal) {

    Map<String, String> errorMap = new HashMap<>();
    TaskValidator validator = new TaskValidator();
    errorMap = validator.validationFindTaskByStatus(status);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "task/findTaskByUserId";
    }
    errorMap = validator.validateBetweenDates(startDate, endDate);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "task/findTaskByUserId";
    }

    User currentUser = userDAO.findUserByLogin(principal.getName());
    Collection<Task> tasksPerPeriod = taskDAO
        .findTaskByUserAndStatusPerPeriod(currentUser.getUserId(),
            TaskStatus.valueOf(status).getId(),
            converter.convertStringToDateFromJSP(startDate),
            converter.convertStringToDateFromJSP(endDate));

    model.addAttribute("taskList", tasksPerPeriod);

    return "task/showTaskListWithUser";
  }

  @RequestMapping(value = "showTask/{taskId}", method = RequestMethod.GET)
  public String showTask(@PathVariable(value = "taskId") Integer taskId,
      Model model) {
    Collection<Task> tasksWithPriority = taskDAO
        .findTaskByTaskId(BigInteger.valueOf(taskId));
    model.addAttribute("modelTask", tasksWithPriority);
    return "task/showTask";
  }

}
