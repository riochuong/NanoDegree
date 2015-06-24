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
public class SpotifyTrackArrayAdapter extends ArrayAdapter {

    Context mContext;
    SpotifyTrackDisplayDataObjects mSpotifyData;

    private static  final Integer THUMB_MAX_WIDTH = 100;

    private static final Integer THUMB_MAX_HEIGHT = 100;

    private final String TAG = this.getClass().getSimpleName();

    public SpotifyTrackArrayAdapter(Context context,  SpotifyTrackDisplayDataObjects obj) {
        super(context, -1); // we overrided getView as below
        mContext = context;
        mSpotifyData = obj;
    }


    private static class ViewHolder {
        TextView trackNameTextView;
        TextView trackAlbumNameTextView;
        ImageView imgView;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d(TAG, "Inflate Top Track List View");

        ViewHolder holder;

        if (convertView == null) {
            // get the layout inflater service
            holder = new ViewHolder();
            LayoutInflater viewInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // inflate the view with coresponding data...
            convertView = viewInflater.inflate(R.layout.top_track_item_view, null);

            // find the correspoding data...
            holder.trackNameTextView = (TextView) convertView.findViewById(R.id.topTrackNameTextView);
            holder.trackAlbumNameTextView = (TextView) convertView.findViewById(R.id.topTrackAlbumNameTextView);
            holder.imgView = (ImageView)convertView.findViewById(R.id.topTrackImageView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        // now set the data...
        if (mSpotifyData != null ) {

            String trackName = mSpotifyData.getTracktAt(position).getName();

            String thumbNailURl = mSpotifyData.getTracktAt(position).getImageUrl();

            String albumNAme = mSpotifyData.getAlbumNameAt(position);

            // check valid data and set correct data
            if (trackName != null)
                holder.trackNameTextView.setText(trackName);

            if (albumNAme != null)
                holder.trackAlbumNameTextView.setText(albumNAme);

            if (thumbNailURl != null){
                // load image
                Picasso.with(mContext).load(Uri.parse(thumbNailURl)).resize(THUMB_MAX_WIDTH
                                                                    ,THUMB_MAX_HEIGHT).into(holder.imgView);
            } else {
                Picasso.with(mContext).load(android.R.drawable.stat_notify_voicemail).resize(THUMB_MAX_WIDTH
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
    public void setSpotifyData(SpotifyTrackDisplayDataObjects mSpotifyData) {
        this.mSpotifyData = mSpotifyData;
    }
}
