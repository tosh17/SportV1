package thstdio.sportv1.logic.TTren;

import thstdio.sportv1.logic.ETren.Eday;
import thstdio.sportv1.logic.ETren.Eexes;
import thstdio.sportv1.logic.ETren.Epodhod;

/**
 * Created by shcherbakov on 01.06.2017.
 */

public class Texes {
    //field
    Epodhod podhod;
    Eexes exes;
    int iRazminka, iPodhod;
    int iCurrent = 0;
    int[] iCount, iWeight, iTime;

    //temp
    boolean isStart = false;
    int rCount, pCount, rWeigt, pWeight, pTime;

    public Texes(Eday.EdayList dayExes) {
        this.exes = dayExes.getExes();
        this.podhod = dayExes.getPodhod();
        iRazminka = podhod.getRazminka();
        iPodhod = podhod.getCount();
        iCount = new int[iRazminka + iPodhod];
        iWeight = new int[iRazminka + iPodhod];
        iTime = new int[iRazminka + iPodhod];
        for (int i = 0; i < iRazminka + iPodhod; i++)
            iCount[i] = -2;// инициализация -2 невыполнялась, -1 пропуск
    }

    /**
     * Пропуск подхода в упражнении
     *
     * @param position
     */
    public void disExes(int position, boolean isDis) {
        iCount[position] = isDis ? -1 : -2;

    }

    public boolean isDone() {
        for (int i = 0; i < iRazminka + iPodhod; i++) {
            if (iCount[i] == -2)
                return false;
        }
        return true;
    }

    public void addExex(int count, int weight, int time) {
        while (iCount[iCurrent] == -1) {
            iCurrent++;
        }
        iCount[iCurrent] = count;
        iWeight[iCurrent] = weight;
        iTime[iCurrent] = time;
        iCurrent++;
    }
  public void correctCurrent(){
      while (iCount[iCurrent] > -2) {
          if(iCurrent<size()) iCurrent++;
          else return;
      }
  }
    public void loadExes(int number, int count, int weight, int time) {
        iCount[number] = count;
        iWeight[number] = weight;
        iTime[number] = time;
        while (iCount[iCurrent] != -2 && iCurrent < size() - 1) {
            iCurrent++;
        }
    }

    public Epodhod getPodhod() {
        return podhod;
    }

    public Eexes getExes() {
        return exes;
    }

    /**
     * возращает false  если сейчас разминка
     *
     * @return
     */
    public boolean getExesType() {
        if (iCurrent -1 < iRazminka) return false;
        return true;
    }
    public boolean getExesType(int position) {
        if (position < iRazminka) return false;
        return true;
    }
    public int[] getPodhoVal(int position) {
        int[] val = {iCount[position], iWeight[position], iTime[position]};
        return val;
    }

    public boolean isExesDis(int id) {
        return (iCount[id] == -1) ? true : false;
    }

    public int size() {
        return iRazminka + iPodhod;
    }

    public void disALL() {
        for (int i = 0; i < size(); i++) {
            if (iCount[i] == -2) iCount[i] = -1;
        }
    }

    public int getiCurrent() {
        return iCurrent;
    }
}
