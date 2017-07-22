package thstdio.sportv1.logic.base;

import java.util.ArrayList;
import java.util.List;

import thstdio.sportv1.logic.ETren.Eday;
import thstdio.sportv1.logic.ETren.Eexes;
import thstdio.sportv1.logic.ETren.Epodhod;
import thstdio.sportv1.logic.ETren.Eprog;

/**
 * Created by shcherbakov on 29.05.2017.
 */

public class Eexepmple {
    public static Eexes getExes(int i){
        switch (i){
            case 1:
                return new Eexes(1, "Подтягивание");
            case 2:
                return new Eexes(2, "Приседание со штангой");
            case 3:
                return new Eexes(3, "Приседание в тренажере");
            case 4:
                return new Eexes(4, "Жим ногами в тренажере");
            case 5:
                return new Eexes(5, "Тяга штанги в наклоне");
            case 6:
                return new Eexes(6, "Тяга гантель в наклоне");
            case 7:
                return new Eexes(7, "ГиперЭкстензия",1,true);
            case 8:
                return new Eexes(8, "Икры");
            case 9:
                return new Eexes(9, "Пресс",1,true);
            case 10:
                return new Eexes(10, "Жим штанги на наклоной скамье");
            case 11:
                return new Eexes(11, "Жим гантелей на наклоной скамье");
            case 12:
                return new Eexes(12, "Жим штанги стоя с груди");
            case 13:
                return new Eexes(13, "Жим гантелей стоя с груди");
            case 14:
                return new Eexes(14, "Бицепс");
            case 15:
                return new Eexes(15, "Брусья",1,true);
            case 16:
                return new Eexes(16, "Подтягивание широким хватом");
            case 17:
                return new Eexes(17, "Тяга вертикального блока широким хватом");
            case 18:
                return new Eexes(18, "Предплечье");
            case 19:
            return new Eexes(i, "Планка",2,true);
            case 20:
                return new Eexes(i, "отжимания широким хватом",1,true);
            case 21:
                return new Eexes(i, "отжимания узким хватом",1,true);
            case 22:
                return new Eexes(i, "отжимания на кулаках");
            case 23:
                return new Eexes(i, "Присиденая",1,true);
            case 24:
                return new Eexes(i, "Пистолет с опорой",1,true);
            case 25:
                return new Eexes(i, "Пистолет",1,true);
            case 26:
                return new Eexes(i, "Гантели -Молотки ");
            case 27:
                return new Eexes(i, "Гантели - Бицепс ");
            case 28:
                return new Eexes(i, "Гантели - Бокс ");
            case 29:
                return new Eexes(i, "Гантели - Жим вертикально ");
        }
       return null;
    }
    public static Epodhod getPodhod(int id) {

        int razminka = 2;
        int count = 3;
        String max ;
        String min;
        switch(id){
            case 2:
                razminka=1;
                count=3;
                max = "12,12,12";
                min = "6,6,6";
                return new Epodhod(id, razminka, count, min, max);
            case 5:
                razminka=1;
                count=3;
                max = "12,12,12";
                min = "6,6,6";
                return new Epodhod(id, razminka, count, min, max);
            case 12:
                razminka=0;
                count=3;
                max = "12,12,12";
                min = "6,6,6";
                return new Epodhod(id, razminka, count, min, max);
            case 20:
                razminka=0;
                count=4;
                max = "50,50,50,50";
                min = "10,10,10,10";
                return new Epodhod(id, razminka, count, min, max);
            case 23:
                razminka=0;
                count=5;
                max = "50,50,50,50,50";
                min = "10,10,10,10,10";
                return new Epodhod(id, razminka, count, min, max);
            case 21:
                razminka=0;
                count=5;
                max = "50,50,50,50,50";
                min = "10,10,10,10,10";
                return new Epodhod(id, razminka, count, min, max);
            case 27:
                razminka=0;
                count=4;
                max = "15,15,15,15";
                min = "10,10,10,10";
                return new Epodhod(id, razminka, count, min, max);
            case 29:
                razminka=0;
                count=4;
                max = "15,15,15,15";
                min = "10,10,10,10";
                return new Epodhod(id, razminka, count, min, max);
            case 19:
                razminka=0;
                count=5;
                max = "300,300,300,300,300";
                min = "120,120,120,120,120";
                return new Epodhod(id, razminka, count, min, max);
            case 9:
                razminka=0;
                count=5;
                max = "50,50,50,50,50";
                min = "15,15,15,15,15";
                return new Epodhod(id, razminka, count, min, max);
        }

         max = "12,12,12";
         min = "6,6,6";
        if (id == 7 || id == 8 || id == 9 || id == 18) {
            id=1;
            razminka = 0;
            count = 4;
            max = "50,50,50,50";
            min = "20,20,20,20";
            return new Epodhod(id, razminka, count, min, max);
        } else {
            id=2;
            return new Epodhod(id, razminka, count, min, max);
        }
    }

