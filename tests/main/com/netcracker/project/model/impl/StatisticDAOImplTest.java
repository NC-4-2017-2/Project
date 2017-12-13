package main.com.netcracker.project.model.impl;

import main.com.netcracker.project.model.StatisticDAO;
import main.com.netcracker.project.model.entity.SprintStatistic;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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

    private StatisticDAO statisticDAO;
    private ApplicationContext context;
    private JdbcTemplate template;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Before
    public void setUp() {
        Locale.setDefault(Locale.ENGLISH);
        context = new ClassPathXmlApplicationContext("Spring-Module.xml");
        statisticDAO = (StatisticDAO) context.getBean("statisticDAO");
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

}
