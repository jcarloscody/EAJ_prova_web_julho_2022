package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Usuario{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String nome;
    String email;
    String username;
    String password;

    int idade;

    Boolean admin;
}
