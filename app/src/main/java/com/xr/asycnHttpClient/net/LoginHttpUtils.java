package com.xr.asycnHttpClient.net;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.xr.news.util.StreamUtils;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by 16271 on 2018/3/6.
 */

public class LoginHttpUtils {
    private static final String TAG = "-----LoginHttpUtils----";

    public static void AsycnHttpClientGet(final Context context, final String username, final String password) {

        try {
            String path = "http://11.14.3.216:8080/Test/servlet/LoginServlet?username=" + URLEncoder.encode(username, "utf-8") + "&pwd=" + URLEncoder.encode(password, "utf-8");
            AsyncHttpClient httpClient = new AsyncHttpClient();
            httpClient.get(path, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (statusCode == 200) {
                        Log.d(TAG, "Successs");
                        try {
                            String result = new String(responseBody, "utf-8");
                            Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.d(TAG, "Failure");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void AsycnHttpClientPost(final Context context, final String username, final String password) {

                try {
                    String path = "http://11.14.3.216:8080/Test/servlet/LoginServlet";
                    AsyncHttpClient httpClient = new AsyncHttpClient();
                    RequestParams requestParams = new RequestParams();
                    requestParams.put("username",username);
                    requestParams.put("pwd",password);
                    httpClient.post(path, requestParams, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            if(statusCode==200){
                                try {
                                    String result = new String(responseBody, "utf-8");
                                    Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

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
