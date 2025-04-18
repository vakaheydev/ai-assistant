package com.vaka;

import com.vaka.service.RecognitionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Application {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(Application.class, args);
        RecognitionService service = ctx.getBean(RecognitionService.class);
        service.start();
    }
}
