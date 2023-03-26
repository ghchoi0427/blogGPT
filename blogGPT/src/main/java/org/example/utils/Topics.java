package org.example.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Topics {

    private static List<String> topics;

    static {
        try {
            topics = loadFileFromResources(System.getenv("TOPIC_FILENAME"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getTopics() {
        return new ArrayList<>(topics);
    }

    public static void updateTopicsFromFile() throws IOException {
        List<String> newTopics = loadFileFromResources(System.getenv("TOPIC_FILENAME"));
        if (!newTopics.equals(topics)) {
            topics = newTopics;
            System.out.println("Updated list of topics: " + topics);
        }
    }

    static {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000 * 60 * 10);
                    updateTopicsFromFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public static List<String> loadFileFromResources(String filename) throws IOException {
        ClassLoader classLoader = Topics.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(filename);
        if (inputStream == null) {
            throw new FileNotFoundException("File not found in resources: " + filename);
        } else {
            List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            }
            return lines;
        }
    }
}