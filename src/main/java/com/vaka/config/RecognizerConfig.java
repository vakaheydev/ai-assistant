package com.vaka.config;

import com.vaka.recognition.CloseableModel;
import com.vaka.recognition.CustomRecognizer;
import com.vaka.recognition.MicrophoneRecognizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.vosk.Model;

import java.io.IOException;

@Configuration
public class RecognizerConfig {
    @Value("${recognizer.model.path}")
    private String MODEL_FILE_PATH;

    @Bean
    public CustomRecognizer recognizer() {
        return new MicrophoneRecognizer(model());
    }

    @Bean
    public CloseableModel model() {
        try {
            return new CloseableModel(new Model(MODEL_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
