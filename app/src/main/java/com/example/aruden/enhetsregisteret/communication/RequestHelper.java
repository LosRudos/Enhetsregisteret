package com.example.aruden.enhetsregisteret.communication;

import android.content.Context;
import android.util.Log;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                ArrayList<JSONObject> orgsList = new ArrayList<>();
                try {
                    orgsList = JsonParser.parseResponse(context, response);
                } catch (JSONException e) {
                    if (JsonParser.validOrganization(context, response)) {
                        orgsList.add(response);
                    } else {
                        // TODO Hva skal gjøres hvis listen er tom?
                        listener.onError(context.getString(R.string.no_organizations_found));
                    }
                }
                listener.onResult(orgsList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    listener.onError(context.getString(R.string.volley_timeout_error));
                } else {
                    listener.onError(context.getString(R.string.volley_unkown_error));
                }
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(listener.getContext());
        requestQueue.add(jsonObjectRequest);
    }

    public void delayedSearch(final String url) {
        Log.d("Debug", "Staring delayed search");
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

    private String fixUrlSpaces(String url) {
        return url.replaceAll(" ", "%20");
    }

    private String createUrl(String string) {
        String urlStart;
        String urlEnd;
        if (string.matches("\\d+(?:\\.\\d+)?")) {
            if (string.length() != Constants.ORG_NUMBER_LENGTH) {
                listener.onError("Organisasjonsnummer må bestå av 9 tall");
                return null;
            }
            urlStart = "http://data.brreg.no/enhetsregisteret/enhet/";
            urlEnd = ".json";
        } else {
            if (string.length() < Constants.MINIMUM_SEARCH_LENGHT) {
                listener.onError("Søketekst for kort");
                return null;
            }
            urlStart = "http://data.brreg.no/enhetsregisteret/enhet.json?page=0&size=100&$filter=startswith(navn,'";
            urlEnd = "')";
            string = fixUrlSpaces(string);
        }
        Log.d("Debug", urlStart + string + urlEnd);
        return (urlStart + string + urlEnd);
    }
}
