package thstdio.sportv1.display.abstract_package;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import thstdio.sportv1.R;
import thstdio.sportv1.logic.ETren.Types;
import thstdio.sportv1.logic.base.BaseInterface;
import thstdio.sportv1.logic.base.BaseLab;

/**
 * Created by shcherbakov on 14.06.2017.
 */

public class MyAsset {
    final static String  DIR_ICON="icon";
    final static String  DIR_EXES="exes_";
    final static String  STYLE_HALK="halk";
    final static String  STYLE_MUSKUL="muskul";
    private String styleIcon;
    private AssetManager mAssets;
    private Context mContext;
    private BaseInterface bs;

    InputStream data;
    public MyAsset(Context context){
        mContext=context;
        mAssets = context.getAssets();
        bs= BaseLab.getBS(context);
        styleIcon=STYLE_HALK;
    }
    public void setStyleIcon(String str){


        if(str.equals(mContext.getResources().getString(R.string.setting_exes_list_icon_style_item1))){
             styleIcon=STYLE_MUSKUL;
        }
         else if(str.equals(mContext.getResources().getString(R.string.setting_exes_list_icon_style_item2)))
        {
            styleIcon=STYLE_HALK;
        }


    }
    public Drawable getIcon(int id) {
         id=bs.getExesType(id);
         id=id==-1?0:id;
         String assetPath=DIR_ICON+"/"+DIR_EXES+styleIcon+"/";

        String str="";
         try {
             Types t=Types.search(id);
         str=t.getName();

         } catch (Exception e) {
             str=getExesType(id);
             Log.e("XXX","Проблема для " + id);
             e.printStackTrace();
         }
         assetPath=assetPath+ str+"_128.png";
         try {

             data = mAssets.open(assetPath);
             Drawable d = Drawable.createFromStream(data, null);
             Log.i("XXX","Load  " + assetPath);
             return d;
         } catch (IOException e) {
             Log.e("XXX","Проблема загрузки  " + assetPath);
             e.printStackTrace();
         }
        return null;
     }
    private String getExesType(int id){
        switch(id){
            case 0:
                return "all";
            case 1:
                return "arms";
            case 2:
                return "chest";
            case 3:
                return "back";
            case 4:
                return "press";
            case 5:
                return "legs";
            case 6:
                return "shoulders";
            default:
                return "all";
        }
    }
}
