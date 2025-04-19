package com.vaka.handling;

import com.vaka.neural.service.NeuralService;
import com.vaka.service.SoundPlayerService;

public class UnrecognizedHandler implements Handler {
    private final SoundPlayerService soundPlayerService;

    public UnrecognizedHandler(SoundPlayerService soundPlayerService) {
        this.soundPlayerService = soundPlayerService;
    }

    @Override
    public String handle(String phrase) {
        soundPlayerService.dontUnderstand();
        return "К сожалению, я не понимаю";
    }
}
