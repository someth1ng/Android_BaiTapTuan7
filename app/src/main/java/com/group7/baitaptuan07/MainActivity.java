package com.group7.baitaptuan07;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class MainActivity extends Activity {
    GridView gridView;

    String[] items = { "VNExpress", "Thanh Nien" };

    Integer[] logos = { R.drawable.vne_logo, R.drawable.logo_thanhnien };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridView);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
//        gridView.setAdapter(adapter);
        gridView.setAdapter(new custom_grid_view_item(this, logos));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showChannels = new Intent(MainActivity.this, ChannelsActivity.class);
                Bundle myData = new Bundle();
                myData.putInt("key", position);
                showChannels.putExtras(myData);
                startActivity(showChannels);
            }
        });
    }
}
