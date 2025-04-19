package com.vaka.handling;

import com.vaka.handling.open.OpenHandler;
import com.vaka.neural.service.NeuralService;
import com.vaka.service.SoundPlayerService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class HandlerResolver {
    private final SoundPlayerService soundPlayerService;
    private final NeuralService neuralService;
    private final Map<String, Handler> firstWordsHandlers = new HashMap<>();

    public HandlerResolver(SoundPlayerService soundPlayerService, NeuralService neuralService) {
        this.soundPlayerService = soundPlayerService;
        this.neuralService = neuralService;
        firstWordsHandlers.put("привет", (phrase) -> {
            soundPlayerService.playSound("Привет");
            return "Привет, друг!";
        });
        firstWordsHandlers.put("открой", new OpenHandler(soundPlayerService));
        firstWordsHandlers.put("закрой", new CloseHandler(soundPlayerService));
        firstWordsHandlers.put("спроси", new NeuralHandler(soundPlayerService, neuralService));
        firstWordsHandlers.put("стоп", new StopHandler(soundPlayerService));
    }

    public Handler resolve(String phrase) {
        String firstWord = phrase.split(" ")[0];
        return firstWordsHandlers.getOrDefault(firstWord, new NeuralHandler(soundPlayerService, neuralService));
    }
}
