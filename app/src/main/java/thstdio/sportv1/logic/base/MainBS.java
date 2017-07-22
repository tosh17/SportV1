package thstdio.sportv1.logic.base;

import java.util.ArrayList;
import java.util.List;

import thstdio.sportv1.logic.ETren.Eday;
import thstdio.sportv1.logic.ETren.Eexes;
import thstdio.sportv1.logic.ETren.Epodhod;
import thstdio.sportv1.logic.ETren.Eprog;
import thstdio.sportv1.logic.TTren.Tday;
import thstdio.sportv1.logic.base.BaseInterface;

/**
 * Created by shcherbakov on 09.06.2017.
 */

public class MainBS implements BaseInterface {
    /**
     * Список программ в виде имя::id
     *
     * @return
     */
    @Override
    public List<String> getMyProgList() {
        return null;
    }

    /**
     * Поиск упражнения по id
     *
     * @param id
     * @return
     */
    @Override
    public Eexes getEexes(int id) {
        return null;
    }

    /**
     * поиск подхода по id
     *
     * @param id
     * @return
     */
    @Override
    public Epodhod getEpodhod(int id) {
        return null;
    }

    /**
     * возращает основную группы упражнения
     *
     * @param idExes
     * @return
     */
    @Override
    public int getExesType(int idExes) {
        return 0;
    }

    @Override
    public String getExesTypeDescription(int idType) {
        return null;
    }

    /**
     * Поиск общей информации по дню
     *
     * @param idprog      int id Программы
     * @param numberOfDay int id дня
     * @return Eday  возращает день без упражнений
     */
    @Override
    public Eday getEdayInfo(int idprog, int numberOfDay) {
        return null;
    }

    /**
     * поиск упражнений дня по ид програмы и дня(номер дня с единицы)
     *
     * @param idprog      int id Программы
     * @param numberOfDay int id дня
     * @return
     */
    @Override
    public Eday getEday(int idprog, int numberOfDay) {
        return null;
    }

    /**
     * поиск информации По програме
     *
     * @param id int
     * @return Eprog
     */
    @Override
    public Eprog getEprogInfo(int id) {
        return null;
    }

    /**
     * поиск прокрамы по id
     *
     * @param id
     * @return
     */
    @Override
    public Eprog getEprog(int id) {
        return null;
    }

    /**
     * запись базу упражнения, для коллекции пустой метод
     *
     * @param exes
     */
    @Override
    public void setEexes(Eexes exes) {

    }

    @Override
    public void setExesType(int idExes, int idType) {

    }

    @Override
    public void setExesTypeDescription(int idType, String description) {

    }

    /**
     * запись в базу подход
     *
     * @param podhod
     */
    @Override
    public int setEpodhod(Epodhod podhod) {
        return 0;
    }

    /**
     * запись базу дня тренеровки, для коллекции пустой метод
     *
     * @param day Eday
     */
    @Override
    public void setEday(Eday day) {

    }

    /**
     * запись базу программы тренеровки, для коллекции пустой метод
     *
     * @param prog Eprog
     */
    @Override
    public void setEprog(Eprog prog) {

    }

    @Override
    public void addExes(Eexes exes) {
        if(getEexes(exes.getId())==null) {setEexes(exes);}
        else {updateExes(exes);}
    }

    /**
     * обнавление данных упражнения
     *
     * @param exes
     */
    @Override
    public void updateExes(Eexes exes) {

    }

    /**
     * поиск индекса подхода
     *
     * @param podhod Epodhod
     * @return int
     */
    @Override
    public int findEpodhod(Epodhod podhod) {
        return 0;
    }

    /**
     * обновляет данные о дне
     *
     * @param day Eday
     */
    @Override
    public void updateEday(Eday day) {
             deleteEday(day);
          setEday(day);
    }

    /**
     * Удаляет день из базы
     *
     * @param day Eday
     */
    @Override
    public void deleteEday(Eday day) {

    }

    /**
     * @return int Возращает максимальный id для упражнения
     */
    @Override
    public int getIdExesMax() {
        return 0;
    }

    /**
     * вывод полного списка упражнений
     *
     * @return ывод полного списка упражнений
     */
    @Override
    public List<Eexes> getAllExes() {
        return null;
    }

    /**
     * @return int Возращает максимальный id для gпрограммы
     */
    @Override
    public int getIdProgMax() {
        return 0;
    }

    /**
     * Записывает в базу информацию он начале тренеровки
     *
     * @param day Передаем день тренеровки
     */
    @Override
    public void startTday(Tday day) {

    }

    /**
     * Завершаем тренеровку Записываем время оканчания
     *
     * @param day     Передаем день тренеровки
     * @param endTime Время окончания
     */
    @Override
    public void endTday(Tday day, long endTime) {

    }

    /**
     * Поиск не завершенного дня
     *
     * @return Возращает тренеровочный день
     */
    @Override
    public Tday findNotEndTday() {
        return null;
    }

    /**
     * Запись выполненого подхода в базу
     *
     * @param tExesValues упорядочный массив данных(порядок согластно полям таблицы)
     */
    @Override
    public void setTexesPodhod(long[] tExesValues) {

    }

    /**
     * вспомогательная функция для востоновления тренеровочного дня
     *
     * @param idTday номер тренеровочного дня в базе
     * @param idExes номер упражнения
     * @return упорядочный массив данных(порядок согластно полям таблицы)
     */
    @Override
    public int[] loadTexes(int idTday, int idExes) {
        return new int[0];
    }

    /**
     * Запись показаний измерений в базу
     *
     * @param date  время
     * @param type  тип
     * @param value значение
     */
    @Override
    public void setMeasure(int date, int type, int value) {

    }

    @Override
    public int[][] getTdayStat(long idTday) {
        return new int[0][];
    }

    /**
     * Возращает массив для заполнение инфы о тренеровочном дне
     *
     * @return {IdDay,ProgName,DayName,TotalTime}
     */
    @Override
    public List<String[]> getStatList() {
        return null;
    }

    /**
     * Поиск пары значений номер програмы и дня для которого была тренеровка
     *
     * @return {idProg,idDay}
     */
    @Override
    public int[] findLastTday(int idProg) {
        return new int[0];
    }

    /**
     * Поис последнего времени записи тренеровочного дна(для завершение брошенной тренеровки)
     *
     * @param idTday
     * @return
     */
    @Override
    public long findLastTime(long idTday) {
        return 0;
    }

    /**
     * Удаляет день из инфо таблицы
     *
     * @param idTday идификатор дня
     */
    @Override
    public void delTday(long idTday) {

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
        return new int[0];
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
        return null;
    }

    /**
     * Поиск статистики информации
     * по упражнению с последней тренеровки
     *
     *  * @param idTday
     * @param idExes упражнения
     * @return массив статистики
     */
    @Override
    public ArrayList<int[]> findLastTexes(long idTday, int idExes) {
        return null;
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
        return null;
    }
}
