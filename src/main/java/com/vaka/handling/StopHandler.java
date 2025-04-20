package com.vaka.handling;

import com.vaka.service.SoundPlayerService;

public class StopHandler implements PhraseHandler {
    private final SoundPlayerService soundPlayerService;

    public StopHandler(SoundPlayerService soundPlayerService) {
        this.soundPlayerService = soundPlayerService;
    }

    @Override
    public String handle(String phrase) {
        soundPlayerService.stopAll();
        soundPlayerService.playSound("Остановился");
        return "Остановил любую активность";
    }
}
