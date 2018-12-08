package com.group7.baitaptuan07;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ChannelsActivity extends Activity {
    TextView txtChannels;
    ListView listChannels;
    int      type;

    String[] VNChannelsUrl = {
            "https://vnexpress.net/rss/the-thao.rss",
            "https://vnexpress.net/rss/du-lich.rss",
            "https://vnexpress.net/rss/giao-duc.rss"
    };

    String[] VNTitles = {
            "Thể thao",
            "Du lịch",
            "Giáo dục"
    };

    String[] TNChannelsUrl = {
            "https://thanhnien.vn/rss/thoi-su/quoc-phong.rss",
            "https://thanhnien.vn/rss/viet-nam/phap-luat.rss",
            "https://thanhnien.vn/rss/phap-luat/trong-an.rss"
    };

    String[] TNTitles = {
            "Quốc phòng",
            "Pháp luật",
            "Trọng án"
    };

    void initUI(String title, String[] _items) {
        txtChannels  = (TextView) findViewById(R.id.txtChannels);
        txtChannels.setText("CHANNELS IN " + title);
        this.setTitle("CHANNELS IN " + title);

        listChannels = (ListView) findViewById(R.id.listChannels);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, _items);
        listChannels.setAdapter(adapter);
        listChannels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showHeadLines = new Intent(ChannelsActivity.this, ShowHeadlines.class);
                Bundle myChannelsData = new Bundle();
                if (type == 0) {
                    myChannelsData.putString("urlAddress", VNChannelsUrl[position]);
                    myChannelsData.putString("urlCaption", VNTitles[position]);
                    myChannelsData.putString("title", "ITEMS IN CHANNEL " + VNTitles[position] + " - VNEXPRESS");
                } else {
                    myChannelsData.putString("urlAddress", TNChannelsUrl[position]);
                    myChannelsData.putString("urlCaption", TNTitles[position]);
                    myChannelsData.putString("title", "ITEMS IN CHANNEL " + TNTitles[position] + " - THANH NIÊN");
                }
                showHeadLines.putExtras(myChannelsData);

                startActivity(showHeadLines);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channels);

        txtChannels  = (TextView) findViewById(R.id.txtChannels);
        listChannels = (ListView) findViewById(R.id.listChannels);
        ArrayAdapter<String> adapter;

        Intent callingIntent = getIntent();
        Bundle myData = callingIntent.getExtras();
        int key = myData.getInt("key");
        type = key;
        if (key == 0) {
            initUI("VNEXPRESS", VNTitles);
        } else {
            initUI("THANH NIÊN", TNTitles);
        }
    }
}
