package thstdio.sportv1.display.statistic_activity;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import thstdio.sportv1.R;
import thstdio.sportv1.display.abstract_package.AbstractNavigationActivity;
import thstdio.sportv1.logic.base.BaseInterface;
import thstdio.sportv1.logic.base.BaseLab;

/**
 * Created by shcherbakov on 27.06.2017.
 */

public class StatisticListActivity extends AbstractNavigationActivity implements DatePickerDialog.OnDateSetListener {


    private long[] time;
    private Callbacks mCallbacks;

    public interface Callbacks {
        void dataDialog(int year, int monthOfYear, int dayOfMonth);

    }

    @Override
    protected Fragment createFragment() {
        Fragment fragment = StaticListFragment.newInstance();
        mCallbacks=(StaticListFragment)fragment;
         return fragment;
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_change_statistic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_change) {
          showDateDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDateDialog() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                (DatePickerDialog.OnDateSetListener) this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        BaseInterface bs = BaseLab.getBS(getApplicationContext());
        List<String[]> list = bs.getStatList();
        time=new long[list.size()];
        for(int i=0;i<list.size();i++){
            time[i]=Long.parseLong(list.get(i)[0]);
        }
        // dpd.setHighlightedDays(days);
        dpd.setSelectableDays(parceTodays());
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }
    private Calendar[] parceTodays(){

        Calendar[] calend=new Calendar[time.length];
        for(int i=0;i<time.length;i++){

            calend[i]=new GregorianCalendar();
            calend[i].setTimeInMillis(time[i]);
        }
        return calend;
    }

    /**
     * @param view        The view associated with this listener.
     * @param year        The year that was set.
     * @param monthOfYear The month that was set (0-11) for compatibility
     *                    with {@link Calendar}.
     * @param dayOfMonth  The day of the month that was set.
     */
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            mCallbacks.dataDialog(year,monthOfYear,dayOfMonth);
    }
}
