package thstdio.sportv1.logic.ETren;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shcherbakov on 29.05.2017.
 */

public class Eday {

    private int idDay=0;
    private int idProg;
    private int nomberOfDay;
    private String description;
    private int count;

    private Map<Integer, EdayList> dayList = new HashMap<Integer, EdayList>();

    public Eday(int idProg, int nomberOfDay,String description) {
        this.idProg = idProg;
        this.nomberOfDay = nomberOfDay;
        this.description=description;
        count=0;
    }

    public int getIdProg() {
        return idProg;
    }

    public int getNomberOfDay() {
        return nomberOfDay;
    }

    public String getDescription() {
        return description;
    }
    public int getIdDay() {
        return idDay;
    }

    public Eday setIdDay(int idDay) {
        this.idDay = idDay;
        return this;
    }

    /**
     * Добавляем упражнение
     * @param exes
     * @param podhod
     */
    public void add(Eexes exes,Epodhod podhod){
        dayList.put(count++,new EdayList(exes,podhod));

    }
    public  EdayList getEdayexes(int i){
        return dayList.get(i);
    }
    public List<EdayList> getList(){
        List<EdayList> list = new ArrayList<EdayList>();
       for(int i=0;i<dayList.size();i++){
           list.add(dayList.get(i));
       }
        return  list;

    }
    public int countOfExes(){
        return dayList.size();
    }

    public class EdayList {
      private  Eexes exes;
      private Epodhod podhod;

        public EdayList(Eexes exes, Epodhod podhod) {
            this.exes = exes;
            this.podhod = podhod;
        }

        public Eexes getExes() {
            return exes;
        }

        public Epodhod getPodhod() {
            return podhod;
        }
    }
}
