package thstdio.sportv1.logic.TTren;

import thstdio.sportv1.logic.ETren.Eday;
import thstdio.sportv1.logic.ETren.Eexes;
import thstdio.sportv1.logic.ETren.Epodhod;

/**
 * Created by shcherbakov on 01.06.2017.
 */

public class Texes {
    Epodhod podhod;
    Eexes exes;
    int exCountRazminka[]; //количество раз
    int exCountPodhod[];
    int exWRazminka[];  //вес
    int exWPodhod[];
    int iRazminka,iPodhod;
    int delay[],time[],numberOfExes;
    boolean isStart=false;
    public Texes(Eday.EdayList dayExes){
        this.exes=dayExes.getExes();
        this.podhod=dayExes.getPodhod();
        iRazminka=podhod.getRazminka();
        numberOfExes=0;
        exCountRazminka=new int[iRazminka];
        exWRazminka=new int[iRazminka];
        iPodhod=podhod.getCount();
        exCountPodhod=new int[iPodhod];
        exWPodhod=new int[iPodhod];
        time=new int[iPodhod+iRazminka];
        delay=new int[iPodhod+iRazminka-1];
        for(int i=0;i<iRazminka;i++){
           exCountRazminka[i]=-2;
        }
        for(int i=0;i<iPodhod;i++){
          exCountPodhod[i]=-2;
        }
    }

    /**
     * Пропуск подхода в упражнении
     * @param position
     */
    public void disExes(int position){
        if(position>iRazminka) {
            exCountRazminka[position]=-1;
        }
        else{
            exCountPodhod[position-iRazminka]=-1;
        }
    }
    public void done(int position,int count,int weight){
        isStart=true;
        if(position>iRazminka) {
            exCountRazminka[position]=count;
            exWRazminka[position]=weight;
        }
        else{
            exCountPodhod[position-iRazminka]=count;
            exWPodhod[position-iRazminka]=weight;

        }
    }
    public boolean isDone(){
        for(int i=0;i<iRazminka;i++){
            if(exCountRazminka[i]==-2)return false;
        }
        for(int i=0;i<iPodhod;i++){
            if(exCountPodhod[i]==-2)return false;
        }
        return true;
    }
    public void addExex(int count,int weight,int time,int delay){
        done(numberOfExes,count,weight);
        this.time[numberOfExes]=time;
        if(numberOfExes>0)this.delay[numberOfExes-1]=delay;
        numberOfExes++;
    }
}
