package thstdio.sportv1.display.start_activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
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
 * Created by shcherbakov on 11.07.2017.
 */

public class StartExesHistoryDialog extends DialogFragment {

    private static final String ARG_ID_PROG ="id_prog" ;
    private static String ARG_ID_EXES = "id_exes";
    private static String ARG_ID_TDAY = "id_tday";
    private LineChart grafic;
    private GridView grid;
    private int max=0,min=1000;
    private TextView title,dateTxt;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.start_exes_dialog, null);

        grafic = (LineChart) v.findViewById(R.id.lineChart);
        grid=(GridView)  v.findViewById(R.id.ExesTable);
        title=(TextView) v.findViewById(R.id.textTitle);
        dateTxt=(TextView) v.findViewById(R.id.textDate);
        loadHistory();
        return new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog)
                .setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ///////////////////////////////////////////////
                    }
                })
                .create();
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
        long id_day = getArguments().getLong(ARG_ID_TDAY);
        int id_exes = getArguments().getInt(ARG_ID_EXES);
        int mainValue = bs.getEexes(id_exes).getMainValue();
        title.setText(bs.getEexes(id_exes).getName());

        String[] lastExes={"type","count","weight","timer"};
        ArrayList<int[]> temp = bs.findLastTexes(id_day,id_exes);

        String[] gridStr=new String[(temp.size()+1)*4];
        dateTxt.setText(temp.get(temp.size()-1)[2]+"/"+temp.get(temp.size()-1)[1]+"/"+temp.get(temp.size()-1)[0]);
        gridStr[1]=getResources().getString(R.string.count_str);
        gridStr[2]=getResources().getString(R.string.weight_str);
        gridStr[3]=getResources().getString(R.string.timer_str);
        for(int i=0;i<temp.size()-1;i++){
            gridStr[(i+1)*4]=temp.get(i)[0]==0?getResources().getString(R.string.razminka_str):getResources().getString(R.string.podhod_str);

                gridStr[(i+1)*4+1]="---";
                gridStr[(i+1)*4+2]="---";
                gridStr[(i+1)*4+3]="---";
            if(temp.get(i)[1]!=-1) {
                switch(mainValue){
                         case 1:  //количество
                    gridStr[(i+1)*4+1]=new Integer(temp.get(i)[1]).toString();
                    gridStr[(i+1)*4+3]=new Integer(temp.get(i)[3]).toString();
                    break;
                    case 2://время
                        gridStr[(i+1)*4+3]=new Integer(temp.get(i)[3]).toString();
                        break;
                    default: //вес+количество (все)
                        gridStr[(i+1)*4+1]=new Integer(temp.get(i)[1]).toString();
                        gridStr[(i+1)*4+2]=new Integer(temp.get(i)[2]).toString();
                        gridStr[(i+1)*4+3]= DateLab.parseSecondt(temp.get(i)[3],":");
                        break;
            }}


        }
        GridViewAdapter mAdapter = new GridViewAdapter(getContext(),
                android.R.layout.simple_list_item_1,gridStr);
        grid.setAdapter(mAdapter);
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

    public static StartExesHistoryDialog newInstance(long day,int exes) {
        Bundle args = new Bundle();

        args.putSerializable(ARG_ID_EXES, exes);
        args.putSerializable(ARG_ID_TDAY, day);
        StartExesHistoryDialog fragment = new StartExesHistoryDialog();
        fragment.setArguments(args);
        return fragment;
    }
}
