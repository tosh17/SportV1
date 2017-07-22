package thstdio.sportv1.display.start_activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import thstdio.sportv1.R;
import thstdio.sportv1.logic.TTren.Texes;
import thstdio.sportv1.logic.base.BaseInterface;
import thstdio.sportv1.logic.base.BaseLab;
import thstdio.sportv1.logic.date.DateLab;

/**
 * Created by shcherbakov on 21.06.2017.
 */

public class StartPageFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, StartAvtivty.Callbacks, View.OnClickListener {
    private static final String EXES_ID = "exes_id";
    private final int MAX_PODHOD = 6;
    StartAvtivty activity;
    BaseInterface bs;
    boolean[] isUpdate = new boolean[MAX_PODHOD];


    public static StartPageFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putSerializable(EXES_ID, position);
        StartPageFragment fragment = new StartPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    TextView textView[] = new TextView[6];
    LinearLayout exesCase[] = new LinearLayout[6];
    SwitchCompat swExes[] = new SwitchCompat[6];
    boolean isStart = false;
    TextView title;
    Texes tExes;

    private LinearLayout dialog;
    private LinearLayout mainContent;
    private Animation animation;
    private Button dialogOk;
    private EditText editCount;
    private EditText editWeight;
    private boolean isDialogTextChange = false;
    private static final String ARG_ID = "exes_id";
    int REQUEST_DIALOG = 0;
    int currentExes = 0;
    int exesTime;
    long tDayId;

