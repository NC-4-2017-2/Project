package com.netcracker.project.model.entity;


import java.math.BigInteger;
import java.util.Date;

public class Comment {

  private BigInteger commentId;
  private String bodyComment;
  private Date creationDate;
  private BigInteger userId;
  private BigInteger taskId;

  public Comment(CommentBuilder commentBuilder) {
    this.commentId = commentBuilder.commentId;
    this.bodyComment = commentBuilder.bodyComment;
    this.creationDate = commentBuilder.creationDate;
    this.userId = commentBuilder.userId;
    this.taskId = commentBuilder.taskId;
  }

  public Comment() {

  }

  @Override
  public String toString() {
    return "Comment{" +
        "commentId=" + commentId +
        ", bodyComment='" + bodyComment  +
        ", creationDate=" + creationDate +
        ", userId=" + userId +
        ", taskId=" + taskId +
        '}';
  }

  public BigInteger getCommentId() {
    return commentId;
  }

  public BigInteger getUserId() {
    return userId;
  }

  public String getBodyComment() {
    return bodyComment;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public BigInteger getTaskId() {
    return taskId;
  }

  public static class CommentBuilder{
    private BigInteger commentId;
    private String bodyComment;
    private Date creationDate;
    private BigInteger userId;
    private BigInteger taskId;

    public CommentBuilder() {
    }

    public CommentBuilder commentId(BigInteger commentId) {
      this.commentId = commentId;
      return this;
    }


    public CommentBuilder bodyComment(String bodyComment) {
      this.bodyComment = bodyComment;
      return this;
    }

    public CommentBuilder creationDate(Date creationDate) {
      this.creationDate = creationDate;
      return this;
    }

    public CommentBuilder userId(BigInteger userId) {
      this.userId = userId;
      return this;
    }

    public CommentBuilder taskId(BigInteger taskId){
      this.taskId = taskId;
      return this;
    }

    public Comment build(){
      return new Comment(this);
    }

  }
}
