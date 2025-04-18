package com.vaka;

import com.vaka.service.AssistantService;
import com.vaka.service.RecognitionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Application {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(Application.class, args);
        AssistantService assistantService = ctx.getBean("assistantService", AssistantService.class);
        assistantService.start();
    }
}
