package thstdio.sportv1.logic.base.sqlite.e;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import thstdio.sportv1.logic.ETren.Eexes;

/**
 * Created by shcherbakov on 08.06.2017.
 */

public final class EexesTable {
    public static final String TABLE_NAME="e_exes";
    public final class Cols {
        public static final String ID="id_exes";
        public static final String NAME="name";
        public static final String FREE_WEIGHT="free_weight";
        public static final String MAIN_VALUE="main_value";
    }
    public static void createTable(SQLiteDatabase db){
        db.execSQL("create table " + TABLE_NAME+"(" +
                " _id integer primary key autoincrement, " +
                Cols.ID + " integer, " +
                Cols.NAME + ", " +
                Cols.FREE_WEIGHT+ " integer, " +
                Cols.MAIN_VALUE+ " integer" +
                ")");

    }
    public static ContentValues getContentValues(Eexes eexes) {
        ContentValues values = new ContentValues();
        values.put(Cols.ID, eexes.getId());
        values.put(Cols.NAME, eexes.getName());
        values.put(Cols.FREE_WEIGHT, eexes.isFreeWeight() ? 1 : 0);
        values.put(Cols.MAIN_VALUE, eexes.getMainValue());
        return values;
    }

}
