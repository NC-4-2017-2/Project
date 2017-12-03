package main.com.netcracker.project.model.impl.mappers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import main.com.netcracker.project.model.impl.BusinessTripDAOImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class MapperDateConverter extends JsonSerializer<Date> {

  private Logger logger = Logger.getLogger(BusinessTripDAOImpl.class);
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
      "dd.MM.yy");


  public Date convertStringToDate(String str) {
    Date date = null;
    String pattern = "dd.MM.yy";
    DateFormat dateFormat = new SimpleDateFormat(pattern);

    try {
      date = dateFormat.parse(str);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }

  public String convertDateTosString(Date startDate) {
    String pattern = "E MMM dd HH:mm:ss Z yyyy";
    String patternWriteFormat = "dd.MM.yy";
    String dateStr = startDate.toString();
    DateFormat readFormat = new SimpleDateFormat(pattern);

    DateFormat writeFormat = new SimpleDateFormat(patternWriteFormat);
    Date date = null;
    try {
      date = readFormat.parse(dateStr);
    } catch (ParseException e) {
      logger.error("ParseException : " + e.getMessage());
    }

    String formattedDate = "";
    if (date != null) {
      formattedDate = writeFormat.format(date);
    }
    return formattedDate;
  }

  @Override
  public void serialize(Date date, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider)
      throws IOException, JsonProcessingException {
    String formattedDate = dateFormat.format(date);

    jsonGenerator.writeString(formattedDate);
  }
}
