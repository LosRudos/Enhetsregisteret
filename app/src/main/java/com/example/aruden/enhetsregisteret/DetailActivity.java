package com.example.aruden.enhetsregisteret;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.aruden.enhetsregisteret.utils.JsonParser;

import org.json.JSONObject;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private JSONObject organization;
    private List<List<String>> orgData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getOrg();
        displayList();
    }

    private void getOrg() {
        Intent parentIntent = getIntent();
        String stringOrg = parentIntent.getStringExtra("orgData");
        try {
            organization = new JSONObject(stringOrg);
        } catch (Throwable e) {
            organization = null;
        }
    }

    private void displayList() {
        orgData = JsonParser.fillOrgData(this, organization);
        ListAdapter orgAdapter = new TwoLineAdapter(this,orgData);
        ListView orgListView = (ListView) findViewById(R.id.orgDetailView);
        orgListView.setAdapter(orgAdapter);

        orgListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        List<String> headerList = orgData.get(Constants.ORG_LIST_HEADER);
                        if (headerList.get(position) == getString(R.string.web_page_h)) {
                            List<String> contentList = orgData.get(Constants.ORG_LIST_CONTENT);
                            String url = contentList.get(position);
                            goToUrl(url);
                        } else if (headerList.get(position) == getString(R.string.business_address_h)) {
                            List<String> contentList = orgData.get(Constants.ORG_LIST_CONTENT);
                            String address = contentList.get(position);
                            searchForAddress(address);
                        }
                    }
                }
        );
    }

    private void goToUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (url.startsWith("http://") || url.startsWith("https://")) {
            intent.setData(Uri.parse(url));
        } else {
            intent.setData(Uri.parse("http://" + url));
        }
        startActivity(intent);
    }

    private void searchForAddress(String address) {
        String split[] = address.split("\\n");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(getString(R.string.google_search_address) + split[0] + " " + split[2]));
        startActivity(intent);
    }

    public Context getContext() {
        return this;
    }
}