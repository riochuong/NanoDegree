package sd.chuongdao.spotify;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Image;

/**
 * Hold the corresponding Spotify data
 * Created by chuongdao on 6/21/15.
 */
public class SpotifyArtistDisplayDataObjects implements Parcelable {

    List<MyArtistParceleable> mArtistList;

    private final String TAG = this.getClass().getSimpleName();

    public SpotifyArtistDisplayDataObjects(List<Artist> artistList) {

        mArtistList = new ArrayList<>();

        // now construct the new artist list
        for (int i = 0; i < artistList.size(); i++){

            Artist artist = artistList.get(i);
            // extract data from artist List
            String name = artist.name;
            String id = artist.id;
            String imageUrl = getUrlThumbnail(i,artistList);

            // construct parceleable object
            MyArtistParceleable newArtist = new MyArtistParceleable(
                name, id, imageUrl
            );

            // put it to the array list
            mArtistList.add(newArtist);

        }
    }

    /**
     * getter for retreiving artist info
     * @param pos
     * @return
     */
    public MyArtistParceleable getArtistAt (int pos){
        if (pos < mArtistList.size()) {
            return mArtistList.get(pos);
        } else
            return null;
    }

    private String getUrlThumbnail (int pos, List<Artist> artistList ){

        // assume last image should be smallest
        if ((pos >= 0) && (pos < artistList.size())
                        && (artistList.size() > 0)) {


            int listImageSize = artistList.get(pos).images.size();

            // if there is no image ...just return null
            if (listImageSize == 0) {
                Log.d(TAG, "No image available");
                return null;
            }

            List<Image> listImgs = artistList.get(pos).images;

            Image lastImage = listImgs.get(SpotifyStreamerUtils.getCorrectImageIndex(listImgs));

            return lastImage.url;

        } else
            return null;
    }

    /**
     * Consturctor for Parcelable object
     * @param in
     */
    public SpotifyArtistDisplayDataObjects (Parcel in) {
        mArtistList = new ArrayList<>();
        in.readTypedList(mArtistList, MyArtistParceleable.CREATOR);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(mArtistList);

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public SpotifyArtistDisplayDataObjects createFromParcel(Parcel in) {
            return new SpotifyArtistDisplayDataObjects(in);
        }

        public SpotifyArtistDisplayDataObjects[] newArray(int size) {
            return new SpotifyArtistDisplayDataObjects[size];
        }
    };
}
