package main.net.cracker.project.model.impl;

import main.net.cracker.project.model.WorkingDayDAO;
import main.net.cracker.project.model.entity.User;

import java.math.BigInteger;
import java.util.Date;
import java.util.Map;

public class WorkingDayDAOImpl implements WorkingDayDAO {
    @Override
    public Boolean addHoursPerDay(Double hours, User user, BigInteger taskId) {
        return null;
    }

    @Override
    public Map<String, BigInteger> findHoursPerWeek(Integer numberOfWeek, BigInteger userId) {
        return null;
    }

    @Override
    public Integer findHoursPerPeriod(Integer userId, Date startDate, Date endDate) {
        return null;
    }
}
