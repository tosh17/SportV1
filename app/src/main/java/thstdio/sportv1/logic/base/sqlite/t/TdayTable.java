package thstdio.sportv1.logic.base.sqlite.t;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import thstdio.sportv1.logic.TTren.Tday;

/**
 * Created by shcherbakov on 22.06.2017.
 */

public class TdayTable {
          public static final String TABLE_NAME = "t_day";

        public final class Cols {
            public static final String DATE = "date"; //id_t_day
            public static final String ID_PROG = "id_prog";
            public static final String NUMBER_DAY = "number_day";
            public static final String TIME_END = "time_end"; //-1 тренеровка не закончена
        }

    public static void createTable(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(" +
                " _id integer primary key autoincrement, " +
                Cols.DATE + " LARGEINT, " +
                Cols.ID_PROG + " integer, " +
                Cols.NUMBER_DAY + " integer, " +
                Cols.TIME_END + " LARGEINT" +
                ")");

    }

    public static ContentValues getContentValues(Tday day) {
        ContentValues values = new ContentValues();
        values.put(Cols.DATE, day.getId());
        values.put(Cols.ID_PROG, day.getDayInfo()[0]);
        values.put(Cols.NUMBER_DAY, day.getDayInfo()[1]);
        values.put(Cols.TIME_END, -1);
        return values;
    }
    public static ContentValues getContentToEndValues(long end) {
        ContentValues values = new ContentValues();
        values.put(Cols.TIME_END, end);
        return values;
    }
}
