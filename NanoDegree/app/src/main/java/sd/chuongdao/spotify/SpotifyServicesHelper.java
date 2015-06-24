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
        ArtistsPager res = null;

        try {
            Log.d(TAG, "Query Spotify DB !!");
            if (name != null && !name.trim().equalsIgnoreCase(""))
                res = spServ.searchArtists(name);
        } catch (Exception e) {
            Log.e(TAG, "SPOTIFY ARTIST QUERY SERVICE ERROR -- TRY TO CATCH NETWORK ERROR !!!! ");
            e.printStackTrace();
        }

        return res;
    }


    public Tracks getTopTracksFromId(String id) {

        Tracks res = null;

        try {
            Log.v(TAG," Query db for top tracks of ID "+id);

            Map<String,Object> options = new HashMap<>();

            options.put(SpotifyService.COUNTRY, "US");
            //hardcoded US for now ...will put it in settings
            // later

            res =  spServ.getArtistTopTrack(id.trim(),options);

        } catch (Exception e) {
            Log.e(TAG, "SPOTIFY GET TOP 10SERVICE ERROR -- TRY TO CATCH NETWORK ERROR !!!! ");
            e.printStackTrace();
        }

        return res;
    }





    //GETTERS
    public SpotifyService getSpServ() {
        return spServ;
    }

    public SpotifyApi getSpotifyApi() {

        return spotifyApi;
    }
}
