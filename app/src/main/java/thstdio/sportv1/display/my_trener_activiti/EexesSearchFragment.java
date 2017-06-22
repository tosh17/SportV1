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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import thstdio.sportv1.R;
import thstdio.sportv1.display.abstract_package.MyAsset;
import thstdio.sportv1.logic.ETren.Eexes;
import thstdio.sportv1.logic.base.BaseInterface;
import thstdio.sportv1.logic.base.BaseLab;

/**
 * Created by shcherbakov on 18.06.2017.
 */

public class EexesSearchFragment extends Fragment {
    private static final String ID_TYPE = "id_type";

    ListViewAdapter listViewAdapter;
    RecyclerView mExesRecyclerView;
    EditText mSearch;
    List<Eexes> listAllExes;
    List<Eexes> listFindExes;
    public MyAsset myAsset;
    SharedPreferences prefs;
    BaseInterface bs;
    public boolean isitemSelect=false;
    public int itemSelect;

    private int setting_text_size = 14;
    private int setting_icon_size = 144;

    public static EexesSearchFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putSerializable(ID_TYPE, type);
        EexesSearchFragment fragment = new EexesSearchFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.exes_search_fragment, container, false);
        mExesRecyclerView = (RecyclerView) v
                .findViewById(R.id.activity_recycler_view);
        mExesRecyclerView.setLayoutManager(new LinearLayoutManager
                (getActivity()));
        final Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
        String[] listType=getResources().getStringArray(R.array.list_type_exes_with_none);
// Настраиваем адаптер
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(getContext(),R.layout.spiner,R.id.textViewSpiner,listType);

// Вызываем адаптер
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

            findList(selectedItemPosition-1,mSearch.getText().toString() );

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
         mSearch=(EditText )v.findViewById(R.id.editTextSearchExes);
        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence c, int start, int count, int after) {
                // Здесь намеренно оставлено пустое место
            }

            @Override
            public void onTextChanged(
                    CharSequence c, int start, int before, int count) {
               findList(spinner.getSelectedItemPosition()-1,c.toString());
            }

            @Override
            public void afterTextChanged(Editable c) {
                // И здесь тоже
            }
        });
        myAsset = new MyAsset(getContext());
        prefs =
                PreferenceManager.getDefaultSharedPreferences(getContext());
        bs = BaseLab.getBS(getContext());
        listAllExes = bs.getAllExes();
        listFindExes = listAllExes;

        UpdateSetting(prefs);

        return v;
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
        str = prefs.getString("list_exes_icon_style", "default");
        myAsset.setStyleIcon(str);
        updateUI();
    }

    private void updateUI() {
        if (listViewAdapter == null) {
            listViewAdapter = new EexesSearchFragment.ListViewAdapter(listFindExes);
            mExesRecyclerView.setAdapter(listViewAdapter);
        } else {
            listViewAdapter.notifyDataSetChanged();
        }
    }

    //Holder
    private class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mExesTextView;
        public ImageView mImageView;
        private LinearLayout lineExesList;
        int position;
        boolean isSelect=false;

        public Holder(View itemView) {
            super(itemView);
            mExesTextView = (TextView) itemView.findViewById(R.id.textViewListExes);
            mImageView = (ImageView) itemView.findViewById(R.id.imageViewExes);
            lineExesList = (LinearLayout) itemView.findViewById(R.id.lineExesList);
            itemView.setOnClickListener(this);
        }

        public void bindHolder(int position, Eexes exes) {
            mExesTextView.setText(exes.getName());
            this.position = position;
            Drawable d = myAsset.getIcon(exes.getId());
            mImageView.setImageDrawable(d);
            if (setting_icon_size == 0) {
                mImageView.setVisibility(View.GONE);
            } else {
                mImageView.setVisibility(View.VISIBLE);
                mImageView.setMinimumWidth((int) (setting_icon_size * getResources().getDisplayMetrics().density));
                mImageView.setMinimumHeight((int) (setting_icon_size * getResources().getDisplayMetrics().density));
            }
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            if(!isSelect && !isitemSelect) {
                isSelect=!isSelect;
                isitemSelect=!isitemSelect;
                itemSelect=position;
                lineExesList.setBackgroundColor(getResources().getColor(R.color.Orange200));}
            else if(isSelect && isitemSelect ){
                isSelect=!isSelect;
                isitemSelect=!isitemSelect;
                itemSelect=-1;
                lineExesList.setBackgroundColor(getResources().getColor(R.color.TOrange100));}
            else {
                String strWarning = getResources().getString(R.string.warning_exes_search);
                Toast toast = Toast.makeText(getContext(),
                        strWarning , Toast.LENGTH_SHORT);
                toast.show();
            }

            if(getActivity() != null) {
                if(itemSelect==-1){
                    ((EexesAddActivity) getActivity()).setItemResult(0);
                }else {
                    ((EexesAddActivity) getActivity()).setItemResult(listFindExes.get(itemSelect).getId());
                }
            }
        }

    }


    // Adapter

    private class ListViewAdapter extends RecyclerView.Adapter<EexesSearchFragment.Holder> {

        private List<Eexes> mExesList;

        public ListViewAdapter(List<Eexes> exes) {
            mExesList = exes;
        }

        @Override
        public EexesSearchFragment.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.exes_list_item, parent, false);
            return new EexesSearchFragment.Holder(view);
        }

        @Override
        public void onBindViewHolder(EexesSearchFragment.Holder holder, int position) {
            Eexes exes = mExesList.get(position);
            holder.bindHolder(position, exes);
        }

        @Override
        public int getItemCount() {
            return mExesList.size();
        }
    }

    public void findList(int type,String str){

        if(type!=-1){
            listFindExes=new ArrayList<>();
            for(Eexes i:listAllExes){
                if(bs.getExesType(i.getId())==type) listFindExes.add(i);
            }

        }else{
            listFindExes = listAllExes;
        }

        if(str.length()!=0){
            List<Eexes>  temp=new ArrayList<>();
            for(Eexes i:listFindExes){
                if(i.getName().contains(str)){temp.add(i);}
            }
            listFindExes=temp;
        }


        listViewAdapter=null;
        updateUI();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((EexesAddActivity) getActivity()).upHome(false);
        updateUI();
    }
    /**
     * Called when the Fragment is no longer resumed.  This is generally
     * tied to {@link Activity#onPause() Activity.onPause} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onPause() {
        super.onPause();
        ((EexesAddActivity) getActivity()).upHome(true);

    }


}
