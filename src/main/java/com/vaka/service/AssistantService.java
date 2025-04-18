package com.vaka.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class AssistantService {
    private final RecognitionService recognitionService;
    private final HandlerService handlerService;
    private final BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);

    public AssistantService(RecognitionService recognitionService, HandlerService handlerService) {
        this.recognitionService = recognitionService;
        this.handlerService = handlerService;
    }

    @Async("asynchronousListenerExecutor")
    public void start() {
        recognitionService.listen(queue);

        while (true) {
            log.info("Listening...");
            String phrase = takePhrase(queue);
            log.info("Got phrase: {}", phrase);
            CompletableFuture<String> future = handlerService.handle(phrase);

            future
                    .thenAccept(res -> log.info("Got result: {}", res));
        }
    }

    private String takePhrase(BlockingQueue<String> queue) {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
