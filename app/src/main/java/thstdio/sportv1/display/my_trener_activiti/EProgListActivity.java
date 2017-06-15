package thstdio.sportv1.display.my_trener_activiti;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import thstdio.sportv1.display.abstract_package.AbstractListActivity;

/**
 * Created by shcherbakov on 02.06.2017.
 */

public class EProgListActivity extends AbstractListActivity {
    @Override
    protected Fragment createFragment() {
        return new EProgListFragment();
    }

    @Override
    protected void init() {
        fabChangeImage();
    }

    @Override
    protected void fabOnClic() {
        Toast.makeText(this.getApplicationContext(),
                 " clicked!", Toast.LENGTH_SHORT)
                .show();
    }


}
