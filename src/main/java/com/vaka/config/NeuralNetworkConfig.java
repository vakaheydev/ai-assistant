package com.vaka.config;

import com.vaka.neural.service.NeuralModel;
import com.vaka.neural.service.NeuralModelService;
import com.vaka.neural.service.NeuralService;
import com.vaka.neural.service.NeuralServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class NeuralNetworkConfig {
    @Value("${neural.model}")
    private String modelName;

    private final NeuralServiceFactory neuralServiceFactory;
    private final NeuralModelService neuralModelService;

    @Autowired
    public NeuralNetworkConfig(NeuralServiceFactory neuralServiceFactory, NeuralModelService neuralModelService) {
        this.neuralServiceFactory = neuralServiceFactory;
        this.neuralModelService = neuralModelService;
    }

    @Bean
    public NeuralService defaultNeuralService() {
        return neuralServiceFactory.getNeuralService(defaultNeuralModel());
    }

    @Bean
    public NeuralModel defaultNeuralModel() {
        NeuralModel neuralModel = neuralModelService.getNeuralModelByName(modelName);
        log.info("Default neural model: {}", neuralModel);
        return neuralModel;
    }
}
