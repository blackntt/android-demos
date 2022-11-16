package com.example.camerademo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    final int IMAGE_LOADER = 0;
    ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageAdapter = new ImageAdapter(this, new ArrayList<ImageItem>());
        GridView imageGrid = findViewById(R.id.imageGrid);
        imageGrid.setAdapter(imageAdapter);


        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                    values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
                    values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());

                    Uri url = null;
                    Bitmap source = (Bitmap) result.getData().getExtras().get("data");
                    ContentResolver contentResolver = getContentResolver();
                    try {

                        url = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                        if (source != null) {
                            OutputStream imageOut = contentResolver.openOutputStream(url);
                            try {
                                source.compress(Bitmap.CompressFormat.JPEG, 50, imageOut);
                            } finally {
                                imageOut.close();
                            }
                        } else {
                            contentResolver.delete(url, null, null);
                            url = null;
                        }

                        LoaderManager.getInstance(MainActivity.this).restartLoader(IMAGE_LOADER, null, MainActivity.this);
                    } catch (Exception e) {
                        if (url != null) {
                            contentResolver.delete(url, null, null);
                            url = null;
                        }
                    }


                }

            }
        });
        FloatingActionButton btn = findViewById(R.id.floatingActionButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                launcher.launch(intent);
            }
        });
        LoaderManager.getInstance(this).initLoader(IMAGE_LOADER, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == IMAGE_LOADER) {
            String[] SELECTED_FIELDS = new String[]
                    {
                            MediaStore.Images.Media.TITLE,
                            MediaStore.Images.Media.DATA
                    };
            return new CursorLoader(this, MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    SELECTED_FIELDS,
                    null,
                    null, null);
        }

        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == IMAGE_LOADER) {
            List<ImageItem> images = new ArrayList<>();
            if (data != null) {
                while (!data.isClosed() && data.moveToNext()) {
                    String title = data.getString(0);
                    String path = data.getString(1);
                    images.add(new ImageItem(title, path));
                }
                imageAdapter.clear();
                imageAdapter.addAll(images);
                imageAdapter.notifyDataSetChanged();
                data.close();
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        loader = null;
    }
}