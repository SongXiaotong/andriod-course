package com.example.a12524.httpapi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a12524.httpapi.adapter.VideoAdapter;
import com.example.a12524.httpapi.model.RecyclerObj;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.disposables.CompositeDisposable;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private List<RecyclerObj> videos = new ArrayList<>();
    private ConnectivityManager connectivityManager;
    private Button search;
    private TextView id_input;
    private RecyclerView recyclerView;
    private RecyclerObj recyclerObj;
    private VideoAdapter recycleAdapter;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private boolean has;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 200:
                    recycleAdapter.notifyDataSetChanged();
                    break;
                case 404:
                    Toast.makeText(MainActivity.this, "数据库不存在", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取组件
        initView();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        recycleAdapter = new VideoAdapter(videos);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper. VERTICAL);
        recyclerView.setAdapter(recycleAdapter);


        // 点击按钮产生交互：连接失败/得到数据
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(MainActivity.this);

                id = id_input.getText().toString();

                // 如果没有网络则toast提示
                if(!haveNet(MainActivity.this)){
                    Toast.makeText(MainActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                }

                // 如果有网络则获取数据
                else{
                    if(id.equals("")){
                        Toast.makeText(MainActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
                        boolean digit = pattern.matcher(id).matches();
                        if(!digit || (digit && Integer.valueOf(id) == 0)){
                            Toast.makeText(MainActivity.this, "输入必须为小于40的正整数", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(Integer.parseInt(id) <= 40)
                                getElement();
                            else{
                                Toast.makeText(MainActivity.this, "输入必须为小于40的正整数", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

            }
        });

    }

    public void initView(){
        search = (Button) findViewById(R.id.search);
        id_input = (TextView) findViewById(R.id.serach_content);
        recyclerView = (RecyclerView) findViewById(R.id.videoslist);
        id = "";
        has = false;
    }

    public boolean haveNet(Context context) {
        // 获得网络状态管理器
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            // 建立网络数组
            NetworkInfo[] net_info = connectivityManager.getAllNetworkInfo();
            if (net_info != null) {
                for (int i = 0; i < net_info.length; i++) {
                    // 判断获得的网络状态是否是处于连接状态
                    if (net_info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void getElement(){
        Thread thread_net = new Thread(new Runnable() {
            @Override
            public void run() {
                URL text = null;
                try {
                    text = new URL("https://space.bilibili.com/ajax/top/showTop?mid=" + id);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) text.openConnection();
                    httpURLConnection.setRequestMethod( "GET");
                    InputStream in = httpURLConnection.getInputStream();
                    byte[] buffer = new byte[10000];
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    for ( int len = 0; (len = in.read(buffer)) > 0;) {
                        baos.write(buffer, 0, len);
                    }
                    String video_content = new String(baos.toByteArray(), "utf-8" );
                    baos.flush();
                    baos.close();
                    in.close();
                    httpURLConnection.disconnect();
                    try {
                        recyclerObj = new Gson().fromJson((String)video_content, RecyclerObj.class);
                            has = false;
                            for(int i = 0; i < videos.size(); ++i){
                                if(recyclerObj.data.getTitle().equals(videos.get(i).data.getTitle())){
                                    has = true;
                                    break;
                                }
                            }
                            if(!has) {
                                videos.add(recyclerObj);
                                handler.obtainMessage(200).sendToTarget();
                            }
                    }catch (Exception e){
                        handler.obtainMessage(404).sendToTarget();
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread_net.start();
    }

    public static void hideKeyboard(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(), 0);
    }
//
//    public void setUrl(final int order){
//        Observable observable = Observable.create(new ObservableOnSubscribe<Object>() {
//            @Override
//            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
//                while (true) {
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    URL url = new URL(videos.get(order).data.getCover());
//                    //获取连接
//                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                    //使用GET方法访问网络
//                    connection.setRequestMethod("GET");
//                    //超时时间为10秒
//                    connection.setConnectTimeout(10000);
//                    //获取返回码
//                    int code = connection.getResponseCode();
//                    if (code == 200) {
//                        InputStream inputStream = connection.getInputStream();
//                        //使用工厂把网络的输入流生产Bitmap
//                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                        //利用Message把图片发给Handler
//                        emitter.onNext(1);
//                        inputStream.close();
//                    }
//                }
//            }
//        });
//        DisposableObserver observer = new DisposableObserver() {
//            @Override
//            public void onNext(Object o) {
//                if (o.equals(1)){
//                    try{
//
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        };
//        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
//        mCompositeDisposable.add(observer);
//    }

}
