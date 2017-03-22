package com.example.aruden.enhetsregisteret.utils;

import android.content.Context;

import com.example.aruden.enhetsregisteret.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    public static ArrayList<JSONObject> parseResponse(Context context, JSONObject jsonObject) throws JSONException {
        ArrayList<JSONObject> orgList = new ArrayList<>();
        JSONObject organization;
        JSONArray jsonArray;
        int sizeOfJsonArray;

        try {
            jsonArray = jsonObject.getJSONArray(context.getString(R.string.data));
        } catch (JSONException e) {
            throw e;
        }
        sizeOfJsonArray = jsonArray.length();
        for (int i=0; i<sizeOfJsonArray; i++) {
            organization = jsonArray.getJSONObject(i);
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
        if (getJsonData(jsonObject, context.getString(R.string.organization_number)) != null) {
            return true;
        }
        return false;
    }

    public static JSONObject getSubObject(JSONObject jsonObject, String objName) {
        try {
            return jsonObject.getJSONObject(objName);
        } catch (JSONException e) {
            return null;
        }
    }

    private static String fillNaeKode(Context context, JSONObject jsonObject) {
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

    private static String fillAddress(Context context, JSONObject jsonObject) {
        String addrData = "";
        String tmpData;
        tmpData = getJsonData(jsonObject, context.getString(R.string.address));
        if (tmpData != null) {
            addrData+=tmpData;
            addrData+="\n";
        }
        tmpData = getJsonData(jsonObject, context.getString(R.string.zip_code));
        if (tmpData != null) {
            addrData+=tmpData;
            addrData+="\n";
        }
        tmpData = getJsonData(jsonObject, context.getString(R.string.post_office));
        if (tmpData != null) {
            addrData+=tmpData;
            addrData+="\n";
        }
        tmpData = getJsonData(jsonObject, context.getString(R.string.city_number));
        if (tmpData != null) {
            addrData+=tmpData;
            addrData+="\n";
        }
        tmpData = getJsonData(jsonObject, context.getString(R.string.city));
        if (tmpData != null) {
            addrData+=tmpData;
            addrData+="\n";
        }
        tmpData = getJsonData(jsonObject, context.getString(R.string.country_code));
        if (tmpData != null) {
            addrData+=tmpData;
            addrData+="\n";
        }
        tmpData = getJsonData(jsonObject, context.getString(R.string.country));
        if (tmpData != null) {
            addrData+=tmpData;
        }
        return addrData;
    }

    public static List<List<String>> fillOrgData(Context context, JSONObject jsonObject) {
        ArrayList<String> orgData = new ArrayList<>();
        ArrayList<String> orgDataHeader = new ArrayList<>();
        String data;
        JSONObject subObject;
        data = getJsonData(jsonObject, context.getString(R.string.organization_number));
        if (data != null) {
            orgData.add(data);
            orgDataHeader.add(context.getString(R.string.organization_number_h));
        }
        data = getJsonData(jsonObject, context.getString(R.string.name));
        if (data != null) {
            orgData.add(data);
            orgDataHeader.add(context.getString(R.string.name_h));
        }
        data = getJsonData(jsonObject, context.getString(R.string.establish_date));
        if (data != null) {
            orgData.add(data);
            orgDataHeader.add(context.getString(R.string.establish_date_h));
        }
        data = getJsonData(jsonObject, context.getString(R.string.registration_date_in_register));
        if (data != null) {
            orgData.add(data);
            orgDataHeader.add(context.getString(R.string.registration_date_in_register_h));
        }
        data = getJsonData(jsonObject, context.getString(R.string.organization_form));
        if (data != null) {
            orgData.add(data);
            orgDataHeader.add(context.getString(R.string.organization_form_h));
        }
        data = getJsonData(jsonObject, context.getString(R.string.web_page));
        if (data != null) {
            orgData.add(data);
            orgDataHeader.add(context.getString(R.string.web_page_h));
        }
        data = getJsonData(jsonObject, context.getString(R.string.regitered_in_voluntary_register));
        if (data != null) {
            orgData.add(data);
            orgDataHeader.add(context.getString(R.string.regitered_in_voluntary_register_h));
        }
        data = getJsonData(jsonObject, context.getString(R.string.registered_in_vat_register));
        if (data != null) {
            orgData.add(data);
            orgDataHeader.add(context.getString(R.string.registered_in_vat_register_h));
        }
        data = getJsonData(jsonObject, context.getString(R.string.registered_in_buisness_register));
        if (data != null) {
            orgData.add(data);
            orgDataHeader.add(context.getString(R.string.registered_in_buisness_register_h));
        }
        data = getJsonData(jsonObject, context.getString(R.string.registered_in_foundation_register));
        if (data != null) {
            orgData.add(data);
            orgDataHeader.add(context.getString(R.string.registered_in_fundation_register_h));
        }
        data = getJsonData(jsonObject, context.getString(R.string.voluntary_registered_in_vat_reg));
        if (data != null) {
            orgData.add(data);
            orgDataHeader.add(context.getString(R.string.voluntary_registered_in_vat_reg_h));
        }
        data = getJsonData(jsonObject, context.getString(R.string.number_of_employees));
        if (data != null) {
            orgData.add(data);
            orgDataHeader.add(context.getString(R.string.number_of_employees_h));
        }
        data = getJsonData(jsonObject, context.getString(R.string.last_submitted_annual_accounts));
        if (data != null) {
            orgData.add(data);
            orgDataHeader.add(context.getString(R.string.last_submitted_annual_accounts_h));
        }
        data = getJsonData(jsonObject, context.getString(R.string.bankrupt));
        if (data != null) {
            orgData.add(data);
            orgDataHeader.add(context.getString(R.string.bankrupt_h));
        }
        data = getJsonData(jsonObject, context.getString(R.string.under_liquidation));
        if (data != null) {
            orgData.add(data);
            orgDataHeader.add(context.getString(R.string.under_liquidation_h));
        }
        data = getJsonData(jsonObject, context.getString(R.string.under_compulsory_liquidation));
        if (data != null) {
            orgData.add(data);
            orgDataHeader.add(context.getString(R.string.under_compulsory_liquidation_h));
        }
        data = getJsonData(jsonObject, context.getString(R.string.parent_entity));
        if (data != null) {
            orgData.add(data);
            orgDataHeader.add(context.getString(R.string.parent_entity_h));
        }
        subObject = getSubObject(jsonObject, context.getString(R.string.mail_address));
        if (subObject != null) {
            String address = fillAddress(context, subObject);
            orgData.add(address);
            orgDataHeader.add(context.getString(R.string.mail_address_h));
        }
        subObject = getSubObject(jsonObject, context.getString(R.string.business_address));
        if (subObject != null) {
            String address = fillAddress(context, subObject);
            orgData.add(address);
            orgDataHeader.add(context.getString(R.string.business_address_h));
        }
        subObject = getSubObject(jsonObject, context.getString(R.string.industry_code_1));
        if (subObject != null) {
            String code = fillNaeKode(context, subObject);
            orgData.add(code);
            orgDataHeader.add(context.getString(R.string.industry_code_1_h));
        }
        subObject = getSubObject(jsonObject, context.getString(R.string.industry_code_2));
        if (subObject != null) {
            String code = fillNaeKode(context, subObject);
            orgData.add(code);
            orgDataHeader.add(context.getString(R.string.industry_code_2_h));
        }
        subObject = getSubObject(jsonObject, context.getString(R.string.industry_code_3));
        if (subObject != null) {
            String code = fillNaeKode(context, subObject);
            orgData.add(code);
            orgDataHeader.add(context.getString(R.string.industry_code_3_h));
        }

        List<List<String>> dualOrgData = new ArrayList<>();
        dualOrgData.add(orgData);
        dualOrgData.add(orgDataHeader);
        return dualOrgData;
    }
}