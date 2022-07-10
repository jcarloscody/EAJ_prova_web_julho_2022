package com.example.camisa.service;

import com.example.camisa.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    UsuarioRepository repository;

    UsuarioService(UsuarioRepository repository){
        this.repository = repository;
    }
}
