package com.netcracker.project.controllers;

import com.netcracker.project.controllers.task.TaskData;
import com.netcracker.project.controllers.task.TaskForm;
import com.netcracker.project.model.TaskDAO;
import com.netcracker.project.model.TaskDAO.TaskPriority;
import com.netcracker.project.model.TaskDAO.TaskStatus;
import com.netcracker.project.model.TaskDAO.TaskType;
import com.netcracker.project.model.entity.Task;
import com.netcracker.project.model.impl.mappers.MapperDateConverter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/task")
public class TaskController {
  private ApplicationContext context =
      new ClassPathXmlApplicationContext("Spring-Module.xml");

  private TaskDAO taskDAO = (TaskDAO) context.getBean("taskDAO");
  private Logger logger = Logger.getLogger(TaskController.class);

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public String createTask(
      @RequestParam("taskId") Integer id,
      @RequestParam("name") String name,
      @RequestParam("taskType") TaskType type,
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
      @RequestParam("projectId") Integer projectId
      ) {
    MapperDateConverter mdc = new MapperDateConverter();
    logger.info("begin work with process creation:");
    Task createTask = new Task.TaskBuilder()
        .taskId(BigInteger.valueOf(id))
        .name(name)
        .taskType(type)
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
        .projectId(BigInteger.valueOf(projectId))
        .build();

    logger.info("createTask request from DB. Task id: " + id);
    taskDAO.createTask(createTask);
    return "response_for_task/success";
  }


  @RequestMapping(value = "/create", method = RequestMethod.GET)
  public String createTaskWithGetParams(){
    logger.info("createTask with get params.: ");
    return "task/create";
  }

  @RequestMapping(value = "/edit={id}", method = RequestMethod.POST)
  public String editTask(@RequestParam("updatingTask") Task updatingTask, Model model){

    logger.info("editTask method. Task id" + updatingTask);
    taskDAO.updateTask(updatingTask);
    return "task/show_task";
  }

  @RequestMapping(value = "/edit={id}", method = RequestMethod.GET)
  public String editTaskWithGetParams(@PathVariable("id") Integer id, Model model) {
    logger.info("editTaskWithGetParams method. Task id" + id);
    MapperDateConverter mdc = new MapperDateConverter();
    List<Task> task = (List<Task>) taskDAO.findTaskByProjectId(BigInteger.valueOf(id));

    TaskForm taskForm = new TaskForm();
    List<TaskData> tasks = createEmptyList(300);
    taskForm.setTasks(tasks);

    model.addAttribute("tasks", tasks);

    return "task/edit";

  }

  @RequestMapping(value = "/view={id}", method = RequestMethod.GET)
  public String findTask(@PathVariable("id") Integer id, Model model) {

      logger.info("findTask method. Task id" + id);


      MapperDateConverter mdc = new MapperDateConverter();
      List<Task> task = (List<Task>) taskDAO.findTaskByProjectId(BigInteger.valueOf(id));

      TaskForm taskForm = new TaskForm();
      List<TaskData> tasks = createEmptyList(300);
      taskForm.setTasks(tasks);

      model.addAttribute("id", id);
      model.addAttribute("tasks", tasks);

      return "task/show_task";
  }

  private List createEmptyList(int i) {
    List<TaskData> list = new ArrayList<>();
    for (int j = 0; j < i; j++) {
      list.add(null);
    }
    return list;
  }

}
