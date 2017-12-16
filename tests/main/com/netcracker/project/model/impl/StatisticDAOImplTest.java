package main.com.netcracker.project.model.impl;

import main.com.netcracker.project.model.StatisticDAO;
import main.com.netcracker.project.model.entity.SprintStatistic;
import main.com.netcracker.project.model.entity.UserTaskStatistic;
import main.com.netcracker.project.model.entity.VacationStatistic;
import main.com.netcracker.project.model.entity.WorkPeriodStatistic;
import main.com.netcracker.project.model.entity.WorkingHoursStatistic;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:Spring-Module.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StatisticDAOImplTest {

    @Autowired
    private StatisticDAO statisticDAO;
    private JdbcTemplate template;
    @Autowired
    private DataSource dataSource;

    @Before
    public void setUp() {
        Locale.setDefault(Locale.ENGLISH);
        template = new JdbcTemplate(dataSource);
    }

    @Test
    public void findProjectSprintStatByProjectId() {
        List<SprintStatistic> sprintStatisticList = statisticDAO.findProjectSprintStatByProjectId(BigInteger.valueOf(4));
        assertEquals("[SprintStatistic{sprintName='SPRINT1', plannedTakeDays=12, takeDays=11}," +
                " SprintStatistic{sprintName='Sprint3', plannedTakeDays=29, takeDays=31}," +
                " SprintStatistic{sprintName='Sprint4', plannedTakeDays=39, takeDays=31}," +
                " SprintStatistic{sprintName='Sprint5', plannedTakeDays=26, takeDays=31}," +
                " SprintStatistic{sprintName='Sprint6', plannedTakeDays=36, takeDays=28}]", sprintStatisticList.toString());
    }

    @Test
    public void findProjectTaskStatisticCountByProjectIdAndPeriod() {
        UserTaskStatistic result = statisticDAO.findProjectTaskStatisticCountByProjectIdAndPeriod(BigInteger.valueOf(4), "11.11.11", "16.12.12");
        assertEquals("UserTaskStatistic{critical=1, high=2, normal=0, low=0}", result.toString());
    }

    @Test
    public void findUserTaskCountByUserIdAndPeriod() {
        UserTaskStatistic result = statisticDAO.findUserTaskCountByUserIdAndPeriod(BigInteger.valueOf(2), "11.11.11", "16.12.12");
        assertEquals("UserTaskStatistic{critical=2, high=1, normal=0, low=0}", result.toString());
    }

    @Test
    public void findUserWorkingHoursByUserIdAndPeriod() {
        List<WorkingHoursStatistic> result = statisticDAO.findUserWorkingHoursByUserIdAndPeriod(BigInteger.valueOf(2), "12.12.12", "15.12.12");
        assertEquals("[WorkingHoursStatistic{userId=2, workingDayDate='14.12.12', hoursCount=8}]", result.toString());
    }

    @Test
    public void findWorkPeriodByProjectIdAndStatus() {
        WorkPeriodStatistic workPeriodStatistic = statisticDAO.findWorkPeriodByProjectIdAndStatus(BigInteger.valueOf(4));
        assertEquals("WorkPeriodStatistic{allTimeWorkers=2, currentWorkers=0}", workPeriodStatistic.toString());

    }

    @Test
    public void findVacationsByProjectIdAndPeriod() {
        List<VacationStatistic> result = statisticDAO.findVacationsByProjectIdAndPeriod(BigInteger.valueOf(4), "16.12.12", "28.12.12");
        assertEquals("[VacationStatistic{userId=2, countDays=10}, VacationStatistic{userId=3, countDays=9}]", result.toString());

    }

}
