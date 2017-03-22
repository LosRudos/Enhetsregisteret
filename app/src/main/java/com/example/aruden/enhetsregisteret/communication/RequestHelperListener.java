package com.example.aruden.enhetsregisteret.communication;

import android.content.Context;

import com.example.aruden.enhetsregisteret.utils.Organization;

import org.json.JSONObject;

import java.util.ArrayList;

public interface RequestHelperListener {
    void onError(String errorMsg);
    Context getContext();
    void onResult(ArrayList<JSONObject> result);
}
