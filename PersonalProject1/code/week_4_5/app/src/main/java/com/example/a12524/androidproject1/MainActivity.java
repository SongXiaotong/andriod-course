package com.example.a12524.androidproject1;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void searching(View search) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

        EditText searchline = (EditText) findViewById(R.id.search_line);
        String search_content = searchline.getText().toString();
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        RadioGroup checkbox = (RadioGroup) findViewById(R.id.checkbox);
        String text = "";
        RadioButton rb = (RadioButton) MainActivity.this.findViewById(checkbox.getCheckedRadioButtonId());
        text = rb.getText().toString();

        alert.setTitle("提示").setPositiveButton("确认",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "对话框“确定”按钮被点击", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "对话框“取消”按钮被点击", Toast.LENGTH_SHORT).show();
                    }
                }).create();

        if (search_content.equals("")) {
            Toast.makeText(getApplicationContext(), "搜索内容不能为空", Toast.LENGTH_SHORT).show();
        } else if (search_content.equals("Health")) {

            alert.setMessage(text+"搜索成功");
            alert.show();
        } else {
            alert.setMessage("搜索失败");
            alert.show();
        }
    }

    public void clickpicture(View picture){
        Toast.makeText(getApplicationContext(), "图片被选中", Toast.LENGTH_SHORT).show();
    }
    public void clickvideo(View video){
        Toast.makeText(getApplicationContext(), "视频被选中", Toast.LENGTH_SHORT).show();
    }
    public void clickqa(View qa){
        Toast.makeText(getApplicationContext(), "问答被选中", Toast.LENGTH_SHORT).show();
    }
    public void clicknews(View news){
        Toast.makeText(getApplicationContext(), "资讯被选中", Toast.LENGTH_SHORT).show();
    }


}


