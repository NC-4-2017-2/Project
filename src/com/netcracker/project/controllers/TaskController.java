package com.netcracker.project.controllers;

import com.netcracker.project.controllers.validators.TaskValidator;
import com.netcracker.project.model.ProjectDAO;
import com.netcracker.project.model.TaskDAO;
import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.entity.Project;
import com.netcracker.project.model.entity.User;
import com.netcracker.project.model.enums.JobTitle;
import com.netcracker.project.model.enums.TaskPriority;
import com.netcracker.project.model.enums.TaskStatus;
import com.netcracker.project.model.enums.TaskType;
import com.netcracker.project.model.entity.Task;
import com.netcracker.project.services.impl.DateConverterService;
import java.math.BigInteger;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
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

  private static Logger logger = Logger.getLogger(com.netcracker.project.controllers.TaskController.class);
  private DateConverterService converter = new DateConverterService();

  @Autowired
  private TaskDAO taskDAO;
  @Autowired
  private UserDAO userDAO;
  @Autowired
  private ProjectDAO projectDAO;

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public String createTask(
      @RequestParam("name") String name,
      @RequestParam("taskType") String type,
      @RequestParam("startDate") String startDate,
      @RequestParam("plannedEndDate") String plannedEndDate,
      @RequestParam("priority") String priority,
      @RequestParam("status") String status,
      @RequestParam("description") String description,
      @RequestParam("reopenCounter") Integer reopenCounter,
      @RequestParam("comments") String comments,
      @RequestParam("authorNames") List authorId,
      @RequestParam("userNames") List userId,
      @RequestParam("projectNames") String projectName,
      Model model
  ) {

    logger.info("begin work with process creation:");
    Project project = projectDAO.findProjectByName(projectName);

    Map<String, String> errorMapCreate = new TaskValidator()
        .validationCreate(name, type, startDate, plannedEndDate, priority, status, description,
            comments, authorId.toString(), userId.toString(), projectName);

    if (!errorMapCreate.isEmpty()){
      model.addAttribute("name", name);
      model.addAttribute("taskType", type);
      model.addAttribute("startDate", startDate);
      model.addAttribute("plannedEndDate", plannedEndDate);
      model.addAttribute("priority", priority);
      model.addAttribute("status",  status);
      model.addAttribute("description", description);
      model.addAttribute("comments", comments);
      Map<String, String> authorNames = userDAO.getAllUserName();
      model.addAttribute("authorNames", authorNames);
      Map<String, String> userNames = userDAO.getAllUserName();
      model.addAttribute("userNames", userNames);
      Collection<String> projects = projectDAO.findAllOpenedProjects();
      model.addAttribute("projectNamesList", projects);

      model.addAttribute("errorMap", errorMapCreate);
      return "task/create";
    }

    Task createTask = new Task.TaskBuilder()
        .name(name)
        .taskType(TaskType.valueOf(type))
        .startDate(converter.convertStringToDateFromJSP(startDate))
        .plannedEndDate(converter.convertStringToDateFromJSP(plannedEndDate))
        .priority(TaskPriority.valueOf(priority))
        .status(TaskStatus.valueOf(status))
        .description(description)
        .reopenCounter(reopenCounter)
        .comments(comments)
        .authorId(new BigInteger(authorId.get(0).toString()))
        .userId(new BigInteger(userId.get(0).toString()))
        .projectId(project.getProjectId())
        .build();

    logger.info("createTask request from DB. Task name: " + name);
    taskDAO.createTask(createTask);
    return "response_status/success";
  }


  @RequestMapping(value = "/create", method = RequestMethod.GET)
  public String createTaskWithGetParams(Model model) {
    logger.info("createTask with get params.: ");

    Map<String, String> authorNames = userDAO.getAllUserName();
    Map<String, String> userNames = userDAO.getAllUserName();
    Collection<String> projects = projectDAO.findAllOpenedProjects();
    model.addAttribute("authorNames", authorNames);
    model.addAttribute("userNames", userNames);
    model.addAttribute("projectNamesList", projects);

    return "task/create";
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
      @RequestParam("authorNames") List authorId,
      @RequestParam("userNames") List userId,
      @RequestParam("projectNames") String projectName, Model model) {
    logger.info("begin work updating process:");
    Project project = projectDAO.findProjectByName(projectName);

    Map<String, String> errorMap = new TaskValidator()
        .validationUpdate(name, type, startDate, plannedEndDate, priority, status, description,
            comments, authorId.toString(), userId.toString(), projectName);

    if (!errorMap.isEmpty()){
      Collection<Task> taskCollection = taskDAO.findTaskByTaskId(id);
      model.addAttribute("modelTask", taskCollection);
      Map<String, String> authorNames = userDAO.getAllUserName();
      model.addAttribute("authorNames", authorNames);
      Map<String, String> userNames = userDAO.getAllUserName();
      model.addAttribute("userNames", userNames);
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
        .authorId(new BigInteger(authorId.get(0).toString()))
        .userId(new BigInteger(userId.get(0).toString()))
        .projectId(project.getProjectId())
        .build();

    if (TaskStatus.valueOf(status).getId() == 2){
        reopenCounter++;
        taskDAO.updateReopenCounter(reopenCounter, updatingTask.getTaskId());
    }

    if (TaskStatus.valueOf(status).getId() == 1){
        Date date = new Date();
        taskDAO.updateEndDate(date, updatingTask.getTaskId());
    }

    taskDAO.updateTask(updatingTask);

    return "response_status/success";
  }

  @RequestMapping(value = "/edit={taskId}", method = RequestMethod.GET)
  public String editTaskWithGetParams(@PathVariable("taskId") BigInteger taskId, Model model) {
    logger.info("editTaskWithGetParams method. taskId" + taskId);
    Collection<Task> taskCollection = taskDAO.findTaskByTaskId(taskId);
    Map<String, String> authorNames = userDAO.getAllUserName();
    Map<String, String> userNames = userDAO.getAllUserName();
    Collection<String> projects = projectDAO.findAllOpenedProjects();

    model.addAttribute("authorNames", authorNames);
    model.addAttribute("userNames", userNames);
    model.addAttribute("projectNamesList", projects);
    model.addAttribute("modelTask", taskCollection);
    return "task/edit";

  }

  @RequestMapping(value = "findTaskByPriority", method = RequestMethod.GET)
  public String findTaskByPriority(){
    return "task/findTaskByPriority";
  }

  @RequestMapping(value = "findTaskByUserId", method = RequestMethod.GET)
  public String findTaskByUserId(){
    return "task/findTaskByUserId";
  }

  @RequestMapping(value = "findTaskByPriority", params = "priority", method = RequestMethod.GET)
  public String showTaskListWithPriority(@RequestParam(value = "priority") TaskPriority priority, Model model, Principal principal){
//    Map<String, String> errorMap = new TaskValidator().validationFindTask(priority);
//    if (errorMap.isEmpty()){
//      model.addAttribute("errorMap", errorMap);
//      return  "task/findTask";
//    }

    String loginUser = principal.getName();
    User user = userDAO.findUserByLogin(loginUser);
    Collection<Task> tasks = null;
    if (user.getJobTitle().equals(JobTitle.SOFTWARE_ENGINEER)){
      tasks = taskDAO.findTaskByUserIdAndPriority(user.getUserId(), priority.getId());
    }
    model.addAttribute("taskList", tasks);
    return "task/showTaskListWithPriority";
  }


  @RequestMapping(value = "findTaskByUserId", params = "userId", method = RequestMethod.GET)
  public String showTaskListWithUserId(@RequestParam(value = "userId") BigInteger userId, Model model, Principal principal){

    String loginUser = principal.getName();
    User user = userDAO.findUserByLogin(loginUser);
    Collection<Task> tasksUser = null;
    if (user.getJobTitle().equals(JobTitle.SOFTWARE_ENGINEER)){
      tasksUser = taskDAO.findTaskByUserId(user.getUserId());
    }
    model.addAttribute("tasksUser", tasksUser);
    return "task/showTaskListWithUser";
  }

  @RequestMapping(value = "showTask/{taskId}", method = RequestMethod.GET)
  public String showTask(@PathVariable(value = "taskId") Integer taskId, Model model) {
    Collection<Task> tasksWithPriority  = taskDAO.findTaskByTaskId(BigInteger.valueOf(taskId));
    model.addAttribute("modelTask", tasksWithPriority);
    return "task/showTask";
  }

}
