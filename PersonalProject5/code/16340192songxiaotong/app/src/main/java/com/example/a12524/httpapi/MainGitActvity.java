package com.example.a12524.httpapi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a12524.httpapi.adapter.RepoAdapter;
import com.example.a12524.httpapi.method.GithubService;
import com.example.a12524.httpapi.method.SSLSocketFactoryCompat;
import com.example.a12524.httpapi.model.repo;

import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import rx.android.schedulers.AndroidSchedulers;


public class MainGitActvity extends AppCompatActivity {
    private TextView input;
    private Button search;
    private List<com.example.a12524.httpapi.model.repo> repos = new ArrayList<>();
    private RecyclerView recyclerView;
    private repo repo;
    private RepoAdapter repoAdapter;
    private CardView myLayout;
    private String user;
    private ConnectivityManager connectivityManager;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_git_actvity);

        initView();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        repoAdapter = new RepoAdapter(repos);
        repoAdapter.setOnItemClickListener(new RepoAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainGitActvity.this,addIssue.class);
                Bundle bundle = new Bundle();
                bundle.putString("username", repos.get(position).getFull_name().split("/")[0]);
                bundle.putString("reponame", repos.get(position).getName());
                intent.putExtras(bundle);
                startActivityForResult(intent, 2);
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper. VERTICAL);
        recyclerView.setAdapter(repoAdapter);

        final String baseURL = "https://api.github.com/";
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(haveNet(MainGitActvity.this)){
                    repos.clear();
                    final X509TrustManager trustAllCert =
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
                    final SSLSocketFactory sslSocketFactory = new SSLSocketFactoryCompat(trustAllCert);
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
                    GithubService service = retrofit.create(GithubService.class);
                    String user_name = input.getText().toString();
                    if(user_name == null){
                        user_name = "SongXiaotong";
                    }
                    service.getRepository(user_name)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<List<repo>>() {

                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    Toast.makeText(MainGitActvity.this, "There's a 404 error!", Toast.LENGTH_SHORT).show();
                                    Log.e("wrong", e.toString());
                                }

                                @Override
                                public void onNext(List<repo> repo0) {

                                    Log.e("start", String.valueOf(repos.size()));
                                    for(int i = 0; i < repo0.size(); ++i){
                                        if(repo0.get(i).getHas_issues()){
                                            repos.add(repo0.get(i));
                                        }
                                    }
                                    if(repos.size() == 0){
                                        Toast.makeText(MainGitActvity.this, "There's no repository", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        repoAdapter.notifyDataSetChanged();
                                    }
                                }
                            });

                    repoAdapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(MainGitActvity.this, "No network connection", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public void initView(){
        input = (TextView)findViewById(R.id.serach_node);
        search = (Button) findViewById(R.id.search_button);
        recyclerView = (RecyclerView) findViewById(R.id.reposlist);
        user = input.getText().toString();
    }

    public boolean haveNet(Context context) {
        // 获得网络状态管理器
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            // 建立网络数组
            NetworkInfo[] net_info = connectivityManager.getAllNetworkInfo();
            if (net_info != null) {
                for (int i = 0; i < net_info.length; i++) {
                    // 判断获得的网络状态是否是处于连接状态
                    if (net_info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
