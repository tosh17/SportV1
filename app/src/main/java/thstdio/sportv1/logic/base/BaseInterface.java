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

    public Eday getEdayInfo(int idprog, int numberOfDay);
    /**
     * поиск дня по ид програмы и дня(номер дня с единицы)
     * @param idprog
     * @param numberOfDay
     * @return
     */

    public Eday getEday(int idprog, int numberOfDay);

    /**
     * поиск информации По програме
     * @param id
     * @return
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
     */
    public void setEday(Eday day);

    /**
     * запись базу программы тренеровки, для коллекции пустой метод
     *
     * @param prog
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

     void updateExes(Eexes exes);

    /**
     * поиск индекса подхода
     * @param podhod
     * @return
     */
    public int findEpodhod(Epodhod podhod);
}
