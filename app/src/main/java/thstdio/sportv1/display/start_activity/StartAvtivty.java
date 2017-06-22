package thstdio.sportv1.display.start_activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import thstdio.sportv1.R;
import thstdio.sportv1.display.abstract_package.AbstractNavigationPageActivity;
import thstdio.sportv1.logic.ETren.Eday;
import thstdio.sportv1.logic.TTren.Tday;
import thstdio.sportv1.logic.TTren.Texes;
import thstdio.sportv1.logic.base.BaseInterface;
import thstdio.sportv1.logic.base.BaseLab;

import static thstdio.sportv1.R.id.spinner;


/**
 * Created by shcherbakov on 21.06.2017.
 */

public class StartAvtivty extends AbstractNavigationPageActivity {
    private static final String EXTRA_ID = "id_prog";

    Tday tDay;

    @Override
    protected int getNumberPage() {
        return Tday.getTday().size();
    }

    @Override
    protected Fragment setFragment(int position) {
        return StartPageFragment.newInstance(position);
    }

    @Override
    public void init() {
        BaseInterface bs= BaseLab.getBS(getApplicationContext());
        int idProg=getIntent().getIntExtra(EXTRA_ID,0);
        if(idProg==0) idProg=lastId();
        Eday day=bs.getEday(idProg,1);
        tDay =Tday.getnewTday(day);
        bs.startTday(tDay);
    }

    @Override
    public void initAfterNavigation() {

        spinnerVsTablayout(true);
        String[] strSpiner=new String[tDay.size()];
        for(int i=0;i<tDay.size();i++) strSpiner[i]= tDay.getTexes(i).getExes().getName();
        // Настраиваем адаптер
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(getApplicationContext(),R.layout.spiner,R.id.textViewSpiner,strSpiner);

// Вызываем адаптер
        toolSpinner.setAdapter(adapter);
        toolSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                mViewPager.setCurrentItem(selectedItemPosition);

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private int lastId() {
        return 1;
    }

    @Override
    protected CharSequence getMyTitle(int position) {
        return "Ex"+(position+1);
    }

    @Override
    protected void tabSelect(int idTab) {
        if(toolSpinner!=null)
        toolSpinner.setSelection(idTab);

    }

    @Override
    protected void fabOnClic() {

    }
    public static Intent newIntent(Context packageContext, int idProg) {
        Intent intent = new Intent(packageContext, StartAvtivty.class);
        intent.putExtra(EXTRA_ID, idProg);
        return intent;
    }
    public Texes getTexes(int position){
        return tDay.getTexes(position);
    }
    public Tday getTday()  {
        return tDay;
    }
}