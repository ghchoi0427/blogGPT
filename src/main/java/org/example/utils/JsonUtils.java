package org.example.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    public static String getResponseContent(String responseBody) {
        String content = null;
        try {
            JSONObject responseJson = new JSONObject(responseBody);
            JSONArray choices = responseJson.getJSONArray("choices");

            JSONObject message = choices.getJSONObject(0).getJSONObject("message");
            content = message.getString("content");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static String extractURLFromJSON(String jsonStr) throws JSONException {
        JSONObject jsonObj = new JSONObject(jsonStr);
        JSONArray dataArray = jsonObj.getJSONArray("data");
        JSONObject dataObj = dataArray.getJSONObject(0);
        return dataObj.getString("url");
    }
}
