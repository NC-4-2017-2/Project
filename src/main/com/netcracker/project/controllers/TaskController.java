package main.com.netcracker.project.controllers;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import main.com.netcracker.project.model.TaskDAO;
import main.com.netcracker.project.model.TaskDAO.TaskPriority;
import main.com.netcracker.project.model.TaskDAO.TaskStatus;
import main.com.netcracker.project.model.TaskDAO.TaskType;
import main.com.netcracker.project.model.entity.Task;
import main.com.netcracker.project.model.impl.mappers.MapperDateConverter;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/task")
public class TaskController {
  private ApplicationContext context =
      new ClassPathXmlApplicationContext("Spring-Module.xml");

  private TaskDAO taskDAO = (TaskDAO) context.getBean("taskDAO");
  private Logger logger = Logger.getLogger(TaskController.class);

  @Secured(value = {"ROLE_ADMIN"})
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public String createTask(
      @RequestParam("taskId") Integer id,
      @RequestParam("name") String name,
      @RequestParam("taskType") TaskType taskType,
      @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate,
      @RequestParam("plannedEndDate") String plannedEndDate,
      @RequestParam("priority") TaskPriority priority,
      @RequestParam("status")TaskStatus status,
      @RequestParam("description") String description,
      @RequestParam("reopenCounter") Integer reopenCounter,
      @RequestParam("comments") String comments,
      @RequestParam("authorId") Integer authorId,
      @RequestParam("userId") Integer userId,
      @RequestParam("projectId") Integer projectId) {

    MapperDateConverter mdc = new MapperDateConverter();
    Task createTask = new Task.TaskBuilder()
        .taskId(BigInteger.valueOf(id))
        .name(name)
        .taskType(taskType)
        .startDate(mdc.convertStringToDate(startDate))
        .endDate(mdc.convertStringToDate(endDate))
        .plannedEndDate(mdc.convertStringToDate(plannedEndDate))
        .priority(priority)
        .status(status)
        .description(description)
        .reopenCounter(reopenCounter)
        .comments(comments)
        .authorId(BigInteger.valueOf(authorId))
        .userId(BigInteger.valueOf(userId))
        .projectId(BigInteger.valueOf(projectId)).build();

    createTask.setTaskType(taskType);
    createTask.setPriority(priority);
    createTask.setStatus(status);
    taskDAO.createTask(createTask);

    return "/create";
  }

  @RequestMapping(value = "/create", method = RequestMethod.GET)
  public String createTaskWithGetParams(){
    return "/create";
  }

  @Secured(value = {"ROLE_ADMIN"})
  @RequestMapping(value = "/edit", method = RequestMethod.POST)
  public String editTask(
      @RequestParam("taskId") Integer id,
      @RequestParam("name") String name,
      @RequestParam("taskType") TaskType taskType,
      @RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate,
      @RequestParam("plannedEndDate") String plannedEndDate,
      @RequestParam("priority") TaskPriority priority,
      @RequestParam("status")TaskStatus status,
      @RequestParam("description") String description,
      @RequestParam("reopenCounter") Integer reopenCounter,
      @RequestParam("comments") String comments,
      @RequestParam("authorId") Integer authorId,
      @RequestParam("userId") Integer userId,
      @RequestParam("projectId") Integer projectId){

    MapperDateConverter mdc = new MapperDateConverter();
    Task updateTask = new Task.TaskBuilder()
        .taskId(BigInteger.valueOf(id))
        .name(name)
        .taskType(taskType)
        .startDate(mdc.convertStringToDate(startDate))
        .endDate(mdc.convertStringToDate(endDate))
        .plannedEndDate(mdc.convertStringToDate(plannedEndDate))
        .priority(priority)
        .status(status)
        .description(description)
        .reopenCounter(reopenCounter)
        .comments(comments)
        .authorId(BigInteger.valueOf(authorId))
        .userId(BigInteger.valueOf(userId))
        .projectId(BigInteger.valueOf(projectId)).build();

    updateTask.setTaskType(taskType);
    updateTask.setPriority(priority);
    updateTask.setStatus(status);
    taskDAO.updateTask(updateTask);

    return "/edit";
  }

  @RequestMapping(value = "/edit{taskStatus}", method = RequestMethod.GET)
  public String editTaskWithGetParams(){
    return "/edit";
  }

  @Secured("ROLE_REGULAR_USER")
  @RequestMapping(value = "/delete={taskStatus}", method = RequestMethod.GET)
  public String updateTaskStatus(@PathVariable("taskStatus") Integer taskStatus,
                               @PathVariable("taskId") BigInteger taskId, Model model){
    taskDAO.updateStatus(taskStatus, taskId);
    model.addAttribute("status", taskStatus);
    return "/edit{taskStatus}";
  }

  @Secured("ROLE_REGULAR_USER")
  @RequestMapping(value = "/view{projectId}", method = RequestMethod.GET)
  @ResponseBody
  public String findTask(@PathVariable("projectId") Integer projectId, Model model)
      throws InvocationTargetException{
      Task task = null;
      MapperDateConverter mdc = new MapperDateConverter();
      task = (Task) taskDAO.findTaskByProjectId(BigInteger.valueOf(projectId));
      model.addAttribute("taskId", task.getTaskId());
      model.addAttribute("name", task.getName());
      model.addAttribute("taskType", task.getTaskType());
      model.addAttribute("startDate", task.getStartDate());
      model.addAttribute("endDate", task.getEndDate());
      model.addAttribute("plannedEndDate", task.getPlannedEndDate());
      model.addAttribute("priority", task.getPriority());
      model.addAttribute("status", task.getStatus());
      model.addAttribute("description", task.getDescription());
      model.addAttribute("reopenCounter", task.getReopenCounter());
      model.addAttribute("comments", task.getComments());
      model.addAttribute("authorId", task.getAuthorId());
      model.addAttribute("userId", task.getUserId());
      model.addAttribute("projectId", task.getProjectId());

      return "task/view";
  }
}
