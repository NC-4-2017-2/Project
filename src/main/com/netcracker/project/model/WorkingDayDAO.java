package main.com.netcracker.project.model;

import java.math.BigInteger;
import java.util.Date;
import java.util.Map;
import main.com.netcracker.project.model.entity.WorkingDay;

public interface WorkingDayDAO {

  void addHoursPerDay(WorkingDay workingDay);

  Map<String, BigInteger> findHoursPerWeek(Integer numberOfWeek,
      BigInteger userId);

  Integer findHoursPerPeriod(Integer userId, Date startDate, Date endDate);


}
