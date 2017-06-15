package thstdio.sportv1.logic.TTren;

import java.util.HashMap;
import java.util.Map;

import thstdio.sportv1.logic.ETren.Eday;

/**
 * Created by shcherbakov on 01.06.2017.
 */

public class Tday {
    private Eday day;
    private Map<Integer, Texes> dayList = new HashMap<Integer, Texes>();

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
}
