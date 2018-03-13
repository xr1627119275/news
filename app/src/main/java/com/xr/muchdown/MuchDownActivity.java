package com.xr.muchdown;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.xr.news.R;
import com.xr.util.SharedUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MuchDownActivity extends AppCompatActivity {

    private EditText et_threadCount;
    private Context mcontext;
    private LinearLayout linearLayout;
    private  int threadCount ;
    private  int blocksize = 0;
    private  String path = "http://11.14.3.216:8080/Test/feiq.exe";
    private  int runningTrheadCount = 0;
    private Map<Integer,ProgressBar> map = new HashMap<Integer, ProgressBar>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_much_down);
        mcontext = this;
        et_threadCount = findViewById(R.id.ed_threadCount);
        linearLayout = findViewById(R.id.ll_progress_layout);
    }

    public void download(View view) {

        threadCount = Integer.parseInt(et_threadCount.getText().toString().trim());
        linearLayout.removeAllViews();
        for (int i = 0; i < threadCount; i++) {
            ProgressBar progressBar = (ProgressBar) View.inflate(mcontext, R.layout.child_progressbar_layout, null);
            map.put(i,progressBar);
            linearLayout.addView(progressBar);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    download();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void download() throws Exception{
        URL url = new URL(path);
        HttpURLConnection openConnection = (HttpURLConnection) url.openConnection();
        openConnection.setRequestMethod("GET");
        openConnection.setConnectTimeout(10 * 1000);
        int code = openConnection.getResponseCode();
        if (code == 200) {
            long length = openConnection.getContentLength();
            RandomAccessFile randomAccessFile = new RandomAccessFile(new File(getFileName(path)), "rw");
            randomAccessFile.setLength(length);
            blocksize = (int) (length / threadCount);
            for (int i = 0; i < threadCount; i++) {
                int startIndex = i * blocksize;
                int endIndex = (i + 1) * blocksize - 1;
                if (i == 2) {
                    endIndex = (int) (length - 1);
                }
                new DownloadThread(i, startIndex, endIndex).start();
            }
        }
    }
    private class DownloadThread extends Thread {
        private int currentThreadProgressTotal;
        private int id;
        private int start;
        private int end;
        private int lastPostion;

        public DownloadThread(int id, int start, int end) {
            this.id = id;
            this.start = start;
            this.end = end;
            this.currentThreadProgressTotal = end-start+1;
        }

        @Override
        public void run() {
            // 分段请求网络连接
            ProgressBar progressBar = map.get(id);
            synchronized (MuchDownActivity.class) {
                runningTrheadCount += 1;
            }
            try {
                URL url = new URL(path);
                HttpURLConnection openConnection = (HttpURLConnection) url.openConnection();
                openConnection.setRequestMethod("GET");
                openConnection.setConnectTimeout(10 * 1000);
                System.out.println(id + "开始下载");
//                File file2 = new File(getFilePath()+id + ".txt");
                if (SharedUtils.getLastPostion(mcontext, id)!=-1) {
//                    BufferedReader bufferedReader = new BufferedReader(
//                            new InputStreamReader(new FileInputStream(file2)));
//                    String lastPostion_str = bufferedReader.readLine();
//                    lastPostion = Integer.parseInt(lastPostion_str);
//                    bufferedReader.close();
                    lastPostion = SharedUtils.getLastPostion(mcontext, id);
                    if(lastPostion == (end+1)){
                        progressBar.setProgress(currentThreadProgressTotal);
                        runningTrheadCount-=1;
                    }
                    openConnection.setRequestProperty("Range", "bytes=" + lastPostion + "-" + end);
                } else {
                    lastPostion = start;
                    openConnection.setRequestProperty("Range", "bytes=" + start + "-" + end);
                }
                int code = openConnection.getResponseCode();
                if (code == 206) {
                    InputStream inputStream = openConnection.getInputStream();
                    int len = 0;
                    int total = 0;
                    byte[] bytes = new byte[1024];
                    RandomAccessFile randomAccessFile = new RandomAccessFile(new File(getFileName(path)), "rw");
                    randomAccessFile.seek(lastPostion);
                    while ((len = inputStream.read(bytes)) != -1) {
                        randomAccessFile.write(bytes, 0, len);

                        total += len;
                        int currentThreadPostion = total + lastPostion;
                        SharedUtils.setLastPostion(mcontext,currentThreadPostion,id);
//                        File file = new File(getFilePath()+id + ".txt");
//                        RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
//                        accessFile.write(String.valueOf(currentThreadPostion).getBytes());
//                        accessFile.close();
                        progressBar.setMax(currentThreadProgressTotal);
                        progressBar.setProgress(currentThreadPostion-start);
                    }
//                    file2.delete();
                    inputStream.close();
                    randomAccessFile.close();
                    System.out.println(id + "下载完毕");
                    // 标志一个下载结束
                    synchronized (MuchDownActivity.class) {
                        runningTrheadCount -= 1;
                        if (runningTrheadCount == 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(mcontext,"下载成功",Toast.LENGTH_SHORT).show();
                                }
                            });
//                            for (int i = 0; i < threadCount; i++) {
//                                new File(getFilePath()+i + ".txt").delete();
//                            }
                        }
                    }

                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    public  String getFileName(String url) {
        return Environment.getExternalStorageDirectory()+"/"+url.substring(url.lastIndexOf("/"));
    }
    public  String getFilePath(){
        return Environment.getExternalStorageDirectory()+"/";
    }
}
