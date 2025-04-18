package com.vaka.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
public class SoundPlayerService {
    private String VOICE = "Vsevolod";
    public void playSound(String phrase) {
        CompletableFuture.runAsync(() -> {
            try {
                String script = String.format(
                        "Add-Type -AssemblyName System.Speech; " +
                                "$synth = New-Object System.Speech.Synthesis.SpeechSynthesizer; " +
                                "$synth.SelectVoice('%s'); " +
                                "$synth.Speak('%s');",
                        VOICE, phrase
                );
                new ProcessBuilder("powershell", "-Command", script)
                    .start()
                    .waitFor();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void ok() {
        playSound("Окей");
    }

    public void dontUnderstand() {
        playSound("Не понял");
    }
}
