package thstdio.sportv1.display.test;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import thstdio.sportv1.R;
import thstdio.sportv1.logic.ETren.Eday;
import thstdio.sportv1.logic.ETren.Eprog;
import thstdio.sportv1.logic.TTren.Tday;
import thstdio.sportv1.logic.TTren.Texes;
import thstdio.sportv1.logic.base.BaseInterface;
import thstdio.sportv1.logic.base.SqliteBS;
import thstdio.sportv1.logic.date.DateLab;

/**
 * Created by shcherbakov on 29.06.2017.
 */

public class Generator extends AppCompatActivity {
    private Handler mHandler = new Handler();
    private long mTime = 0L;
    BaseInterface bs;
    TextView text, text1, text2;

    final Random random = new Random();

    Eprog prog;
    int currentProg = 5;
    int currentDay = 1;
    Tday tday;
    Texes texes;
    int currentTexes = 0;
    int currentPodhod = 0;
    ArrayList<int[]> last;
    int count;
    int weight;
    int timer = 10;
    int maxC, maxW;
    int rCurrent;

    int speed=1;
    FloatingActionButton fab;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        text = (TextView) findViewById(R.id.textView3);
        text1 = (TextView) findViewById(R.id.textView4);
        text2 = (TextView) findViewById(R.id.textView5);
        bs = SqliteBS.getInstance(getApplicationContext());
        prog = bs.getEprog(currentProg);

         fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTday(newDay(++currentDay));
                pause();
                fab.setEnabled(false);
            }

        });
    }

    private void pause() {
        mTime = SystemClock.uptimeMillis();
        mHandler.removeCallbacks(timeUpdaterRunnable);
        // Добавляем Runnable-объект timeUpdaterRunnable в очередь
        // сообщений, объект должен быть запущен после задержки в 100 мс
        mHandler.postDelayed(timeUpdaterRunnable, 100);
    }

    private Runnable timeUpdaterRunnable = new Runnable() {
        public void run() {

            int mainValue = texes.getExes().getMainValue();

            if (getRboolean(70) && (currentPodhod != 0 || currentPodhod != rCurrent)) {
                texes.disExes(currentPodhod++, true);

            } else {
                makeNewValue();
                timer = getRint(speed, 3*speed);
                texes.addExex(count, weight, timer);
                bs.setTexesPodhod(getCurrentVal(currentPodhod));
                currentPodhod++;
            }
            int delay = (timer) * 1;
            text.setText(texes.getExes().getName() + "::" + currentTexes);
            text1.setText("" + currentPodhod + "  delay=" + delay);
            text2.setText("##" + currentDay);
            delay += getRint(speed, 3*speed) * 1;//отдых
            if (texes.isDone()) {
                getNewTexes();
                delay += getRint(speed*2, speed*4) * 1; //доп отдых между подходами
            }
            mHandler.postDelayed(this, delay);
        }
    };

    private void makeNewValue() {
        int t, xc = 1, xw = 0;
        ///разминка
        if (currentPodhod < rCurrent) {
            weight = (int) (maxW * 0.7);
            count = texes.getPodhod().getMax()[currentPodhod];
        } else {
            count=maxC+getRint(0, 3)-(currentPodhod - rCurrent)*(getRint(0, 2));
            count=count<2?2:count;
            if(count>texes.getPodhod().getMax()[currentPodhod- rCurrent]){
                count=(texes.getPodhod().getMax()[currentPodhod- rCurrent]+texes.getPodhod().getMin()[currentPodhod- rCurrent])/2;
                maxC=count;
                maxW=maxW+getRint(5, 10);
            }
            weight=maxW;
        }
        }


    private void getNewTexes() {
        if (tday.iSDayDone()) {
            bs.endTday(tday, DateLab.now());
            startTday(newDay(++currentDay));

        } else {
            for (int i = 0; i < texes.size(); i++) {
                if (texes.isExesDis(i)) bs.setTexesPodhod(getCurrentVal(i));
            }

            currentTexes++;
            startTEaes();
        }
    }

    public long[] getCurrentVal(int position) {
        long[] currentVal = new long[9];
        currentVal[0] = tday.getId();   //id TDAy
        currentVal[1] = DateLab.now();               //TIME_START
        currentVal[2] = texes.getExes().getId();// ID_EXES
        currentVal[3] = position;//NUMBER_PODHOD
        currentVal[4] = texes.getExesType() ? 1 : 0;        //TYPE
        currentVal[5] = texes.getPodhoVal(position)[0];        //COUNT
        currentVal[6] = texes.getPodhoVal(position)[1];        //WEIGHT

        currentVal[8] = texes.getPodhoVal(position)[2];        //TIMER
        if (texes.getExes().isFreeWeight()) {
            currentVal[7] = 0;
        } else {
            currentVal[7] = 0;
        }
        return currentVal;
    }

    private void startTday(Eday day) {
        tday = new Tday(day);
        tday.setId(DateLab.now());
        bs.startTday(tday);
        currentTexes = 0;
        currentPodhod = 0;
        startTEaes();

    }

    private void startTEaes() {
        texes = tday.getTexes(currentTexes);
        last = bs.findLastTexes(tday.getId(), texes.getExes().getId());
        maxC = 0;
        maxW = 0;
        rCurrent = texes.getPodhod().getRazminka();
        currentPodhod = 0;
        for (int i = 0; i < last.size() - 1; i++) {
            if (last.get(i)[0] != 0) {
                maxW = Math.max(maxW, last.get(i)[2]);
                maxC = Math.max(maxC, last.get(i)[1]);
            }
        }
    }


    private Eday newDay(int iteration) {
        int number;
        number = iteration % prog.getNumberOfDay();
        return prog.getDay(number);

    }

    private boolean getRboolean(int i) {
        if (random.nextInt(100) > i) return true;
        return false;
    }

    private int getRint(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        mHandler.removeCallbacks(timeUpdaterRunnable);
        super.onBackPressed();
    }
}
