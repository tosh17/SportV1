package thstdio.sportv1.display.my_trener_activiti;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import thstdio.sportv1.R;
import thstdio.sportv1.display.abstract_package.SinglePageFragmentActivity;
import thstdio.sportv1.logic.ETren.Eprog;
import thstdio.sportv1.logic.base.BaseInterface;
import thstdio.sportv1.logic.base.BaseLab;

/**
 * Created by shcherbakov on 03.06.2017.
 */

public class EdayPage extends SinglePageFragmentActivity {
    Eprog prog;
    int idProg;
    int numberOfDay;

    private Callbacks mCallbacks[];

    public interface Callbacks {
        void clickFab();
        void changeTab(int numberTab);
    }
  public int currentPage(){
      return mViewPager.getCurrentItem();
  }

    @Override
    protected int getNumberPage() {

        return prog.getNumberOfDay();
    }

    @Override
    protected Fragment setFragment(int position) {
        Fragment fragment = EdayPageFragment.newInstance(idProg,position);
        mCallbacks[position]=(EdayPageFragment)fragment;
        return fragment;
    }

    @Override
    public void init() {
        idProg=getIntent().getIntExtra(EXTRA_ID,0);
        BaseInterface bs= BaseLab.getBS(getApplicationContext());
        prog=bs.getEprog(idProg);
        mCallbacks=new Callbacks[prog.getNumberOfDay()];
        changeToolbarTitle( prog.getName());

    }

    @Override
    protected CharSequence getMyTitle(int position) {
        return "День "+ (position+1);
    }

    @Override
    protected void tabSelect(int idTab ) {
        if(mCallbacks[currentPage()]!=null)
        mCallbacks[currentPage()].changeTab(idTab);
    }


    @Override
    protected void fabOnClic() {
        mCallbacks[currentPage()].clickFab();

    }

    public static final String EXTRA_ID =
            "prog_id";

    public static Intent newIntent(Context packageContext, int position) {
        Intent intent = new Intent(packageContext, EdayPage.class);
        intent.putExtra(EXTRA_ID, position);
        return intent;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

}
