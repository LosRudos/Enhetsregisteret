package com.example.aruden.enhetsregisteret;


import android.content.Context;
import android.test.mock.MockContext;

import com.example.aruden.enhetsregisteret.utils.JsonParser;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JsonParserUnitTest extends TestCase {

    @Mock
    Context mockContext;
    @Mock
    JSONObject jsonObject;

    String objectContent;
    String naeKode;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mockContext = new MockContext();
        jsonObject = new JSONObject();
    }

    @Test
    public void testGetJsonDataName() {
        givenJsonObjectHasContent();
        whenElementToGetIs("navn");
        thenShouldBeEqualTo(objectContent, "Giant Leap");
    }

    @Test
    public void testGetJsonDataOrgnr() {
        givenJsonObjectHasContent();
        whenElementToGetIs("organisasjonsnummer");
        thenShouldBeEqualTo(objectContent, "982831962");
    }

    @Test
    public void testValidOrganization() {
        givenJsonObjectHasContent();
        thenJsonObjShouldBeValid();
    }

    @Test
    public void testFillNaeKode() {
        givenNaeKodeIs("123", "Software");
        thenKoseShoudBe("123\nSoftware");
    }

    public void givenJsonObjectHasContent() {
        try {
            when(jsonObject.getString("navn")).thenReturn("Giant Leap");
            when(jsonObject.getString("organisasjonsnummer")).thenReturn("982831962");
        } catch (JSONException e) {
            e.printStackTrace();
            assertFalse(false);
        }
        when(mockContext.getString(R.string.organization_number)).thenReturn("organisasjonsnummer");
    }

    public void whenElementToGetIs(String string) {
        objectContent = JsonParser.getJsonData(jsonObject, string);
    }

    public void thenShouldBeEqualTo(String string1, String string2) {
        assertEquals(string1, string2);
    }

    public void thenJsonObjShouldBeValid() {
        boolean valid = JsonParser.validOrganization(mockContext, jsonObject);
        assertEquals(true, valid);
    }

    public void givenNaeKodeIs(String code, String description) {
        when(mockContext.getString(R.string.industy_code_code)).thenReturn("code");
        when(mockContext.getString(R.string.industy_code_description)).thenReturn(description);
        when(JsonParser.getJsonData(jsonObject, mockContext.getString(R.string.industy_code_description))).thenReturn(description);
        when(JsonParser.getJsonData(jsonObject, mockContext.getString(R.string.industy_code_code))).thenReturn(code);
        naeKode = JsonParser.fillNaeKode(mockContext, jsonObject);
    }

    public void thenKoseShoudBe(String expectation) {
        assertEquals(expectation, naeKode);
    }
}