package main.net.cracker.project.model;

import java.math.BigInteger;
import java.util.Collection;
import main.net.cracker.project.model.entity.Status;
import main.net.cracker.project.model.entity.Vacation;

public interface VacationDAO {

  void createVacation(Vacation vacation);

  void updateVacation(BigInteger id, Vacation vacation);

  Vacation findVacationByUserId(BigInteger id);

  Vacation findVacationByProjectId(BigInteger id);

  Collection<Vacation> findVacationByUserIdAndPmStatus(BigInteger id,
      Boolean status);

  Collection<Vacation> findVacationByUserIdAndLmStatus(BigInteger id,
      Boolean status);


}
