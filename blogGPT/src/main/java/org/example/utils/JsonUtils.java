package org.example.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonUtils {

    public static String getResponseContent(String responseBody) {
        String content = null;
        try {
            JsonObject responseJson = JsonParser.parseString(responseBody).getAsJsonObject();
            JsonArray choices = responseJson.getAsJsonArray("choices");

            JsonObject message = choices.get(0).getAsJsonObject().getAsJsonObject("message");
            content = message.get("content").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    public static String extractURLFromJSON(String jsonStr) {
        JsonObject jsonObj = JsonParser.parseString(jsonStr).getAsJsonObject();
        JsonArray dataArray = jsonObj.getAsJsonArray("data");
        JsonObject dataObj = dataArray.get(0).getAsJsonObject();
        return dataObj.get("url").getAsString();
    }
}