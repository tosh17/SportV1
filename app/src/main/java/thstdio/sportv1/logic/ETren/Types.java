package thstdio.sportv1.logic.ETren;

import java.lang.reflect.Type;

/**
 * Created by shcherbakov on 10.06.2017.
 */

public enum Types {
    All (0,"all") ,
    Arms (1,"arms"),
    Chest(2,"chest") ,
    Back(3,"back"),
    Press(4,"press"),
    Legs(5,"legs") ,
    Shoulders(6,"shoulders");
    private final int id;
    private final String name;
    Types(int id,String str){
       this.id=id;
       this.name=str;


   }
   public String getName(){
      int c=0;
       return name;
   }

    public int getId() {
        return id;
    }
    static public Types search(int sId){
        switch(sId){
            case 0:
                return All;
            case 1:
                return Arms;
            case 2:
                return Chest;
            case 3:
                return Back;
            case 4:
                return Back;
            case 5:
                return Legs;
            case 6:
               // return Shoulders;
                return All;
        }
        return null;
    }
}
