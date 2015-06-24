package sd.chuongdao.spotify;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;

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
            String imageUrl = SpotifyStreamerUtils.getUrlThumbnail(artist.images);

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