    public static Eday getDay(int id, int number) {
        Eday day;

        switch (id) {
            case 11:

                switch (number) {
                    case 1:
                        day = new Eday(id, number, "Ноги и Спина");
                        int temp[] = {1, 3, 4, 16, 6, 7, 8, 9};

                        for (int i : temp) {
                            day.add(getExes(i), getPodhod(i));
                        }
                        return day;
                    case 2:
                        day = new Eday(id, number, "Грудь и Плечи");
                        int temp1[] = {10, 11, 13, 14, 15, 18, 7, 9};
                        for (int i : temp1) {
                            day.add(getExes(i), getPodhod(i));
                        }
                        return day;

                }
            case 12:
                day = new Eday(id, number, "ALL");
                int temp[] = {20, 23, 21, 19, 25, 27, 28, 29,9};

                for (int i : temp) {
                    day.add(getExes(i), getPodhod(i));
                }
                return day;
            case 13:
                day = new Eday(id, number, "ALL");
                int temp1[] = {20, 23, 21, 19, 24,9};

                for (int i : temp1) {
                    day.add(getExes(i), getPodhod(i));
                }
                return day;
            case 14:
                day = new Eday(id, number, "");
                int temp4[] = {20, 23, 21, 27, 29,19,9};

                for (int i : temp4) {
                    day.add(getExes(i), getPodhod(i));
                }
                return day;
            case 15:
                day = new Eday(id, number, "");
                int temp5[] = {2,5,12};
                for (int i : temp5) {
                    day.add(getExes(i), getPodhod(i));
                }
                return day;
        }
        return null;
    }

    public static Eprog getProg(int id) {
        Eprog prog;
       switch(id) {
           case 1:
               prog = new Eprog(id, "Свободная тренеровка",1);
               return prog;
           case 11:
               prog = new Eprog(id, "Зал База",1);
               prog.add(getDay(id, 1));
               prog.add(getDay(id, 2));
               return prog;
           case 12:
               prog = new Eprog(id, "Дом Гантели",2);
               prog.add(getDay(id, 1));
               return prog;
           case 13:
               prog = new Eprog(id, "Улица свободный вес",3);
               prog.add(getDay(id, 1));
               return prog;
           case 14:
               prog = new Eprog(id,"Дом комплексная база",2);
               prog.add(getDay(id, 1));
               return prog;
           case 15:
               prog = new Eprog(id,"Для генератора",3);
               prog.add(getDay(id, 1));
               return prog;
       }
       return null;
    }
    public static int getNumberOfProg(){return 15;}
    public static List<String> getMyProgList(){
        List<String> list=new ArrayList<>();
        for(int i=11;i<=getNumberOfProg();i++){
           list.add(getProg(i).getName()+"::"+i+"::"+getProg(i).getPlace());
       }
       return list;
    }
    public static int getExesType(int id){
        switch (id){
            case 1:
                return 5;
            case 2:
                return 5;
            case 3:
                return 5;
            case 4:
                return 5;
            case 5:
                return 3;
            case 6:
                return 3;
            case 7:
                return 3;
            case 8:
                return 5;
            case 9:
                return 4;
            case 10:
                return 2;
            case 11:
                 return 2;
            case 12:
                return 6;
            case 13:
                return 6;
            case 14:
                return 1;
            case 15:
                return 3;
            case 16:
                return 3;
            case 17:
                return 3;
            case 18:
                return 1;
            case 19:
                return 4;
            case 20:
                return 3;
            case 21:
                return 2;
            case 22:
                return 1;
            case 23:
                return 5;
            case 24:
                return 5;
            case 25:
                return 5;
            case 26:
                return 1;
            case 27:
                return 1;
            case 28:
                return 1;
            case 29:
                return 3;
    }
    return 0;
}
}
