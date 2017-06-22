package thstdio.sportv1.display.my_trener_activiti;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import thstdio.sportv1.R;
import thstdio.sportv1.logic.ETren.Eday;
import thstdio.sportv1.logic.ETren.Eprog;
import thstdio.sportv1.logic.base.BaseInterface;
import thstdio.sportv1.logic.base.BaseLab;

/**
 * Created by shcherbakov on 18.06.2017.
 */

public class EProgAddFragment extends Fragment implements EProgAdd.Callbacks {
    private static final int MAX_NUMBER_DAY = 5;
    private RadioButton radio[];
    private LinearLayout line[];
    private EditText progName, dayName[];
    private int currentDay = 1;
    private String strWarning;

    public static EProgAddFragment newInstance() {
        Bundle args = new Bundle();
        EProgAddFragment fragment = new EProgAddFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    private void init(View v) {
        strWarning = getResources().getString(R.string.warning_eprog_add_max_limit);
        progName = (EditText) v.findViewById(R.id.textViewProgName);
        radio = new RadioButton[3];
        radio[0] = (RadioButton) v.findViewById(R.id.radioButton);
        radio[0].setChecked(true);
        radio[1] = (RadioButton) v.findViewById(R.id.radioButton2);
        radio[2] = (RadioButton) v.findViewById(R.id.radioButton3);
        line = new LinearLayout[MAX_NUMBER_DAY];
        dayName = new EditText[MAX_NUMBER_DAY];
        line[0] = (LinearLayout) v.findViewById(R.id.lineEprogAdd0);
        dayName[0] = (EditText) v.findViewById(R.id.editTextDayName0);
        line[1] = (LinearLayout) v.findViewById(R.id.lineEprogAdd1);
        dayName[1] = (EditText) v.findViewById(R.id.editTextDayName1);
        line[2] = (LinearLayout) v.findViewById(R.id.lineEprogAdd2);
        dayName[2] = (EditText) v.findViewById(R.id.editTextDayName2);
        line[3] = (LinearLayout) v.findViewById(R.id.lineEprogAdd3);
        dayName[3] = (EditText) v.findViewById(R.id.editTextDayName3);
        line[4] = (LinearLayout) v.findViewById(R.id.lineEprogAdd4);
        dayName[4] = (EditText) v.findViewById(R.id.editTextDayName4);

        for (int i = 1; i < MAX_NUMBER_DAY; i++) {
            line[i].setVisibility(View.GONE);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.eprog_add, container, false);
        init(v);
        return v;
    }

    @Override
    public void fab() {
        if (currentDay >= MAX_NUMBER_DAY) {
            Toast toast = Toast.makeText(getContext(),
                    strWarning + " " + MAX_NUMBER_DAY, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        line[currentDay].setVisibility(View.VISIBLE);
        currentDay++;
    }
    public void saveProg(){
        BaseInterface bs = BaseLab.getBS(getContext());
        int id = bs.getIdProgMax();
        Eday[] day=new Eday[currentDay];
        String tName=progName.getText().toString();
        int place=1;
        for(int i=0;i<3;i++){
            if(radio[i].isChecked())place=i;}
        Eprog prog=new Eprog(id+1,tName,place+1);
        for(int i=0;i<currentDay;i++){
            tName=dayName[i].getText().toString();
            day[i]=new Eday(id+1,i+1,tName);
            prog.add(day[i]);
        }
        bs.setEprog(prog);
    }

    /**
     * Called when the Fragment is no longer resumed.  This is generally
     * tied to {@link Activity#onPause() Activity.onPause} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onPause() {
        super.onPause();
        saveProg();
    }
}

