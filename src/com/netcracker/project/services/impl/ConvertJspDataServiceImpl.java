package com.netcracker.project.services.impl;

import com.netcracker.project.controllers.project_form.SprintFormData;
import com.netcracker.project.controllers.project_form.WorkPeriodFormData;
import com.netcracker.project.controllers.task.TaskData;
import com.netcracker.project.model.entity.Sprint;
import com.netcracker.project.model.entity.Sprint.SprintBuilder;
import com.netcracker.project.model.entity.Task;
import com.netcracker.project.model.entity.WorkPeriod;
import com.netcracker.project.model.impl.mappers.MapperDateConverter;
import com.netcracker.project.services.ConvertJspDataService;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConvertJspDataServiceImpl implements ConvertJspDataService {

  MapperDateConverter converter = new MapperDateConverter();

  @Override
  public Collection<Sprint> createSprintFromJsp(List<SprintFormData> sprints,
      BigInteger projectId) {

    Collection<Sprint> resultSprint = new ArrayList<>();

    sprints.forEach(s -> {
      Sprint sprint = new SprintBuilder()
          .name(s.getName())
          .startDate(converter.convertStringToDateFromJSP(s.getStartDate()))
          .plannedEndDate(converter.convertStringToDateFromJSP(s.getPlannedEndDate()))
          .endDate(converter.convertStringToDateFromJSP(s.getPlannedEndDate()))
          .build();
      resultSprint.add(sprint);
    });

    return resultSprint;
  }

  @Override
  public Collection<WorkPeriod> createWorkPeriod(List<WorkPeriodFormData> workers,
      BigInteger projectId) {
    Collection<WorkPeriod> resultPeriod = new ArrayList<>();

    workers.forEach(wp -> {
      WorkPeriod workPeriod = new WorkPeriod.WorkPeriodBuilder()
          .workPeriodId(wp.getWorkPeriodId())
          .startWorkDate(converter.convertStringToDateFromJSP(wp.getStartWorkDate()))
          .endWorkDate(converter.convertStringToDateFromJSP(wp.getEndWorkDate()))
          .workPeriodStatus(wp.getWorkPeriodStatus())
          .projectId(wp.getProjectId())
          .userId(wp.getUserId())
          .build();
      resultPeriod.add(workPeriod);
    });

    return resultPeriod;
  }

  @Override
  public List<SprintFormData> convertSprintToSprintForm(Collection<Sprint> sprints) {
    List<SprintFormData> sprintForms = new ArrayList<>();

    sprints.forEach(sprint -> sprintForms.add(new SprintFormData(
        sprint.getSprintId(),
        sprint.getName(),
        sprint.getStatus(),
        sprint.getPlannedEndDate())));

    return sprintForms;
  }

  @Override
  public List<WorkPeriodFormData> convertWorkPeriodToWPForm(
      Collection<WorkPeriod> workPeriodCollection) {
    List<WorkPeriodFormData> workPeriods = new ArrayList<>();

    workPeriodCollection.forEach(work -> workPeriods
        .add(new WorkPeriodFormData(work.getWorkPeriodId(), work.getUserId(),
            converter.convertDateToString(work.getStartWorkDate()),
            converter.convertDateToString(work.getEndWorkDate()),
            work.getWorkPeriodStatus())));

    return workPeriods;
  }

  @Override
  public List<TaskData> convertTaskToTaskForm(Collection<Task> taskCollection) {
    List<TaskData> taskData = new ArrayList<>();

    taskCollection.forEach(t -> taskData.add(new TaskData(t.getTaskId(), t.getName(),
        t.getTaskType(),
        converter.convertDateToString(t.getStartDate()),
        converter.convertDateToString(t.getEndDate()),
        converter.convertDateToString(t.getPlannedEndDate()),
        t.getPriority(), t.getStatus(),
        t.getDescription(), t.getReopenCounter(),
        t.getComments(), t.getAuthorId(),
        t.getUserId(), t.getProjectId())));
    return taskData;
  }

}
