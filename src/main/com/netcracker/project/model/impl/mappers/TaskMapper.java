package main.com.netcracker.project.model.impl.mappers;

import java.util.ArrayList;
import main.com.netcracker.project.model.TaskDAO;
import main.com.netcracker.project.model.entity.Task;
import main.com.netcracker.project.model.entity.User;
import org.springframework.jdbc.core.RowMapper;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;


public class TaskMapper implements RowMapper<Task> {

  private MapperDateConverter converter;

  @Override
  public Task mapRow(ResultSet resultSet, int numRow) throws SQLException {

    Date startDate = null;
    Date plannedEndDate = null;
    Date endDate = null;

    converter = new MapperDateConverter();

    String dateStart = resultSet.getString(EnumMapper.START_DATE.getFullName());
    String datePlannedEnd = resultSet
        .getString(EnumMapper.PLANNED_END_DATE.getFullName());
    String dateEnd = resultSet.getString(EnumMapper.END_DATE.getFullName());

    startDate = converter.convertStringToDate(dateStart);
    plannedEndDate = converter.convertStringToDate(datePlannedEnd);
    endDate = converter.convertStringToDate(dateEnd);

    return new Task.TaskBuilder()
        .taskId(new BigInteger(
            resultSet.getString(EnumMapper.TASK_ID.getFullName())))
        .name(resultSet.getString(EnumMapper.NAME.getFullName()))
        .taskType(TaskDAO.TaskType
            .valueOf(resultSet.getString(EnumMapper.TASK_TYPE.getFullName())))
        .startDate(startDate)
        .plannedEndDate(plannedEndDate)
        .endDate(endDate)
        .priority(TaskDAO.TaskPriority.valueOf(
            resultSet.getString(EnumMapper.TASK_PRIORITY.getFullName())))
        .status(TaskDAO.TaskStatus
            .valueOf(resultSet.getString(EnumMapper.STATUS.getFullName())))
        .description(
            resultSet.getString(EnumMapper.TASK_DESCRIPTION.getFullName()))
        .comments(resultSet.getString(EnumMapper.TASK_COMMENT.getFullName()))
        .build();
  }

}

