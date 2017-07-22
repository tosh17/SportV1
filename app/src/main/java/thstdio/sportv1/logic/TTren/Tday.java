package thstdio.sportv1.logic.TTren;

import java.util.HashMap;
import java.util.Map;

import thstdio.sportv1.logic.ETren.Eday;
import thstdio.sportv1.logic.date.DateLab;

/**
 * Created by shcherbakov on 01.06.2017.
 */

public class Tday {
    private Eday day;
    private Map<Integer, Texes> dayList = new HashMap<Integer, Texes>();
    private long id;

    public Tday(Eday day) {
        this.day = day;
        for (int i = 0; i < day.countOfExes(); i++) {
            dayList.put(i, new Texes(day.getEdayexes(i)));
        }
    }



    public Texes getTexes(int position) {
        return dayList.get(position);
    }

    public boolean iSDayDone() {
        for (int i = 0; i < day.countOfExes(); i++) {
            if (!getTexes(i).isDone()) return false;
        }
        return true;
    }

    public int size() {
        return dayList.size();
    }

    public long getId() {
        return id;
    }

    public Tday setId(long id) {
        this.id = id;
        return this;
    }
// информация о дне
    public int[] getDayInfo(){
        int i[]={day.getIdProg(),day.getNumberOfDay(),day.getIdDay()};
        return i;
    }
    public String getNameDay(){
        return day.getDescription();
    }
}
