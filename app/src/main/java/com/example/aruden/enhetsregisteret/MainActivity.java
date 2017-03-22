package com.example.aruden.enhetsregisteret;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aruden.enhetsregisteret.communication.RequestHelper;
import com.example.aruden.enhetsregisteret.communication.RequestHelperListener;
import com.example.aruden.enhetsregisteret.utils.JsonParser;

import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RequestHelperListener {

    RequestHelper requestHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText searchField = (EditText)findViewById(R.id.editText1);
        requestHelper = new RequestHelper(this);

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

    private void displayList(final ArrayList<JSONObject> orgList) {
        int listSize = orgList.size();
        ArrayList<String> orgNames = new ArrayList<>();
        for (int i=0; i<listSize; i++) {
            JSONObject currentOrg = orgList.get(i);
            String currentName = JsonParser.getJsonData(currentOrg, "navn");
            orgNames.add(currentName);
        }
        ListAdapter orgAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, orgNames);
        ListView orgListView = (ListView) findViewById(R.id.orgListView);
        orgListView.setAdapter(orgAdapter);

        orgListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        JSONObject currentOrg = orgList.get(position);
                        goToDetailNew(view, currentOrg);
                    }
                }
        );
    }

    private void displayList(String string) {
        ArrayList<String> message = new ArrayList<>();
        message.add(string);
        ListAdapter stringAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, message);
        ListView orgListView = (ListView) findViewById(R.id.orgListView);
        orgListView.setAdapter(stringAdapter);
    }

    // TODO rename to goToDetail
    public void goToDetailNew(View view, JSONObject organization) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra("orgData", organization.toString());
        startActivity(detailIntent);
    }

    @Override
    public void onResult(ArrayList<JSONObject> result) {
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

