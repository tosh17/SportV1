package thstdio.sportv1.display.my_trener_activiti;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;

import thstdio.sportv1.display.abstract_package.AbstractNavigationActivity;
import thstdio.sportv1.display.abstract_package.SingleFragmentActivity;

/**
 * Created by shcherbakov on 15.06.2017.
 */

public class EexesAddActivity extends SingleFragmentActivity {
    private static final String DAY_ID = "day_id";
    private static final String PROG_ID = "prog_id";

    private Callbacks mCallbacks;
    private boolean isNewFragment=false;

    public interface Callbacks {
        void addPodhod();
        boolean isCorrect();
        void loadExes(int id);
    }

    public static Intent newIntent(Context packageContext, int idProg, int idDay) {
        Intent intent = new Intent(packageContext, EexesAddActivity.class);
        intent.putExtra(PROG_ID, idProg);
        intent.putExtra(DAY_ID, idDay);
        return intent;
    }


    @Override
    protected Fragment createFragment() {
        int idDay = getIntent().getIntExtra(DAY_ID, 0);
        int idProg = getIntent().getIntExtra(PROG_ID, 0);
        Fragment fragment = EexesAddFragment.newInstance(idProg, idDay);
        mCallbacks = (EexesAddFragment) fragment;
        return fragment;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void fabOnClic() {
      mCallbacks.addPodhod();
    }

    @Override
    public void onBackPressed() {
         if(isNewFragment) {
             isNewFragment=false;
             detachFragment("new");
             fab.setVisibility(View.VISIBLE);
             return;
         }
        if (mCallbacks.isCorrect()) super.onBackPressed();
    }

    public void setItemResult(int id) {
      mCallbacks.loadExes(id);
    }
    public void startFind(){
        isNewFragment=true;
        fab.setVisibility(View.INVISIBLE);
        addFragment(EexesSearchFragment.newInstance(0),"new");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
