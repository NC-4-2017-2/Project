package main.com.netcracker.project.model.impl.mappers;

import main.com.netcracker.project.model.TaskDAO;
import main.com.netcracker.project.model.entity.Task;
import org.springframework.jdbc.core.RowMapper;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TaskMapper implements RowMapper<Task> {

  @Override
  public Task mapRow(ResultSet resultSet, int numRow) throws SQLException {

    Date startDate = null, plannedEndDate = null, endDate = null;

    String dateStart = resultSet.getString("START_DATE");
    String datePlannedEnd = resultSet.getString("PLANNED_END_DATE");
    String dateEnd = resultSet.getString("END_DATE");

    startDate = convertStringToDate(dateStart);
    plannedEndDate = convertStringToDate(datePlannedEnd);
    endDate = convertStringToDate(dateEnd);

    return new Task.TaskBuilder()
        .taskId(new BigInteger(resultSet.getString("TASK_ID")))
        .name(resultSet.getString("NAME"))
        .taskType(TaskDAO.TaskType.valueOf(resultSet.getString("TASK_TYPE")))
        .startDate(startDate)
        .plannedEndDate(plannedEndDate)
        .endDate(endDate)
        .priority(TaskDAO.TaskPriority.valueOf(resultSet.getString("TASK_PRIORITY")))
        .status(TaskDAO.TaskStatus.valueOf(resultSet.getString("TASK_STATUS")))
        .description(resultSet.getString("DESCRIPTION"))
        .authorId(new BigInteger(resultSet.getString("AUTHOR_ID")))
        .comments(resultSet.getString("COMMENTS"))
        .reopenCounter(new Integer(resultSet.getString("REOPEN_COUNTER")))
        .projectId(new BigInteger(resultSet.getString("PROJECT_ID")))
        .build();
  }

  private Date convertStringToDate(String string) {
    Date date = null;
    DateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy");

    try {
      date = dateFormat.parse(string);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }
}

