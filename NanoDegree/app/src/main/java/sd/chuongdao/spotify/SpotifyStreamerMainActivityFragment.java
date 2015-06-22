package sd.chuongdao.spotify;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;
import sd.chuongdao.nanodegree.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class SpotifyStreamerMainActivityFragment extends Fragment implements TextView.OnEditorActionListener {

    ListView artistListResultView;

    EditText artistSearchTextView;

    SpotifyArrayAdapter artistResultAdapter;

    SpotifyDisplayDataObjects mCurrentDisplayData;

    SpotifyServicesHelper mSpotifyServiceHelper;

    private final String TAG = this.getClass().getSimpleName();

    public SpotifyStreamerMainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize Spotify services for the life cycle of the fragment
        mSpotifyServiceHelper = new SpotifyServicesHelper();
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

        artistResultAdapter = new SpotifyArrayAdapter(getActivity(), null);

        artistListResultView.setAdapter(artistResultAdapter);

        return rootView;
    }


    /**
     * Helper used for updating ListView Data
     * make sure that
     */

    private void updateListViewData (SpotifyDisplayDataObjects newDisplayData) {
        // set current data
        mCurrentDisplayData = newDisplayData;

        // update result list
        if (artistResultAdapter != null ){
            artistResultAdapter.setSpotifyData(mCurrentDisplayData);
            Log.v(TAG,"Set new data point for Adapter");
        } else {
            artistResultAdapter = new SpotifyArrayAdapter(getActivity(),newDisplayData);
        }

        // make sure data get updated correctly
        Log.v(TAG,"Notify changes in data set");
        artistResultAdapter.notifyDataSetChanged();
        artistListResultView.setAdapter(artistResultAdapter);
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        boolean handled = false;
        if (i == EditorInfo.IME_ACTION_SEARCH){
            // extract artist name and send it to other function for handling
            String artistName = textView.getText().toString();

            Log.v(TAG,"Search Spotify for Artist : " + artistName);

            // Query artist name
            // TODO: call SPOTIFY API methods here -- maybe AsyncTask should work or Service
            new SpotifyQueryTask().execute(artistName);
            handled = true;
        }

        return handled;
    }


    /**
     * Helper class used for querying spotify data
     */
    private class SpotifyQueryTask extends AsyncTask<String,String,SpotifyDisplayDataObjects> {

        @Override
        protected SpotifyDisplayDataObjects doInBackground(String... strings) {
            // assume the fist element is artist name

            String artName = strings[0];

            List<Artist> results = mSpotifyServiceHelper.searchForArtist(artName).artists.items;

            if (results != null && results.size() > 0){

                // save Artist results Name
                SpotifyDisplayDataObjects newData = new SpotifyDisplayDataObjects(results);

                Log.d(TAG," There are  " + results.size() +" matched data found !!!");

                return newData;
            } else {
                Log.d(TAG,"NO DATA related to "+artName+" was found");
            }


            return null;

        }

        @Override
        protected void onPostExecute(SpotifyDisplayDataObjects s) {

            Log.d(TAG,"Update data on Post Exec of Spotify Query Task");

            updateListViewData(s);
        }
    }
}
