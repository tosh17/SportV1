package thstdio.sportv1.logic.date;

/**
 * Created by shcherbakov on 22.06.2017.
 */
import android.app.Application;
import net.danlew.android.joda.JodaTimeAndroid;

public class MyDate extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }

}