package main.com.netcracker.project.model.impl;

import main.com.netcracker.project.model.entity.User;
import main.com.netcracker.project.model.UserDAO;

import java.io.File;
import java.math.BigInteger;

public class UserDAOImpl implements UserDAO {
    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public void updateUser(BigInteger id, User user) {

    }

    @Override
    public User findUserByUserId(BigInteger id) {
        return null;
    }

    @Override
    public User findUserByLogin(String login) {
        return null;
    }

    @Override
    public void updatePhoneNumber(BigInteger id, String phoneNumber) {

    }

    @Override
    public void updateEmail(BigInteger id, String email) {

    }

    @Override
    public void updatePassword(BigInteger id, String password) {

    }

    @Override
    public void updatePhoto(BigInteger id, File photo) {

    }

    @Override
    public void updateWorkingPeriodByUserId(BigInteger userId, BigInteger projectId) {

    }
}
