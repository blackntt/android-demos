package com.example.multiplechoicedialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private String[] carBrands = new String[]{"Toyota", "BMW", "Ford", "Tesla"};
    private int choosenIndex = 0;
    AlertDialog brandDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        brandDialog = buildBrandDialog();
        Button brandButton = findViewById(R.id.branchButton);
        brandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                brandDialog.show();
            }
        });
    }
    private AlertDialog buildBrandDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        dialogBuilder.setTitle("Choose your Car");
        dialogBuilder.setSingleChoiceItems(carBrands, choosenIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                choosenIndex = which;
            }
        });
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "You choose the brand: " + carBrands[choosenIndex],
                        Toast.LENGTH_LONG).show();
            }
        });
       return dialogBuilder.create();
    }
}