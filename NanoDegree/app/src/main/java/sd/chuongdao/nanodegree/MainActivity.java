package sd.chuongdao.nanodegree;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import sd.chuongdao.spotify.SpotifyStreamerMainActivity;


public class MainActivity extends ActionBarActivity implements OnClickListener {

    //BUTTONS REFS
    Button spotifyStreamerBtn = null;
    Button buildItBiggerBtn = null;
    Button scoresAppBtn = null;
    Button libraryAppBtn = null;
    Button xyzReaderBtn = null;
    Button capstoneAppBtn = null;

    public static final String TAG = "NANO_MAIN_ACT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get buttons refs
        spotifyStreamerBtn = (Button) this.findViewById(R.id.spotify_streamer_btn);
        buildItBiggerBtn = (Button) this.findViewById(R.id.build_it_bigger_btn);
        scoresAppBtn = (Button) this.findViewById(R.id.scores_app_btn);
        libraryAppBtn = (Button) this.findViewById(R.id.library_app_btn);
        xyzReaderBtn = (Button) this.findViewById(R.id.xyz_reader_btn);
        capstoneAppBtn = (Button) this.findViewById(R.id.capstone_app_btn);

        // set button listeners
        spotifyStreamerBtn.setOnClickListener(this);
        buildItBiggerBtn.setOnClickListener(this);
        scoresAppBtn.setOnClickListener(this);
        libraryAppBtn.setOnClickListener(this);
        xyzReaderBtn.setOnClickListener(this);
        capstoneAppBtn.setOnClickListener(this);

        Log.d(TAG, "On Create called!");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /** all button click events will be handle here **/
    @Override
    public void onClick(View view) {
        Log.d(TAG,"On Click called");
        
        switch(view.getId()) {
            case (R.id.spotify_streamer_btn):
                spotifyStreamerClicked();
                break;
            case (R.id.build_it_bigger_btn):
                buildItBiggerClicked();
                break;
            case (R.id.scores_app_btn):
                scoresAppClicked();
                break;
            case (R.id.library_app_btn):
                libraryAppClicked();
                break;
            case (R.id.xyz_reader_btn):
                xyzReaderClicked();
                break;
            case (R.id.capstone_app_btn):
                capstoneAppClicked();
                break;

        }
    }



    /** BUTTON FUNCTION HELPERS **/

    /**
     * this method use for responding to spotify streamer btn clicked
     */
    private void spotifyStreamerClicked() {
        // for now just launch a started activity when the button is pressed
        startActivity(SpotifyStreamerMainActivity.makeLaunchIntent(this));
        Utils.makeToast(this,"Launch Spotify Streamer");

    }

    /**
     * this method use for responding to Build it bigger btn clicked
     */
    private void buildItBiggerClicked() {
        Utils.makeToast(this,"Launch Build It Bigger");

    }

    /**
     * this method use for responding to scores App btn clicked
     */
    private void scoresAppClicked() {
        Utils.makeToast(this,"Launch Scores App");

    }

    /**
     * this method use for responding to library app btn clicked
     */
    private void libraryAppClicked() {
        Utils.makeToast(this,"LLaunch Library App");

    }

    /**
     * this method use for responding to xyz reader btn clicked
     */
    private void xyzReaderClicked() {
        Utils.makeToast(this,"Launch XYZ reader");

    }

    /**
     * this method use for responding to capstone  btn clicked
     */
    private void capstoneAppClicked() {
        Utils.makeToast(this,"Launch My CApstone APP");

    }



}
