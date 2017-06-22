package thstdio.sportv1.logic.base.sqlite.e;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import thstdio.sportv1.logic.ETren.Eday;

/**
 * Created by shcherbakov on 09.06.2017.
 */

public class EdayTable {
    public static class INFO {
        public static final String TABLE_NAME = "e_day_info";

        public final class Cols {
            
            public static final String ID_PROG = "id_prog";
            public static final String NUMBER_DAY = "number_day";
            public static final String DESCRIPTION = "description";
              }

        public static void createTable(SQLiteDatabase db) {
            db.execSQL("create table " + TABLE_NAME + "(" +
                    " _id integer primary key autoincrement, " +
                    Cols.ID_PROG + " integer, " +
                    Cols.NUMBER_DAY + " integer, " +
                    Cols.DESCRIPTION +
                    ")");

        }

        public static ContentValues getContentValues(Eday day) {
            ContentValues values = new ContentValues();
            values.put(Cols.ID_PROG, day.getIdProg());
            values.put(Cols.NUMBER_DAY, day.getNumberOfDay());
            values.put(Cols.DESCRIPTION, day.getDescription());
            return values;
        }
    }


    public static final String TABLE_NAME = "e_day_list_exes";

    public final class Cols {
        public static final String ID_DAY = "id_day";
        public static final String ID_EXES = "id_exes";
        public static final String ID_PODHOD = "id_podhod";
    }

    public static void createTable(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(" +
                " _id integer primary key autoincrement, " +
                Cols.ID_DAY + " integer, " +
                Cols.ID_EXES + " integer, " +
                Cols.ID_PODHOD + " integer" +
                ")");

    }

    public static ContentValues getContentValues(Eday day,int number) {
        ContentValues values = new ContentValues();
        values.put(Cols.ID_DAY, day.getIdDay());
        values.put(Cols.ID_EXES, day.getEdayexes(number).getExes().getId());
        values.put(Cols.ID_PODHOD, day.getEdayexes(number).getPodhod().getId());
        return values;
    }
}
