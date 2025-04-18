package com.vaka.recognition;

import org.vosk.Recognizer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class FileRecognizer extends AbstractRecognizer {
    public FileRecognizer(CloseableModel closeableModel) {
        super(closeableModel);
    }

    @Override
    public void listen(BlockingQueue<String> queue) {
        try (Recognizer recognizer = new Recognizer(closeableModel.getModel(), 16000)) {
            File file = new File("speech.wav");
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = ais.read(buffer)) >= 0) {
                if (recognizer.acceptWaveForm(buffer, bytesRead)) {
                    String jsonText = recognizer.getResult();
                    String text = extractText(jsonText);

                    if (!text.isBlank()) {
                        queue.put(text);
                    }
                }
            }
        } catch (UnsupportedAudioFileException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
