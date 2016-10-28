package sideproject.uwaterloo.ca.emotone;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Johnson on 2016-10-24.
 */

public class NotificationManager extends NotificationListenerService {
    public static TextView mtv;

    @Override
    public void onCreate() {
        Log.i("emott", "started service ...");
        super.onCreate();
    }
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {

    }
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        final String packageName = sbn.getPackageName();
        if(MainActivity.serviceEnabled == false || TextUtils.isEmpty(packageName) == true) return;
        final String text = sbn.getNotification().extras.getCharSequence("android.text").toString();
        //API
        try {
            int mood = getMood(text);
            Log.i("emott", packageName + " -> " + mood + " -> " + text);
            AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
            Log.i("emott", "status -> " + am.isMusicActive());
            if(mood > 50) AudioValet.playPositive();
            else if(mood == 50){}
            else AudioValet.playNegative();
        }catch(IOException ex){
            Log.i("emott","Excep tion");
        }
    }

    public static int getMood(String input) throws IOException{
        //String input = "Good night";
        String builder = "{\"texts\": [\"" + input + "\"]}";
        System.out.println(builder);
        byte[] requestData = builder.getBytes("UTF-8");
        URL url = new URL("https://api.uclassify.com/v1/uclassify/sentiment/classify");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setInstanceFollowRedirects(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Content-Length", Integer.toString(requestData.length));
        conn.setRequestProperty("Authorization", "Token 3tlob1gJnqIw");
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.write(requestData);
        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String output = "";
        for (int c = in.read(); c != -1; c = in.read()) {
            output += Character.toString((char)c);
        }
        return moodPercentage(output);
    }

    private static int moodPercentage(String input){
        int positive = 0, negative = 0;
        boolean virgin = true;
        for(int x = 0;x < input.length();x++){
            System.out.print(input.charAt(x));
            // p == 112 && " == 34
            if(input.charAt(x) != 34) continue;
            if(input.charAt(x) == 34 && input.charAt(x+1) == 112 && input.charAt(x+2) == 34){
                if(isValidNumber(input.charAt(x+6)) && isValidNumber(input.charAt(x+7))){
                    String builder = Character.toString(input.charAt(x+6)) + Character.toString(input.charAt(x+7));
                    if(virgin)  negative = Integer.parseInt(builder);
                    else	    positive = Integer.parseInt(builder);
                    virgin = false;
                }
            }
        }
        if((positive + negative) > 90) return positive;
        else return -1;
    }
    private static boolean isValidNumber(char input){
        if(input >= 48 && input <= 57) return true;return false;
    }
}