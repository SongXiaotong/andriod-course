package com.example.a12524.experimentone_week6;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;
import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ListActions extends AppCompatActivity{
    private DynamicReceiver mReceiver;
    private NewAppWidget mwidget;
      private String[] act = {"分享信息", "不感兴趣", "查看更多信息", "出错反馈"};
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        IntentFilter intentFilter= new IntentFilter();
        intentFilter.addAction("collect");
        mReceiver=new DynamicReceiver();
        mwidget= new NewAppWidget();
        registerReceiver(mReceiver,intentFilter);
        registerReceiver(mwidget,intentFilter);
        setContentView(R.layout.food_detail);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListActions.this,R.layout.layout2, act);//适配器
        ListView listView = (ListView) findViewById(R.id.four);
        listView.setAdapter(adapter);

        Intent intent = getIntent();
        final listitem food=(listitem) intent.getSerializableExtra("food");
        final String foodname = food.getName();
        final String foodtype = food.getType();
        final String foodelement = food.getElement();
        final String foodcolor = food.getColor();
        final String change = intent.getStringExtra("change");

        RelativeLayout r = (RelativeLayout) findViewById(R.id.backgrounddiv);
        TextView type = (TextView) findViewById(R.id.detail_type);
        TextView name = (TextView) findViewById(R.id.foodname);
        TextView element = (TextView) findViewById(R.id.detail_element);
        ImageView back = (ImageView) findViewById(R.id.back) ;

        ImageView backimage = (ImageView) findViewById(R.id.back);
        final ImageView starimage = (ImageView) findViewById(R.id.star);
        ImageView collectimage = (ImageView) findViewById(R.id.collect_image);


        name.setText(foodname);
        type.setText(foodtype);
        element.setText("富含 "+ foodelement);
        r.setBackgroundColor(Color.parseColor(foodcolor));
        back.setBackgroundColor(Color.parseColor(foodcolor));

        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        starimage.setOnClickListener(new View.OnClickListener(){
            boolean flag = false;
            @Override
            public void onClick(View v) {
                if (flag == false){
                    starimage.setImageResource(R.drawable.full_star);
                    flag=true;
                }
                else{
                    starimage.setImageResource(R.drawable.empty_star);
                    flag=false;
                }
            }
        });

        collectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View item) {
                Toast.makeText(ListActions.this, "已收藏", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new MsgEvent(foodname));
                Intent intent1=new Intent("collect");
                Bundle bundle=new Bundle();
                bundle.putSerializable("food",food);
                intent1.putExtras(bundle);
                sendBroadcast(intent1);
            }
        });

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mReceiver);
        unregisterReceiver(mwidget);
    }
}
