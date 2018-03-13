package com.xr.news.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by 16271 on 2018/3/5.
 */

public class StreamUtils {
    public static String StreamToString(InputStream in) throws Exception{
        String result = "";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;

        StringBuffer sb = new StringBuffer();
        while ((len = in.read(buffer)) != -1) {
            outputStream.write(buffer,0,len);
            outputStream.flush();
        }
        result = new String(outputStream.toByteArray(),"gbk");
        outputStream.close();

        return result;
    }


}
