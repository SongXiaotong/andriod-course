package com.example.a12524.week11;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class CommentAdapter extends ArrayAdapter<CommentInfo> {
    private int resourceID;
private myDB DATABASE;
    public CommentAdapter(@NonNull Context context, int resource, List<CommentInfo> comments) {
        super(context, resource, comments);
        resourceID = resource;
    }

    @NonNull
    @Override
    public Context getContext() {
        return super.getContext();
    }


    public interface onItemDeleteListener {
        void onDeleteClick(int i);
    }

    private onItemDeleteListener mOnItemDeleteListener;

    public void setOnItemDeleteClickListener(onItemDeleteListener mOnItemDeleteListener) {
        this.mOnItemDeleteListener = mOnItemDeleteListener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final CommentInfo info = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceID, parent, false);
        ImageButton head = (ImageButton) view.findViewById(R.id.head_comment);
        TextView name = (TextView) view.findViewById(R.id.name_comment);
        TextView time = (TextView) view.findViewById(R.id.time_comment);
        TextView comment = (TextView) view.findViewById(R.id.content_comment);
        final TextView number = (TextView) view.findViewById(R.id.number);
        final ImageButton good = (ImageButton) view.findViewById(R.id.good);

        name.setText(info.getName());
        time.setText(info.getTime());
        comment.setText(info.getComment());
        number.setText(String.valueOf(info.getNumber()));
        good.setBackgroundResource(info.getImageID());

        if(info.getHead() != null){
            Bitmap temp = BitmapFactory.decodeByteArray(info.getHead(), 0, info.getHead().length);
            Drawable drawable =  new BitmapDrawable(temp);
            head.setBackground(drawable);
        }
        else{
            head.setBackgroundResource(R.mipmap.me);
        }
        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemDeleteListener.onDeleteClick(position);
            }
        });

        return  view;
    }

}
