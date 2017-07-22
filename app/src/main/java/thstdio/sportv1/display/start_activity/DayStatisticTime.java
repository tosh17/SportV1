package thstdio.sportv1.display.start_activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import thstdio.sportv1.R;
import thstdio.sportv1.logic.date.DateLab;


/**
 * Created by shcherbakov on 24.06.2017.
 */

public class DayStatisticTime extends Fragment implements OnChartValueSelectedListener {
    private static final String EXTRA_ID = "idTday";
    private static final String EXTRA_STR = "exesName";
    int[] table;
    String[] strExes;


    private PieChart commonTime;
    private PieChart exesTime;
    TextView textCommon[], textExes[];


    public static DayStatisticTime newInstance(int[] table, String[] str) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_ID, table); //общее время, отдых между, отдых в нутри, уражнения время
        args.putSerializable(EXTRA_STR, str);
        DayStatisticTime fragment = new DayStatisticTime();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        table = getArguments().getIntArray(EXTRA_ID);
        strExes = getArguments().getStringArray(EXTRA_STR);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.day_statistic_time, container, false);
        commonTime = (PieChart) v.findViewById(R.id.chartHalfPie1);
        exesTime = (PieChart) v.findViewById(R.id.chartHalfPie2);
        textCommon = new TextView[4];
        textCommon[0] = (TextView) v.findViewById(R.id.textComonTime1);
        textCommon[1] = (TextView) v.findViewById(R.id.textComonTime2);
        textCommon[2] = (TextView) v.findViewById(R.id.textComonTime3);
        textCommon[3] = (TextView) v.findViewById(R.id.textComonTime4);


        String str[] = {"", "", ""};
        int total = 0;
        float f[] = new float[3];
        for (int i = 0; i < 3; i++) total += table[i];
        textCommon[0].setText(DateLab.parseSecondt(total, ":"));

        for (int i = 0; i < 3; i++) {
            f[i] = (float) (table[i]) / total;
            textCommon[i + 1].setText(String.format("%.1f", f[i] * 100) + "%     " + DateLab.parseSecondt(table[i], ":"));
            textCommon[i + 1].setTextColor(ColorTemplate.MATERIAL_COLORS[i]);
        }

        drawPie(commonTime, f, str, 180f, ColorTemplate.MATERIAL_COLORS);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int height = display.getHeight();  // deprecated

        int offset = (int) (height * 0.25); /* percent to move */
        commonTime.setMinimumHeight(height / 2);
        LinearLayout.LayoutParams rlParams = (LinearLayout.LayoutParams) commonTime.getLayoutParams();
        rlParams.setMargins(50, -offset / 20, 50, -offset);
        commonTime.setLayoutParams(rlParams);
        setCommonText();
        int min, max = 0, avg, imax = 0, imin = 0;
        min = total;
        total = 0;

        f = new float[table.length - 3];
        for (int i = 3; i < table.length; i++) {
            total += table[i];
            if (table[i] > max) {
                max = table[i];
                imax = i;
            }
            if (table[i] < min) {
                min = table[i];
                imin = i;
            }
        }
        avg = total / (table.length - 3);

        for (int i = 3; i < table.length; i++) {
            f[i - 3] = (float) table[i] / total;
        }
  if(imax>=3) {
      textExes = new TextView[4];
      textExes[0] = (TextView) v.findViewById(R.id.textExes1);
      textExes[1] = (TextView) v.findViewById(R.id.textExes2);
      textExes[2] = (TextView) v.findViewById(R.id.textExes3);

      textExes[0].setText(strExes[imax - 3] + " " + DateLab.parseSecondt(table[imax], ":"));
      textExes[2].setText(strExes[imin - 3] + " " + DateLab.parseSecondt(table[imin], ":"));
      textExes[1].setText(DateLab.parseSecondt(avg, ":"));

      exesTime.setHighlightPerTapEnabled(true);
      exesTime.setTouchEnabled(true);
      exesTime.setOnChartValueSelectedListener(this);
      drawPie(exesTime, f, strExes, 360, ColorTemplate.JOYFUL_COLORS);
      exesTime.setMinimumHeight(height);
      rlParams = (LinearLayout.LayoutParams) exesTime.getLayoutParams();
      rlParams.setMargins(50, -height / 5, 50, -height / 4);
       /* setLegend(exesTime, Legend.LegendVerticalAlignment.TOP
                , Legend.LegendHorizontalAlignment.RIGHT,
                Legend.LegendOrientation.VERTICAL,
                height / 10);*/

  }
  else {
      //отключить карточку
  }
        return v;
    }

    private void setCommonText() {
        int total = 0;
        for (int i = 0; i < 3; i++) total += table[i];


    }


    void drawPie(PieChart grafPie, float[] val, String[] str, float angel, int[] colorVal) {

        grafPie.setBackgroundColor(Color.TRANSPARENT);

        grafPie.setUsePercentValues(true);
        grafPie.getDescription().setEnabled(false);

        //  grafPie.setCenterTextTypeface(mTfLight);


        grafPie.setDrawHoleEnabled(true);
        grafPie.setHoleColor(Color.WHITE);

        if (angel == 180f) {
            grafPie.setTransparentCircleColor(Color.WHITE);
            grafPie.setTransparentCircleAlpha(110);

            grafPie.setHoleRadius(25f);
            grafPie.setTransparentCircleRadius(81f);

            grafPie.setDrawCenterText(true);

            grafPie.setRotationEnabled(false);
            grafPie.setHighlightPerTapEnabled(true);
        }

        grafPie.setMaxAngle(angel); // HALF CHART
        grafPie.setRotationAngle(180f);
        grafPie.setCenterTextOffset(0, -20);

        ArrayList<PieEntry> values = new ArrayList<PieEntry>();
        for (int i = 0; i < val.length; i++) {

            values.add(new PieEntry(val[i], str[i]));
        }

        PieDataSet dataSet = new PieDataSet(values, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        dataSet.setColors(colorVal);
        dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.DKGRAY);
        //data.setValueTypeface(mTfLight);
        grafPie.setData(data);

        grafPie.animateY(1400, Easing.EasingOption.EaseInOutQuad);


        // entry label styling

        grafPie.setDrawEntryLabels(false);
        grafPie.setEntryLabelColor(Color.BLUE);
        // grafPie.setEntryLabelTypeface(mTfRegular);
        grafPie.setEntryLabelTextSize(12f);
    }

    private void setLegend(PieChart grafPie, Legend.LegendVerticalAlignment vertical,
                           Legend.LegendHorizontalAlignment horizontal,
                           Legend.LegendOrientation orientation, float y) {
        Legend l = grafPie.getLegend();
        l.setVerticalAlignment(vertical);
        l.setHorizontalAlignment(horizontal);
        l.setOrientation(orientation);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(y);

    }

    /**
     * Called when a value has been selected inside the chart.
     *
     * @param e The selected Entry
     * @param h The corresponding highlight object that contains information
     */
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        PieEntry p = (PieEntry) e;
        int position=(int) (h.getX());
        Toast toast = Toast.makeText(getContext(),
                p.getLabel().toString() + System.lineSeparator() + DateLab.parseSecondt(table[3 + position], ":"), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 150);
        LinearLayout toastContainer = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(getContext());
        int[] massColor = ColorTemplate.JOYFUL_COLORS;
        int iColor=position%massColor.length;
        imageView.setBackgroundColor(massColor[iColor]);
        imageView.setMinimumWidth(30);
        imageView.setMinimumHeight(30);
        toastContainer.addView(imageView, 0);

        toast.show();
    }

    /**
     * Called when nothing has been selected or an "un-select" has been made.
     */
    @Override
    public void onNothingSelected() {

    }
}
