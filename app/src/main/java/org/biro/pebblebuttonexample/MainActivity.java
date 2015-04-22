package org.biro.pebblebuttonexample;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.biro.pebble.Pebble;
import org.biro.pebble.PebbleException;
import org.biro.pebble.PebbleTextLayer;
import org.biro.pebble.PebbleWindow;

import java.io.PrintWriter;
import java.io.StringWriter;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = "PebbleButton: ";

    Pebble mPebble;
    PebbleWindow mPebbleWindow;
    PebbleTextLayer mPebbleTextLayer;
    PebbleButtonReceiver mButtonReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
            TextView tv = (TextView) findViewById(R.id.textView);
            tv.setText("None");

            mButtonReceiver = new PebbleButtonReceiver(this);
            // This is the same UUID as hello, world.  Make sure you
            // don't run both apps at once or bad things will happen.
            try {
                mPebble = new Pebble();
                mPebble.setPebbleAppUUID("9312d524-6e77-47e4-96ed-e67bd11ce1d5");
                mPebble.registerHandlers(getApplicationContext());

                // Set up the pebbles stuff and turn it on.
                mPebbleWindow = PebbleWindow.getRootWindow();
                mPebbleTextLayer = new PebbleTextLayer();

                mPebbleWindow.setParent(mPebble);
                mPebbleTextLayer.setText("None");

                mPebbleWindow.addLayer(mPebbleTextLayer);
                mPebbleWindow.update(getApplicationContext());

            } catch (PebbleException e) {
                e.printStackTrace();
            }

            // register a broadcast receiver to get the button press messages.
            // Now ask for some button presses.

            IntentFilter filter = new IntentFilter();
            filter.addAction(Pebble.ACTION_BUTTON_PRESS);

            registerReceiver(mButtonReceiver, filter);

            for (int i = 0; i < Pebble.BUTTON_NUM_BUTTONS; ++i) {
                mPebbleWindow.setClickRequests(i);
            }
            mPebbleWindow.requestClicks(getApplicationContext());
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String msg = e.toString() + "\n" + sw.toString();
            Log.e(TAG, msg);
        }

    }

    private static final String[] mButtonNames = new String[] {
        "Back", "Up", "Select", "Down"
    };


    public void buttonPress(int button) {
        TextView tv = (TextView)findViewById(R.id.textView);
        tv.setText(mButtonNames[button]);
        mPebbleTextLayer.setText(mButtonNames[button]);
        mPebbleWindow.update(getApplicationContext());
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
}
