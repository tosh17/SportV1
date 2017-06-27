package thstdio.sportv1.display.start_activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by shcherbakov on 26.06.2017.
 */

public class DayStatisticExes extends Fragment {

    private static final String EXTRA_ID = "exes_val";
    private static final String EXTRA_STR = "exes_name";

    public static DayStatisticExes newInstance(int[] table, String str) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_ID, table); //
        args.putSerializable(EXTRA_STR, str);
        DayStatisticExes fragment = new DayStatisticExes();
        fragment.setArguments(args);
        return fragment;
    }



}
