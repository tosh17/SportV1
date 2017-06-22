package thstdio.sportv1.display.abstract_package;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import thstdio.sportv1.R;
import thstdio.sportv1.display.my_trener_activiti.EProgListActivity;
import thstdio.sportv1.display.start_activity.StartAvtivty;

public abstract class AbstractNavigationActivity extends SingleFragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.abstract_list_activity_main;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initAfterNavigation();
    }

    protected abstract void initAfterNavigation();


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

   @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent ;
        if (id == R.id.nav_start) {
            intent  = new Intent(AbstractNavigationActivity.this, StartAvtivty.class);
            startActivity(intent);
        } else if (id == R.id.nav_my_tren) {
            intent  = new Intent(AbstractNavigationActivity.this, EProgListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_statistic) {

        } else if (id == R.id.nav_mesuare) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
