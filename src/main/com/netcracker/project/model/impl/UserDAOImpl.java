package main.com.netcracker.project.model.impl;

import main.com.netcracker.project.model.entity.User;
import main.com.netcracker.project.model.UserDAO;
import main.com.netcracker.project.model.impl.mappers.MapperDateConverter;
import main.com.netcracker.project.model.impl.mappers.UserMapper;
import main.com.netcracker.project.model.impl.mappers.WorkPeriodMapper;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.File;
import java.math.BigInteger;
import java.util.Collection;

public class UserDAOImpl implements UserDAO {

    private Logger logger = Logger.getLogger(UserDAOImpl.class);
    private MapperDateConverter converter = new MapperDateConverter();

    private JdbcTemplate template;

    public void setDataSource(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public User createUser(User user) {
        logger.info("Entering insert(user=" + user + ")");
        this.template.update(CREATE_USER, new Object[]{
                user.getLastName() + " " + user.getFirstName(),
                user.getLastName(),
                user.getFirstName(),
                user.getEmail(),
                converter.convertDateToString(user.getDateOfBirth()),
                converter.convertDateToString(user.getHireDate()),
                user.getPhoneNumber(),
                user.getJobTitle().getId(),
                user.getProjectStatus().getId(),
                user.getLogin(),
                user.getPassword(),
                user.getUserRole().getId(),
                user.getUserStatus().getId()
        });

        return user;
    }

    @Override
    public User findUserByUserId(BigInteger id) {
        logger.info("Entering findUserByUserId(" + id + ")");
        return template.queryForObject(FIND_USER_BY_USER_ID, new Object[]{id}, new UserMapper());
    }

    @Override
    public User findUserByLogin(String login) {
        logger.info("Entering findUserByLogin(" + login + ")");
        return template.queryForObject(FIND_USER_BY_LOGIN, new Object[]{login}, new UserMapper());
    }

    @Override
    public void updatePhoneNumber(BigInteger id, String phoneNumber) {
        logger.info("Entering updatePhoneNumber(id=" + id + "," + " phoneNumber=" + phoneNumber + ")");
        template.update(UPDATE_PHONE_NUMBER, phoneNumber, id);
    }

    @Override
    public void updateEmail(BigInteger id, String email) {
        logger.info("Entering updateEmail(id=" + id + "," + " email=" + email + ")");
        template.update(UPDATE_EMAIL, email, id);
    }

    @Override
    public void updatePassword(BigInteger id, String password) {
        logger.info("Entering updatePassword(id=" + id + "," + " password=" + password + ")");
        template.update(UPDATE_PASSWORD, password, id);
    }

    @Override
    public void updatePhoto(BigInteger id, File photo) {

    }

    @Override
    public void updateProjectStatus(BigInteger id, Integer status) {
        logger.info("Entering updateProjectStatus(id=" + id + "," + " status=" + status + ")");
        template.update(UPDATE_USER_PROJECT_STATUS, status, id);
    }

    @Override
    public Collection<WorkPeriod> findWorkPeriodsByUserId(BigInteger id) {
        logger.info("Entering findWorkPeriodsByUserId(id=" + id + ")");
        return template.query(FIND_WORK_PERIOD_BY_USER_ID, new Object[]{id}, new WorkPeriodMapper());
    }

    @Override
    public Collection<WorkPeriod> findWorkPeriodsByProjectId(BigInteger id) {
        logger.info("Entering findWorkPeriodsByProjectId(id=" + id + ")");
        return template.query(FIND_WORK_PERIOD_BY_PROJECT_ID, new Object[]{id}, new WorkPeriodMapper());
    }

    @Override
    public Collection<WorkPeriod> findWorkPeriodByUserIdAndProjectId(BigInteger userId, BigInteger projectId) {
        logger.info("Entering findWorkPeriodByUserIdAndProjectId(userId=" + userId + "," + " projectId=" + projectId + ")");
        return template.query(FIND_WORK_PERIOD_BY_USER_ID_AND_PROJECT_ID, new Object[]{userId, projectId}, new WorkPeriodMapper());
    }

    @Override
    public void updateWorkingPeriodByUserId(UserDAO.WorkPeriod workPeriod) {
        logger.info("Entering updateWorkingPeriodByUserId(userId=" + workPeriod.getUserId() + "," + " projectId=" + workPeriod.getProjectId() + "," + " UserDAO.WorkPeriod=" + workPeriod + ")");
        template.update(UPDATE_WORKING_PERIOD_END_DATE, converter.convertDateToString(workPeriod.getEndWorkDate()), workPeriod.getUserId(), workPeriod.getProjectId());
    }

}
