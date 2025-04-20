package com.vaka.handling;

import com.vaka.neural.service.NeuralService;
import com.vaka.service.SoundPlayerService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NeuralHandler implements PhraseHandler {
    private final SoundPlayerService soundPlayerService;
    private final NeuralService neuralService;

    public NeuralHandler(SoundPlayerService soundPlayerService, NeuralService neuralService) {
        this.soundPlayerService = soundPlayerService;
        this.neuralService = neuralService;
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
        soundPlayerService.playSound("Спрошу у нейросети");
        String answer = neuralService.request(phrase);
        soundPlayerService.playSound(answer);
        return answer;
    }
}
