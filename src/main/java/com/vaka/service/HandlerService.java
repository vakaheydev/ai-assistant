package com.vaka.service;

import com.vaka.handling.PhraseHandler;
import com.vaka.handling.HandlerResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class HandlerService {
    private final HandlerResolver handlerResolver;

    public HandlerService(HandlerResolver handlerResolver) {
        this.handlerResolver = handlerResolver;
    }

    @Async("asynchronousListenerExecutor")
    public CompletableFuture<String> handle(String phrase) {
        log.info("Handling phrase: {}", phrase);

        PhraseHandler phraseHandler = handlerResolver.resolve(phrase);
        phrase = phrase.substring(phrase.indexOf(" ") + 1);
        String result = phraseHandler.handle(phrase);

        return CompletableFuture.completedFuture(result);
    }
}
