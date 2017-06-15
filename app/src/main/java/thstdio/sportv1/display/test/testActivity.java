package thstdio.sportv1.display.test;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import thstdio.sportv1.R;
import thstdio.sportv1.logic.ETren.Eexes;
import thstdio.sportv1.logic.ETren.Eprog;
import thstdio.sportv1.logic.ETren.Types;
import thstdio.sportv1.logic.base.BaseInterface;
import thstdio.sportv1.logic.base.CollectionBS;
import thstdio.sportv1.logic.base.SqliteBS;

public class testActivity extends AppCompatActivity {
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        text=(TextView) findViewById(R.id.textView3);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseInterface bs1 = SqliteBS.getInstance(getApplicationContext());
                BaseInterface bs2 = new CollectionBS();
                Eexes exes1,exes2;
                Eprog prog;
                exes2=bs2.getEexes(10);
                prog=bs2.getEprog(1);
                bs1.setEprog(prog);
                prog=bs2.getEprog(3);
                bs1.setEprog(prog);
                prog=bs2.getEprog(2);
                bs1.setEprog(prog);
                exes1=bs1.getEexes(7);
                text.setText(exes1.getName());
                for(int i=0;i<30;i++){
                   int z=bs2.getExesType(i);
                    bs1.setExesType(i,z);
                    bs1.setExesTypeDescription(z, Types.search(z).getName());
                }
            }
        });
    }

}
