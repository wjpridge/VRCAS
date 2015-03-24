package com.team26.will.vrcas;

import com.team26.will.vrcas.util.SystemUiHider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.IOException;
import java.lang.String;

import static com.team26.will.vrcas.SplashScreen.fOut;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class VR_BALANCE_TEST extends Activity implements OnCompletionListener, SensorEventListener {
//    /**
//     * Whether or not the system UI should be auto-hidden after
//     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
//     */
//    private static final boolean AUTO_HIDE = true;
//
//    /**
//     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
//     * user interaction before hiding the system UI.
//     */
//    private static final int AUTO_HIDE_DELAY_MILLIS = 0;
//
//    /**
//     * If set, will toggle the system UI visibility upon interaction. Otherwise,
//     * will show the system UI visibility upon interaction.
//     */
//    private static final boolean TOGGLE_ON_CLICK = true;
//
//    /**
//     * The flags to pass to {@link SystemUiHider#getInstance}.
//     */
//    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;
//
//    /**
//     * The instance of the {@link SystemUiHider} for this activity.
//     */
//    private SystemUiHider mSystemUiHider;

    SensorManager sensorManager;
    double ax,ay,az;
    long count = 0;
    long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);

        int currentApiVersion;
        currentApiVersion = android.os.Build.VERSION.SDK_INT;
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        if(currentApiVersion >= Build.VERSION_CODES.KITKAT)
        {

            getWindow().getDecorView().setSystemUiVisibility(flags);

            // Code below is to handle presses of Volume up or Volume down.
            // Without this, after pressing volume buttons, the navigation bar will
            // show up and won't hide
            final View decorView = getWindow().getDecorView();
            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener()
                    {
                        @Override
                        public void onSystemUiVisibilityChange(int visibility)
                        {
                            if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0)
                            {
                                decorView.setSystemUiVisibility(flags);
                            }
                        }
                    });
        }

        setContentView(R.layout.activity_video_plane);

        VideoView v = (VideoView) findViewById(R.id.videoView);

        String url = null;
        url = "android.resource://" + getPackageName() + "/raw/"+ R.raw.video1b;

            if (url != null) {
                v.setMediaController(new MediaController(this));
                v.setOnCompletionListener(this);
                v.setVideoURI(Uri.parse(url));
                v.start();

            }

        if (url == null) {
            throw new IllegalArgumentException("Must set url extra paremeter in intent.");
        }
    }

    @Override
    public void onCompletion(MediaPlayer v) {
        try {
            fOut.flush();
            VR_BALANCE_TEST.this.startActivity(new Intent(VR_BALANCE_TEST.this, BESS_TEST1.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        finish();
    }

    @Override
    public void onPause(){
        super.onPause();
        try {
            fOut.write("3D Video Paused");
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sensorManager.unregisterListener(this);
    }

    //Convenience method to show a video
    public static void showRemoteVideo(Context ctx, String url) {
        Intent i = new Intent(ctx, VR_BALANCE_TEST.class);

        i.putExtra("url", url);
        ctx.startActivity(i);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            ax=event.values[0];
            ay=event.values[1];
            az=event.values[2];
            time = System.currentTimeMillis();
            if(time > (count + 5)) {
                count = time;
                writeACC();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void writeACC() {
        System.out.println(count);
        try {
            fOut.write(count + ": " + String.valueOf(ax) + ", " + String.valueOf(ay) + ", " + String.valueOf(az) + ";\n");
            fOut.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

