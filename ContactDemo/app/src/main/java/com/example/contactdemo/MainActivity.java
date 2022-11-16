package com.example.contactdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    final int CONTACT_LOADER = 0;
    final int READ_CONTACTS_REQUEST_CODE = 0;
    ContactListAdapter contactListAdapter;
    boolean isASC = true;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.asc:
                this.isASC = true;
                break;
            case R.id.desc:
                this.isASC = false;
                break;
        }
        LoaderManager.getInstance(this).restartLoader(this.CONTACT_LOADER,null,this);
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACTS_REQUEST_CODE);

        ListView list = (ListView) findViewById(R.id.contactList);

        contactListAdapter = new ContactListAdapter(getApplicationContext(), R.layout.contact_item, new ArrayList<ContactItem>());
        list.setAdapter(contactListAdapter);
        LoaderManager.getInstance(this).initLoader(CONTACT_LOADER, null, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_CONTACTS_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Permission denied to read your contacts", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == CONTACT_LOADER) {
            String[] SELECTED_FIELDS = new String[]
                    {
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                            ContactsContract.CommonDataKinds.Phone.NUMBER,
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    };
            return new CursorLoader(this, ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    SELECTED_FIELDS,
                    null,
                    null,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " " + (isASC ? "ASC" : "DESC"));
        }

        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == CONTACT_LOADER) {
            List<ContactItem> contacts = new ArrayList<>();
            if (data != null) {
                while (!data.isClosed() && data.moveToNext()) {
                    String phone = data.getString(1);
                    String name = data.getString(2);
                    contacts.add(new ContactItem(name, phone));
                }
                contactListAdapter.clear();
                contactListAdapter.addAll(contacts);
                contactListAdapter.notifyDataSetChanged();
                data.close();
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        loader = null;
    }
}


