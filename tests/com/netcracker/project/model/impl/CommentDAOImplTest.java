package com.netcracker.project.model.impl;

import com.netcracker.project.model.CommentDAO;
import com.netcracker.project.model.entity.Comment;
import com.netcracker.project.services.impl.DateConverterService;
import java.math.BigInteger;
import java.util.Date;
import java.util.Locale;
import javax.sql.DataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:Spring-Module.xml"})
public class CommentDAOImplTest {

  @Autowired
  private CommentDAO commentDAO;
  private JdbcTemplate template;
  @Autowired
  private DataSource dataSource;
  private Comment testComment;

  @Before
  public void setUp() {
    Locale.setDefault(Locale.ENGLISH);
    template = new JdbcTemplate(dataSource);
  }

  @Test
  public void test1createComment(){
    DateConverterService mdc = new DateConverterService();
    Date date = new Date();
    testComment = new Comment.CommentBuilder()
        .bodyComment("We are fixing bug for comment")
        .creationDate(date)
        .userId(BigInteger.valueOf(1))
        .taskId(BigInteger.valueOf(7))
        .build();

    commentDAO.createComment(testComment);
  }

  @Test
  public void test2updateBodyComment(){
    commentDAO.updateCommentBody("Updated comment3", BigInteger.valueOf(701));
  }

  @Test
  public void test3deleteComment(){
    commentDAO.deleteCommentFromTask(BigInteger.valueOf(1089), BigInteger.valueOf(7));
  }

  @Test
  public void getAllCommentsForTask(){
    commentDAO.getCommentsForTask(BigInteger.valueOf(300));
  }
}
