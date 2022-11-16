package com.example.navdrawer;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);
        drawerLayout = findViewById(R.id.draw_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.toggle_open,
                R.string.toggle_close);
        drawerLayout = findViewById(R.id.draw_layout);
        drawerLayout.addDrawerListener(toggle);
        NavigationView nv = findViewById(R.id.navigation_view);
        nv.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        toggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        TextView contentView = findViewById(R.id.content);
        switch (item.getItemId()){
            case R.id.menu_activity:
                contentView.setText("Activity");
                break;
            case R.id.menu_call:
                contentView.setText("Call");
                break;
            case R.id.menu_gallery:
                contentView.setText("Gallery");
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}