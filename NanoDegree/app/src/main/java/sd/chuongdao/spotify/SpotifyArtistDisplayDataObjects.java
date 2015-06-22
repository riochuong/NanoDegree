package sd.chuongdao.spotify;

import android.util.Log;

import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Image;

/**
 * Hold the corresponding Spotify data
 * Created by chuongdao on 6/21/15.
 */
public class SpotifyArtistDisplayDataObjects {

    List<Artist> mArtistList;



    private final String TAG = this.getClass().getSimpleName();

    public SpotifyArtistDisplayDataObjects(List<Artist> mArtistList) {
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

            Image lastImage = listImgs.get(SpotifyStreamerUtils.getCorrectImageIndex(listImgs));

            return lastImage.url;

        } else
            return null;
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
