package com.example.a12524.mediaplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;


import java.io.File;
import java.io.FileInputStream;

public class MusicService extends Service {
    private final IBinder binder = new MyBinder();
    private static MediaPlayer mp = new MediaPlayer();
    private static MediaMetadataRetriever mmr = new MediaMetadataRetriever();
    public String path;
    public MusicService() {
        // 初始化调用
        try{
            mp.setDataSource(Environment.getExternalStorageDirectory().getPath() + "/Music/山高水长.mp3");
            mmr.setDataSource(Environment.getExternalStorageDirectory().getPath() + "/Download/山高水长.mp3");
            // 播放器就绪
            mp.prepare();
            // 把播放器跳到零
            mp.seekTo(0);
            // 设置循环播放
            mp.setLooping(true);
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.e("tag", "parseMp3File名称: " + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
        Log.e("tag", "parseMp3File专辑: " + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
        Log.e("tag", "parseMp3File歌手: " + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
        Log.e("tag", "parseMp3File码率: " + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE));
        Log.e("tag", "parseMp3File时长: " + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
        Log.e("tag", "parseMp3File类型: " + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE));
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class MyBinder extends Binder {
        // 获得当前的实例
        public MusicService getService(){
            return MusicService.this;
        }
        // 重写onTransact函数判断
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code)
            {
                case 101:
                    //播放
                    int isplaying = checkplay();
                    reply.writeInt(isplaying);
                    break;
                case 102:
                    //停止按钮
                    stop();
                    break;
                case 103:
                    //退出按钮
                    break;
                case 104:
                    //界面刷新
                    int i = 0;
                    if(mp.isPlaying()){
                        i = 1;
                    }
                    reply.writeInt(i);
                    reply.writeInt(mp.getCurrentPosition());
                    reply.writeInt(mp.getDuration());
                    reply.writeString(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
                    reply.writeString(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
                    reply.writeInt(mmr.getEmbeddedPicture().length);
                    reply.writeByteArray(mmr.getEmbeddedPicture());
                    break;
                case 105:
                //拖动进度条
                int progress = data.readInt();
                controlprogress(progress);
                break;
                case 106:
                    //获取字符串
                    stop();
                    path = data.readString();
                    Log.e("music path" , "1" + path + "1");
                    try{
                        mp.reset();
                        mp.setDataSource(Environment.getExternalStorageDirectory().getPath() + path);
                        mmr.setDataSource(Environment.getExternalStorageDirectory().getPath() + path);
                        // 播放器就绪
                        mp.prepare();
                        // 把播放器跳到零
                        mp.seekTo(0);
                        // 设置循环播放
                        mp.setLooping(true);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                break;
            }
            return super.onTransact(code,data,reply,flags);
        }

        public int checkplay(){
            if(mp.isPlaying()){
                mp.pause();
                return 1;
            }
            else {
                mp.start();
                return 0;
            }
        }

        public void stop(){
            if(mp!=null){
                mp.stop();
                try{
                    mp.prepare();
                    mp.seekTo(0);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        public void controlprogress(int progress){
            if(mp != null){
                try{
                    mp.seekTo(progress);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startI){
        return Service.START_STICKY;
    }
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);}

    @Override//被关闭之前回调该方法
    public void onDestroy() {
        super.onDestroy();
        Log.e("stop", "dafd");
        mp.stop();
        mp.release();
        mmr.release();
    }
}
