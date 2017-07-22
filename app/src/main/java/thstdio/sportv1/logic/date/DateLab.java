package thstdio.sportv1.logic.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by shcherbakov on 22.06.2017.
 */

public class DateLab {
    private static int zone = 3;
    private static Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"),
            Locale.getDefault());
   // private static String[] month = {"Января", "Февраля", "Марта", "Апреля", "Мая", "Июня", "Июля", "Августа", "Сентября", "Октября", "Ноября", "Декабря"};

    public static long now() {
        Date date = new Date();
        return date.getTime();
    }

    public static String parseSecondt(int sec, String separator) {
        String str = "";
        int h = 0, m = 0, s = 0;
        h = sec / 3600;
        m = (sec - h * 3600) / 60;
        s = sec % 60;
        return String.format("%02d%s%02d%s%02d",
                h,separator, m,separator,s);

    }

    public static String parceDate(long time,String[] month) {
        int[] date = parseToUserTime(time);
        return String.format("%d %s %d  %02d:%02d",
                date[2], month[date[1]], date[0], date[3], date[4]);

    }

    public static int[] parseToUserTime(long time) {
        calendar.setTimeInMillis(time);
        Date currentLocalTime = calendar.getTime();
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd-hh-mm");
        String localTime = date.format(currentLocalTime);
        String[] strTime = localTime.split("-");
        int[] dateTime = new int[5];
        for (int i = 0; i < 5; i++) {
            dateTime[i] = Integer.parseInt(strTime[i]);
        }
        dateTime[1]--;
        return dateTime;
    }

    public static boolean equalDay(long time, int[] ymd) {
        int[] date = parseToUserTime(time);
        //int[] ymd1=ymd;
        for (int i = 0; i < 3; i++) {
            if (date[i] != ymd[i]) return false;
        }
        return true;
    }

}
