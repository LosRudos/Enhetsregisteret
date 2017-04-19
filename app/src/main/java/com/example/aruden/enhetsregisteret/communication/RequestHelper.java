package com.example.aruden.enhetsregisteret.communication;

import android.content.Context;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aruden.enhetsregisteret.Constants;
import com.example.aruden.enhetsregisteret.R;
import com.example.aruden.enhetsregisteret.utils.JsonParser;
import com.example.aruden.enhetsregisteret.utils.Organization;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class RequestHelper {

    private RequestHelperListener listener;
    private Context context;
    private Thread thread;

    public RequestHelper(RequestHelperListener listener) {
        this.listener = listener;
    }

    private void jsonSearch(String string) {
        String url = createUrl(string);
        if (url == null) {
            return;
        }
        context = listener.getContext();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ArrayList<Organization> orgsList = new ArrayList<>();
                try {
                    orgsList = JsonParser.parseResponse(context, response);
                } catch (JSONException e) {
                        listener.onError(context.getString(R.string.error_no_organizations_found));
                }
                listener.onResult(orgsList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    listener.onError(context.getString(R.string.error_volley_timeout));
                } else {
                    listener.onError(context.getString(R.string.error_volley_unkown));
                }
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(listener.getContext());
        requestQueue.add(jsonObjectRequest);
    }

    public void delayedSearch(final String url) {
        if (thread != null) {
            if (thread.isAlive()) {
                thread.interrupt();
            }
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                long timeToSearch = System.currentTimeMillis() + 500;
                while (System.currentTimeMillis() < timeToSearch) {
                    synchronized (this) {
                        try {
                            wait(timeToSearch - System.currentTimeMillis());
                        } catch (Exception e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                }
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }
                jsonSearch(url);
            }
        };
        thread = new Thread(runnable);
        thread.start();
    }

    private String createUrl(String string) {
        context = listener.getContext();
        String urlStart;
        String urlEnd;
        if (string.matches(context.getString(R.string.regex_numbers_only))) {
            if (string.length() != Constants.ORG_NUMBER_LENGTH) {
                listener.onError(context.getString(R.string.error_nine_numbers_minimum));
                return null;
            }
            urlStart = context.getString(R.string.url_start_number);
            urlEnd = context.getString(R.string.url_end_number);
        } else {
            if (string.length() < Constants.MINIMUM_SEARCH_LENGTH) {
                listener.onError(context.getString(R.string.error_short_search_text));
                return null;
            }
            urlStart = context.getString(R.string.url_start_string);
            urlEnd = context.getString(R.string.url_end_string);
            try {
                string = URLEncoder.encode(string, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // Bør vise feilmelding
                return null;
            }

        }
        return (urlStart + string + urlEnd);
    }
}
