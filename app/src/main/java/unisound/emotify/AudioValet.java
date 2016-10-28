package unisound.emotify;

import android.media.MediaPlayer;

public class AudioValet {

    static MediaPlayer positive = null;
    static MediaPlayer negative = null;
    AudioValet(MediaPlayer positive, MediaPlayer negative){
        this.positive = positive;this.negative = negative;
    }
    public static void playPositive(){
        if(positive.isPlaying()) positive.seekTo(0);
        positive.start();
    }
    public static void playNegative(){
        if(negative.isPlaying()) negative.seekTo(0);
        negative.start();
    }
    public static MediaPlayer getPositiveAudio(){
        return positive;
    }
    public static MediaPlayer getNegativeAudio(){
        return negative;
    }
}
