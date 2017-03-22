package com.example.aruden.enhetsregisteret;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

class TwoLineAdapter extends BaseAdapter {

    private Context context;
    private List<List<String>> listContent;

    public TwoLineAdapter(Context context, List<List<String>> listContent) {
        this.context = context;
        this.listContent = listContent;
    }

    @Override
    public int getCount() {
        // Both list in listContent will have same size
        List<String> list = listContent.get(Constants.ORG_LIST_CONTENT);
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // The actual content is in list 0
        List<String> list = listContent.get(Constants.ORG_LIST_CONTENT);
        return list.get(position);
    }

    public String getHeader(int position) {
        List<String> list = listContent.get(Constants.ORG_LIST_HEADER);
        return list.get(position);
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
