package thstdio.sportv1.display.start_activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.ArrayList;

import thstdio.sportv1.display.abstract_package.SinglePageFragmentActivity;
import thstdio.sportv1.logic.TTren.Tday;
import thstdio.sportv1.logic.base.BaseInterface;
import thstdio.sportv1.logic.base.BaseLab;


/**
 * Created by shcherbakov on 24.06.2017.
 */

public class DayStatistic extends SinglePageFragmentActivity {
    private static final String EXTRA_ID = "idTday";

    long idTday;
    int[][] resTable;
    int[] idExes;
    String[] strExes;
    BaseInterface bs;
    // resTable
    private static final int TIMER = 7;
    private static final int TIME_START = 0;
    private static final int IDEXES = 1;
    private static final int COUNT = 4;

    @Override
    protected int getNumberPage() {
        return 1;
    }

    @Override
    protected Fragment setFragment(int position) {
        if (position == 0)
            return DayStatisticTime.newInstance(dayTimeStat(),strExes);
        return null;
    }

    @Override
    public void init() {
        idTday = getIntent().getLongExtra(EXTRA_ID, 0);
        bs = BaseLab.getBS(getApplicationContext());
        resTable = bs.getTdayStat(idTday);
        idExes = findExesid();
        fab.setVisibility(View.GONE);

    }

    @Override
    protected CharSequence getMyTitle(int position) {
        return null;
    }

    @Override
    protected void tabSelect(int idTab) {

    }

    @Override
    protected void fabOnClic() {
    }

    public static Intent newIntent(Context packageContext, long idTday) {
        Intent intent = new Intent(packageContext, DayStatistic.class);
        intent.putExtra(EXTRA_ID, idTday);
        return intent;
    }

    private int[] dayTimeStat() {
        int[] res = new int[idExes.length + 3];
        //время выполнения упражнения
        res[0] = resTable[0][TIMER];
        for (int i = 1; i < resTable.length; i++) {
            res[0] += resTable[i][TIMER];
            if (resTable[i][IDEXES] != resTable[i - 1][IDEXES]) {
                res[1] += resTable[i][TIME_START] - resTable[i - 1][TIME_START];
            }

        }

        int n = resTable.length - 1;
        if (resTable[n][COUNT] == -1) {
            while (resTable[n--][COUNT] == -1) ;
        }
        res[2] = resTable[n][TIME_START]  + resTable[n][TIMER] - res[1] - res[0];

        n = 3;

        for (int i : idExes) {
            int min = 0, max = 0, timer = 0;
            for (int j = 0; j < resTable.length; j++) {
                if (resTable[j][COUNT] != -1) {
                    if (resTable[j][IDEXES] == i) {
                        if (min == 0) min = resTable[j][TIME_START];
                        if (resTable[j][TIME_START] < min) min = resTable[j][TIME_START];
                        if (resTable[j][TIME_START] > max) {
                            max = resTable[j][TIME_START];
                            timer = resTable[j][TIMER];
                        }
                    }
                }
            }
            res[n++] = max + timer - min;
        }
        return res;
    }

    private int[] findExesid() {
        ArrayList<Integer> temp = new ArrayList();
        for (int i = 0; i < resTable.length; i++) {
            if (!temp.contains(resTable[i][1])) temp.add(resTable[i][1]);
        }
        int n = 0;
        int[] res = new int[temp.size()];
        for (Integer i : temp.toArray(new Integer[temp.size()])) {
            res[n++] = i;
                   }
                   n=0;
        strExes=new String[res.length];
        for(int i:res) strExes[n++]=bs.getEexes(i).getName();
        return res;
    }

}
