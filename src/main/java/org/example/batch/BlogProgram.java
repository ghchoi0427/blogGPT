package org.example.batch;

import org.example.openai.ChatApi;
import org.example.openai.ImageApi;
import org.example.tistory.BlogWriter;
import org.example.utils.HtmlUtil;
import org.example.utils.Topics;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

public class BlogProgram {

    private final ChatApi chatApi;
    private final ImageApi imageApi;
    private final ExecutorService executorService;
    private final ScheduledExecutorService scheduledExecutorService;

    List<String> topics = Topics.getTopics();
    int index = 0;

    public BlogProgram(ChatApi chatApi, ImageApi imageApi, int numThreads) {
        this.chatApi = chatApi;
        this.imageApi = imageApi;
        this.executorService = Executors.newFixedThreadPool(numThreads);
        this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
    }

    public void start() {
        scheduledExecutorService.scheduleAtFixedRate(this::writeBlog, 0, 24 * 60 * 60 / 15, TimeUnit.SECONDS);
    }

    public void writeBlog() {
        System.out.println("upload initiated");
        String topic = topics.get((index++) % topics.size());
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

        String htmlContent = HtmlUtil.htmlFormatter(imageUrl, content);
        try {
            BlogWriter.upload(topic, htmlContent);
        } catch (IOException | InterruptedException e) {
            System.err.println(e);
            throw new RuntimeException(e);
        } finally {
            shutdown();
            System.out.println("writeBlog ended");
        }
    }

    public void shutdown() {
        scheduledExecutorService.shutdown();
        executorService.shutdown();
    }
}
