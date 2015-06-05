package sd.chuongdao.nanodegree;

import android.content.Context;
import android.widget.Toast;

/**
 * A collection of common use methods across the application
 * Created by chuongdao on 6/5/15.
 */
public class Utils {

    /**
     * Helper for displaying Toast
     * @param ctx
     * @param text
     */
    public static void makeToast(Context ctx, String text) {
        Toast.makeText(ctx,text,Toast.LENGTH_LONG).show();
    }

}
