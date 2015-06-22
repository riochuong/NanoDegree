package sd.chuongdao.spotify;

import android.util.Log;

import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Image;

/**
 * Hold the corresponding Spotify data
 * Created by chuongdao on 6/21/15.
 */
public class SpotifyDisplayDataObjects {

    List<Artist> mArtistList;

    private static final int THUMBNAIL_WIDTH_MAX = 200;
    private static final int THUMBNAIL_HEIGHT_MAX = 200;

    private final String TAG = this.getClass().getSimpleName();

    public SpotifyDisplayDataObjects(List<Artist> mArtistList) {
        this.mArtistList = mArtistList;
    }

    /**
     * getter for retreiving artist info
     * @param pos
     * @return
     */
    public Artist getArtistAt (int pos){
        if (pos < mArtistList.size()) {
            return mArtistList.get(pos);
        } else
            return null;
    }

    public String getUrlThumbnail (int pos ){

        // assume last image should be smallest
        if ((pos >= 0) && (pos < mArtistList.size())
                        && (mArtistList.size() > 0)) {
            

            int listImageSize = mArtistList.get(pos).images.size();

            // if there is no image ...just return null
            if (listImageSize == 0) {
                Log.d(TAG, "No image available");
                return null;
            }

            List<Image> listImgs = mArtistList.get(pos).images;

            Image lastImage = listImgs.get(getCorrectImageIndex(listImgs));

            return lastImage.url;

        } else
            return null;
    }


    /**
     * check array list of image for the correct thumbnaisl index
     * should be the biggest image within the specified max sizes
     * @param listImgs
     * @return
     */
    private int getCorrectImageIndex(List<Image> listImgs) {

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
     * get number of elements need to be displayed
     * @return
     */
    public int getSizeOfData () {
        if (mArtistList != null)
            return mArtistList.size();
        else
            return 0;
    }
}
