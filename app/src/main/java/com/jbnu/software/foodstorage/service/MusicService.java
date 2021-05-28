package com.jbnu.software.foodstorage.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.jbnu.software.foodstorage.R;

public class MusicService extends Service {
    MediaPlayer mediaPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        int min = 0;
        int max = 4;
        int randomNumber = (int) ((Math.random() * (max - min)) + min);
        Log.e("random", String.valueOf(randomNumber));
        switch (randomNumber) {
            case 0:
                mediaPlayer = MediaPlayer.create(this, R.raw.bgm2);
                break;
            case 1:
                mediaPlayer = MediaPlayer.create(this, R.raw.classic1);
                break;
            case 2:
                mediaPlayer = MediaPlayer.create(this, R.raw.classic2);
                break;
            case 3:
                mediaPlayer = MediaPlayer.create(this, R.raw.piano1);
                break;
            case 4:
                mediaPlayer = MediaPlayer.create(this, R.raw.piano2);
                break;
        }

        mediaPlayer.setLooping(false);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }
}



