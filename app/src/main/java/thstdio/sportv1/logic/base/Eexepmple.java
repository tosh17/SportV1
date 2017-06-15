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
                return new Eexes(1, "Разгибание ног сидя на тренажере");
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
                return new Eexes(7, "ГиперЭкстензия");
            case 8:
                return new Eexes(8, "Икры");
            case 9:
                return new Eexes(9, "Пресс");
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
                return new Eexes(15, "Брусья");
            case 16:
                return new Eexes(16, "Подтягивание широким хватом");
            case 17:
                return new Eexes(17, "Тяга вертикального блока широким хватом");
            case 18:
                return new Eexes(18, "Предплечье");
            case 19:
            return new Eexes(i, "Планка");
            case 20:
                return new Eexes(i, "отжимания широким хватом");
            case 21:
                return new Eexes(i, "отжимания узким хватом");
            case 22:
                return new Eexes(i, "отжимания на кулаках");
            case 23:
                return new Eexes(i, "Присиденая");
            case 24:
                return new Eexes(i, "Пистолет с опорой");
            case 25:
                return new Eexes(i, "Пистолет");
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
        String max = "12,12,12";
        String min = "6,6,6";
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
            case 1:

                switch (number) {
                    case 1:
                        day = new Eday(1, 1, "Ноги и Спина");
                        int temp[] = {1, 3, 4, 16, 6, 7, 8, 9};

                        for (int i : temp) {
                            day.add(getExes(i), getPodhod(i));
                        }
                        return day;
                    case 2:
                        day = new Eday(1, 2, "Грудь и Плечи");
                        int temp1[] = {10, 11, 13, 14, 15, 18, 7, 9};
                        for (int i : temp1) {
                            day.add(getExes(i), getPodhod(i));
                        }
                        return day;

                }
            case 2:
                day = new Eday(id, 1, "ALL");
                int temp[] = {20, 23, 21, 19, 25, 27, 28, 29,9};

                for (int i : temp) {
                    day.add(getExes(i), getPodhod(i));
                }
                return day;
            case 3:
                day = new Eday(id, 1, "ALL");
                int temp1[] = {20, 23, 21, 19, 24,9};

                for (int i : temp1) {
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
               prog = new Eprog(1, "Зал База",1);
               prog.add(getDay(1, 1));
               prog.add(getDay(1, 2));
               return prog;
           case 2:
               prog = new Eprog(2, "Дом Гантели",2);
               prog.add(getDay(2, 1));
               return prog;
           case 3:
               prog = new Eprog(3, "Улица свободный вес",3);
               prog.add(getDay(3, 1));
               return prog;
       }
       return null;
    }
    public static int getNumberOfProg(){return 3;}
    public static List<String> getMyProgList(){
        List<String> list=new ArrayList<>();
        for(int i=1;i<=getNumberOfProg();i++){
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
