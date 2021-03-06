package thstdio.sportv1.display.start_activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayout;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

import thstdio.sportv1.R;
import thstdio.sportv1.logic.base.BaseInterface;
import thstdio.sportv1.logic.base.BaseLab;
import thstdio.sportv1.logic.date.DateLab;

/**
 * Created by shcherbakov on 26.06.2017.
 */

public class DayStatisticExes extends Fragment {


    private static final String ARG_ID_EXES ="id_exes" ;
    private static final String ARG_ID_TDAY = "id_tday";
    private long id_tday;
    private int id_exes;
    private LineChart grafic;
    private GridLayout table;
    int wTable;
    private int max=0,min=1000;
    public static DayStatisticExes newInstance(long day, int exes) {
        Bundle args = new Bundle();

        args.putSerializable(ARG_ID_EXES, exes);
        args.putSerializable(ARG_ID_TDAY, day);
        DayStatisticExes fragment = new DayStatisticExes();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id_tday = getArguments().getLong(ARG_ID_TDAY);
        id_exes = getArguments().getInt(ARG_ID_EXES);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.day_statistic_exes, container, false);
        grafic = (LineChart) v.findViewById(R.id.lineChart);
        table =(GridLayout)  v.findViewById(R.id.table);
        table.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {

            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight,
                                       int oldBottom) {
                // its possible that the layout is not complete in which case
                // we will get all zero values for the positions, so ignore the event
                if (left == 0 && top == 0 && right == 0 && bottom == 0) {
                    return;
                }
                 wTable=right-left;
                 tableChangeSize();
                // Do what you need to do with the height/width since they are now set
            }
        });
        loadHistory();
        return v;
    }

    private void tableChangeSize() {
       for(int i=0;i<table.getChildCount();i++){
           table.getChildAt(i).setMinimumWidth(wTable/4);
       }
    }

    private void showGraph(LineChart mChart) {
        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        mChart.setPinchZoom(true);

        // set an alternative background color
        // mChart.setBackgroundColor(Color.GRAY);

        // x-axis limit line
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);


        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.setAxisMaximum(max);
        leftAxis.setAxisMinimum(min);
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);
        // add data


    }
    private void setData(LineChart mChart, ArrayList<int[]> val, int mainValue) {
        ArrayList<Entry> values = new ArrayList<Entry>();
        for (int i = 0; i < val.size(); i++) {
            values.add(new Entry(i, val.get(i)[1], null));
        }
        LineDataSet set1;

        // create a dataset and give it a type
        set1 = new LineDataSet(values, "");

        set1.setDrawIcons(false);

        // set the line to be drawn like this "- - - - - -"
        set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.RED);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(1f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(0f);

        set1.setDrawFilled(true);
        set1.setFormLineWidth(1f);
        //set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        set1.setFormSize(15.f);

        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setCubicIntensity(0.2f);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(dataSets);
        // set data
        mChart.setData(data);
        mChart.animateX(2500);
        Legend l = mChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.EMPTY);
    }


    private void loadHistory() {

        BaseInterface bs = BaseLab.getBS(getContext());
         int mainValue = bs.getEexes(id_exes).getMainValue();


        String[] lastExes={"type","count","weight","timer"};
        ArrayList<int[]> temp = bs.findTexes(id_tday,id_exes);

        String[] gridStr=new String[(temp.size())*4];
        for(int i=0;i<temp.size();i++){
            gridStr[(i)*4]=temp.get(i)[0]==0?getResources().getString(R.string.razminka_str):getResources().getString(R.string.podhod_str);

            gridStr[(i)*4+1]="---";
            gridStr[(i)*4+2]="---";
            gridStr[(i)*4+3]="---";
            if(temp.get(i)[1]!=-1) {
                switch(mainValue){
                    case 1:  //количество
                        gridStr[(i)*4+1]=new Integer(temp.get(i)[1]).toString();
                        gridStr[(i)*4+3]=new Integer(temp.get(i)[3]).toString();
                        break;
                    case 2://время
                        gridStr[(i)*4+3]=new Integer(temp.get(i)[3]).toString();
                        break;
                    default: //вес+количество (все)
                        gridStr[(i)*4+1]=new Integer(temp.get(i)[1]).toString();
                        gridStr[(i)*4+2]=new Integer(temp.get(i)[2]).toString();
                        gridStr[(i)*4+3]= DateLab.parseSecondt(temp.get(i)[3],":");
                        break;
                }}


        }

        for(int i=0;i<gridStr.length;i++){
            TextView text=new TextView(getContext());
            text.setText(gridStr[i]);
            text.setGravity(Gravity.CENTER);
            table.addView(text);

        }

        ArrayList<int[]> val = bs.findLastListExes(id_exes, 100, false);
        val=reverce(val,mainValue);
        showGraph(grafic);
        switch (mainValue) {
            case 0:// вес количество
            {
                setData(grafic, val, 1);
                break;
            }
            case 1: //количество
            {
                setData(grafic, val, 0);
                break;
            }
            case 2: //время
            {
                setData(grafic, val, 2);
                break;
            }
        }
    }

    private ArrayList<int[]> reverce(ArrayList<int[]> temp, int mainValue) {
        ArrayList<int[]> newTemp=new ArrayList<>();
        int index=0;
        switch (mainValue) {
            case 0:// вес количество
            {
                index=1;
                break;
            }
            case 1: //количество
            {
                index=0;
                break;
            }
            case 2: //время
            {
                index=2;
                break;
            }}
        min=temp.get(0)[index];
        for(int i=temp.size()-1;i>0;i--)
        {
            newTemp.add(temp.get(i));
            max=Math.max(max,temp.get(i)[index]);
            min=Math.min(min,temp.get(i)[index]);
        }
        return newTemp;
    }
}
