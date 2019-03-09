package com.example.a12524.httpapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a12524.httpapi.adapter.IssueAdapter;
import com.example.a12524.httpapi.method.IssueService;
import com.example.a12524.httpapi.method.SSLSocketFactoryCompat;
import com.example.a12524.httpapi.model.PostIssue;
import com.example.a12524.httpapi.model.issue;

import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class addIssue extends AppCompatActivity {

    private IssueAdapter issueAdapter;
    private List<issue> issues = new ArrayList<>();
    private RecyclerView recyclerView;
    private String baseURL;
    private String user_name;
    private String repo_name;
    private TextView title;
    private TextView body;
    private Button add;
    private X509TrustManager trustAllCert;
    private SSLSocketFactory sslSocketFactory;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_issue);
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle = intent.getExtras();
        issues.clear();
        repo_name = bundle.getString("reponame");
        user_name = bundle.getString("username");
        init();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        issueAdapter = new IssueAdapter(issues);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper. VERTICAL);
        recyclerView.setAdapter(issueAdapter);

        getIssues();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titlecontent = title.getText().toString();
                String bodycontent = body.getText().toString();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
                Date date = new Date(System.currentTimeMillis());
                time = simpleDateFormat.format(date);
                final PostIssue addissue = new PostIssue(titlecontent,bodycontent);
                OkHttpClient build = new OkHttpClient.Builder()
                        .connectTimeout(2, TimeUnit.SECONDS)
                        .readTimeout(2, TimeUnit.SECONDS)
                        .writeTimeout(2, TimeUnit.SECONDS)
                        .sslSocketFactory(sslSocketFactory, trustAllCert)
                        .build();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(baseURL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .client(build)
                        .build();
                final IssueService service = retrofit.create(IssueService.class);
                service.postIssue(addissue, user_name, repo_name)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<PostIssue>(){
                            @Override
                            public void onCompleted() {
                                Toast.makeText(addIssue.this, "Post successfully", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(addIssue.this, "Post failed", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNext(PostIssue postIssue) {
                                issues.clear();
                                service.getIssue(user_name, repo_name)
                                        .subscribeOn(Schedulers.newThread())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Subscriber<List<issue>>() {

                                            @Override
                                            public void onCompleted() {
                                                issueAdapter.notifyDataSetChanged();
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                Toast.makeText(addIssue.this, "post fail!", Toast.LENGTH_SHORT).show();
                                                Log.e("wrong", e.toString());
                                            }

                                            @Override
                                            public void onNext(List<issue> issue0) {
                                                for(int i = 0; i < issue0.size(); ++i){
                                                    issues.add(issue0.get(i));
                                                }
                                                if(issues.size() == 0){
                                                    Toast.makeText(addIssue.this, "There's no issue", Toast.LENGTH_SHORT).show();
                                                }
                                             //   Toast.makeText(addIssue.this, "post succcessfully!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });
            }

        });

    }

    public void init(){
        baseURL = "https://api.github.com/";
        recyclerView = (RecyclerView) findViewById(R.id.issuelist);
        title = (TextView) findViewById(R.id.title);
        body = (TextView) findViewById(R.id.body);
        add = (Button) findViewById(R.id.add);
        trustAllCert =
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                };
        sslSocketFactory = new SSLSocketFactoryCompat(trustAllCert);
    }

    public void getIssues(){

        if(issues != null)
        issues.clear();

        OkHttpClient build = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .sslSocketFactory(sslSocketFactory, trustAllCert)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(build)
                .build();
        IssueService service = retrofit.create(IssueService.class);
        service.getIssue(user_name, repo_name)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<issue>>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(addIssue.this, "There's a 404 error!", Toast.LENGTH_SHORT).show();
                        Log.e("wrong", e.toString());
                    }

                    @Override
                    public void onNext(List<issue> issue0) {

                        Log.e("start", String.valueOf(issues.size()));
                        for(int i = 0; i < issue0.size(); ++i){
                                issues.add(issue0.get(i));
                        }
                        if(issues.size() == 0){
                            Toast.makeText(addIssue.this, "There's no issue", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
       issueAdapter.notifyDataSetChanged();
    }
}
