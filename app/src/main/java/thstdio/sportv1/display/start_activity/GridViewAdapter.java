package thstdio.sportv1.display.start_activity;

/**
 * Created by shcherbakov on 13.07.2017.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

import thstdio.sportv1.R;

public class GridViewAdapter extends ArrayAdapter<String> {

        Context mContext;
        String[] str;

        // Конструктор
        public GridViewAdapter(Context context, int textViewResourceId, String[] str) {
            super(context, textViewResourceId,str);
            // TODO Auto-generated constructor stub
            this.mContext = context;
            this.str=str;
        }



    @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            TextView label = (TextView) convertView;

            if (convertView == null) {
                convertView = new TextView(mContext);
                label = (TextView) convertView;
            }
            label.setText(str[position]);
           // label.setBackground(mContext.getResources().getDrawable(R.drawable.rectangle_rounded_some_grey));
             setCentr(label);
            return (convertView);
        }
        @TargetApi(17)
        private void setCentr(TextView label){
            if (Utils.getSDKInt() >= 17)  label.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }

        // возвращает содержимое выделенного элемента списка
        public String getItem(int position) {
            return str[position];
        }

    }

