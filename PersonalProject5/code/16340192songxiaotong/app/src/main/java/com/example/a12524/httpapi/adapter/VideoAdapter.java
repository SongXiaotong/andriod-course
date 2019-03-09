package com.example.a12524.httpapi.adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.a12524.httpapi.method.ImageCut;
import com.example.a12524.httpapi.method.MyImageView;
import com.example.a12524.httpapi.R;
import com.example.a12524.httpapi.model.RecyclerObj;
import com.example.a12524.httpapi.model.Preview;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class VideoAdapter<T> extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private List<RecyclerObj> list;
    private Handler handler;
    private Preview pre;

    public VideoAdapter(List<RecyclerObj> _list){
        list = _list;
    }


    static public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        MyImageView pic;
        ProgressBar pro;
        SeekBar seek;
        TextView title;
        TextView create;
        TextView play;
        TextView comment;
        TextView time;
        TextView detail;
        String path;
        String path0;
        String totaltime;
        int pos;
        private List<Bitmap> res;
        public ViewHolder(View itemView){
            super(itemView);
            view = itemView;
            pic = (MyImageView) itemView.findViewById((R.id.pic));
            title = (TextView) itemView.findViewById(R.id.title);
            create = (TextView) itemView.findViewById(R.id.create);
            play = (TextView) itemView.findViewById(R.id.play);
            comment = (TextView) itemView.findViewById(R.id.comment);
            time = (TextView) itemView.findViewById(R.id.time);
            detail = (TextView) itemView.findViewById(R.id.detail);
            pro = (ProgressBar) itemView.findViewById(R.id.pro);
            seek = (SeekBar) itemView.findViewById(R.id.seek);
            pos = 0;
        }
        static public ViewHolder get(ViewGroup parent) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.video, parent,false);
            final ViewHolder holder = new ViewHolder(itemView);
            return holder;
        }
    }

    public int getItemCount()
    {
        return list.size();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final ViewHolder viewHolder = ViewHolder.get(viewGroup);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        final RecyclerObj video = list.get(i);
        holder.title.setText(video.data.getTitle());
        holder.create.setText("创建时间：" + video.data.getCreate());
        holder.play.setText("播放："+String.valueOf(video.data.getPlay()));
        holder.comment.setText("评论："+String.valueOf(video.data.getVideo_review()));
        holder.time.setText("时长："+String.valueOf(video.data.getDuration()));
        holder.detail.setText(String.valueOf(video.data.getContent()));
        holder.path = video.data.getCover();
        holder.pos = video.data.getAid();
        holder.seek.setProgress(0);
        holder.totaltime = String.valueOf(video.data.getDuration());
        holder.res = null;
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL text = null;
                try {
                    Log.e("aid", String.valueOf(holder.pos));
                    text = new URL("https://api.bilibili.com/pvideo?aid=" + String.valueOf(holder.pos));
                    HttpURLConnection httpURLConnection = (HttpURLConnection) text.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    InputStream in = httpURLConnection.getInputStream();
                    byte[] buffer = new byte[10000];
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    for (int len = 0; (len = in.read(buffer)) > 0; ) {
                        baos.write(buffer, 0, len);
                    }
                    String pic_content = new String(baos.toByteArray(), "utf-8");
                    baos.flush();
                    baos.close();
                    in.close();
                    httpURLConnection.disconnect();
                    pre = new Gson().fromJson((String) pic_content, Preview.class);
                    holder.res = null;
                    for (int i = 0; i < pre.data.getImageSize(); ++i) {
                         URL preview = new URL(pre.data.getImage(i));
                        HttpURLConnection httpURLConnection2 = (HttpURLConnection) preview.openConnection();
                        httpURLConnection2.setRequestMethod("GET");
                        //超时时间为10秒
                        httpURLConnection2.setConnectTimeout(5000);
                        //获取返回码
                        int code = httpURLConnection2.getResponseCode();
                        Bitmap bitmap = null;
                        if (code == 200) {
                            InputStream inputStream = httpURLConnection2.getInputStream();
                            bitmap = BitmapFactory.decodeStream(inputStream);
                            List<Bitmap> temp = ImageCut.split(bitmap, pre.data.getImg_x_size(), pre.data.getImg_y_size(), pre.data.getImg_x_len(), pre.data.getImg_y_len());
                            if (i == 0) {
                                holder.res = ImageCut.split(bitmap, pre.data.getImg_x_size(), pre.data.getImg_y_size(), pre.data.getImg_x_len(), pre.data.getImg_y_len());
                            } else {
                                holder.res.addAll(temp);
                            }

                            inputStream.close();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
        initPic(holder);
        listen(holder);
    }


            @SuppressLint("HandlerLeak")
            public void initPic(final ViewHolder holder) {
                handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 200:
                                holder.pic.setImageURL(holder.path);
                                holder.pro.setVisibility(View.GONE);
                                break;
                        }
                    }
                };

                new Thread() {
                    public void run() {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        handler.obtainMessage(200).sendToTarget();
                    }
                }.start();
            }

            public void listen(final ViewHolder holder) {
                holder.seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress1, boolean fromUser) {
                        seekBar.setProgress(progress1);
                        @SuppressLint("HandlerLeak") final Handler mhandler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                switch (msg.what) {
                                    case 200:
                                        Bitmap bitmap = (Bitmap) msg.obj;
                                        holder.pic.setImageBitmap(bitmap);
                                        break;
                                    case 202:
                                        holder.pic.setImageURL(holder.path);
                                        break;

                                }
                            }
                        };
                        if (seekBar.getProgress() != 0) {
                            String[] time0 = holder.totaltime.split(":");
                            int total = 0;
                            for (int i = 0; i < time0.length; ++i) {
                                Log.e("time", time0[i]);
                                total = 60 * total + Integer.parseInt(time0[i]);
                            }
                            Log.e("totaltime", String.valueOf(total));
                            if (seekBar.getProgress() * total >= pre.data.getIndex().get(pre.data.getIndex().size() - 1) * 100) {
                                mhandler.obtainMessage(200, holder.res.get(pre.data.getIndex().size() - 1)).sendToTarget();
                            } else {
                                for (int i = 0; i < pre.data.getIndex().size() - 1; ++i) {
                                    if (seekBar.getProgress() * total < pre.data.getIndex().get(i + 1) * 100 && seekBar.getProgress() * total >= 100 * pre.data.getIndex().get(i)) {
                                        mhandler.obtainMessage(200, holder.res.get(i)).sendToTarget();
                                        break;
                                    }
                                }
                            }
                        } else {
                            mhandler.obtainMessage(202).sendToTarget();
                        }

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(final SeekBar seekBar) {
                        seekBar.setProgress(0);
                        initPic(holder);
                    }
                });
            }



}