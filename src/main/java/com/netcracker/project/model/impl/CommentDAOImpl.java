package com.netcracker.project.model.impl;


import com.netcracker.project.model.CommentDAO;
import com.netcracker.project.model.entity.Comment;
import com.netcracker.project.model.impl.mappers.CommentMapper;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@ImportResource("classpath:Spring-Comment.xml")
public class CommentDAOImpl implements CommentDAO {

  private static final Logger logger = Logger.getLogger(CommentDAOImpl.class);
  private JdbcTemplate template;

  @Autowired
  public void setDataSource(DataSource dataSource) {
    template = new JdbcTemplate(dataSource);
  }

  @Override
  @Transactional
  public void createComment(Comment comment) {
    logger.info("Entering createComment(comment=" + comment + ")");
    template.update(CREATE_COMMENT, new Object[]{
        comment.getBodyComment(),
        comment.getCreationDate(),
        comment.getUserId(),
        comment.getTaskId()
    });
  }

  @Override
  public void deleteCommentFromTask(BigInteger commentId, BigInteger taskId) {
    logger.info("Entering deleteComment(commentId=" + commentId + "taskId" + "taskId" + taskId + ")");
    template.update(DELETE_COMMENT_FROM_TASK, commentId, taskId);
  }

  @Override
  public void updateCommentBody(String commentBody, BigInteger commentId ) {
    logger.info("Entering updateCommentBody(commentId=" + commentId + "taskId" + "commentBody" + commentBody + ")");
    template.update(UPDATE_BODY_COMMENT, commentBody, commentId);
  }

  @Override
  public void updateCreationDate(Date creationDate, BigInteger commentId) {
    logger.info(
        "Entering updateStatus(date=" + creationDate + ")" + "Entering commentId="
            + commentId);
    template.update(UPDATE_CREATION_DATE, creationDate, commentId);
  }

  @Override
  public List<Comment> getCommentsForTask(BigInteger taskId) {
    logger.info("Entering getCommentsForTask(taskId=" + taskId + ")");
    return template.query(GET_ALL_COMMENTS_FOR_TASK, new Object[]{taskId},
        new CommentMapper());
  }

  @Override
  public Integer findIfCommentExists(BigInteger taskId) {
    logger.info("Entering findIfCommentExists(taskId=" + taskId + ")");
    return template.queryForObject(FIND_COMMENT_IF_EXIST, new Object[]{taskId},
            Integer.class);
  }

}
