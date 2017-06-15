package thstdio.sportv1.logic.base;

import java.util.List;

import thstdio.sportv1.logic.ETren.*;

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

}
