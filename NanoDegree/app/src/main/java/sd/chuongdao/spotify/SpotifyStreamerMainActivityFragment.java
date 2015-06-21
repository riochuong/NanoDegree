package sd.chuongdao.spotify;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sd.chuongdao.nanodegree.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class SpotifyStreamerMainActivityFragment extends Fragment {

    public SpotifyStreamerMainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_spotify_streamer_main, container, false);
    }
}
