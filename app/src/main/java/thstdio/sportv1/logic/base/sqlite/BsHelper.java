package thstdio.sportv1.logic.base.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import thstdio.sportv1.logic.TTren.Tday;
import thstdio.sportv1.logic.base.sqlite.e.EdayTable;
import thstdio.sportv1.logic.base.sqlite.e.EpodhodTable;
import thstdio.sportv1.logic.base.sqlite.e.EprogTable;
import thstdio.sportv1.logic.base.sqlite.e.EexesTable;
import thstdio.sportv1.logic.base.sqlite.e.ExesTypeTable;
import thstdio.sportv1.logic.base.sqlite.s.MeasureTable;
import thstdio.sportv1.logic.base.sqlite.t.TdayTable;
import thstdio.sportv1.logic.base.sqlite.t.TexesTable;

/**
 * Created by shcherbakov on 08.06.2017.
 */

public class BsHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "sportv1.db";
    private boolean mNew=false;
    public BsHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    public boolean isNew(){return mNew;}
    @Override
    public void onCreate(SQLiteDatabase db) {
        mNew=true;
        EexesTable.createTable(db);
        EpodhodTable.createTable(db);
        ExesTypeTable.Description.createTable(db);
        ExesTypeTable.createTable(db);
        EdayTable.INFO.createTable(db);
        EdayTable.createTable(db);
        EprogTable.INFO.createTable(db);

        TdayTable.createTable(db);
        TexesTable.createTable(db);

        MeasureTable.INFO.createTable(db);
        MeasureTable.createTable(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
