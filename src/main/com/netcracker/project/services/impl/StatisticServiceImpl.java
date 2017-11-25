package main.com.netcracker.project.services.impl;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;

import main.com.netcracker.project.services.StatisticService;
import main.com.netcracker.project.model.entity.Sprint;
import main.com.netcracker.project.model.entity.Task;
import main.com.netcracker.project.model.entity.User;


public class StatisticServiceImpl implements StatisticService {


  public String generatePiechart(Map<String, Double> param) {
    return null;
  }


  public String generateCurve(Collection<Sprint> sprint) {
    return null;
  }


  public Map<String, Double> calculatePerformance(Collection<User> users,
      Collection<Task> tasks) {
    return null;
  }


  public Double getAllHours(BigInteger userId) {
    return null;
  }

}
