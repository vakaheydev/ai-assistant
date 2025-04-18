package com.vaka.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Service
public class FileTextService {
    private static final Path LOGS_FILE_PATH = Path.of("data/data.txt");

    public List<String> getTextLines() {
        createFileIfNotExists();
        try {
            return Files.readAllLines(Path.of(LOGS_FILE_PATH.toUri()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getText() {
        createFileIfNotExists();
        try {
            return Files.readString(Path.of(LOGS_FILE_PATH.toUri()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeText(String log) {
        createFileIfNotExists();
        clearText();

        try {
            Files.writeString(Path.of(LOGS_FILE_PATH.toUri()), log);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearText() {
        try {
            Files.writeString(Path.of(LOGS_FILE_PATH.toUri()), "", StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
        }
    }

    private void createFileIfNotExists() {
        File file = new File(LOGS_FILE_PATH.toString());

        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
