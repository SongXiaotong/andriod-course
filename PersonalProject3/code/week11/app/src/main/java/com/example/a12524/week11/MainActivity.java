package com.example.a12524.week11;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private EditText password;
    private EditText username;
    private EditText conpassword;
    private RadioGroup mode;
    private ImageButton head;
    private Button ok;
    private Button clear;
    private myDB database;
    private Bitmap bmp;
    private Bitmap bmp2;
    private Bitmap bmp3;
    private ByteArrayOutputStream baos;
    private ByteArrayOutputStream baos2;
    private ByteArrayOutputStream baos3;
    private byte[] head_set;
    private byte[] head_default;
    private static int RESULT_LOAD_IMAGE = 10;
    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new myDB(this);

        username = (EditText) findViewById(R.id.username_input);
        password = (EditText) findViewById(R.id.newpassword_input);
        conpassword = (EditText) findViewById(R.id.confirmpassword_input);
        mode = (RadioGroup) findViewById(R.id.mode_group);
        head = (ImageButton) findViewById(R.id.head);
        ok = (Button) findViewById(R.id.ok_button);
        clear = (Button) findViewById(R.id.clear_button);
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        password.setSelection(password.getText().toString().length());
        conpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        conpassword.setSelection(conpassword.getText().toString().length());
        login_mode();

        res = getResources();
        baos = new ByteArrayOutputStream();
        bmp = BitmapFactory.decodeResource(res, R.mipmap.me);
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        head_default = baos.toByteArray();
        baos2 = new ByteArrayOutputStream();
        bmp2 = BitmapFactory.decodeResource(res, R.mipmap.set);
        bmp2.compress(Bitmap.CompressFormat.PNG, 100, baos2);
        head_set = baos2.toByteArray();
        baos3 = new ByteArrayOutputStream();

        initDatabase();


        mode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.login_button)
                    login_mode();
                else
                    register_mode();
            }
        });
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, RESULT_LOAD_IMAGE);
                }

            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Username cannot be empty.", Toast.LENGTH_SHORT).show();
                }
                else if(password.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Password cannot be empty.", Toast.LENGTH_SHORT).show();
                }
                else if(mode.getCheckedRadioButtonId() == R.id.register_button){
                    if(!password.getText().toString().equals(conpassword.getText().toString()))
                        Toast.makeText(MainActivity.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                    else{
                        if(database.findAccount(username.getText().toString()).get("name") != null){
                            Log.e("orig", "1"+database.findAccount(username.getText().toString()).get("name"));
                            Log.e("find", "2"+database.findAccount(username.getText().toString()));
                            Toast.makeText(MainActivity.this, "Username already existed.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            database.insertAccount(username.getText().toString(), password.getText().toString(), head_default);
                            head_default = baos.toByteArray();
                            head.setImageResource(R.mipmap.add);
                            mode.check(R.id.login_button);
                        }
                    }
                }
                else{
                    HashMap<String, Object> map;
                    map = database.findAccount(username.getText().toString());
                    String pass = (String) map.get("password");
                    if(pass == null){
                        Log.e("login", "not exist");
                        Toast.makeText(MainActivity.this, "Username not existed.", Toast.LENGTH_SHORT).show();
                    }
                    else if(!(pass.equals(password.getText().toString()))) {
                        Toast.makeText(MainActivity.this, "Invalid Password.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Log.e("check", "success");
                        Intent intent = new Intent(MainActivity.this, Comment.class);
                        intent.putExtra("username", username.getText().toString());
                        username.setText(null);
                        password.setText(null);
                        conpassword.setText(null);
                        startActivityForResult(intent, 0);
                    }
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setText(null);
                password.setText(null);
                conpassword.setText(null);
            }
        });



    }

    public void login_mode(){
        password.setText(null);
        conpassword.setText(null);
        conpassword.setVisibility(View.GONE);
        head.setVisibility(View.GONE);
        password.setHint(R.string.password_hint);
    }
    public void register_mode(){
        conpassword.setVisibility(View.VISIBLE);
        head.setVisibility(View.VISIBLE);
        password.setText(null);
        password.setHint(R.string.newpassword_hint);
        head.setBackgroundResource(R.mipmap.add);
    }

    public void initDatabase(){
        database.insertAccount("Amy", "0", head_set);
        database.insertAccount("Bob", "0", head_set);
        database.insertAccount("Cindy", "0", head_set);
        database.insertAccount("David", "0", head_set);
        database.insertAccount("Evil", "0", head_set);

        database.insertComment("Amy", "Hi~", "2018-11-12 11:12:18", 1, 0,"");
        database.insertComment("Bob", "Hello~", "2018-11-12 11:13:18", 1, 0,"");
        database.insertComment("Cindy", "Emmmm~", "2018-11-12 11:14:15", 1, 0,"");
        database.insertComment("David", "????", "2018-11-12 11:15:12", 1, 0,"");
        database.insertComment("Evil", "Hahahahah", "2018-11-12 11:16:41", 1, 0,"");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//我们需要判断requestCode是否是我们之前传给startActivityForResult()方法的RESULT_LOAD_IMAGE，并且返回的数据不能为空

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            //查询我们需要的数据
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            head = (ImageButton) findViewById(R.id.head);
            head.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            bmp3 = BitmapFactory.decodeFile(picturePath);
            bmp3.compress(Bitmap.CompressFormat.PNG, 100, baos3);
            head_default = baos3.toByteArray();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
            else {
                Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
