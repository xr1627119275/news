package com.xr.asycnHttpClient;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.xr.asycnHttpClient.net.LoginHttpUtils;
import com.xr.news.R;

public class AsycnHttpClientActivity extends AppCompatActivity implements View.OnClickListener{

    private Context mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_httpclient);
        mcontext = this;
        findViewById(R.id.bt_get).setOnClickListener(this);
        findViewById(R.id.bt_Post).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_get:
//                Toast.makeText(this,"-------",Toast.LENGTH_SHORT).show();
                LoginHttpUtils.AsycnHttpClientGet(mcontext,"root","123");
                break;
            case R.id.bt_Post:
                LoginHttpUtils.AsycnHttpClientPost(mcontext,"root","123");
//                LoginHttpUtils.HttpClientPost(handler,"哈哈","123");
                break;
        }
    }
}
