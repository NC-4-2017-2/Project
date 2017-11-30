package main.com.netcracker.project.model.impl.mappers;

import main.com.netcracker.project.model.TaskDAO;
import main.com.netcracker.project.model.entity.Task;
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

    String dateStart = resultSet.getString("START_DATE");
    String datePlannedEnd = resultSet.getString("PLANNED_END_DATE");
    String dateEnd = resultSet.getString("END_DATE");

    startDate = converter.convertStringToDate(dateStart);
    plannedEndDate = converter.convertStringToDate(datePlannedEnd);
    endDate = converter.convertStringToDate(dateEnd);

    return new Task.TaskBuilder()
        .taskId(new BigInteger(resultSet.getString("TASK_ID")))
        .name(resultSet.getString("TASK_NAME"))
        .taskType(TaskDAO.TaskType.valueOf(resultSet.getString("TASK_TYPE_VALUE")))
        .startDate(startDate)
        .plannedEndDate(plannedEndDate)
        .endDate(endDate)
        .priority(TaskDAO.TaskPriority.valueOf(resultSet.getString("TASK_PRIORITY_VALUE")))
        .status(TaskDAO.TaskStatus.valueOf(resultSet.getString("TASK_STATUS_VALUE")))
        .description(resultSet.getString("TASK_DESCRIPTION"))
        .comments(resultSet.getString("TASK_COMMENT"))
        .build();
  }

}

