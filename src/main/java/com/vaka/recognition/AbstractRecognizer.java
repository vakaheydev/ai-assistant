package com.vaka.recognition;

public abstract class AbstractRecognizer implements CustomRecognizer {
    protected final CloseableModel closeableModel;

    public AbstractRecognizer(CloseableModel closeableModel) {
        this.closeableModel = closeableModel;
    }

    protected String extractText(String jsonText) {
        String[] split = jsonText.split("\n");
        String textLine = split[1];
        String[] textSplit = textLine.split(":");
        return textSplit[1].substring(2, textSplit[1].length() - 1);
    }
}

