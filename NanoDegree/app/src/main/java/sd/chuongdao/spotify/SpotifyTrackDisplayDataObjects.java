package sd.chuongdao.spotify;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;

/**
 * Hold the corresponding Spotify data for Track
 * Created by chuongdao on 6/21/15.
 */
public class SpotifyTrackDisplayDataObjects implements Parcelable {

    List<MyTrackParceleable> mTrackList;

    private final String TAG = this.getClass().getSimpleName();

    public SpotifyTrackDisplayDataObjects(List<Track> trackList) {

        this.mTrackList =  new ArrayList<>();

        // parse data
        for (int i = 0; i < trackList.size(); i++){
            Track track = trackList.get(i);

            String name = track.name;
            String album = track.album.toString();
            String imageUrl = SpotifyStreamerUtils.getUrlThumbnail(track.album.images);

            MyTrackParceleable newTrack = new MyTrackParceleable(
                    name,  album,    imageUrl
            );

            mTrackList.add(newTrack);

        }
    }



    /**
     * Consturctor for Parcelable object
     * @param in
     */
    public SpotifyTrackDisplayDataObjects (Parcel in) {
        mTrackList = new ArrayList<>();
        in.readTypedList(mTrackList, MyTrackParceleable.CREATOR);
    }


    /**
     * getter for retreiving artist info
     * @param pos
     * @return
     */
    public MyTrackParceleable getTracktAt (int pos){
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
            return getTracktAt(pos).getAlbumName();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
            parcel.writeTypedList(mTrackList);
    }

    // required constructor
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public SpotifyTrackDisplayDataObjects createFromParcel(Parcel in) {
            return new SpotifyTrackDisplayDataObjects(in);
        }

        public SpotifyTrackDisplayDataObjects[] newArray(int size) {
            return new SpotifyTrackDisplayDataObjects[size];
        }
    };
}
