package main.net.cracker.project.model;

import java.math.BigInteger;
import java.util.Collection;
import main.net.cracker.project.model.entity.BusinessTrip;

public interface BusinessTripDAO {

  void createTrip(BusinessTrip trip);

  void updateTrip(BigInteger id, BusinessTrip trip);

  Collection<BusinessTrip> findTripByUserld(BigInteger id);

  BusinessTrip findTripByProjectId(BigInteger id);
}
