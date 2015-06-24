package sd.chuongdao.spotify;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import sd.chuongdao.nanodegree.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class SpotifyTop10ActivityFragment extends Fragment {


    // VARIABLES
    ListView topTrackListView;

    SpotifyTrackDisplayDataObjects mCurrentTopTracksData;

    SpotifyTrackArrayAdapter trackAdapter;

    String mCurrentArtistID; // obtains from intents

    GetTopTrackTask mCurrentQueryTask;

    Toast mCurrentNoTrackFoundToast;




    // CONSTANTS
    private final SpotifyTrackDisplayDataObjects EMPTY_TRACK_DATA =
            new SpotifyTrackDisplayDataObjects(new ArrayList<Track>());

    private final String TAG = this.getClass().getSimpleName();

    private final String NO_TRACK_FOUND = " NO TRACK FOUND";

    private final String DATA_OBJECT_KEY = "DATA_OBJECT_KEY";

    public SpotifyTop10ActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_spotify_top10, container, false);

        topTrackListView = (ListView) root.findViewById(R.id.topTracksListView);


        trackAdapter = new SpotifyTrackArrayAdapter(getActivity(),null);

        topTrackListView.setAdapter(trackAdapter);

        return root;
    }

    /**
     * Helper to trigger querying data for top 10
     * @param 
     */
    private void queryDatafromIntent () {

        Bundle bundle = getActivity().getIntent().getExtras();

        String artistId = bundle.getString(SpotifyStreamerUtils.ARTIST_ID);

        if (artistId != null ){

            Log.d(TAG, " Query Spotify DB for top tracks");

            mCurrentArtistID = artistId;

            // Execute as async task
            queryNewTop10DataFromSpotify(mCurrentArtistID);

        }
        else
        {
            Log.e(TAG, " NO ARTIST ID PASSED THROUGH INTENT ... THIS SHOULD NOT HAPPENED");
            // close right away to avoid issue
            getActivity().finish();
        }
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

    /**
     * start the new asynctask to query data from spotify for top 10 tracks
     * this will include cancelling old query task which have not
     * been finished
     * @param id
     */
    private void queryNewTop10DataFromSpotify(String id) {

        //cancel old task if need to
        cancelCurrentQueryTask();

        // make sure there is available network
        if (SpotifyStreamerUtils.isNetworkAvailable(getActivity())){
            // dont bother to query empty string...which is wasted of resoursec
            if (id != null
                    && (!(id.trim().equalsIgnoreCase("")))){
                mCurrentQueryTask = new GetTopTrackTask();
                mCurrentQueryTask.execute(id);
            } else {
                updateDataList(EMPTY_TRACK_DATA);
            }
        } else {
            getActivity().finish(); //no need to keep activiy if network is down
        }

    }

    /**
     * display toast of no track found
     */
    private void displayToastForNoTrackFound() {
        if (mCurrentNoTrackFoundToast != null)
            mCurrentNoTrackFoundToast.cancel();

        mCurrentNoTrackFoundToast = Toast.makeText(getActivity(),NO_TRACK_FOUND,Toast.LENGTH_SHORT);
        mCurrentNoTrackFoundToast.show();
    }

    /**
     * Cancel current query task if text changed or done button is hitted again
     */
    private void cancelCurrentQueryTask() {
        if (mCurrentQueryTask != null
                && (!mCurrentQueryTask.isCancelled()))
            mCurrentQueryTask.cancel(true);
    }

    // make sure we saved data here
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(DATA_OBJECT_KEY,mCurrentTopTracksData);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // make sure it should not crashed
        if (savedInstanceState != null
                && savedInstanceState.containsKey(DATA_OBJECT_KEY)) {
            mCurrentTopTracksData = savedInstanceState.getParcelable(DATA_OBJECT_KEY);
            updateDataList(mCurrentTopTracksData);
        } else {
            queryDatafromIntent();
        }
    }

    /**
     * Async task classes used for Query top 10 tracks of an ARTIST
     */
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
            if (tracks != null && tracks.tracks.size() > 0)
                updateDataList(new SpotifyTrackDisplayDataObjects(tracks.tracks));
            else{
                displayToastForNoTrackFound();
                updateDataList(EMPTY_TRACK_DATA);
            }

        }
    }
}