    //timer
    private Handler mHandler = new Handler();
    private TextView text;
    int minute, secunde, timer = 0;
    boolean isRunExes = true;
    private long mTime;
    ConstraintLayout timerL;
    ImageView timerOval, fon;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int position = getArguments().getInt(ARG_ID, 0);
        bs = BaseLab.getBS(getContext());
        activity = (StartAvtivty) getActivity();
        tExes = activity.getTexes(position);
        tDayId = activity.getTday().getId();
        currentExes = tExes.getiCurrent();
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.start_page_fragment, container, false);
        title = (TextView) v.findViewById(R.id.textViewTitle);
        title.setText(tExes.getExes().getName());

        textView[0] = (TextView) v.findViewById(R.id.textViewPodhod0);
        textView[1] = (TextView) v.findViewById(R.id.textViewPodhod1);
        textView[2] = (TextView) v.findViewById(R.id.textViewPodhod2);
        textView[3] = (TextView) v.findViewById(R.id.textViewPodhod3);
        textView[4] = (TextView) v.findViewById(R.id.textViewPodhod4);
        textView[5] = (TextView) v.findViewById(R.id.textViewPodhod5);

        swExes[0] = (SwitchCompat) v.findViewById(R.id.switch0);
        swExes[1] = (SwitchCompat) v.findViewById(R.id.switch1);
        swExes[2] = (SwitchCompat) v.findViewById(R.id.switch2);
        swExes[3] = (SwitchCompat) v.findViewById(R.id.switch3);
        swExes[4] = (SwitchCompat) v.findViewById(R.id.switch4);
        swExes[5] = (SwitchCompat) v.findViewById(R.id.switch5);

        exesCase[0] = (LinearLayout) v.findViewById(R.id.podhod_case0);
        exesCase[1] = (LinearLayout) v.findViewById(R.id.podhod_case1);
        exesCase[2] = (LinearLayout) v.findViewById(R.id.podhod_case2);
        exesCase[3] = (LinearLayout) v.findViewById(R.id.podhod_case3);
        exesCase[4] = (LinearLayout) v.findViewById(R.id.podhod_case4);
        exesCase[5] = (LinearLayout) v.findViewById(R.id.podhod_case5);


        timerL = (ConstraintLayout) v.findViewById(R.id.TimerLayot);
        timerOval = (ImageView) v.findViewById(R.id.imageTimerOval);
        text = (TextView) v.findViewById(R.id.textViewTimer);
        text.setOnClickListener(this);

        mainContent = (LinearLayout) v.findViewById(R.id.mainContent);
        fon = (ImageView) v.findViewById(R.id.imageFon);

        dialog = (LinearLayout) v.findViewById(R.id.textDialog);
        editCount = (EditText) v.findViewById(R.id.editTexDialogCount);
        TextWatcher textChange = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isDialogTextChange = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        editCount.addTextChangedListener(textChange);
        editWeight = (EditText) v.findViewById(R.id.editTextDialogWeight);
        editWeight.addTextChangedListener(textChange);
        dialogOk = (Button) v.findViewById(R.id.buttonDialogOk);
        dialogOk.setOnClickListener(this);
        fitIn();
        changeFon(0, R.drawable.rectangle_rounded_some_yellow);
        return v;
    }

    /**
     * Заполняет поля описанием(Например это подход#2 min-max)
     */
    private void fitIn() {
        int iRazminka, iPodhod;
        iRazminka = tExes.getPodhod().getRazminka();
        iPodhod = tExes.getPodhod().getCount();

        for (int i = 0; i < MAX_PODHOD; i++) {
            exesCase[i].setVisibility(View.INVISIBLE);
            exesCase[i].setVisibility(View.GONE);
            swExes[i].setOnCheckedChangeListener(this);

        }
        for (int i = 0; i < iRazminka; i++) {
            exesCase[i].setVisibility(View.VISIBLE);
            textView[i].setText("Разминка   ");
        }
        for (int i = 0; i < iPodhod; i++) {
            exesCase[i + iRazminka].setVisibility(View.VISIBLE);
            textView[i + iRazminka].setText("Подход#" + (i + 1) + "  " + tExes.getPodhod().getMin()[i] + "-" + tExes.getPodhod().getMax()[i]);
        }
        timerL.setVisibility(View.GONE);
        fon.setVisibility(View.GONE);

    }

    /**
     * Called when the checked state of a compound button has changed.
     * Проверка четбокса, делаем не активным поле и помечаем что упражнение пропускается.
     *
     * @param buttonView The compound button view whose state has changed.
     * @param isChecked  The new checked state of buttonView.
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int i = 0;
        switch (buttonView.getId()) {
            case R.id.switch0:
                i = 0;
                break;
            case R.id.switch1:
                i = 1;
                break;
            case R.id.switch2:
                i = 2;
                break;
            case R.id.switch3:
                i = 3;
                break;
            case R.id.switch4:
                i = 4;
                break;
            case R.id.switch5:
                i = 5;
                break;
        }

        exesCase[i].setEnabled(isChecked);
        tExes.disExes(i, !isChecked);
        if (tExes.isDone()) done();
        else restoration();

    }


    private void update(int position, int[] iCW) {
        if (!isUpdate[position]) {
            isUpdate[position] = true;
            textView[position].append(" /" + iCW[0] + "x" + iCW[1]);
        }
    }

    /**
     * Подготавливает данные для записи в бд
     *
     * @return
     */

    public long[] getCurrentVal(int position) {
        long[] currentVal = new long[9];
        currentVal[0] = tDayId;   //id TDAy
        currentVal[1] = DateLab.now();               //TIME_START
        currentVal[2] = tExes.getExes().getId();// ID_EXES
        currentVal[3] = position;//NUMBER_PODHOD
        currentVal[4] = tExes.getExesType(position) ? 1 : 0;        //TYPE
        currentVal[5] = tExes.getPodhoVal(position)[0];        //COUNT
        currentVal[6] = tExes.getPodhoVal(position)[1];        //WEIGHT

        currentVal[8] = tExes.getPodhoVal(position)[2];        //TIMER
        if (tExes.getExes().isFreeWeight()) {
            currentVal[7] = 0;
        } else {
            currentVal[7] = 0;
        }
        return currentVal;
    }

    private void done() {
        timerL.setVisibility(View.GONE);
        for (int i = 0; i < tExes.size(); i++) {
            if (tExes.isExesDis(i)) bs.setTexesPodhod(getCurrentVal(i));
        }
        activity.exesDone();
    }

    private void changeFon(int i, int idColor) {
        exesCase[i].setBackground(getResources().getDrawable(idColor));
        //  exesCase[i].setBackgroundColor(getResources().getColor(idColor));
        //   textView[i].setBackgroundColor(getResources().getColor(idColor));
        //   swExes[i].setBackgroundColor(getResources().getColor(idColor));

    }

    @Override
    public void onResume() {
        super.onResume();
        restoration();

    }

    /**
     * востанавливает поля
     */
    private void restoration() {

        for (int i = 0; i < tExes.size(); i++) {

            int[] val = tExes.getPodhoVal(i);
            if (val[0] > -1) {
                changeFon(i, R.drawable.rectangle_rounded_some_green);
                update(i, val);
                swExes[i].setVisibility(View.GONE);
            } else {
                changeFon(i, R.drawable.rectangle_rounded_some_red);
            }
            if (tExes.isExesDis(i)) {
                if (tExes.isDone()) swExes[i].setVisibility(View.GONE);
                else swExes[i].setChecked(false);
                changeFon(i, R.drawable.rectangle_rounded_some_grey);
            }
        }

        currentExes = -1;
        if (!tExes.isDone()) { //Если упражнение не выполнено то ищем текущее
            while (tExes.getPodhoVal(++currentExes)[0] != -2) {
            }
            changeFon(currentExes, R.drawable.rectangle_rounded_some_yellow);
        } else {
            for (int i = 0; i < tExes.size(); i++) {
                swExes[i].setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void clickFab() {
        activity.hitFAB(false);
        exesTime = secunde + minute * 60;
        secunde = 0;
        minute = 0;
        mTime = SystemClock.uptimeMillis();

        if (!isStart) {
            isStart = true;
            timerL.setVisibility(View.VISIBLE);
            dialogFon(false);
            startTimer();
            animation = AnimationUtils.loadAnimation(getContext(), R.anim.timer_on);
            timerL.setAnimation(animation);
            text.setTextColor(Color.GREEN);
            isRunExes = true;
            return;
        }
        if (isRunExes) {
            animation = AnimationUtils.loadAnimation(getContext(), R.anim.timer_out);
            timerL.setAnimation(animation);
            dialog.setVisibility(View.VISIBLE);
            text.setTextColor(Color.YELLOW);
            changeFon(currentExes, R.drawable.rectangle_rounded_some_green);
            swExes[currentExes].setVisibility(View.GONE);
            animation = AnimationUtils.loadAnimation(getContext(), R.anim.dialog_on);
            dialog.startAnimation(animation);

        } else {
            dialogFon(false);
            mainContent.setEnabled(false);
            text.setTextColor(Color.GREEN);
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.timer_on);
            timerL.setAnimation(animation);
        }
        isRunExes = !isRunExes;
    }


    //Timer----------------------------------------------------------------------------------------
    private void startTimer() {
        mTime = SystemClock.uptimeMillis();
        text.setText("00:00");
        mHandler.removeCallbacks(timeUpdaterRunnable);
        // Добавляем Runnable-объект timeUpdaterRunnable в очередь
        // сообщений, объект должен быть запущен после задержки в 100 мс
        mHandler.postDelayed(timeUpdaterRunnable, 1);
    }

    private float speed = (float) 0.5;
    String strTime;
    private Runnable timeUpdaterRunnable = new Runnable() {
        public void run() {
            mHandler.postDelayed(this, (long) (speed * 1000));
            secunde = (int) ((SystemClock.uptimeMillis() - mTime) / 1000);
            if (secunde >= 60) {
                minute = (int) (secunde / 60);
                secunde = secunde % 60;
            }
            strTime = (minute < 10 ? "0" + minute : minute) + ":" + (secunde < 10 ? "0" + secunde : secunde);
            text.setText(strTime);
        }
    };


    // Menu-----------------------------------------------------------------------------------------
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_start_exes, menu);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        FragmentManager manager = getFragmentManager();

        StartExesHistoryDialog dialog = StartExesHistoryDialog.newInstance(activity.getTday().getId(),tExes.getExes().getId());
            dialog.setTargetFragment(StartPageFragment.this, REQUEST_DIALOG);
            dialog.show(manager, "");

        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.buttonDialogOk) {
            if (isDialogTextChange) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
                isDialogTextChange=false;
            }
             dialogFon(true);
            activity.hitFAB(true);
            animation = AnimationUtils.loadAnimation(getContext(), R.anim.dialog_out);
            dialog.startAnimation(animation);
            int[] iCW = new int[2];
            if (!editCount.getText().toString().equals(""))
                iCW[0] = Integer.parseInt(editCount.getText().toString());
            if (!editWeight.getText().toString().equals(""))
                iCW[1] = Integer.parseInt(editWeight.getText().toString());
            tExes.addExex(iCW[0], iCW[1], exesTime);
            BaseInterface bs = BaseLab.getBS(getContext());
            bs.setTexesPodhod(getCurrentVal(currentExes));
            update(currentExes, iCW);
            if (tExes.isDone()) done();
            else {
                currentExes++;
                while (tExes.isExesDis(currentExes)) currentExes++;
                changeFon(currentExes, R.drawable.rectangle_rounded_some_yellow);
            }
            return;
        }
        if (id == R.id.textViewTimer) {
            clickFab();
            return;
        }
    }

    private void dialogFon(boolean enable) {
        if (enable) {
            fon.setVisibility(View.GONE);
        } else {
            fon.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < MAX_PODHOD; i++) {
            swExes[i].setEnabled(enable);
        }
    }
}
