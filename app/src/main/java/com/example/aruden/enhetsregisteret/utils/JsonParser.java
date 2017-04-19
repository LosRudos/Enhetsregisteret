package com.example.aruden.enhetsregisteret.utils;

import android.content.Context;
import android.content.res.Resources;

import com.example.aruden.enhetsregisteret.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParser {

    public static ArrayList<Organization> parseResponse(Context context, JSONObject jsonObject) throws JSONException {
        ArrayList<Organization> orgList = new ArrayList<>();
        JSONObject jsonOrganization;
        Organization organization;
        JSONArray jsonArray;
        int sizeOfJsonArray;

        try {
            jsonArray = jsonObject.getJSONArray(context.getString(R.string.data));
        } catch (JSONException e) {
            if (JsonParser.validOrganization(context, jsonObject)) {
                organization = new Organization(context, jsonObject);
                orgList.add(organization);
                return orgList;
            } else {
                throw e;
            }
        }
        sizeOfJsonArray = jsonArray.length();
        for (int i=0; i<sizeOfJsonArray; i++) {
            jsonOrganization = jsonArray.getJSONObject(i);
            organization = new Organization(context, jsonOrganization);
            orgList.add(organization);
        }
        return orgList;
    }

    public static String getJsonData(JSONObject jsonObject, String dataName) {
        try {
            return jsonObject.getString(dataName);
        } catch (JSONException e) {
            return null;
        }
    }

    public static boolean validOrganization(Context context, JSONObject jsonObject) {
        return getJsonData(jsonObject, context.getString(R.string.organization_number)) != null;
    }

    public static JSONObject getSubObject(JSONObject jsonObject, String objName) {
        try {
            return jsonObject.getJSONObject(objName);
        } catch (JSONException e) {
            return null;
        }
    }

    public static String fillNaeKode(Context context, JSONObject jsonObject) {
        String kode;
        String beskr;
        kode = getJsonData(jsonObject, context.getString(R.string.industy_code_code));
        beskr = getJsonData(jsonObject, context.getString(R.string.industy_code_description));
        if (kode != null) {
            if (beskr != null) {
                kode+="\n";
                kode+=beskr;
            }
            return kode;
        }
        return "";
    }

    public static String fillAddress(Context context, JSONObject jsonObject) {
        String addrData = "";
        String tmpData;
        Resources res = context.getResources();
        String[] jsonAddrUnits = res.getStringArray(R.array.json_address_units);
        for (int i = 0; i < jsonAddrUnits.length; i++) {
            tmpData = getJsonData(jsonObject, jsonAddrUnits[i]);
            if (tmpData != null) {
                addrData += tmpData;
                if (i < (jsonAddrUnits.length - 1)) {
                    addrData += "\n";
                }
            }
        }
        return addrData;
    }
}