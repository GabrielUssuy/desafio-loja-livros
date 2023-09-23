package com.ussuy.gabriel.desafiolojalivros.autor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NovoAutorRequest {

    private static final int TAMANHO_LIMITE_DESCRICAO = 400;

    @NotBlank
    private String nome;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Size(max = TAMANHO_LIMITE_DESCRICAO)
    private String descricao;

    public NovoAutorRequest(@NotBlank  String nome,
                            @NotBlank @Email String email,
                            @NotBlank @Size(max = TAMANHO_LIMITE_DESCRICAO) String descricao) {
        this.nome = nome;
        this.email = email;
        this.descricao = descricao;
    }

    public Autor toAutor() {
        return new Autor(nome, email, descricao);
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
