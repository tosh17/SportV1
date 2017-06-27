package thstdio.sportv1.display.statistic_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import thstdio.sportv1.R;
import thstdio.sportv1.display.abstract_package.MyRes;

import thstdio.sportv1.display.my_trener_activiti.EdayPage;
import thstdio.sportv1.display.start_activity.DayStatistic;
import thstdio.sportv1.logic.ETren.Eprog;
import thstdio.sportv1.logic.TTren.Tday;
import thstdio.sportv1.logic.base.BaseInterface;
import thstdio.sportv1.logic.base.BaseLab;
import thstdio.sportv1.logic.date.DateLab;

/**
 * Created by shcherbakov on 27.06.2017.
 */

public class StaticListFragment extends Fragment {

    Adapter adapter;
    RecyclerView mExesRecyclerView;
    BaseInterface bs;
    public static StaticListFragment newInstance() {
        Bundle args = new Bundle();
        StaticListFragment fragment = new StaticListFragment();
       // fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_recycler_view, container, false);
        mExesRecyclerView = (RecyclerView) v
                .findViewById(R.id.activity_recycler_view);
        mExesRecyclerView.setLayoutManager(new LinearLayoutManager
                (getActivity()));
        updateUI();
        return v;
    }
    private void updateUI() {
        bs = BaseLab.getBS(getContext());

        List<String[]> list = bs.getStatList();
        list=reformat(list);
        if (adapter == null) {
            adapter = new Adapter(list);
            mExesRecyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private List<String[]>  reformat( List<String[]> list) {
        List<String[]> newlist= new ArrayList<>();
        for(String[] s:list) {
            String[] temp=new String[5];
            temp[0]=s[0];
            temp[4]=s[3];
            Eprog t=bs.getEprog(Integer.parseInt(s[1]));
            temp[1]= DateLab.parceDate(Long.parseLong(s[0]));
            temp[2]=t.getName();
            temp[3]=t.getDay(Integer.parseInt(s[2])-1).getDescription();
            newlist.add(temp);
        }
        Collections.sort(newlist, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                if(Long.parseLong(o1[0])>Long.parseLong(o2[0])) return -1;
                return 1 ;
            }
        });
        return newlist;
    }

    //Holder
    private class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView progName,dayName,totalTime,date;
        int position;
        long idDay;

        public Holder(View itemView) {
            super(itemView);
            progName = (TextView) itemView.findViewById(R.id.textProgName);
            dayName = (TextView) itemView.findViewById(R.id.textDayName);
            totalTime = (TextView) itemView.findViewById(R.id.textTotalTime);
            date = (TextView) itemView.findViewById(R.id.textDate);
            itemView.setOnClickListener(this);
        }

        public void bindHolder(int position, String[] strVal) {
            idDay=Long.parseLong(strVal[0]);
            date.setText(strVal[1]);
            progName.setText(strVal[2]);
            dayName.setText(strVal[3]);
            totalTime.setText(strVal[4]);


        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            Intent intent = DayStatistic.newIntent(getContext(),idDay);
            startActivity(intent);
        }

    }


    // Adapter

    private class Adapter extends RecyclerView.Adapter<StaticListFragment.Holder> {

        private List<String[]> tDay;

        public Adapter(List<String[]> strVals) {
            tDay = strVals;
        }

        @Override
        public StaticListFragment.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.statistic_list_item, parent, false);
            return new StaticListFragment.Holder(view);
        }

        @Override
        public void onBindViewHolder(StaticListFragment.Holder holder, int position) {
            String[] strVal = tDay.get(position);
            holder.bindHolder(position, strVal);
        }

        @Override
        public int getItemCount() {
            return tDay.size();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}
