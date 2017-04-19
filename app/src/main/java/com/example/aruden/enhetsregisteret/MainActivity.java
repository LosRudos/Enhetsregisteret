package com.example.aruden.enhetsregisteret;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.aruden.enhetsregisteret.communication.RequestHelper;
import com.example.aruden.enhetsregisteret.communication.RequestHelperListener;
import com.example.aruden.enhetsregisteret.utils.Organization;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RequestHelperListener {

    RequestHelper requestHelper;
    ArrayAdapter<String> orgArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupSearchField();
        requestHelper = new RequestHelper(this);
        orgArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1);
    }

    private void setupSearchField() {
        EditText searchField = (EditText)findViewById(R.id.editText1);
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchString = s.toString();
                sendSearchReq(searchString);
            }
        });
    }

    void sendSearchReq(String SearchString) {
        requestHelper.delayedSearch(SearchString);
    }

    private void displayList(final ArrayList<Organization> orgList) {
        int listSize = orgList.size();
        orgArrayAdapter.clear();
        for (int i=0; i<listSize; i++) {
            Organization currentOrg = orgList.get(i);
            String currentName = currentOrg.getName();
            orgArrayAdapter.add(currentName);
        }
        ListAdapter orgAdapter = orgArrayAdapter;
        ListView orgListView = (ListView) findViewById(R.id.orgListView);
        orgListView.setAdapter(orgAdapter);

        orgListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Organization currentOrg = orgList.get(position);
                        goToDetailView(currentOrg);
                    }
                }
        );
    }

    public void goToDetailView(Organization organization) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra("orgData", organization);
        startActivity(detailIntent);
    }

    @Override
    public void onResult(ArrayList<Organization> result) {
        displayList(result);
    }

    @Override
    public void onError(String errorMsg) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), errorMsg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public Context getContext() {
        return this;
    }
}

