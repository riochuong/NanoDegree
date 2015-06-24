package sd.chuongdao.spotify;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import kaaes.spotify.webapi.android.models.Image;

/**
 * Created by chuongdao on 6/21/15.
 */
public class SpotifyStreamerUtils {

    static SpotifyServicesHelper mSpotifyHelper;

    private static final int THUMBNAIL_WIDTH_MAX = 200;
    private static final int THUMBNAIL_HEIGHT_MAX = 200;

    public static final String ARTIST_ID = "artist_id";

    private static final String TAG = "SPOTIFY_STREAMER_UTILS";

    private static final String NETWORK_NOT_AVAILABLE = " NO AVAILABLE NETWORK ";

    static Toast mToastNetworkNotAvailable;


    static {
        mSpotifyHelper = new SpotifyServicesHelper();
    }

    /**
     * check array list of image for the correct thumbnaisl index
     * should be the biggest image within the specified max sizes
     * @param listImgs
     * @return
     */
    public static int getCorrectImageIndex(List<Image> listImgs) {

        int selected = 0;
        int prevHeight = 0;
        int prevWidth = 0;


        if (listImgs.size() <= 0)
            return -1;


        for (int i = 0; i < listImgs.size(); i++){
            Image img = listImgs.get(i);

            // verify that new thumbnails should be bigger
            // but within range
            if( (img.height <= THUMBNAIL_HEIGHT_MAX
                    && img.height > prevHeight) &&
                    (img.width <= THUMBNAIL_WIDTH_MAX
                            && img.width > prevWidth)    )  {

                selected = i;
                prevHeight = img.height;
                prevWidth = img.width;

            }

        }

        return selected;

    }

    /**
     * Function helps to determine if network is available
     * @param ctx
     * @return - true if device is connected to an avaialable network
     * false - other wise
     */
    public static boolean isNetworkAvailable(Context ctx) {

        if (ctx != null ){
            // get system service
            ConnectivityManager connectivityManager = (ConnectivityManager)
                                                ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

            // obtain network info
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            // only this statement can be true
            if (networkInfo != null
                    && networkInfo.isConnected()
                    && networkInfo.isAvailable())
            {
                return true;
            }

        }

        Log.e(TAG, "Cannot get Network Service as context is null");
        displayNetworkNotAvailable(ctx);
        return false;

    }

    /**
     * Let user know when network is not available
     * @param ctx
     */
    public static void displayNetworkNotAvailable(Context ctx){
        if (mToastNetworkNotAvailable != null)
            mToastNetworkNotAvailable.cancel();

        mToastNetworkNotAvailable = Toast.makeText(ctx,NETWORK_NOT_AVAILABLE,Toast.LENGTH_SHORT);

        mToastNetworkNotAvailable.show();
    }

    public static String getUrlThumbnail (List<Image> imgList ){

        // assume last image should be smallest
        if (imgList.size() > 0) {




            // if there is no image ...just return null
            if (imgList.size() == 0) {
                Log.d(TAG, "No image available");
                return null;
            }

            Image lastImage = imgList.get(getCorrectImageIndex(imgList));

            return lastImage.url.toString();

        } else
            return null;
    }

}
