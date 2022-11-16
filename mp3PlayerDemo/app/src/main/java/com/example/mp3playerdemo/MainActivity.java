package com.example.mp3playerdemo;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
  private Handler mHandler = new Handler();
  private MediaPlayer mediaPlayer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mediaPlayer = new MediaPlayer();
    TextView songName = findViewById(R.id.songName);
    TextView songTime = findViewById(R.id.time);
    SeekBar seekBar = findViewById(R.id.seekBar);
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
          int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
          seekBar.setProgress(mCurrentPosition);
        }
        mHandler.postDelayed(this, 500);
      }
    });
    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mediaPlayer != null && fromUser) {
          mediaPlayer.seekTo(progress * 1000);
        }
        String curDuration = formatDuration(seekBar.getProgress());
        String totalDuration = formatDuration(seekBar.getMax());
        songTime.setText(curDuration + "/" + totalDuration);
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {
      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
      }
    });
    ActivityResultLauncher<String> mp3ChooseLauncher =
        registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
              @Override
              public void onActivityResult(Uri uri) {
                if (uri != null) {
                  songName.setText(getSongTitle(uri));
                  if (mediaPlayer.isPlaying())
                    mediaPlayer.stop();
                  mediaPlayer = MediaPlayer.create(MainActivity.this, uri);
                  mediaPlayer.start();
                  seekBar.setMax(mediaPlayer.getDuration() / 1000);
                }
              }
            });


    Button btn = findViewById(R.id.openBtn);
    btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mp3ChooseLauncher.launch("audio/mpeg");
      }
    });
  }

  private String getSongTitle(Uri uri) {
    MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
    metaRetriever.setDataSource(getApplicationContext(), uri);
    return metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
  }

  private String formatDuration(int s) {
    long min = TimeUnit.SECONDS.toMinutes(s);
    return String.format("%d min %d sec",
        min,
        s - TimeUnit.MINUTES.toSeconds(min)
    );
  }
}