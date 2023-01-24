package com.infobip.secops.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Controller
@RequestMapping(value = "/source")
public class SourceController {

    private static final boolean REVENGE = false;

    @GetMapping
    public String source(Model model) {
        model.addAttribute("revenge", REVENGE);
        return "source";
    }

    @PostMapping
    public String fetchSource(@RequestParam("host") String host, Model model) {
        Boolean success;
        String source = null;
        try {
            URL url = new URL(host);
            URLConnection connection = url.openConnection();
            source = fetch(connection.getInputStream());
            success = Boolean.TRUE;
        } catch (Exception e) {
            success = Boolean.FALSE;
        }
        model.addAttribute("revenge", REVENGE);
        model.addAttribute("success", success);
        model.addAttribute("source", source);
        return "source";
    }

    private String fetch(InputStream is) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is)))
        {
            String input;
            StringBuilder builder = new StringBuilder();
            while ((input = reader.readLine()) != null) {
                builder.append(input);
            }
            return builder.toString();
        }
    }
}
