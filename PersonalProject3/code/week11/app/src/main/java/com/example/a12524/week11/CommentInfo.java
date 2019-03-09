package com.example.a12524.week11;

import android.graphics.drawable.Drawable;

public class CommentInfo {
    private byte[] head;
    private String name;
    private String time;
    private String comment;
    private int number;
    private int imageID;
    private int status;

    public CommentInfo(byte[] os, String name, String time, String comment, int number, int imageID, int status){
        this.head = os;
        this.name = name;
        this.time = time;
        this.comment = comment;
        this.number = number;
        this.imageID = imageID;
        this.status = status;
    }
    public String getName(){
        return this.name;
    }
    public void setNumber(int number){
        this.number = number;
    }
    public int getNumber(){
        return this.number;
    }
    public void setImageID(int id){
        this.imageID = id;
    }
    public int getImageID(){
        return this.imageID;
    }
    public String getComment(){
        return this.comment;
    }
    public String getTime(){
        return  this.time;
    }
    public byte[] getHead(){
        return this.head;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
