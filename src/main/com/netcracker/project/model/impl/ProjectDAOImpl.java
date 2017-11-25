package main.com.netcracker.project.model.impl;

import main.com.netcracker.project.model.ProjectDAO;
import main.com.netcracker.project.model.entity.Project;

import java.math.BigInteger;
import java.util.Date;

public class ProjectDAOImpl implements ProjectDAO {
    @Override
    public void createProject(Project project) {

    }

    @Override
    public Project findProjectByProjectId(BigInteger id) {
        return null;
    }

    @Override
    public Project findProjectByName(String name) {
        return null;
    }

    @Override
    public Project findProjectByDate(Date startDate) {
        return null;
    }

    @Override
    public void deleteUserByUserId(BigInteger userId, BigInteger projectID) {

    }

    @Override
    public void updateProject(BigInteger id, Project project) {

    }

    @Override
    public void addUser(BigInteger userId, BigInteger projectId) {

    }
}
