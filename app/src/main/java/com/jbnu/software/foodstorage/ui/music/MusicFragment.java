package com.jbnu.software.foodstorage.ui.music;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.jbnu.software.foodstorage.R;

import static android.content.Context.MODE_PRIVATE;

public class MusicFragment extends Fragment {

    TextView musicTitle;
    ImageButton btnPlay;
    ImageButton btnNext;
    ImageButton btnBack;
    ListView listView;
    ArrayAdapter adapter;
    MediaPlayer mediaPlayer;
    int[] musicArray = {R.raw.bgm2, R.raw.classic1, R.raw.classic2, R.raw.piano1, R.raw.piano2, R.raw.pop1, R.raw.pop2, R.raw.pop3};
    int index = 0;
    static final String[] musicListName = {"Music1", "Music2", "Music3", "Music4", "Music5", "Music6", "Music7", "Music8"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_music, container, false);

        adapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1, musicListName);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);

        listView = (ListView) root.findViewById(R.id.lv_musics);
        listView.setAdapter(adapter);

        musicTitle = (TextView) root.findViewById(R.id.music_title);
        btnBack = (ImageButton) root.findViewById(R.id.btn_back);
        btnNext = (ImageButton) root.findViewById(R.id.btn_forward);
        btnPlay = (ImageButton) root.findViewById(R.id.btn_play);

        String title = sharedPreferences.getString("Title", "재생 대기 중");
        musicTitle.setText(title);
        final int musicstate = sharedPreferences.getInt("CurrentMusic", index);
        mediaPlayer = MediaPlayer.create(getActivity(), musicArray[musicstate]);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                musicTitle.setText((String) parent.getItemAtPosition(position));
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    index = position;
                    if (index >= musicArray.length) {
                        index = 0;
                    }
                    mediaPlayer = MediaPlayer.create(getActivity(), musicArray[index]);
                    mediaPlayer.start();
                } else {
                    index = position;
                    mediaPlayer = MediaPlayer.create(getActivity(), musicArray[index]);
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.ic_pause);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    index += 1;
                    if (index >= musicArray.length) {
                        index = 0;
                        musicTitle.setText(musicListName[index]);
                    } else
                        musicTitle.setText(musicListName[index]);

                    mediaPlayer = MediaPlayer.create(getActivity(), musicArray[index]);
                    mediaPlayer.start();
                } else {
                    Toast.makeText(getActivity(), "재생중아님", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    index -= 1;
                    if (index < 0) {
                        index = musicArray.length - 1;
                        musicTitle.setText(musicListName[index]);
                    } else
                        musicTitle.setText(musicListName[index]);

                    mediaPlayer = MediaPlayer.create(getActivity(), musicArray[index]);
                    mediaPlayer.start();
                } else {
                    Toast.makeText(getActivity(), "재생중아님", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.ic_play);
                } else {
                    mediaPlayer.start();
                    musicTitle.setText(musicListName[musicstate]);
                    btnPlay.setImageResource(R.drawable.ic_pause);

                }
            }
        });
        return root;
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String title = musicTitle.getText().toString();
        int musicstate = index;
        editor.putString("Title", title);
        editor.putInt("CurrentMusic", musicstate);
        editor.commit();
        mediaPlayer.pause();
    }

}