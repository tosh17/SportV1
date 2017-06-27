package thstdio.sportv1.logic.base.sqlite.t;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by shcherbakov on 22.06.2017.
 */

public class TexesTable {
    public static final String TABLE_NAME = "t_exes";

    public final class Cols {
        public static final String ID_T_DAY = "id_tday";
        public static final String TIME_START = "date";
        public static final String ID_EXES = "id_exes";
        public static final String NUMBER_PODHOD = "number";
        public static final String TYPE  = "type";  //0--разминка 1--подход
        public static final String COUNT  = "count";
        public static final String WEIGHT = "weight";
        public static final String WEIGHT_PLUS = "weight_plus";
        public static final String TIMER = "timer";
    }

    public static void createTable(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(" +
                " _id integer primary key autoincrement, " +
                Cols.ID_T_DAY + " LARGEINT, " +
                Cols.TIME_START + " LARGEINT, " +
                Cols.ID_EXES + " integer, " +
                Cols.NUMBER_PODHOD + " integer, " +
                Cols.TYPE + " integer, " +
                Cols.COUNT + " integer, " +
                Cols.WEIGHT + " integer, " +
                Cols.WEIGHT_PLUS + " integer, " +
                Cols.TIMER + " integer" +
                ")");

    }
    public static ContentValues getContentValues(long[] val) {
        ContentValues values = new ContentValues();
        values.put(Cols.ID_T_DAY,val[0]);
        values.put(Cols.TIME_START, val[1]);
        values.put(Cols.ID_EXES, val[2]);
        values.put(Cols.NUMBER_PODHOD, val[3]);
        values.put(Cols.TYPE, val[4]);
        values.put(Cols.COUNT, val[5]);
        values.put(Cols.WEIGHT, val[6]);
        values.put(Cols.WEIGHT_PLUS, val[7]);
        values.put(Cols.TIMER, val[8]);
        return values;
    }
}
