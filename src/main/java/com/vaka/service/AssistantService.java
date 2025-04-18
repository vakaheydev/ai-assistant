package com.vaka.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AssistantService {
    private final RecognitionService recognitionService;

    public AssistantService(RecognitionService recognitionService) {
        this.recognitionService = recognitionService;
    }

    public void start() {
        
    }
}
