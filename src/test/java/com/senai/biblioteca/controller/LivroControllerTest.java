package com.senai.biblioteca.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LivroController.class)
// Cada teste recebe uma copia nova do controller (lista em memoria limpa)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class LivroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Teste 1 - Consultar livros disponiveis
    @Test
    void testarListarLivros() throws Exception {

        mockMvc.perform(get("/livros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].titulo").value("Introducao a Programacao"))
                .andExpect(jsonPath("$[0].autor").value("Joao Silva"))
                .andExpect(jsonPath("$[0].editora").value("Tech Books"))
                .andExpect(jsonPath("$[0].ano").value(2022))
                .andExpect(jsonPath("$[0].categoria").value("Tecnologia"))
                .andExpect(jsonPath("$[0].status").value("Disponivel"));
    }

    // Teste 2 - Cadastrar novo livro
    @Test
    void testarCadastrarLivro() throws Exception {

        String livro = """
                {
                    "titulo": "Banco de Dados para Iniciantes",
                    "autor": "Maria Souza",
                    "editora": "EduTech",
                    "ano": 2023,
                    "categoria": "Informatica",
                    "status": "Disponivel"
                }
                """;

        mockMvc.perform(post("/livros/cadastrar")
                .contentType("application/json")
                .content(livro))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.titulo").value("Banco de Dados para Iniciantes"))
                .andExpect(jsonPath("$.ano").value(2023));
    }

    // Teste 3 - Cadastro sem titulo deve ser recusado
    @Test
    void testarCadastrarLivroInvalido() throws Exception {

        String livro = """
                {
                    "autor": "Maria Souza",
                    "editora": "EduTech",
                    "ano": 2023
                }
                """;

        mockMvc.perform(post("/livros/cadastrar")
                .contentType("application/json")
                .content(livro))
                .andExpect(status().isBadRequest());
    }

    // Teste 4 - Alterar informacoes de um livro
    @Test
    void testarAlterarLivro() throws Exception {

        String livro = """
                {
                    "titulo": "Banco de Dados Essencial",
                    "autor": "Maria Souza",
                    "editora": "EduTech",
                    "ano": 2024,
                    "categoria": "Banco de Dados",
                    "status": "Emprestado"
                }
                """;

        mockMvc.perform(put("/livros/alterar/3")
                .contentType("application/json")
                .content(livro))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.titulo").value("Banco de Dados Essencial"))
                .andExpect(jsonPath("$.ano").value(2024))
                .andExpect(jsonPath("$.categoria").value("Banco de Dados"))
                .andExpect(jsonPath("$.status").value("Emprestado"));
    }

    // Teste 5 - Alterar livro inexistente
    @Test
    void testarAlterarLivroInexistente() throws Exception {

        String livro = """
                {
                    "titulo": "Livro Que Nao Existe",
                    "autor": "Autor",
                    "editora": "Editora",
                    "ano": 2024,
                    "categoria": "Geral",
                    "status": "Disponivel"
                }
                """;

        mockMvc.perform(put("/livros/alterar/99")
                .contentType("application/json")
                .content(livro))
                .andExpect(status().isNotFound());
    }

    // Teste 6 - Consultar livro por ID
    @Test
    void testarBuscarLivroPorId() throws Exception {

        mockMvc.perform(get("/livros/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.titulo").value("Redes de Computadores"))
                .andExpect(jsonPath("$.autor").value("Carlos Mendes"));
    }

    // Teste 7 - Consultar livro inexistente
    @Test
    void testarLivroInexistente() throws Exception {

        mockMvc.perform(get("/livros/99"))
                .andExpect(status().isNotFound());
    }
}
