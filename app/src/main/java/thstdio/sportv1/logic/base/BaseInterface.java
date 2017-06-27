package thstdio.sportv1.logic.base;

import java.util.List;

import thstdio.sportv1.logic.ETren.*;
import thstdio.sportv1.logic.TTren.Tday;
import thstdio.sportv1.logic.TTren.Texes;

/**
 * Created by shcherbakov on 01.06.2017.
 */

public interface BaseInterface {
    /**
     * Список программ в виде имя::id
     * @return
     */
    public List<String> getMyProgList();

    /**
     * Поиск упражнения по id
     * @param id
     * @return
     */
    public Eexes getEexes(int id);

    /**
     * поиск подхода по id
     * @param id
     * @return
     */
    public Epodhod getEpodhod(int id);

    /**
     * возращает основную группы упражнения
     * @param idExes
     * @return
     */
    public int getExesType(int idExes);
    public String getExesTypeDescription(int idType);

    /**
     * Поиск общей информации по дню
     * @param idprog int id Программы
     * @param numberOfDay int id дня
     * @return  Eday  возращает день без упражнений
     */
    public Eday getEdayInfo(int idprog, int numberOfDay);
    /**
     * поиск упражнений дня по ид програмы и дня(номер дня с единицы)
     * @param idprog  int id Программы
     * @param numberOfDay int id дня
     * @return
     */

    public Eday getEday(int idprog, int numberOfDay);

    /**
     * поиск информации По програме
     * @param id int
     * @return Eprog
     */
    public Eprog getEprogInfo(int id);
    /**
     * поиск прокрамы по id
     * @param id
     * @return
     */
    public Eprog getEprog(int id);

    /**
     * запись базу упражнения, для коллекции пустой метод
     */
    public void setEexes(Eexes exes);
    public void setExesType(int idExes,int idType);
    public void setExesTypeDescription(int idType, String description);
    /**
     * запись в базу подход
     */
   public int setEpodhod(Epodhod podhod);

    /**
     * запись базу дня тренеровки, для коллекции пустой метод
     * @param day Eday
     */
    public void setEday(Eday day);

    /**
     * запись базу программы тренеровки, для коллекции пустой метод
     *
     * @param prog Eprog
     */
      public void setEprog (Eprog prog);

    /**
     * Добовление упражнения в базу, если оно уже там есть то коректируется
     * @param exes
     */
      public void addExes(Eexes exes);

    /**
     * обнавление данных упражнения
     * @param exes
     */

     public void updateExes(Eexes exes);

    /**
     * поиск индекса подхода
     * @param podhod  Epodhod
     * @return int
     */
    public int findEpodhod(Epodhod podhod);

    /**
     * обновляет данные о дне
     * @param day Eday
     */
    public void updateEday(Eday day);

    /**
     * Удаляет день из базы
     * @param day Eday
     */
    public void deleteEday(Eday day);

    /**
     *
     * @return int Возращает максимальный id для упражнения
     */
    public int getIdExesMax();

    /**
     * вывод полного списка упражнений
     * @return ывод полного списка упражнений
     */
    public List<Eexes> getAllExes();


    /**
     *
     * @return int Возращает максимальный id для gпрограммы
     */
    public int getIdProgMax();

    /**
     * Записывает в базу информацию он начале тренеровки
     * @param day Передаем день тренеровки
     */
    public void startTday(Tday day);

    /**
     * Завершаем тренеровку Записываем время оканчания
     * @param day Передаем день тренеровки
     * @param endTime Время окончания
     */
    public void endTday(Tday day,long endTime);

    /**
     * Поиск не завершенного дня
     * @return  Возращает тренеровочный день
     */
    public Tday findNotEndTday();

    /**
     * Запись выполненого подхода в базу
     * @param tExesValues упорядочный массив данных(порядок согластно полям таблицы)
     */
    public void setTexesPodhod(long[] tExesValues);

    /**
     * вспомогательная функция для востоновления тренеровочного дня
     * @param idTday  номер тренеровочного дня в базе
     * @param idExes  номер упражнения
     * @return  упорядочный массив данных(порядок согластно полям таблицы)
     */
     int[] loadTexes(int idTday,int idExes);

    /**
     * Запись показаний измерений в базу
     * @param date  время
     * @param type тип
     * @param value  значение
     */
     public void setMeasure(int date,int type,int value);
    public int[][] getTdayStat(long idTday);

    /**
     * Возращает массив для заполнение инфы о тренеровочном дне
      * @return {IdDay,ProgName,DayName,TotalTime}
     */
    List<String[]> getStatList();
}
