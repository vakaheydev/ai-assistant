package com.vaka.handling;

import com.vaka.service.SoundPlayerService;

public class CloseHandler implements Handler {
    private final SoundPlayerService soundPlayerService;

    public CloseHandler(SoundPlayerService soundPlayerService) {
        this.soundPlayerService = soundPlayerService;
    }

    /**
     case "закрой блокнот" -> {
     *                 new ProcessBuilder("cmd", "/c", "taskkill", "/IM", "notepad.exe", "/F").start();
     *                 fileTextService.writeText("Закрыл блокнот");
     *             }
     */
    @Override
    public String handle(String phrase) {
        soundPlayerService.ok();
        return "Закрываю " + phrase;
    }
}
