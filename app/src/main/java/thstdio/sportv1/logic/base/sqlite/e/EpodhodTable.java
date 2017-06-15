package thstdio.sportv1.logic.base.sqlite.e;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import thstdio.sportv1.logic.ETren.Epodhod;

/**
 * Created by shcherbakov on 09.06.2017.
 */

public class EpodhodTable {
    public static final String TABLE_NAME="e_podhod";
    public final class Cols {
        public static final String ID="id_podhod";
        public static final String R_COUNT="r_count"; //разминка
        public static final String EXES_COUNT="exes_count"; //  упражнения
        public static final String MIN="min";
        public static final String MAX="max";
    }
    public static void createTable(SQLiteDatabase db){
        db.execSQL("create table " + TABLE_NAME+"(" +
                Cols.ID + " integer primary key autoincrement, " +
                Cols.R_COUNT + " integer, " +
                Cols.EXES_COUNT +" integer, " +
                Cols.MIN +" NVARCHAR2, " +
                Cols.MAX +" NVARCHAR2" +
                ")");

    }
    public static ContentValues getContentValues(Epodhod podhod) {
        ContentValues values = new ContentValues();
        values.put(Cols.R_COUNT, podhod.getRazminka());
        values.put(Cols.EXES_COUNT, podhod.getCount());
        values.put(Cols.MIN, podhod.getStrMin());
        values.put(Cols.MAX, podhod.getStrMax());
        return values;
    }
}
