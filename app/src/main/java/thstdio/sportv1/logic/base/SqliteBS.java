package thstdio.sportv1.logic.base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import thstdio.sportv1.logic.ETren.Eday;
import thstdio.sportv1.logic.ETren.Eexes;
import thstdio.sportv1.logic.ETren.Epodhod;
import thstdio.sportv1.logic.ETren.Eprog;
import thstdio.sportv1.logic.TTren.Tday;
import thstdio.sportv1.logic.base.sqlite.BsHelper;
import thstdio.sportv1.logic.base.sqlite.MyCursorWrapper;
import thstdio.sportv1.logic.base.sqlite.e.EdayTable;
import thstdio.sportv1.logic.base.sqlite.e.EpodhodTable;
import thstdio.sportv1.logic.base.sqlite.e.EprogTable;
import thstdio.sportv1.logic.base.sqlite.e.EexesTable;
import thstdio.sportv1.logic.base.sqlite.e.ExesTypeTable;
import thstdio.sportv1.logic.base.sqlite.t.TdayTable;

/**
 * Created by shcherbakov on 08.06.2017.
 */

public class SqliteBS extends MainBS {
    private static SqliteBS ourInstance = null;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static SqliteBS getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new SqliteBS(context);
        }
        return ourInstance;
    }

    private SqliteBS(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new BsHelper(mContext)
                .getWritableDatabase();

    }

    private Cursor myQuery(String tableName, String whereClause, String[] whereArgs) {


        Cursor cursor = mDatabase.query(
                tableName,
                null, // Columns - null выбирает все столбцы
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        int c = cursor.getCount();
        return cursor;
    }

    private Cursor myMaxQuery(String tableName,String column ,String whereClause, String[] whereArgs) {

        String maxCol[]={"Max("+column+") as id"};
        Cursor cursor = mDatabase.query(
                tableName,
                maxCol, // Columns - null выбирает все столбцы
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        int c = cursor.getCount();
        return cursor;
    }

    @Override
    public Eexes getEexes(int id) {
        String myWhere = EexesTable.Cols.ID + " = ?";
        String[] myArg = {new Integer(id).toString()};

        MyCursorWrapper cursor = new MyCursorWrapper(myQuery(EexesTable.TABLE_NAME, myWhere, myArg));

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Eexes exes = cursor.getEexes();
                return exes;
            }
        } finally {
            cursor.close();
        }

        return null;
    }

    @Override
    public void setEexes(Eexes exes) {
        if(getEexes(exes.getId())==null) {
            ContentValues values = EexesTable.getContentValues(exes);
            mDatabase.insert(EexesTable.TABLE_NAME, null, values);
        }
    }

    @Override
    public void updateExes(Eexes exes) {
        ContentValues values = EexesTable.getContentValues(exes);
        mDatabase.update(EexesTable.TABLE_NAME, values,
                EexesTable.Cols.ID + " = ?",
                new String[]{new Integer(exes.getId()).toString()});
    }

    /**
     * поиск подхода по id
     *
     * @param id
     * @return
     */
    @Override
    public Epodhod getEpodhod(int id) {
        String myWhere = EpodhodTable.Cols.ID + " = ?";
        String[] myArg = {new Integer(id).toString()};

        MyCursorWrapper cursor = new MyCursorWrapper(myQuery(EpodhodTable.TABLE_NAME, myWhere, myArg));

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Epodhod podhod = cursor.getEpodhod();
                return podhod;
            }
        } finally {
            cursor.close();
        }

        return null;
    }

    /**
     * запись в базу подход
     *
     * @param podhod
     */
    @Override
    public int setEpodhod(Epodhod podhod) {
        int c = findEpodhod(podhod);
        if (c > 0) return c;
        ContentValues values = EpodhodTable.getContentValues(podhod);
        mDatabase.insert(EpodhodTable.TABLE_NAME, null, values);
        return findEpodhod(podhod);
    }

    /**
     * поиск индекса подхода
     *
     * @param podhod
     * @return
     */
    @Override
    public int findEpodhod(Epodhod podhod) {
        String myWhere = EpodhodTable.Cols.R_COUNT + " = ? AND " + EpodhodTable.Cols.EXES_COUNT + " = ? AND "
                + EpodhodTable.Cols.MIN + " = ? AND " + EpodhodTable.Cols.MAX + " = ?";
        String[] myArg = {new Integer(podhod.getRazminka()).toString(),
                new Integer(podhod.getCount()).toString(),
                podhod.getStrMin(), podhod.getStrMax()};

        MyCursorWrapper cursor = new MyCursorWrapper(myQuery(EpodhodTable.TABLE_NAME, myWhere, myArg));

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                return cursor.getEpodhod().getId();

            }
        } finally {
            cursor.close();
        }

        return 0;
    }

    /**
     * возращает основную группы упражнения
     *
     * @param idExes
     * @return
     */
    @Override
    public int getExesType(int idExes) {
        String myWhere = ExesTypeTable.Cols.ID_EXES + " = ?";
        String[] myArg = {new Integer(idExes).toString()};

        MyCursorWrapper cursor = new MyCursorWrapper(myQuery(ExesTypeTable.TABLE_NAME, myWhere, myArg));

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int result = cursor.getExesType();
                return result;
            }
        } finally {
            cursor.close();
        }

        return -1;
    }

    @Override
    public void setExesType(int idExes, int idType) {
        ContentValues values = ExesTypeTable.getContentValues(idExes, idType);
        mDatabase.insert(ExesTypeTable.TABLE_NAME, null, values);
    }

    @Override
    public void setExesTypeDescription(int idExes, String description) {
        ContentValues values = ExesTypeTable.Description.getContentValues(idExes, description);
        mDatabase.insert(ExesTypeTable.Description.TABLE_NAME, null, values);
    }

    /**
     * поиск дня по ид програмы и дня(номер дня с единицы)
     *
     * @param idprog
     * @param numberOfDay
     * @return
     */
    @Override
    public Eday getEday(int idprog, int numberOfDay) {
        Eday day=getEdayInfo(idprog,numberOfDay);

        String myWhere = EdayTable.Cols.ID_DAY + " = ?" ;
        String[] myArg = {new Integer(day.getIdDay()).toString()};

        MyCursorWrapper cursor = new MyCursorWrapper(myQuery(EdayTable.TABLE_NAME, myWhere, myArg));

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int[] idEdayList=cursor.getidEdayList();
                day.add(getEexes(idEdayList[0]),getEpodhod(idEdayList[1]));
                cursor.moveToNext();
                 }
        } finally {
            cursor.close();
        }
        return day;
    }

    @Override
    public Eday getEdayInfo(int idprog, int numberOfDay) {
        String myWhere = EdayTable.INFO.Cols.ID_PROG + " = ? AND " + EdayTable.INFO.Cols.NUMBER_DAY + " = ?" ;
        String[] myArg = {new Integer(idprog).toString(),new Integer(numberOfDay).toString()};

        MyCursorWrapper cursor = new MyCursorWrapper(myQuery(EdayTable.INFO.TABLE_NAME, myWhere, myArg));

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Eday day = cursor.getEdayInfo();
                return day;
            }
        } finally {
            cursor.close();
        }

        return null;
    }

    /**
     * запись базу дня тренеровки, для коллекции пустой метод
     *
     * @param day
     */
    @Override
    public void setEday(Eday day) {
        if(getEdayInfo(day.getIdProg(),day.getNumberOfDay())!=null) return;
        //Запись информации о дне
        ContentValues values = EdayTable.INFO.getContentValues(day);
        mDatabase.insert(EdayTable.INFO.TABLE_NAME, null, values);
        //поиск id созданого дня
        day.setIdDay(getEdayInfo(day.getIdProg(), day.getNumberOfDay()).getIdDay());
        for (int i = 0; i < day.countOfExes(); i++) {
            setEexes(day.getEdayexes(i).getExes());
            day.getEdayexes(i).getPodhod().setId(setEpodhod(day.getEdayexes(i).getPodhod()));//попытка записи подхода в базу, если текущий подход есть то переписываем индификатор
            values = EdayTable.getContentValues(day, i);
            mDatabase.insert(EdayTable.TABLE_NAME, null, values);
        }
    }


     private List<Object> getProgHelper(int id){

         String myWhere = EprogTable.INFO.Cols.ID_PROG + " = ?" ;
         String[] myArg = {new Integer(id).toString()};

         MyCursorWrapper cursor = new MyCursorWrapper(myQuery(EprogTable.INFO.TABLE_NAME, myWhere, myArg));

         try {
             cursor.moveToFirst();
             while (!cursor.isAfterLast()) {
                 List<Object> list = cursor.getEprogInfo();
                 return list;
             }
         } finally {
             cursor.close();
         }

         return null;
     }

     /**
     * поиск прокрамы по id
     *
     * @param id ид программы
     * @return
     */
    @Override
    public Eprog getEprog(int id) {  //id--Description--count day
        List<Object> list= getProgHelper(id);
        Eprog prog=new Eprog((int)list.get(0),(String)list.get(1),(int)list.get(3));

     for(int i=1;i<=(int)list.get(2);i++){
         prog.add(getEday(id,i));
     }
     return prog;
    }

    /**
     * запись базу программы тренеровки, для коллекции пустой метод
     *
     * @param prog Программа Eprog
     */
    @Override
    public void setEprog(Eprog prog) {
        if(getProgHelper(prog.getId())!=null) return;
        //Запись информации о  программе
        ContentValues values = EprogTable.INFO.getContentValues(prog);
        mDatabase.insert(EprogTable.INFO.TABLE_NAME, null, values);

        for (int i = 0; i < prog.getNumberOfDay(); i++) {
            Eday day=prog.getDay(i);
            setEday(day);
        }
    }

    /**
     * Список программ в виде имя::id
     *
     * @return
     */
    @Override
    public List<String> getMyProgList() {
        String myWhere = null;
        String[] myArg = null;

        MyCursorWrapper cursor = new MyCursorWrapper(myQuery(EprogTable.INFO.TABLE_NAME, myWhere, myArg));
        List<String> list=new ArrayList<>();
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                List<Object> temp= cursor.getEprogInfo(); //id--Description--count day
                String str="";
                str=temp.get(1)+"::"+temp.get(0)+"::"+temp.get(3);
                list.add(str);
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return list;
    }

    /**
     * Удаляет день из базы
     *
     * @param day Eday
     */
    @Override
    public void deleteEday(Eday day) {

        String myWhere = EdayTable.INFO.Cols.ID_PROG+" = ? AND "+ EdayTable.INFO.Cols.NUMBER_DAY+" = ?";
        String[] myArg = {new Integer(day.getIdProg()).toString(),new Integer(day.getNumberOfDay()).toString()};
        mDatabase.delete(EdayTable.INFO.TABLE_NAME, myWhere, myArg);
        myWhere = EdayTable.Cols.ID_DAY+" = ?";
        myArg = new String[1];
        myArg[0]=new Integer(day.getIdDay()).toString();
        mDatabase.delete(EdayTable.TABLE_NAME, myWhere, myArg);
    }

    /**
     * @return int Возращает максимальный id для упражнения
     */
    @Override
    public int getIdExesMax() {
        String myWhere = null;
        String[] myArg = null;

        MyCursorWrapper cursor = new MyCursorWrapper(myMaxQuery(EexesTable.TABLE_NAME,EexesTable.Cols.ID ,myWhere, myArg));
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int id = cursor.getId();
                return id;
            }
        } finally {
            cursor.close();
        }
        return 0;
    }

    /**
     * вывод полного списка упражнений
     *
     * @return ывод полного списка упражнений
     */
    @Override
    public List<Eexes> getAllExes() {
        List<Eexes> list=new ArrayList<>();
        String myWhere = null;
        String[] myArg = null;

        MyCursorWrapper cursor = new MyCursorWrapper(myQuery(EexesTable.TABLE_NAME, myWhere, myArg));

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Eexes exes = cursor.getEexes();
                list.add(exes);
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return list;
    }

    /**
     * @return int Возращает максимальный id для gпрограммы
     */
    @Override
    public int getIdProgMax() {
        String myWhere = null;
        String[] myArg = null;

        MyCursorWrapper cursor = new MyCursorWrapper(myMaxQuery(EprogTable.INFO.TABLE_NAME,EprogTable.INFO.Cols.ID_PROG ,myWhere, myArg));
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int id = cursor.getId();
                return id;
            }
        } finally {
            cursor.close();
        }
        return 0;
    }

    /**
     * Записывает в базу информацию он начале тренеровки
     *
     * @param day Передаем день тренеровки
     */
    @Override
    public void startTday(Tday day) {
        ContentValues values = TdayTable.getContentValues(day);
        mDatabase.insert(TdayTable.TABLE_NAME, null, values);
    }

}
