package sd.chuongdao.spotify;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import kaaes.spotify.webapi.android.models.Tracks;
import sd.chuongdao.nanodegree.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class SpotifyTop10ActivityFragment extends Fragment {



    ListView topTrackListView;

    SpotifyTrackDisplayDataObjects mCurrentTopTracksData;

    SpotifyTrackArrayAdapter trackAdapter;

    String mCurrentArtistID; // obtains from intents

    private final String TAG = this.getClass().getSimpleName();

    public SpotifyTop10ActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_spotify_top10, container, false);

        topTrackListView = (ListView) root.findViewById(R.id.topTracksListView);


        Bundle bundle = getActivity().getIntent().getExtras();

        String artistId = bundle.getString(SpotifyStreamerUtils.ARTIST_ID);

        trackAdapter = new SpotifyTrackArrayAdapter(getActivity(),null);

        topTrackListView.setAdapter(trackAdapter);

        if (artistId != null ){

            Log.d(TAG, " Query Spotify DB for top tracks");

            mCurrentArtistID = artistId;

            // Execute as async task
            new GetTopTrackTask().execute(mCurrentArtistID);

        }
        else
        {
            Log.d(TAG, " NO ARTIST ID PASSED THROUGH INTENT ... THIS SHOULD NOT HAPPENED");
        }

        return root;
    }

    /**
     * update adapter data
     */
    private void updateDataList (SpotifyTrackDisplayDataObjects newData) {
        mCurrentTopTracksData = newData;
        trackAdapter.setSpotifyData(mCurrentTopTracksData);
        trackAdapter.notifyDataSetChanged();
        Log.v(TAG,"UPDATE TOP TRACK LIST");
    }


    private class GetTopTrackTask extends AsyncTask<String,String,Tracks>{

        @Override
        protected Tracks doInBackground(String... strings) {

            // assume first element should be artis ID
            String id = strings[0];

            Log.d(TAG,"ARTIST ID : "+id);

            Tracks topTracks = SpotifyStreamerUtils.mSpotifyHelper.getTopTracksFromId(id);

            return topTracks;


        }


        @Override
        protected void onPostExecute(Tracks tracks) {
            // update current track list
            updateDataList(new SpotifyTrackDisplayDataObjects(tracks.tracks));
        }
    }
}
