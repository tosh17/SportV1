package thstdio.sportv1.logic.base;

import java.util.List;

import thstdio.sportv1.logic.ETren.Eday;
import thstdio.sportv1.logic.ETren.Eexes;
import thstdio.sportv1.logic.ETren.Epodhod;
import thstdio.sportv1.logic.ETren.Eprog;
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

    @Override
    public Eday getEdayInfo(int idprog, int numberOfDay) {
        return null;
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
        return null;
    }

    /**
     * поиск информации По програме
     *
     * @param id
     * @return
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
     * @param day
     */
    @Override
    public void setEday(Eday day) {

    }

    /**
     * запись базу программы тренеровки, для коллекции пустой метод
     *
     * @param prog
     */
    @Override
    public void setEprog(Eprog prog) {

    }

    @Override
    public void addExes(Eexes exes) {
             if(getEexes(exes.getId())==null) {setEexes(exes);}
            else {updateExes(exes);}
     }

    @Override
    public void updateExes(Eexes exes) {

    }

    /**
     * поиск индекса подхода
     *
     * @param podhod
     * @return
     */
    @Override
    public int findEpodhod(Epodhod podhod) {
        return 0;
    }
}
