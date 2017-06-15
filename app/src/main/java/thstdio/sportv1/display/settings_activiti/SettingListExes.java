package thstdio.sportv1.display.settings_activiti;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;

import thstdio.sportv1.R;

/**
 * Created by shcherbakov on 15.06.2017.
 */

public class SettingListExes extends PreferenceActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting_list_exes);
    }
}
