package org.example.utils;

public class HtmlUtil {
    public static String htmlFormatter(String title, String imageUrl, String content) {
        String html = "<html><head><meta charset=\"utf-8\"></head><body>";
        html += "<h1>" + title + "</h1>";
        html += "<img src=\"" + imageUrl + "\"/>";
        html += "<p>";
        String[] contentParts = content.split(",");
        for (int i = 0; i < contentParts.length; i++) {
            html += contentParts[i].trim();
            if (i != contentParts.length - 1) {
                html += "<br/>";
            }
        }
        html += "</p>";
        html += "</body></html>";
        return html;
    }


}
