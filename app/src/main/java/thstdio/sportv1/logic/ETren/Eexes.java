package thstdio.sportv1.logic.ETren;

/**
 * Created by shcherbakov on 29.05.2017.
 */

public class Eexes {
    int id;
    String name;
    int mainValue=0; //0 вес+количество, 1 - количество, 2 -время 3-все параметры
    boolean freeWeight=false; //упражнение с собственным весом
    public Eexes(int id, String name ) {
        this.id = id;
        this.name = name;
           }

    public Eexes(int id, String name, int mainValue) {
        this.id = id;
        this.name = name;
        this.mainValue = mainValue;
    }

    public Eexes(int id, String name, int mainValue, boolean freeWeight) {
        this.id = id;
        this.name = name;
        this.mainValue = mainValue;
        this.freeWeight = freeWeight;
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

    public int getMainValue() {
        return mainValue;
    }

    public Eexes setMainValue(int mainValue) {
        this.mainValue = mainValue;
        return this;
    }
}
