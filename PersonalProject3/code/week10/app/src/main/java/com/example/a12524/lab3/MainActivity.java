package com.example.a12524.lab3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText newPassword;
    private EditText confirmPassword;
    private String newpassword;
    private String confirmpassword;
    private SharedPreferences preferences;
    private Boolean first;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        preferences = getSharedPreferences("password", MODE_PRIVATE);
        editor = preferences.edit();
        first = preferences.getBoolean("first", true);
        if(!first){
            newPassword.setVisibility(View.GONE);
            confirmpassword = null;
            confirmPassword.setText(null);
            confirmPassword.setHint("Password");
        }
        Log.e("first", first.toString());

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

    public void initView(){
        newPassword = (EditText) findViewById(R.id.newPassword);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        newpassword = newPassword.getText().toString();
        confirmpassword = confirmPassword.getText().toString();

    }
    public void click_ok(View password){
        first = preferences.getBoolean("first", true);
        newpassword = newPassword.getText().toString();
        confirmpassword = confirmPassword.getText().toString();
        if(first){
            Log.e("new", newpassword+"1");
            Log.e("confirm", confirmpassword+"1");
            if(newpassword.equals("") || confirmpassword.equals("")){
                Toast.makeText(MainActivity.this, "Password cannot be empty.", Toast.LENGTH_SHORT).show();;
            }
            else if(newpassword.equals(confirmpassword)) {
                editor.putBoolean("first", false);
                editor.putString("1", newpassword);
                editor.commit();

                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivityForResult(intent, 0);
                newPassword.setVisibility(View.GONE);
                confirmpassword = null;
                confirmPassword.setText(null);
                confirmPassword.setHint("Password");
            }
            else{
                Toast.makeText(MainActivity.this, "Password Mismatch.", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            String ref = preferences.getString("1", "");
            Log.e("pass1", ref);
            Log.e("confir", confirmpassword);
            if(confirmpassword.equals("")){
                Toast.makeText(MainActivity.this, "Password cannot be empty.", Toast.LENGTH_SHORT).show();
            }
            else if(confirmpassword.equals(ref)){
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivityForResult(intent, 0);
                finish();
            }
            else{
                Toast.makeText(MainActivity.this, "Invalid Password.", Toast.LENGTH_SHORT).show();
            }
        }

    }
    public void click_clear(View pass){
        newPassword.setText(null);
        confirmPassword.setText(null);
    }
}
