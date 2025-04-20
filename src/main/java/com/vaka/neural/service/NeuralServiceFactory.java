package com.vaka.neural.service;

import com.vaka.neural.AssistantNeuralRequestBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class NeuralServiceFactory {
    private static GeneralNeuralService generalNeuralService;
    private final AssistantNeuralRequestBuilder requestBuilder;

    public NeuralServiceFactory(RestClient restClient, @Value("${neural.api.key}") String authToken,
                                AssistantNeuralRequestBuilder requestBuilder) {
        this.requestBuilder = requestBuilder;
        generalNeuralService = new GeneralNeuralService(null, restClient, authToken, requestBuilder);
    }

    public List<NeuralModel> getAvailableNeuralModels() {
        return List.of(NeuralModel.values());
    }

    public NeuralService getNeuralService(NeuralModel model) {
        return switch (model) {
            case BYTE_DANCE_72B, DEEP_SEEK_V3, QWEN_72B, GEMINI_25_PRO_EXP, GEMMA_3_27B, QWERKY_72B -> {
                generalNeuralService.setNeuralModel(model);
                yield generalNeuralService;
            }
        };
    }
}
