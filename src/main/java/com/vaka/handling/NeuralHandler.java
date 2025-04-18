package com.vaka.handling;

import com.vaka.service.SoundPlayerService;

public class NeuralHandler implements Handler {
    private final SoundPlayerService soundPlayerService;

    public NeuralHandler(SoundPlayerService soundPlayerService) {
        this.soundPlayerService = soundPlayerService;
    }

    /**
    *         if (text.contains("спроси") || text.contains("спросить") || text.contains(
            *                 "узнай") || text.contains("узнать") || text.contains("скажи")) {
     *             try {
     *                 System.out.println("Извлекаю вопросительную часть...");
     *                 fileTextService.writeText("Думаю...");
     *                 String response = text.substring(text.indexOf(" "));
     *                 String answer = neuralService.request(response);
     *                 fileTextService.writeText(answer);
     *             } catch (IndexOutOfBoundsException e) {
     *                 fileTextService.writeText("Вы не сказали, что спросить :)");
     *             }
     *         }
     */
    @Override
    public String handle(String phrase) {
        soundPlayerService.ok();
        return "Спрошу у нейросети про " + phrase;
    }
}
