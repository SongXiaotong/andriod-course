package com.example.a12524.experimentone_week6;

import java.io.Serializable;

public class listitem implements Serializable{
    private String type;
    private String name;
    private String title;
    private String element;
    private String color;

    public String getType(){
        return type;
    }
    public void setType(String t){
        type = t;
    }

    public String getName(){
        return name;
    }
    public void setName(String t){
        name = t;
    }

    public String getElement(){
        return element;
    }
    public void setElement(String t){
        element = t;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String t){
        title = t;
    }
    public String getColor(){
        return color;
    }
    public void setColor(String t){
        color = t;
    }
}
