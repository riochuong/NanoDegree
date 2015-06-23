package sd.chuongdao.nanodegree;

import android.content.Context;
import android.widget.Toast;

/**
 * A collection of common use methods across the application
 * Created by chuongdao on 6/5/15.
 */
public class Utils {


    static Toast mToast = null;

    /**
     * Helper for displaying Toast
     * @param ctx
     * @param text
     */
    public static void makeToast(Context ctx, String text) {
        if (mToast != null)
            mToast.cancel();
        // save the reference to help reduce waiting time when showing toast
        mToast = Toast.makeText(ctx,text,Toast.LENGTH_SHORT);
        mToast.show();
    }

}
