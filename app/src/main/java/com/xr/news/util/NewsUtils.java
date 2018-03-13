package com.xr.news.util;

import android.content.Context;

import com.xr.news.bean.NewsBean;
import com.xr.news.dao.NewsDao;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by 16271 on 2018/3/4.
 */

public class NewsUtils {
    public static String newsPath_url = "http://11.14.3.216:8080/Test/servlet/GetNewsServlet";

    public static ArrayList<NewsBean> getAllNewsForNetWork(Context context) {
        ArrayList<NewsBean> arrayList = new ArrayList<NewsBean>();
        try {
            URL url = new URL(newsPath_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(1000 * 5);
            connection.setRequestMethod("GET");

            int code = connection.getResponseCode();
            if (code == 200) {
                InputStream in = connection.getInputStream();
                String result = StreamUtils.StreamToString(in);
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("newss");
                for (int i = 0; i <jsonArray.length() ; i++) {
                    JSONObject news_json = jsonArray.getJSONObject(i);
                    NewsBean newsBean = new NewsBean();
                    newsBean.id =  news_json.getInt("id");
                    newsBean.comment = news_json.getInt("comment");
                    newsBean.type = news_json.getInt("type");
                    newsBean.time = news_json.getString("time");
                    newsBean.des = news_json.getString("des");
                    newsBean.title = news_json.getString("title");
                    newsBean.news_url = news_json.getString("news_url");
                    newsBean.icon_url = news_json.getString("icon_url");
                    arrayList.add(newsBean);
                }
                new NewsDao(context).delete();
                new NewsDao(context).saveNews(arrayList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static ArrayList<NewsBean> getAllNewsForDatabase(Context context){

        return new NewsDao(context).getNews();
    }
}
