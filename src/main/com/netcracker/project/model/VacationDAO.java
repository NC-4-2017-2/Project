package main.com.netcracker.project.model;

import java.math.BigInteger;
import java.util.Collection;

import main.com.netcracker.project.model.entity.Vacation;

public interface VacationDAO {

  void createVacation(Vacation vacation);

  void updateVacation(BigInteger id, Vacation vacation);

  Vacation findVacationByUserId(BigInteger id);

  Vacation findVacationByProjectId(BigInteger id);

  Collection<Vacation> findVacationByUserIdAndPmStatus(BigInteger id,
      Integer status);

  Collection<Vacation> findVacationByUserIdAndLmStatus(BigInteger id,
      Integer status);


}
