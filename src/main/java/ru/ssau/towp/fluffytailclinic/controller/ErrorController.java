package ru.ssau.towp.fluffytailclinic.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping
    public String handleError(HttpServletRequest request, HttpServletResponse response, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        // Если ERROR_STATUS_CODE отсутствует, пытаемся взять его из параметра запроса
        if (status == null) {
            String paramStatus = request.getParameter("status");
            if (paramStatus != null) {
                status = Integer.parseInt(paramStatus);
            } else {
                status = 500; // По умолчанию
            }
        }

        int statusCode = Integer.parseInt(status.toString());

        if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
            model.addAttribute("message", "Ошибка 401: Доступ запрещён. Вы не авторизованы.");
        } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
            model.addAttribute("message", "Ошибка 403: У вас нет прав для доступа к этому ресурсу.");
        } else if (statusCode == HttpStatus.NOT_FOUND.value()) {
            model.addAttribute("message", "Ошибка 404: Страница не найдена.");
        } else {
            model.addAttribute("message", "Произошла неизвестная ошибка.");
        }

        model.addAttribute("status", statusCode);
        return "error";
    }
}
