package com.vaka.handling.determenition;

import com.vaka.handling.PhraseHandler;
import com.vaka.service.SoundPlayerService;

import java.time.LocalDateTime;

public class HowMuchHandler implements PhraseHandler {
    private final SoundPlayerService soundPlayerService;

    public HowMuchHandler(SoundPlayerService soundPlayerService) {
        this.soundPlayerService = soundPlayerService;
    }

    @Override
    public String handle(String phrase) {
        return switch (phrase) {
            case "время", "часов" -> {
                LocalDateTime now = LocalDateTime.now();
                String currentTime = String.format("Сейчас %d часов, %d минут", now.getHour(), now.getMinute());
                soundPlayerService.playSound(currentTime);
                yield currentTime;
            }
            default -> {
                soundPlayerService.playSound("Я не знаю, что это такое");
                yield "Я Вас не понял :(";
            }
        };
    }
}
