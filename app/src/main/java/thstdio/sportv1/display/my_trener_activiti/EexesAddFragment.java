package thstdio.sportv1.display.my_trener_activiti;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import thstdio.sportv1.R;
import thstdio.sportv1.logic.ETren.Eday;
import thstdio.sportv1.logic.ETren.Eexes;
import thstdio.sportv1.logic.ETren.Epodhod;
import thstdio.sportv1.logic.base.BaseInterface;
import thstdio.sportv1.logic.base.BaseLab;

/**
 * Created by shcherbakov on 16.06.2017.
 */

public class EexesAddFragment extends Fragment implements CompoundButton.OnCheckedChangeListener,EexesAddActivity.Callbacks, View.OnClickListener {
    private static final String DAY_ID = "day_id";
    private static final int MAX_PODGOD = 10;
    private static final String PROG_ID = "prog_id";
    public static final int REQUEST = 5;

    private EditText nameExes;
    private Spinner exesType;
    private Switch isFreeWeight;
    private LinearLayout[] line;
    private Switch[] podhodType;
    private EditText[] min;
    private EditText[] max;

    private boolean isExesLoad = false;
    private boolean goToLoad = false;

    private int currentPodhod;
    private int numberR = 1, numberC = 0;

    private Eexes exes;
    private Epodhod podhod;

    private String strP, strR, strWarning1, strWarning2;
    private int idDay, idProg;

