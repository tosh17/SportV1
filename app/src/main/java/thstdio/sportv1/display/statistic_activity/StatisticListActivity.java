package thstdio.sportv1.display.statistic_activity;

import android.support.v4.app.Fragment;
import android.view.View;

import thstdio.sportv1.display.abstract_package.AbstractNavigationActivity;
import thstdio.sportv1.display.abstract_package.AbstractNavigationPageActivity;

/**
 * Created by shcherbakov on 27.06.2017.
 */

public class StatisticListActivity extends AbstractNavigationActivity {

    @Override
    protected Fragment createFragment() {
        return StaticListFragment.newInstance();
    }

    @Override
    protected void init() {
    fab.setVisibility(View.GONE);

    }

    @Override
    protected void fabOnClic() {

    }

    @Override
    protected void initAfterNavigation() {

    }
}
