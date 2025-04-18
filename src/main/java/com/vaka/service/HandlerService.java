package com.vaka.service;

import com.vaka.handling.Handler;
import com.vaka.handling.MirrorHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class HandlerService {
    @Async("asynchronousListenerExecutor")
    public CompletableFuture<String> handle(String phrase) {
        log.info("Handling phrase: {}", phrase);

        Handler handler = resolveHandler(phrase);

        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String result = handler.handle(phrase);

        return CompletableFuture.completedFuture(result);
    }

    private Handler resolveHandler(String phrase) {
        return new MirrorHandler();
    }
}
