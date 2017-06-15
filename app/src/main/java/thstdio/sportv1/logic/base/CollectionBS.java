package thstdio.sportv1.logic.base;

import java.util.List;

import thstdio.sportv1.logic.ETren.Eday;
import thstdio.sportv1.logic.ETren.Eexes;
import thstdio.sportv1.logic.ETren.Epodhod;
import thstdio.sportv1.logic.ETren.Eprog;

/**
 * Created by shcherbakov on 01.06.2017.
 */

public class CollectionBS extends MainBS {
    @Override
    public List<String> getMyProgList() {
        return Eexepmple.getMyProgList();
    }

    @Override
    public Eexes getEexes(int id) {
        return Eexepmple.getExes(id);
    }

    @Override
    public Epodhod getEpodhod(int id) {
        return null;
    }

    @Override
    public Eday getEday(int idprog, int numberOfDay) {
        return  Eexepmple.getDay(idprog, numberOfDay);
    }

    @Override
    public Eprog getEprog(int id) {
        return Eexepmple.getProg(id);
    }

    /**
     * возращает основную группы упражнения
     *
     * @param idExes
     * @return
     */
    @Override
    public int getExesType(int idExes) {
        return Eexepmple.getExesType(idExes);
    }
}
