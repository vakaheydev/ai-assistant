package com.vaka.recognition;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;
import org.vosk.Model;

import java.io.IOException;

public class CloseableModel {
    private final Model model;

    public CloseableModel(Model model) {
        this.model = model;
    }

    @PreDestroy
    public void destroy() throws IOException {
        model.close();
    }

    public Model getModel() {
        return model;
    }
}
