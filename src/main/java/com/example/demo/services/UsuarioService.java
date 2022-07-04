package com.example.demo.services;

import com.example.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import com.example.demo.domain.Usuario;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario create(Usuario u) {
        return repository.save(u);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Usuario update(Usuario u) {
        return repository.saveAndFlush(u);
    }

    public Usuario findById(Long id) {
        Optional<Usuario> UsuarioOptional = repository.findById(id);
        return UsuarioOptional.orElse(null);
    }

    public List<Usuario> findAll() {
        return repository.findAll();
    }
}