package com.vaka.neural.service;

public interface NeuralService {
    String request(String requestText);

    boolean isAvailable();

    NeuralModel getNeuralModel();
}
