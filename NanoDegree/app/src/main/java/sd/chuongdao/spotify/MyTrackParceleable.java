package sd.chuongdao.spotify;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Simplified to Parceleable object that we care about
 * Created by chuongdao on 6/24/15.
 */
public class MyTrackParceleable implements Parcelable {


    private String name;

    private String albumName;
    

    private String imageUrl;


    public MyTrackParceleable(String name, String id, String imageUrl) {
        this.name = name;
        this.albumName = id;
        this.imageUrl = imageUrl;
    }

    public MyTrackParceleable(Parcel in){
        this.name = in.readString();
        this.albumName = in.readString();
        this.imageUrl = in.readString();
    }


    public String getName() {
        return name;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(albumName);
        parcel.writeString(imageUrl);
    }

    public static final Creator CREATOR = new Creator() {
        public MyTrackParceleable createFromParcel(Parcel in) {
            return new MyTrackParceleable(in);
        }

        public MyTrackParceleable[] newArray(int size) {
            return new MyTrackParceleable[size];
        }
    };
}
