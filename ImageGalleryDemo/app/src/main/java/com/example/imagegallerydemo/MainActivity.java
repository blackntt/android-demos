package com.example.imagegallerydemo;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_MEDIA_LOCATION}, 1);
        LoaderManager.getInstance(this).initLoader(1, null, this);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        if(id == 1){
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            // Set up an array of the Thumbnail Image ID column we want
            String[] projection = {MediaStore.Images.Media.DATA};

            final Cursor cursor = getContentResolver().query(uri,
                    projection,
                    null,
                    null,
                    null);

            ArrayList<String> result = new ArrayList<String>(cursor.getCount());
            Log.i("cursor.getCount()) :", uri.toString()+" "+String.valueOf(cursor.getCount()));
            while(cursor != null && !cursor.isClosed() && cursor.moveToNext()){
                Log.i("cursor.getCount()) :", cursor.getCount() + " "+cursor.getString(0));
            }
            if(cursor!=null)
                cursor.close();
            return new CursorLoader(MainActivity.this, uri, projection, null, null, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if(data != null){

            while(!data.isClosed()&& data.moveToNext()){
                Log.d("TAG", "onLoadFinished: "+data.getString(0));
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Permission denied to read your contacts", Toast.LENGTH_SHORT).show();
            }
        }
    }
}