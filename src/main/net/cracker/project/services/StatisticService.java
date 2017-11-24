package main.net.cracker.project.services;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;
import main.net.cracker.project.model.entity.Sprint;
import main.net.cracker.project.model.entity.Task;
import main.net.cracker.project.model.entity.User;


public interface StatisticService {

  String generatePiechart(Map<String, Double> param);

  String generateCurve(Collection<Sprint> sprint);

  Map<String, Double> calculatePerfomance(Collection<User> users, Collection<Task> tasks);

  Double getAllHours(BigInteger userId);

}
