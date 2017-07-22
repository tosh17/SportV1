package thstdio.sportv1.display.start_activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import thstdio.sportv1.R;
import thstdio.sportv1.display.abstract_package.AbstractNavigationPageActivity;
import thstdio.sportv1.display.my_trener_activiti.EProgListActivity;
import thstdio.sportv1.display.my_trener_activiti.EdayPage;
import thstdio.sportv1.logic.ETren.Eday;
import thstdio.sportv1.logic.TTren.Tday;
import thstdio.sportv1.logic.TTren.Texes;
import thstdio.sportv1.logic.base.BaseInterface;
import thstdio.sportv1.logic.base.BaseLab;
import thstdio.sportv1.logic.date.DateLab;


/**
 * Created by shcherbakov on 21.06.2017.
 */

public class StartAvtivty extends AbstractNavigationPageActivity {
    private static final String EXTRA_ID = "id_prog";

    private Callbacks mCallbacks[];

    public interface Callbacks {
        void clickFab();
     //   void changeTab(int numberTab);
    }


    Tday tDay;
    BaseInterface bs;
    int idProg, numberDay;
    int numberPage = 0;
    boolean isFinishing=false;
    long lastTime;

    @Override
    protected int getNumberPage() {
        if(idProg==0) return 0;
        if(tDay==null) {
            bs = BaseLab.getBS(getApplicationContext());
            tDay = bs.findNotEndTday();
        }
        return tDay.size();
    }

    @Override
    protected Fragment setFragment(int position) {
        StartPageFragment fragment= StartPageFragment.newInstance(position);
        mCallbacks[position]= fragment;
       return fragment;

    }

    @Override
    public void init() {
        bs = BaseLab.getBS(getApplicationContext());
        idProg = getIntent().getIntExtra(EXTRA_ID, 0);
        tDay = null;
        tDay = bs.findNotEndTday();

        if (tDay == null) {
            idProg = getIntent().getIntExtra(EXTRA_ID, 0);
            lastId();
            if(idProg==0) return ;
            Eday day = bs.getEprog(idProg).getNextDay(numberDay-1);
            tDay = new Tday(day);
            mCallbacks=new Callbacks[getNumberPage()];
            tDay.setId(DateLab.now());
            dialogStartTday();

        } else {
            int tempIdProg=idProg;
            idProg=-1;//исключаем проверку на не сучествующую тренеровку
            mCallbacks=new Callbacks[getNumberPage()];
            dialogNotEndTday(tempIdProg);

        }
    }


    @Override
    public void initAfterNavigation() {
        if(idProg==0) return ;
        spinnerVsTablayout(true);
        String[] strSpiner = new String[tDay.size()];
        for (int i = 0; i < tDay.size(); i++) strSpiner[i] = tDay.getTexes(i).getExes().getName();
        // Настраиваем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spiner, R.id.textViewSpiner, strSpiner);

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

    private void dialogNotEndTday(final int tempIdProg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.start_dialog_title))
                .setMessage(getResources().getString(R.string.start_dialog_mess,System.lineSeparator()))
                .setIcon(R.drawable.all)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.start_dialog_continion),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })

                .setNegativeButton(getString(R.string.start_dialog_cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                isFinishing=true;
                                lastTime=bs.findLastTime(tDay.getId());
                                for (int i = 0; i < tDay.size(); i++) {
                                    if (!tDay.getTexes(i).isDone()) {
                                        tDay.getTexes(i).disALL();
                                        exesDone(false);
                                    }
                                }
                                finish();
                                Intent intent= StartAvtivty.newIntent(getApplicationContext(),tempIdProg);
                                startActivity(intent);
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();

    }
    private void dialogStartTday(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String str=bs.getEprog(idProg).getName();
        builder.setTitle(getResources().getString(R.string.start_dialog2_title))
                .setMessage( str + System.lineSeparator() + tDay.getNameDay())
                .setIcon(R.drawable.all)
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.start_dialog2_start),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                bs.startTday(tDay);
                                dialog.cancel();
                            }
                        })

                .setNegativeButton(getResources().getString(R.string.start_dialog2_change),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                finish();
                                Intent intent= new Intent(StartAvtivty.this,EProgListActivity.class);
                                startActivity(intent);
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void lastId() {
        bs = BaseLab.getBS(getApplicationContext());
        int temp[]=bs.findLastTday(idProg);
        if (idProg==0)idProg = temp[0];
        idProg=temp[0]==0?idProg: temp[0];
        if (idProg==0){

            Intent intent= new Intent(getApplicationContext(),EProgListActivity.class);
            startActivity(intent);
           finish();
        }
        numberDay=temp[1]==0?1: temp[1];


    }

    @Override
    protected CharSequence getMyTitle(int position) {
        return "Ex" + (position + 1);
    }

    @Override
    protected void tabSelect(int idTab) {
        if (toolSpinner != null)
            toolSpinner.setSelection(idTab);
        if(tDay.getTexes(idTab).isDone()) {
            fab.setVisibility(View.GONE);
        }
        else fab.setVisibility(View.VISIBLE);

    }

    @Override
    protected void fabOnClic() {
        mCallbacks[mViewPager.getCurrentItem()].clickFab();

    }

    public static Intent newIntent(Context packageContext, int idProg) {
        Intent intent = new Intent(packageContext, StartAvtivty.class);
        intent.putExtra(EXTRA_ID, idProg);
        return intent;
    }

    public Texes getTexes(int position) {
        return tDay.getTexes(position);
    }

    public Tday getTday() {
        return tDay;
    }

    public void exesDone() {
        if (tDay.iSDayDone()) {
            bs.endTday(tDay, DateLab.now());
            Intent intent = DayStatistic.newIntent(getApplicationContext(), tDay.getId());
            finish();
            startActivity(intent);

        }
        int i = 0;
        while (tDay.getTexes(i).isDone()) {
            if (i < tDay.size() - 1) i++;
            else break;
        }
        boolean t=fragmentManager.beginTransaction().isEmpty();
       if(t)
        mViewPager.setCurrentItem(i);
    }
    private void exesDone(boolean b) {
        if (tDay.iSDayDone()) {
            boolean isNullDay= bs.getTdayStat(tDay.getId()).length==0;
            if(isNullDay){bs.delTday(tDay.getId());}
            else{
            bs.endTday(tDay, DateLab.now());}
                }
        int i = 0;
        while (tDay.getTexes(i).isDone()) {
            if (i < tDay.size() - 1) i++;
            else break;
        }
        mViewPager.setCurrentItem(i);
    }
    public long disTime(){
        if(isFinishing) return lastTime;
        return DateLab.now();

    }
    public void hitFAB(boolean enable){
        if(enable) fab.setVisibility(View.VISIBLE);
        else fab.setVisibility(View.INVISIBLE);
    }

    public int getIdProg() {
        return idProg;
    }

    public int getNumberDay() {
        return numberDay;
    }
}
