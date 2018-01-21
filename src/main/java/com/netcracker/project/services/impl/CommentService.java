package com.netcracker.project.services.impl;

import com.netcracker.project.model.TaskDAO;
import com.netcracker.project.model.UserDAO;
import com.netcracker.project.model.entity.Comment;
import com.netcracker.project.model.entity.Task;
import com.netcracker.project.model.entity.User;
import java.math.BigInteger;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentService {

  @Autowired
  private UserDAO userDAO;

  @Autowired
  private TaskDAO taskDAO;

  private DateConverterService converter = new DateConverterService();

  public Comment addComment(BigInteger commentId, BigInteger userId, String bodyComment,
                            String creationDate, BigInteger taskId, Principal principal){
    User user = userDAO.findUserByLogin(principal.getName());
    Task task = taskDAO.findTaskByTaskId(taskId);
    return new Comment.CommentBuilder()
        .commentId(commentId)
        .userId(user.getUserId())
        .bodyComment(bodyComment)
        .creationDate(converter.convertStringToDateFromJSP(creationDate))
        .taskId(task.getTaskId())
        .build();
  }


}
