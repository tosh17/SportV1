package thstdio.sportv1.display.my_trener_activiti;

import android.content.Intent;
import android.support.v4.app.Fragment;

import thstdio.sportv1.display.abstract_package.AbstractNavigationActivity;

/**
 * Created by shcherbakov on 02.06.2017.
 */

public class EProgListActivity extends AbstractNavigationActivity {
    @Override
    protected Fragment createFragment() {
        return new EProgListFragment();
    }

    @Override
    protected void init() {

    }

    @Override
    protected void fabOnClic() {
     Intent intent=EProgAdd.newIntent(getApplicationContext());
     startActivity(intent);
    }


    @Override
    protected void initAfterNavigation() {

    }
}
