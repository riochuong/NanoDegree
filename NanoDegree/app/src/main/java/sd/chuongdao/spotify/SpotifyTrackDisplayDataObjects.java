package sd.chuongdao.spotify;

import android.util.Log;

import java.util.List;

import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;

/**
 * Hold the corresponding Spotify data for Track
 * Created by chuongdao on 6/21/15.
 */
public class SpotifyTrackDisplayDataObjects {

    List<Track> mTrackList;

    private final String TAG = this.getClass().getSimpleName();

    public SpotifyTrackDisplayDataObjects(List<Track> mTrackList) {
        this.mTrackList = mTrackList;
    }

    /**
     * getter for retreiving artist info
     * @param pos
     * @return
     */
    public Track getTracktAt (int pos){
        if (pos < mTrackList.size()) {
            return mTrackList.get(pos);
        } else
            return null;
    }

    /**
     * getter for retreiving album name
     * @param pos
     * @return
     */
    public String getAlbumNameAt (int pos){
        if (pos < mTrackList.size()) {
            return getTracktAt(pos).album.name;
        } else
            return null;
    }


    public String getUrlThumbnail (int pos ){

        // assume last image should be smallest
        if ((pos >= 0) && (pos < mTrackList.size())
                        && (mTrackList.size() > 0)) {

            Track currTrack = mTrackList.get(pos);

            List<Image> listImgs = getTrackImages(currTrack);

            //extract size
            int listImageSize = (listImgs == null )? 0 : listImgs.size() ;

            // if there is no image ...just return null
            if (listImageSize == 0) {
                Log.d(TAG, "No image available");
                return null;
            }

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
        if (mTrackList != null)
            return mTrackList.size();
        else
            return 0;
    }

    // get list of images for track
    public List<Image> getTrackImages(Track tr) {
        if (tr != null)
            return tr.album.images;
        else
            return null;
    }
}
