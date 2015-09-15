package com.elite.contentgetter;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private final String MOOC_URL = "http://www.imooc.com/api/teacher?type=4&num=30";
    private ListView mNewsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNewsListView=(ListView)findViewById(R.id.main_list);
        MyAsyncTask asyncTask = new MyAsyncTask();
        asyncTask.execute(MOOC_URL);
        try {
            List<NewsBean> newsList=asyncTask.get();
            NewsAdapter newsAdapter=new NewsAdapter(MainActivity.this,newsList);
            mNewsListView.setAdapter(newsAdapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private class MyAsyncTask extends AsyncTask<String, Void, List<NewsBean>> {

        @Override
        protected List<NewsBean> doInBackground(String... params) {
            return getNewsList(params[0]);
        }
    }

    private List<NewsBean> getNewsList(String URL) {
        List<NewsBean> newsList = new ArrayList<>();
        //把InputStream转成String.
        try {
            String result = readStream(new URL(MOOC_URL).openStream());
//            Log.d("RESULT_MOOC", result);
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                NewsBean news = new NewsBean();
                news.setContent(object.getString("description"));
                news.setImageUrl(object.getString("picSmall"));
                news.setTitle(object.getString("name"));
                newsList.add(news);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newsList;
    }

    private String readStream(InputStream inputStream) {
        StringBuffer result = new StringBuffer();
        try {
            String line;
            InputStreamReader isr = new InputStreamReader(inputStream, "utf-8");
            BufferedReader reader = new BufferedReader(isr);
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}
