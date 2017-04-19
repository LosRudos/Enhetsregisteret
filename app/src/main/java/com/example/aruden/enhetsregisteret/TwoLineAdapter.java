package com.example.aruden.enhetsregisteret;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class TwoLineAdapter extends BaseAdapter {

    private Context context;
    private List<String> listContent;
    private List<String> listHeader;

    public TwoLineAdapter(Context context, String[] listContent, String[] listHeader) {
        this.context = context;
        this.listContent = new ArrayList<>();
        this.listHeader = new ArrayList<>();
        for (int i = 0; i < listContent.length; i++) {
            if (listContent[i] != null) {
                this.listContent.add(listContent[i]);
                this.listHeader.add(listHeader[i]);
            }
        }
    }

    @Override
    public int getCount() {
        return listContent.size();
    }

    @Override
    public Object getItem(int position) {
        return listContent.get(position);
    }

    private String getHeader(int position) {
        return listHeader.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View twoLineView = inflater.inflate(R.layout.two_line_list_item, parent, false);

        String header = getHeader(position);
        String data = (String) getItem(position);
        TextView headerText = (TextView) twoLineView.findViewById(R.id.text1);
        TextView dataText = (TextView) twoLineView.findViewById(R.id.text2);

        headerText.setText(header);
        dataText.setText(data);
        return twoLineView;
    }
}
