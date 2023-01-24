package com.infobip.secops.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/greet")
public class GreeterController {

    @GetMapping
    public String greet() {
        return "greet";
    }

    @PostMapping
    public String greet(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "greet";
    }

    @GetMapping("/get")
    public String greetGet(@RequestParam("name") String name, Model model) {
        return greet(name, model);
    }
}
