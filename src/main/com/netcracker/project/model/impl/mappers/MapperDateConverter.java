package main.com.netcracker.project.model.impl.mappers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MapperDateConverter {

  public Date convertStringToDate(String str) {
    Date date = null;
    DateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy");

    try {
      date = dateFormat.parse(str);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }

  public String convertDateTosString(Date startDate) {
    String dateStr = startDate.toString();
    DateFormat readFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

    DateFormat writeFormat = new SimpleDateFormat("dd.mm.yy");
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
