package thstdio.sportv1.display.my_trener_activiti;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import thstdio.sportv1.R;
import thstdio.sportv1.display.abstract_package.AbstractPageActivity;
import thstdio.sportv1.display.settings_activiti.SettingListExes;
import thstdio.sportv1.logic.ETren.Eprog;
import thstdio.sportv1.logic.base.BaseInterface;
import thstdio.sportv1.logic.base.BaseLab;
import thstdio.sportv1.logic.base.CollectionBS;

/**
 * Created by shcherbakov on 03.06.2017.
 */

public class EdayPage extends AbstractPageActivity   {
    private ViewPager mViewPager;
    Eprog prog;
    int idProg;
    int numberOfDay;

    @Override
    protected int getNumberPage() {
        return prog.getNumberOfDay();
    }

    @Override
    protected Fragment setFragment(int position) {
        return EdayPageFragment.newInstance(idProg,position);
    }

    @Override
    protected void init() {
        idProg=getIntent().getIntExtra(EXTRA_ID,0);
        BaseInterface bs= BaseLab.getBS(getApplicationContext());
        prog=bs.getEprog(idProg);
    }

    @Override
    protected CharSequence getMyTitle(int position) {
        return "День "+ (position+1);
    }


    @Override
    protected void fabOnClic() {

    }

    public static final String EXTRA_ID =
            "prog_id";

    public static Intent newIntent(Context packageContext, int position) {
        Intent intent = new Intent(packageContext, EdayPage.class);
        intent.putExtra(EXTRA_ID, position);
        return intent;
    }

}
