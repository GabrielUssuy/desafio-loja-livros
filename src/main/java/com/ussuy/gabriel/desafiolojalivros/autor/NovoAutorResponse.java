package com.ussuy.gabriel.desafiolojalivros.autor;

public class NovoAutorResponse {

    private Long id;
    private String nome;
    private String email;
    private String descricao;

    public NovoAutorResponse(Long id, String nome, String email, String descricao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.descricao = descricao;
    }

    public static NovoAutorResponse from(Autor autor) {
        return new NovoAutorResponse(autor.getId(), autor.getNome(), autor.getEmail(), autor.getDescricao());
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getDescricao() {
        return descricao;
    }
}
