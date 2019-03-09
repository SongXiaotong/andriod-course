package com.example.a12524.httpapi.method;

import com.example.a12524.httpapi.model.repo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface GithubService{
    @Headers("Authorization: token 68cdf548bc82b4394c6b9bf398d672b6a9741bf0")
    @GET("/users/{user_name}/repos")
    rx.Observable<List<repo>> getRepository(@Path("user_name") String user_name);

}

