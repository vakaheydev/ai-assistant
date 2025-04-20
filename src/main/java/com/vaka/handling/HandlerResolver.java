package com.vaka.handling;

import com.vaka.handling.determenition.HowMuchHandler;
import com.vaka.handling.open.OpenHandler;
import com.vaka.neural.service.NeuralService;
import com.vaka.service.SoundPlayerService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.vaka.handling.CommandPhrase.*;

@Component
public class HandlerResolver {
    private final SoundPlayerService soundPlayerService;
    private final NeuralService neuralService;
    private final Map<String, PhraseHandler> firstWordsHandlers = new HashMap<>();
    private final PhraseHandler defaultHandler;

    public HandlerResolver(SoundPlayerService soundPlayerService, NeuralService neuralService) {
        this.soundPlayerService = soundPlayerService;
        this.neuralService = neuralService;
        this.defaultHandler = new NeuralHandler(soundPlayerService, neuralService);

        firstWordsHandlers.put("дата", phrase -> {
            LocalDate now = LocalDate.now();
            String currentDate = now.format(DateTimeFormatter.ofPattern("d MMMM", new Locale("ru")));
            soundPlayerService.playSound("Сегодня " + currentDate);
            return "Сегодня " + currentDate;
        });

        Map<CommandPhrase, PhraseHandler> commandHandlers = Map.of(
                GREETING, phrase -> {
                    soundPlayerService.playSound("Привет");
                    return "Привет, друг!";
                },
                CommandPhrase.OPEN, new OpenHandler(soundPlayerService),
                CommandPhrase.CLOSE, new CloseHandler(soundPlayerService),
                CommandPhrase.ASK, new NeuralHandler(soundPlayerService, neuralService),
                CommandPhrase.STOP, new StopHandler(soundPlayerService),
                CommandPhrase.HOW_MUCH, new HowMuchHandler(soundPlayerService)
        );

        for (var entry : commandHandlers.entrySet()) {
            for (String phrase : entry.getKey().getPhrases()) {
                firstWordsHandlers.put(phrase, entry.getValue());
            }
        }
    }

    public PhraseHandler resolve(String phrase) {
        String firstWord = phrase.split(" ")[0];
        return firstWordsHandlers.getOrDefault(firstWord, defaultHandler);
    }
}
