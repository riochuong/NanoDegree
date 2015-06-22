package sd.chuongdao.spotify;

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

}
