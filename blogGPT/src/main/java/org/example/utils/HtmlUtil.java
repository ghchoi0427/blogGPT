package org.example.utils;

public class HtmlUtil {
    public static String htmlFormatter(String imageUrl, String content) {
        int bodyIndex = content.indexOf("<body");
        if (bodyIndex == -1) {
            return content;
        }

        String imageTag = "<img src=\"" + imageUrl + "\">";
        int insertIndex = content.indexOf(">", bodyIndex) + 1; // insert after the opening body tag
        String before = content.substring(0, insertIndex);
        String after = content.substring(insertIndex);
        return before + imageTag + after;
    }


}