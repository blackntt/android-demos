package com.example.videodemo;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
  ActivityResultLauncher<String> videoChooseLauncher;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    videoChooseLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
        new ActivityResultCallback<Uri>() {
          @Override
          public void onActivityResult(Uri uri) {
            if (uri != null) {
              VideoView videoView = findViewById(R.id.videoView);
              MediaController mediaController = new MediaController(MainActivity.this);
              mediaController.setAnchorView(videoView);
              videoView.setMediaController(mediaController);
              videoView.setVideoURI(uri);
              videoView.setKeepScreenOn(true);
              videoView.start();
            }
          }
        });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      case R.id.openVideoItem:
        videoChooseLauncher.launch("video/mp4");
        break;

    }
    return super.onOptionsItemSelected(item);
  }
}