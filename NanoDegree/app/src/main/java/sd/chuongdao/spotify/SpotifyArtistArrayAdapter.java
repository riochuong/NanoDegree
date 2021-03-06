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
public class SpotifyArtistArrayAdapter extends ArrayAdapter {

    Context mContext;
    SpotifyArtistDisplayDataObjects mSpotifyData;


    // Constants
    private final String TAG = this.getClass().getSimpleName();
    private static  final Integer THUMB_MAX_WIDTH = 100;

    private static final Integer THUMB_MAX_HEIGHT = 100;



    // holder class for improve scrolling exp.
    private static class ViewHolder {
        TextView artName;
        ImageView imgView;
    }


    public SpotifyArtistArrayAdapter(Context context, SpotifyArtistDisplayDataObjects obj) {
        super(context, -1); // we overrided getView as below
        mContext = context;
        mSpotifyData = obj;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            // get the layout inflater service
            LayoutInflater viewInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            holder = new ViewHolder();
            Log.d(TAG, "Inflate List View");
            // inflate the view with coresponding data...
            convertView = viewInflater.inflate(R.layout.artist_item_view, null);

            // find the correspoding data...
            holder.artName = (TextView) convertView.findViewById(R.id.artistTextView);
            holder.imgView = (ImageView) convertView.findViewById(R.id.artistImageView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // now set the data...
        if (mSpotifyData != null ) {

            String artistName = mSpotifyData.getArtistAt(position).getName();
            String thumbNailURl = mSpotifyData.getArtistAt(position).getImageUrl();

            // check valid data and set correct data
            if (artistName != null)
                holder.artName.setText(artistName);

            if (thumbNailURl != null){
                // load image
                Picasso.with(mContext).load(Uri.parse(thumbNailURl)).resize(THUMB_MAX_WIDTH
                        ,THUMB_MAX_HEIGHT).into(holder.imgView);
            } else {
                Picasso.with(mContext).load(android.R.drawable.btn_star_big_on).resize(THUMB_MAX_WIDTH
                        ,THUMB_MAX_HEIGHT).into(holder.imgView);
            }
        }
        return convertView;

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
    public void setSpotifyData(SpotifyArtistDisplayDataObjects mSpotifyData) {
        this.mSpotifyData = mSpotifyData;
    }
}
