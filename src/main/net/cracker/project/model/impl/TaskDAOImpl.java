package main.net.cracker.project.model.impl;

import main.net.cracker.project.model.TaskDAO;
import main.net.cracker.project.model.entity.Task;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

public class TaskDAOImpl implements TaskDAO {
    @Override
    public void createTask(Task task) {

    }

    @Override
    public void updateTask(BigInteger id) {

    }

    @Override
    public Collection<Task> findTaskByProjectld(BigInteger id) {
        return null;
    }

    @Override
    public Collection<Task> findTaskByUserld(BigInteger id) {
        return null;
    }

    @Override
    public Collection<Task> findTaskByDate(Date date, BigInteger id) {
        return null;
    }

    @Override
    public Collection<Task> findTaskByPriority(TaskPriority taskPriority, BigInteger employeeID) {
        return null;
    }

    @Override
    public Task findTaskByStatusAndUserId(String status, BigInteger id) {
        return null;
    }

    @Override
    public String updateStatus(TaskStatus status) {
        return null;
    }
}
