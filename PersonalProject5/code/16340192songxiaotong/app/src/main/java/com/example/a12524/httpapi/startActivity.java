package com.example.a12524.httpapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class startActivity extends AppCompatActivity {
    private Button bili;
    private Button git;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        git = (Button)findViewById(R.id.github);
        bili = (Button)findViewById(R.id.bilibili);
        bili.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(startActivity.this, MainActivity.class);
                startActivityForResult(intent,0);
            }
        });
        git.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(startActivity.this, MainGitActvity.class);
                startActivityForResult(intent, 1);
            }
        });
    }
}
