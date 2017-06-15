package thstdio.sportv1.display.abstract_package;

import thstdio.sportv1.R;

/**
 * Created by shcherbakov on 11.06.2017.
 */

public class MyRes {
    public static int getResTypeProg(String prog) {
        int id=Integer.parseInt(prog);
        switch (id) {
            case 1:
                return R.drawable.zal;
            case 2:
                return R.drawable.home;
            case 3:
                return R.drawable.street;
           default:
               return R.drawable.zal;
        }
    }
}
