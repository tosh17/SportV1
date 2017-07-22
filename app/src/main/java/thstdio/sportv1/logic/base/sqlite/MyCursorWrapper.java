package thstdio.sportv1.logic.base.sqlite;


import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.ArrayList;
import java.util.List;

import thstdio.sportv1.logic.ETren.Eday;
import thstdio.sportv1.logic.ETren.Eexes;
import thstdio.sportv1.logic.ETren.Epodhod;
import thstdio.sportv1.logic.base.sqlite.e.EdayTable;
import thstdio.sportv1.logic.base.sqlite.e.EpodhodTable;
import thstdio.sportv1.logic.base.sqlite.e.EprogTable;
import thstdio.sportv1.logic.base.sqlite.e.EexesTable;
import thstdio.sportv1.logic.base.sqlite.e.ExesTypeTable;
import thstdio.sportv1.logic.base.sqlite.t.TdayTable;
import thstdio.sportv1.logic.base.sqlite.t.TexesTable;
import thstdio.sportv1.logic.date.DateLab;

/**
 * Created by shcherbakov on 09.06.2017.
 */

public class MyCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public MyCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Eexes getEexes() {
        int id = getInt(getColumnIndex(EexesTable.Cols.ID));
        String name = getString(getColumnIndex(EexesTable.Cols.NAME));
        int isSolved = getInt(getColumnIndex(EexesTable.Cols.FREE_WEIGHT));
        Eexes exes=new Eexes(id,name);
        exes.setFreeWeight(isSolved==0 ? false : true);
        int mainValue=getInt(getColumnIndex(EexesTable.Cols.MAIN_VALUE));
        exes.setMainValue(mainValue);
        return exes;

    }
    public Epodhod getEpodhod() {
        int id = getInt(getColumnIndex(EpodhodTable.Cols.ID));
        int rCount = getInt(getColumnIndex(EpodhodTable.Cols.R_COUNT));
        int exesCount = getInt(getColumnIndex(EpodhodTable.Cols.EXES_COUNT));
        String min=getString(getColumnIndex(EpodhodTable.Cols.MIN));
        String max=getString(getColumnIndex(EpodhodTable.Cols.MAX));
        Epodhod podhod=new Epodhod(id,rCount,exesCount,min,max);

        return podhod;

    }

    public int getExesType() {
        int exesType = getInt(getColumnIndex(ExesTypeTable.Cols.ID_TYPE));
        return exesType;
    }

    public Eday getEdayInfo() {
        int id = getInt(getColumnIndex("_id"));
        int idprog = getInt(getColumnIndex(EdayTable.INFO.Cols.ID_PROG));
        int numberDay = getInt(getColumnIndex(EdayTable.INFO.Cols.NUMBER_DAY));
        String description=getString(getColumnIndex(EdayTable.INFO.Cols.DESCRIPTION));
        Eday day= new Eday(idprog,numberDay,description);
        day.setIdDay(id);
        return day;
    }

    public int[] getidEdayList() {
        int[] id=new int[2];
        id[0] = getInt(getColumnIndex(EdayTable.Cols.ID_EXES));
        id[1] = getInt(getColumnIndex(EdayTable.Cols.ID_PODHOD));
        return id;
    }

    public List<Object> getEprogInfo() { //id--Description--count day--place
        List<Object> list=new ArrayList<>();
        int idprog = getInt(getColumnIndex(EprogTable.INFO.Cols.ID_PROG));
        list.add(idprog);
        String description=getString(getColumnIndex(EprogTable.INFO.Cols.DESCRIPTION));
        list.add(description);
        int number=getInt(getColumnIndex(EprogTable.INFO.Cols.COUNT_DAY));
        list.add(number);
        int place=getInt(getColumnIndex(EprogTable.INFO.Cols.PLACE));
        list.add(place);
        return list;
    }

    public String getId() {
        String id = getString(getColumnIndex("id"));
        return id;
    }

    public int[] getTdayStat() {
        int[] tdayStat=new int[8];
        long t1=getLong(getColumnIndex(TexesTable.Cols.ID_T_DAY));
        long t2=getLong(getColumnIndex(TexesTable.Cols.TIME_START));
        tdayStat[0]= (int) (t2-t1)/1000;
        tdayStat[1]=getInt(getColumnIndex(TexesTable.Cols.ID_EXES));
        tdayStat[2]=getInt(getColumnIndex(TexesTable.Cols.NUMBER_PODHOD));
        tdayStat[3]=getInt(getColumnIndex(TexesTable.Cols.TYPE));
        tdayStat[4]=getInt(getColumnIndex(TexesTable.Cols.COUNT));
        tdayStat[5]=getInt(getColumnIndex(TexesTable.Cols.WEIGHT));
        tdayStat[6]=getInt(getColumnIndex(TexesTable.Cols.WEIGHT_PLUS));
        tdayStat[7]=getInt(getColumnIndex(TexesTable.Cols.TIMER));


        return tdayStat;
    }

    public String[] getTdayStatInfo() {
        String[] str = new String[4];
        str[0]=getString(getColumnIndex(TdayTable.Cols.DATE));
        str[1]=getString(getColumnIndex(TdayTable.Cols.ID_PROG));
        str[2]=getString(getColumnIndex(TdayTable.Cols.NUMBER_DAY));
        str[3]=DateLab.parseSecondt((int) (getLong(getColumnIndex(TdayTable.Cols.TIME_END))-getLong(getColumnIndex(TdayTable.Cols.DATE)))/1000,":");
        return str;
    }
}
