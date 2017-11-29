package main.com.netcracker.project.model;

import java.math.BigInteger;
import java.util.Collection;
import main.com.netcracker.project.model.entity.BusinessTrip;

public interface BusinessTripDAO {

  void createTrip(BusinessTrip trip);

  void updateTrip(BusinessTrip trip);

  Collection<BusinessTrip> findTripByUserId(BigInteger id);

  Collection<BusinessTrip> findTripByProjectId(BigInteger id);
}
