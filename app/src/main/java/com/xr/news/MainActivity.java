package com.xr.news;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xr.news.adapter.NewsAdapter;
import com.xr.news.bean.NewsBean;
import com.xr.news.util.NewsUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView lv;
    private Context mcontext;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ArrayList<NewsBean> allNews = (ArrayList<NewsBean>) msg.obj;
            if(allNews!=null&&allNews.size()>0) {
                lv.setAdapter(new NewsAdapter(mcontext, allNews));
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mcontext = this;
        lv = findViewById(R.id.listView);

        ArrayList<NewsBean> allNews_database = NewsUtils.getAllNewsForDatabase(mcontext);
        if (allNews_database != null && allNews_database.size() > 0) {
            NewsAdapter newsAdapter = new NewsAdapter(mcontext, allNews_database);

            lv.setAdapter(newsAdapter);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message m = Message.obtain();
                m.obj = NewsUtils.getAllNewsForNetWork(mcontext);
                handler.sendMessage(m);
            }
        }).start();

        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NewsBean newsBean = (NewsBean) parent.getItemAtPosition(position);
        String url = newsBean.news_url;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
