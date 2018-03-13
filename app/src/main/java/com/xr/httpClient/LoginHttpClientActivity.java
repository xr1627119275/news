package com.xr.httpClient;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.xr.httpClient.net.LoginHttpUtils;
import com.xr.news.MainActivity;
import com.xr.news.R;

/**
 * Created by 16271 on 2018/3/6.
 */

public class LoginHttpClientActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_httpclient);
        findViewById(R.id.bt_get).setOnClickListener(this);
        findViewById(R.id.bt_Post).setOnClickListener(this);
    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String)msg.obj;
            switch (msg.what){
                case 1:
                    Toast.makeText(LoginHttpClientActivity.this,result,Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(LoginHttpClientActivity.this,result,Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_get:
                LoginHttpUtils.HttpClientGet(handler,"哈哈","123");
                break;
            case R.id.bt_Post:
                LoginHttpUtils.HttpClientPost(handler,"哈哈","123");
                break;
        }
    }
}
