package com.xr.httpClient.net;

import android.os.Handler;
import android.os.Message;

import com.xr.news.util.NewsUtils;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by 16271 on 2018/3/6.
 */

public class LoginHttpUtils {
    public static void HttpClientGet(final Handler handler, final String username, final String password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String path = "http://11.14.3.216:8080/Test/servlet/LoginServlet?username="+ URLEncoder.encode(username,"utf-8")+"&pwd="+URLEncoder.encode(password,"utf-8");
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(path);
                    HttpResponse response = httpClient.execute(httpGet);
                    StatusLine statusLine = response.getStatusLine();
                    int code = statusLine.getStatusCode();
                    if(code==200){
                        HttpEntity entity = response.getEntity();
                        InputStream in = entity.getContent();
                        String result = StreamUtils.StreamToString(in);
                        Message message = Message.obtain();
                        message.obj = result;
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public static void HttpClientPost(final Handler handler, final String username, final String password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String path = "http://11.14.3.216:8080/Test/servlet/LoginServlet";
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(path);
                    ArrayList<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
                    BasicNameValuePair basicNameValuePair = new BasicNameValuePair("username",username);
                    BasicNameValuePair basicNameValuePair1 = new BasicNameValuePair("pwd",password);
                    list.add(basicNameValuePair);
                    list.add(basicNameValuePair1);
                    UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(list,"utf-8");
                    httpPost.setEntity(encodedFormEntity);
                    HttpResponse response = httpClient.execute(httpPost);
                    StatusLine statusLine = response.getStatusLine();
                    int code = statusLine.getStatusCode();
                    if(code==200){
                        HttpEntity entity = response.getEntity();
                        InputStream in = entity.getContent();
                        String result = StreamUtils.StreamToString(in);
                        Message message = Message.obtain();
                        message.obj = result;
                        message.what = 2;
                        handler.sendMessage(message);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
