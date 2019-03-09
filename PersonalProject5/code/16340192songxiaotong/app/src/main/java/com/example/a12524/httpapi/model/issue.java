package com.example.a12524.httpapi.model;

public class issue {
    private String title;
    private String state;
    private String created_at;
    private String body;

    public issue(String title0, String state0, String created_at0, String body0){
        title = title0;
        state = state0;
        created_at = created_at0;
        body = body0;
    }

    public String getBody() {
        return body;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getState() {
        return state;
    }

    public String getTitle() {
        return title;
    }

}
