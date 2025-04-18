package com.vaka.recognition;

import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Recognizer;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class MicrophoneRecognizer extends AbstractRecognizer {
    public MicrophoneRecognizer(CloseableModel closeableModel) {
        super(closeableModel);
    }

    @Override
    public void listen(BlockingQueue<String> queue) {
        LibVosk.setLogLevel(LogLevel.INFO);

        try (Recognizer recognizer = new Recognizer(closeableModel.getModel(), 16000)) {
            TargetDataLine line;
            AudioFormat format = new AudioFormat(16000, 16, 1, true, false);

            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            byte[] buffer = new byte[4096];
            while (true) {
                int bytesRead = line.read(buffer, 0, buffer.length);
                if (recognizer.acceptWaveForm(buffer, bytesRead)) {
                    String jsonText = recognizer.getResult();
                    String text = extractText(jsonText);

                    if (!text.isBlank()) {
                        queue.put(text);
                    }
                }
            }
        } catch (LineUnavailableException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
