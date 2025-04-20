package com.vaka.neural.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaka.neural.AssistantNeuralRequestBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Slf4j
public class GeneralNeuralService implements NeuralService {
    private final String URL = "https://openrouter.ai/api/v1/chat/completions";

    private NeuralModel neuralModel;
    private final RestClient restClient;
    private final String authToken;
    private final AssistantNeuralRequestBuilder requestBuilder;

    public GeneralNeuralService(NeuralModel neuralModel, RestClient restClient, String authToken,
                                AssistantNeuralRequestBuilder requestBuilder) {
        this.neuralModel = neuralModel;
        this.restClient = restClient;
        this.authToken = authToken;
        this.requestBuilder = requestBuilder;
    }

    @Override
    public String request(String requestText) {
        String request = requestBuilder.buildAssistantRequest(requestText);
        String responseText = sendRequest(request);
        log.debug("Response text: {}", responseText.trim());
        return getAnswer(responseText);
    }


    @Override
    public boolean isAvailable() {
        ResponseEntity<Void> responseEntity = restClient.options()
                .uri(URL)
                .header("Authorization", "Bearer " + authToken)
                .retrieve()
                .toBodilessEntity();

        return responseEntity.getStatusCode().is2xxSuccessful();
    }

    private String sendRequest(String requestMessage) {
        if (neuralModel == null) {
            throw new IllegalStateException("Can't send request because neural model is null");
        }

        log.debug("Request message: {}", requestMessage);
        Map<String, Object> requestBody = Map.of(
                "model", neuralModel.getModelName(),
                "messages", new Object[]{
                        Map.of("role", "user", "content", requestMessage)
                }
        );

        return restClient.post()
                .uri(URL)
                .header("Authorization", "Bearer " + authToken)
                .body(requestBody)
                .retrieve()
                .body(String.class);
    }

    private String getAnswer(String responseText) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(responseText);
            JsonNode msgNode = rootNode.path("choices").get(0).path("message").path("content");
            String messageJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(msgNode);
            return messageJson.substring(1, messageJson.length() - 1);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (NullPointerException e) {
            log.error(e.getMessage(), e);
            return "";
        }
    }

    @Override
    public NeuralModel getNeuralModel() {
        return neuralModel;
    }

    public void setNeuralModel(NeuralModel neuralModel) {
        this.neuralModel = neuralModel;
    }
}
