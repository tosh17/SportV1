package thstdio.sportv1.logic.base.sqlite.s;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by shcherbakov on 22.06.2017.
 */

public class MeasureTable {
    public static class INFO{
        public static final String TABLE_NAME = "mesuare_info";

        public final class Cols {
            public static final String TYPE = "type";
            public static final String DESRIPTION = "description";
        }
        public static void createTable(SQLiteDatabase db) {
            db.execSQL("create table " + TABLE_NAME + "(" +
                    " _id integer primary key autoincrement, " +
                    Cols.TYPE + " integer, " +
                    Cols.DESRIPTION +
                                   ")");
        }
    }
    public static final String TABLE_NAME = "mesuare";

    public final class Cols {
        public static final String DAY = "day";
        public static final String MONTH = "month";
        public static final String YEARS = "year";
        public static final String TYPE = "type";
        public static final String VALUE = "value";
    }
    public static void createTable(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(" +
                " _id integer primary key autoincrement, " +
                Cols.DAY + " int, " +
                Cols.MONTH + " integer, " +
                Cols.YEARS + " integer, " +
                Cols.TYPE + " integer, " +
                Cols.VALUE + " integer" +
                ")");
    }
}
