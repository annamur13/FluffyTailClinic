package ru.ssau.towp.fluffytailclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    @GetMapping("/dashboard")
    public String dashboard() {
        System.out.println("dashboard");
        return "dashboard";// Отдаёт шаблон dashboard.html
    }
}
