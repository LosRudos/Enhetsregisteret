package com.example.aruden.enhetsregisteret;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.aruden.enhetsregisteret.utils.Organization;


public class DetailActivity extends AppCompatActivity {

    private Organization organization;
    private String[] orgData;
    private String[] orgHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getOrg();
        displayList();
    }

    private void getOrg() {
        Intent parentIntent = getIntent();
        organization = (Organization)parentIntent.getSerializableExtra("orgData");
    }

    private void displayList() {
        orgData = removeNullElements(organization.getOrgContent());
        orgHeader = removeNullElements(organization.getOrgHeader());
        ListAdapter orgAdapter = new TwoLineAdapter(this, orgData, orgHeader);
        ListView orgListView = (ListView) findViewById(R.id.orgDetailView);
        orgListView.setAdapter(orgAdapter);

        orgListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (orgHeader[position].equals(getString(R.string.web_page_h))) {
                            String url = orgData[position];
                            goToUrl(url);
                        } else if (orgHeader[position].equals(getString(R.string.business_address_h))) {
                            String address = orgData[position];
                            searchForAddress(address);
                        }
                    }
                }
        );
    }

    private void goToUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (url.startsWith(getString(R.string.http)) || url.startsWith(getString(R.string.https))) {
            intent.setData(Uri.parse(url));
        } else {
            intent.setData(Uri.parse(getString(R.string.http) + url));
        }
        startActivity(intent);
    }

    private void searchForAddress(String address) {
        String split[] = address.split("\\n");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(getString(R.string.google_search_address) + split[0] + " " + split[2]));
        startActivity(intent);
    }

    private String[] removeNullElements(String[] list) {
        int newLength = 0;
        int newIndex = 0;
        for (String aList : list) {
            if (aList != null) {
                newLength = ++newLength;
            }
        }
        String[] reducedList = new String[newLength];
        for (String aList : list) {
            if (aList != null) {
                reducedList[newIndex] = aList;
                newIndex = ++newIndex;
            }
        }
        return reducedList;
    }
}