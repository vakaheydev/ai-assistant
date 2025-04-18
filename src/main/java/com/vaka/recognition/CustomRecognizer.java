package com.vaka.recognition;

import java.util.concurrent.BlockingQueue;

public interface CustomRecognizer {
    void listen(BlockingQueue<String> queue);
}
