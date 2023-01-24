package com.infobip.secops.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@Controller
@RequestMapping(value = "/dir")
public class DirController {

    private String processOutput(Process p) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader err = new BufferedReader(new InputStreamReader(p.getErrorStream()));

        String s;
        StringBuilder out = new StringBuilder();
        while ((s = in.readLine()) != null) {
            out.append(s);
        }
        while ((s = err.readLine()) != null) {
            out.append(s);
        }
        return out.toString();
    }

    @GetMapping
    public String dirUs() {
        return "dir";
    }

    @PostMapping
    public String dir(@RequestParam("dir") String dir, Model model) {
        String output;
        Boolean success;
      
        try {
            if (dir.contains(".")) throw new ValidationException("NO.");
            String cmd = "ls /home" + dir;
            Process p = Runtime.getRuntime().exec(new String[]{"/bin/bash", "-c", cmd});
            p.waitFor();
            output = processOutput(p);
            success = Boolean.TRUE;
        } catch (Exception e) {
            output = "ERROR: " + e.getMessage();
            success = Boolean.FALSE;
        }

        model.addAttribute("success", success);
        model.addAttribute("output", output);
        return "dir";
    }
  
    @GetMapping(value = "/safe")
    public String dirUsSafe() {
        return "dir_safe";
    }
  
    @PostMapping(value = "/safe")
    public String dirSafe(@RequestParam("dir") String dir, Model model) {
        String output;
        Boolean success;

        try {
            if (dir.contains(".")) throw new ValidationException("NO.");
            ProcessBuilder pb = new ProcessBuilder("ls", "/home" + dir);
            Process p = pb.start();
            p.waitFor();
            output = processOutput(p);
            success = Boolean.TRUE;
        } catch (Exception e) {
            output = "ERROR: " + e.getMessage();
            success = Boolean.FALSE;
        }

        model.addAttribute("success", success);
        model.addAttribute("output", output);
        return "dir";
    }

    @GetMapping(value = "/notsafe")
    public String dirUsNotSafe() {
        return dirUs();
    }

    @PostMapping(value = "/notsafe")
    public String dirNotSafe(@RequestParam("dir") String dir, Model model) {
        String output;
        Boolean success;
        try {
            ProcessBuilder pb = new ProcessBuilder(dir);
            Process p = pb.start();
            p.waitFor();
            output = processOutput(p);
            success = Boolean.TRUE;
        } catch (Exception e) {
            output = "ERROR: " + e.getMessage();
            success = Boolean.FALSE;
        }
        model.addAttribute("success", success);
        model.addAttribute("output", output);
        return "dir";
    }
}
