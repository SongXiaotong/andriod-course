package com.example.a12524.experimentone_week6;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.content.Intent;
import android.transition.Explode;
import android.transition.Transition;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    private RecyclerView foodlist;
    private ListView collectlist;
    private String[] foodname = {"大豆", "十字花科蔬菜", "牛奶", "海鱼", "菌菇类", "番茄", "胡萝卜", "荞麦", "鸡蛋"};
    private String[] foodtitle = {"粮", "蔬", "饮", "肉", "蔬", "蔬", "蔬", "粮", "杂"};
    private String[] foodtype = {"粮食", "蔬菜", "饮品", "肉食", "蔬菜", "蔬菜", "蔬菜", "粮食", "杂"};
    private String[] foodelement = {"蛋白质", "维生素C", "钙", "蛋白质", "微量元素", "番茄红素", "胡萝卜素", "膳食纤维", "几乎所有营养物质"};
    private String[] foodcolor = {"#BB4C3B", "#C48D30", "#4469B0", "#20A17B", "#BB4C3B", "#4469B0", "#20A17B", "#BB4C3B", "#C48D30"};

    private List<listitem> allfood = new ArrayList<>();
    private List<listitem> collectfood = new ArrayList<>();

    private FloatingActionButton changebutton;
    private CollectAdapter collectAdapter = new CollectAdapter(MainActivity.this, R.layout.layout3, collectfood);
    int flag = 0;
    static int time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        changebutton = (FloatingActionButton)findViewById(R.id.changebutton);
        EventBus.getDefault().register(this);

        if(time == 0){
            time = 1;
            Random random = new Random();
            int num = random.nextInt(allfood.size());
            Intent intentBroadcast = new Intent("start");
            Bundle bundle = new Bundle();
            bundle.putSerializable("food", allfood.get(num));
            intentBroadcast.setComponent(new ComponentName("com.example.a12524.experimentone_week6","com.example.a12524.experimentone_week6.MyReceiver1" ));
            intentBroadcast.putExtras(bundle);
            sendBroadcast(intentBroadcast);
        }


        collectlist = (ListView) findViewById(R.id.foodlist);
        collectlist.setAdapter(collectAdapter);
        collectlist.setVisibility(View.GONE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        final FoodAdapter foodAdapter = new FoodAdapter(allfood);
        foodlist = (RecyclerView) findViewById(R.id.foodrecycler);
        foodlist.setLayoutManager(layoutManager);

        foodAdapter.setOnItemClickListener(new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle details = new Bundle();
                details.putSerializable("food", allfood.get(position));
                Intent intent = new Intent(MainActivity.this, ListActions.class);
                intent.putExtras(details);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }
        });
        foodAdapter.setOnItemLongClickListener(new FoodAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                Toast.makeText(MainActivity.this, "删除"+allfood.get(position).getName(), Toast.LENGTH_SHORT).show();
                allfood.remove(position);
                foodAdapter.notifyDataSetChanged();

            }
        });

        foodlist.setAdapter(foodAdapter);

        changebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag == 0){
                    collectlist.setVisibility(View.VISIBLE);
                    foodlist.setVisibility(View.GONE);
                    changebutton.setImageResource(R.drawable.mainpage);
                    flag = 1;
                }else if (flag == 1){
                    collectlist.setVisibility(View.GONE);
                    foodlist.setVisibility(View.VISIBLE);
                    changebutton.setImageResource(R.drawable.collect);
                    flag = 0;
                }
            }
        });
        collectlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    Intent intent = new Intent(MainActivity.this, ListActions.class);
                    intent.putExtra("food_name", collectfood.get(position).getName());
                    intent.putExtra("food_type", collectfood.get(position).getType());
                    intent.putExtra("food_element", collectfood.get(position).getElement());
                    intent.putExtra("food_color", collectfood.get(position).getColor());
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                }
            }
        });
        collectlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if(position != 0){

                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setMessage("是否从收藏夹中移除"+collectfood.get(position).getName());
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            collectfood.remove(position);
                            collectAdapter.notifyDataSetChanged();
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    dialog.show();
                }
                return true;
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MsgEvent event){
        String msg = event.getMsg();
        for(int i = 0;i<foodname.length;i++) {
            if(msg.equals(foodname[i])) {
                listitem my = new listitem();
                my.setName(foodname[i]);
                my.setTitle(foodtitle[i]);
                my.setType(foodtype[i]);
                my.setElement(foodelement[i]);
                my.setColor(foodcolor[i]);
                collectfood.add(my);
                collectAdapter.notifyDataSetChanged();
            }
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    public void initData() {
        for (int i = 0; i < foodname.length; i++) {
            listitem all = new listitem();
            all.setName(foodname[i]);
            all.setType(foodtype[i]);
            all.setTitle(foodtitle[i]);
            all.setElement(foodelement[i]);
            all.setColor(foodcolor[i]);
            allfood.add(all);
        }
        listitem collect = new listitem();
        collect.setName("收藏夹");
        collect.setTitle("*");
        collectfood.add(collect);
    }
}
