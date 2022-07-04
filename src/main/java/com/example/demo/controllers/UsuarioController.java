package com.example.demo.controllers;
import com.example.demo.services.UsuarioService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String login(){
        return "index";
    }

}
