package main.com.netcracker.project.model.impl.mappers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class MapperDateConverter {

    Date convertStringToDate(String str) {
        Date date = null;
        DateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy");

        try {
            date = dateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
