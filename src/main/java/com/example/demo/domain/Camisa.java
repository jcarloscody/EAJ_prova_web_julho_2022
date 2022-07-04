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
public class Camisa{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String modelo;
    String nomeFabricante;
    String tecido;
    char tamanho;

    String imageUri;

    Boolean estoque;
}
