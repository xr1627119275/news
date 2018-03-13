package com.xr.upload;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xr.news.R;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;

public class UploadActivity extends AppCompatActivity {

    private Context mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        mcontext = this;
    }

    public void fileupload(View view) {
        EditText path = findViewById(R.id.et_filepath);
        String url = "http://11.14.3.216:8080/Test/servlet/UploaderServlet";
        String path_str = path.getText().toString().trim();
        try {
            RequestParams params = new RequestParams();
            params.put("filename",new File(path_str));
            AsyncHttpClient client = new AsyncHttpClient();
            client.post(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Toast.makeText(mcontext,"Success",Toast.LENGTH_SHORT).show();
                    if(statusCode==200){
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
