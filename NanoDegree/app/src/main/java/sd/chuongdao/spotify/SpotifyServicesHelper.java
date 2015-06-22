package sd.chuongdao.spotify;

import android.util.Log;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;

/**
 * Created by chuongdao on 6/21/15.
 */
public class SpotifyServicesHelper {

    SpotifyApi spotifyApi;

    SpotifyService spServ ;

    private  final String TAG = this.getClass().getSimpleName();

    /**
     * Construct Spotify Services object
     */

    public SpotifyServicesHelper() {
        this.spotifyApi = new SpotifyApi();
        this.spServ = spotifyApi.getService();
    }

    /**
     * Query Spotify DB for the given artist album
     * @param name - name of the
     * @return ArtistPager represent the list of all album results with the given name
     */
    public ArtistsPager searchForArtist (String name){

        Log.d(TAG, "Query Spotify DB !!");

        return spServ.searchArtists(name);
    }





    //GETTERS
    public SpotifyService getSpServ() {
        return spServ;
    }

    public SpotifyApi getSpotifyApi() {

        return spotifyApi;
    }
}
