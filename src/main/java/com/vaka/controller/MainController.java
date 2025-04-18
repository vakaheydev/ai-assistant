package com.vaka.controller;

import com.vaka.service.FileTextService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class MainController {
    private final FileTextService fileService;

    public MainController(FileTextService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/info")
    public String getInfo(Model model) {
        String data = fileService.getText();

        if (data.isEmpty()) {
            model.addAttribute("data", "No data was read");
        } else {
            model.addAttribute("data", data);
        }


        return "index";
    }
}
