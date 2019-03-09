package com.example.a12524.httpapi.model;

public class PostIssue {
    private String title;
    private String body;
    public PostIssue(String t, String b){
        title = t;
        body = b;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
