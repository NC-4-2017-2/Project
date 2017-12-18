package com.netcracker.project.services;

import com.netcracker.project.controllers.task.TaskData;
import com.netcracker.project.model.entity.Task;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import com.netcracker.project.controllers.project_form.SprintFormData;
import com.netcracker.project.controllers.project_form.WorkPeriodFormData;
import com.netcracker.project.model.UserDAO.WorkPeriod;
import com.netcracker.project.model.entity.Sprint;

public interface ConvertJspDataService {

  Collection<Sprint> createSprintFromJsp(List<SprintFormData> sprints, BigInteger projectId);

  Collection<WorkPeriod> createWorkPeriod(List<WorkPeriodFormData> workers, BigInteger projectId);

  List<SprintFormData> convertSprintToSprintForm(Collection<Sprint> sprints);

  List<WorkPeriodFormData> convertWorkPeriodToWPForm(Collection<WorkPeriod> workPeriodCollection);

  List<TaskData> convertTaskToTaskForm(Collection<Task> taskCollection);

}
