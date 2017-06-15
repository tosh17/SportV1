package thstdio.sportv1.logic.base.sqlite.e;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import thstdio.sportv1.logic.ETren.Eprog;

/**
 * Created by shcherbakov on 12.06.2017.
 */

public class EprogTable {
    public static class INFO {
        public static final String TABLE_NAME = "e_prog";

        public final class Cols {

            public static final String ID_PROG = "id_prog";
            public static final String DESCRIPTION = "description";
            public static final String PLACE = "place";
            public static final String COUNT_DAY = "count_day";
        }

        public static void createTable(SQLiteDatabase db) {
            db.execSQL("create table " + TABLE_NAME + "(" +
                    " _id integer primary key autoincrement, " +
                    Cols.ID_PROG + " integer, " +
                    Cols.DESCRIPTION +", " +
                    Cols.PLACE + " integer, " +
                    Cols.COUNT_DAY + " integer" +

                    ")");

        }

        public static ContentValues getContentValues(Eprog prog) {
            ContentValues values = new ContentValues();
            values.put(Cols.ID_PROG, prog.getId());
            values.put(Cols.DESCRIPTION, prog.getName());
            values.put(Cols.PLACE, prog.getPlace());
            values.put(Cols.COUNT_DAY, prog.getNumberOfDay());
            return values;
        }
    }


}

