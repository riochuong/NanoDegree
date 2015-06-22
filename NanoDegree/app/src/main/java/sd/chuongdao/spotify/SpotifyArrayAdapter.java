package sd.chuongdao.spotify;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import sd.chuongdao.nanodegree.R;

/**
 * Created by chuongdao on 6/21/15.
 */
public class SpotifyArrayAdapter extends ArrayAdapter {

    Context mContext;
    SpotifyDisplayDataObjects mSpotifyData;
    private final String TAG = this.getClass().getSimpleName();

    public SpotifyArrayAdapter(Context context, SpotifyDisplayDataObjects obj) {
        super(context, -1); // we overrided getView as below
        mContext = context;
        mSpotifyData = obj;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get the layout inflater service
        LayoutInflater viewInflater = (LayoutInflater) mContext.
                                            getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Log.d(TAG, "Inflate List View");
        // inflate the view with coresponding data...
        View rowView = viewInflater.inflate(R.layout.artist_item_view, null);

        // find the correspoding data...
        TextView artName = (TextView) rowView.findViewById(R.id.artistTextView);
        ImageView imgView = (ImageView) rowView.findViewById(R.id.artistImageView);



        // now set the data...
        if (mSpotifyData != null ) {

            String artistName = mSpotifyData.getArtistAt(position).name;
            String thumbNailURl = mSpotifyData.getUrlThumbnail(position);

            // check valid data and set correct data
            if (artistName != null)
                artName.setText(artistName);

            if (thumbNailURl != null){
                // load image
                Picasso.with(mContext).load(Uri.parse(thumbNailURl)).into(imgView);
            }
        }
        return rowView;

    }

    @Override
    public int getCount() {

        if (mSpotifyData == null )
            return 0;



        return mSpotifyData.getSizeOfData();

    }

    /**
     * Setter used for updating data
     * @param mSpotifyData
     */
    public void setSpotifyData(SpotifyDisplayDataObjects mSpotifyData) {
        this.mSpotifyData = mSpotifyData;
    }
}
