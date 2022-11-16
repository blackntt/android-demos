package com.example.filedemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class EditFileActivity extends AppCompatActivity {

    String fileName;
    EditText contentEditText;
    EditText fileNameTextEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_file);
        fileNameTextEdit = findViewById(R.id.fileName);
        contentEditText = findViewById(R.id.contentEditText);
        if (getIntent()!=null && getIntent().getExtras() != null) {
            fileName = getIntent().getExtras().getString("fileName");
            if (fileName!=null && !fileName.isEmpty()) {
                fileNameTextEdit.setText(fileName);
                try {
                    FileInputStream fis = null;
                    fis = this.openFileInput(fileName);
                    InputStreamReader inputStreamReader =
                            new InputStreamReader(fis, StandardCharsets.UTF_8);
                    StringBuilder stringBuilder = new StringBuilder();
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    String line = reader.readLine();
                    while (line != null) {
                        stringBuilder.append(line).append('\n');
                        line = reader.readLine();
                    }
                    contentEditText.setText(stringBuilder.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_file, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveNote:
                fileName = fileNameTextEdit.getText().toString();
                if (fileName.isEmpty()) {
                    Toast.makeText(this, "Invalid file name", Toast.LENGTH_SHORT).show();
                } else {
                    String fileContents = contentEditText.getText().toString();
                    try (FileOutputStream fos = this.openFileOutput(fileName, this.MODE_PRIVATE)) {
                        fos.write(fileContents.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    setResult(Activity.RESULT_OK);
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}