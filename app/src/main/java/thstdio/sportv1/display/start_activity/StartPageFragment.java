package thstdio.sportv1.display.start_activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import thstdio.sportv1.R;
import thstdio.sportv1.logic.TTren.Texes;
import thstdio.sportv1.logic.base.BaseInterface;
import thstdio.sportv1.logic.base.BaseLab;
import thstdio.sportv1.logic.date.DateLab;

/**
 * Created by shcherbakov on 21.06.2017.
 */

public class StartPageFragment extends Fragment implements  CompoundButton.OnCheckedChangeListener, View.OnClickListener{
    private static final String EXES_ID ="exes_id" ;
    StartAvtivty activity;
    BaseInterface bs;
    int tempC=-1,tempW=-1;
    public static StartPageFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putSerializable(EXES_ID, position);
        StartPageFragment fragment = new StartPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    TextView textView[] = new TextView[6];
    LinearLayout exesCase[] = new LinearLayout[6];
    Switch swExes[] = new Switch[6];
    Chronometer chronometer;
    boolean isChronometr = true;
    TextView title;
    Texes tExes;
    private static final String ARG_ID = "exes_id";
    int REQUEST_DIALOG=0;
    int currentExes =0;
    int exesTime;
    long tDayId;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int position = getArguments().getInt(ARG_ID, 0);
        bs = BaseLab.getBS(getContext());
        activity = (StartAvtivty) getActivity();
        tExes=activity.getTexes(position);

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

        swExes[0] = (Switch) v.findViewById(R.id.switch0);
        swExes[1] = (Switch) v.findViewById(R.id.switch1);
        swExes[2] = (Switch) v.findViewById(R.id.switch2);
        swExes[3] = (Switch) v.findViewById(R.id.switch3);
        swExes[4] = (Switch) v.findViewById(R.id.switch4);
        swExes[5] = (Switch) v.findViewById(R.id.switch5);

        exesCase[0] = (LinearLayout) v.findViewById(R.id.podhod_case0);
        exesCase[1] = (LinearLayout) v.findViewById(R.id.podhod_case1);
        exesCase[2] = (LinearLayout) v.findViewById(R.id.podhod_case2);
        exesCase[3] = (LinearLayout) v.findViewById(R.id.podhod_case3);
        exesCase[4] = (LinearLayout) v.findViewById(R.id.podhod_case4);
        exesCase[5] = (LinearLayout) v.findViewById(R.id.podhod_case5);
        chronometer = (Chronometer) v.findViewById(R.id.chronometer);
        fitIn();
        changeFon(0,R.color.colorPYellow);
        return v;
    }

    /**
     * Заполняет поля описанием(Например это подход#2 min-max)
     */
    private void fitIn() {
        int iRazminka, iPodhod;
        iRazminka = tExes.getPodhod().getRazminka();
        iPodhod = tExes.getPodhod().getCount();

        for (int i = 0; i < 6; i++) {
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
        chronometer.setOnClickListener(this);

    }

    /**
     * Called when the checked state of a compound button has changed.
     *Проверка четбокса, делаем не активным поле и помечаем что упражнение пропускается.
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
      /*  if (!isChecked) {
            changeFon(i,R.color.colorPGrey);

        }
        if (isChecked) {
            int idCollor;
            if(i>currentExes) {idCollor=R.color.colorPRed;}
            else{idCollor=R.color.colorPYellow;}
            changeFon(i,idCollor);
            } */
        exesCase[i].setEnabled(isChecked);
        tExes.disExes(i,!isChecked);
        if(tExes.isDone()) done();
        else restoration();

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.chronometer) {
            if (isChronometr) {
                isChronometr = !isChronometr;
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                chronometer.setTextColor(Color.GREEN);
                tDayId = activity.getTday().getId();

            } else {
                isChronometr = !isChronometr;
                String[] strTime=chronometer.getText().toString().split(":");
                exesTime=Integer.parseInt(strTime[0])*60+Integer.parseInt(strTime[1]);
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                chronometer.setTextColor(Color.RED);
                changeFon(currentExes,R.color.colorPGreen);

                createDialog(tempC,tempW);
                swExes[currentExes].setVisibility(View.GONE);


            }
        }
    }

    private void createDialog(int c,int w) {
        FragmentManager manager = getFragmentManager();
        StartFragmentDialog dialog = StartFragmentDialog.newInstance(c,w);
        dialog.setTargetFragment(StartPageFragment.this,REQUEST_DIALOG);
        dialog.show(manager, "");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_CANCELED) {createDialog(tempC,tempW);;return;}
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DIALOG) {
            int[] iCW =  data.getIntArrayExtra(StartFragmentDialog.SEND_EXES);

            tExes.addExex(iCW[0],iCW[1],exesTime);
            tempC=iCW[0];
            tempW=iCW[1];
            BaseInterface bs= BaseLab.getBS(getContext());
            bs.setTexesPodhod(getCurrentVal(currentExes));

            update(currentExes,iCW);
            if(tExes.isDone()) done();
            else {
                currentExes++;
                while (tExes.isExesDis(currentExes)) currentExes++;
                changeFon(currentExes, R.color.colorPYellow);
            }
        }
    }

    private void update(int position,int[] iCW) {
    textView[position].append(" /"+iCW[0]+"x"+iCW[1]);
    }

    /**
     * Подготавливает данные для записи в бд
     * @return
     */

    public long[] getCurrentVal(int position) {
        long[] currentVal=new long[9];
        currentVal[0]= tDayId;   //id TDAy
        currentVal[1]=DateLab.now();               //TIME_START
        currentVal[2]=tExes.getExes().getId();// ID_EXES
        currentVal[3]=position;//NUMBER_PODHOD
        currentVal[4]=tExes.getExesType()?1:0;        //TYPE
        currentVal[5]=tExes.getPodhoVal(position)[0];        //COUNT
        currentVal[6]=tExes.getPodhoVal(position)[1];        //WEIGHT

        currentVal[8]=tExes.getPodhoVal(position)[2];        //TIMER
        if(tExes.getExes().isFreeWeight() ) {
            currentVal[7]=0;
        }
        else{
            currentVal[7]=0;
        }
        return currentVal;
    }
    private void done(){
        chronometer.setVisibility(View.GONE);
        for(int i=0;i<tExes.size();i++){
            if(tExes.isExesDis(i)) bs.setTexesPodhod(getCurrentVal(i));
        }
        activity.exesDone();
    }
    private void changeFon(int i,int idColor){
        exesCase[i].setBackgroundColor(getResources().getColor(idColor));
        textView[i].setBackgroundColor(getResources().getColor(idColor));
        swExes[i].setBackgroundColor(getResources().getColor(idColor));

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
        for(int i=0;i<tExes.size();i++){

            int[] val=tExes.getPodhoVal(i);
            if (val[0]>-1) {
                changeFon(i,R.color.colorPGreen);
                update(i,val);
                swExes[i].setVisibility(View.GONE);
                           }
               else {changeFon(i,R.color.colorPRed);}
            if(tExes.isExesDis(i)) {
                swExes[i].setChecked(false);
                changeFon(i,R.color.colorPGrey);
            }
                }

        currentExes=-1;
        while(tExes.getPodhoVal(++currentExes)[0]!=-2){
                    }
        changeFon(currentExes,R.color.colorPYellow);
    }
}
