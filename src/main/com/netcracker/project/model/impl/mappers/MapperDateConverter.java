package main.com.netcracker.project.model.impl.mappers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MapperDateConverter {

  public Date convertStringToDate(String str) {
    Date date = null;
    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");

    try {
      date = dateFormat.parse(str);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }

  public String convertDateTosString(Date startDate) {
    String dateStr = startDate.toString();
    DateFormat readFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");

    DateFormat writeFormat = new SimpleDateFormat("dd.MM.yy");
    Date date = null;
    try {
      date = readFormat.parse(dateStr);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    String formattedDate = "";
    if (date != null) {
      formattedDate = writeFormat.format(date);
    }
    return formattedDate;
  }
}
