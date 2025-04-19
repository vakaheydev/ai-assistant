package com.vaka.handling.open;

import lombok.Getter;

@Getter
public class OpenCommandTarget {
    private final ProcessBuilder processBuilder;

    public OpenCommandTarget(String itemName) {
        this.processBuilder = new ProcessBuilder("cmd", "/c", "start", itemName);
    }

    public OpenCommandTarget(String itemName, String secondItemName) {
        this.processBuilder = new ProcessBuilder("cmd", "/c", "start", itemName, secondItemName);
    }
}
