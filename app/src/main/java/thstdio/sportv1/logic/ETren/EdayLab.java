package thstdio.sportv1.logic.ETren;

import android.content.Context;

import java.util.List;

/**
 * Created by shcherbakov on 01.06.2017.
 */

public class EdayLab {
    private static EdayLab sEdayLab;
    private Context mContext;
    private Eday mEday;

    private EdayLab(Context context) {
        mContext = context;
    }

    public static EdayLab get(Context context) {
        if (sEdayLab == null) {
            sEdayLab = new EdayLab(context);
        }
        return sEdayLab;
    }

    public void setEday(Eday mEday) {
        this.mEday = mEday;
    }
    public List<Eday.EdayList> getListExes(){
        return mEday.getList();
    }
    public Eday.EdayList getDayExes(int i) {

        return mEday.getEdayexes(i);
    }
}
