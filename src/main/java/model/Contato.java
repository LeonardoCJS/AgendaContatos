package model;

import model.enums.Categoria;

public class Contato {
    private final Long id;
    private String nome;
    private String telefone;
    private String email;
    private Categoria categoria;

    public Contato(Long id, String nome, String telefone, String email, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.categoria = categoria;
    }

    public Contato(String nome, String telefone, String email, Categoria categoria) {
        this.id = null;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Contato setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getTelefone() {
        return telefone;
    }

    public Contato setTelefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Contato setEmail(String email) {
        this.email = email;
        return this;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Contato setCategoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    @Override
    public String toString() {
        return "Nome: " + nome +
                "\nTelefone: " + telefone +
                "\nEmail: " + email +
                "\nCategoria: " + categoria;
    }
}
