package org.example.api.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {


    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object type = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION_TYPE);

        model.addAttribute("errorMessage", message != null ? message : "Unexpected error");
        model.addAttribute("errorType", type != null ? type : "Unknown");
        model.addAttribute("status", status);
        return "error";
    }
}
