package com.netcracker.project.controllers;

import com.netcracker.project.model.TaskDAO;
import com.netcracker.project.model.enums.TaskPriority;
import com.netcracker.project.model.enums.TaskStatus;
import com.netcracker.project.model.enums.TaskType;
import com.netcracker.project.model.entity.Task;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

  @Autowired
  private TaskDAO taskDAO;

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public String createTask(
      @RequestParam("taskId") Integer id,
      @RequestParam("name") String name,
      @RequestParam("taskType") TaskType type,
      @RequestParam("startDate")@DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
      @RequestParam("plannedEndDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date plannedEndDate,
      @RequestParam("priority") TaskPriority priority,
      @RequestParam("status") TaskStatus status,
      @RequestParam("description") String description,
      @RequestParam("reopenCounter") Integer reopenCounter,
      @RequestParam("comments") String comments,
      @RequestParam("authorId") Integer authorId,
      @RequestParam("userId") Integer userId,
      @RequestParam("projectId") Integer projectId
  ) {

    logger.info("begin work with process creation:");
    Task createTask = new Task.TaskBuilder()
        .taskId(BigInteger.valueOf(id))
        .name(name)
        .taskType(type)
        .startDate(startDate)
        .endDate(endDate)
        .plannedEndDate(plannedEndDate)
        .priority(priority)
        .status(status)
        .description(description)
        .reopenCounter(reopenCounter)
        .comments(comments)
        .authorId(BigInteger.valueOf(authorId))
        .userId(BigInteger.valueOf(userId))
        .projectId(BigInteger.valueOf(projectId))
        .build();

    logger.info("createTask request from DB. Task id: " + id);
    taskDAO.createTask(createTask);
    return "response_status/success";
  }


  @RequestMapping(value = "/create", method = RequestMethod.GET)
  public String createTaskWithGetParams() {
    logger.info("createTask with get params.: ");
    return "task/create";
  }

  @RequestMapping(value = "/edit={projectId}/taskId={taskId}", method = RequestMethod.POST)
  public String editTask(
      @RequestParam("taskId") Integer id,
      @RequestParam("name") String name,
      @RequestParam("taskType") TaskType type,
      @RequestParam("startDate")@DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
      @RequestParam("plannedEndDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date plannedEndDate,
      @RequestParam("priority") TaskPriority priority,
      @RequestParam("status") TaskStatus status,
      @RequestParam("description") String description,
      @RequestParam("reopenCounter") Integer reopenCounter,
      @RequestParam("comments") String comments,
      @RequestParam("authorId") Integer authorId,
      @RequestParam("userId") Integer userId,
      @RequestParam("projectId") Integer projectId
  ) {
    logger.info("begin work updating process:");
    Task updatingTask = new Task.TaskBuilder()
        .taskId(BigInteger.valueOf(id))
        .name(name)
        .taskType(type)
        .startDate(startDate)
        .endDate(endDate)
        .plannedEndDate(plannedEndDate)
        .priority(priority)
        .status(status)
        .description(description)
        .reopenCounter(reopenCounter)
        .comments(comments)
        .authorId(BigInteger.valueOf(authorId))
        .userId(BigInteger.valueOf(userId))
        .projectId(BigInteger.valueOf(projectId))
        .build();

    taskDAO.updateTask(updatingTask);
    return "response_status/success";
  }

  @RequestMapping(value = "/edit={projectId}/taskId={taskId}", method = RequestMethod.GET)
  public String editTaskWithGetParams(@PathVariable("projectId") BigInteger projectId, @PathVariable("taskId") BigInteger taskId, Model model) {
    logger.info("editTaskWithGetParams method. projectId" + projectId + "taskId" + taskId);
    Collection<Task> taskCollection = taskDAO.findTaskByProjectIdAndTaskId(projectId, taskId);

    model.addAttribute("modelTask", taskCollection);
    return "task/edit";

  }

  @RequestMapping(value = "/view={projectId}/taskId={taskId}", method = RequestMethod.GET)
  public String findTaskByProjectIdAndTask(@PathVariable("projectId") BigInteger projectId,
                                           @PathVariable("taskId") BigInteger taskId, Model model) {

    logger.info("findTaskByProjectIdAndTask method. projectId: " + projectId + "taskId" + taskId);
    Collection<Task> taskCollection = taskDAO.findTaskByProjectIdAndTaskId(projectId, taskId);
    model.addAttribute("modelTask", taskCollection);

    return "task/show_task";
  }

  @RequestMapping(value = "/view={projectId}/priority={priority}", method = RequestMethod.GET)
  public String findTaskByProjectIdAndPriority(@PathVariable("projectId") BigInteger projectId,
                                               @PathVariable("priority") TaskPriority priority, Model model) {

    logger.info("findTaskByProjectIdAndPriority method. projectId:" + projectId + "priority" + priority);
    Collection<Task> taskCollection = taskDAO.findTaskByProjectIdAndPriority(projectId, priority.getId());
    model.addAttribute("modelTask", taskCollection);

    return "task/show_task";
  }

}
