package thstdio.sportv1.display;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import thstdio.sportv1.R;
import thstdio.sportv1.display.abstract_package.AbstractNavigationActivity;
import thstdio.sportv1.display.my_trener_activiti.EProgListActivity;
import thstdio.sportv1.display.test.testActivity;
import thstdio.sportv1.logic.ETren.Eprog;
import thstdio.sportv1.logic.ETren.Types;
import thstdio.sportv1.logic.base.BaseInterface;
import thstdio.sportv1.logic.base.CollectionBS;
import thstdio.sportv1.logic.base.SqliteBS;

public class MainActivity extends AbstractNavigationActivity {


    @Override
    protected Fragment createFragment() {
        return new Fragment();
    }

    @Override
    protected void init() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String str = prefs.getString("first_start", "yes");
        if(str.equals("yes")){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("first_start", "no");
            BaseInterface bs1 = SqliteBS.getInstance(getApplicationContext());
            BaseInterface bs2 = new CollectionBS();
            Eprog prog=bs2.getEprog(1);
            bs1.setEprog(prog);
            prog=bs2.getEprog(3);
            bs1.setEprog(prog);
            prog=bs2.getEprog(2);
            bs1.setEprog(prog);
            for(int i=0;i<30;i++){
                int z=bs2.getExesType(i);
                bs1.setExesType(i,z);
                bs1.setExesTypeDescription(z, Types.search(z).getName());
            }
        }


    }

    @Override
    protected void fabOnClic() {

    }

    @Override
    protected void initAfterNavigation() {

    }
}
