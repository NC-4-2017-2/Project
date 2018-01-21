package com.netcracker.project.model.impl.mappers;

import com.netcracker.project.model.entity.Task;
import com.netcracker.project.model.enums.TaskPriority;
import com.netcracker.project.model.enums.TaskStatus;
import com.netcracker.project.model.enums.TaskType;
import org.springframework.jdbc.core.RowMapper;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskMapper implements RowMapper<Task> {

  @Override
  public Task mapRow(ResultSet resultSet, int numRow) throws SQLException {
    return new Task.TaskBuilder()
        .taskId(new BigInteger(resultSet.getString(EnumMapper.TASK_ID.getFullName())))
        .name(resultSet.getString(EnumMapper.NAME.getFullName()))
        .taskType(TaskType
            .valueOf(resultSet.getString(EnumMapper.TASK_TYPE.getFullName())))
        .startDate(resultSet.getDate(EnumMapper.START_DATE.getFullName()))
        .plannedEndDate(resultSet.getDate(EnumMapper.PLANNED_END_DATE.getFullName()))
        .endDate(resultSet.getDate(EnumMapper.END_DATE.getFullName()))
        .priority(TaskPriority.valueOf(
            resultSet.getString(EnumMapper.TASK_PRIORITY.getFullName())))
        .status(TaskStatus
            .valueOf(resultSet.getString(EnumMapper.STATUS.getFullName())))
        .description(
            resultSet.getString(EnumMapper.TASK_DESCRIPTION.getFullName()))
        .reopenCounter(resultSet.getInt(EnumMapper.TASK_REOPEN_COUNTER.getFullName()))
        .comments(resultSet.getString(EnumMapper.TASK_COMMENT.getFullName()))
        .authorId(new BigInteger(resultSet.getString(EnumMapper.AUTHOR_ID.getFullName())))
        .userId(new BigInteger(resultSet.getString(EnumMapper.USER_ID.getFullName())))
        .projectId(new BigInteger(resultSet.getString(EnumMapper.PROJECT_ID.getFullName())))
        .build();
  }

}

