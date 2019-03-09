package com.example.a12524.mediaplayer;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.Image;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private ImageButton start;
    private ImageButton stop;
    private ImageButton choose;
    private ImageButton back;
    private TextView name;
    private TextView author;
    private boolean hasPermission = false;
    private TextView currtime;
    private TextView totaltime;
    private SimpleDateFormat showtime = new SimpleDateFormat("mm:ss");
    private SeekBar progress;
    private CircleImageView pic;
    private ObjectAnimator animation;
    private ServiceConnection serviceConnection;
    private MusicService musicService;
    private MusicService.MyBinder mBinder;
    private Handler mHandler;
    private String path;
    private int startindex;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        // 初始化server
        initBind();
        // 获取权限后运行项目
        verifyStoragePermissions(MainActivity.this);
        Log.e("start", "start");

    }

    private void initBind(){
        Intent intent = new Intent(this, MusicService.class);
        // 开启服务
        startService(intent);
        // 自动建立服务连接的绑定
        bindService(intent, serviceConnection, this.BIND_AUTO_CREATE);
    }

    private void initView(){
        start = (ImageButton) findViewById(R.id.play);
        stop = (ImageButton) findViewById(R.id.stop);
        choose = (ImageButton) findViewById(R.id.choose);
        back = (ImageButton) findViewById(R.id.back);
        pic = (CircleImageView) findViewById(R.id.background_img);
        currtime = (TextView) findViewById(R.id.name);
        totaltime = (TextView) findViewById(R.id.author);
        currtime = (TextView) findViewById(R.id.currtime);
        totaltime = (TextView) findViewById(R.id.totaltime);
        progress = (SeekBar) findViewById(R.id.progress);
        choose = (ImageButton) findViewById(R.id.choose);
        name = (TextView) findViewById(R.id.name);
        author = (TextView) findViewById(R.id.author);
        // 旋转动画
        animation = ObjectAnimator.ofFloat(pic, "rotation", 0.0f, 360.0f);
        animation.setDuration(60000);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(-1);
        animation.setRepeatMode(ObjectAnimator.RESTART);

        // serviceConnection 是通信的连接，通过连接得到Mybinder和service
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mBinder = (MusicService.MyBinder)service;
                musicService = mBinder.getService();
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {
                serviceConnection = null;
            }
        };

        path = "a";

    }

    public void verifyStoragePermissions(Activity activity) {
        try{
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.READ_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE }, 1);
            } else {
                hasPermission = true;
                setmThread();
                mylistener();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @SuppressLint("HandlerLeak")
    public void setmThread() {
//        // 刷新当前的歌曲进程
//         mHandler = new Handler(){
//            @Override
//            public void handleMessage(Message msg){
//                super.handleMessage(msg);
//                // public方法
////                        currtime.setText(showtime.format(musicService.mp.getCurrentPosition()));
////                        progress.setProgress(musicService.mp.getCurrentPosition());
////                        progress.setMax(musicService.mp.getDuration());
////                        totaltime.setText(showtime.format(musicService.mp.getDuration()));
////                        name.setText(musicService.mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
////                        author.setText(musicService.mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
////                        Bitmap bitmap = BitmapFactory.decodeByteArray(musicService.mmr.getEmbeddedPicture(), 0, musicService.mmr.getEmbeddedPicture().length);
////                        pic.setImageBitmap(bitmap);
//                // 服务器方法
//                if (msg.what == 104){
//                    if(musicService.mp.isPlaying()){
//                        start.setBackgroundResource(R.mipmap.pause);
//                    }
//                    else{
//                        start.setBackgroundResource(R.mipmap.play);
//                    }
//                    try{
//                        int code = 104;
//                        Parcel data = Parcel.obtain();
//                        Parcel reply = Parcel.obtain();
//                        mBinder.transact(code,data,reply,0);
//                        int curr = reply.readInt();
//                        int total = reply.readInt();
//                        String name0 = reply.readString();
//                        String author0 = reply.readString();
//                        int length = reply.readInt();
//                        byte[] data0 = new byte[length];
//                        reply.readByteArray(data0);
//                        currtime.setText(showtime.format(curr));
//                        progress.setProgress(curr);
//                        progress.setMax(total);
//                        totaltime.setText(showtime.format(total));
//                        name.setText(name0);
//                        author.setText(author0);
//                        Bitmap bitmap = BitmapFactory.decodeByteArray(data0, 0, data0.length);
//                        pic.setImageBitmap(bitmap);
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//        };
//
//         // 每1秒发送刷新信息
//        Thread mThread = new Thread(){
//            @Override
//            public void run(){
//                while(true){
//                    try {
//                        Thread.sleep(1000);
//
//                    }catch (InterruptedException e){
//                        e.printStackTrace();
//                    }
//                    if(serviceConnection!=null && hasPermission == true)
//                        mHandler.obtainMessage(104).sendToTarget();
//                }
//            }
//        };
//        mThread.start();
        Observable observable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                while (true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    if (serviceConnection!=null && hasPermission == true)
                        emitter.onNext(104);
                }
            }
        });
        DisposableObserver observer = new DisposableObserver() {
            @Override
            public void onNext(Object o) {
                if (o.equals(104)){
                    try{
                        int code = 104;
                        Parcel data = Parcel.obtain();
                        Parcel reply = Parcel.obtain();
                        mBinder.transact(code,data,reply,0);
                        int is = reply.readInt();
                        if(is == 1)
                            start.setBackgroundResource(R.mipmap.pause);
                        else
                            start.setBackgroundResource(R.mipmap.play);
                        int curr = reply.readInt();
                        int total = reply.readInt();
                        String name0 = reply.readString();
                        String author0 = reply.readString();
                        int length = reply.readInt();
                        byte[] data0 = new byte[length];
                        reply.readByteArray(data0);
                        currtime.setText(showtime.format(curr));
                        progress.setProgress(curr);
                        progress.setMax(total);
                        totaltime.setText(showtime.format(total));
                        name.setText(name0);
                        author.setText(author0);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(data0, 0, data0.length);
                        pic.setImageBitmap(bitmap);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
        mCompositeDisposable.add(observer);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1)
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                hasPermission = true;
                setmThread();
                mylistener();
            }
            else {
                Toast.makeText(this, "程序无法运行", Toast.LENGTH_LONG).show();
                finish();
                System.exit(0);
            }
    }

    public void mylistener(){
        Log.e("listener", "play");
        start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // public 方法
//                if(musicService.mp.isPlaying()){
//                    musicService. mp.pause();
//                    animation.pause();
//                    start.setBackgroundResource(R.mipmap.play);
//                }
//                else{
//                    musicService. mp.start();
//                    if (animation.isStarted())
//                        animation.resume();
//                    else
//                        animation.start();
//                    start.setBackgroundResource(R.mipmap.pause);
//                }
                // 服务器方法
                try{
                    int code = 101;
                    Parcel data = Parcel.obtain();
                    Parcel reply = Parcel.obtain();
                    mBinder.transact(code,data,reply,0);
                    int isplaying = reply.readInt();
                    if(isplaying == 1){
                        animation.pause();
                        start.setBackgroundResource(R.mipmap.play);
                    }
                    else{
                        if (animation.isStarted())
                            animation.resume();
                        else
                            animation.start();
                        start.setBackgroundResource(R.mipmap.pause);
                    }
                }catch (RemoteException e){
                    e.printStackTrace();
                }
            }
        });
       stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // public 方法
