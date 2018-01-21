package com.netcracker.project.model.impl.mappers;


import com.netcracker.project.model.entity.Comment;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CommentMapper implements RowMapper<Comment> {

  @Override
  public Comment mapRow(ResultSet resultSet, int numRow) throws SQLException {
    return new Comment.CommentBuilder()
        .commentId(new BigInteger(resultSet.getString(EnumMapper.COMMENT_ID.getFullName())))
        .bodyComment(resultSet.getString(EnumMapper.COMMENT_BODY.getFullName()))
        .creationDate(resultSet.getDate(EnumMapper.COMMENT_DATE.getFullName()))
        .userId(new BigInteger(
            resultSet.getString(EnumMapper.USER_ID.getFullName())))
        .taskId(new BigInteger(
            resultSet.getString(EnumMapper.TASK_ID.getFullName())))
        .lastName(resultSet.getString(EnumMapper.LAST_NAME.getFullName()))
        .firstName(resultSet.getString(EnumMapper.FIRST_NAME.getFullName()))
        .build();
  }
}
