package com.vaka.handling;

import com.vaka.service.SoundPlayerService;

public class OpenHandler implements Handler {
    private final SoundPlayerService soundPlayerService;

    public OpenHandler(SoundPlayerService soundPlayerService) {
        this.soundPlayerService = soundPlayerService;
    }

    /**
     *             case "открой хром" -> {
     *                 var pb = new ProcessBuilder("cmd", "/c", "start", "chrome");
     *                 pb.start();
     *                 fileTextService.writeText("Открыл хром");
     *             }
     *             case "открой консоль" -> {
     *                 var pb = new ProcessBuilder("cmd", "/c", "start", "cmd");
     *                 pb.start();
     *                 fileTextService.writeText("Открыл консоль");
     *             }
     *             case "открой шелл" -> {
     *                 var pb = new ProcessBuilder("cmd", "/c", "start", "powershell");
     *                 pb.start();
     *                 fileTextService.writeText("Открыл консоль");
     *             }
     *             case "открой папку", "открой проводник" -> {
     *                 var pb = new ProcessBuilder("explorer");
     *                 pb.start();
     *                 fileTextService.writeText("Открыл проводник");
     *             }
     *             case "открой идею" -> {
     *                 var pb = new ProcessBuilder("cmd", "/c", "start", "idea");
     *                 pb.start();
     *                 fileTextService.writeText("Открыл Intellij IDEA");
     *             }
     *
     *             case "открой вс", "открой код", "открой вэ эс" -> {
     *                 var pb = new ProcessBuilder("cmd", "/c", "start", "code");
     *                 pb.start();
     *                 fileTextService.writeText("Открыл VS CODE");
     *             }
     *
     *             case "открой ютуб" -> {
     *                 new ProcessBuilder("cmd", "/c", "start", "chrome", "https://www.youtube.com").start();
     *                 fileTextService.writeText("Открыл ютуб");
     *             }
     *             case "открой блокнот" -> {
     *                 new ProcessBuilder("cmd", "/c", "start", "notepad").start();
     *                 fileTextService.writeText("Открыл блокнот");
     *             }
     */
    @Override
    public String handle(String phrase) {
        soundPlayerService.ok();
        return "Открываю " + phrase;
    }
}
