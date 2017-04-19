package com.example.aruden.enhetsregisteret.utils;

import android.content.Context;
import android.content.res.Resources;

import com.example.aruden.enhetsregisteret.R;

import org.json.JSONObject;

import java.io.Serializable;

public class Organization implements Serializable {

    private String[] orgContent;
    private String[] orgHeader;


    public Organization(Context context, JSONObject jsonObject) {
        Resources res = context.getResources();
        String[] jsonMainUnits = res.getStringArray(R.array.json_main_units);
        String[] jsonSubUnits = res.getStringArray(R.array.json_sub_units);
        String[] jsonMainUnitsHeader = res.getStringArray(R.array.jason_main_units_h);
        String[] jsonSubUnitsHeader = res.getStringArray(R.array.json_sub_units_h);
        int mainUnitsLength = jsonMainUnits.length;
        int subUnitsLength = jsonSubUnits.length;
        int totalLength = mainUnitsLength + subUnitsLength;
        String data;
        String header;
        JSONObject subObject;

        orgContent = new String[totalLength];
        orgHeader = new String[totalLength];
        for (int i = 0; i < totalLength; i++) {
            data = null;
            header = null;
            if (i < mainUnitsLength) {
                data = JsonParser.getJsonData(jsonObject, jsonMainUnits[i]);
                header = jsonMainUnitsHeader[i];
            } else if (i < totalLength) {
                subObject = JsonParser.getSubObject(jsonObject, jsonSubUnits[i - mainUnitsLength]);
                if (subObject != null) {
                    if (jsonSubUnits[i - mainUnitsLength].contains("adresse")) {
                        data = JsonParser.fillAddress(context, subObject);
                    } else if (jsonSubUnits[i - mainUnitsLength].contains("kode")) {
                        data = JsonParser.fillNaeKode(context, subObject);
                    }
                    header = jsonSubUnitsHeader[i - mainUnitsLength];
                }
            }
            if (data != null) {
                orgContent[i] = data;
                orgHeader[i] = header;
            } else {
                orgContent[i] = null;
                orgHeader[i] = null;
            }
        }
    }

    public String[] getOrgContent() {
        return orgContent;
    }

    public String[] getOrgHeader() {
        return orgHeader;
    }

    public String getName() {
        return orgContent[1];
    }
}
