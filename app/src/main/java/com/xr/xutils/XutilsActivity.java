package com.xr.xutils;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.xr.news.R;

import java.io.File;

public class XutilsActivity extends AppCompatActivity {

    private Context mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xutils);
        mcontext = this;
    }

    public void donwload(View view) {
        EditText edit = findViewById(R.id.et_url);
        String url = edit.getText().toString().trim();

        HttpUtils httpUtils = new HttpUtils();
        httpUtils.download(url, Environment.getExternalStorageDirectory() + "/X_download/feiq.exe", new RequestCallBack<File>() {
            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                Toast.makeText(mcontext,"total="+total+";current="+current,Toast.LENGTH_SHORT).show();

                super.onLoading(total, current, isUploading);
            }

            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                File file = responseInfo.result;
                Toast.makeText(mcontext,file.toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });
    }
}
