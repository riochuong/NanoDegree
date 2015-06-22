package sd.chuongdao.spotify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import sd.chuongdao.nanodegree.R;

public class SpotifyTop10Activity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify_top10);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_spotify_top10, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * helper to create intent to launch top10 tracks activity with extra
     * @param id
     * @return
     */
    public static Intent getLaunchIntentWithArtisId (Context ctx, String id) {

        Intent intent = new Intent(ctx,SpotifyTop10Activity.class);
        intent.putExtra(SpotifyStreamerUtils.ARTIST_ID,id);
        return intent;

    }
}
