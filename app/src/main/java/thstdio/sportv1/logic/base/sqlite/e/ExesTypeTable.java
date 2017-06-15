package thstdio.sportv1.logic.base.sqlite.e;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by shcherbakov on 10.06.2017.
 */

public class ExesTypeTable {
    public static final String TABLE_NAME="e_exes_type";
    public final class Cols {
        public static final String ID_EXES="id_exes";
        public static final String ID_TYPE="id_type";


    }
    public static void createTable(SQLiteDatabase db){
        db.execSQL("create table " + TABLE_NAME+"(" +
                " _id integer primary key autoincrement, " +
                Cols.ID_EXES + " integer, " +
                Cols.ID_TYPE +
                ")");

    }
    public static ContentValues getContentValues(int idExes,int idType) {
        ContentValues values = new ContentValues();
        values.put(Cols.ID_EXES, idExes);
        values.put(Cols.ID_TYPE, idType);
        return values;
    }

    public static class Description{
        public static final String TABLE_NAME="e_exes_type_description";
        public final class Cols {
            public static final String ID_TYPE="id_type";
            public static final String VALUE="value";

        }
        public static void createTable(SQLiteDatabase db){
            db.execSQL("create table " + TABLE_NAME+"(" +
                    " _id integer primary key autoincrement, " +
                    Cols.ID_TYPE + "integer, " +
                    Cols.VALUE +
                    ")");

        }
        public static ContentValues getContentValues(int idType,String description) {
            ContentValues values = new ContentValues();

            values.put(Cols.ID_TYPE, idType);
            values.put(Cols.VALUE, description);
            return values;
        }
    }
}
