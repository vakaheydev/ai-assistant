package com.vaka.service;

import com.vaka.neural.service.NeuralService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;
import org.vosk.Recognizer;

import javax.sound.sampled.*;
import java.io.IOException;

@Service
public class RecognitionService {
    private final FileTextService fileTextService;
    private final NeuralService neuralService;

    public RecognitionService(FileTextService fileTextService, NeuralService neuralService) {
        this.fileTextService = fileTextService;
        this.neuralService = neuralService;
    }

    @Async
    public void start() {
        LibVosk.setLogLevel(LogLevel.DEBUG);

        try (Model model = new Model(
                "C:\\Users\\Vaka\\Documents\\java_learn\\ai-assistant\\src\\main\\resources\\model\\vosk-model-small-ru-0.22");
             Recognizer recognizer = new Recognizer(model, 16000)) {

            TargetDataLine line;
            AudioFormat format = new AudioFormat(16000, 16, 1, true, false);

            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            System.out.println("Listening...");

            byte[] buffer = new byte[4096];
            while (true) {
                int bytesRead = line.read(buffer, 0, buffer.length);
                if (recognizer.acceptWaveForm(buffer, bytesRead)) {
                    String jsonText = recognizer.getResult();
                    String text = extractText(jsonText);
                    if (!text.isBlank()) {
                        System.out.println("Command: " + text);
                        switch (text) {
                            case "привет" -> {
                                fileTextService.writeText("Привет, бро!");
                            }
                            case "открой хром" -> {
                                var pb = new ProcessBuilder("cmd", "/c", "start", "chrome");
                                pb.start();
                                fileTextService.writeText("Открыл хром");
                            }
                            case "открой консоль" -> {
                                var pb = new ProcessBuilder("cmd", "/c", "start", "cmd");
                                pb.start();
                                fileTextService.writeText("Открыл консоль");
                            }
                            case "открой шелл" -> {
                                var pb = new ProcessBuilder("cmd", "/c", "start", "powershell");
                                pb.start();
                                fileTextService.writeText("Открыл консоль");
                            }
                            case "открой папку", "открой проводник" -> {
                                var pb = new ProcessBuilder("explorer");
                                pb.start();
                                fileTextService.writeText("Открыл проводник");
                            }
                            case "открой идею" -> {
                                var pb = new ProcessBuilder("cmd", "/c", "start", "idea");
                                pb.start();
                                fileTextService.writeText("Открыл Intellij IDEA");
                            }

                            case "открой вс", "открой код", "открой вэ эс" -> {
                                var pb = new ProcessBuilder("cmd", "/c", "start", "code");
                                pb.start();
                                fileTextService.writeText("Открыл VS CODE");
                            }

                            case "открой ютуб" -> {
                                new ProcessBuilder("cmd", "/c", "start", "chrome", "https://www.youtube.com").start();
                                fileTextService.writeText("Открыл ютуб");
                            }
                            case "открой блокнот" -> {
                                new ProcessBuilder("cmd", "/c", "start", "notepad").start();
                                fileTextService.writeText("Открыл блокнот");
                            }
                            case "закрой блокнот" -> {
                                new ProcessBuilder("cmd", "/c", "taskkill", "/IM", "notepad.exe", "/F").start();
                                fileTextService.writeText("Закрыл блокнот");
                            }
                        }

                        if (text.contains("спроси") || text.contains("спросить") || text.contains(
                                "узнай") || text.contains("узнать") || text.contains("скажи")) {
                            try {
                                System.out.println("Извлекаю вопросительную часть...");
                                fileTextService.writeText("Думаю...");
                                String response = text.substring(text.indexOf(" "));
                                String answer = neuralService.request(response);
                                fileTextService.writeText(answer);
                            } catch (IndexOutOfBoundsException e) {
                                fileTextService.writeText("Вы не сказали, что спросить :)");
                            }
                        }
//                        System.out.println(text);
//                        String answer = neuralService.request(text);
//                        System.out.println("Neural answer: " + answer);
                    }
                }
            }
        } catch (LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String extractText(String jsonText) {
        String[] split = jsonText.split("\n");
        String textLine = split[1];
        String[] textSplit = textLine.split(":");
        return textSplit[1].substring(2, textSplit[1].length() - 1);
    }
}
