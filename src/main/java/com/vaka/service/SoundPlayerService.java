package com.vaka.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class SoundPlayerService {
    private String VOICE = "Vsevolod";
    private List<Process> processes = new ArrayList<>();
    private AtomicBoolean isRun = new AtomicBoolean(false);
    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public SoundPlayerService() {
        Thread worker = new Thread(this::runWorker, "speech-worker");
        worker.setDaemon(true);
        worker.start();
    }

    private void runWorker() {
        while (true) {
            try {
                String phrase = queue.take();

                String script = String.format(
                        "Add-Type -AssemblyName System.Speech; " +
                                "$synth = New-Object System.Speech.Synthesis.SpeechSynthesizer; " +
                                "$synth.SelectVoice('%s'); " +
                                "$synth.Speak('%s');",
                        VOICE, phrase
                );

                Process process = new ProcessBuilder("powershell", "-Command", script)
                        .inheritIO()
                        .start();

                processes.add(process);

                process.waitFor();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void playSound(String phrase) {
        queue.offer(phrase);
    }

    public void ok() {
        playSound("Окей");
    }

    public void dontUnderstand() {
        playSound("Не понял");
    }

    public void stopAll() {
        processes.forEach(Process::destroy);
        queue.clear();
    }
}
