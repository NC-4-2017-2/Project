package main.net.cracker.project.model.impl;

import main.net.cracker.project.model.BusinessTripDAO;
import main.net.cracker.project.model.entity.BusinessTrip;

import java.math.BigInteger;
import java.util.Collection;

public class BusinessTripDAOImpl implements BusinessTripDAO {
    @Override
    public void createTrip(BusinessTrip trip) {

    }

    @Override
    public void updateTrip(BigInteger id, BusinessTrip trip) {

    }

    @Override
    public Collection<BusinessTrip> findTripByUserld(BigInteger id) {
        return null;
    }

    @Override
    public BusinessTrip findTripByProjectId(BigInteger id) {
        return null;
    }
}
