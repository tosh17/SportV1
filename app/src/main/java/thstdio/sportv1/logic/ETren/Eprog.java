package thstdio.sportv1.logic.ETren;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shcherbakov on 29.05.2017.
 */

public class Eprog {
    private int id;
     private String name;
    private int count;
    private int place;
    private Map<Integer, Eday> listDay = new HashMap<Integer, Eday>();

    public Eprog(int id, String name,int place) {
        this.id = id;
        this.name = name;this.place=place;
    }

    public void add(Eday day){
        listDay.put(count++,day);
    }

    /**
     * Возращает Лист дней тренеровок
     * @return
     */

    public List<Eday> getList(){
        List<Eday> list = new ArrayList<>();
        for(int i=0;i<listDay.size();i++){
            list.add(listDay.get(i));
        }
        return  list;

        //  return (List<Eday>) listDay.values();
    }
    public Eday getDay(int number){ return listDay.get(number);}
    public int getId() {
        return id;
    }

    public Eprog setId(int id) {
        this.id = id;
        return this;
    }

    public Eprog setName(String name) {
        this.name = name;
        return this;
    }

    public String getName(){return name;}

    public int getPlace() {
        return place;
    }

    public int getNumberOfDay(){return listDay.size();}
    public Eday getNextDay(int position){
        position=(position+1)%getNumberOfDay();
        return  getDay(position);
    }
}
