package com.vaka.handling.open;

import com.vaka.handling.PhraseHandler;
import com.vaka.service.SoundPlayerService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OpenHandler implements PhraseHandler {
    private final SoundPlayerService soundPlayerService;
    private final Map<String, OpenCommandTarget> openItems = new HashMap<>();

    public OpenHandler(SoundPlayerService soundPlayerService) {
        this.soundPlayerService = soundPlayerService;

        openItems.put("блокнот", new OpenCommandTarget("notepad"));
        openItems.put("хром", new OpenCommandTarget("chrome"));
        openItems.put("браузер", openItems.get("хром"));
        openItems.put("консоль", new OpenCommandTarget("cmd"));
        openItems.put("шелл", new OpenCommandTarget("powershell"));
        openItems.put("проводник", new OpenCommandTarget("explorer"));
        openItems.put("ютуб", new OpenCommandTarget("chrome", "https://www.youtube.com"));
    }

    @Override
    public String handle(String phrase) {
        OpenCommandTarget openCommandTarget = openItems.get(phrase);

        if (openCommandTarget == null) {
            soundPlayerService.playSound("Я не знаю что это такое");
            return "Не знаю, как открыть \"" + phrase + "\"";
        }

        soundPlayerService.playSound("Открываю " + phrase);

        process(openCommandTarget);
        return "Открываю " + phrase;
    }

    public void process(OpenCommandTarget openCommandTarget) {
        try {
            openCommandTarget.getProcessBuilder().start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
