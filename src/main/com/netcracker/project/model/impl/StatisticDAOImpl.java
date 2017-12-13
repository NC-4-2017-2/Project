package main.com.netcracker.project.model.impl;

import main.com.netcracker.project.model.StatisticDAO;
import main.com.netcracker.project.model.UserDAO;
import main.com.netcracker.project.model.entity.SprintStatistic;
import main.com.netcracker.project.model.entity.Status;
import main.com.netcracker.project.model.impl.mappers.SprintStatisticMapper;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class StatisticDAOImpl implements StatisticDAO {

    private Logger logger = Logger.getLogger(UserDAOImpl.class);

    private JdbcTemplate template;

    public void setDataSource(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public List<SprintStatistic> findProjectSprintStatByProjectId(BigInteger projectId) {
        logger.info("Entering findProjectSprintStatByProjectId(" + projectId + ")");
        return template.query(FIND_PROJECT_SPRINT_STAT_BY_PROJECT_ID, new Object[]{projectId}, new SprintStatisticMapper());
    }

    @Override
    public String findProjectsTasksStatisticsByProjectIdByPeriod(BigInteger projectId, Date startDate, Date endDate) {
        return null;
    }

    @Override
    public String findUserTasksStatisticsByUserIdByPeriod(BigInteger userId, Date startDate, Date endDate) {
        return null;
    }

    @Override
    public String findUserWorkingHoursByUserIdByPeriod(BigInteger userId, Date startDate, Date endDate) {
        return null;
    }

    @Override
    public String findWorkPeriodByProjectIdAndStatus(BigInteger projectId, UserDAO.UserStatus status) {
        return null;
    }

    @Override
    public String findVacationsByProjectIdAndStatusAndPeriod(BigInteger projectId, Status pmApprove, Status lmApprove, Date startDate, Date endDate) {
        return null;
    }
}
