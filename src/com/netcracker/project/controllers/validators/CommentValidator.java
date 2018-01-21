package com.netcracker.project.controllers.validators;

import com.netcracker.project.controllers.validators.errorMessage.ErrorMessages;
import com.netcracker.project.model.entity.Comment;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommentValidator  extends AbstractValidator {

  private String datePattern = "[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])";
  private String symbolPattern = "^[\\pL\\pN0-9\\p{Punct}\\s]+$";

  public Map<String, String> validationCreate(String body) {

    validateBody(body);
  //  validateCreationDate(creationDate);

    return getErrorMap();
  }

  private void validateBody(String body){
    if (body == null || body.isEmpty()){
      setErrorToMap("body_error", "Field can't to be null!");
    }
    if (!checkString(body)){
      setErrorToMap("body_error", "Invalid name, please try again!");
    }
  }

  public Map<String, String> validateExistenceComment(Integer commentExistence) {
    if (commentExistence == 0) {
      setErrorToMap("COMMENT_ERROR", ErrorMessages.COMMENT_ERROR);
    }
    return getErrorMap();
  }

  void validateCreationDate(String creationDate) {
    if (creationDate == null) {
      setErrorToMap("COMMENT_ERROR", ErrorMessages.COMMENT_DATE_ERROR);
    }
    if (!checkDate(creationDate)) {
      setErrorToMap("COMMENT_ERROR", ErrorMessages.COMMENT_DATE_ERROR);
    }
  }

  public Map<String, String> validationEntityComment(Comment comment) {
    validateComment(comment);
    return getErrorMap();
  }

  public Map<String, String> validateInputId(String id) {
    validateId(id);
    return getErrorMap();
  }

  public void validateComment(Comment comment){
    if (comment == null){
      setErrorToMap("existence_error", "You else haven't comments!");
    }
  }

  private boolean checkDate(String dateString) {
    Pattern p = Pattern.compile(datePattern);
    Matcher m = p.matcher(dateString);
    return m.matches();
  }

  private boolean checkString(String enteringString){
    Pattern pattern = Pattern.compile(symbolPattern);
    Matcher matcher = pattern.matcher(enteringString);
    return matcher.matches();
  }

}
