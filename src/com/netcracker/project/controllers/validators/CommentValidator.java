package com.netcracker.project.controllers.validators;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommentValidator  extends AbstractValidator {

  private String datePattern = "[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])";
  private String symbolPattern = "^[\\pL\\pN0-9\\p{Punct}\\s]+$";

  public Map<String, String> validationCreate(String body) {

    validateBody(body);
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

  public Map<String, String> validateLastNameAndFirstName(String lastName, String firstName) {
    validateAbstractName(lastName);
    validateAbstractName(firstName);
    return getErrorMap();
  }

  private boolean validateCommentDate(String date) {
    if (!checkDate(date)) {
      setErrorToMap("dateError", "Wrong data format!");
      return false;
    }

    return true;
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
