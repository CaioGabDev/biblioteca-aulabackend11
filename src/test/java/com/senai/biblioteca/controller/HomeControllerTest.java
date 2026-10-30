package com.senai.biblioteca.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
@TestPropertySource(properties = "app.versao=1.0.0")
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Teste 1 - Pagina inicial
    @Test
    void testarPaginaInicial() throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem")
                        .value("Bem-vindo ao sistema de gerenciamento da Biblioteca CloudTech Solutions"));
    }

    // Teste 2 - Documentacao dos endpoints
    @Test
    void testarDocumentacao() throws Exception {

        mockMvc.perform(get("/documentacao"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.endpoints").isArray())
                .andExpect(jsonPath("$.endpoints.length()").value(6))
                .andExpect(jsonPath("$.endpoints[0]").value("/"))
                .andExpect(jsonPath("$.endpoints[1]").value("/livros"));
    }

    // Teste 3 - Versao da aplicacao
    @Test
    void testarVersaoDaAplicacao() throws Exception {

        mockMvc.perform(get("/versao"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.aplicacao").value("biblioteca"))
                .andExpect(jsonPath("$.versao").value("1.0.0"));
    }
}
