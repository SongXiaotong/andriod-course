package com.example.a12524.httpapi.method;

import com.example.a12524.httpapi.model.PostIssue;
import com.example.a12524.httpapi.model.issue;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IssueService{
    @Headers("Authorization: token 68cdf548bc82b4394c6b9bf398d672b6a9741bf0")
    @GET("/repos/{user_name}/{repo_name}/issues")
    rx.Observable<List<issue>> getIssue(@Path("user_name") String user_name, @Path("repo_name") String repo_name);

    @Headers("Authorization: token 68cdf548bc82b4394c6b9bf398d672b6a9741bf0")
    @POST("/repos/{user_name}/{repo_name}/issues")
    rx.Observable<PostIssue> postIssue(@Body PostIssue issue, @Path("user_name") String user_name, @Path("repo_name") String repo_name);

}
