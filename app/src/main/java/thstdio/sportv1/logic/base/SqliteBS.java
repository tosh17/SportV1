package thstdio.sportv1.logic.base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import thstdio.sportv1.logic.ETren.Eday;
import thstdio.sportv1.logic.ETren.Eexes;
import thstdio.sportv1.logic.ETren.Epodhod;
import thstdio.sportv1.logic.ETren.Eprog;
import thstdio.sportv1.logic.TTren.Tday;
import thstdio.sportv1.logic.base.sqlite.BsHelper;
import thstdio.sportv1.logic.base.sqlite.MyCursorWrapper;
import thstdio.sportv1.logic.base.sqlite.e.EdayTable;
import thstdio.sportv1.logic.base.sqlite.e.EexesTable;
import thstdio.sportv1.logic.base.sqlite.e.EpodhodTable;
import thstdio.sportv1.logic.base.sqlite.e.EprogTable;
import thstdio.sportv1.logic.base.sqlite.e.ExesTypeTable;
import thstdio.sportv1.logic.base.sqlite.t.TdayTable;
import thstdio.sportv1.logic.base.sqlite.t.TexesTable;
import thstdio.sportv1.logic.date.DateLab;

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

    private Cursor myMaxQuery(String tableName, String column, String whereClause, String[] whereArgs) {

        String maxCol[] = {"Max(" + column + ") as id"};
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

    /**
     * возращает максимальное значение столбца
     *
     * @param table Таблица
     * @param coll  искомый столбец
     * @return
     */
    private String findMaxColl(String table, String coll, String whereClause, String[] whereArgs) {
        MyCursorWrapper cursor = new MyCursorWrapper(myMaxQuery(table, coll, whereClause, whereArgs));
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String str = cursor.getId();
                str = str == null ? "" : str;
                return str;
            }
        } finally {
            cursor.close();
        }
        return "";
    }

    private Cursor myOrderQuery(String tableName, String whereClause, String[] whereArgs, String order) {


        Cursor cursor = mDatabase.query(
                tableName,
                null, // Columns - null выбирает все столбцы
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                order // orderBy
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
        if (getEexes(exes.getId()) == null) {
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
        Eday day = getEdayInfo(idprog, numberOfDay);

        String myWhere = EdayTable.Cols.ID_DAY + " = ?";
        String[] myArg = {new Integer(day.getIdDay()).toString()};

        MyCursorWrapper cursor = new MyCursorWrapper(myQuery(EdayTable.TABLE_NAME, myWhere, myArg));

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int[] idEdayList = cursor.getidEdayList();
                day.add(getEexes(idEdayList[0]), getEpodhod(idEdayList[1]));
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return day;
    }

    @Override
    public Eday getEdayInfo(int idprog, int numberOfDay) {
        String myWhere = EdayTable.INFO.Cols.ID_PROG + " = ? AND " + EdayTable.INFO.Cols.NUMBER_DAY + " = ?";
        String[] myArg = {new Integer(idprog).toString(), new Integer(numberOfDay).toString()};

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
        if (getEdayInfo(day.getIdProg(), day.getNumberOfDay()) != null) return;
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


    private List<Object> getProgHelper(int id) {

        String myWhere = EprogTable.INFO.Cols.ID_PROG + " = ?";
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
        List<Object> list = getProgHelper(id);
        Eprog prog = new Eprog((int) list.get(0), (String) list.get(1), (int) list.get(3));

        for (int i = 1; i <= (int) list.get(2); i++) {
            prog.add(getEday(id, i));
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
        if (getProgHelper(prog.getId()) != null) return;
        //Запись информации о  программе
        ContentValues values = EprogTable.INFO.getContentValues(prog);
        mDatabase.insert(EprogTable.INFO.TABLE_NAME, null, values);

        for (int i = 0; i < prog.getNumberOfDay(); i++) {
            Eday day = prog.getDay(i);
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
        List<String> list = new ArrayList<>();
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                List<Object> temp = cursor.getEprogInfo(); //id--Description--count day
                String str = "";
                str = temp.get(1) + "::" + temp.get(0) + "::" + temp.get(3);
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

        String myWhere = EdayTable.INFO.Cols.ID_PROG + " = ? AND " + EdayTable.INFO.Cols.NUMBER_DAY + " = ?";
        String[] myArg = {new Integer(day.getIdProg()).toString(), new Integer(day.getNumberOfDay()).toString()};
        mDatabase.delete(EdayTable.INFO.TABLE_NAME, myWhere, myArg);
        myWhere = EdayTable.Cols.ID_DAY + " = ?";
        myArg = new String[1];
        myArg[0] = new Integer(day.getIdDay()).toString();
        mDatabase.delete(EdayTable.TABLE_NAME, myWhere, myArg);
    }

    /**
     * @return int Возращает максимальный id для упражнения
     */
    @Override
    public int getIdExesMax() {
        String str = findMaxColl(EexesTable.TABLE_NAME, EexesTable.Cols.ID, null, null);
        if (str.equals("")) return 0;
        return Integer.parseInt(str);
    }

    /**
     * вывод полного списка упражнений
     *
     * @return ывод полного списка упражнений
     */
    @Override
    public List<Eexes> getAllExes() {
        List<Eexes> list = new ArrayList<>();
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
        String str = findMaxColl(EprogTable.INFO.TABLE_NAME, EprogTable.INFO.Cols.ID_PROG, null, null);
        if (str.equals("")) return 0;
        return Integer.parseInt(str);

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

    /**
     * Завершаем тренеровку Записываем время оканчания
     *
     * @param day     Передаем день тренеровки
     * @param endTime Время окончания
     */
    @Override
    public void endTday(Tday day, long endTime) {
        ContentValues values = TdayTable.getContentToEndValues(endTime);
        mDatabase.update(TdayTable.TABLE_NAME, values,
                TdayTable.Cols.DATE + " = ?",
                new String[]{new Long(day.getId()).toString()});
    }

    /**
     * Удаляет день из инфо таблицы
     *
     * @param idTday идификатор дня
     */
    @Override
    public void delTday(long idTday) {
        String myWhere = TdayTable.Cols.DATE + " = ?";
        String[] myArg = {new Long(idTday).toString()};
        mDatabase.delete(TdayTable.TABLE_NAME, myWhere, myArg);
    }

    /**
     * Запись выполненого подхода в базу
     *
     * @param tExesValues упорядочный массив данных(порядок согластно полям таблицы)
     */
    @Override
    public void setTexesPodhod(long[] tExesValues) {
        ContentValues values = TexesTable.getContentValues(tExesValues);
        mDatabase.insert(TexesTable.TABLE_NAME, null, values);
    }

    /**
     * Возращается сстатистика дня
     *
     * @param idTday
     * @return
     */
    @Override
    public int[][] getTdayStat(long idTday) {
        int[][] resTable;
        String myWhere = TexesTable.Cols.ID_T_DAY + " = ?";
        String[] myArg = {new Long(idTday).toString()};

        MyCursorWrapper cursor = new MyCursorWrapper(myQuery(TexesTable.TABLE_NAME, myWhere, myArg));

        try {
            resTable = new int[cursor.getCount()][8];
            cursor.moveToFirst();
            int i = 0;
            while (!cursor.isAfterLast()) {
                resTable[i++] = cursor.getTdayStat();

                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return resTable;
    }

    /**
     * Возращает массив для заполнение инфы о тренеровочном дне
     *
     * @return {IdDay,ProgName,DayName,TotalTime}
     */
    @Override
    public List<String[]> getStatList() {
        String[] resTable = new String[4];
        List<String[]> list = new ArrayList<>();
        String myWhere = TdayTable.Cols.TIME_END + ">?";
        String[] myArg = {"-1"};

        MyCursorWrapper cursor = new MyCursorWrapper(myQuery(TdayTable.TABLE_NAME, myWhere, myArg));

        try {

            cursor.moveToFirst();
            int i = 0;
            while (!cursor.isAfterLast()) {
                list.add(cursor.getTdayStatInfo());

                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    /**
     * Поиск не завершенного дня
     *
     * @return Возращает тренеровочный день и null если не найдено
     */
    @Override
    public Tday findNotEndTday() {
        Tday tDay = null;
        String myWhere = TdayTable.Cols.TIME_END + " = ?";
        String[] myArg = {"-1"};

        MyCursorWrapper cursor = new MyCursorWrapper(myQuery(TdayTable.TABLE_NAME, myWhere, myArg));

        try {

            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                String[] temp = cursor.getTdayStatInfo();

                tDay = new Tday(getEday(Integer.parseInt(temp[1]), Integer.parseInt(temp[2])));
                tDay.setId(Long.parseLong(temp[0]));
                loadTday(tDay);
                return tDay;
            }
        } finally {
            cursor.close();
        }
        return tDay;
    }

    /**
     * Вспомогательная функция для findNotEndTday()
     *
     * @param tDay
     * @return
     */
    private Tday loadTday(Tday tDay) {

        int[][] log = getTdayStat(tDay.getId());
        int z = -1;
        for (int i = 0; i < log.length; i++) {
            int idExes = log[i][1];
            int number = log[i][2];
            int count = log[i][4];
            int weight = log[i][5];
            int timer = log[i][7];
            z = -1;
            while (tDay.getTexes(++z).getExes().getId() != idExes) ;

            tDay.getTexes(z).loadExes(number, count, weight, timer);
        }
        return tDay;
    }

    /**
     * Поиск пары значений номер програмы и дня для которого была тренеровка
     *
     * @return {idProg,idDay}
     */
    @Override
    public int[] findLastTday(int idProg) {
        int result[] = {0, 0};
        String myWhere = null;
        String[] myArg = null;
        if (idProg != 0) {
            myWhere = TdayTable.Cols.ID_PROG + " = ?";
            myArg = new String[1];
            myArg[0] = new Integer(idProg).toString();
        }

        String str = findMaxColl(TdayTable.TABLE_NAME, TdayTable.Cols.TIME_END, myWhere, myArg);
        if (str.equals("")) return result;
        String myWhere1 = TdayTable.Cols.TIME_END + " = ?";
        String[] myArg1 = {str};
        MyCursorWrapper cursor = new MyCursorWrapper(myQuery(TdayTable.TABLE_NAME, myWhere1, myArg1));
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String[] temp = cursor.getTdayStatInfo();
                result[0] = Integer.parseInt(temp[1]);
                result[1] = Integer.parseInt(temp[2]);
                return result;
            }
        } finally {
            cursor.close();
        }
        return result;
    }

    /**
     * Поис последнего времени записи тренеровочного дна(для завершение брошенной тренеровки)
     *
     * @param idTday
     * @return
     */
    @Override
    public long findLastTime(long idTday) {
        String myWhere = TexesTable.Cols.ID_T_DAY + " = ?";
        String[] myArg = {new Long(idTday).toString()};

        String str = findMaxColl(TexesTable.TABLE_NAME, TexesTable.Cols.TIME_START, myWhere, myArg);
        if (str.equals("")) return -1;
        return Long.parseLong(str);
    }

    /**
     * Поиск результатов последнего упражнения.
     *
     * @param idExes ид упражнения
     * @param type   подход или разминка
     * @return {вес, подходы, таймер}
     */
    @Override
    public int[] findLastExes(int idExes, int type) {
        String myWhere = TexesTable.Cols.ID_EXES + " = ? AND " + TexesTable.Cols.TYPE + " = ? AND ( NOT " + TexesTable.Cols.COUNT + " = ?)";
        String[] myArg = {new Integer(idExes).toString(), new Integer(type).toString(), "-1"};

        String str = findMaxColl(TexesTable.TABLE_NAME, TexesTable.Cols.TIME_START, myWhere, myArg);
        if (str.equals("")) return new int[]{0, 0, 0};
        long time = Long.parseLong(str);


        String myWhere1 = TexesTable.Cols.TIME_START + " = ?";
        String[] myArg1 = {str};
        int[] resTable;
        int[] result = new int[3];
        MyCursorWrapper cursor = new MyCursorWrapper(myQuery(TexesTable.TABLE_NAME, myWhere1, myArg1));

        try {

            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                resTable = cursor.getTdayStat();
                result[0] = resTable[4];
                result[1] = resTable[5];
                result[2] = resTable[7];
                return result;
            }
        } finally {
            cursor.close();
        }

        return result;
    }

    /**
     * возращает значения последних тренеровок(разминки не учитывается)
     *
     * @param id_exes индификатор тренеровки
     * @param count
     * @param lasted  учитываем текущую тренеровку ?  @return
     */
    @Override
    public ArrayList<int[]> findLastListExes(int id_exes, int count, boolean lasted) {
        String myWhere = TexesTable.Cols.ID_EXES + " = ? AND " + TexesTable.Cols.TYPE + " = ?";
        String[] myArg = {new Integer(id_exes).toString(), "1"};
        String order = TexesTable.Cols.ID_T_DAY + " DESC";
        ArrayList<int[]> result = new ArrayList<>();
        int[][] tempResult = new int[count][3];

        MyCursorWrapper cursor = new MyCursorWrapper(myOrderQuery(TexesTable.TABLE_NAME, myWhere, myArg, order));

        try {

            cursor.moveToFirst();
            if (!lasted && !cursor.isAfterLast()) cursor.moveToNext();
            int step = 0;
            while (!cursor.isAfterLast() && step < count) {
                int[] resTable = cursor.getTdayStat();
                if (resTable[4] != -1 && resTable[3] != 0) {
                    tempResult[step][0] = resTable[4];
                    tempResult[step][1] = resTable[5];
                    tempResult[step][2] = resTable[7];
                    result.add(tempResult[step]);
                    step++;
                }
                cursor.moveToNext();
            }

        } finally {

            cursor.close();
        }

        return result;
    }

    /**
     * Поиск статистики информации
     * по упражнению с последней тренеровки
     *
     * @param idExes упражнения
     * @return массив статистики
     */
    @Override
    public ArrayList<int[]> findLastTexes(long idTday, int idExes) {
        String tWhere = TdayTable.Cols.DATE + " = ?";
        String[] tArg = {new Long(idTday).toString()};
        int idProg = 0, idDay = 0;
        MyCursorWrapper cursor = new MyCursorWrapper(myQuery(TdayTable.TABLE_NAME, tWhere, tArg));

        try {

            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                String[] temp = cursor.getTdayStatInfo();

                idProg = Integer.parseInt(temp[1]);
                idDay = Integer.parseInt(temp[2]);
            }
        } finally {
            cursor.close();
        }

        String myWhere = TdayTable.Cols.ID_PROG + " = ? AND " + TdayTable.Cols.NUMBER_DAY + " = ?";
        String[] myArg = {new Integer(idProg).toString(), new Integer(idDay).toString()};
        String order = TdayTable.Cols.DATE + " DESC";
        ArrayList<int[]> result = new ArrayList<>();
        boolean isStop = false;
        cursor = new MyCursorWrapper(myOrderQuery(TdayTable.TABLE_NAME, myWhere, myArg, order));

        try {
            cursor.moveToFirst();
            cursor.moveToNext();
            while (!cursor.isAfterLast()) {

                String[] temp = cursor.getTdayStatInfo();
                long date = Long.parseLong(temp[0]);
                String myWhere1 = TexesTable.Cols.ID_T_DAY + " = ? AND " + TexesTable.Cols.ID_EXES + " = ?";
                String[] myArg1 = {new Long(date).toString(), new Integer(idExes).toString()};
                MyCursorWrapper cursor1 = new MyCursorWrapper(myQuery(TexesTable.TABLE_NAME, myWhere1, myArg1));
                try {
                    cursor1.moveToFirst();
                    result.add(DateLab.parseToUserTime(date));
                    while (!cursor1.isAfterLast()) {
                        int[] tempResult = new int[5];
                        int[] resTable = cursor1.getTdayStat();
                        tempResult[0] = resTable[3];
                        tempResult[1] = resTable[4];
                        if (resTable[4] != -1) isStop = true;
                        tempResult[2] = resTable[5];
                        tempResult[3] = resTable[7];
                        tempResult[4] = resTable[2];
                        result.add(tempResult);
                        cursor1.moveToNext();
                    }
                } finally {
                    cursor1.close();
                }
                if (!isStop) {
                    cursor.moveToNext();
                    result.clear();
                } else break;
            }
        } finally {
            cursor.close();
        }

        Collections.sort(result, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[4] > o2[4]) return 1;
                return -1;
            }
        });
        return result;
    }

    /**
     * Поиск статистики информации
     * по упражнению с заданной тренеровки
     *
     * @param idTday
     * @param idExes упражнения
     * @return массив статистики
     */
    @Override
    public ArrayList<int[]> findTexes(long idTday, int idExes) {
        String myWhere = TexesTable.Cols.ID_T_DAY + " = ? AND " + TexesTable.Cols.ID_EXES + " = ?";
        String[] myArg = {new Long(idTday).toString(), new Integer(idExes).toString()};
        MyCursorWrapper cursor = new MyCursorWrapper(myQuery(TexesTable.TABLE_NAME, myWhere, myArg));
        ArrayList<int[]> result = new ArrayList<>();

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int[] tempResult = new int[5];
                int[] resTable = cursor.getTdayStat();
                tempResult[0] = resTable[3];
                tempResult[1] = resTable[4];
                tempResult[2] = resTable[5];
                tempResult[3] = resTable[7];
                tempResult[4] = resTable[2];
                result.add(tempResult);
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        Collections.sort(result, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[4] > o2[4]) return 1;
                return -1;
            }
        });
           return result;

    }
}