package com.vaka.handling;

import java.util.List;

public enum CommandPhrase {
    GREETING(List.of("привет", "здравствуй", "здравствуйте", "хай", "хеллоу")),
    STOP(List.of("стоп", "пауза", "остановись", "прекрати", "перестань")),
    OPEN(List.of("открой", "открыть", "опен")),
    CLOSE(List.of("закрой", "закрыть", "клоуз")),
    ASK(List.of("спроси", "аск", "знаешь", "скажи")),
    HOW_MUCH(List.of("сколько"));

    private final List<String> phrases;

    CommandPhrase(List<String> phrases) {
        this.phrases = phrases;
    }

    public List<String> getPhrases() {
        return phrases;
    }
}
