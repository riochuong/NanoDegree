package sd.chuongdao.spotify;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Tracks;

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
     * null --  if the string is invalid
     */
    public ArtistsPager searchForArtist (String name){

        Log.d(TAG, "Query Spotify DB !!");
        if (name != null && !name.trim().equalsIgnoreCase(""))
            return spServ.searchArtists(name);

        return null;
    }


    public Tracks getTopTracksFromId(String id) {
        Log.v(TAG," Query db for top tracks of ID "+id);

        Map<String,Object> options = new HashMap<>();

        options.put(SpotifyService.COUNTRY, "US");
        //hardcoded US for now ...will put it in settings
        // later

        return spServ.getArtistTopTrack(id.trim(),options);
    }





    //GETTERS
    public SpotifyService getSpServ() {
        return spServ;
    }

    public SpotifyApi getSpotifyApi() {

        return spotifyApi;
    }
}
