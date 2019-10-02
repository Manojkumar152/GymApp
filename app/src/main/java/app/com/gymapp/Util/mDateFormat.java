package app.com.gymapp.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class mDateFormat {
//     SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");

    public String mFormat(String dt) {
        String format = "MMMM dd,yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        // SimpleDateFormat df = new SimpleDateFormat("MMMM dd,yyyy");
        return sdf.format(new Date(dt.replaceAll("-", "/")));
    }
}
