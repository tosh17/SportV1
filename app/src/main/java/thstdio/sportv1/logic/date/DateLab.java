package thstdio.sportv1.logic.date;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by shcherbakov on 22.06.2017.
 */

public class DateLab {
    private static int zone=3;
    private static String[] month={"Января","Февраля","Марта","Апреля","Мая","Июня","Июля"};
    public static long now(){
    Date date = new Date();
    return date.getTime();
}
    public static String detParseSecondt(int sec, String separator){
        String str="";
        int h=0,m=0,s=0;
        if(sec>3600){
            h=sec/3600;
            str=h +" Часов"+separator;
        }
        if(sec>60){
            m=(sec-h*3600)/60;
            str+=m +" Минут"+separator;
        }
        s=sec%60;
        str+=s +" Секунд"+separator;
        return str;
    }
    public static String parseSecondt(int sec, String separator){
        String str="";
        int h=0,m=0,s=0;
        if(sec>3600){
            h=sec/3600; }
            if(h>10) str+=h;
            else str="0"+h;
            str+=separator;

        if(sec>60){
            m=(sec-h*3600)/60;}
            if(m>10) str+=m;
            else str+="0"+m;
            str+=separator;


        s=sec%60;
        if(s>10) str+=s;
        else str+="0"+s;
        return str;
    }
    public static String parceDate(long time){
        String str="";
        DateTime date=new DateTime(time);
        str=date.getDayOfMonth()+ " " + month[date.getMonthOfYear()]+" "+ date.getYear()+ "  "
                +(date.getHourOfDay()+zone)+":"+(date.getMinuteOfHour()<10?"0"+date.getMinuteOfHour():date.getMinuteOfHour());
        return str;
    }
}
