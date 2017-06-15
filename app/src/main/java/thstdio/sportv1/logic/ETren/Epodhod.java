/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thstdio.sportv1.logic.ETren;

/**
 * @author a_scherbakov
 */
public class Epodhod {

    private int id;
    private int razminka;
    private int count;
    private int[] max;
    private int[] min;

    /**
     * @param id       ид подхода
     * @param razminka количество подходов разминки
     * @param count    количество рабочих подходов
     * @param min      массив минимув
     * @param max      массив максимув
     */
    public Epodhod(int id, int razminka, int count, int[] min, int[] max) {
        this.id = id;
        this.razminka = razminka;
        this.count = count;
        this.max = max;
        this.min = min;
    }

    public Epodhod(int id, int razminka, int count, String min, String max) {
        this.id = id;
        this.razminka = razminka;
        this.count = count;
        this.max = stringToArray(max);
        this.min = stringToArray(min);
    }

    public int[] getMin() {
        return min;
    }

    public void setMin(int[] min) {
        this.min = min;
    }

    public int getRazminka() {
        return razminka;
    }

    public void setRazminka(int razminka) {
        this.razminka = razminka;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int[] getMax() {
        return max;
    }

    public void setMax(int[] max) {
        this.max = max;
    }

    @Override
    public String toString() {
        String str = "Р(" + razminka + ")" + " П";
        for (int i = 0; i < count; i++) {
            str = str + "/" + min[i] + "-" + max[i];
        }


        return str;
    }

    private String arrayToString(int mas[]) {
        String str = "";
        for (int i : mas) {
            str = str + i + ",";
        }
        return str.substring(0, str.length() - 1);
    }

    private int[] stringToArray(String str) {
        int mas[] = new int[count];
        String tmp[] = str.split(",");
        for (int i = 0; i < tmp.length; i++) {
            mas[i] = Integer.parseInt(tmp[i]);
        }
        return mas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStrMax() {
        return arrayToString(max);
    }

    public String getStrMin() {
        return arrayToString(min);
    }

    public String toStringDetail() {
        String str = "Разминка ---" + razminka + System.lineSeparator() + " Подходы";
        for (int i = 0; i < count; i++) {
            str = str + "/" + min[i] + "-" + max[i];
        }
        return str;
    }
}
