package org.example.batch;

import org.example.openai.ChatApi;
import org.example.openai.ImageApi;
import org.example.tistory.BlogWriter;
import org.example.utils.HtmlUtil;
import org.example.utils.Topics;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BlogProgram {

    private final ChatApi chatApi;
    private final ImageApi imageApi;
    private final ExecutorService executorService;

    String[] topics = Topics.getTopics();
    int index = 0;

    public BlogProgram(ChatApi chatApi, ImageApi imageApi, int numThreads) {
        this.chatApi = chatApi;
        this.imageApi = imageApi;
        this.executorService = Executors.newFixedThreadPool(numThreads);
    }

    public void writeBlog() {
        String topic = topics[(index++) % topics.length];
        Future<String> imageUrlFuture = executorService.submit(() -> imageApi.run(topic));
        Future<String> contentFuture = executorService.submit(() -> chatApi.run(topic, 1000));

        String imageUrl;
        String content;
        try {
            imageUrl = imageUrlFuture.get();
            content = contentFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }

        String htmlContent = HtmlUtil.htmlFormatter(topic, imageUrl, content);
        try {
            BlogWriter.upload(topic, htmlContent);
        } catch (IOException | InterruptedException e) {
            System.err.println(e);
            throw new RuntimeException(e);
        } finally {
            shutdown();
        }
    }

    public void shutdown() {
        executorService.shutdown();
    }

}
