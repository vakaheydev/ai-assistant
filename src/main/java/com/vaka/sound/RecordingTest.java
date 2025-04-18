package com.vaka.sound;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class RecordingTest {
    // текущий звуковой файл
    private File file;
    // полное имя файла
    private String soundFileName;
    // основное имя файла
    private String filename = "samples_";
    // номер файла
    private int suffix = 0;
    // аудио формат
    private AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    private int MONO = 1;
    // определение формата аудио данных
    private AudioFormat format = new AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED, 44100, 16, MONO, 2, 44100, true);
    // микрофонный вход
    private TargetDataLine microphone;

    // создать новый файл
    File getNewFile() {
        try {
            do {
                // новое название файла
                soundFileName = filename + (suffix++) + "." + fileType.getExtension();
                file = new File(soundFileName);
            } while (!file.createNewFile());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return file;
    }

    // запуск записи
    public void startRecording() {
        new Thread(() -> {
            // линию соединения
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            // проверить, поддерживается ли линия
            if (!AudioSystem.isLineSupported(info)) {
                JOptionPane.showMessageDialog(null, "Line not supported" +
                                info, "Line not supported",
                        JOptionPane.ERROR_MESSAGE);
            }
            try {
                // получить подходящую линию
                microphone = (TargetDataLine) AudioSystem.getLine(info);
                // открываем линию соединения с указанным
                // форматом и размером буфера
                microphone.open(format, microphone.getBufferSize());
                // поток микрофона
                AudioInputStream sound = new AudioInputStream(microphone);
                // запустить линию соединения
                microphone.start();
                System.out.println("Listening to microphone... Speak now!");
                // записать содержимое потока в файл
                AudioSystem.write(sound, fileType, file);
            } catch (LineUnavailableException ex) {
                JOptionPane.showMessageDialog(null, "Line not available" +
                                ex, "Line not available",
                        JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "I/O Error " + ex,
                        "I/O Error", JOptionPane.ERROR_MESSAGE);
            }
        }).start();
    }

    // остановка записи
    public void stopRecording() {
        microphone.stop();
        microphone.close();
    }

    public static void main(String[] args) {
        RecordingTest rec = new RecordingTest();
        File f = rec.getNewFile();
        rec.startRecording();
    }
}
