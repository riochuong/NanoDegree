package sd.chuongdao.spotify;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import sd.chuongdao.nanodegree.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class SpotifyStreamerMainActivityFragment extends Fragment
                                                implements TextView.OnEditorActionListener,
                                                            ListView.OnItemClickListener {

    ListView artistListResultView;

    EditText artistSearchTextView;

    SpotifyArtistArrayAdapter artistResultAdapter;

    SpotifyArtistDisplayDataObjects mCurrentDisplayData;

    Toast mToastArtistNotFound;

    SpotifyArtistQueryTask mCurrentQueryTask;

    private final SpotifyArtistDisplayDataObjects EMPTY_ARTIST_DATA =
            new SpotifyArtistDisplayDataObjects(new ArrayList<Artist>());



    //SpotifyServicesHelper mSpotifyServiceHelper;


    // CONSTANTS
    private final String TAG = this.getClass().getSimpleName();

    private static final String ARTIST_NOT_FOUND = "THERE WAS NO ARTIST MATCHED";

    public SpotifyStreamerMainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize Spotify services for the life cycle of the fragment
        //mSpotifyServiceHelper = new SpotifyServicesHelper();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_spotify_streamer_main, container, false);

        // find the list view
        artistListResultView = (ListView) rootView.findViewById(R.id.artistResultListView);

        artistSearchTextView = (EditText) rootView.findViewById(R.id.artistSearchTextView);

        // set editor actions for search
        artistSearchTextView.setOnEditorActionListener(this);


        // add text watcher to edit text to trigger search when artist name is entered
        artistSearchTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG,"Text changed ...re-query");



                //queryNewDataFromSpotify(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {
                queryNewDataFromSpotify(editable.toString());
            }

        });

        artistResultAdapter = new SpotifyArtistArrayAdapter(getActivity(),null);

        artistListResultView.setAdapter(artistResultAdapter);

        artistListResultView.setOnItemClickListener(this);

        return rootView;
    }


    /**
     * Helper used for updating ListView Data
     * make sure that
     */

    private void updateListViewData (SpotifyArtistDisplayDataObjects newDisplayData) {

        // set current data
        mCurrentDisplayData = newDisplayData;

        // update result list
        if (artistResultAdapter != null ){
            // switched to good adapter
            artistResultAdapter.setSpotifyData(mCurrentDisplayData);

            Log.v(TAG,"Set new data point for Adapter");
        } else {
            artistResultAdapter = new SpotifyArtistArrayAdapter(getActivity(),newDisplayData);
        }

        // make sure data get updated correctly
        Log.v(TAG,"Notify changes in data set");
        artistResultAdapter.notifyDataSetChanged();
        artistListResultView.setAdapter(artistResultAdapter);


    }

    /**
     * Cancel current query task if text changed or done button is hitted again
     */
    private void cancelCurrentQueryTask() {
        if (mCurrentQueryTask != null
                && (!mCurrentQueryTask.isCancelled()))
            mCurrentQueryTask.cancel(true);
    }

    /**
     * start the new asynctask to query data from spotify
     * this will include cancelling old query task which have not
     * been finished
     * @param artist
     */
    private void queryNewDataFromSpotify(String artist) {

            //cancel old task if need to
            cancelCurrentQueryTask();

            // make sure there is available network
            if (SpotifyStreamerUtils.isNetworkAvailable(getActivity())){
                // dont bother to query empty string...which is wasted of resoursec
                if (artist != null
                        && (!(artist.trim().equalsIgnoreCase("")))){
                    mCurrentQueryTask = new SpotifyArtistQueryTask();
                    mCurrentQueryTask.execute(artist);
                } else {
                    updateListViewData(EMPTY_ARTIST_DATA);
                }
            } else {
                updateListViewData(EMPTY_ARTIST_DATA);
            }

    }


    /**
     * Display the toast to let user know that their artist was not found
     */
    private void displayToastForNotFoundArtist () {
        // first let cancel the old toast if there is one
        if (mToastArtistNotFound != null)
            mToastArtistNotFound.cancel();

        mToastArtistNotFound = Toast.makeText(getActivity(),ARTIST_NOT_FOUND,Toast.LENGTH_SHORT);
        mToastArtistNotFound.show();
    }




    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        boolean handled = false;
        if (i == EditorInfo.IME_ACTION_SEARCH){
            // extract artist name and send it to other function for handling
            String artistName = textView.getText().toString();

            Log.v(TAG,"Search Spotify for Artist : " + artistName);

            // Query artist name
            queryNewDataFromSpotify(artistName);

            handled = true;
        }

        return handled;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // initiate an Itent to get the top tracks
        String artistId = mCurrentDisplayData.getArtistAt(i).id;
        // now launch top 10 Activity to display results
        startActivity(SpotifyTop10Activity.getLaunchIntentWithArtisId(getActivity(), artistId));
    }


    /**
     * Helper class used for querying spotify data
     */
    private class SpotifyArtistQueryTask extends AsyncTask<String,String,SpotifyArtistDisplayDataObjects> {

        @Override
        protected SpotifyArtistDisplayDataObjects doInBackground(String... strings) {
            // assume the fist element is artist name

            String artName = strings[0];

            ArtistsPager artistsPager = SpotifyStreamerUtils.mSpotifyHelper.searchForArtist(artName);


            if (artistsPager != null){

                List<Artist> results = artistsPager.artists.items;

                // save Artist results Name
                SpotifyArtistDisplayDataObjects newData = new SpotifyArtistDisplayDataObjects(results);

                Log.d(TAG," There are  " + results.size() +" matched data found !!!");

                return newData;

            } else {
                Log.d(TAG,"NO DATA related to "+artName+" was found");
            }


            return null;

        }

        @Override
        protected void onPostExecute(SpotifyArtistDisplayDataObjects s) {

            Log.d(TAG,"Update data on Post Exec of Spotify Query Task");
            // dont have to update data if artist not found
            if (s != null && s.getSizeOfData() > 0)
                updateListViewData(s);
            else {
                displayToastForNotFoundArtist(); // let user know we cannot find there artist
                updateListViewData(EMPTY_ARTIST_DATA);
            }
        }
    }
}
