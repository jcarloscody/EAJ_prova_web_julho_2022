package com.example.demo.controllers;

import com.example.demo.services.CamisaService;

import org.springframework.stereotype.Controller;

@Controller
public class CamisaController {

    private final CamisaService service;

    public CamisaController(CamisaService service) {
        this.service = service;
    }
}
