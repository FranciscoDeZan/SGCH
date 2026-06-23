package com.consignataria.sgch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Mapea la raíz de la aplicación al menú principal
    @GetMapping("/")
    public String mostrarMenuPrincipal() {
        return "menuPrincipal"; // Llama a menuPrincipal.html
    }
}