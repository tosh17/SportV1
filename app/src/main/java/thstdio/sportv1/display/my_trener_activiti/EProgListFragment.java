package thstdio.sportv1.display.my_trener_activiti;

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

import java.util.List;

import thstdio.sportv1.R;
import thstdio.sportv1.display.abstract_package.MyRes;
import thstdio.sportv1.logic.base.BaseInterface;
import thstdio.sportv1.logic.base.BaseLab;

/**
 * Created by shcherbakov on 02.06.2017.
 */

public class EProgListFragment extends Fragment {
    Adapter adapter;
    RecyclerView mExesRecyclerView;

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
        BaseInterface bs = BaseLab.getBS(getContext());

        List<String> list = bs.getMyProgList();

        if (adapter == null) {
            adapter = new EProgListFragment.Adapter(list);
            mExesRecyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    //Holder
    private class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mExesTextView;
        public ImageView imageView;
        int position;

        public Holder(View itemView) {
            super(itemView);
            mExesTextView = (TextView) itemView.findViewById(R.id.textViewList);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(this);
        }

        public void bindHolder(int position, String prog) {
            String progs[] = prog.split("::");
            mExesTextView.setText(progs[0]);
            this.position = Integer.parseInt(progs[1]);
            imageView.setImageResource(MyRes.getResTypeProg(progs[2]));
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            Intent intent = EdayPage.newIntent(getActivity(), position);
            startActivity(intent);
        }

    }


    // Adapter

    private class Adapter extends RecyclerView.Adapter<EProgListFragment.Holder> {

        private List<String> mProg;

        public Adapter(List<String> exes) {
            mProg = exes;
        }

        @Override
        public EProgListFragment.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.prog_list_item, parent, false);
            return new EProgListFragment.Holder(view);
        }

        @Override
        public void onBindViewHolder(EProgListFragment.Holder holder, int position) {
            String prog = mProg.get(position);
            holder.bindHolder(position, prog);
        }

        @Override
        public int getItemCount() {
            return mProg.size();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}
