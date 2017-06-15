package thstdio.sportv1.display.abstract_package;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import thstdio.sportv1.R;
import thstdio.sportv1.display.my_trener_activiti.EProgListActivity;

public abstract class AbstractPageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {



    FloatingActionButton fab;
    private ViewPager mViewPager;
    private int numberPage;
    protected  abstract int getNumberPage();
    protected abstract Fragment setFragment(int position);
    protected abstract void init();
    protected abstract CharSequence getMyTitle(int position);// определяет имена секций
    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.start_page_activity_main;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_page_activity_main);
        mViewPager = (ViewPager) findViewById(R.id.activity_pager_view_pager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


         init();
        numberPage=getNumberPage();
        FragmentManager fragmentManager = getSupportFragmentManager();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        if(numberPage>1){
           tabLayout.setupWithViewPager(mViewPager);}
         else{
            tabLayout.setVisibility(View.GONE);
        }
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int namberOfDay) {

                return setFragment(namberOfDay);
            }

            @Override
            public int getCount() {
                return numberPage;
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return getMyTitle(position);
            }
        });
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent ;
        if (id == R.id.nav_start) {
            // Handle the camera action
        } else if (id == R.id.nav_my_tren) {
            intent  = new Intent(AbstractPageActivity.this, EProgListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_statistic) {

        } else if (id == R.id.nav_mesuare) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
       if(v.getId()==R.id.fab)  fabOnClic();
    }

    protected abstract void fabOnClic();
    public void fabChangeImage(){

        fab.setImageResource(R.drawable.ic_menu_manage);
    }
}
