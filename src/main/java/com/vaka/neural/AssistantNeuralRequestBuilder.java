package com.vaka.neural;

import org.springframework.stereotype.Component;

@Component
public class AssistantNeuralRequestBuilder {
    public String buildAssistantRequest(String request) {
        String preForm = "Ты - мой AI помощник, установленный на моём компьютере." +
                "Твоя задача помогать мне с выполнением задач на моём устройстве." +
                "Твои сообщения должны быть красивыми и естественными." +
                "Например, никогда не используй явно \"\\n\" в своих сообщениях.";
        return preForm + "\n" + request;
    }
}
