package main.com.netcracker.project.services;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;
import main.com.netcracker.project.model.entity.Sprint;
import main.com.netcracker.project.model.entity.Task;
import main.com.netcracker.project.model.entity.User;


public interface StatisticService {

  String generatePiechart(Map<String, Double> param);

  String generateCurve(Collection<Sprint> sprint);

  Map<String, Double> calculatePerformance(Collection<User> users, Collection<Task> tasks);

  Double getAllHours(BigInteger userId);

}
