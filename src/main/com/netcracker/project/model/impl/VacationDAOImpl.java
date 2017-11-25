package main.com.netcracker.project.model.impl;

import main.com.netcracker.project.model.VacationDAO;
import main.com.netcracker.project.model.entity.Vacation;

import java.math.BigInteger;
import java.util.Collection;

public class VacationDAOImpl implements VacationDAO {
    @Override
    public void createVacation(Vacation vacation) {

    }

    @Override
    public void updateVacation(BigInteger id, Vacation vacation) {

    }

    @Override
    public Vacation findVacationByUserId(BigInteger id) {
        return null;
    }

    @Override
    public Vacation findVacationByProjectId(BigInteger id) {
        return null;
    }

    @Override
    public Collection<Vacation> findVacationByUserIdAndPmStatus(BigInteger id, Boolean status) {
        return null;
    }

    @Override
    public Collection<Vacation> findVacationByUserIdAndLmStatus(BigInteger id, Boolean status) {
        return null;
    }
}
