package com.senai.biblioteca.models;

public class Livro {

    // Atributos do livro

    private int id;
    private String titulo;
    private String autor;
    private String editora;
    private int ano;
    private String categoria;
    private String status;

    // Construtor vazio - necessario para o Spring converter o JSON recebido
    public Livro() {
    }

    // Construtor

    public Livro(int id, String titulo, String autor, String editora,
            int ano, String categoria, String status) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.ano = ano;
        this.categoria = categoria;
        this.status = status;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
