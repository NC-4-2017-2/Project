package com.netcracker.project.controllers;

import com.netcracker.project.controllers.validators.CommentValidator;
import com.netcracker.project.controllers.validators.TaskValidator;
import com.netcracker.project.controllers.validators.UserValidator;
import com.netcracker.project.controllers.validators.errorMessage.ErrorMessages;
import com.netcracker.project.model.CommentDAO;
import com.netcracker.project.model.ProjectDAO;
import com.netcracker.project.model.TaskDAO;
import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.entity.Comment;
import com.netcracker.project.model.entity.Project;
import com.netcracker.project.model.entity.Task.TaskBuilder;
import com.netcracker.project.model.entity.User;
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
import org.springframework.security.access.annotation.Secured;
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
  @Autowired
  private CommentDAO commentDAO;


  @Secured({"ROLE_PM"})
  @RequestMapping(value = "selectTaskType", method = RequestMethod.GET)
  public String createTaskType() {
    return "task/selectTaskType";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "selectTaskType", params = "taskType", method = RequestMethod.GET)
  public String createType(@RequestParam("taskType") String taskType, Model model) {
    Map<String, String> errorMap = new HashMap<>();
    TaskValidator validator = new TaskValidator();
    errorMap = validator.validateType(taskType);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "task/selectTaskType";
    }
    Collection<String> projects = projectDAO.findAllOpenedProjects();
    model.addAttribute("projectNamesList", projects);

    return "task/createTask";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "/createTask", method = RequestMethod.POST)
  public String createTask(
      @RequestParam("name") String name,
      @RequestParam("taskType") String taskType,
      @RequestParam("startDate") String startDate,
      @RequestParam("plannedEndDate") String plannedEndDate,
      @RequestParam("priority") String priority,
      @RequestParam("description") String description,
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
            description, projectName);

    if (!errorMap.isEmpty()) {
      model.addAttribute("name", name);
      model.addAttribute("taskType", taskType);
      model.addAttribute("startDate", startDate);
      model.addAttribute("plannedEndDate", plannedEndDate);
      model.addAttribute("priority", priority);
      model.addAttribute("description", description);
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
        .projectId(project.getProjectId())
        .build();
    logger.info("createTask request from DB. Task name: " + name);
    taskDAO.createTask(task);

    return "responseStatus/success";
  }

  @RequestMapping(value = "/updateTask/{id}", method = RequestMethod.POST)
  public String updateTask(
      @PathVariable("id") String id,
      @RequestParam("name") String name,
      @RequestParam("taskType") String type,
      @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate,
      @RequestParam("plannedEndDate") String plannedEndDate,
      @RequestParam("priority") String priority,
      @RequestParam("status") String status,
      @RequestParam("description") String description,
      @RequestParam("reopenCounter") Integer reopenCounter,
      @RequestParam("lastName") String lastName,
      @RequestParam("firstName") String firstName,
      @RequestParam("projectNames") String projectName, Model model,
      Principal principal) {
    logger.info("begin work updating process:");

    logger.info("begin work with process creation:");
    TaskValidator validator = new TaskValidator();
    Map<String, String> errorMap = new HashMap<>();
    Collection<String> projects = projectDAO.findAllOpenedProjects();

    errorMap = validator.validateInputId(id);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "task/updateTask";
    }

    BigInteger validTaskId = new BigInteger(id);

    errorMap = validator.validationUpdate(name, type, startDate,
         plannedEndDate, priority, status,
         description, projectName);

    if (!errorMap.isEmpty()) {
      model.addAttribute("name", name);
      model.addAttribute("taskType", type);
      model.addAttribute("startDate", startDate);
      model.addAttribute("plannedEndDate", plannedEndDate);
      model.addAttribute("priority", priority);
      model.addAttribute("status", status);
      model.addAttribute("description", description);
      model.addAttribute("projectNamesList", projects);
      model.addAttribute("errorMap", errorMap);
      return "task/updateTask";
    }


    Integer existenceProject = projectDAO
        .findProjectByNameIfExist(projectName);
    if (existenceProject != 1) {
      errorMap.put("PROJECT_EXIST_ERROR", ErrorMessages.PROJECT_EXIST_ERROR);
      model.addAttribute("errorMap", errorMap);
      return "task/updateTask";
    }

    errorMap = validator.validateLastNameAndFirstName(lastName, firstName);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap",
          lastName + " " + firstName + " " + errorMap);
      return "task/updateTask";
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
      return "task/updateTask";
    }
    if (users.size() >= 1) {
      Iterator<User> userIterator = users.iterator();
      while (userIterator.hasNext()) {
        user = userIterator.next();
      }
    }

    Project updationProject = projectDAO.findProjectByName(projectName);
    User currentUser = userDAO.findUserByLogin(principal.getName());

    Task updatingTask = new Task.TaskBuilder()
        .taskId(validTaskId)
        .name(name)
        .taskType(TaskType.valueOf(type))
        .startDate(converter.convertStringToDateFromJSP(startDate))
        .endDate(converter.convertStringToDateFromJSP(endDate))
        .plannedEndDate(converter.convertStringToDateFromJSP(plannedEndDate))
        .priority(TaskPriority.valueOf(priority))
        .status(TaskStatus.valueOf(status))
        .description(description)
        .reopenCounter(0)
        .authorId(currentUser.getUserId())
        .userId(user.getUserId())
        .projectId(updationProject.getProjectId())
        .build();

    if (TaskStatus.valueOf(status).equals(TaskStatus.REOPENED) || TaskStatus.valueOf(status).equals(TaskStatus.READY_FOR_TESTING)) {
      reopenCounter++;
      taskDAO.updateReopenCounter(reopenCounter, updatingTask.getTaskId());
    }

    if (TaskStatus.valueOf(status).equals(TaskStatus.CLOSED)) {
      Date date = new Date();
      taskDAO.updateEndDate(date, updatingTask.getTaskId());
    }

    taskDAO.updateTask(updatingTask);

    return "responseStatus/success";
  }

  @RequestMapping(value = "/updateTask/{id}", method = RequestMethod.GET)
  public String updateTaskWithGetParams(@PathVariable("id") String id,
      Model model, Principal principal) {
    logger.info("editTaskWithGetParams method. taskId" + id);

    String currentUserLogin = principal.getName();
    User currentUser = userDAO.findUserByLogin(currentUserLogin);

    BigInteger validId = new BigInteger(id);

    Task task = taskDAO.findTaskByTaskId(validId);
    User taskUser = userDAO.findUserByUserId(task.getUserId());
    Collection<String> projects = projectDAO.findAllOpenedProjects();


    model.addAttribute("task", task);
    model.addAttribute("taskId", task.getTaskId());
    model.addAttribute("name", task.getName());
    model.addAttribute("type", task.getTaskType());
    model.addAttribute("startDate", task.getStartDate());
    model.addAttribute("endDate", task.getEndDate());
    model.addAttribute("plannedEndDate", task.getPlannedEndDate());
    model.addAttribute("priority", task.getPriority());
    model.addAttribute("status", task.getStatus());
    model.addAttribute("description", task.getDescription());
    model.addAttribute("reopenCounter", task.getReopenCounter());
    model.addAttribute("taskUser", taskUser);
    model.addAttribute("projectNamesList", projects);
    model.addAttribute("curUser", currentUser);

    return "task/updateTask";

  }

  @RequestMapping(value = "/showTask/{taskId}", method = RequestMethod.GET)
  public String showTask(@PathVariable("taskId") String taskId,
                         Model model, Principal principal){
    TaskValidator validator = new TaskValidator();
    Map<String, String> errorMap = validator.validateInputId(taskId);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "task/showTask";
    }
    BigInteger validTaskId = new BigInteger(taskId);

    Integer taskExistence = taskDAO.findIfTaskExists(validTaskId);
    Map<String, String> existenceError = validator.validateExistence(taskExistence);
    if (!existenceError.isEmpty()) {
      model.addAttribute("errorMap", existenceError);
      return "task/showTask";
    }

    String currentUserLogin = principal.getName();
    User currentUser = userDAO.findUserByLogin(currentUserLogin);

    Task task = taskDAO.findTaskByTaskId(validTaskId);
    User taskAuthor = userDAO.findUserByUserId(task.getAuthorId());
    User taskUser = userDAO.findUserByUserId(task.getUserId());
    Project project = projectDAO.findProjectByProjectId(task.getProjectId());

    model.addAttribute("task", task);
    model.addAttribute("taskId", task.getTaskId());
    model.addAttribute("name", task.getName());
    model.addAttribute("type", task.getTaskType());
    model.addAttribute("startDate", task.getStartDate());
    model.addAttribute("endDate", task.getEndDate());
    model.addAttribute("plannedEndDate", task.getPlannedEndDate());
    model.addAttribute("priority", task.getPriority());
    model.addAttribute("status", task.getStatus());
    model.addAttribute("description", task.getDescription());
    model.addAttribute("reopenCounter", task.getReopenCounter());
    model.addAttribute("taskAuthor", taskAuthor);
    model.addAttribute("taskUser", taskUser);
    model.addAttribute("project", project);
    model.addAttribute("curUser", currentUser);

    return "task/showTask";
  }

  @RequestMapping(value = "findTaskByPriority", method = RequestMethod.GET)
  public String findTaskByPriority() {
    return "task/findTaskByPriority";
  }

  @RequestMapping(value = "findTaskPerPeriodAndStatus", method = RequestMethod.GET)
  public String findTaskPerPeriodAndStatus() {
    return "task/findTaskPerPeriodAndStatus";
  }

  @RequestMapping(value = "findTaskByFirstAndLastName", method = RequestMethod.GET)
  public String findTaskByFirstAndLastName() {
    return "task/findTaskByFirstAndLastName";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "findTaskByProject", method = RequestMethod.GET)
  public String findTaskByProject(Model model) {
    Collection<String> projects = projectDAO.findAllOpenedProjects();
    model.addAttribute("projectNamesList", projects);
    return "task/findTaskByProject";
  }

  @RequestMapping(value = "findOwnTask", method = RequestMethod.GET)
  public String findOwnTask() {
    return "task/findOwnTask";
  }

  @RequestMapping(value = "findTaskByPriority", params = "priority", method = RequestMethod.GET)
  public String showTaskListWithPriority(
      @RequestParam(value = "priority") String priority, Model model,
      Principal principal) {

    Map<String, String> errorMap = new TaskValidator()
        .validationFindTaskByPriority(priority);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "task/findTaskByPriority";
    }

    String loginUser = principal.getName();
    User user = userDAO.findUserByLogin(loginUser);

    Collection<Task> tasks = taskDAO
        .findTaskByUserIdAndPriority(user.getUserId(),
            TaskPriority.valueOf(priority).getId());


    Task task = null;
    if (tasks.size() >= 1) {
      Iterator<Task> taskIterator = tasks.iterator();
      while (taskIterator.hasNext()) {
        task = taskIterator.next();
      }
    }

    if (tasks.isEmpty()){
      Map<String, String> error = new TaskValidator().validationEntityTask(task);
      if (!error.isEmpty()) {
        model.addAttribute("errorMap", error);
        return "task/viewTask";
      }
    }

    model.addAttribute("taskList", tasks);
    return "task/viewTask";
  }

  @RequestMapping(value = "findTaskPerPeriodAndStatus", params = {"status", "startDate",
      "endDate"}, method = RequestMethod.GET)
  public String findTaskListWithPeriods(@RequestParam("status") String status,
      @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate, Model model,
      Principal principal) {

    Map<String, String> errorMap = new HashMap<>();
    TaskValidator validator = new TaskValidator();
    errorMap = validator.validationFindTaskByStatus(status);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "task/findTaskPerPeriodAndStatus";
    }
    errorMap = validator.validateBetweenDates(startDate, endDate);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "task/findTaskPerPeriodAndStatus";
    }

    String loginUser = principal.getName();
    User user = userDAO.findUserByLogin(loginUser);
    Collection<Task> tasksPerPeriod = taskDAO
        .findTaskByUserAndStatusPerPeriod(user.getUserId(),
            TaskStatus.valueOf(status).getId(),
            converter.convertStringToDateFromJSP(startDate),
            converter.convertStringToDateFromJSP(endDate));

    Task task = null;
    if (tasksPerPeriod.size() >= 1) {
      Iterator<Task> taskIterator = tasksPerPeriod.iterator();
      while (taskIterator.hasNext()) {
        task = taskIterator.next();
      }
    }

    if (tasksPerPeriod.isEmpty()){
      Map<String, String> error = new TaskValidator().validationEntityTask(task);
      if (!error.isEmpty()) {
        model.addAttribute("errorMap", error);
        return "task/viewTask";
      }
    }

    model.addAttribute("taskList", tasksPerPeriod);

    return "task/viewTask";
  }

  @RequestMapping(value = "findTaskByFirstAndLastName", params = {"lastName", "firstName", "status"}, method = RequestMethod.GET)
  public String showTaskListWithUsers(
      @RequestParam("lastName") String lastName,
      @RequestParam("firstName") String firstName,
      @RequestParam("status") String status, Model model) {

    Map<String, String> errorMap = new TaskValidator().validateLastNameAndFirstName(lastName, firstName);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "task/findTaskByFirstAndLastName";
    }

    Collection<User> users = userDAO.findUserByLastNameAndFirstName(lastName, firstName);

    if (users.isEmpty()) {
      errorMap.put("USER_ERROR", lastName + " " +
              firstName + " "
              + ErrorMessages.USER_ERROR);
      model.addAttribute("errorMap", errorMap);
      return "task/findTaskByFirstAndLastName";
    }

    User user = null;
    if (users.size() >= 1) {
      Iterator<User> userIterator = users.iterator();
      while (userIterator.hasNext()) {
        user = userIterator.next();
      }
    }
    Collection<Task> tasks = taskDAO.findTaskByUserIdAndStatus(user.getUserId(), TaskStatus.valueOf(status).getId());

    errorMap = new TaskValidator()
        .validationFindTaskByStatus(status);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "task/viewTask";
    }

    Task task = null;
    if (tasks.size() >= 1) {
      Iterator<Task> taskIterator = tasks.iterator();
      while (taskIterator.hasNext()) {
        task = taskIterator.next();
      }
    }

    if (tasks.isEmpty()){
        Map<String, String> existenceError = new TaskValidator().validationEntityTask(task);
        if (!existenceError.isEmpty()) {
          model.addAttribute("errorMap", existenceError);
          return "task/viewTask";
        }
    }

    model.addAttribute("taskList", tasks);

    return "task/viewTask";
  }

  @RequestMapping(value = "findOwnTask", params = "status", method = RequestMethod.GET)
  public String showTaskOwnTask(
      @RequestParam(value = "status") String status, Model model,
      Principal principal) {

    Map<String, String> errorMap = new TaskValidator().validationFindTaskByStatus(status);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      return "task/viewTask";
    }

    String loginUser = principal.getName();
    User user = userDAO.findUserByLogin(loginUser);

    Collection<Task> tasks = taskDAO.findTaskByUserIdAndStatus(user.getUserId(),
        TaskStatus.valueOf(status).getId());

    Task task = null;
    if (tasks.size() >= 1) {
      Iterator<Task> taskIterator = tasks.iterator();
      while (taskIterator.hasNext()) {
        task = taskIterator.next();
      }
    }

    if (tasks.isEmpty()){
      Map<String, String> error = new TaskValidator().validationEntityTask(task);
      if (!error.isEmpty()) {
        model.addAttribute("errorMap", error);
        return "task/viewTask";
      }
    }

    model.addAttribute("taskList", tasks);

    return "task/viewTask";
  }

  @Secured({"ROLE_PM"})
  @RequestMapping(value = "findTaskByProject", params = "project", method = RequestMethod.GET)
  public String showTaskListWithProjects(@RequestParam("project") String projectName, Model model) {

    Collection<String> projects = projectDAO.findAllOpenedProjects();

    Map<String, String> errorMap = new TaskValidator().validationFindTaskByProject(projectName);
    if (!errorMap.isEmpty()) {
      model.addAttribute("errorMap", errorMap);
      model.addAttribute("projectNamesList", projects);
      return "task/findTaskByProject";
    }

    Integer existenceProject = projectDAO.findProjectByNameIfExist(projectName);
    if (existenceProject != 1) {
      errorMap.put("PROJECT_EXIST_ERROR", ErrorMessages.PROJECT_EXIST_ERROR);
      model.addAttribute("errorMap", errorMap);
      return "task/findTaskByProject";
    }


    Project project = projectDAO.findProjectByName(projectName);
    Collection<Task> tasks = taskDAO.findTaskByProjectId(project.getProjectId());

    Task task = null;
    if (tasks.size() >= 1) {
      Iterator<Task> taskIterator = tasks.iterator();
      while (taskIterator.hasNext()) {
        task = taskIterator.next();
      }
    }

    if (tasks.isEmpty()){
      Map<String, String> existenceError = new TaskValidator().validationEntityTask(task);
      if (!existenceError.isEmpty()) {
        model.addAttribute("errorMap", existenceError);
        return "task/viewTask";
      }
    }

    model.addAttribute("taskList", tasks);

    return "task/viewTask";
  }

  @RequestMapping(value = "/createComment/taskId/{taskId}/userId/{userId}/creationDate/{creationDate}",
      method = RequestMethod.POST)
  public String createComment(
      @RequestParam("bodyComment") String bodyComment,
      @PathVariable("creationDate") String creationDate,
      @PathVariable("taskId") String taskId,
      @PathVariable("userId") String userId,
      Model model, Principal principal) {

    logger.info("begin work with creation comment : ");
    CommentValidator validator = new CommentValidator();
    Map<String, String> errorMap = new HashMap<>();

    errorMap = validator.validationCreate(bodyComment);

    if (!errorMap.isEmpty()) {
      model.addAttribute("bodyComment", bodyComment);
      model.addAttribute("errorMap", errorMap);
      return "task/createComment";
    }

    TaskValidator taskValidator = new TaskValidator();
    errorMap = taskValidator.validateInputId(taskId);
    if (!errorMap.isEmpty()){
      model.addAttribute("errorMap", errorMap);
    }

    UserValidator userValidator = new UserValidator();
    errorMap = userValidator.validateInputId(userId);


    BigInteger validTaskId = new BigInteger(taskId);
    Task tasks = taskDAO.findTaskByTaskId(validTaskId);

    String currentUserLogin = principal.getName();
    User currentUser = userDAO.findUserByLogin(currentUserLogin);

    if (!errorMap.isEmpty()){
      model.addAttribute("errorMap", errorMap);
    }

    Date date = new Date();
    creationDate = converter.convertDateToString(date);

    if (!errorMap.isEmpty()){
      model.addAttribute("creationDate", creationDate);
      model.addAttribute("errorMap", errorMap);
    }


    Comment comment = new Comment.CommentBuilder()
        .bodyComment(bodyComment)
        .creationDate(converter.convertStringToDateFromJSP(creationDate))
        .userId(currentUser.getUserId())
        .taskId(tasks.getTaskId())
        .build();

    commentDAO.createComment(comment);

    return "responseStatus/success";
  }

  @RequestMapping(value = "/createComment/{taskId}", method = RequestMethod.GET)
  public String createCommentGet(@PathVariable(value = "taskId") String taskId, Model model, Principal principal) {
    logger.info("createComment with get params.: ");
    Map<String, String> errorMap = new HashMap<>();
    TaskValidator validator = new TaskValidator();

    errorMap = validator.validateInputId(taskId);
    if (!errorMap.isEmpty()){
      model.addAttribute("errorMap", errorMap);
    }

    if (!errorMap.isEmpty()){
      model.addAttribute("errorMap", errorMap);
    }

    String currentUserLogin = principal.getName();
    User currentUser = userDAO.findUserByLogin(currentUserLogin);

    Date date = new Date();

    model.addAttribute("errorMap", errorMap);
    model.addAttribute("userId", currentUser.getUserId());
    model.addAttribute("creationDate", date);

    return "task/createComment";
  }
}
