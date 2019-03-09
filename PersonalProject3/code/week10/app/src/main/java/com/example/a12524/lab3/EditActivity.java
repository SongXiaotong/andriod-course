package com.example.a12524.lab3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class EditActivity extends AppCompatActivity {
    private EditText file;
    private String filename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        file = (EditText) findViewById(R.id.file);
        filename = "file_content";
    }



    public void click_save(View button){
        try (FileOutputStream fileOutputStream = openFileOutput(filename, MODE_PRIVATE)) {
            String str = file.getText().toString();
            fileOutputStream.write(str.getBytes());
            Toast.makeText(EditActivity.this, "Save successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException ex) {
            Toast.makeText(EditActivity.this, "save failed", Toast.LENGTH_SHORT).show();
        }
    }
    public void click_load(View button){
        try (FileInputStream fileInputStream = openFileInput(filename)) {
            byte[] contents = new byte[fileInputStream.available()];
            fileInputStream.read(contents);
            file.setText(new String(contents, "UTF-8"));
            Toast.makeText(EditActivity.this, "Load successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException ex) {
            Toast.makeText(EditActivity.this, "Fail to load file.", Toast.LENGTH_SHORT).show();
        }
    }
    public void click_clear(View button){
        file.setText(null);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
