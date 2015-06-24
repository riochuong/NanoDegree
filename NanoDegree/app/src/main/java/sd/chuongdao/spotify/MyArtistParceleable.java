package sd.chuongdao.spotify;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Simplified to Parceleable object that we care about
 * Created by chuongdao on 6/24/15.
 */
public class MyArtistParceleable implements Parcelable {


    private String name;

    private String id;

    private String imageUrl;


    public MyArtistParceleable(String name, String id, String imageUrl) {
        this.name = name;
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public MyArtistParceleable(Parcel in ){
        this.name = in.readString();
        this.id = in.readString();
        this.imageUrl = in.readString();
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(id);
        parcel.writeString(imageUrl);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MyArtistParceleable createFromParcel(Parcel in) {
            return new MyArtistParceleable(in);
        }

        public MyArtistParceleable[] newArray(int size) {
            return new MyArtistParceleable[size];
        }
    };
}
