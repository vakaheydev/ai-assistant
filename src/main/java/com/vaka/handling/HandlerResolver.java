package com.vaka.handling;

import com.vaka.service.SoundPlayerService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class HandlerResolver {
    private final SoundPlayerService soundPlayerService;
    private final Map<String, Handler> firstWordsHandlers = new HashMap<>();

    public HandlerResolver(SoundPlayerService soundPlayerService) {
        this.soundPlayerService = soundPlayerService;
        firstWordsHandlers.put("привет", (phrase) -> {
            soundPlayerService.ok();
            return "Привет, друг!";
        });
        firstWordsHandlers.put("открой", new OpenHandler(soundPlayerService));
        firstWordsHandlers.put("закрой", new CloseHandler(soundPlayerService));
        firstWordsHandlers.put("спроси", new NeuralHandler(soundPlayerService));
    }

    public Handler resolve(String phrase) {
        String firstWord = phrase.split(" ")[0];
        return firstWordsHandlers.getOrDefault(firstWord, new UnrecognizedHandler(soundPlayerService));
    }
}
