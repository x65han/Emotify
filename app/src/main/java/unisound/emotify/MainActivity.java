package unisound.emotify;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static boolean serviceEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(unisound.emotify.R.layout.activity_main);
        enableService();
        // create Media
        MediaPlayer positiveAudio = MediaPlayer.create(MainActivity.this, unisound.emotify.R.raw.positive);
        MediaPlayer negativeAudio = MediaPlayer.create(MainActivity.this, unisound.emotify.R.raw.negative);
        new AudioValet(positiveAudio, negativeAudio);
        // run service
        startService(new Intent(this, NotificationManager.class));
        // Create UI Component
        final TextView mtv = (TextView) findViewById(unisound.emotify.R.id.tv1);
        mtv.setText("Service Status: " + isMyServiceRunning(NotificationManager.class));
        CheckBox cb = (CheckBox) findViewById(unisound.emotify.R.id.cb1);
        cb.setText("Enable Service");
        cb.setChecked(isMyServiceRunning(NotificationManager.class));
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                String msg = isChecked ? "Service Enabled" : "Service Disabled";
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                if(isChecked == true) enableService();
                serviceEnabled = isChecked;
            }
        });
    }
    private void enableService(){
        // Enable Notification
        if (Settings.Secure.getString(this.getContentResolver(),"enabled_notification_listeners").contains(getApplicationContext().getPackageName()))
        {
            Log.i("yomi", "Notification Enabled");
        } else {
            startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
            Log.i("yomi", "nope");
        }
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}

