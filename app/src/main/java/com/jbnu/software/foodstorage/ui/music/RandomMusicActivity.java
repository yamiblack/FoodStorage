package com.jbnu.software.foodstorage.ui.music;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.jbnu.software.foodstorage.service.MusicService;
import com.jbnu.software.foodstorage.R;

public class RandomMusicActivity extends AppCompatActivity {
    private ToggleButton toggleButtonMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_random);
        getSupportActionBar().setTitle("랜덤 재생");

        toggleButtonMusic = findViewById(R.id.toggleMusic);
        toggleButtonMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMusic = new Intent(getApplicationContext(), MusicService.class);

                if (toggleButtonMusic.isChecked() == true) {
                    startService(intentMusic);
                } else {
                    stopService(intentMusic);
                }

            }
        });

    }
}
