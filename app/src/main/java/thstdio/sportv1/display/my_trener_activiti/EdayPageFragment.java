package thstdio.sportv1.display.my_trener_activiti;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import thstdio.sportv1.R;
import thstdio.sportv1.display.abstract_package.MyAsset;
import thstdio.sportv1.display.settings_activiti.SettingListExes;
import thstdio.sportv1.display.start_activity.StartAvtivty;
import thstdio.sportv1.logic.ETren.Eday;
import thstdio.sportv1.logic.base.BaseInterface;
import thstdio.sportv1.logic.base.BaseLab;


/**
 * Created by shcherbakov on 03.06.2017.
 */

public class EdayPageFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener, EdayPage.Callbacks {
    private static final String PROG_ID = "prog_id";
    private static final String DAY_ID = "day_id";

    public MyAsset myAsset;
    SharedPreferences prefs;
    BaseInterface bs;
    EdayPage activity;

    private int setting_text_size = 14;
    private int setting_icon_size = 144;
    Eday day;

    private boolean isSelect = false;
    public static boolean selectItem[];
    public static int countSelect = 0;

    private Menu mMenu;


    public static EdayPageFragment newInstance(int idProg, int position) {
        Bundle args = new Bundle();
        args.putSerializable(DAY_ID, position);
        args.putSerializable(PROG_ID, idProg);
        EdayPageFragment fragment = new EdayPageFragment();
        fragment.setArguments(args);
        return fragment;
    }


    EdayPageFragment.Adapter adapter;
    RecyclerView mExesRecyclerView;
    int idProg, idDay;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        bs = BaseLab.getBS(getContext());
        activity = (EdayPage) getActivity();
        activity.fabChangeImage(0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myAsset = new MyAsset(getContext());

        View v = inflater.inflate(R.layout.activity_recycler_view, container, false);
        mExesRecyclerView = (RecyclerView) v
                .findViewById(R.id.activity_recycler_view);
        mExesRecyclerView.setLayoutManager(new LinearLayoutManager
                (getActivity()));
        if (getArguments() != null) {
            idProg = getArguments().getInt(PROG_ID);
            idDay = getArguments().getInt(DAY_ID);
        }
        prefs =
                PreferenceManager.getDefaultSharedPreferences(getContext());
        prefs.registerOnSharedPreferenceChangeListener(this);

        BaseInterface bs = BaseLab.getBS(getContext());
        UpdateSetting(prefs);

        return v;
    }