//                if(musicService.mp!=null){
//                    musicService.mp.stop();
//                    try{
//                        musicService.mp.prepare();
//                        musicService.mp.seekTo(0);
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//                animation.end();
//                progress.setProgress(0);
                // 服务器方法
                try {
                    int code = 102;
                    Parcel data = Parcel.obtain();
                    Parcel reply = Parcel.obtain();
                    mBinder.transact(code,data,reply,0);
                    start.setBackgroundResource(R.mipmap.play);
                    animation.end();
                    progress.setProgress(0);
                }catch (RemoteException e){
                    e.printStackTrace();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {

//            Intent intent = new Intent(MainActivity.this, MusicService.class);
//            stopService(intent);
            @Override
            public void onClick(View v) {

                try {
                    int code = 103;
                    Parcel data = Parcel.obtain();
                    Parcel reply = Parcel.obtain();
                    mBinder.transact(code,data,reply,0);
                    // 使用stopService回调destory销毁
                    Intent intent = new Intent(MainActivity.this, MusicService.class);
                    stopService(intent);
                    // 解除连接
                    unbindService(serviceConnection);
                    serviceConnection = null;
                    try{
                        MainActivity.this.finish();
                        System.exit(0);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }catch (RemoteException e){
                    e.printStackTrace();
                }

            }
        });

        // 进度条滑动事件的监听
        progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress1, boolean fromUser) {
                progress.setProgress(progress1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // public 方法
//                if(musicService.mp != null){
//                    try{
//                        musicService.mp.seekTo(progress);
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
                // 服务器方法
                try{
                    int code = 105;
                    Parcel data = Parcel.obtain();
                    Parcel reply = Parcel.obtain();
                    data.writeInt(seekBar.getProgress());
                    mBinder.transact(code, data, reply, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // public 方法
//                if(musicService.mp!=null){
//                    musicService.mp.stop();
//                    try{
//                        musicService.mp.prepare();
//                        musicService.mp.seekTo(0);
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//                animation.end();
//                progress.setProgress(0);

                // 服务器方法
                try {
                    int code = 102;
                    Parcel data = Parcel.obtain();
                    Parcel reply = Parcel.obtain();
                    mBinder.transact(code,data,reply,0);
                    start.setBackgroundResource(R.mipmap.play);
                    animation.end();
                    progress.setProgress(0);
                }catch (RemoteException e){
                    e.printStackTrace();
                }
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");//无类型限制
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 2);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == Activity.RESULT_OK && requestCode == 2 && intent != null) {
            Uri uri = intent.getData();
                path = uri.getPath();
                Log.e("show path", path);
                Toast.makeText(this,path,Toast.LENGTH_SHORT).show();

                String res[] = path.split("/");
                startindex = 0;
                for( int i = 0; i < res.length; ++i){
                    if(res[i].equals("0")){
                        startindex = i+1;
                        break;
                    }
                }
                if(startindex == res.length){
                    startindex = 1;
                }
                path = "";
                for(int i = startindex; i < res.length; ++i){
                        path = path + "/" + res[i];
                }
                // public 方法
//                try{
//                    musicService.mp.reset();
//                    musicService.mp.setDataSource(Environment.getExternalStorageDirectory().getPath() + path);
//                    musicService.mmr.setDataSource(Environment.getExternalStorageDirectory().getPath() + path);
//                    // 播放器就绪
//                    musicService.mp.prepare();
//                    // 把播放器跳到零
//                    musicService.mp.seekTo(0);
//                    // 设置循环播放
//                    musicService.mp.setLooping(true);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
                try{
                    int code = 106;
                    Parcel data = Parcel.obtain();
                    Parcel reply = Parcel.obtain();
                    data.writeString(path);
                    mBinder.transact(code, data, reply, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 使用stopService回调destory销毁
        Intent intent = new Intent(MainActivity.this, MusicService.class);
        stopService(intent);

        // 解除连接
        unbindService(serviceConnection);
        serviceConnection = null;
    }
@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
    if(keyCode == KeyEvent.KEYCODE_BACK){
        moveTaskToBack(true);
        return true;
    }
    return super.onKeyDown(keyCode, event);
}



}
