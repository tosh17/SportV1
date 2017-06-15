package thstdio.sportv1.logic.ETren;

/**
 * Created by shcherbakov on 29.05.2017.
 */

public class Eexes {
    int id;
    String name;
    int typeCount=0; //0  количество, 1 секунды
    boolean freeWeight=false;
    public Eexes(int id, String name ) {
        this.id = id;
        this.name = name;
           }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isFreeWeight() {
        return freeWeight;
    }

    public void setFreeWeight(boolean freeWeight) {
        this.freeWeight = freeWeight;
            }
}
