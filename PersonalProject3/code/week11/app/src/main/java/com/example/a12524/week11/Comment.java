package com.example.a12524.week11;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Comment extends AppCompatActivity {
    private String username;
    private myDB database;
    private List<CommentInfo> comments;
    private CommentAdapter adapter;
    private ListView commentslist;
    private EditText addcomment;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        database = new myDB(this);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        database.getUser(username);
        Log.e("login name", username);
        initComments();

        adapter.setOnItemDeleteClickListener(new CommentAdapter.onItemDeleteListener(){
            @Override
            public void onDeleteClick(int i) {
                if(!database.getStatus(comments.get(i).getName(), comments.get(i).getComment(), comments.get(i).getTime(), username)){
                    Log.e("check if good", "not in");
                    comments.get(i).setNumber(comments.get(i).getNumber()+1);
                    comments.get(i).setImageID(R.mipmap.red);
                    comments.get(i).setStatus(1);
                    database.upgradeComment(comments.get(i).getName(), comments.get(i).getComment(), comments.get(i).getTime(), comments.get(i).getNumber(), 1, username);
                }
                else{
                    Log.e("check if good", "in");
                    comments.get(i).setNumber(comments.get(i).getNumber()-1);
                    comments.get(i).setImageID(R.mipmap.white);
                    comments.get(i).setStatus(0);
                    database.upgradeComment(comments.get(i).getName(), comments.get(i).getComment(), comments.get(i).getTime(), comments.get(i).getNumber(), 0, username);
                }
                adapter.notifyDataSetChanged();
            }
        });

        commentslist.setAdapter(adapter);

        commentslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tel = "\nPhone: ";
                if (ContextCompat.checkSelfPermission(Comment.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Comment.this,new String[]{Manifest.permission.READ_CONTACTS},2);
                }else {
                    Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = \"" + comments.get(position).getName() + "\"", null, null);
                    if(cursor.moveToFirst()){
                        tel += cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    else{
                        tel += "number not exist";
                    }
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Comment.this)
                            .setTitle("Info")
                            .setMessage("Username: " + comments.get(position).getName() + tel)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {}
                            });
                    dialog.show();
                }
            }
        });

        commentslist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                String choose = " or not?";
                Log.e(comments.get(position).getName()+"name1", username+"name2");
                if(comments.get(position).getName().equals(username)){
                    choose = "Delete" + choose;
                }
                else
                    choose = "Export" + choose;
                final String choose1 = choose;

                AlertDialog.Builder dialog = new AlertDialog.Builder(Comment.this)
                        .setMessage(choose)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(choose1.equals("Delete or not?")){
                                    database.deleteComment(comments.get(position).getName(), comments.get(position).getComment(), comments.get(position).getTime());
                                    comments.remove(position);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        });
                dialog.show();
                return true;
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getWindow().peekDecorView();
                if (view != null) {
                    InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                if(addcomment.getText().toString().equals("")){
                    Toast.makeText(Comment.this, "Comment cannot be empty.", Toast.LENGTH_SHORT).show();
                }
                else{
                    SimpleDateFormat formatter =  new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                    Date curDate =  new Date(System.currentTimeMillis());
                    String systime  = formatter.format(curDate);
                    database.insertComment(username, addcomment.getText().toString(), systime, 0, 0, "");

                    HashMap<String, Object> user = database.findAccount(username);
                    byte[] temp_head =(byte[]) user.get("head");
                    CommentInfo comment = new CommentInfo(temp_head, username, systime, addcomment.getText().toString(), 0, R.mipmap.white, 0);
                    comments.add(comment);
                    addcomment.setText("");
                    adapter.notifyDataSetChanged();
                }

            }
        });

        
    }
    
    public void initComments(){
        comments = database.getComments();
        adapter = new CommentAdapter(this, R.layout.item, comments);
        commentslist = (ListView) findViewById(R.id.comment_list);
        addcomment = (EditText) findViewById(R.id.addcomment);
        send = (Button) findViewById(R.id.send);
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 2){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
            }
            else {
                Toast.makeText(Comment.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
