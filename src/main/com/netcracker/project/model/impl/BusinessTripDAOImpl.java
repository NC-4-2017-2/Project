package main.com.netcracker.project.model.impl;

import main.com.netcracker.project.model.BusinessTripDAO;
import main.com.netcracker.project.model.entity.BusinessTrip;

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
    public Collection<BusinessTrip> findTripByUserId(BigInteger id) {
        return null;
    }

    @Override
    public BusinessTrip findTripByProjectId(BigInteger id) {
        return null;
    }
}
