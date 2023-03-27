package org.example.utils;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static String getResponseContent(String responseBody) {
        String content = null;
        try {
            String[] choices = getArrayFromJson(responseBody, "choices");
            String message = getObjectFromJson(choices[0], "message");
            content = getObjectFromJson(message, "content");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    public static String extractURLFromJSON(String jsonStr) {
        String[] dataArray = getArrayFromJson(jsonStr, "data");
        String dataObj = dataArray[0];
        return getObjectFromJson(dataObj, "url");
    }

    private static String[] getArrayFromJson(String jsonString, String key) {
        List<String> values = new ArrayList<>();
        int start = jsonString.indexOf("\"" + key + "\":[");
        if (start != -1) {
            start += key.length() + 3;
            int end = jsonString.indexOf("]", start);
            String[] parts = jsonString.substring(start, end).split("\\{");
            for (String part : parts) {
                if (part.length() > 2) {
                    values.add("{" + part);
                }
            }
        }
        return values.toArray(new String[0]);
    }

    private static String getObjectFromJson(String jsonString, String key) {
        String value = null;
        int start = jsonString.indexOf("\"" + key + "\":");
        if (start != -1) {
            start += key.length() + 3;
            int end = jsonString.indexOf(",", start);
            if (end == -1) {
                end = jsonString.indexOf("}", start);
            }
            value = jsonString.substring(start, end);
            value = value.replaceAll("\"", "");
        }
        return value;
    }
}
