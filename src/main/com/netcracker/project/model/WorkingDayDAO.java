package main.com.netcracker.project.model;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import main.com.netcracker.project.model.entity.WorkingDay;

public interface WorkingDayDAO {

  void addHoursPerDay(WorkingDay workingDay);

  Collection<WorkingDay> findHoursPerPeriod(BigInteger userId, Date startDate, Date endDate);
}
