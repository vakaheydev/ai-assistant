package com.vaka;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayerTest {
    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                new File("C:\\Users\\Vaka\\Documents\\java_learn\\ai-assistant\\apple.wav"));
        AudioFormat format = audioStream.getFormat();
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);

        line.open(format);
        line.start();

        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = audioStream.read(buffer, 0, buffer.length)) != -1) {
            line.write(buffer, 0, bytesRead);
        }

        line.drain();
        line.close();
        audioStream.close();
    }
}
