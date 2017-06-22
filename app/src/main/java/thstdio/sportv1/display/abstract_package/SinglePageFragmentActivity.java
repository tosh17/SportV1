package thstdio.sportv1.display.abstract_package;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Spinner;

import thstdio.sportv1.R;


/**
 * Created by shcherbakov on 26.04.2017.
 */


public abstract class SinglePageFragmentActivity extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton fab;
    protected ViewPager mViewPager;
    protected TabLayout tabLayout;
    protected Spinner toolSpinner;
    Toolbar toolbar;
    private int numberPage;

    protected abstract int getNumberPage();

    protected abstract Fragment setFragment(int position);

    public abstract void init();

    protected abstract CharSequence getMyTitle(int position);// определяет имена секций

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.abstract_page_app_bar_main;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        mViewPager = (ViewPager) findViewById(R.id.activity_pager_view_pager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        init();
        numberPage = getNumberPage();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentStatePagerAdapter pagerAdapter = new FragmentStatePagerAdapter(fragmentManager) {
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
        };
        mViewPager.setAdapter(pagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabSelect(mViewPager.getCurrentItem());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        if (numberPage > 1) {
            tabLayout.setupWithViewPager(mViewPager);
        } else {
            tabLayout.setVisibility(View.GONE);
        }
        toolSpinner=(Spinner) findViewById(R.id.tool_spinner);
        toolSpinner.setVisibility(View.GONE);
           }

    protected abstract void tabSelect(int idTab);

    protected void changeToolbarTitle(String str) {
        toolbar.setTitle(str);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab) fabOnClic();
    }

    protected abstract void fabOnClic();

    public void fabChangeImage() {

        fab.setImageResource(R.drawable.ic_menu_manage);
    }

    @Override
    public void onResume() {
        super.onResume();

    }
    public void fabChangeImage(int i){

        switch(i) {
            case 0:
                fab.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
                break;
            case 1:
                fab.setImageResource(R.drawable.ic_add_circle_outline_black_24dp);
                break;
        }    }

    protected void spinnerVsTablayout(boolean enableSpinner) {
        tabLayout.setVisibility(View.GONE);
        toolSpinner.setVisibility(View.VISIBLE);
    }
}