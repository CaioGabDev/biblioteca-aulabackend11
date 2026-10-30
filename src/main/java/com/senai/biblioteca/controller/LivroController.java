package com.senai.biblioteca.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import com.senai.biblioteca.models.Livro;

@RestController
@RequestMapping("/livros")
public class LivroController {

    // Lista que simula um banco de dados
    private List<Livro> livros = new ArrayList<>();

    // Construtor - Acervo inicial
    public LivroController() {

        livros.add(new Livro(1, "Introducao a Programacao", "Joao Silva",
                "Tech Books", 2022, "Tecnologia", "Disponivel"));

        livros.add(new Livro(2, "Redes de Computadores", "Carlos Mendes",
                "Net Books", 2021, "Redes", "Disponivel"));

        livros.add(new Livro(3, "Banco de Dados para Iniciantes", "Maria Souza",
                "EduTech", 2023, "Informatica", "Emprestado"));
    }

    // GET /livros - Consultar livros disponiveis (item b)
    @GetMapping
    public ResponseEntity<List<Livro>> listarLivros() {

        // HTTP 200 - OK
        return ResponseEntity.ok(livros);
    }

    // POST /livros/cadastrar - Cadastrar novo livro (item c)
    @PostMapping("/cadastrar")
    public ResponseEntity<Livro> cadastrarLivro(
            @RequestBody Livro livro) {

        // Validacao basica dos dados recebidos
        if (livro.getTitulo() == null || livro.getTitulo().isBlank()) {

            // HTTP 400 - Bad Request
            return ResponseEntity.badRequest().build();
        }

        // Se o id nao for informado, gera o proximo da sequencia
        if (livro.getId() == 0) {
            livro.setId(proximoId());
        }

        livros.add(livro);

        // HTTP 201 - Created
        return ResponseEntity.status(201).body(livro);
    }

    // PUT /livros/alterar/{id} - Alterar informacoes de um livro (item d)
    @PutMapping("/alterar/{id}")
    public ResponseEntity<Livro> alterarLivro(
            @PathVariable int id,
            @RequestBody Livro dados) {

        for (Livro livro : livros) {

            if (livro.getId() == id) {

                livro.setTitulo(dados.getTitulo());
                livro.setAutor(dados.getAutor());
                livro.setEditora(dados.getEditora());
                livro.setAno(dados.getAno());
                livro.setCategoria(dados.getCategoria());
                livro.setStatus(dados.getStatus());

                return ResponseEntity.ok(livro);
            }
        }

        // HTTP 404 - Not Found
        return ResponseEntity.notFound().build();
    }

    // GET /livros/{id} - Consultar livro por ID (item e)
    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarLivro(
            @PathVariable int id) {

        for (Livro livro : livros) {

            if (livro.getId() == id) {
                return ResponseEntity.ok(livro);
            }
        }

        // HTTP 404 - Not Found
        return ResponseEntity.notFound().build();
    }

    // Calcula o proximo id disponivel
    private int proximoId() {

        int maiorId = 0;

        for (Livro livro : livros) {

            if (livro.getId() > maiorId) {
                maiorId = livro.getId();
            }
        }

        return maiorId + 1;
    }
}
