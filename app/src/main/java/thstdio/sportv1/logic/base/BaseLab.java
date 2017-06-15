package thstdio.sportv1.logic.base;

import android.content.Context;

/**
 * Created by shcherbakov on 07.06.2017.
 */

public class BaseLab {

    public static BaseInterface getBS(Context context){ return SqliteBS.getInstance(context);}
    public static BaseInterface getBS(TypeBs bs,Context context){
        switch(bs) {
            case Collection:
            return new CollectionBS();
            case SQLite:
                return SqliteBS.getInstance(context);
        }
        return new CollectionBS();
    }
    public enum TypeBs{
        Collection,
        SQLite;
    }
}