    public static EexesAddFragment newInstance(int idProg, int idDay) {
        Bundle args = new Bundle();
        args.putSerializable(PROG_ID, idProg);
        args.putSerializable(DAY_ID, idDay);
        EexesAddFragment fragment = new EexesAddFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Called to do initial creation of a fragment.  This is called after
     * {@link #onAttach(Activity)} and before
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * <p>
     * <p>Note that this can be called while the fragment's activity is
     * still in the process of being created.  As such, you can not rely
     * on things like the activity's content view hierarchy being initialized
     * at this point.  If you want to do work once the activity itself is
     * created, see {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>Any restored child fragments will be created before the base
     * <code>Fragment.onCreate</code> method returns.</p>
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.exes_add_fragment, container, false);
        idDay = getArguments().getInt(DAY_ID);
        idProg = getArguments().getInt(PROG_ID);
        currentPodhod = 1;
        strWarning1 = getResources().getString(R.string.warning_exes_add_max_limit);
        strWarning2 = getResources().getString(R.string.warning_exes_add_min_limit);
        strP = getResources().getString(R.string.podhod_str);
        strR = getResources().getString(R.string.razminka_str);
        strWarning1 = getResources().getString(R.string.warning_exes_add_min_limit);
        podhodType = new Switch[MAX_PODGOD];
        min = new EditText[MAX_PODGOD];
        max = new EditText[MAX_PODGOD];
        line = new LinearLayout[MAX_PODGOD];
        init(v);
        return v;
    }

    private void init(View v) {
        nameExes = (EditText) v.findViewById(R.id.editName);
        exesType = (Spinner) v.findViewById(R.id.spinner);
        isFreeWeight = (Switch) v.findViewById(R.id.add_exes_switchWeight);
        isFreeWeight.setOnCheckedChangeListener(this);
        podhodType[0] = (Switch) v.findViewById(R.id.add_exes_switch0);
        min[0] = (EditText) v.findViewById(R.id.add_exes_editTextMin0);
        max[0] = (EditText) v.findViewById(R.id.add_exes_editTextMax0);
        podhodType[1] = (Switch) v.findViewById(R.id.add_exes_switch1);
        min[1] = (EditText) v.findViewById(R.id.add_exes_editTextMin1);
        max[1] = (EditText) v.findViewById(R.id.add_exes_editTextMax1);
        podhodType[2] = (Switch) v.findViewById(R.id.add_exes_switch2);
        min[2] = (EditText) v.findViewById(R.id.add_exes_editTextMin2);
        max[2] = (EditText) v.findViewById(R.id.add_exes_editTextMax2);
        podhodType[3] = (Switch) v.findViewById(R.id.add_exes_switch3);
        min[3] = (EditText) v.findViewById(R.id.add_exes_editTextMin3);
        max[3] = (EditText) v.findViewById(R.id.add_exes_editTextMax3);
        podhodType[4] = (Switch) v.findViewById(R.id.add_exes_switch4);
        min[4] = (EditText) v.findViewById(R.id.add_exes_editTextMin4);
        max[4] = (EditText) v.findViewById(R.id.add_exes_editTextMax4);
        podhodType[5] = (Switch) v.findViewById(R.id.add_exes_switch5);
        min[5] = (EditText) v.findViewById(R.id.add_exes_editTextMin5);
        max[5] = (EditText) v.findViewById(R.id.add_exes_editTextMax5);
        podhodType[6] = (Switch) v.findViewById(R.id.add_exes_switch6);
        min[6] = (EditText) v.findViewById(R.id.add_exes_editTextMin6);
        max[6] = (EditText) v.findViewById(R.id.add_exes_editTextMax6);
        podhodType[7] = (Switch) v.findViewById(R.id.add_exes_switch7);
        min[7] = (EditText) v.findViewById(R.id.add_exes_editTextMin7);
        max[7] = (EditText) v.findViewById(R.id.add_exes_editTextMax7);
        podhodType[8] = (Switch) v.findViewById(R.id.add_exes_switch8);
        min[8] = (EditText) v.findViewById(R.id.add_exes_editTextMin8);
        max[8] = (EditText) v.findViewById(R.id.add_exes_editTextMax8);
        podhodType[9] = (Switch) v.findViewById(R.id.add_exes_switch9);
        min[9] = (EditText) v.findViewById(R.id.add_exes_editTextMin9);
        max[9] = (EditText) v.findViewById(R.id.add_exes_editTextMax9);

        line[0] = (LinearLayout) v.findViewById(R.id.add_exes_line0);
        line[1] = (LinearLayout) v.findViewById(R.id.add_exes_line1);
        line[2] = (LinearLayout) v.findViewById(R.id.add_exes_line2);
        line[3] = (LinearLayout) v.findViewById(R.id.add_exes_line3);
        line[4] = (LinearLayout) v.findViewById(R.id.add_exes_line4);
        line[5] = (LinearLayout) v.findViewById(R.id.add_exes_line5);
        line[6] = (LinearLayout) v.findViewById(R.id.add_exes_line6);
        line[7] = (LinearLayout) v.findViewById(R.id.add_exes_line7);
        line[8] = (LinearLayout) v.findViewById(R.id.add_exes_line8);
        line[9] = (LinearLayout) v.findViewById(R.id.add_exes_line9);
        podhodType[0].setOnClickListener(this);
        podhodType[0].setText("Разминка#1");
        min[0].setEnabled(false);
        max[0].setEnabled(false);
        for (int i = 1; i < 10; i++) {
            podhodType[i].setOnClickListener(this);
            min[i].setEnabled(false);
            max[i].setEnabled(false);
            line[i].setVisibility(View.GONE);
            numberR++;
            podhodType[i].setText("Разминка#" + (i + 1));
            ;
        }
    }

    @Override
    public void addPodhod() {
        if (currentPodhod >= MAX_PODGOD - 1) {
            Toast toast = Toast.makeText(getContext(),
                    strWarning1 + " " + MAX_PODGOD, Toast.LENGTH_SHORT);
            toast.show();
        } else {

            line[currentPodhod].setVisibility(View.VISIBLE);
            if (podhodType[currentPodhod - 1].isChecked()) {
                numberR--;
                numberC++;
                podhodType[currentPodhod].setChecked(true);
                podhodType[currentPodhod].setText(strP + "#" + numberC);

                min[currentPodhod].setEnabled(true);
                max[currentPodhod].setEnabled(true);
                min[currentPodhod].setText(min[currentPodhod - 1].getText().toString());
                max[currentPodhod].setText(max[currentPodhod - 1].getText().toString());
            }
            currentPodhod++;
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        for (int i = 0; i < MAX_PODGOD; i++) {
            if (podhodType[i].getId() == id) {
                if (podhodType[i].isChecked()) {
                    numberR--;
                    numberC++;
                    podhodType[i].setText(strP + "#" + numberC);
                    min[i].setEnabled(true);
                    max[i].setEnabled(true);

                } else {
                    numberR++;
                    numberC--;
                    podhodType[i].setText(strR + "#" + numberR);
                    min[i].setEnabled(false);
                    max[i].setEnabled(false);
                }
            }
        }
    }

    /**
     * Called when the Fragment is no longer resumed.  This is generally
     * tied to {@link Activity#onPause() Activity.onPause} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onPause() {
        super.onPause();
        if (!goToLoad || isCorrect()) saveDayExes();
    }


    public boolean saveDayExes() {
         BaseInterface bs = BaseLab.getBS(getContext());
        if (!isExesLoad) {
            int id = bs.getIdExesMax();
            exes = new Eexes(id + 1, nameExes.getText().toString());
            exes.setFreeWeight(isFreeWeight.isChecked());
            int idt = exesType.getSelectedItemPosition();
            bs.setExesType(id, exesType.getSelectedItemPosition());
        }

        int imin[] = new int[numberC];
        int imax[] = new int[numberC];
        int iTemp = 0;
        for (int i = 0; i < currentPodhod; i++) {
            if (podhodType[i].isChecked()) {

                imin[iTemp] = Integer.parseInt(min[i].getText().toString());
                imax[iTemp] = Integer.parseInt(max[i].getText().toString());
                iTemp++;
            }
        }
        podhod = new Epodhod(0, numberR - (MAX_PODGOD - currentPodhod), numberC, imin, imax);
        Eday day = bs.getEday(idProg, idDay);
        day.add(exes, podhod);
        bs.updateEday(day);
        return true;
    }

    @Override
    public void loadExes(int id) {
        if (id < 1) return;
        goToLoad=false;
        isExesLoad = true;
        BaseInterface bs = BaseLab.getBS(getContext());
        exes = bs.getEexes(id);
        nameExes.setText(exes.getName());
        nameExes.setEnabled(false);
        exesType.setSelection(bs.getExesType(exes.getId()));
        exesType.setEnabled(false);
        isFreeWeight.setChecked(exes.isFreeWeight());
        isFreeWeight.setEnabled(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_e_exes_add, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_download) {
            goToLoad = true;
            setHasOptionsMenu(false);
            ((EexesAddActivity) getActivity()).startFind();

        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to {@link Activity#onResume() Activity.onResume} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onStart() {
        super.onStart();
        setHasOptionsMenu(true);

    }

    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    public boolean isCorrect(){
        if (numberC == 0) {
            Toast toast = Toast.makeText(getContext(),
                    strWarning2, Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

        int imin[] = new int[numberC];
        int imax[] = new int[numberC];
        int iTemp = 0;
        for (int i = 0; i < currentPodhod; i++) {
            if (podhodType[i].isChecked()) {

               try{
                imin[iTemp] = Integer.parseInt(min[i].getText().toString());
                imax[iTemp] = Integer.parseInt(max[i].getText().toString()); }
                catch(Exception e){
                    min[i].setBackgroundColor(Color.RED);
                    max[i].setBackgroundColor(Color.RED);
                    return false;}
                if(imin[iTemp]>=imax[iTemp]) {
                    min[i].setBackgroundColor(Color.RED);
                    max[i].setBackgroundColor(Color.RED);
                    return false;}
                iTemp++;
            }
        }
         return true;

    }


    /**
     * Called when the checked state of a compound button has changed.
     *
     * @param buttonView The compound button view whose state has changed.
     * @param isChecked  The new checked state of buttonView.
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        final String[] mChooseCats = getResources().getStringArray(R.array.exes_main_values);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getResources().getString(R.string.e_exes_add_dialog_title))
                .setCancelable(false)
                // добавляем переключатели
                .setSingleChoiceItems(mChooseCats, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int item) {
                                exes.setMainValue(item);
                              dialog.cancel();
                            }
                        });
        builder.create();
        builder.show();
    }
}