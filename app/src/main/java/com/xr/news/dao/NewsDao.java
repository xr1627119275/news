package com.xr.news.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xr.news.bean.NewsBean;

import java.util.ArrayList;

/**
 * Created by 16271 on 2018/3/5.
 */

public class NewsDao {
    private final Context context;
    private final NewsOpenHelper openHelper;

    public NewsDao(Context context){
        this.context = context;
        openHelper = new NewsOpenHelper(context);
    }
    public void delete(){
        SQLiteDatabase db = openHelper.getReadableDatabase();
        db.delete("news",null,null);
        db.close();
    }
    public ArrayList<NewsBean> getNews(){
        ArrayList<NewsBean> list = new ArrayList<NewsBean>();

        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from news order by _id desc", null);
        if(cursor!=null&&cursor.getColumnCount()>0){
            while (cursor.moveToNext()){
                NewsBean newsBean = new NewsBean();
                newsBean.id = cursor.getInt(0);
                newsBean.title = cursor.getString(1);
                newsBean.des =	cursor.getString(2);
                newsBean.icon_url =	cursor.getString(3);
                newsBean.news_url =	cursor.getString(4);
                newsBean.type = cursor.getInt(5);
                newsBean.time =	cursor.getString(6);
                newsBean.comment = cursor.getInt(7);
                list.add(newsBean);
            }
        }
        db.close();
        return list;
    }

    public void saveNews(ArrayList<NewsBean> list){
        SQLiteDatabase db = openHelper.getReadableDatabase();
        for (NewsBean newsBean: list) {
            ContentValues values = new ContentValues();
            values.put("_id", newsBean.id);
            values.put("title", newsBean.title);
            values.put("des", newsBean.des);
            values.put("icon_url", newsBean.icon_url);
            values.put("news_url", newsBean.news_url);
            values.put("type", newsBean.type);
            values.put("time", newsBean.time);
            values.put("comment", newsBean.comment);
            db.insert("news",null,values);
        }
        db.close();
    }

}
