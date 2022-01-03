package org.kenux.miraclelibrary.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "제목~~~");
        model.addAttribute("message", "안녕하세요.~~~");
        return "home";
    }
}
