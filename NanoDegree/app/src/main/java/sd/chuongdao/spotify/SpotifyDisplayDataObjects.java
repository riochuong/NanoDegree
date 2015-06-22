package sd.chuongdao.spotify;

import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Image;

/**
 * Hold the corresponding Spotify data
 * Created by chuongdao on 6/21/15.
 */
public class SpotifyDisplayDataObjects {

    List<Artist> mArtistList;

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
        if (pos < mArtistList.size()) {

            int size = mArtistList.get(pos).images.size();

            Image lastImage = mArtistList.get(pos).images.get(size - 1);

            return lastImage.url;

        } else
            return null;
    }
}
