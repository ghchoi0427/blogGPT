package org.example;

import org.example.batch.BlogProgram;
import org.example.openai.ChatApi;
import org.example.openai.ImageApi;

public class Main {
    public static void main(String[] args) {
        BlogProgram program = new BlogProgram(new ChatApi(), new ImageApi(), 4);
        program.start();
    }
}