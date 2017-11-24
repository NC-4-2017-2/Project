package main.net.cracker.project.services.impl;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;
import main.net.cracker.project.model.entity.Sprint;
import main.net.cracker.project.model.entity.Task;
import main.net.cracker.project.model.entity.User;
import main.net.cracker.project.services.StatisticService;


public class StatisticServiceImpl implements StatisticService {


  public String generatePiechart(Map<String, Double> param) {
    return null;
  }


  public String generateCurve(Collection<Sprint> sprint) {
    return null;
  }


  public Map<String, Double> calculatePerfomance(Collection<User> users,
      Collection<Task> tasks) {
    return null;
  }


  public Double getAllHours(BigInteger userId) {
    return null;
  }

}
