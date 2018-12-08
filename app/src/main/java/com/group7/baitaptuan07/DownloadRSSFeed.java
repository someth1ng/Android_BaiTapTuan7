package com.group7.baitaptuan07;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class DownloadRSSFeed extends AsyncTask<String, Void, ArrayList<SingleItem>> {

    ShowHeadlines callerContext; //caller class
    String urlAddress;
    String urlCaption;
    ProgressDialog dialog = null;

    public DownloadRSSFeed ( Context callerContext){
        this.callerContext = (ShowHeadlines) callerContext;
        dialog = new ProgressDialog(callerContext);
    }

    protected void onPreExecute() {
        this.dialog.setMessage("Please wait\nReading RSS feed ..." );
        this.dialog.setCancelable(false); //outside touching doesn't dismiss you
        this.dialog.show();
    }

    @Override
    protected ArrayList<SingleItem> doInBackground(String... strings) {
        ArrayList<SingleItem> newList = new ArrayList<SingleItem>();
        urlAddress = strings[0];
        urlCaption = strings[1];

        this.dialog.setMessage("Please wait\nReading RSS feed " +  urlCaption + "...");

        try {
            URL url = new URL(urlAddress);
            URLConnection connection;
            connection = url.openConnection();

            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream in = httpConnection.getInputStream();
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document dom = db.parse(in);

                Element treeElements = dom.getDocumentElement();

                newList.clear();
                NodeList itemNodes = treeElements.getElementsByTagName("item");
                if ((itemNodes != null) && (itemNodes.getLength() > 0)) {
                    for (int i = 0; i < itemNodes.getLength(); i++) {
                        newList.add(dissectItemNode(itemNodes, i));
                    }
                }
            }
            httpConnection.disconnect();
        } catch (Exception e) {
            Log.e("Error>>", e.getMessage());
        }
        return newList;
    }

    @Override
    protected void onPostExecute(ArrayList<SingleItem> singleItems) {
        super.onPostExecute(singleItems);

        callerContext.newList = singleItems;
        ArrayList<String> item_titles = new ArrayList<String>();
        for (int i = 0; i < singleItems.size(); i++) {
            item_titles.add(singleItems.get(i).getTitle());
        }
        ArrayAdapter<String> adapterNews =
                new ArrayAdapter<>(callerContext, android.R.layout.simple_list_item_1, item_titles);
        callerContext.listView.setAdapter(adapterNews);

        dialog.dismiss();
    }

    public SingleItem dissectItemNode(NodeList nodeList, int i) {
        try {
            Element entry = (Element) nodeList.item(i);
            Element title = (Element) entry.getElementsByTagName("title").item(0);
            Element description = (Element) entry.getElementsByTagName("description").item(0);
            Element pubDate = (Element) entry.getElementsByTagName(
                    "pubDate").item(0);
            Element link = (Element) entry.getElementsByTagName(
                    "link").item(0);
            String titleValue = title.getFirstChild().getNodeValue();
            String descriptionValue =description.getFirstChild().getNodeValue();
            String dateValue = pubDate.getFirstChild().getNodeValue();
            String linkValue = link.getFirstChild().getNodeValue();
            SingleItem singleItem = new SingleItem( dateValue,
                    titleValue,
                    descriptionValue,
                    linkValue );
            return  singleItem;
        } catch (DOMException e) {
            return new SingleItem("", "Error", e.getMessage(), null);
        }
    }
}
