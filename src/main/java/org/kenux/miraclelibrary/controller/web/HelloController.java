package org.kenux.miraclelibrary.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hello")
public class HelloController {

    @GetMapping
    String hello(Model model) {
        model.addAttribute("name", "kenux");
        return "hello";
    }
}