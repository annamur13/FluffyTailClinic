package ru.ssau.towp.fluffytailclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EntryController {

    @GetMapping("/entry")
    public String showEntryPage() {
        return "entry.html";
    }

    @PostMapping("/entry")
    public String handleEntry(@RequestParam String name, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "Hello, " + name + "!");
        return "redirect:/entry.html";
    }
}
