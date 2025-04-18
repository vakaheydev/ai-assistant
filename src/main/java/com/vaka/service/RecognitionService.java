package com.vaka.service;

import com.vaka.recognition.CustomRecognizer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;

@Service
public class RecognitionService {
    private final CustomRecognizer recognizer;

    public RecognitionService(CustomRecognizer recognizer) {
        this.recognizer = recognizer;
    }

    @Async("asynchronousListenerExecutor")
    public void listen(BlockingQueue<String> queue) {
        recognizer.listen(queue);
    }
}
