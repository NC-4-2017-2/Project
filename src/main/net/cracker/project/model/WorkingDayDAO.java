package main.net.cracker.project.model;

import java.math.BigInteger;
import java.util.Date;
import java.util.Map;
import main.net.cracker.project.model.entity.User;

public interface WorkingDayDAO {

  Boolean addHoursPerDay(Double hours, User user, BigInteger taskId);

  Map<String, BigInteger> findHoursPerWeek(Integer numberOfWeek,
      BigInteger userId);

  Integer findHoursPerPeriod(Integer userId, Date startDate, Date endDate);


}