    private void updateUI() {
        if (activity != null) {
            changeTab(activity.currentPage());
        }
        day = bs.getEday(idProg, idDay + 1);

        List<Eday.EdayList> list = day.getList();
        selectItem = new boolean[list.size()];
        if (adapter == null) {
            adapter = new EdayPageFragment.Adapter(list);
            mExesRecyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * Called when a shared preference is changed, added, or removed. This
     * may be called even if a preference is set to its existing value.
     * <p>
     * <p>This callback will be run on your main thread.
     *
     * @param sharedPreferences The {@link SharedPreferences} that received
     *                          the change.
     * @param key               The key of the preference that was changed, added, or
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        UpdateSetting(sharedPreferences);
    }

    private void UpdateSetting(SharedPreferences prefs) {

        String str = prefs.getString("list_exes_icon_size", "default");

        //Размер иконок
        if (str.equals(getResources().getString(R.string.setting_exes_list_icon_size_item1))) {
            setting_icon_size = (int) (144 * 1.25);
        } else if (str.equals(getResources().getString(R.string.setting_exes_list_icon_size_item2))) {
            setting_icon_size = 144;
        } else if (str.equals(getResources().getString(R.string.setting_exes_list_icon_size_item3))) {
            setting_icon_size = (int) (144 * 0.75);
        } else if (str.equals(getResources().getString(R.string.setting_exes_list_icon_size_item4))) {
            setting_icon_size = 0;
        }
        //Размер шрифта
        str = prefs.getString("list_exes_text_size", "default");
        if (str.equals(getResources().getString(R.string.setting_exes_list_text_size_item1))) {
            setting_text_size = 18;
        } else if (str.equals(getResources().getString(R.string.setting_exes_list_text_size_item2))) {
            setting_text_size = 16;
        } else if (str.equals(getResources().getString(R.string.setting_exes_list_text_size_item3))) {
            setting_text_size = 14;
        }

        myAsset.setStyleIcon(prefs.getString("list_exes_icon_style", "default"));
        updateUI();
    }

    //callback

    public void addDayExes() {

        Intent intent = EexesAddActivity.newIntent(getContext(), day.getIdProg(), day.getNumberOfDay());
        startActivity(intent);
    }

    @Override
    public void clickFab() {
        if (isSelect)  addDayExes();
        else {
                       Intent intent= StartAvtivty.newIntent(getContext(),idProg);
            startActivity(intent);
            getActivity().finish();
        }
    }

    @Override
    public void changeTab(int numberTab) {
        Eday tDay = bs.getEday(idProg, numberTab + 1);
        activity.getSupportActionBar().setSubtitle(tDay.getDescription());


    }


    //Holder
    private class Holder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {

        public TextView mExesTextView;
        public ImageView mImageView;
        public CheckBox mCheckBox;
        int position;

        public Holder(View itemView) {
            super(itemView);
            mExesTextView = (TextView) itemView.findViewById(R.id.textViewListExes);
            mImageView = (ImageView) itemView.findViewById(R.id.imageViewExes);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.checkBoxExes);
            mCheckBox.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void bindHolder(int position, Eday.EdayList exes) {
            Drawable d = myAsset.getIcon(exes.getExes().getId());
            mImageView.setImageDrawable(d);
            if (setting_icon_size == 0) {
                mImageView.setVisibility(View.GONE);
            } else {
                mImageView.setVisibility(View.VISIBLE);
                mImageView.setMinimumWidth((int) (setting_icon_size * getResources().getDisplayMetrics().density));
                mImageView.setMinimumHeight((int) (setting_icon_size * getResources().getDisplayMetrics().density));
            }


            mExesTextView.setText("#" + (position + 1) + "  " + exes.getExes().getName());
            mExesTextView.append(System.lineSeparator() + exes.getPodhod().toString());
            //  mExesTextView.append( System.lineSeparator() + exes.getPodhod().toStringDetail());
            mExesTextView.setTextSize(setting_text_size);

            if (isSelect) {
                mCheckBox.setVisibility(View.VISIBLE);
            } else {
                mCheckBox.setVisibility(View.GONE);
            }

            this.position = position;

        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            selectItem[position] = mCheckBox.isChecked();
            countSelect += mCheckBox.isChecked() ? 1 : -1;

        }

        /**
         * Called when a view has been clicked and held.
         *
         * @param v The view that was clicked and held.
         * @return true if the callback consumed the long click, false otherwise.
         */
        @Override
        public boolean onLongClick(View v) {
            Toast toast = Toast.makeText(getContext(),
                    "Хорошь уже", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
    }


    // Adapter

    private class Adapter extends RecyclerView.Adapter<EdayPageFragment.Holder> {

        private List<Eday.EdayList> mExes;

        public Adapter(List<Eday.EdayList> exes) {
            mExes = exes;
        }

        @Override
        public EdayPageFragment.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.exes_list_item, parent, false);
            return new EdayPageFragment.Holder(view);

        }

        @Override
        public void onBindViewHolder(EdayPageFragment.Holder holder, int position) {
            Eday.EdayList exes = mExes.get(position);
            holder.bindHolder(position, exes);
        }

        @Override
        public int getItemCount() {
            return mExes.size();
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_eday_page, menu);
        mMenu = menu;
        menu.findItem(R.id.action_delete).setVisible(false);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_delete).setVisible(isSelect);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getContext(), SettingListExes.class);
            startActivity(intent);
        } else if (id == R.id.action_sellect) {
            isSelect = !isSelect;
            if (isSelect) {
                activity.fabChangeImage(1);
            } else {
                activity.fabChangeImage(0);
            }
            onPrepareOptionsMenu(mMenu);
            updateUI();
        } else if (id == R.id.action_delete) {
            int delId[] = new int[countSelect];
            int idMass = 0;
            for (int i = 0; i < selectItem.length; i++) {
                if (selectItem[i]) delId[idMass++] = i;
            }
            day.del(delId);
            isSelect = false;
            bs.updateEday(day);
            adapter = null;
            updateUI();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = null;
        updateUI();
    }

}
