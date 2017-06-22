package thstdio.sportv1.display.my_trener_activiti;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import thstdio.sportv1.display.abstract_package.AbstractNavigationActivity;
import thstdio.sportv1.display.abstract_package.SingleFragmentActivity;

/**
 * Created by shcherbakov on 18.06.2017.
 */

public class EProgAdd extends SingleFragmentActivity {
    private Callbacks mCallbacks;



    public interface Callbacks {
        void fab();

    }
    @Override
    protected Fragment createFragment() {
        Fragment fragment=EProgAddFragment.newInstance();
        mCallbacks=(EProgAddFragment)fragment;
        return fragment;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void fabOnClic() {
          mCallbacks.fab();

    }
    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, EProgAdd.class);
        return intent;
    }
}
