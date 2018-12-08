package com.group7.baitaptuan07;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowHeadlines extends Activity {
    ArrayList<SingleItem> newList = new ArrayList<SingleItem>();
    ListView listView;
    String urlAddress;
    String urlCaption;
    SingleItem selectedItem;
    TextView txtItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_headlines);

        // tim intent dang goi den
        Intent callingIntent = getIntent();
        Bundle myBundle = callingIntent.getExtras();
        urlAddress = myBundle.getString("urlAddress");
        urlCaption = myBundle.getString("urlCaption");

        txtItems = (TextView) findViewById(R.id.txtItems);
        txtItems.setText(myBundle.getString("title"));

        listView = (ListView) this.findViewById(R.id.listItems);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = newList.get(position);
                showNiceDialog(selectedItem, getApplicationContext());
            }
        });

        DownloadRSSFeed downloader = new DownloadRSSFeed(ShowHeadlines.this);
        downloader.execute(urlAddress, urlCaption);
    }

    public void showNiceDialog(SingleItem item, Context context){
        String title = item.getTitle();
        String desc = item.getDescription();

        try {
          final Uri storyLink = Uri.parse(item.getLink());
          AlertDialog.Builder myBuilder = new AlertDialog.Builder(this);
          myBuilder
                  .setTitle(Html.fromHtml(urlCaption))
                  .setMessage(title + "\n\n" + Html.fromHtml(desc) + "\n")
                  .setPositiveButton("Close", null)
                  .setNegativeButton("More", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          Intent browser = new Intent(Intent.ACTION_VIEW, storyLink);
                          startActivity(browser);
                      }
                  })
                  .show();
        } catch (Exception e) {
            Log.e("Error DialogBox", e.getMessage() );
        }
    }
}
